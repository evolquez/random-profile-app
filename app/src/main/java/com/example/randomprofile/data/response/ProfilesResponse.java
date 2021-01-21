package com.example.randomprofile.data.response;

import com.example.randomprofile.domain.entity.Profile;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfilesResponse {

    @SerializedName("results")
    private List<Profile> profiles;

    public ProfilesResponse(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
