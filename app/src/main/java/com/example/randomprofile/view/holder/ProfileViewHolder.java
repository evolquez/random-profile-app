package com.example.randomprofile.view.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.randomprofile.R;
import com.example.randomprofile.entity.Profile;
import com.example.randomprofile.view.adapter.ProfilesAdapter;

public class ProfileViewHolder extends RecyclerView.ViewHolder{

    private final ImageView profileImage;
    private final ProfilesAdapter.OnProfileClickHandler onProfileClickHandler;

    public ProfileViewHolder(View itemView, ProfilesAdapter.OnProfileClickHandler onProfileClickHandler) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.profileImage);
        this.onProfileClickHandler = onProfileClickHandler;
    }

    public void bind(Profile profile){
        itemView.setOnClickListener(v -> onProfileClickHandler.onProfileClicked(profile));
    }

    public ImageView getProfileImage() {
        return profileImage;
    }
}