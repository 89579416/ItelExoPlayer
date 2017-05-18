package com.itel.szj.m;

import java.io.Serializable;

/**
 * 图片数据类
 * 包括路径和要生成的视频时间
 * Created by Administrator on 2017-04-11.
 */
public class MyImages implements Serializable {
    private String path;
    private int time;

    public MyImages(String path, int time) {
        this.path = path;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Images{" +
                "path='" + path + '\'' +
                ", time=" + time +
                '}';
    }
}
