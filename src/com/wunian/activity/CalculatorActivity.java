package com.wunian.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.service.CalculatorService;
import com.wunian.service.SkinService;
/**
 * 计算器
 * @author jinbin
 *
 */
public class CalculatorActivity extends Activity {
	private LinearLayout calc;
	private TextView resultView;
	private Button Left;
	private int colorIndex;
	private int[] screenColor = { Color.BLACK, Color.BLUE, Color.RED,
			Color.MAGENTA, Color.argb(100, 0, 0, 0), Color.YELLOW, Color.argb(100, 255, 150, 150) };
	private boolean finish=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_calculator);
		calc=(LinearLayout)findViewById(R.id.calcLayout);
		calc.setBackgroundResource(SkinService.getSkin(this));
		resultView = (TextView) this.findViewById(R.id.resultView);
		Left=(Button) this.findViewById(R.id.left);
		SharedPreferences preferences=getSharedPreferences("colorIndex", Context.MODE_PRIVATE);
		colorIndex=preferences.getInt("index", 0);
		resultView.setBackgroundColor(screenColor[colorIndex]);
	}
	/**
	 * 设置屏幕色
	 * @param v 控件
	 */
	public void setScreenColor(View v){
		resultView.setBackgroundColor(screenColor[colorIndex]);//设置屏幕色
		SharedPreferences preferences=getSharedPreferences("colorIndex", Context.MODE_PRIVATE);
		Editor e=preferences.edit();
		e.putInt("index", colorIndex);
		e.commit();
//		Left.setBackgroundColor(screenColor[colorIndex]);//设置按钮背景色
		colorIndex++;
		if(colorIndex>6) colorIndex=0;
	}

	public void buttonEvent(View v) {
		String text = resultView.getText().toString();
		/**
		 * 根据按钮id执行相应操作
		 */
		switch (v.getId()) {
		case R.id.backspace:
			if (text.length() > 0&&!finish) {
				String resultText = text.substring(0, text.length() - 1);
				resultView.setText(resultText);
			}else{
				resultView.setText("");
				finish=false;
			}
			break;
		case R.id.clear:
			resultView.setText("");
			finish=false;
			break;
		case R.id.result:
			
			try{
				double result = CalculatorService.getResult4(text);
				resultView.setText(result+"");
				finish=true;
			}catch(Exception e){
				Toast.makeText(getApplicationContext(),e.getMessage(), 1)
				.show();
				finish=false;
			}
			
			break;
		case R.id.one:
			if(!finish) text += "1";
			break;
		case R.id.two:
			if(!finish) text += "2";
			break;
		case R.id.three:
			if(!finish) text += "3";
			break;
		case R.id.four:
			if(!finish) text += "4";
			break;
		case R.id.five:
			if(!finish) text += "5";
			break;
		case R.id.six:
			if(!finish) text += "6";
			break;
		case R.id.seven:
			if(!finish) text += "7";
			break;
		case R.id.eight:
			if(!finish) text += "8";
			break;
		case R.id.nine:
			if(!finish) text += "9";
			break;
		case R.id.zero:
			if(!finish) text += "0";
			break;
		case R.id.add:
			if(!finish) text += "+";
			break;
		case R.id.decrease:
			if(!finish) text += "-";
			break;
		case R.id.multiply:
			if(!finish) text += "*";
			break;
		case R.id.chu:
			if(!finish) text += "/";
			break;
		case R.id.dian:
			if(!finish) text += ".";
			break;
		case R.id.tvReturn:
			finish();
			break;
		}
		if(v.getId()!=R.id.backspace&&v.getId()!=R.id.clear&&v.getId()!=R.id.result){
			resultView.setText(text);
			if(finish){
				Toast.makeText(this, "请先清空屏幕！", 0).show();
			}
		}
	}
}
