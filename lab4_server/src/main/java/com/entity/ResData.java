package com.entity;

public class ResData {
	private boolean flag;
	private String msg;
	private Object data;
	public ResData() {
		flag=false;
		msg="";
		data=null;
	}
	public boolean getFlag() {
		return flag;
	}
	public String getMsg() {
		return msg;
	}
	public Object getData() {
		return data;
	}
	public void setFlag(boolean Flag) {
		flag=Flag;
	}
	public void setMsg(String Msg) {
		msg=Msg;
	}
	public void setData(Object Data) {
		data=Data;
	}
}
