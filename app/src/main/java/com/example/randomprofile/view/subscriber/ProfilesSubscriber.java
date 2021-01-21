package com.example.randomprofile.view.subscriber;

import com.example.randomprofile.data.response.ProfilesResponse;
import com.example.randomprofile.domain.entity.Profile;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class ProfilesSubscriber extends Subscriber<ProfilesResponse> {

    private List<Profile> profiles;
    private OnServiceProfileListener onServiceProfileListener;

    public ProfilesSubscriber(OnServiceProfileListener onServiceProfileListener) {
        this.profiles = profiles;
        this.onServiceProfileListener = onServiceProfileListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        profiles = new ArrayList<>();
    }

    @Override
    public void onCompleted() {
        this.onServiceProfileListener.onLoadCompleted(this.profiles);
    }

    @Override
    public void onError(Throwable e) {
        this.onServiceProfileListener.onLoadError(e.getMessage());
    }

    @Override
    public void onNext(ProfilesResponse profilesResponse) {
        this.profiles = profilesResponse.getProfiles();
    }

    public interface OnServiceProfileListener{
        void onLoadCompleted(List<Profile> profiles);
        void onLoadError(String error);
    }
}