package com.xm.Bean;

public class ProgramManageBean {

	private String programName = "";
	private String time = "";
	private String notes = "";
	private int isStandard = 0;
	
	public int getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(int isStandard) {
		this.isStandard = isStandard;
	}

	public ProgramManageBean() {
	}
	
	public ProgramManageBean(String programName, String time, String notes, int isStandard) {
		this.programName = programName;
		this.time = time;
		this.notes = notes;
		this.isStandard = isStandard;
	}

	public void copyValues(ProgramManageBean programManageBean){
		this.programName = programManageBean.programName;
		this.time = programManageBean.time;
		this.notes = programManageBean.notes;
	}
	
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
