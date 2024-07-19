package com.practice.utility;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure<T> {

	private int errorstatus;
	private String errormessage;
	private T errordata;
	
	public int getErrorstatus() {
		return errorstatus;
	}
	public void setErrorstatus(int errorstatus) {
		this.errorstatus = errorstatus;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public T getErrordata() {
		return errordata;
	}
	public void setErrordata(T errordata) {
		this.errordata = errordata;
	}
	
	
	
	
	
	
}
