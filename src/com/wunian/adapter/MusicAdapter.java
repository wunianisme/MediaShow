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

public class MusicAdapter extends BaseAdapter {

	private List<Music> musicList;
	private Context context;
	private LayoutInflater inflater;
	private MusicSQLiteService musicSQLiteService;
	
	public MusicAdapter(List<Music> musicList, Context context) {
		this.musicList = musicList;
		this.context = context;
		this.inflater=LayoutInflater.from(context);
		this.musicSQLiteService=new MusicSQLiteService(context);
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
	public View getView(int position, View view, ViewGroup arg2) {
		view=inflater.inflate(R.layout.layout_listview_localmusic, null);
		TextView name=(TextView)view.findViewById(R.id.tvMusicName);
		TextView time=(TextView)view.findViewById(R.id.tvMusicTime);
		ImageView add=(ImageView) view.findViewById(R.id.ivAdd_love);
		name.setText(musicList.get(position).getName());
		time.setText(musicList.get(position).getTime());
		final int pos=position;
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(musicSQLiteService.checkLove(musicList.get(pos).getPath())){
					Music music=new Music();
					music.setName(musicList.get(pos).getName());
					music.setPath(musicList.get(pos).getPath());
					music.setTime(musicList.get(pos).getTime());
					musicSQLiteService.addLove(music);
					Toast.makeText(context, "添加成功！", 0).show();
				}else{
					Toast.makeText(context, "添加失败！已经添加到‘我喜爱的’列表中", 0).show();
				}
			}
		});
		return view;
	}

}
