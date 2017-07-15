package com.wunian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
/**
 * Òýµ¼Ò³
 * @author jinbin
 *
 */
public class GuideActivity extends Activity{

	private Handler handler;
	private Runnable run;
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		handler=new Handler();
		run=new Runnable() {
			@Override
			public void run() {
				enterMain();
				handler.postDelayed(run, 1000);
			}
		};
		handler.post(run);
	}
	
	@Override
	protected void onDestroy() {
		handler.removeCallbacks(run);
		super.onDestroy();
	}
	
	public void enterMain(){
		index++;
		if(index==4){
			startActivity(new Intent(this,MainActivity.class));
			finish();
		}
	}
}
