package com.example.randomprofile.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.randomprofile.R;
import com.example.randomprofile.config.Constants;
import com.example.randomprofile.databinding.ActivityProfileDetailBinding;
import com.example.randomprofile.domain.entity.Profile;
import com.squareup.picasso.Picasso;

public class ProfileDetailActivity extends AppCompatActivity {

    private ActivityProfileDetailBinding activityProfileDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityProfileDetailBinding = ActivityProfileDetailBinding.inflate(getLayoutInflater());

        View view = activityProfileDetailBinding.getRoot();

        setContentView(view);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.detail_view_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Intent i = getIntent();

        Profile profile = (Profile) i.getSerializableExtra(Constants.PROFILE);

        showData(profile);
    }

    private void showData(Profile profile){

        Picasso.get().load(profile.getPicture().getLarge()).into(activityProfileDetailBinding.profileImage);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}