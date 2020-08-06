package com.akshaytech.truckwale.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SSlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SSlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("History");
    }

    public LiveData<String> getText() {
        return mText;
    }
}