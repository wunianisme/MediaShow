package com.wunian.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.wunian.dao.MusicSQLiteHelper;
import com.wunian.povo.Music;

public class MusicSQLiteService {

	private MusicSQLiteHelper helper;
	private SQLiteDatabase db;
	public MusicSQLiteService(Context context){
		helper=new MusicSQLiteHelper(context);
	}
	
	public void addLove(Music music){
		db=helper.getWritableDatabase();
		db.execSQL("insert into love(name,path,time) values(?,?,?)", new Object[]{music.getName(),music.getPath(),music.getTime()});
	}
	
	public void deleteLove(String path){
		db=helper.getWritableDatabase();
		db.execSQL("delete from love where path=?", new String[]{path});
	}
	
	public List<Music> queryLove(){
		List<Music> musicList=new ArrayList<Music>();
		db=helper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from love", null);
		while(cursor.moveToNext()){
			Music music=new Music();
			music.setName(cursor.getString(cursor.getColumnIndex("name")));
			music.setPath(cursor.getString(cursor.getColumnIndex("path")));
			music.setTime(cursor.getString(cursor.getColumnIndex("time")));
			musicList.add(music);
		}
		cursor.close();
		return musicList;
	}
	
	public boolean checkLove(String path){
		
		db=helper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from love where path=?", new String[]{path});
		if(cursor.moveToNext()) return false;
		cursor.close();
		return true;
	}
	
	public void close(){
		if(db!=null){
			db.close();
		}
	}
}
