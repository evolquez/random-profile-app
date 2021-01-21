package com.example.randomprofile.view.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.randomprofile.R;
import com.example.randomprofile.config.Constants;
import com.example.randomprofile.dao.ProfileDao;
import com.example.randomprofile.data.database.AppDatabase;
import com.example.randomprofile.databinding.ActivityProfileDetailBinding;
import com.example.randomprofile.entity.Profile;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class ProfileDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProfileDetailBinding activityProfileDetailBinding;
    private boolean isFav;
    private ProfileDao profileDao;
    private ProgressBar loadingIndicator;
    private ImageView favButton;
    private Profile profile;
    private Profile profileSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activityProfileDetailBinding = ActivityProfileDetailBinding.inflate(getLayoutInflater());

        View view = activityProfileDetailBinding.getRoot();

        setContentView(view);

        loadingIndicator = activityProfileDetailBinding.loadingBar;
        favButton = activityProfileDetailBinding.favButton;

        favButton.setOnClickListener(this);

        activityProfileDetailBinding.addContactButton.setOnClickListener(this);

        profileDao = AppDatabase.getInstance(this).profileDao();

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.detail_view_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Intent i = getIntent();

        profile = (Profile) i.getSerializableExtra(Constants.PROFILE);

        if(profile != null)
            initializeData(profile);
    }

    private void initializeData(Profile profile){

        Picasso.get().load(profile.getLargeImage()).into(activityProfileDetailBinding.profileImage);

        activityProfileDetailBinding.dniField.setText(profile.getId());
        activityProfileDetailBinding.nameField.setText(String.format(Locale.US, "%s %s",
                profile.getFirstName(), profile.getLastName()));

        activityProfileDetailBinding.emailField.setText(profile.getEmail());

        activityProfileDetailBinding.phoneField.setText(profile.getPhone());

        activityProfileDetailBinding.ageField.setText(String.valueOf(profile.getAge()));

        activityProfileDetailBinding.addressField.setText(profile.getAddress());
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
    protected void onResume() {
        super.onResume();
        verify();
    }

    /*Verify if the displayed profile is saved on local database*/
    private void verify() {
        toggleLoading();

        this.profileSaved = profileDao.findById(this.profile.getId());

        if(this.profileSaved != null){
            isFav = true;
            toggleLoading();
            setFavIcon(true);
        }else{
            toggleLoading();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.favButton)
            markAsFavorite();
        else
            addContact();
    }

    private void addContact() {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);

        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        // Set default data
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, profile.getPhone());
        intent.putExtra(ContactsContract.Intents.Insert.NAME, String.format(Locale.US, "%s %s", profile.getFirstName(), profile.getLastName()));
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, profile.getEmail());

        startActivity(intent);
    }

    private void toggleLoading(){

        int favVisibility       = View.VISIBLE;
        int loadingVisibility   = View.GONE;

        if(favButton.getVisibility() == favVisibility){
            favVisibility = View.INVISIBLE;
            loadingVisibility = View.VISIBLE;
        }

        favButton.setVisibility(favVisibility);
        loadingIndicator.setVisibility(loadingVisibility);
    }

    private void markAsFavorite(){
        toggleLoading();

        if(!isFav){
            // Save

            this.profileDao.insert(this.profile);

            this.profileSaved = this.profile;

            isFav = true;

            setFavIcon(true);

            toggleLoading();
            Toast.makeText(ProfileDetailActivity.this, "Marked as favorite!", Toast.LENGTH_SHORT).show();
        }else{
            this.profileDao.delete(this.profileSaved);
            isFav = false;
            setFavIcon(false);
            toggleLoading();
            // Remove
            Toast.makeText(this, "Remove from favorite!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFavIcon(boolean marked){
        if(marked){
            Drawable markedFavIcon = ContextCompat.getDrawable(this, R.drawable.ic_fav).mutate();

            markedFavIcon.setColorFilter(ContextCompat.getColor(this, R.color.green_1), PorterDuff.Mode.SRC_ATOP);
            activityProfileDetailBinding.favButton.setImageDrawable(markedFavIcon);
        }else{
            activityProfileDetailBinding.favButton.getDrawable().setColorFilter(null);
        }
    }
}