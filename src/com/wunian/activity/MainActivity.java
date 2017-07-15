package com.wunian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.adapter.MenuAdapter;
import com.wunian.service.SkinService;
/**
 * 首页菜单
 * @author jinbin
 *
 */
public class MainActivity extends Activity implements OnClickListener,OnGestureListener{

	private String[] menuTitle = { "音乐", "视频", "图片", "相机", "浏览器", "计算器", "日历",
			"计时器", "画板" };

	private GridView menu;
	private LinearLayout main;
	private MenuAdapter menuAdapter;
	private ImageView skin;
	private GestureDetector detector;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		menu = (GridView) findViewById(R.id.gvMenu);
		main = (LinearLayout) findViewById(R.id.mainLayout);
		skin = (ImageView) findViewById(R.id.ivSkin);
		detector=new GestureDetector(this);
		main.setBackgroundResource(SkinService.getSkin(this));
		menuAdapter = new MenuAdapter(this, menuTitle);
		menu.setAdapter(menuAdapter);
		skin.setOnClickListener(this); 
		menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					startActivity(new Intent(MainActivity.this,
							MusicActivity.class));
					break;
				case 1:
					startActivity(new Intent(MainActivity.this,
							MovieActivity.class));
					break;
				case 2:
					startActivity(new Intent(MainActivity.this,
							PictureActivity.class));
					break;
				case 3:
					startActivity(new Intent(MainActivity.this,
							CameraActivity.class));
					break;
				case 4:
					startActivity(new Intent(MainActivity.this,
							WebbrowserActivity.class));
					break;
				case 5:
					startActivity(new Intent(MainActivity.this,
							CalculatorActivity.class));
					break;
				case 6:
					startActivity(new Intent(MainActivity.this,
							CalendarActivity.class));
					break;
				case 7:
					startActivity(new Intent(MainActivity.this,
							ChronActivity.class));
					break;
				case 8:
					startActivity(new Intent(MainActivity.this,
							PaintActivity.class));
					break;
				}
			}
		});

	}

	@Override
	protected void onResume() {
		main.setBackgroundResource(SkinService.getSkin(this));
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		try{
			Intent intent=new Intent(MainActivity.this,SkinActivity.class);
			startActivity(intent);
		}catch(Exception e){
			Toast.makeText(this, e.getMessage(), 1).show();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		if(e1.getX()-e2.getX()>100){
			Intent intent=new Intent(MainActivity.this,SkinActivity.class);
			startActivity(intent);
			this.overridePendingTransition(R.anim.right_in,R.anim.left_out);
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
