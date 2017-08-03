package com.robert.autoplayvideo;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by robert on 17/08/03.
 */
public class DownloadService extends IntentService {

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        String path = intent.getStringExtra("path");

        if (!TextUtils.isEmpty(url)) {
            downloadData(url,path);
        }
        this.stopSelf();
    }

    private boolean downloadData(String requestUrl,String path) {
        try {
            File rootDir = new File(path);
            if (!rootDir.exists()) {
                rootDir.mkdir();
            }

            File rootFile = new File(rootDir, new Date().getTime() + ".mp4");
            if (!rootFile.exists()) {
                rootFile.createNewFile();
            }

            URL url = new URL(requestUrl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();
            FileOutputStream f = new FileOutputStream(rootFile);
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
            Utils.saveString(getApplicationContext(), requestUrl, rootFile.getAbsolutePath());
            return true;
        } catch (Exception e) {
            return false;
        }

    }


}