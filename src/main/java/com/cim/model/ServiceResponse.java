package com.cim.model;

import java.io.Serializable;

public class ServiceResponse implements Serializable {
	
	private boolean status;
	private String message;
	private String errorCode;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("status : ").append(this.isStatus());
		sb.append(", errorCode : ").append(this.getErrorCode());
		sb.append(", message : ").append(this.getMessage());
		
		return sb.toString();
	}
}
