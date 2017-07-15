package com.wunian.activity;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wunian.service.SkinService;

/**
 * »’¿˙
 * 
 * @author jinbin
 * 
 */
public class CalendarActivity extends Activity implements OnClickListener {

	private FrameLayout calendar;
	private TextView back;
	private DatePicker dp;
	private TextView yt, mt, dt, tt;
	private int year, month, day, hour, minutes, second;
	private Handler handle;
	private Runnable run;
	private ImageView[] clock = new ImageView[6];
	private ImageView pt1, pt2;
	private int[] clockId = { R.id.n_1, R.id.n_2, R.id.n_3, R.id.n_4, R.id.n_5,
			R.id.n_6 };
	private int[] clockNumber = { R.drawable.n_0, R.drawable.n_1,
			R.drawable.n_2, R.drawable.n_3, R.drawable.n_4, R.drawable.n_5,
			R.drawable.n_6, R.drawable.n_7, R.drawable.n_8, R.drawable.n_9, };
	private boolean clock_white=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_calendar);
		calendar = (FrameLayout) findViewById(R.id.calendarLayout);
		dp = (DatePicker) findViewById(R.id.datePicker1);
		yt = (TextView) findViewById(R.id.tvYear);
		mt = (TextView) findViewById(R.id.tvMonth);
		dt = (TextView) findViewById(R.id.tvDay);
		pt1=(ImageView) findViewById(R.id.c_1);
		pt2=(ImageView) findViewById(R.id.c_2);
//		tt = (TextView) findViewById(R.id.tvTime);
		back = (TextView) findViewById(R.id.tvReturn);
		calendar.setBackgroundResource(SkinService.getSkin(this));
		back.setOnClickListener(this);
		for (int i = 0; i < clock.length; i++) {
			clock[i]=(ImageView) findViewById(clockId[i]);
		}
		
		
		// getNowDays();
		handle = new Handler();
		run = new Runnable() {
			@Override
			public void run() {
				getNowDays();
				handle.postDelayed(run, 1000);
			}
		};
		handle.post(run);

	}

	public void getNowDays() {
		Calendar now = Calendar.getInstance();
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH);
		day = now.get(Calendar.DAY_OF_MONTH);
		hour = now.get(Calendar.HOUR_OF_DAY);
		minutes = now.get(Calendar.MINUTE);
		second = now.get(Calendar.SECOND);
		getClockImage(1, hour);
		getClockImage(2, minutes);
		getClockImage(3, second);
		yt.setText("" + year);
		mt.setText("" + (month + 1));
		dt.setText("" + day);
	}
	
	public void getClockImage(int flag,int clockPoint){
		int pos1=0,pos2=0;
		switch (flag) {
		case 1:
			pos1=0;
			pos2=1;
			break;
		case 2:
			pos1=2;
			pos2=3;
			break;
			
		case 3:
			pos1=4;
			pos2=5;
			break;
		}
		if(clockPoint<10){
			clock[pos1].setImageResource(clockNumber[0]);
			clock[pos2].setImageResource(clockNumber[clockPoint]);
			
		}else{
			clock[pos1].setImageResource(clockNumber[clockPoint/10]);
			clock[pos2].setImageResource(clockNumber[clockPoint%10]);
		}
		
		if(!clock_white){
			pt1.setImageResource(R.drawable.clock_point2);
			pt2.setImageResource(R.drawable.clock_point2);
			clock_white=true;
		}else{
			pt1.setImageResource(R.drawable.clock_point);
			pt2.setImageResource(R.drawable.clock_point);
			clock_white=false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvReturn:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// getNowDays();
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDestroy() {
		handle.removeCallbacks(run);
		super.onDestroy();
	}
}
