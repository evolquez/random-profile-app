package com.example.randomprofile.data.service;

import com.example.randomprofile.data.response.ProfilesResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface ProfilesService {

    @GET("?results=50&nat=ES")
    Observable<ProfilesResponse> getProfiles();
}

