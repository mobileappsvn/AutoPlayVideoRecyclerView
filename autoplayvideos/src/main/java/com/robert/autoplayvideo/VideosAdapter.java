package com.robert.autoplayvideo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by robert on 17/08/03.
 */
public class VideosAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    public VideosAdapter() {
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
    }

    @Override
    public void onViewDetachedFromWindow(final CustomViewHolder holder) {
        if (holder != null) {
            holder.getAah_vi().getCustomVideoView().clearAll();
            holder.getAah_vi().getCustomVideoView().invalidate();
        }
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(CustomViewHolder holder) {
        if (holder != null) {
            holder.getAah_vi().getCustomVideoView().clearAll();
            holder.getAah_vi().getCustomVideoView().invalidate();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }



}