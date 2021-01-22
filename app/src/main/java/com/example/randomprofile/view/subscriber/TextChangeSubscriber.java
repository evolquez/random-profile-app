package com.example.randomprofile.view.subscriber;

import rx.Observer;

public class TextChangeSubscriber implements Observer<String> {

    private final OnTextChangeListener listener;

    public TextChangeSubscriber(OnTextChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(String s) {
        this.listener.emitTextFromSearchBox(s);
    }

    public interface OnTextChangeListener {
        void emitTextFromSearchBox(String s);
    }
}
