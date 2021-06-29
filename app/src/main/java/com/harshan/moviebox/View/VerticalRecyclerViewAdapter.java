package com.harshan.moviebox.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.harshan.moviebox.R;

import java.util.List;

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.MyViewHolder> {
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w500";
    private Context mContext;
    private List<String> imagesPath;
    private List<String> titles;
    private List<String> releaseDates;
    private int VIEW_TYPE = 1, LOADING_TYPE = 0;

    public VerticalRecyclerViewAdapter(Context context, List<String> imagesPath, List<String> titles, List<String> releaseDates) {
        mContext = context;
        this.imagesPath = imagesPath;
        this.titles = titles;
        this.releaseDates = releaseDates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vertical_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(mContext).load(BASE_URL + imagesPath.get(position)).into(holder.mImageView);
        holder.txtTitle.setText(titles.get(position));
        holder.txtDate.setText(releaseDates.get(position));
    }


    @Override
    public int getItemCount() {
        return imagesPath.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView txtTitle, txtDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_verticallist_item);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
        }
    }


}
