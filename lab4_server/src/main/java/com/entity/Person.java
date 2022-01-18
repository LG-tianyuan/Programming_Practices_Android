package com.entity;

public class Person {
	private String username;
	private String name;
	private String age;
	private String telenumber;
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public String getAge() {
		return age;
	}
	public String getTele() {
		return telenumber;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(String age) {
		this.age=age;
	}
	public void setTele(String tele) {
		this.telenumber=tele;
	}
	public Person() {
		this.username="";
		this.age="";
		this.name="";
		this.telenumber="";
	}
	public Person(String username,String name,String age,String telenumber) {
		this.username=username;
		this.name=name;
		this.age=age;
		this.telenumber=telenumber;
	}
}
