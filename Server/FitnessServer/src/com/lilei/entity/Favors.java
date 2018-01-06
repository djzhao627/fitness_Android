package com.lilei.entity;

/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月1日 下午8:03:42
 */
public class Favors {

	private int id;

	private int userId;

	private int newsId;

	private String favorTime;

	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getFavorTime() {
		return favorTime;
	}

	public void setFavorTime(String favorTime) {
		this.favorTime = favorTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}



}
