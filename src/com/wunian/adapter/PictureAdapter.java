package com.wunian.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wunian.activity.R;
import com.wunian.povo.Picture;

public class PictureAdapter extends BaseAdapter {

	private List<Picture> picList;
	private Context context;
	private LayoutInflater inflater;
	
	public PictureAdapter(List<Picture> picList, Context context) {
		this.picList = picList;
		this.context = context;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return picList.size();
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
		view=inflater.inflate(R.layout.layout_gridview_picture, null);
		ImageView img=(ImageView) view.findViewById(R.id.ivPic);
		Bitmap bm = BitmapFactory.decodeFile(picList.get(position).getPath());//重新生成位图对象
		img.setImageBitmap(bm);
		img.setBackgroundColor(Color.WHITE);
		return view;
	}

}
