package com.wunian.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.wunian.povo.Music;
import com.wunian.povo.Video;

public class VideoService {

	public List<Video> getSDCardVideo(Context c){
		List<Video> videoList=new ArrayList<Video>();
		ContentResolver resolver=c.getContentResolver();
		Cursor cursor=resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		while(cursor.moveToNext()){
			Video video=new Video();
			video.setName(cursor.getString(cursor.getColumnIndex("_display_name")));
			video.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
			long time=cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
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
			video.setTime(minutesFormat+":"+secondsFormat);
			videoList.add(video);
		}
		cursor.close();
		return videoList;
	}
}
