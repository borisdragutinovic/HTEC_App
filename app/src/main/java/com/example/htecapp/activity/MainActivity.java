package com.example.htecapp.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.htecapp.R;
import com.example.htecapp.adapter.PostsAdapter;
import com.example.htecapp.model.User;
import com.example.htecapp.model.UserPost;
import com.example.htecapp.network.RestApi;
import com.example.htecapp.network.RetrofitClientInstance;
import com.example.htecapp.util.Alert;
import com.example.htecapp.service.DeleteTimer;
import com.example.htecapp.view.DataViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private PostsAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    FloatingActionButton floatingActionButton;
    RestApi service;
    List<UserPost> posts = new ArrayList<UserPost>();
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDoalog = new ProgressDialog(MainActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PostsAdapter(this, posts, new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getUserById(adapter.getDataItemId(position));
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }



            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int itemId = adapter.getDataItemId(viewHolder.getAdapterPosition());
                mDataViewModel.deleteItemById(itemId);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);




        floatingActionButton = findViewById(R.id.refresh);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromApi();
            }
        });

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        service = RetrofitClientInstance.getRetrofitInstance().create(RestApi.class);
        getDataFromDatabase();
    }

    private void showProgresDialog(){
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
    }

    private void hideProgresDialog(){
        progressDoalog.dismiss();
    }

    private void getDataFromApi(){
        showProgresDialog();
        Call<List<UserPost>> call = service.getPosts();
        call.enqueue(new Callback<List<UserPost>>() {
            @Override
            public void onResponse(Call<List<UserPost>> call, Response<List<UserPost>> response) {
                hideProgresDialog();
                stopService(new Intent(MainActivity.this, DeleteTimer.class));
                saveData(response.body());
            }

            @Override
            public void onFailure(Call<List<UserPost>> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserById(int id){
        showProgresDialog();
        Call<User> call = service.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                hideProgresDialog();
                Alert.showAlertDialog(MainActivity.this, response.body().getName(), response.body().getEmail());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideProgresDialog();
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(List<UserPost> body) {
        mDataViewModel.insertAllData(body);
        getDataFromDatabase();

        int timeInSec = 5*60;
        Intent intent = new Intent(this, DeleteTimer.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                this.getApplicationContext(), 234, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (timeInSec * 1000), pendingIntent);
    }

    private void getDataFromDatabase(){
        mDataViewModel.getAllData().observe(this, new Observer<List<UserPost>>() {
            @Override
            public void onChanged(List<UserPost> posts) {
                if (posts != null) {
                    showPosts(posts);
                }
            }
        });
    }

    public void showPosts(List<UserPost> userPosts) {
        if (userPosts == null) {
            userPosts = new ArrayList<>();
        }
        posts.clear();
        posts.addAll(userPosts);

        if (adapter != null) {
            adapter.setItems(posts);
        }
    }
}
