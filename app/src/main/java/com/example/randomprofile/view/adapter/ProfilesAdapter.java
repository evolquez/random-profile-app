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

    public ProfilesAdapter(List<Profile> profiles, Context context) {
        this.profiles = profiles;
        this.context = context;
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
    }
}
