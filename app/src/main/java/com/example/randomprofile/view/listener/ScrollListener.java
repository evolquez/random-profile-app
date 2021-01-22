package com.example.randomprofile.view.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollListener extends RecyclerView.OnScrollListener {

    private OnScrollEndListener listener;

    public ScrollListener(OnScrollEndListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(dy > 0){

            GridLayoutManager layoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
            if(layoutManager != null){

                int lastItem  = layoutManager.findLastCompletelyVisibleItemPosition();

                if(lastItem == (this.listener.getProfilesCount() - 1)){
                    this.listener.onScrollEnd();
                }
            }

        }
    }

    public interface OnScrollEndListener{
        void onScrollEnd();
        int getProfilesCount();
    }
}
