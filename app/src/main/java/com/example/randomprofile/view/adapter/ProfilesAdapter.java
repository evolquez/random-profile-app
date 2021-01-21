package com.example.randomprofile.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomprofile.R;
import com.example.randomprofile.domain.entity.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Profile> profiles;
    private Context context;
    private OnProfileClickHandler onProfileClickHandler;

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
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Profile profile = profiles.get(position);

        Holder h = (Holder) holder;

        h.bind(profile);

        Picasso.get().load(profile.getPicture().getThumbnail()).into(h.profileImage);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        private ImageView profileImage;

        private Holder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
        }

        private void bind(Profile profile){
            itemView.setOnClickListener(v -> onProfileClickHandler.onProfileClicked(profile));
        }
    }

    public interface OnProfileClickHandler{
        void onProfileClicked(Profile profile);
    }
}
