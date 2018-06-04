package com.xm.Bean;


public class ProgramBean {
	private String stepName;
	private int hole;
	private int motion;
	private int speed;
    private int time;
	public ProgramBean() {
	}

	public ProgramBean(String stepName, int hole, int motion, int speed, int time) {
		this.stepName = stepName;
		this.hole = hole;
		this.motion = motion;
		this.speed = speed;
		this.time = time;
	}

	public void setValues(String stepName, int hole, int motion, int speed, int time) {
		this.stepName = stepName;
		this.hole = hole;
		this.motion = motion;
		this.speed = speed;
		this.time = time;
	}

	public void copyValues(ProgramBean programBean) {
		this.stepName = programBean.stepName;
		this.hole = programBean.hole;
		this.motion = programBean.motion;
		this.speed = programBean.speed;
		this.time = programBean.time;
	}
	
	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public int getHole() {
		return hole;
	}

	public void setHole(int hole) {
		this.hole = hole;
	}

	public int getMotion() {
		return motion;
	}

	public String getMotionStr() {
		String[] motion_str = {"振动混匀", "吸磁珠", "放磁珠", "等待时间", "加热开启", "加热关闭"};
		return motion_str[motion-1];
	}

	public void setMotion(int motion) {
		this.motion = motion;
	}

	public int getSpeed() {
		return speed;
	}

	public String getSpeedStr() {
		return speed==0?" ":speed+"";
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTime() {
		return time;
	}

	public String getTimeStr() {
		return time==0?" ":time+"";
	}

	public void setTime(int time) {
		this.time = time;
	}
    
}
