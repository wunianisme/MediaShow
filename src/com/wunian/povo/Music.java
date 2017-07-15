package com.wunian.povo;

import java.io.Serializable;

public class Music implements Serializable {

	private String name;
	private String path;
	private String time;
	
	public Music(){}
	
	public Music(String name, String path, String time) {
		this.name = name;
		this.path = path;
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
