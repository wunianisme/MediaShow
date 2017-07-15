package com.wunian.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.view.DrawView;

/**
 * »­°å
 * 
 * @author jinbin
 * 
 */
public class PaintActivity extends Activity implements OnCheckedChangeListener,
		OnClickListener {

	private DrawView dv;
	private LinearLayout menu;
	private TextView back;
	private RadioGroup color, width;
	private Button btnColor, btnWidth, btnSave, btnClear;
	private RadioButton[] rbColor = new RadioButton[6];
	private RadioButton[] rbWidth = new RadioButton[6];
	private int[] colorId = new int[] { R.id.rbBlack, R.id.rbRed, R.id.rbBlue,
			R.id.rbGreen, R.id.rbOrange, R.id.rbMage };
	private int[] colors = new int[] { Color.BLACK, Color.RED, Color.BLUE,
			Color.GREEN, Color.argb(255, 255, 200, 0), Color.MAGENTA };

	private int[] widthId = new int[] { R.id.rb1, R.id.rb3, R.id.rb5, R.id.rb7,
			R.id.rb10, R.id.rb20 };
	private int[] widths = new int[] { 1, 3, 5, 7,
			10, 20 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_paint);
		menu = (LinearLayout) findViewById(R.id.menuLayout);
		color = (RadioGroup) findViewById(R.id.rgColor);
		width = (RadioGroup) findViewById(R.id.rgWidth);
		color.setOnCheckedChangeListener(this);
		width.setOnCheckedChangeListener(this);
		btnColor = (Button) findViewById(R.id.btnColor);
		btnWidth = (Button) findViewById(R.id.btnWidth);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnClear = (Button) findViewById(R.id.btnClear);
		back=(TextView) findViewById(R.id.tvReturn);
		btnColor.setOnClickListener(this);
		btnWidth.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		back.setOnClickListener(this);
		for (int i = 0; i < rbColor.length; i++) {
			rbColor[i] = (RadioButton) findViewById(colorId[i]);
		}
		for (int i = 0; i < rbWidth.length; i++) {
			rbWidth[i] = (RadioButton) findViewById(widthId[i]);
		}

		dv = (DrawView) findViewById(R.id.dvPaint);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		if (arg0.getId() == R.id.rgColor) {
			for (int i = 0; i < rbColor.length; i++) {
				if (rbColor[i].isChecked()) {
					dv.paint.setColor(colors[i]);
				}
			}
		}
		
		if(arg0.getId() == R.id.rgWidth){
			for (int i = 0; i < rbWidth.length; i++) {
				if (rbWidth[i].isChecked()) {
					dv.paint.setStrokeWidth(widths[i]);
				}
			}
		}
		color.setVisibility(View.GONE);
		menu.setVisibility(View.VISIBLE);
		width.setVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		dv.paint.setStrokeWidth(5);
		dv.paint.setXfermode(null);
		switch (v.getId()) {
		case R.id.btnColor:
			color.setVisibility(View.VISIBLE);
			menu.setVisibility(View.GONE);
			width.setVisibility(View.GONE);
			break;
		case R.id.btnWidth:
			color.setVisibility(View.GONE);
			menu.setVisibility(View.GONE);
			width.setVisibility(View.VISIBLE);
			break;
		case R.id.btnClear:
			dv.clear();
			break;
		case R.id.btnSave:
			dv.save();
			Toast.makeText(this, "±£´æ³É¹¦£¡", 0).show();
			break;
		case R.id.tvReturn:
			finish();
			break;
		}
	}
}
