package com.akshaytech.truckwale.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SHomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Place orders");
    }

    public LiveData<String> getText() {
        return mText;
    }
}