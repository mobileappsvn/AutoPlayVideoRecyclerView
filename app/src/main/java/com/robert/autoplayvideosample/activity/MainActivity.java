package com.robert.autoplayvideosample.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.robert.autoplayvideosample.adapter.MyVideosAdapter;
import com.robert.autoplayvideo.CustomRecyclerView;
import com.robert.autoplayvideosample.model.MyModel;
import com.robert.autoplayvideosample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by robert on 17/08/03.
 */
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.rv_home)
    CustomRecyclerView recyclerView;

    private final List<MyModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Picasso p = Picasso.with(this);

        modelList.add(new MyModel("http://video.pp.cn/fs08/2017/02/23/10/aa74cfad-fca1-4aa4-9969-4a22d0d2b45b.mp4", "http://android-imgs.25pp.com/fs08/2017/02/23/2/48da103a3a21d8a1dea01570bc35de8e.jpg", "Video1"));
        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "Video1"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1491561340/hello_cuwgcb.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1491561340/hello_cuwgcb.jpg", "Video2"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/3_lfndfq.jpg", "Image3"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/3_yqeudi.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/3_yqeudi.jpg", "Video4"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/1_pyn1fm.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/1_pyn1fm.jpg", "Video5"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1491561340/hello_cuwgcb.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1491561340/hello_cuwgcb.jpg", "Video6"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/2_qwpgis.jpg", "Image7"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/1_ybonak.jpg", "Image8"));
        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "Video9"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/3_lfndfq.jpg", "Name10"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795676/4_nvnzry.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795676/4_nvnzry.jpg", "Video11"));
        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "Video12"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/3_lfndfq.jpg", "Image13"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/2_qwpgis.jpg", "Image14"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/3_yqeudi.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/3_yqeudi.jpg", "Video16"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/1_ybonak.jpg", "Image15"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/1_pyn1fm.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/1_pyn1fm.jpg", "Video17"));
        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "Video18"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/2_qwpgis.jpg", "Image19"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/3_lfndfq.jpg", "Image20"));
        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/1_ybonak.jpg", "Image21"));

        //you can pass local file uri, but make sure it exists
//        modelList.add(new MyModel("/storage/emulated/0/VideoPlay/myvideo.mp4","http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg","video18"));

        MyVideosAdapter mAdapter = new MyVideosAdapter(modelList, p);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //todo before setAdapter
        recyclerView.setActivity(this);

        //optional - to play only first visible video
        recyclerView.setPlayOnlyFirstVideo(true); // false by default

        //optional - by default we check if url ends with ".mp4". If your urls do not end with mp4, you can set this param to false and implement your own check to see if video points to url
        recyclerView.setCheckForMp4(false); //true by default

        //optional - download videos to local storage (requires "android.permission.WRITE_EXTERNAL_STORAGE" in manifest or ask in runtime)
        recyclerView.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); // (Environment.getExternalStorageDirectory() + "/Video") by default

        recyclerView.setDownloadVideos(true); // false by default

        //extra - start downloading all videos in background before loading RecyclerView
        List<String> urls = new ArrayList<>();
        for (MyModel object : modelList) {
            if (object.getVideo_url() != null && object.getVideo_url().contains("http"))
                urls.add(object.getVideo_url());
        }
        recyclerView.preDownload(urls);

        recyclerView.setAdapter(mAdapter);
        //call this function when u want to start autoplay on loading async lists (eg firebase)
//        recyclerView.playAvailableVideos(0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //add this code to pause videos (when app is minimised or paused)
        recyclerView.stopVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.playAvailableVideos(0);
    }
}
