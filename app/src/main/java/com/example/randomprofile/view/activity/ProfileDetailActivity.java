package com.example.randomprofile.view.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.randomprofile.R;
import com.example.randomprofile.config.Constants;
import com.example.randomprofile.databinding.ActivityProfileDetailBinding;
import com.example.randomprofile.domain.entity.Location;
import com.example.randomprofile.domain.entity.Profile;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class ProfileDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProfileDetailBinding activityProfileDetailBinding;
    private boolean isFav;
    private Drawable defaultFavIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityProfileDetailBinding = ActivityProfileDetailBinding.inflate(getLayoutInflater());

        View view = activityProfileDetailBinding.getRoot();

        defaultFavIcon = ContextCompat.getDrawable(this, R.drawable.ic_fav);

        setContentView(view);

        activityProfileDetailBinding.favButton.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        save();
    }

    private void save(){

        if(!this.isFav){
            // Save
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            Drawable markedFavIcon = ContextCompat.getDrawable(this, R.drawable.ic_fav).mutate();
            markedFavIcon.setColorFilter(ContextCompat.getColor(this, R.color.green_1), PorterDuff.Mode.SRC_ATOP);
            activityProfileDetailBinding.favButton.setImageDrawable(markedFavIcon);
            this.isFav = true;
        }else{
            Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            this.isFav = false;
            // Remove
            activityProfileDetailBinding.favButton.getDrawable().setColorFilter(null);
        }
    }

    private String getAddress(Location location){
        final String addressFormat = "%s %d, %s, %s, %s";

        return String.format(Locale.US, addressFormat, location.getStreet().getName(),
                location.getStreet().getNumber(), location.getCity(), location.getState(), location.getCountry());
    }
}