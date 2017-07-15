package com.wunian.fragment;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.activity.MusicPlayerActivity;
import com.wunian.activity.R;
import com.wunian.adapter.LoveMusicAdapter;
import com.wunian.adapter.MusicAdapter;
import com.wunian.povo.Music;
import com.wunian.service.MusicSQLiteService;
import com.wunian.service.MusicService;
/**
 * 本地音乐 与 我喜爱的 页面
 * @author jinbin
 *
 */
@SuppressLint("NewApi") 
public class MusicFragment extends Fragment implements OnClickListener,OnSeekBarChangeListener,OnItemClickListener{

	private ListView localView,loveView;
	private SeekBar musicBar;
	private ImageView pre,next,play,stop,musicBg;
	private TextView current,empty;
	private List<Music> musicList;
	private MusicService musicService;
	private MusicSQLiteService musicSQLiteService;
	private MediaPlayer player;
	private int position;
	private Runnable start,update;
	private Handler handler;
	private boolean paused;
	private String type;
	private LinearLayout bottomLayout;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_music, container, false);
		localView=(ListView)v.findViewById(R.id.lvLocal);
		loveView=(ListView)v.findViewById(R.id.lvLove);
		musicBar=(SeekBar) v.findViewById(R.id.sbMusic);
		current=(TextView) v.findViewById(R.id.tvCurrent);
		empty=(TextView) v.findViewById(R.id.tvEmpty);
		pre=(ImageView) v.findViewById(R.id.ivPre);
		next=(ImageView) v.findViewById(R.id.ivNext);
		play=(ImageView) v.findViewById(R.id.ivPlay);
		stop=(ImageView) v.findViewById(R.id.ivStop);
		musicBg=(ImageView) v.findViewById(R.id.ivMusic);
		bottomLayout=(LinearLayout)v.findViewById(R.id.layoutBottom);
		musicBar.setOnSeekBarChangeListener(this);
		pre.setOnClickListener(this);
		next.setOnClickListener(this);
		play.setOnClickListener(this);
		stop.setOnClickListener(this);
		musicBg.setOnClickListener(this);
		paused=false;
		musicService=new MusicService();
		musicSQLiteService=new MusicSQLiteService(getActivity());
		player=new MediaPlayer();
		handler=new Handler();
		Bundle bundle=getArguments();
		type=bundle.getString("type");
		if(type.equals("local")){
			localView.setVisibility(View.VISIBLE);
			loveView.setVisibility(View.GONE);
			localMusic();
		}
		if(type.equals("love")){
			loveView.setVisibility(View.VISIBLE);
			localView.setVisibility(View.GONE);
			try{
				loveMusic();
			}catch(Exception e){
				Toast.makeText(getActivity(), e.getMessage(), 1).show();
			}
		}
		localView.setOnItemClickListener(this);
		loveView.setOnItemClickListener(this);
		start=new Runnable() {
			@Override
			public void run() {
				handler.post(update);
			}
		};
		
		update=new Runnable() {
			
			@Override
			public void run() {
				int seekMax=musicBar.getMax();
				int musicMax=player.getDuration();
				int currentPosition=player.getCurrentPosition();
				musicBar.setProgress(currentPosition*seekMax/musicMax);
				handler.postDelayed(update, 1000);
			}
		};
		return v;
	}
	
	private void localMusic() {
		musicList=musicService.getSDCardMusic(getActivity());
		MusicAdapter adapter=new MusicAdapter(musicList, getActivity());
		localView.setAdapter(adapter);
		SharedPreferences preferences=getActivity().getSharedPreferences("currentLocal",Context.MODE_PRIVATE);
		position=preferences.getInt("position", 0);
		if(musicList.size()>0&&position<musicList.size()) current.setText(musicList.get(position).getName()+"   "+musicList.get(position).getTime());
		else if(musicList.size()>0&&position>=musicList.size()) current.setText(musicList.get(0).getName()+"   "+musicList.get(0).getTime());
		else{
			bottomLayout.setVisibility(View.GONE);
			empty.setVisibility(View.VISIBLE);
			empty.setText("未扫描到本地音乐");
		} 
	}
	
	private void loveMusic() {
		bottomLayout.setVisibility(View.GONE);
		musicList=musicSQLiteService.queryLove();
		LoveMusicAdapter adapter=new LoveMusicAdapter(musicList, getActivity());
		loveView.setAdapter(adapter);
		
		if(musicList.size()<=0){
			empty.setVisibility(View.VISIBLE);
			empty.setText("‘我喜爱的’列表中没有添加任何歌曲");
		} 
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivPlay:
			if(player.isPlaying()){
				player.pause();
				play.setImageResource(R.drawable.play_selector);
			}else{
				if(player.getCurrentPosition()>0&&player.getCurrentPosition()!=player.getDuration()){
					player.start();
					play.setImageResource(R.drawable.pause_selector);
				} 
				else play(musicList.get(position).getPath());
				handler.post(update);
				seekBarUpdate();
				
			}
			break;
		case R.id.ivPre:
			prePlay();
			handler.post(update);
			seekBarUpdate();
			break;
		case R.id.ivNext:
			nextPlay();
			handler.post(update);
			seekBarUpdate();
			break;
		case R.id.ivStop:
			stop();
			play.setImageResource(R.drawable.play_selector);
			player.reset();
			break;
		case R.id.ivMusic:
			if(musicList.size()>0){
				enterPlayer(position);
			}else{
				current.setText("无歌曲");
			}
			break;
		}
	}
	
	public void play(String path){
		try {
			player.setDataSource(path);
			player.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.start();
		current.setText(musicList.get(position).getName()+"   "+musicList.get(position).getTime());
		play.setImageResource(R.drawable.pause_selector);
	}
	
	public void prePlay(){
		if(player.isPlaying()){
			player.stop();
		}
		player.reset();
		--position;
		if(position<0){
			position=musicList.size()-1;
		}
		play(musicList.get(position).getPath());
	}
	
	public void nextPlay(){
		if(player.isPlaying()){
			player.stop();
		}
		player.reset();
		++position;
		if(position>=musicList.size()){
			position=0;
		}
		play(musicList.get(position).getPath());
	}
	
	public void stop(){
		if(player.isPlaying()){
			player.stop();
		}
	}
	
	public void seekBarUpdate(){
		handler.post(start);
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		handler.post(update);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		if(player.isPlaying()){
			player.pause();
			paused=true;
		} 
		handler.post(update);
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		int progress=musicBar.getProgress();
		int seekMax=musicBar.getMax();
		int duration=player.getDuration();
		if(paused){
			player.start();
			paused=false;
		} 
		player.seekTo(duration*progress/seekMax);
	}
	
	@Override
	public void onPause() {
		SharedPreferences preferences;
		if(type.equals("local")){
			preferences=getActivity().getSharedPreferences("currentLocal", Context.MODE_PRIVATE);
			Editor e=preferences.edit();
			e.putInt("position", position);
			e.commit();
		} 
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(update);
		if(player.isPlaying()){
			player.pause();
		} 
		player.release();
		player=null;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
		if(pos<musicList.size()&&musicList.size()>0){
			enterPlayer(pos);
		}
	}

	public void enterPlayer(int pos) {
		Intent intent=new Intent(getActivity(),MusicPlayerActivity.class);
		Bundle bundle=new Bundle();
		if(type.equals("love")){
			musicList=musicSQLiteService.queryLove();
		}
		bundle.putSerializable("musicList", (Serializable) musicList);
		bundle.putInt("position", pos);
		intent.putExtras(bundle);
		startActivity(intent);
		getActivity().finish();
	}
}
