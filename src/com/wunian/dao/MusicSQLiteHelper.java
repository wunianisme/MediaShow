package com.wunian.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicSQLiteHelper extends SQLiteOpenHelper {

	public MusicSQLiteHelper(Context context) {
		super(context, "music", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists love(id integer primary key autoincrement," +
				"name char(20) not null,path char(50) not null,time char(30) not null)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
