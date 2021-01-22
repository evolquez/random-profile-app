package com.example.randomprofile.view.subscriber;

import com.example.randomprofile.entity.Profile;



import java.util.List;

import rx.Subscriber;

public class ProfilesFilterSubscriber extends Subscriber<Profile> {

    private final OnProfileFilterListener listener;

    public ProfilesFilterSubscriber(OnProfileFilterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        listener.getFilteredList().clear();
    }

    @Override
    public void onCompleted() {
        this.listener.onFilteredCompleted();
    }

    @Override
    public void onNext(Profile profile) {
        listener.getFilteredList().add(profile);
    }

    @Override
    public void onError(Throwable t) {

    }

    public interface OnProfileFilterListener {
        List<Profile> getFilteredList();
        void onFilteredCompleted();
    }

}
