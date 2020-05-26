package com.example.htecapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.htecapp.R;
import com.example.htecapp.activity.MainActivity;
import com.example.htecapp.model.UserPost;
import com.example.htecapp.util.Alert;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.CustomViewHolder> {

    private List<UserPost> data;
    private Context context;
    OnItemClickListener onItemClickListener;


    public PostsAdapter(Context context, List<UserPost> dataList) {
        this.context = context;
        this.data = dataList;
    }

    public PostsAdapter(Context context, List<UserPost> dataList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = dataList;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtBody;

        CustomViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.title);
            txtBody = itemView.findViewById(R.id.body);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getTitle());
        holder.txtBody.setText(data.get(position).getBody());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItems(List<UserPost> posts) {
        this.data = posts;
        notifyDataSetChanged();
    }

    public int getDataItemId(int position) {
        return data.get(position).getId();
    }


}
