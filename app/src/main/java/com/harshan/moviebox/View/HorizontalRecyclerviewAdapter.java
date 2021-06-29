package com.harshan.moviebox.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harshan.moviebox.R;


import java.util.List;

public class HorizontalRecyclerviewAdapter extends RecyclerView.Adapter<HorizontalRecyclerviewAdapter.MyViewHolder> {
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w500";
    private Context mContext;
    private List<String> imagesPath;

    public HorizontalRecyclerviewAdapter(Context context, List<String> imagesPath) {
        mContext = context;
        this.imagesPath = imagesPath;
    }

    @NonNull
    @Override
    public HorizontalRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(mContext).load(BASE_URL + imagesPath.get(position)).placeholder(R.drawable.icon_loading).into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return imagesPath.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_horizontallist_item);

        }
    }
}
