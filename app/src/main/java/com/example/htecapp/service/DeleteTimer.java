package com.example.htecapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.htecapp.database.DataRepository;

public class DeleteTimer extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDelete();
        return START_STICKY;
    }

    public void startDelete(){
        new DataRepository(getApplication()).deleteAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
