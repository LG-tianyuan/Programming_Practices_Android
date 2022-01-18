package com.entity;

public class User {
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public String getPwd() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPwd(String password) {
		this.password = password;
	}
	public User(String username,String password) {
		this.username=username;
		this.password=password;
	}
}
