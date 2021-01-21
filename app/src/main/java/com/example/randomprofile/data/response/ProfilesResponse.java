package com.example.randomprofile.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfilesResponse {

    @SerializedName("results")
    private List<ProfileResponse> profileResponses;

    public ProfilesResponse(List<ProfileResponse> profileResponses) {
        this.profileResponses = profileResponses;
    }

    public List<ProfileResponse> getProfileResponses() {
        return profileResponses;
    }

    public void setProfileResponses(List<ProfileResponse> profileResponses) {
        this.profileResponses = profileResponses;
    }
}
