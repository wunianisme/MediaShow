package com.wunian.service;

import java.util.ArrayList;
import java.util.List;

public class BrowserService {

	List<String> urlList;
		
	public List<String> getUrlData(){
		urlList=new ArrayList<String>();
		urlList.add("www.baidu.com");
		urlList.add("www.sougou.com");
		urlList.add("www.sina.cn");
		urlList.add("www.qq.com");
		urlList.add("www.tudou.com");
		
		return urlList;
	}

	
}
