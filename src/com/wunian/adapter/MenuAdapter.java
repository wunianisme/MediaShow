package com.wunian.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wunian.activity.R;

/**
 * ≤Àµ•Õº∆¨  ≈‰∆˜
 * 
 * @author jinbin
 * 
 */
public class MenuAdapter extends BaseAdapter {

	private String[] menuTitle;
	private int[] iconId;
	private LayoutInflater inflater;
	private Context context;

	public MenuAdapter(Context context, String[] menuTitle) {
		this.context = context;
		this.menuTitle = menuTitle;
		this.inflater = LayoutInflater.from(context);
		iconId = new int[] { 
				R.drawable.icon_mus,
				R.drawable.icon_mov,
				R.drawable.icon_pic, 
				R.drawable.icon_cam, 
				R.drawable.icon_brs,
				R.drawable.icon_calc, 
				R.drawable.icon_cad, 
				R.drawable.icon_cho,
				R.drawable.icon_daw };
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuTitle.length;
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
		view = inflater.inflate(R.layout.layout_gridview_menu, null);
		TextView title = (TextView) view.findViewById(R.id.tvMenu);
		// title.setBackgroundColor(color[position]);
		title.setText(menuTitle[position]);
		title.setCompoundDrawablesWithIntrinsicBounds(0, iconId[position], 0, 0);
		return view;
	}
}
