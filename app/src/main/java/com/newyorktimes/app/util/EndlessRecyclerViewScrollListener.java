package com.newyorktimes.app.util;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private int visibleThreshold = 5;
    private int currentPage = 1;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private RecyclerView.LayoutManager layoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.visibleThreshold *= layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.visibleThreshold *= layoutManager.getSpanCount();
    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int var2 = 0;

        for(int var3 = 0; var3 < lastVisibleItemPositions.length; ++var3) {
            if(var3 == 0) {
                var2 = lastVisibleItemPositions[var3];
            } else if(lastVisibleItemPositions[var3] > var2) {
                var2 = lastVisibleItemPositions[var3];
            }
        }

        return var2;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if(newState == 0) {
            this.setRefreshing(false);
        } else {
            this.setRefreshing(true);
        }

    }

    public void onScrolled(RecyclerView view, int dx, int dy) {
        this.onScrolled(dx, dy);
        int var4 = 0;
        int var5 = this.layoutManager.getItemCount();
        if(this.layoutManager instanceof StaggeredGridLayoutManager) {
            int[] var6 = ((StaggeredGridLayoutManager)this.layoutManager).findLastVisibleItemPositions(null);
            var4 = this.getLastVisibleItem(var6);
        } else if(this.layoutManager instanceof GridLayoutManager) {
            var4 = ((GridLayoutManager)this.layoutManager).findLastVisibleItemPosition();
        } else if(this.layoutManager instanceof LinearLayoutManager) {
            var4 = ((LinearLayoutManager)this.layoutManager).findLastVisibleItemPosition();
        }

        if(var5 < this.previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = var5;
            if(var5 == 0) {
                this.loading = true;
            }
        }

        if(this.loading && var5 > this.previousTotalItemCount) {
            this.loading = false;
            this.previousTotalItemCount = var5;
        }

        if(!this.loading && var4 + this.visibleThreshold > var5) {
            ++this.currentPage;
            this.onLoadMore(this.currentPage, var5, view);
            this.loading = true;
        }

    }

    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    public abstract void onLoadMore(int var1, int var2, RecyclerView var3);

    public abstract void setRefreshing(boolean var1);

    public abstract void onScrolled(int var1, int var2);
}