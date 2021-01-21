package com.example.randomprofile.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomprofile.R;
import com.example.randomprofile.config.Constants;
import com.example.randomprofile.data.service.ProfilesService;
import com.example.randomprofile.data.service.RetrofitBuilder;
import com.example.randomprofile.databinding.ActivityMainBinding;
import com.example.randomprofile.domain.entity.Profile;
import com.example.randomprofile.view.adapter.ProfilesAdapter;
import com.example.randomprofile.view.subscriber.ProfilesSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ProfilesSubscriber.OnServiceProfileListener, ProfilesAdapter.OnProfileClickHandler {

    private ActivityMainBinding activityMainBinding;

    private ProfilesAdapter profilesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = activityMainBinding.getRoot();

        setContentView(view);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.home_view_title));
        }


        // Initialize recyclerview component
        this.initialize();

        this.loadProfiles();
    }

    private void initialize() {
        RecyclerView recyclerView = activityMainBinding.gridProfiles;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        this.profilesAdapter = new ProfilesAdapter(new ArrayList<>(), this, this);

        recyclerView.setAdapter(profilesAdapter);

    }

    private void loadProfiles(){

        ProfilesService service = RetrofitBuilder.getInstance().getRetrofit().create(ProfilesService.class);

        ProfilesSubscriber subscriber = new ProfilesSubscriber(this);

        service.getProfiles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void onLoadCompleted(List<Profile> profiles) {
        this.profilesAdapter.setProfiles(profiles);
        this.profilesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProfileClicked(Profile profile) {
        Intent i = new Intent(this, ProfileDetailActivity.class);
        i.putExtra(Constants.PROFILE, profile);

        startActivity(i);
    }
}