package com.wunian.activity;

import java.util.Calendar;

import com.wunian.service.SkinService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 计时器
 * 
 * @author jinbin
 * 
 */
public class ChronActivity extends Activity implements OnClickListener {

	// private Chronometer chronTime;
	private LinearLayout chron;
	private TextView back;
	private Button play, zero;
	private Handler handle;
	private Runnable run;
	private ImageView[] clock = new ImageView[6];
	private int[] clockId = { R.id.n_1, R.id.n_2, R.id.n_3, R.id.n_4, R.id.n_5,
			R.id.n_6 };
	private int[] clockNumber = { R.drawable.n_0, R.drawable.n_1,
			R.drawable.n_2, R.drawable.n_3, R.drawable.n_4, R.drawable.n_5,
			R.drawable.n_6, R.drawable.n_7, R.drawable.n_8, R.drawable.n_9, };
	private int mSecIndex, min, sec;
	private boolean start = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chron);
		chron = (LinearLayout) findViewById(R.id.chronLayout);
		back = (TextView) findViewById(R.id.tvReturn);
		play = (Button) findViewById(R.id.btnPlay);
		zero = (Button) findViewById(R.id.btnZero);
		back.setOnClickListener(this);
		play.setOnClickListener(this);
		zero.setOnClickListener(this);
		for (int i = 0; i < clock.length; i++) {
			clock[i] = (ImageView) findViewById(clockId[i]);
		}
		handle = new Handler();
		run = new Runnable() {
			@Override
			public void run() {
				getRunTime();
				handle.postDelayed(run, 2);
			}
		};
	}

	public void getRunTime() {
		getClockImage(1);
		getClockImage(2);
		getClockImage(3);
	}


	public void getClockImage(int flag) {
		switch (flag) {
		case 0:
			mSecIndex = 0;
			min = 0;
			sec = 0;
			for (int i = 0; i < clock.length; i++) {
				clock[i].setImageResource(clockNumber[0]);
			}
			break;
		case 1:

			if (min < 10) {
				clock[0].setImageResource(clockNumber[0]);
				clock[1].setImageResource(clockNumber[min]);

			} else {
				clock[0].setImageResource(clockNumber[min / 10]);
				clock[1].setImageResource(clockNumber[min % 10]);
			}
			if(min>59){
				min=0;
			}
			break;
		case 2:

			if (sec < 10) {
				clock[2].setImageResource(clockNumber[0]);
				clock[3].setImageResource(clockNumber[sec]);

			} else {
				clock[2].setImageResource(clockNumber[sec / 10]);
				clock[3].setImageResource(clockNumber[sec % 10]);
			}
			if(sec>59){
				sec=0;
				min++;
			}
			break;

		case 3:
			
			if (mSecIndex < 10) {
				clock[4].setImageResource(clockNumber[0]);
				clock[5].setImageResource(clockNumber[mSecIndex]);

			}else{
				clock[4].setImageResource(clockNumber[mSecIndex / 10]);
				clock[5].setImageResource(clockNumber[mSecIndex % 10]);
			}
			mSecIndex+=2;
			if (mSecIndex >= 100) {
				mSecIndex = 0;
				sec++;
			}
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPlay:
			if (play.getText().equals("开始计时")) {
				getClockImage(0);
				handle.post(run);
				play.setText("停止计时");
				start = true;
			} else {
				handle.removeCallbacks(run);
				play.setText("开始计时");
				if (start)
					start = false;
				else
					Toast.makeText(this, "还没开始计时！", 0).show();
			}
			break;

		case R.id.btnZero:
			if (!start) {
				getClockImage(0);
			} else {
				Toast.makeText(this, "计时中不允许归零！", 0).show();
			}
			break;
		case R.id.tvReturn:
			finish();
			break;
		}
	}

	@Override
	protected void onDestroy() {
		handle.removeCallbacks(run);
		super.onDestroy();
	}
}
