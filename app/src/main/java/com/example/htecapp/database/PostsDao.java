package com.example.htecapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.htecapp.model.UserPost;

import java.util.List;

@Dao
public interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserPost> posts);

    @Query("SELECT * FROM post")
    LiveData<List<UserPost>> getAllData();

    @Query("DELETE FROM post WHERE id = :itemId")
    void deleteById(long itemId);

    @Query("DELETE FROM post")
    void deleteAll();
}
