package com.wunian.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wunian.activity.R;
import com.wunian.povo.Video;

public class VideoAdapter extends BaseAdapter{

	private List<Video> videoList;
	private Context context;
	private LayoutInflater inflater;
	
	public VideoAdapter(List<Video> videoList, Context context) {
		this.videoList = videoList;
		this.context = context;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return videoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) {
		view=inflater.inflate(R.layout.layout_listview_video, null);
		TextView name=(TextView) view.findViewById(R.id.tvVideoName);
		TextView time=(TextView) view.findViewById(R.id.tvVideoTime);
		name.setText(videoList.get(pos).getName());
		time.setText(videoList.get(pos).getTime());
		return view;
	}

}
