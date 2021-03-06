package com.example.randomprofile.view.subscriber;

import com.example.randomprofile.data.ProfileFactory;
import com.example.randomprofile.data.response.ProfilesResponse;
import com.example.randomprofile.entity.Profile;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class ProfilesSubscriber extends Subscriber<ProfilesResponse> {

    private List<Profile> profiles;
    private ProfilesResponse profileResponses;
    private final OnServiceProfileListener onServiceProfileListener;

    public ProfilesSubscriber(OnServiceProfileListener onServiceProfileListener) {
        this.onServiceProfileListener = onServiceProfileListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        profiles = new ArrayList<>();
    }

    @Override
    public void onCompleted() {

        this.profiles = ProfileFactory.getInstance().prepareFromResponse(this.profileResponses);

        this.onServiceProfileListener.onLoadCompleted(this.profiles);
    }

    @Override
    public void onError(Throwable e) {
        this.onServiceProfileListener.onLoadError(e.getMessage());
    }

    @Override
    public void onNext(ProfilesResponse profilesResponse) {
        this.profileResponses = profilesResponse;
    }

    public interface OnServiceProfileListener{
        void onLoadCompleted(List<Profile> profiles);
        void onLoadError(String error);
    }
}