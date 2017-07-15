package com.wunian.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.wunian.activity.PictureShowActivity;
import com.wunian.activity.R;
import com.wunian.adapter.PictureAdapter;
import com.wunian.povo.Picture;
/**
 * 显示不同图片类型的fragment
 * @author jinbin
 *
 */
@SuppressLint("NewApi")
public class PictureFragment extends Fragment {

	private List<Picture> picList;
	private Uri uri;
	private ContentResolver resolver;
	private GridView gvPic;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_picture, container, false);
		gvPic=(GridView)view.findViewById(R.id.gvPic);
		Bundle bundle=getArguments();
		String mineType=bundle.getString("mineType");
		picList = new ArrayList<Picture>();
		resolver =getActivity().getContentResolver();
		uri = Uri.parse("content://media/external/images/media");
		queryPictureSource(mineType);
		PictureAdapter adapter=new PictureAdapter(picList, getActivity());
		gvPic.setAdapter(adapter);
		gvPic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent=new Intent(getActivity(),PictureShowActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("pic",(Serializable) picList);
				bundle.putInt("position", position);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
				
			}
		});
		return view;
	}

	public void queryPictureSource(String mineType) {
		Cursor cursor = resolver.query(uri, null,
				MediaStore.Images.Media.MIME_TYPE +"=?",
				new String[] {mineType}, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Picture pic=new Picture();
				pic.setPath(cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.DATA)));
				pic.setDisplayName(cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)));
				picList.add(pic);
			}
			cursor.close();
		}
	}
}
