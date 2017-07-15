package com.wunian.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.wunian.povo.Video;

/**
 * 视频播放界面
 * 
 * @author jinbin
 * 
 */
public class MoviePlayerActivity extends Activity implements OnClickListener {

	private TextView back, name;
	private VideoView video;
	private ImageView pre, next;
	private RelativeLayout top, bottom;
	private List<Video> videoList;
	private int position;
	private MediaController control;
	private Handler handler;
	private Runnable listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_movieplayer);
		try {
			back = (TextView) findViewById(R.id.tvReturn);
			name = (TextView) findViewById(R.id.tvMovieName);
			video = (VideoView) findViewById(R.id.videoView);
			pre = (ImageView) findViewById(R.id.ivPre);
			next = (ImageView) findViewById(R.id.ivNext);
			top = (RelativeLayout) findViewById(R.id.topLayout);
			bottom = (RelativeLayout) findViewById(R.id.bottomLayout);
			back.setOnClickListener(this);
			pre.setOnClickListener(this);
			next.setOnClickListener(this);
			handler=new Handler();
			getMovieData();
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), 1).show();
		}
	}

	@SuppressWarnings("unchecked")
	public void getMovieData() {
		Intent data = getIntent();
		Bundle bundle = data.getExtras();
		videoList = (List<Video>) bundle.getSerializable("videoList");
		position = bundle.getInt("position");
		control = new MediaController(this);
		video.setMediaController(control);
		play(videoList.get(position).getPath());
		
		listener =new Runnable() {
			
			@Override
			public void run() {
				
				if (control.isShowing()) {
					top.setVisibility(View.VISIBLE);
					bottom.setVisibility(View.VISIBLE);
				} else {
					top.setVisibility(View.GONE);
					bottom.setVisibility(View.GONE);
				}
				handler.postDelayed(listener, 500);
			}
		};
		
		handler.post(listener);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivPre:
			pre();
			break;
		case R.id.ivNext:
			next();
			break;
		case R.id.ivPlay:
			play(videoList.get(position).getPath());
			break;
		case R.id.tvReturn:
			finish();
			break;
		}
	}

	public void next() {
		if (video.isPlaying()) {
			video.stopPlayback();
		}
		++position;
		if (position > videoList.size() - 1) {
			position = 0;
		}
		play(videoList.get(position).getPath());
	}

	public void pre() {
		if (video.isPlaying()) {
			video.stopPlayback();
		}
		--position;
		if (position < 0) {
			position = videoList.size() - 1;
		}
		play(videoList.get(position).getPath());
	}

	public void play(String path) {
		if (video.isPlaying()) {
			video.stopPlayback();
			return;
		}

		video.setVideoPath(path);
		video.start();
		name.setText(videoList.get(position).getName() + "   "
				+ videoList.get(position).getTime());
	}
	
	@Override
	protected void onDestroy() {
		handler.removeCallbacks(listener);
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
