# AutoplayVideo

This library is created with the purpose to implement recyclerview with videos easily.

It is targeted at solving following problems:

1. Flicker when scrolling.
2. Lag or skipping frames when video starts.
3. OutOfMemory errors.

And it has following features:

1. Auto-play videos when in view.
2. Auto-pause videos when not in view or partially in view.
3. Mute/Un-mute videos.
4. Option to play only first visible video.
5. Download videos to local storage in background for faster loading.


# Demo
[![Watch the video](http://soicau.com.vn/robert/autoplayvideo.png)](https://youtu.be/JaE3loNC3mg)
# Download
**Gradle**

**Step 1.** Add the jCenter repository to your project-level build.gradle file

``` groovy
allprojects {
	repositories {
		jcenter()
	}
}
```

**Step 2.** Add the dependency to your app-level build.gradle file:

``` groovy
dependencies {
	 compile 'com.robert.autoplayvideo:0.1.2'
}
```


**Or Maven**
``` groovy
<dependency>
  <groupId>com.robert</groupId>
  <artifactId>autoplayvideo</artifactId>
  <version>0.1.2</version>
  <type>pom</type>
</dependency>
```

# Usage

Add `VideoImage` to your xml file for single list item `single_card.xml`:
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:card_view="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <android.support.v7.widget.CardView
        android:id="@+id/card_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
       
       <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical">

             <FrameLayout
                android:layout_width="300dp"
                android:layout_height="150dp">

                <com.robert.autoplayvideo.VideoImage
                    android:layout_width="match_parent"
                    android:layout_height="match_parent" />

                <ImageView
                    android:id="@+id/img_vol"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="right|bottom"
                    android:layout_margin="8dp"
                    android:src="@drawable/ic_unmute"/>
            </FrameLayout>

            <TextView
                android:id="@+id/tv"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center" />
		
        </LinearLayout>
    </android.support.v7.widget.CardView>
</LinearLayout>
```

Add `CustomRecyclerView` to your Activity layout xml `MainActivity.xml`:
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.robert.autoplayvideo.CustomRecyclerView
        android:id="@+id/rv_home"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
	
</LinearLayout>
```

Set Adapter with following specifics:

1. Adapter should extend `VideosAdapter`.
2. ViewHolder should extend `CustomViewHolder`.
3. Set thumbnail image url and video url in `onBindViewHolder` method.
```
public class MyVideosAdapter extends VideosAdapter {

    private List<MyModel> list;
    Picasso picasso;

    public class MyViewHolder extends CustomViewHolder {
        final TextView tv;
	final ImageView img_vol,img_playback;
        boolean isMuted; //to mute/un-mute video (optional)
	
        public MyViewHolder(View x) {
            super(x);
            tv = ButterKnife.findById(x, R.id.tv);
	    img_vol = ButterKnife.findById(x, R.id.img_vol);
	    img_playback = ButterKnife.findById(x, R.id.img_playback);
        }
    }

    public MyVideosAdapter(List<MyModel> list_urls, Picasso p) {
        this.list = list_urls;
        this.picasso = p;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ((MyViewHolder) holder).tv.setText(list.get(position).getName());

        //todo
        holder.setImageUrl(list.get(position).getImage_url());
        holder.setVideoUrl(list.get(position).getVideo_url());
        //load image/thumbnail into imageview
        if (list.get(position).getImage_url() != null && !list.get(position).getImage_url().isEmpty())
            picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getImageView());
    }
    
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
```

Finally `setActivity` in your Activity before setting the adapter and (Optional) scroll programmatically to initiate videos on initial screen:
```
    recyclerView.setActivity(this); //todo before setAdapter
    recyclerView.setAdapter(mAdapter);
```


### Play only 1st video (Optional)
Setting this parameter will play video only in 1st completely visible RecyclerView ViewHolder.
```
recyclerView.setPlayOnlyFirstVideo(true); // false by default
```


### Download videos to local storage (Optional)
You can start downloading video in background on viewholder loaded. You can change download path.
```
recyclerView.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); //optional
recyclerView.setDownloadVideos(true); // false by default
```
Optionally you can start pre-downloading all the videos by passing list of URLs to function as below:
```
List<String> urls = new ArrayList<>();
 for (MyModel object : modelList) {
     if (object.getVideo_url() != null && object.getVideo_url().endsWith(".mp4"))
         urls.add(object.getVideo_url());
 }
recyclerView.preDownload(urls);
```
Note: Do not forget to add WRITE_EXTERNAL_STORAGE permission in the Manifest file and also ask for runtime permission in devices above Marshmallow.


### Remove check for .mp4 (Optional)
By default it checks for url to end with `.mp4` else it is not considered as video URL. You can override this by setting parameter as below. Please use this with caution and make sure you provide video URL only.
```
recyclerView.setCheckForMp4(false); // true by default
```


### Get callbacks when videos starts and pauses

You can override the below methods of `CustomViewHolder` to get callback when video starts to play or pauses.
```
	@Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.ic_pause);
            if (isMuted) {
                muteVideo();
                img_vol.setImageResource(R.drawable.ic_mute);
            } else {
                unmuteVideo();
                img_vol.setImageResource(R.drawable.ic_unmute);
            }
        }
        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.ic_play);
        }
```


### Play or pause videos manually

You can allow the user to play or pause any video by adding below code in `onBindViewHolder`:
```
	((MyViewHolder) holder).img_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isPlaying()) {
                    holder.pauseVideo();
                    holder.setPaused(true);
                } else {
                    holder.playVideo();
                    holder.setPaused(false);
                }
            }
        });
```


### Mute or Unmute the videos

Video can be muted/unmuted by adding below code in `onBindViewHolder`:
```
	holder.getAah_vi().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MyViewHolder) holder).isMuted) {
                    holder.unmuteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_unmute);
                } else {
                    holder.muteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_mute);
                }
                ((MyViewHolder) holder).isMuted = !((MyViewHolder) holder).isMuted;
            }
        });
```


### Set looping on videos

Set looping on videos by adding below code in `onBindViewHolder`:
```
holder.setLooping(true); //optional - true by default
```

# Changelog
* <a href="/CHANGELOG.txt" target="_blank">Changelog</a>

# Apps by developer
[![TIAMO](https://lh3.googleusercontent.com/Uw7oLRg-Iub-C6T3fg-CRyC7hV3doQZZd6OOsW__Ntpf_8BScMY8d2HT2BPQlapTGaQ=w300-rw)](https://play.google.com/store/apps/details?id=com.vnm.tiamo)  [![Zap Courier](https://lh3.googleusercontent.com/2bEjpmHNjcqo2FEtTNjETsYu8JeIjfHI7oxiZWUSXqg7ENSfUo6rhkUckIAv3DaFQzM=w300-rw)](https://play.google.com/store/apps/details?id=delivery.zap.courier)

# License
Copyright 2017 Robert Hoang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


