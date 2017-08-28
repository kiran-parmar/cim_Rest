package com.cim.util;

public class Constants {

	//config value
	public static final Long NUMBER_OF_ACTIVE_ADS_ALLOWED_FOR_PARTNER = 1L;
	
	//SERVICE ROUTES
	public static final String SERVICE_ROOT = "/service";
	public static final String CREATE_AD_CAMPAIGN = "/ad";
	public static final String GET_CAMPAIGN_FOR_PARTNER = "/ad/{partner_id}";
	public static final String GET_ALL_CAMPAIGNS = "/adCampaigns";
	
	//MESSAGES
	public static final String AD_CAMPAIGN_CREATED = "Ad campaign created successfully!";
	public static final String NO_CAMPAIGN_EXISTS = "No ad campaigns exist";
	public static final String REQUEST_AUTHENTICATION_FAILED = "Request authentication failed. Please provide valid credentials."; 
	
	//authentication parameters
	public static final String USERNAME = "cim_user";
	public static final String PASSWORD = "admin#123";
	
}
