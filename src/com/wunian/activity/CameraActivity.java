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
 * �����
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
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//����ȫ��
	        //������Ļ���ñ���λ�ڼ��ز����ļ�֮ǰ
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
	        //��ӻص������¼�
	        surfaceView.getHolder().addCallback(new SurfaceCallback());
	    }
	    
	    public void onClick(View v){
	    	if(camera!=null){
	    		switch (v.getId()) {
	    		case R.id.btnTake:
	    			//����
	    			camera.takePicture(null, null, new MyPictureCallback());
	    			Toast.makeText(this, "OK!", 0).show();
	    			break;

	    		case R.id.btnPreview:
	    			//�Խ�
	    			camera.autoFocus(null);
	    			Toast.makeText(this, "�۽���...", 0).show();
	    			break;
	    		}
	    	}
	    }
	    //��Ƭ�洢����ɺ�����ٴ�Ԥ��״̬�������������գ�
	    private final class MyPictureCallback implements PictureCallback{
			public void onPictureTaken(byte[] data, Camera mycamera) {
				try {
					//�洢��sdcard��Ŀ¼��Ĭ����ϵͳʱ��Ϊ��
					File jpgFile = new File(Environment.getExternalStorageDirectory()+"/pictures/", System.currentTimeMillis()+".jpg");
					FileOutputStream outStream = new FileOutputStream(jpgFile);
					outStream.write(data);
					outStream.close();
					//��Ƭ�洢��ɺ��ٴν���Ԥ��״̬
					mycamera.startPreview();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    	
	    }
	    //��������ĸ������
	    private final class SurfaceCallback implements Callback{
			public void surfaceCreated(SurfaceHolder holder) {
				try{
					camera = Camera.open();//������ͷ
					Camera.Parameters parameters = camera.getParameters();
					//Log.i("MainActivity", parameters.flatten());
					//����Ԥ����Ƭ�ߴ�
					parameters.setPreviewSize(640, 480);
					//һ�벶׽5����Ƭ
					parameters.setPreviewFrameRate(8);
					//������Ƭ�ߴ�
					parameters.setPictureSize(640,480);
					//������Ƭ����80-100
					parameters.setJpegQuality(100);
					//���������ý�camera����
					camera.setParameters(parameters);
					//ͼ����ʾ��surfaceView�ؼ���
					camera.setPreviewDisplay(holder);
					camera.startPreview();//��ʼԤ��
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
			}
			//surfaceView ����ʱ�ͷ�camera������Ϊϵͳһ��ֻ����һ��Ӧ��ʹ�����
			public void surfaceDestroyed(SurfaceHolder holder) {
				if(camera!=null){
					camera.release();
					camera = null;
				}
			}
	    }

	    //��Ļ�����¼�
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			//�ֻ������Ļ���նԽ���ť�ɼ�
			if(event.getAction() == MotionEvent.ACTION_DOWN&&layout.getVisibility()==View.GONE){
				layout.setVisibility(ViewGroup.VISIBLE);
				return true;
			}
			return super.onTouchEvent(event);
		}
	}
