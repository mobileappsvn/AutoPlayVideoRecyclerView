package com.robert.autoplayvideo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Created by robert on 17/08/03.
 */
public class VideoImage extends FrameLayout {
    private CustomVideoView cvv;
    private ImageView iv;

    public VideoImage(Context context) {
        super(context);
        init();
    }

    public VideoImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public CustomVideoView getCustomVideoView() {
        return cvv;
    }

    public ImageView getImageView() {
        return iv;
    }


    private void init() {
        this.setTag("aah_vi");
        cvv = new CustomVideoView(getContext());
        iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(cvv);
        this.addView(iv);
    }
}
