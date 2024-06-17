package com.example.designpattern.UI.learning;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LearningViewModel {
    private final MutableLiveData<String> mText;

    public LearningViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is learning fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
