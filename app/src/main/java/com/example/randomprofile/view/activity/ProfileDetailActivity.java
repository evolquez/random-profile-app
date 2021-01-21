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
import com.example.randomprofile.domain.entity.Location;
import com.example.randomprofile.domain.entity.Profile;
import com.squareup.picasso.Picasso;

import java.util.Locale;

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

        initializeData(profile);
    }

    private void initializeData(Profile profile){

        Picasso.get().load(profile.getPicture().getLarge()).into(activityProfileDetailBinding.profileImage);

        activityProfileDetailBinding.nameField.setText(String.format(Locale.US, "%s %s",
                profile.getName().getFirst(), profile.getName().getLast()));

        activityProfileDetailBinding.emailField.setText(profile.getEmail());

        activityProfileDetailBinding.phoneField.setText(profile.getPhone());

        activityProfileDetailBinding.ageField.setText(String.valueOf(profile.getDob().getAge()));

        activityProfileDetailBinding.addressField.setText(getAddress(profile.getLocation()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getAddress(Location location){
        final String addressFormat = "%s %d, %s, %s, %s";

        return String.format(Locale.US, addressFormat, location.getStreet().getName(),
                location.getStreet().getNumber(), location.getCity(), location.getState(), location.getCountry());
    }
}