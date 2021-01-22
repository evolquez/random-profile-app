package com.example.randomprofile.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomprofile.R;
import com.example.randomprofile.config.Constants;
import com.example.randomprofile.dao.ProfileDao;
import com.example.randomprofile.data.database.AppDatabase;
import com.example.randomprofile.data.service.ProfilesService;
import com.example.randomprofile.data.service.RetrofitBuilder;
import com.example.randomprofile.databinding.ActivityMainBinding;
import com.example.randomprofile.entity.Profile;
import com.example.randomprofile.view.adapter.ProfilesAdapter;
import com.example.randomprofile.view.adapter.SuggestionsAdapter;
import com.example.randomprofile.view.listener.ScrollListener;
import com.example.randomprofile.view.subscriber.ProfilesFilterSubscriber;
import com.example.randomprofile.view.subscriber.ProfilesSubscriber;
import com.example.randomprofile.view.subscriber.TextChangeSubscriber;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ProfilesSubscriber.OnServiceProfileListener,
        ProfilesAdapter.OnProfileClickHandler, ScrollListener.OnScrollEndListener,
        TextChangeSubscriber.OnTextChangeListener, ProfilesFilterSubscriber.OnProfileFilterListener {

    private ActivityMainBinding activityMainBinding;

    private ProfilesAdapter profilesAdapter;
    private ProfilesAdapter favoritesAdapter;
    private ProgressBar loadingProfileIndicator;
    private ProfileDao profileDao;
    private ProfilesService service;
    private Boolean firstLoad;
    private List<Profile> filteredProfiles;
    private List<Profile> profilesLoaded;
    private List<Profile> allFavorites;
    private SuggestionsAdapter suggestionsAdapter;
    private AutoCompleteTextView filterField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = activityMainBinding.getRoot();

        setContentView(view);

        profileDao = AppDatabase.getInstance(this).profileDao();

        filteredProfiles = new ArrayList<>();
        profilesLoaded = new ArrayList<>();

        loadingProfileIndicator = activityMainBinding.loadingProfileIndicator;


        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.home_view_title));
        }

        initFilterField();


        // Initialize recyclerview component
        this.initialize();

        service = RetrofitBuilder.getInstance().getRetrofit().create(ProfilesService.class);
        loadingProfileIndicator.setVisibility(View.VISIBLE);
        this.firstLoad = true;
        this.loadProfiles();
    }

    private void initFilterField() {

        filterField = activityMainBinding.filterField;
        filterField.setThreshold(1);

        this.suggestionsAdapter = new SuggestionsAdapter(this, new ArrayList<>(), this);
        filterField.setAdapter(suggestionsAdapter);

        initFilterListener();
    }

    private void initFilterListener() {
        RxTextView
                .textChangeEvents(filterField)
                .skip(1)
                .debounce(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .flatMap((Func1<TextViewTextChangeEvent, Observable<String>>)
                        textViewTextChangeEvent -> Observable.just(textViewTextChangeEvent.text().toString()))
                .subscribe(new TextChangeSubscriber(this));
    }

    private void initialize() {

        activityMainBinding.reload.setOnClickListener(view -> {
            view.setVisibility(View.GONE);
            loadingProfileIndicator.setVisibility(View.VISIBLE);
            loadProfiles();
        });

        RecyclerView rvProfiles = activityMainBinding.gridProfiles;
        RecyclerView rvFavorites = activityMainBinding.gridFavorites;


        rvProfiles.setHasFixedSize(true);
        rvProfiles.setLayoutManager(new GridLayoutManager(this, 5));

        this.profilesAdapter = new ProfilesAdapter(new ArrayList<>(), this, this);

        rvProfiles.setAdapter(profilesAdapter);
        rvProfiles.addOnScrollListener(new ScrollListener(this));

        rvFavorites.setHasFixedSize(true);
        rvFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        this.favoritesAdapter = new ProfilesAdapter(new ArrayList<>(), this, this);

        rvFavorites.setAdapter(favoritesAdapter);
    }

    private void loadProfiles(){
        service.getProfiles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProfilesSubscriber(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadFavorites();
    }

    private void loadFavorites(){

        allFavorites = this.profileDao.getAll();

        this.suggestionsAdapter.setProfiles(allFavorites);
        this.suggestionsAdapter.notifyDataSetChanged();

        if(allFavorites.size() > 0){
            activityMainBinding.favoritesContainer.setVisibility(View.VISIBLE);
            this.favoritesAdapter.setProfiles(allFavorites, false);
        }else{
            activityMainBinding.favoritesContainer.setVisibility(View.GONE);
            this.favoritesAdapter.setProfiles(new ArrayList<>(), false);
        }

        this.favoritesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadCompleted(List<Profile> profiles) {

        if(firstLoad){
            activityMainBinding.reload.setVisibility(View.GONE);
            this.profilesLoaded = profiles;
        }else{
            this.profilesLoaded.addAll(profiles);
        }

        this.profilesAdapter.setProfiles(profiles, !firstLoad);

        this.profilesAdapter.notifyDataSetChanged();

        if(firstLoad){
            loadingProfileIndicator.setVisibility(View.GONE);
            firstLoad = false;
        }
    }

    @Override
    public void onLoadError(String error) {
        if(this.firstLoad){
            loadingProfileIndicator.setVisibility(View.GONE);
            activityMainBinding.reload.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProfileClicked(Profile profile) {
        Intent i = new Intent(this, ProfileDetailActivity.class);
        i.putExtra(Constants.PROFILE, profile);

        startActivity(i);
    }

    @Override
    public void onScrollEnd() {
        if(this.filteredProfiles.size() == 0)
            loadProfiles();
    }

    @Override
    public int getProfilesCount() {
        return this.profilesLoaded.size();
    }

    @Override
    public void emitTextFromSearchBox(String s) {
        if(!s.equals("")){
            Observable
                    .from(this.profilesLoaded)
                    .filter(profile -> profile.getFirstName().toLowerCase().contains(s.toLowerCase()))
                    .subscribe(new ProfilesFilterSubscriber(MainActivity.this));
        }else{
            this.filteredProfiles.clear();
            this.profilesAdapter.setProfiles(this.profilesLoaded, false);
            this.profilesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public List<Profile> getFilteredList() {
        return this.filteredProfiles;
    }

    @Override
    public void onFilteredCompleted() {

        this.profilesAdapter.setProfiles(this.filteredProfiles, false);
        this.profilesAdapter.notifyDataSetChanged();
    }
}