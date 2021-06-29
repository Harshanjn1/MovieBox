package com.harshan.moviebox.View;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.harshan.moviebox.Services.EventInjectionManager;

public class PageListener extends RecyclerView.OnScrollListener {
    boolean isScrolling = false;
    private EventInjectionManager mEventInjectionManager = EventInjectionManager.getInstance();
    private int totalItemCount, visibleItemCount, scrolledOutItemCount;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        isScrolling = true;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        scrolledOutItemCount = layoutManager.findFirstVisibleItemPosition();
        if (isScrolling && (totalItemCount == visibleItemCount + scrolledOutItemCount)) {
            isScrolling = false;
            mEventInjectionManager.inject(1, recyclerView);
        }

    }


}
