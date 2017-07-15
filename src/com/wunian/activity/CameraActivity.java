package com.wunian.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 * 照相机
 * @author jinbin
 *
 */
public class CameraActivity extends Activity implements OnClickListener{

	private Button take,preview;
	private RelativeLayout layout;
	private SurfaceView surfaceView;
	private SurfaceHolder holder;
	private Camera camera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_camera);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
	        //窗口屏幕设置必须位于加载布局文件之前
	        setContentView(R.layout.activity_camera);
	        take=(Button) findViewById(R.id.btnTake);
	        preview=(Button) findViewById(R.id.btnPreview);
	        layout = (RelativeLayout) findViewById(R.id.takeLayout);
	        take.setOnClickListener(this);
	        preview.setOnClickListener(this);
	        
	        surfaceView = (SurfaceView) this.findViewById(R.id.svCamera);
	        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	        surfaceView.getHolder().setFixedSize(176, 144);
	        surfaceView.getHolder().setKeepScreenOn(true);
	        //添加回调监听事件
	        surfaceView.getHolder().addCallback(new SurfaceCallback());
	    }
	    
	    public void onClick(View v){
	    	if(camera!=null){
	    		switch (v.getId()) {
	    		case R.id.btnTake:
	    			//拍照
	    			camera.takePicture(null, null, new MyPictureCallback());
	    			Toast.makeText(this, "OK!", 0).show();
	    			break;

	    		case R.id.btnPreview:
	    			//对焦
	    			camera.autoFocus(null);
	    			Toast.makeText(this, "聚焦中...", 0).show();
	    			break;
	    		}
	    	}
	    }
	    //照片存储和完成后进入再次预览状态（继续可以拍照）
	    private final class MyPictureCallback implements PictureCallback{
			public void onPictureTaken(byte[] data, Camera mycamera) {
				try {
					//存储进sdcard根目录，默认以系统时间为名
					File jpgFile = new File(Environment.getExternalStorageDirectory()+"/pictures/", System.currentTimeMillis()+".jpg");
					FileOutputStream outStream = new FileOutputStream(jpgFile);
					outStream.write(data);
					outStream.close();
					//照片存储完成后再次进入预览状态
					mycamera.startPreview();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    	
	    }
	    //设置相机的各项参数
	    private final class SurfaceCallback implements Callback{
			public void surfaceCreated(SurfaceHolder holder) {
				try{
					camera = Camera.open();//打开摄像头
					Camera.Parameters parameters = camera.getParameters();
					//Log.i("MainActivity", parameters.flatten());
					//设置预览照片尺寸
					parameters.setPreviewSize(640, 480);
					//一秒捕捉5张照片
					parameters.setPreviewFrameRate(8);
					//设置照片尺寸
					parameters.setPictureSize(640,480);
					//设置照片质量80-100
					parameters.setJpegQuality(100);
					//将参数设置进camera对象
					camera.setParameters(parameters);
					//图像显示在surfaceView控件中
					camera.setPreviewDisplay(holder);
					camera.startPreview();//开始预览
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
			}
			//surfaceView 销毁时释放camera对象，因为系统一次只允许一个应用使用相机
			public void surfaceDestroyed(SurfaceHolder holder) {
				if(camera!=null){
					camera.release();
					camera = null;
				}
			}
	    }

	    //屏幕触碰事件
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			//手机点击屏幕拍照对焦按钮可见
			if(event.getAction() == MotionEvent.ACTION_DOWN&&layout.getVisibility()==View.GONE){
				layout.setVisibility(ViewGroup.VISIBLE);
				return true;
			}
			return super.onTouchEvent(event);
		}
	}
