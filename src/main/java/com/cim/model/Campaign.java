package com.cim.model;

import java.io.Serializable;

public class Campaign implements Serializable{

	private String partner_id;
	private Long startTime;
	private Long endTime;
	private String ad_content;
	private Integer duration;
	
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
		
		long currentTime = System.currentTimeMillis();
		this.startTime = currentTime;
		this.endTime = currentTime + ( this.duration * 1000);
	}
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getAd_content() {
		return ad_content;
	}
	public void setAd_content(String ad_content) {
		this.ad_content = ad_content;
	}
	
	@Override
	public int hashCode() {
		int result = 37;
		result += partner_id.hashCode();
		result += startTime.hashCode();
		result += ad_content.hashCode();

		return result; 
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if(obj == null || !(obj instanceof Campaign)) {
			return false;
		}
		Campaign campaign = (Campaign) obj;
		return this.getPartner_id().equals(campaign.getPartner_id()) && 
				this.getStartTime() == campaign.getStartTime() && this.getAd_content().equals(campaign.getAd_content());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("partner_id : ").append(this.getPartner_id());
		sb.append(", ad_content : ").append(this.getAd_content());
		
		return sb.toString();
	}
}
