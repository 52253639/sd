package com.kingdee.eas.fdc.schedule;

public class CommonValueInfo {
	private String number = null;
	private String name = null;
	private String id = null;
	
	public CommonValueInfo(String number,String name){
		this.number = number;
		this.name = name ;
	}
	
	public CommonValueInfo(String id,String number,String name){
		this.id = id;
		this.number = number;
		this.name = name ;
	}
	
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getId() {
		return id;
	}
}
