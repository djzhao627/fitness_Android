package com.lilei.entity;

/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月2日 下午10:02:36
 */
public class DailyCheck {
	
	private int id;
	
	private String checkDate;
	
	private int userId;
	
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
