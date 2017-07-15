package com.wunian.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wunian.fragment.PictureFragment;
import com.wunian.service.SkinService;
/**
 * œ‘ æ±æµÿÕº∆¨
 * @author jinbin
 *
 */
@SuppressLint("NewApi")
public class PictureActivity extends Activity implements OnClickListener {

	private TextView[] menu = new TextView[3];
	private TextView back;
	private LinearLayout pic;
	private int[] menuId = { R.id.tvPng, R.id.tvJpg, R.id.tvGif };
	private String[] mineType = { "image/png", "image/jpg", "image/gif" };
	private int position;
	private FragmentManager manager;

	
	@SuppressLint("ResourceAsColor") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_picture);
		pic=(LinearLayout) findViewById(R.id.picLayout);
		back = (TextView) findViewById(R.id.tvReturn);
		pic.setBackgroundResource(SkinService.getSkin(this));
		back.setOnClickListener(this);
		for (int i = 0; i < menu.length; i++) {
			menu[i] = (TextView) findViewById(menuId[i]);
			menu[i].setOnClickListener(this);
		}
		manager = getFragmentManager();
		PictureFragment fragment = new PictureFragment();
		Bundle bundle=new Bundle();
		bundle.putString("mineType", mineType[position]);
		fragment.setArguments(bundle);
		manager.beginTransaction().add(R.id.layoutFg, fragment).commit();
		menu[0].setTextColor(Color.WHITE);
		menu[0].setBackgroundColor(R.color.top_color);
		
	}

	@SuppressLint("ResourceAsColor") @Override
	public void onClick(View v) {
		PictureFragment fragment = new PictureFragment();
		switch (v.getId()) {
		case R.id.tvPng:
			position = 0;
			break;
		case R.id.tvJpg:
			position=1;
			break;
		case R.id.tvGif:
			position=2;
			break;
		case R.id.tvReturn:
			finish();
			break;
		}
		for (int i = 0; i < menu.length; i++) {
			if(menu[i].getId()==v.getId()){
				menu[i].setTextColor(Color.WHITE);
				menu[i].setBackgroundColor(R.color.top_color);
			}else{
				menu[i].setTextColor(Color.BLUE);
				menu[i].setBackgroundColor(Color.TRANSPARENT);
			}
		}
		Bundle bundle=new Bundle();
		bundle.putString("mineType", mineType[position]);
		fragment.setArguments(bundle);
		manager.beginTransaction().replace(R.id.layoutFg, fragment).commit();
	}
}
