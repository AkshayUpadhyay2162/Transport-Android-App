package com.akshaytech.truckwale.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SGalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SGalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Transporters list");
    }

    public LiveData<String> getText() {
        return mText;
    }
}