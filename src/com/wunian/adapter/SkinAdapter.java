package com.wunian.adapter;

import com.wunian.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class SkinAdapter extends BaseAdapter {

	private int[] skinId;

	private Context context;
	private LayoutInflater inflater;

	public SkinAdapter(Context context,int[] skinId) {
		this.context = context;
		this.inflater=LayoutInflater.from(context);
		this.skinId=skinId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return skinId.length;
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
		view=inflater.inflate(R.layout.layout_gridview_skin, null);
		ImageView skin=(ImageView) view.findViewById(R.id.ivSkin);
		skin.setImageResource(skinId[pos]);
		return view;
	}

}
