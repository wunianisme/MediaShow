package com.wunian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.adapter.SkinAdapter;
import com.wunian.service.SkinService;

/**
 * 皮肤设置
 * 
 * @author jinbin
 * 
 */
public class SkinActivity extends Activity implements OnClickListener {

	private LinearLayout skinLayout;
	private TextView save;
	private GridView skin;
	private int set;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_skinset);
		skinLayout = (LinearLayout) findViewById(R.id.skinLayout);
		save = (TextView) findViewById(R.id.tvSave);
		skin = (GridView) findViewById(R.id.gvSkin);
		save.setOnClickListener(this);
		skinLayout.setBackgroundResource(SkinService.getSkin(this));
		try {
			SkinAdapter adapter = new SkinAdapter(this,SkinService.skins);
			skin.setAdapter(adapter);
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), 1).show();
		}

		skin.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				set++;
				index = pos;
				skinLayout.setBackgroundResource(SkinService.skins[index]);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvSave:
			if (set >= 1) {
				SkinService.saveSkin(this, index);
				Toast.makeText(this, "设置成功", 0).show();
			}
			finish();
			break;
		}
	}
}
