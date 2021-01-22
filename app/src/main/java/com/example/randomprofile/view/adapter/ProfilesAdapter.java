package com.example.randomprofile.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomprofile.R;
import com.example.randomprofile.entity.Profile;
import com.example.randomprofile.view.holder.ProfileViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Profile> profiles;
    private final Context context;
    private final OnProfileClickHandler onProfileClickHandler;

    public ProfilesAdapter(List<Profile> profiles, Context context, OnProfileClickHandler onProfileClickHandler) {
        this.profiles = profiles;
        this.context = context;
        this.onProfileClickHandler = onProfileClickHandler;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile, parent, false);
        return new ProfileViewHolder(view, onProfileClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Profile profile = profiles.get(position);

        ProfileViewHolder h = (ProfileViewHolder) holder;

        h.bind(profile);

        Picasso.get().load(profile.getThumbnailImage()).into(h.getProfileImage());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public interface OnProfileClickHandler{
        void onProfileClicked(Profile profile);
    }
}
