package com.cim.util;


public enum ErrorCode {
	CODE01("CODE-01", "Only " + Constants.NUMBER_OF_ACTIVE_ADS_ALLOWED_FOR_PARTNER + " active ad campaign(s) allowed for a given partner id"),
	CODE02("CODE-02", "There are no active ad campaign(s) for the partner id.");
	
	private String code;
	private String description;

	private ErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return getCode();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
