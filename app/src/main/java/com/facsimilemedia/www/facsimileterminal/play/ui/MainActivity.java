//package com.facsimilemedia.www.facsimileterminal.play.ui;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.TextView;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.facsimilemedia.www.facsimileterminal.play.R;
//import com.facsimilemedia.www.facsimileterminal.play.exo.MyExoPlayer;
//import com.facsimilemedia.www.facsimileterminal.play.util.FileUtil;
//import com.facsimilemedia.www.facsimileterminal.play.util.L;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.extractor.ExtractorsFactory;
//import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
//import com.google.android.exoplayer2.source.ExtractorMediaSource;
//import com.google.android.exoplayer2.source.LoopingMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.upstream.TransferListener;
//import com.google.android.exoplayer2.util.Util;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import static android.content.ContentValues.TAG;
//
//public class MainActivity extends Activity {
//
//    /**定位控件*/
//    LocationClient mLocClient;
//    /**定位监听*/
//    public MyLocationListenner myListener;
//
//
//    SimpleExoPlayerView simpleExoPlayerView;
//    private ArrayList<String> data = new ArrayList<>();
//    MyExoPlayer myExoPlayer;
//    Context context;
//
//    DataSource.Factory dataSourceFactory;
//    BandwidthMeter bandwidthMeter;
//    MediaSource[] mediaSources;
//    ExtractorsFactory extractorsFactory;
//    LoopingMediaSource loopingMediaSource;
//    ConcatenatingMediaSource concatenatedSource;
//    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/facsimilemedia/Resources/localPlay";
//    private TextView textView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = this;
//        initLocation();
//        initWindow();
//        setContentView(R.layout.activity_main);
//        initView();// 初始化控件
//        //  initData();//初始化数据
//    }
//
//    public void initView() {
//        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.main_player_view);
//        textView = (TextView)findViewById(R.id.text_view);
//    }
//
//    private void initData() {
//        FileUtil.isDirExist(FILE_PATH, true);
//        //视频列表
//        data = FileUtil.getAllMp4Files(new File(FILE_PATH));
//        if (data.size()==0){
//            textView.setText("本地无播放资源，请将视频拷入SD卡：facsimilemedia/Resources/localPlay");
//        }else {
//            textView.setText("");
//        }
//        //所要的数据
//        bandwidthMeter = new DefaultBandwidthMeter();
//        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "MyAppLication"), (TransferListener<? super DataSource>) bandwidthMeter);
//        extractorsFactory = new DefaultExtractorsFactory();
//        mediaSources = new MediaSource[data.size()];
//        for (int i = 0; i < data.size(); i++) {
//            mediaSources[i] = new ExtractorMediaSource(Uri.parse(data.get(i)), dataSourceFactory, extractorsFactory, null, null);
//        }
//        L.i("mediaSources.length=" + mediaSources.length);
//        concatenatedSource = new ConcatenatingMediaSource(mediaSources);
//        //最后要传入的无缝播放的视频列表
//        loopingMediaSource = new LoopingMediaSource(concatenatedSource);
//
//        if (data != null && data.size() > 0) {
//            myExoPlayer = new MyExoPlayer(context, simpleExoPlayerView, loopingMediaSource);
//            myExoPlayer.init();
//            myExoPlayer.start();
//
//        }
//    }
//
//
//    /**
//     * 全屏显示
//     */
//    public void initWindow() {
//        // 设置为全屏并隐藏有些设备下面的控制按钮
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//    }
//
//    /**
//     * 初始化定位
//     */
//    public void initLocation(){
//        // 定位初始化
//        myListener = new MyLocationListenner();
//        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true);// 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(2000); // 设置扫描间隔，单位是毫秒
//        option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//    }
//    /**
//     * 定位SDK监听函数
//     */
//    public class MyLocationListenner implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (location == null) return;
//            L.i( "SDK监听函数11-->" + location.getLatitude() + " " + location.getLongitude() + " " + location.getCity()+" "+location.getAddrStr());
//
//        }
//
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e(TAG, "onPause: ");
//        release();
//        data.clear();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // initWindow();
//        initData();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        release();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        release();
//    }
//
//    /**
//     * 清除播放器缓存
//     */
//    private void release() {
//        if (myExoPlayer != null) {
//            myExoPlayer.stop();
//            myExoPlayer.release();
//        }
//    }
//}
