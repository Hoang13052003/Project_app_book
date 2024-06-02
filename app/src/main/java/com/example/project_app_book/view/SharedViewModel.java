package com.example.project_app_book.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<HashMap<String, String>> authorMap = new MutableLiveData<>();

    public void setAuthorMap(HashMap<String, String> map) {
        authorMap.setValue(map);
    }

    public LiveData<HashMap<String, String>> getAuthorMap() {
        return authorMap;
    }
}
