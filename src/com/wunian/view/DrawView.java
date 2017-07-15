package com.wunian.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 一个画布控件
 * @author jinbin
 *
 */
public class DrawView extends View {
	private int width;
	private int height;
	private float preX;
	private float preY;
	private Path path;
	public Paint paint;
	Bitmap cacheBitmap;
	Canvas cacheCanvas;

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		width = context.getResources().getDisplayMetrics().widthPixels;
		height = context.getResources().getDisplayMetrics().heightPixels;
		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		cacheCanvas = new Canvas();
		path = new Path();
		cacheCanvas.setBitmap(cacheBitmap);
		paint = new Paint(Paint.DITHER_FLAG);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		paint.setDither(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xFFFFFF);
		Paint bmPaint = new Paint();
		canvas.drawBitmap(cacheBitmap, 0, 0, bmPaint);
		canvas.drawPath(path, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(x, y);
			preX=x;
			preY=y;
			break;

		case MotionEvent.ACTION_MOVE:
			float dx=Math.abs(x-preX);
			float dy=Math.abs(y-preY);
			if(dx>=5||dy>=5){
				path.quadTo(preX, preY, (x+preX)/2, (y+preY)/2);
				preX=x;
				preY=y;
			}
			break;
		case MotionEvent.ACTION_UP:
			cacheCanvas.drawPath(path, paint);
			path.reset();
			break;
		}
		invalidate();
		return true;
	}       
	
	public void clear(){
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		paint.setStrokeWidth(50);
	}
	
	public void save(){
		try {
			saveBitmap(System.currentTimeMillis()+"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveBitmap(String name) throws IOException {
		File file=new File(Environment.getExternalStorageDirectory()+"/pictures/",name+".png");
		file.createNewFile();
		FileOutputStream fos=new FileOutputStream(file);
		cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
//		cacheBitmap.recycle();
	}
}
