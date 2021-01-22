package com.example.randomprofile.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.randomprofile.view.listener.ScrollListener;
import com.example.randomprofile.view.subscriber.ProfilesSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ProfilesSubscriber.OnServiceProfileListener,
        ProfilesAdapter.OnProfileClickHandler, ScrollListener.OnScrollEndListener {

    private ActivityMainBinding activityMainBinding;

    private ProfilesAdapter profilesAdapter;
    private ProfilesAdapter favoritesAdapter;
    private ProgressBar loadingProfileIndicator;
    private ProfileDao profileDao;
    private ProfilesService service;
    private Boolean firstLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = activityMainBinding.getRoot();

        setContentView(view);

        profileDao = AppDatabase.getInstance(this).profileDao();

        loadingProfileIndicator = activityMainBinding.loadingProfileIndicator;

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.home_view_title));
        }

        // Initialize recyclerview component
        this.initialize();

        service = RetrofitBuilder.getInstance().getRetrofit().create(ProfilesService.class);
        loadingProfileIndicator.setVisibility(View.VISIBLE);
        this.firstLoad = true;
        this.loadProfiles();
    }

    private void initialize() {
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

        List<Profile> allFavorites = this.profileDao.getAll();

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

        this.profilesAdapter.setProfiles(profiles, !firstLoad);
        this.profilesAdapter.notifyDataSetChanged();

        if(firstLoad){
            loadingProfileIndicator.setVisibility(View.GONE);
            firstLoad = false;
        }
    }

    @Override
    public void onLoadError(String error) {
        if(this.firstLoad)
            loadingProfileIndicator.setVisibility(View.GONE);

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

        loadProfiles();
    }

    @Override
    public int getProfilesCount() {
        return this.profilesAdapter.getItemCount();
    }
}