package com.example.designpattern.UI.progress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ProgressViewModel {
    private final MutableLiveData<String> mText;

    public ProgressViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is progress fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
