package com.facsimilemedia.www.facsimileterminal.play.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.facsimilemedia.www.facsimileterminal.play.R;
import com.itel.szj.m.FileUtil;
import com.itel.szj.m.ImageToVideoUtil;
import com.itel.szj.m.MyImages;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/****
 * 
 *图片转视频测试类
 */
public class ImageToVideoMainActivity extends Activity {
	public static String START = "开始";
	public static String END = "结束";
	public static String PAUSE = "暂停";
	public static String RESTART = "继续";
	EditText et_path;
	Button start,pause;
	String path ;//生成视频的路径
	String videoName;//生成的视频名
	ArrayList<MyImages> imagePathData;//要转成的图片集合
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_image_to_video_main);
		path =  Environment.getExternalStorageDirectory() .getAbsolutePath() + File.separator + "my_screen";
		imagePathData = FileUtil.getImagePaths(path);
		videoName = "test0413.mp4";
		Calendar calendar = Calendar.getInstance();
		videoName = ""+calendar.get(Calendar.YEAR)+(1+calendar.get(Calendar.MONTH))+calendar.get(Calendar.DAY_OF_MONTH)+calendar.get(Calendar.HOUR_OF_DAY)+calendar.get(Calendar.MINUTE)+".mp4";
        et_path = (EditText)findViewById(R.id.editText1);
        start = (Button)findViewById(R.id.button1);
        pause = (Button)findViewById(R.id.button2);
        start.setText(START);
        pause.setText(PAUSE);
        
        start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = ((Button)v).getText().toString();
				if(START.equals(text)){
					ImageToVideoUtil.start(ImageToVideoMainActivity.this, handler, imagePathData, path, videoName, 1920, 1080);
					start.setText(END);
				}else
				if(END.equals(text)){
					ImageToVideoUtil.stop();
					start.setText(START);
				}				
			}
		});
        pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = ((Button)v).getText().toString();
				if(PAUSE.equals(text)){
					ImageToVideoUtil.pause();
					pause.setText(RESTART);
				}else
				if(RESTART.equals(text)){
					ImageToVideoUtil.restart();
					pause.setText(PAUSE);
				}
			}
		});
    }

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 0:
					et_path.setText("生成成功");
					break;
				case 1:
					et_path.setText("生成失败");
					break;
			}
		}
	};

}
