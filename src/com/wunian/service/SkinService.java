package com.wunian.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wunian.activity.R;

public class SkinService {

	public static int[] skins = { R.drawable.bg_music, R.drawable.bg_music3,
			R.drawable.bg_music4, R.drawable.bg_music5, R.drawable.bg_music6,
			R.drawable.bg_music7, R.drawable.bg_music10, R.drawable.bg_music11,
			R.drawable.bg_music9 };

	private static int index;

	public static int getSkin(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("skin",
				Context.MODE_PRIVATE);
		index = preferences.getInt("drawable-skin", 0);
		return skins[index];
	}

	public static void saveSkin(Context context, int index) {
		SharedPreferences preferences = context.getSharedPreferences("skin",
				Context.MODE_PRIVATE);
		Editor e = preferences.edit();
		e.putInt("drawable-skin", index);
		e.commit();
	}
}
