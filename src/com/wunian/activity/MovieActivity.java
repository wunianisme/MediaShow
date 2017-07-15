package com.wunian.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wunian.adapter.VideoAdapter;
import com.wunian.povo.Video;
import com.wunian.service.SkinService;
import com.wunian.service.VideoService;
/**
 * 视频播放功能
 * @author jinbin
 *
 */
public class MovieActivity extends Activity implements OnClickListener{

	private LinearLayout movie;
	private ListView videoView;
	private TextView back;
	private List<Video> videoList;
	private VideoService service;
	private VideoAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_movie);
		movie=(LinearLayout)findViewById(R.id.movieLayout);
		videoView=(ListView) findViewById(R.id.lvMoive);
		back=(TextView) findViewById(R.id.tvReturn);
		back.setOnClickListener(this);
		movie.setBackgroundResource(SkinService.getSkin(this));
		service=new VideoService();
		videoList=new ArrayList<Video>();
		videoList=service.getSDCardVideo(this);
		adapter=new VideoAdapter(videoList, this);
		videoView.setAdapter(adapter);
		videoView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Intent intent=new Intent(MovieActivity.this,MoviePlayerActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt("position", pos);
				bundle.putSerializable("videoList", (Serializable) videoList);
				intent.putExtras(bundle);
				startActivity(intent);
				/*finish();*/
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvReturn:
			finish();
			break;
		}
	}
}
