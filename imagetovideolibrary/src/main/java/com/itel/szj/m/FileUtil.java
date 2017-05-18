package com.itel.szj.m;

import java.io.File;
import java.util.ArrayList;

/**
 * 文件操作类
 */
public class FileUtil {
    /**
     * 获取一个路径下的所有文件 放入一个列表
     * @param path
     * @return
     */
    public static ArrayList<MyImages> getImagePaths(String path) {
        ArrayList<MyImages> paths = new ArrayList<MyImages>();
        File file = new File(path);
        File[] fs = null;
        if (file.exists() && file != null && file.isDirectory()) {
            fs = file.listFiles();
        }
        if (fs != null && fs.length > 0) {
            for (int i = 0; i < fs.length; i++) {
                if(fs[i].getPath().endsWith(".png") || fs[i].getPath().endsWith("jpg")) {
                    paths.add(new MyImages(fs[i].getPath(), 10));
                }
            }
        }
        return paths;
    }
}
