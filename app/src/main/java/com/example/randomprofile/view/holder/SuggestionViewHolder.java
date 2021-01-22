package com.example.randomprofile.view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.randomprofile.R;
import com.example.randomprofile.entity.Profile;
import com.example.randomprofile.view.adapter.ProfilesAdapter;

public class SuggestionViewHolder {

    private final ProfilesAdapter.OnProfileClickHandler onProfileClickHandler;

    private final ImageView profileImage;
    private final TextView profileName;
    private final TextView profileDni;

    private View view;

    public SuggestionViewHolder(View view, ProfilesAdapter.OnProfileClickHandler onProfileClickHandler) {

        this.view = view;
        profileImage = view.findViewById(R.id.image);
        profileName = view.findViewById(R.id.name);
        profileDni = view.findViewById(R.id.dni);

        this.onProfileClickHandler = onProfileClickHandler;
    }

    public void bind(Profile profile){
        view.setOnClickListener(v -> onProfileClickHandler.onProfileClicked(profile));
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public TextView getProfileName() {
        return profileName;
    }

    public TextView getProfileDni() {
        return profileDni;
    }
}
