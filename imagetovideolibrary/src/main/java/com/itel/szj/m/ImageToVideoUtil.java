package com.itel.szj.m;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.cpp.opencv_core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

/**
 * 图片转视频类
 */
public class ImageToVideoUtil {
    private static int switcher = 0;//录像键
    private static boolean isPaused = false;//暂停键
    private static Context context;
    /**
     * 图片转视频
     * @param context  上下文
     * @param handler  用于转换完成后传回消息
     * @param imagePathData 本地图片列表
     * @param filepath  转换后文件路径
     * @param fileName  转换后文件名字
     * @param width     视频宽度
     * @param height    视频高度
     */
    public static void start(Context context, final Handler handler, final ArrayList<MyImages> imagePathData, final String filepath, final String fileName, final int width, final int height) {
        //init
        ImageToVideoUtil.context = context;
        switcher = 1;
        new Thread() {
            public void run() {
                OutputStream os = null;
                try {
                    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(filepath + "/"+ fileName, width, height);
                    recorder.setFormat("mp4");
                    recorder.setFrameRate(2f);//录像帧率
                    recorder.start();
                    if (!isPaused) {
                        Log.i("AAAA","文件个数：" + imagePathData.size());
                        for (int s = 0;s<imagePathData.size();s++) {
                            Log.i("AAAA","当前是第几张图：----"+s);
                            opencv_core.IplImage image = cvLoadImage(imagePathData.get(s).getPath());
                            for (int i = 0; i < imagePathData.get(s).getTime() * 2; i++) {
                                Log.i("AAAA","第"+(0+i)+"次添加图片"+imagePathData.get(s));
                                recorder.record(image);
                            }
                        }
                        Log.i("AAAA","recorder.stop");
                        recorder.stop();
                        handler.sendEmptyMessage(0);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    handler.sendEmptyMessage(-1);
                }
            }
        }.start();
    }



    public static void stop() {
        switcher = 0;
        isPaused = false;
    }

    public static void pause() {
        if (switcher == 1) {
            isPaused = true;
        }
    }

    public static void restart() {
        if (switcher == 1) {
            isPaused = false;
        }
    }

    public static boolean isStarted() {
        if (switcher == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPaused() {
        return isPaused;
    }

    private static Bitmap getImageFromAssetsFile(String filename) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(filename);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
