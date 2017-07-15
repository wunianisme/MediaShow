package com.wunian.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.service.BrowserService;
/**
 * 浏览器
 * @author jinbin
 *
 */
public class WebbrowserActivity extends Activity implements OnClickListener {

	private WebView webView;
	private AutoCompleteTextView urlText;
	private Button go;
	private TextView forward, back;
	private BrowserService browserService;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_browser);
		webView = (WebView) findViewById(R.id.wvBrowser);
		urlText = (AutoCompleteTextView) findViewById(R.id.actUrl);
		go = (Button) findViewById(R.id.btnGo);
		forward = (TextView) findViewById(R.id.tvNext);
		back = (TextView) findViewById(R.id.tvPre);
		go.setOnClickListener(this);
		forward.setOnClickListener(this);
		back.setOnClickListener(this);
		browserService = new BrowserService();

		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		setAdapter();
		urlText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					if (!"".equals(urlText.getText().toString())) {
						openBrowser(urlText.getText().toString());
						return true;
					} else {
						showDialog();
					}
				}
				return false;
			}
		});
		// 监听软键盘
		final View decorView = getWindow().getDecorView();
		decorView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						Rect rect = new Rect();
						decorView.getWindowVisibleDisplayFrame(rect);
						int displayHight = rect.bottom - rect.top;
						int hight = decorView.getHeight();
						boolean visible = (double) displayHight / hight < 0.8;

						if (visible) {
							forward.setVisibility(View.GONE);
							back.setVisibility(View.GONE);
						} else {
							forward.setVisibility(View.VISIBLE);
							back.setVisibility(View.VISIBLE);
						}
					}
				});

	}

	public void setAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				browserService.getUrlData());
		urlText.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvPre:
			webView.goBack();
			break;
		case R.id.tvNext:
			webView.goForward();
			break;
		case R.id.btnGo:
			if (!"".equals(urlText.getText().toString())) {
				openBrowser(urlText.getText().toString());
			} else {
				showDialog();
			}
			break;

		default:
			break;
		}
	}

	private void openBrowser(String data) {
		if (!data.startsWith("http://")) {
			data = "http://" + data;
		}
		webView.loadUrl(data);
		// Toast.makeText(this, "正在加载"+data, 0).show();
	}

	private void showDialog() {
		new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
				.setTitle("来自浏览器的消息").setMessage("请输入具体的网址！")
				.setPositiveButton("确定", null).create().show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();// 返回上一页面
				return true;
			} else {
				System.exit(0);//退出
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
