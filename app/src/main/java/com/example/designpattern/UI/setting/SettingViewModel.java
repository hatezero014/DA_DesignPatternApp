package com.example.designpattern.UI.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SettingViewModel {
    private final MutableLiveData<String> mText;

    public SettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is setting fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
