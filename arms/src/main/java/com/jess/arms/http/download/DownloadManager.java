package com.jess.arms.http.download;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import androidx.core.content.FileProvider;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.NetUtils;
import com.jess.arms.utils.RxUtils;

import java.io.File;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by allenliu on 2018/1/18.
 */
@ActivityScope
public class DownloadManager {
    OkHttpClient mOkHttpClient;
    Application mApplication;

    @Inject
    public DownloadManager(Application application, OkHttpClient client) {
        mApplication = application;
        mOkHttpClient = client;
    }

    public void download(final String url, final String fileName, final DownloadListener listener) {
        if(!NetUtils.isConnected(mApplication)){
            ArmsUtils.makeText("请检查网络是否连接");
            handleFailed(listener);
            return;
        }
        // 储存下载文件的目录
        String downloadApkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/mingzhiyoudian";
        File saveFile = new File(downloadApkPath);
        if (!saveFile.exists()) {
            saveFile.mkdir();
        }
        if (url != null && !url.isEmpty()) {
            Request request = new Request
                    .Builder()
                    //#issue 220
                    .addHeader("Accept-Encoding", "identity")
                    .url(url).build();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null)
                        listener.onCheckerStartDownload();
                }
            });

            mOkHttpClient.newCall(request).enqueue(new FileCallBack(downloadApkPath, fileName) {
                @Override
                public void onSuccess(final File file, Call call, Response response) {
                    RxUtils.runOnMainThread(o -> {
                        if (listener != null)
                            listener.onCheckerDownloadSuccess(file);
                    });
                }

                @Override
                public void onDownloading(final int progress) {
                    RxUtils.runOnMainThread(o -> {
                        if (listener != null)
                            listener.onCheckerDownloading(progress);
                    });
                }

                @Override
                public void onDownloadFailed() {
                    handleFailed(listener);
                }
            });


        } else {
            throw new RuntimeException("you must set download url for download function using");
        }
    }

    public void download(final String url, final String downloadApkPath, final String fileName, final DownloadListener listener) {
        if (url != null && !url.isEmpty()) {
            Request request = new Request
                    .Builder()
                    //#issue 220
                    .addHeader("Accept-Encoding", "identity")
                    .url(url).build();
            RxUtils.runOnMainThread(o -> {
                if (listener != null)
                    listener.onCheckerStartDownload();
            });
            mOkHttpClient.newCall(request).enqueue(new FileCallBack(downloadApkPath, fileName) {
                @Override
                public void onSuccess(final File file, Call call, Response response) {
                    RxUtils.runOnMainThread(o -> {
                        if (listener != null)
                            listener.onCheckerDownloadSuccess(file);
                    });
                }

                @Override
                public void onDownloading(final int progress) {
                    RxUtils.runOnMainThread(o -> {
                        if (listener != null)
                            listener.onCheckerDownloading(progress);
                    });
                }

                @Override
                public void onDownloadFailed() {
                    handleFailed(listener);
                }
            });


        } else {
            throw new RuntimeException("you must set download url for download function using");
        }
    }

    private void handleFailed(final DownloadListener listener) {
        delApk();
        RxUtils.runOnMainThread(o -> {
            if (listener != null)
                listener.onCheckerDownloadFail();
        });
    }

    public void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".versionProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public void delApk() {
        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/mingzhiyoudian/mzyd.apk";
        File file = new File(apkPath);
        if (file.exists()) {
            boolean hadDel = file.delete();
        }
    }
}
