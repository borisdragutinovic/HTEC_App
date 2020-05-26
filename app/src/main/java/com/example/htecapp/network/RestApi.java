package com.example.htecapp.network;

import com.example.htecapp.model.User;
import com.example.htecapp.model.UserPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {

    @GET("/posts")
    Call<List<UserPost>> getPosts();

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") int userId);
}
