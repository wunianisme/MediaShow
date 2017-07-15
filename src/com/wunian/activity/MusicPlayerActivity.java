package com.wunian.activity;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.adapter.MusicPlayerAdapter;
import com.wunian.povo.Music;
import com.wunian.service.SkinService;

@SuppressLint("DefaultLocale") 
/**
 * 音乐播放器界面
 * @author jinbin
 *
 */
public class MusicPlayerActivity extends Activity implements OnClickListener,
		OnSeekBarChangeListener {
	private LinearLayout musicPlayer;
	private TextView back, musicName, lrc,nowTime,endTime;
	private SeekBar musicBar;
	private ImageView playMode, pre, play, next, stop, list;
	private ListView musicView;
	private FrameLayout listFrame;
	private MediaPlayer player;
	private boolean paused;
	private List<Music> musicList;
	private int position;
	private Runnable start, update;
	private Handler handler;
	private int mode;//[列表循环，单曲循环，顺序播放，随机播放]
	private MusicPlayerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_musicplayer);
		musicPlayer=(LinearLayout) findViewById(R.id.musicPlayerLayout);
		musicView=(ListView) findViewById(R.id.lvMusic);
		listFrame=(FrameLayout) findViewById(R.id.frameMusicList);
		musicBar = (SeekBar) findViewById(R.id.sbMusic);
		musicName = (TextView) findViewById(R.id.tvMusicName);
		nowTime=(TextView) findViewById(R.id.tvCurrentTime);
		endTime=(TextView) findViewById(R.id.tvDuration);
		back = (TextView) findViewById(R.id.tvReturn);
		lrc = (TextView) findViewById(R.id.tvLrc);
		pre = (ImageView) findViewById(R.id.ivPre);
		next = (ImageView) findViewById(R.id.ivNext);
		play = (ImageView) findViewById(R.id.ivPlay);
		/*stop = (ImageView) findViewById(R.id.ivStop);*/
		playMode = (ImageView) findViewById(R.id.ivMode);
		list = (ImageView) findViewById(R.id.ivList);
		musicBar.setOnSeekBarChangeListener(this);
		pre.setOnClickListener(this);
		next.setOnClickListener(this);
		play.setOnClickListener(this);
//		stop.setOnClickListener(this);
		back.setOnClickListener(this);
		playMode.setOnClickListener(this);
		list.setOnClickListener(this);
		musicPlayer.setBackgroundResource(SkinService.getSkin(this));
		mode=1;
		paused = false;
		player = new MediaPlayer();
		handler = new Handler();

		start = new Runnable() {
			@Override
			public void run() {
				handler.post(update);
			}
		};

		update = new Runnable() {

			@Override
			public void run() {
				int seekMax = musicBar.getMax();
				int musicMax = player.getDuration();
				int currentPosition = player.getCurrentPosition();
				musicBar.setProgress(currentPosition * seekMax / musicMax);
				nowTime.setText(toTime(player.getCurrentPosition()));
				handler.postDelayed(update, 1000);
			}
		};

		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				if(mode==1){
					nextPlay();
				} 
				if(mode==2){
					player.reset();
					play(musicList.get(position).getPath());
				}
				if(mode==3){
					if(position<musicList.size()-1) nextPlay();
					else{
						player.stop();
						play.setImageResource(R.drawable.play_selector);
					} 
				}
				if(mode==4){
					position=(int) (Math.random()*musicList.size());
					player.reset();
					play(musicList.get(position).getPath());
				}
			}
		});
		
		try{
			getMusicData();
		}catch(Exception e){
			Toast.makeText(this, e.getMessage(), 1).show();
		}
		setMusicAdapter();
		
		musicView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				position=pos;
				if(player.isPlaying()){
					player.stop();
				}
				player.reset();
				play(musicList.get(position).getPath());
				
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void getMusicData() {
		SharedPreferences preferences=getSharedPreferences("play_mode", Context.MODE_PRIVATE);
		mode=preferences.getInt("mode", 1);
		if(mode==2)
		player.setLooping(true);
		if(mode==2) playMode.setImageResource(R.drawable.icon_round1);
		if(mode==1) playMode.setImageResource(R.drawable.icon_listround);
		if(mode==3) playMode.setImageResource(R.drawable.icon_listplay);
		if(mode==4) playMode.setImageResource(R.drawable.icon_listrandom);
		Intent data = getIntent();
		Bundle bundle = data.getExtras();
		musicList = (List<Music>) bundle.getSerializable("musicList");
		position = bundle.getInt("position", 0);
		musicName.setText(musicList.get(position).getName());
		endTime.setText(musicList.get(position).getTime());
		play(musicList.get(position).getPath());
		handler.post(update);
		seekBarUpdate();
		play.setImageResource(R.drawable.pause_selector);
		
	}
	
	private void setMusicAdapter(){
		adapter=new MusicPlayerAdapter(musicList, this, position);
		musicView.setAdapter(adapter);
	}
	
	public String toTime(int time) {
		time /= 1000;
		int minute = time / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivPlay:
			if (player.isPlaying()) {
				player.pause();
				play.setImageResource(R.drawable.play_selector);
			} else {
				if (player.getCurrentPosition() > 0
						&& player.getCurrentPosition() != player.getDuration()) {
					player.start();
					play.setImageResource(R.drawable.pause_selector);
					handler.post(update);
					seekBarUpdate();
				} else
					play(musicList.get(position).getPath());
			}
			break;

		case R.id.ivPre:
			prePlay();
			break;
		case R.id.ivNext:
			nextPlay();
			break;

		case R.id.ivStop:
			stop();
			play.setImageResource(R.drawable.play_selector);
			player.reset();
			break;

		case R.id.ivMode:
			if(mode==1){
				player.setLooping(true);
				mode=2;
				playMode.setImageResource(R.drawable.icon_round1);
			}
			else if(mode==2){
				player.setLooping(false);
				mode=3;
				playMode.setImageResource(R.drawable.icon_listplay);
			}
			else if(mode==3){
				player.setLooping(false);
				mode=4;
				playMode.setImageResource(R.drawable.icon_listrandom);
			} 
			else if(mode==4){
				player.setLooping(false);
				mode=1;
				playMode.setImageResource(R.drawable.icon_listround);
			} 
			SharedPreferences preferences=getSharedPreferences("play_mode", Context.MODE_PRIVATE);
			Editor e=preferences.edit();
			e.putInt("mode", mode);
			e.commit();
			break;
		case R.id.ivList:
			if(listFrame.getVisibility()==View.GONE){
				listFrame.setVisibility(View.VISIBLE);
				lrc.setVisibility(View.GONE);
			}else{
				lrc.setVisibility(View.VISIBLE);
				listFrame.setVisibility(View.GONE);
			}
			break;
		case R.id.tvReturn:
			startActivity(new Intent(this, MusicActivity.class));
			finish();
			break;
		}
	}

	public void play(String path) {
		try {
			player.setDataSource(path);
			player.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.start();
		handler.post(update);
		seekBarUpdate();
		setMusicAdapter();
		musicName.setText(musicList.get(position).getName());
		endTime.setText(musicList.get(position).getTime());
		play.setImageResource(R.drawable.pause_selector);
	}

	public void prePlay() {
		if (player.isPlaying()) {
			player.stop();
		}
		player.reset();
		--position;
		if (position < 0) {
			position = musicList.size() - 1;
		}
		play(musicList.get(position).getPath());
	}

	public void nextPlay() {
		if (player.isPlaying()) {
			player.stop();
		}
		player.reset();
		++position;
		if (position >= musicList.size()) {
			position = 0;
		}
		play(musicList.get(position).getPath());
	}

	public void stop() {
		if (player.isPlaying()) {
			player.stop();
		}
	}

	public void seekBarUpdate() {
		handler.post(start);
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		handler.post(update);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		if (player.isPlaying()) {
			player.pause();
			paused = true;
		}
		handler.post(update);
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		int progress = musicBar.getProgress();
		int seekMax = musicBar.getMax();
		int duration = player.getDuration();
		if (paused) {
			player.start();
			paused = false;
		}
		player.seekTo(duration * progress / seekMax);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(update);
		if (player.isPlaying()) {
			player.pause();
		}
		player.release();
		player=null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(listFrame.getVisibility()==View.VISIBLE){
			lrc.setVisibility(View.VISIBLE);
			listFrame.setVisibility(View.GONE);
			
		}
		return super.onTouchEvent(event);
	}
}
