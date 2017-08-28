package com.cim.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.logging.Logger;

import com.cim.business.RemoveInactiveCampaign;
import com.cim.model.Campaign;
import com.cim.model.ServiceResponse;
import com.cim.util.Constants;
import com.cim.util.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path(Constants.SERVICE_ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdCampaignServiceImpl implements AdCampaignService {

	private static Logger log = Logger.getLogger(AdCampaignServiceImpl.class); 
	
	@Context
	private HttpServletRequest httpRequest;
	
	@POST
	@Path(Constants.CREATE_AD_CAMPAIGN)
	public Response createAdCampaign(String campaignStr) {
		
		log.info("createAdCampaign API called with data : " + campaignStr);
		
		ServiceResponse serviceResponse = new ServiceResponse();
		Campaign campaign = getRequestCampaign(campaignStr);
		
		if(campaign == null) {
			
			serviceResponse.setStatus(false);
			serviceResponse.setMessage(Response.Status.BAD_REQUEST.getReasonPhrase());
			serviceResponse.setErrorCode(String.valueOf(Response.Status.BAD_REQUEST.getStatusCode()));
			return Response.status(Response.Status.BAD_REQUEST).entity(serviceResponse).build();
		} else {
			
			if(storeCampaign(campaign)) {
				serviceResponse.setStatus(true);
				serviceResponse.setMessage(Constants.AD_CAMPAIGN_CREATED);
			} else {
			
				serviceResponse.setStatus(false);
				serviceResponse.setErrorCode(ErrorCode.CODE01.getCode());
				serviceResponse.setMessage(ErrorCode.CODE01.getDescription());
			}
			return Response.status(Response.Status.OK).entity(serviceResponse).build();
		}
	}
	
	@GET
	@Path(Constants.GET_CAMPAIGN_FOR_PARTNER)
	public Response getCampaign(@PathParam("partner_id") String partner_id) {
		log.info("getCampaign API called for partner_id : " + partner_id);
		
		Map<String, List<Campaign>> allCampaigns = getCampaignsMap();
		List<Campaign> campaigns = allCampaigns.get(partner_id);
		if(campaigns != null && !campaigns.isEmpty()) {
			return Response.status(Response.Status.OK).entity(campaigns).build();
		} else {
			ServiceResponse serviceResponse = new ServiceResponse();
			serviceResponse.setStatus(false);
			serviceResponse.setErrorCode(ErrorCode.CODE02.getCode());
			serviceResponse.setMessage(ErrorCode.CODE02.getDescription());
			return Response.status(Response.Status.OK).entity(serviceResponse).build();
		}
	}
	
	@GET
	@Path(Constants.GET_ALL_CAMPAIGNS)
	public Response getAllAdCampaigns() {
		log.info("getAllAdCampaigns API called");
		
		Map<String, List<Campaign>> allCampaigns = getCampaignsMap();
		if(allCampaigns.isEmpty()) {
			ServiceResponse serviceResponse = new ServiceResponse();
			serviceResponse.setStatus(true);
			serviceResponse.setMessage(Constants.NO_CAMPAIGN_EXISTS);
			return Response.status(Response.Status.OK).entity(serviceResponse).build();
		} else {
			return Response.status(Response.Status.OK).entity(allCampaigns).build();
		}
	}
	
	private Campaign getRequestCampaign(String campaignStr) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Campaign campaign = null;
		
		try {
			campaign = objectMapper.readValue(campaignStr, Campaign.class);
		} catch (IOException e) {
			log.error("Could not convert the json into object : " + campaignStr);
		}
		return campaign;
	}
	
	private boolean storeCampaign(Campaign campaign) {
		boolean added = false;
		Map<String, List<Campaign>> allCampaigns = getCampaignsMap();
		List<Campaign> campaigns = allCampaigns.get(campaign.getPartner_id());
		if(campaigns == null) {
			campaigns = new ArrayList<Campaign>();
		}
		
		if(Constants.NUMBER_OF_ACTIVE_ADS_ALLOWED_FOR_PARTNER  == -1L) {
			added = campaigns.add(campaign);
			
		} else {
			if(campaigns.size() < Constants.NUMBER_OF_ACTIVE_ADS_ALLOWED_FOR_PARTNER) {
				added = campaigns.add(campaign);
			}
		}

		allCampaigns.put(campaign.getPartner_id(), campaigns);
		setCampaignsMap(allCampaigns, httpRequest);
		
		if(added) {
			setTimerToRemoveCampaign(campaign);
			log.info("Campaign created successfully : " + campaign);
		}
		
		return added;
	}
	
	private Map<String, List<Campaign>> getCampaignsMap() {
		Map<String, List<Campaign>> allCampaigns = (Map<String, List<Campaign>>) httpRequest.getSession().getAttribute("campaignMap");
		if(allCampaigns == null) {
			allCampaigns = new HashMap<String, List<Campaign>>();
		}
		return allCampaigns;
	}
	
	private void setCampaignsMap(Map<String, List<Campaign>> allCampaigns, HttpServletRequest httpRequest) {
		httpRequest.getSession().setAttribute("campaignMap", allCampaigns);
	}
	
	private void setTimerToRemoveCampaign(Campaign campaign) {
		Timer time = new Timer(); // Instantiate Timer Object
		RemoveInactiveCampaign st = new RemoveInactiveCampaign(getCampaignsMap(), campaign); // Instantiate SheduledTask class
		time.schedule(st, campaign.getDuration() * 1000L);
	}
}
