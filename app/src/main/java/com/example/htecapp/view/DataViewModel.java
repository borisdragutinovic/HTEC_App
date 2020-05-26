package com.example.htecapp.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.htecapp.database.DataRepository;
import com.example.htecapp.model.UserPost;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private DataRepository mDataRepository;
    private LiveData<List<UserPost>> mListLiveData;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = new DataRepository((application));
        mListLiveData = mDataRepository.getAllData();
    }

    public LiveData<List<UserPost>> getAllData() {
        return mListLiveData;
    }

    public void insertAllData(List<UserPost> data) {
        mDataRepository.insert(data);
    }

    public void deleteItemById(int idItem) {
        mDataRepository.deleteById(idItem);
    }

}
