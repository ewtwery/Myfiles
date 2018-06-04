package com.xm.Model;

import java.io.Serializable;

public class GroupData implements Serializable{
	private String name;
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public GroupData(String name) {
		this.name = name;
	}
}
