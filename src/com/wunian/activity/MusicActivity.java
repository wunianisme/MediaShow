package com.wunian.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wunian.fragment.MusicFragment;
import com.wunian.service.SkinService;
/**
 * 音乐播放
 * @author jinbin
 *
 */
@SuppressLint({ "NewApi", "ResourceAsColor" })
public class MusicActivity extends Activity implements OnClickListener {

	private TextView back, local, love;
	private FragmentManager manager;
	private LinearLayout music;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_music);
		music=(LinearLayout) findViewById(R.id.musicLayout);
		back = (TextView) findViewById(R.id.tvReturn);
		local = (TextView) findViewById(R.id.tvLocal);
		love = (TextView) findViewById(R.id.tvLove);
		back.setOnClickListener(this);
		local.setOnClickListener(this);
		love.setOnClickListener(this);
		music.setBackgroundResource(SkinService.getSkin(this));
		manager = getFragmentManager();
		MusicFragment fragment = new MusicFragment();
		Bundle bundle = new Bundle();
		bundle.putString("type", "local");
		fragment.setArguments(bundle);
		manager.beginTransaction().replace(R.id.layoutMusic, fragment).commit();
		local.setBackgroundColor(R.color.more_transparent);
	}

	public void onClick(View v) {
		MusicFragment fragment = new MusicFragment();
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.tvLocal:
			local.setTextColor(Color.WHITE);
			local.setTextSize(24);
			local.setBackgroundColor(R.color.more_transparent);
			love.setTextColor(Color.BLUE);
			love.setTextSize(20);
			love.setBackgroundColor(Color.TRANSPARENT);
			bundle.putString("type", "local");
			fragment.setArguments(bundle);
			manager.beginTransaction()
					.replace(R.id.layoutMusic, fragment).commit();
			
			break;
		case R.id.tvLove:
			local.setTextColor(Color.BLUE);
			local.setTextSize(20);
			local.setBackgroundColor(Color.TRANSPARENT);
			love.setTextColor(Color.WHITE);
			love.setTextSize(24);
			love.setBackgroundColor(R.color.more_transparent);
			bundle.putString("type", "love");
			fragment.setArguments(bundle);
			manager.beginTransaction()
					.replace(R.id.layoutMusic, fragment).commit();
			
			break;
		case R.id.tvReturn:
			new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("退出音乐播放")
					.setMessage("您确定要退出音乐播放吗？")
					.setNegativeButton("是的",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									finish();
								}
							}).setPositiveButton("再听一会", null).create().show();
			break;
		}
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
}
