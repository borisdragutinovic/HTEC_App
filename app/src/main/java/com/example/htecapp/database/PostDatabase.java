package com.example.htecapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.htecapp.model.UserPost;

@Database(entities = UserPost.class, version = 1)
public abstract class PostDatabase extends RoomDatabase {
    private static final String DB_NAME = "posts_db";
    private static PostDatabase instance;

    public static synchronized PostDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PostDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract PostsDao postsDao();
}