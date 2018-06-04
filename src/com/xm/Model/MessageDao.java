package com.xm.Model;

public class MessageDao {
	private String FromID;
	private String ToID;
	private String Content;
	public MessageDao(){}
	public String getFromID() {
		return FromID;
	}
	public void setFromID(String fromID) {
		FromID = fromID;
	}
	public String getToID() {
		return ToID;
	}
	public void setToID(String toID) {
		ToID = toID;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
}
