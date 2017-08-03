package com.robert.autoplayvideo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.robert.autoplayvideo.Utils.getString;

/**
 * Created by robert on 17/08/03.
 */
public class CustomRecyclerView extends RecyclerView {

    private Activity _act;
    private boolean playOnlyFirstVideo = false;
    private boolean downloadVideos = false;
    private boolean checkForMp4 = true;
    private String downloadPath = Environment.getExternalStorageDirectory() + "/Video";
    boolean initilized = false;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setActivity(Activity _act) {
        this._act = _act;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        addCustomOnScrollListener();

    }

    private void addCustomOnScrollListener() {
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                playAvailableVideos(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void playAvailableVideos(int newState) {
        Log.d("k9k9", "playAvailableVideos: ");
//        Log.d("trace", "playAvailableVideos: ");
        List<Thread> threads = new ArrayList<Thread>();
        if (newState == 0) {
            int firstVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int lastVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
//            Log.d("trace", "firstVisiblePosition: " + firstVisiblePosition + " |lastVisiblePosition: " + lastVisiblePosition);
            if (firstVisiblePosition >= 0) {
                Rect rect_parent = new Rect();
                getGlobalVisibleRect(rect_parent);
//                        Log.d("pos", "recyclerview left: " + rect_parent.left + " | right: " + rect_parent.right + " | top: " + rect_parent.top + " | bottom: " + rect_parent.bottom);
                if (playOnlyFirstVideo) {
                    boolean foundFirstVideo = false;
                    for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
                        final RecyclerView.ViewHolder holder = findViewHolderForAdapterPosition(i);
                        try {
                            CustomViewHolder cvh = (CustomViewHolder) holder;
                            if (i >= 0 && cvh != null && cvh.getVideoUrl() != null && !cvh.getVideoUrl().equalsIgnoreCase("null") && (cvh.getVideoUrl().endsWith(".mp4") || !checkForMp4)) {
                                int[] location = new int[2];
                                cvh.getAah_vi().getLocationOnScreen(location);
                                Rect rect_child = new Rect(location[0], location[1], location[0] + cvh.getAah_vi().getWidth(), location[1] + cvh.getAah_vi().getHeight());
//                                        Log.d("k9pos", "x: " + location[0] + " | x right: " + (location[0] + cvh.getAah_vi().getWidth()) + " | y: " + location[1] + " | y bottom: " + (location[1] + cvh.getAah_vi().getHeight()));
//                                Log.d("trace", i + " contains: " + rect_parent.contains(rect_child));
                                if (!foundFirstVideo && rect_parent.contains(rect_child)) {
//                                    Log.d("trace", i + " foundFirstVideo: " + cvh.getVideoUrl());
                                    foundFirstVideo = true;
                                    if (getString(_act, cvh.getVideoUrl()) != null && new File(getString(_act, cvh.getVideoUrl())).exists()) {
                                        ((CustomViewHolder) holder).initVideoView(getString(_act, cvh.getVideoUrl()), _act);
                                    } else {
                                        ((CustomViewHolder) holder).initVideoView(cvh.getVideoUrl(), _act);
                                    }
                                    if (downloadVideos) {
                                        startDownloadInBackground(cvh.getVideoUrl());
                                    }
                                    Thread t = new Thread() {
                                        public void run() {
                                            if (!((CustomViewHolder) holder).isPaused())
                                                ((CustomViewHolder) holder).playVideo();
                                        }
                                    };
                                    t.start();
                                    threads.add(t);
                                } else {
//                                    Log.d("trace", i + " not foundFirstVideo: ");
                                    ((CustomViewHolder) holder).pauseVideo();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                } else {
                    for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
                        final RecyclerView.ViewHolder holder = findViewHolderForAdapterPosition(i);
                        try {
                            CustomViewHolder cvh = (CustomViewHolder) holder;

                            if (i >= 0 && cvh != null && (cvh.getVideoUrl().endsWith(".mp4") || !checkForMp4)) {
                                int[] location = new int[2];
                                cvh.getAah_vi().getLocationOnScreen(location);
                                Rect rect_child = new Rect(location[0], location[1], location[0] + cvh.getAah_vi().getWidth(), location[1] + cvh.getAah_vi().getHeight());
//                                        Log.d("k9pos", "x: " + location[0] + " | x right: " + (location[0] + cvh.getAah_vi().getWidth()) + " | y: " + location[1] + " | y bottom: " + (location[1] + cvh.getAah_vi().getHeight()));
//                                Log.d("trace", i + " contains: " + rect_parent.contains(rect_child));
                                if (rect_parent.contains(rect_child)) {
                                    if (getString(_act, cvh.getVideoUrl()) != null && new File(getString(_act, cvh.getVideoUrl())).exists()) {
                                        ((CustomViewHolder) holder).initVideoView(getString(_act, cvh.getVideoUrl()), _act);
                                    } else {
                                        ((CustomViewHolder) holder).initVideoView(cvh.getVideoUrl(), _act);
                                    }
                                    if (downloadVideos) {
                                        startDownloadInBackground(cvh.getVideoUrl());
                                    }
                                    Thread t = new Thread() {
                                        public void run() {
                                            if (!((CustomViewHolder) holder).isPaused())
                                                ((CustomViewHolder) holder).playVideo();
                                        }
                                    };
                                    t.start();
                                    threads.add(t);
                                } else {
                                    ((CustomViewHolder) holder).pauseVideo();
                                }
                            }
                        } catch (Exception e) {

                        }

                    }
                }
            }
        } else if (threads.size() > 0) {
            for (Thread t : threads) {
                t.interrupt();
                t.stop();
                t.destroy();
            }
            threads.clear();
        }
    }


    public void setPlayOnlyFirstVideo(boolean playOnlyFirstVideo) {
        this.playOnlyFirstVideo = playOnlyFirstVideo;
    }

    @Override
    public boolean getGlobalVisibleRect(Rect r, Point globalOffset) {
        return super.getGlobalVisibleRect(r, globalOffset);
    }

    public void startDownloadInBackground(String url) {
        /* Starting Download Service */
        if ((Utils.getString(_act, url) == null || !(new File(getString(_act, url)).exists())) && url != null && !url.equalsIgnoreCase("null")) {
            Intent intent = new Intent(Intent.ACTION_SYNC, null, _act, DownloadService.class);
        /* Send optional extras to Download IntentService */
            intent.putExtra("url", url);
            intent.putExtra("path", downloadPath);
            intent.putExtra("requestId", 101);
            _act.startService(intent);
        }
    }

    public void setDownloadVideos(boolean downloadVideos) {
        this.downloadVideos = downloadVideos;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void preDownload(List<String> urls) {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(urls);
        urls.clear();
        urls.addAll(hashSet);
        for (int i = 0; i < urls.size(); i++) {
            if ((Utils.getString(_act, urls.get(i)) == null || !(new File(getString(_act, urls.get(i))).exists())) && urls.get(i) != null && !urls.get(i).equalsIgnoreCase("null")) {
                Intent intent = new Intent(Intent.ACTION_SYNC, null, _act, DownloadService.class);
                intent.putExtra("url", urls.get(i));
                intent.putExtra("path", downloadPath);
                intent.putExtra("requestId", 101);
                _act.startService(intent);
            }
        }
    }

    public void setCheckForMp4(boolean checkForMp4) {
        this.checkForMp4 = checkForMp4;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (!initilized) {
            //to start initially
            try {
                playAvailableVideos(0);
                initilized = true;
            } catch (Exception e) {

            }
        }
    }

    public void stopVideos() {
        for (int i = 0; i < getChildCount(); i++) {
            if (findViewHolderForAdapterPosition(i) instanceof CustomViewHolder) {
                final CustomViewHolder cvh = (CustomViewHolder) findViewHolderForAdapterPosition(i);
                if (cvh != null && cvh.getVideoUrl() != null && !cvh.getVideoUrl().equalsIgnoreCase("null") && !cvh.getVideoUrl().isEmpty() && (cvh.getVideoUrl().endsWith(".mp4") || !checkForMp4)) {
                    cvh.pauseVideo();
                }
            }
        }
    }


}
