package com.wunian.activity;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wunian.povo.Picture;
import com.wunian.service.SkinService;
/**
 * 显示每一张图片的Activity
 * @author jinbin
 *
 */
public class PictureShowActivity extends Activity implements OnClickListener,OnGestureListener{

	private List<Picture> picList;
	private int position;
	private TextView back,name,pre,next;
	private ImageView thisImg;
	private GestureDetector detector;
	private static final int MINDISTANCE=100;
	private LinearLayout show;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pictureshow);
//		show=(LinearLayout) findViewById(R.id.showLayout);
		back=(TextView) findViewById(R.id.tvReturn);
		name=(TextView) findViewById(R.id.tvName);
		pre=(TextView) findViewById(R.id.tvBack);
		next=(TextView) findViewById(R.id.tvForward);
		thisImg=(ImageView)findViewById(R.id.ivPic);
//		show.setBackgroundResource(SkinService.getSkin(this));
		back.setOnClickListener(this);
		pre.setOnClickListener(this);
		next.setOnClickListener(this);
		detector=new GestureDetector(this);
		getPicData();
	}

	@SuppressWarnings("unchecked")
	public void getPicData(){
		Intent data=getIntent();
		Bundle bundle=data.getExtras();
		position=bundle.getInt("position", 0);
		picList=(List<Picture>) bundle.getSerializable("pic");
		name.setText(picList.get(position).getDisplayName());
		Bitmap bm=BitmapFactory.decodeFile(picList.get(position).getPath());
		thisImg.setImageBitmap(bm);
//		thisImg.setBackgroundColor(R.color.half_transparent);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvReturn:
			finish();
			break;
		case R.id.tvBack:
			if(position>0) gotoPage(position-1,"left");
			else gotoPage(picList.size()-1,"left");
			break;
		case R.id.tvForward:
			if(position<picList.size()-1) gotoPage(position+1,"right");
			else gotoPage(0,"right");
			break;
		}
	}
	
	public void gotoPage(int index,String direction){
		Intent intent=new Intent(this,PictureShowActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("pic", (Serializable) picList);
		bundle.putInt("position", index);
		intent.putExtras(bundle);
		startActivity(intent);
		if(direction.equals("left")){
			this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
		finish();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.detector.onTouchEvent(event);
	}
	

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		if(e1.getX()-e2.getX()>MINDISTANCE){
			if(position<picList.size()-1) gotoPage(position+1,"right");
			else gotoPage(0,"right");
			return true;
		}
		else if(e1.getX()-e2.getX()<-MINDISTANCE){
			if(position>0) gotoPage(position-1,"left");
			else gotoPage(picList.size()-1,"left");
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onDown(MotionEvent arg0) {
		
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
