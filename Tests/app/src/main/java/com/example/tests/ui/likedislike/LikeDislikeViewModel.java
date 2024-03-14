package com.example.tests.ui.likedislike;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LikeDislikeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LikeDislikeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is like_dislike fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}