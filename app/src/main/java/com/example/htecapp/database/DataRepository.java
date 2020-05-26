package com.example.htecapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.htecapp.model.UserPost;

import java.util.List;

public class DataRepository {

    private PostsDao mDataDao;
    private LiveData<List<UserPost>> mAllData;

    public DataRepository(Application application) {
        PostDatabase dataRoombase = PostDatabase.getInstance(application);
        this.mDataDao = dataRoombase.postsDao();
        this.mAllData = mDataDao.getAllData();
    }

    public LiveData<List<UserPost>> getAllData() {
        return mAllData;
    }

    public void insert(List<UserPost> data) {
        new insertAsyncTask(mDataDao).execute(data);
    }

    private static class insertAsyncTask extends AsyncTask<List<UserPost>, Void, Void> {
        private PostsDao mAsyncTaskDao;
        insertAsyncTask(PostsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<UserPost>... lists) {
            mAsyncTaskDao.insertAll(lists[0]);
            return null;
        }
    }

    public void deleteById(int idItem) {
        new deleteByIdAsyncTask(mDataDao).execute(idItem);
    }

    private static class deleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private PostsDao mAsyncTaskDao;
        deleteByIdAsyncTask(PostsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteById(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        new deleteAll(mDataDao).execute();
    }

    private static class deleteAll extends AsyncTask<Void, Void, Void> {
        private PostsDao mAsyncTaskDao;
        deleteAll(PostsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
