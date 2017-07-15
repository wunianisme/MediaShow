package com.wunian.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wunian.activity.R;
import com.wunian.povo.Music;
import com.wunian.service.MusicSQLiteService;

public class LoveMusicAdapter extends BaseAdapter {

	private  List<Music> musicList;
	private Context context;
	private LayoutInflater inflater;
	private MusicSQLiteService service;
	
	public LoveMusicAdapter(List<Music> musicList, Context context) {
		this.musicList = musicList;
		this.context = context;
		this.inflater=LayoutInflater.from(context);
		this.service=new MusicSQLiteService(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musicList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int pos,View view, ViewGroup arg2) {
		view=inflater.inflate(R.layout.layout_listview_lovemusic, null);
		TextView name=(TextView)view.findViewById(R.id.tvMusicName);
		ImageView delete=(ImageView) view.findViewById(R.id.ivDelete_love);
		TextView time=(TextView)view.findViewById(R.id.tvMusicTime);
		name.setText(musicList.get(pos).getName());
		time.setText(musicList.get(pos).getTime());
		final int index =pos;
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				service.deleteLove(musicList.get(index).getPath());
				musicList=service.queryLove();
				notifyDataSetChanged();
				Toast.makeText(context, "É¾³ý³É¹¦", 0).show();
			}
		});
		return view;
	}

}
