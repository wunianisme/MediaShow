package com.wunian.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.wunian.povo.Music;

public class MusicService {

	public List<Music> getSDCardMusic(Context c){
		List<Music> musicList=new ArrayList<Music>();
		ContentResolver resolver=c.getContentResolver();
		Cursor cursor=resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		while(cursor.moveToNext()){
			Music music=new  Music();
			music.setName(cursor.getString(cursor.getColumnIndex("_display_name")));
			music.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
			long time=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
			long minute=time/1000/60;
			long second=minute%60;
			String minutesFormat="";
			String secondsFormat="";
			if(minute<10){
				minutesFormat="0"+minute;
			}else{
				minutesFormat=minute+"";
			}
			if(second<10){
				secondsFormat="0"+second;
			}else{
				secondsFormat=second+"";
			}
			music.setTime(minutesFormat+":"+secondsFormat);
			musicList.add(music);
		}
		cursor.close();
		return musicList;
	}
}
