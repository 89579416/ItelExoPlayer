//package com.facsimilemedia.www.facsimileterminal.play;
//
//import android.app.Application;
//import android.os.Environment;
//
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
//import com.google.android.exoplayer2.upstream.HttpDataSource;
//import com.google.android.exoplayer2.util.Util;
//import com.itel.szj.m.manager.CrashHandler;
//import com.itel.szj.m.manager.DeviceManager;
//import com.itel.szj.m.manager.LogManager;
//import com.itel.szj.m.util.FileUtil;
//import com.itel.szj.m.util.L;
//
//import org.xutils.x;
//
//
///**
// * 配置 xutils,未处理异常日志记录
// * Created by Administrator on 2017-05-09.
// */
//public class MyApplication extends Application {
//
//    protected String userAgent;
//    private String defaultUrl;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //对xUtils进行初始化
//        x.Ext.init(this);
//        //是否是开发、调试模式
//        x.Ext.setDebug(BuildConfig.DEBUG);//是否输出debug日志，开启debug会影响性能
//        //初始化日志记录类
//        initLogManager();
//        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
//        createFile();
//    }
//    /**
//     * create file
//     */
//    public void createFile() {
//        L.i("start_create_file");
//        defaultUrl = Environment.getExternalStorageDirectory() + "";
//        /**创建我们软件的专用文件夹*/
//        FileUtil.makeRootDirectory(defaultUrl + "/facsimilemedia");
//        /**创建存放日志的文件夹*/
//        FileUtil.makeRootDirectory(defaultUrl + "/facsimilemedia/Logs");
//        /**创建存放宿主APK的文件夹*/
//        FileUtil.makeRootDirectory(defaultUrl + "/facsimilemedia/Host");
//        /**创建存放插件APK的文件夹*/
//        FileUtil.makeRootDirectory(defaultUrl + "/facsimilemedia/Plugin");
//        /**创建存放资源文件的文件夹*/
//        FileUtil.makeRootDirectory(defaultUrl + "/facsimilemedia/Resources");
//    }
//    /**
//     * 初始化日志记录类
//     */
//    private void initLogManager() {
//        //异常信息日志记录配置
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(this); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
//        DeviceManager.setContext(getApplicationContext());
//        LogManager.setContext(this);
//    }
//    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
//        return new DefaultDataSourceFactory(this, bandwidthMeter,
//                buildHttpDataSourceFactory(bandwidthMeter));
//    }
//
//    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
//        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
//    }
//
//    public boolean useExtensionRenderers() {
//        return BuildConfig.FLAVOR.equals("withExtensions");
//    }
//}
