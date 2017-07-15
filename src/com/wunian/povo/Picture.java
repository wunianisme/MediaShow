package com.wunian.povo;

import java.io.Serializable;

/**
 * ͼƬ��Ϣ
 * @author jinbin
 *
 */
public class Picture implements Serializable{

	private String displayName;
	private String path;
	
	public Picture(){}
	
	public Picture(String displayName, String path) {
		this.displayName = displayName;
		this.path = path;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
