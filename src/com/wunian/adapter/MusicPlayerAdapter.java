package com.wunian.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wunian.activity.R;
import com.wunian.povo.Music;

public class MusicPlayerAdapter extends BaseAdapter {

	private List<Music> musics;
	private Context context;
	private LayoutInflater inflater;
	private int current;
	
	public MusicPlayerAdapter(List<Music> musics, Context context, int current) {
		this.musics = musics;
		this.context = context;
		this.inflater=LayoutInflater.from(context);
		this.current = current;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musics.size();
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
	public View getView(int pos, View view, ViewGroup arg2) {
		view=inflater.inflate(R.layout.layout_listview_musicplayer, null);
		ImageView img=(ImageView) view.findViewById(R.id.ivicon_current);
		TextView name=(TextView) view.findViewById(R.id.tvMusicName);
		TextView time=(TextView) view.findViewById(R.id.tvMusicTime);
		name.setText(musics.get(pos).getName());
		time.setText(musics.get(pos).getTime());
		if(current==pos){
			img.setImageResource(R.drawable.icon_music);
		}
		return view;
	}

}
