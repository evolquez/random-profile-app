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
import com.example.randomprofile.view.subscriber.ProfilesSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ProfilesSubscriber.OnServiceProfileListener, ProfilesAdapter.OnProfileClickHandler {

    private ActivityMainBinding activityMainBinding;

    private ProfilesAdapter profilesAdapter;
    private ProfilesAdapter favoritesAdapter;
    private ProgressBar loadingProfileIndicator;
    private ProfileDao profileDao;

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

        this.loadProfiles();
    }

    private void initialize() {
        RecyclerView rvProfiles = activityMainBinding.gridProfiles;
        RecyclerView rvFavorites = activityMainBinding.gridFavorites;


        rvProfiles.setHasFixedSize(true);
        rvProfiles.setLayoutManager(new GridLayoutManager(this, 5));

        this.profilesAdapter = new ProfilesAdapter(new ArrayList<>(), this, this);

        rvProfiles.setAdapter(profilesAdapter);

        rvFavorites.setHasFixedSize(true);
        rvFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        this.favoritesAdapter = new ProfilesAdapter(new ArrayList<>(), this, this);

        rvFavorites.setAdapter(favoritesAdapter);
    }

    private void loadProfiles(){
        loadingProfileIndicator.setVisibility(View.VISIBLE);
        ProfilesService service = RetrofitBuilder.getInstance().getRetrofit().create(ProfilesService.class);

        ProfilesSubscriber subscriber = new ProfilesSubscriber(this);

        service.getProfiles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
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
            this.favoritesAdapter.setProfiles(allFavorites);
            this.favoritesAdapter.notifyDataSetChanged();
        }else{
            activityMainBinding.favoritesContainer.setVisibility(View.GONE);
            this.favoritesAdapter.setProfiles(new ArrayList<>());
            this.favoritesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadCompleted(List<Profile> profiles) {
        loadingProfileIndicator.setVisibility(View.GONE);
        this.profilesAdapter.setProfiles(profiles);
        this.profilesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadError(String error) {
        loadingProfileIndicator.setVisibility(View.GONE);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProfileClicked(Profile profile) {
        Intent i = new Intent(this, ProfileDetailActivity.class);
        i.putExtra(Constants.PROFILE, profile);

        startActivity(i);
    }
}