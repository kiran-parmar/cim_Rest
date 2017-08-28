package com.cim.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import com.cim.model.Campaign;

public class ManageCampaign {

	public static Long NUMBER_OF_ACTIVE_ADS_ALLOWED_FOR_PARTNER = 1L;
	
	public boolean createCampaign(Campaign campaign, HttpServletRequest httpRequest) {
		boolean added = false;
		Map<String, List<Campaign>> allCampaigns = getCampaignsMap(httpRequest);
		List<Campaign> campaigns = allCampaigns.get(campaign.getPartner_id());
		if(campaigns == null) {
			campaigns = new ArrayList<Campaign>();
		}
		
		if(NUMBER_OF_ACTIVE_ADS_ALLOWED_FOR_PARTNER  == -1L) {
			added = campaigns.add(campaign);
			
		} else {
			if(campaigns.size() < NUMBER_OF_ACTIVE_ADS_ALLOWED_FOR_PARTNER) {
				added = campaigns.add(campaign);
			}
		}

		allCampaigns.put(campaign.getPartner_id(), campaigns);
		setCampaignsMap(allCampaigns, httpRequest);
		
		if(added) {
			setTimerToRemoveCampaign(campaign, httpRequest);
		}
		
		return added;
	}

	private Map<String, List<Campaign>> getCampaignsMap(HttpServletRequest httpRequest) {
		Map<String, List<Campaign>> allCampaigns = (Map<String, List<Campaign>>) httpRequest.getSession().getAttribute("campaignMap");
		if(allCampaigns == null) {
			allCampaigns = new HashMap<String, List<Campaign>>();
		}
		return allCampaigns;
	}
	
	private void setCampaignsMap(Map<String, List<Campaign>> allCampaigns, HttpServletRequest httpRequest) {
		httpRequest.getSession().setAttribute("campaignMap", allCampaigns);
	}
	
	private void setTimerToRemoveCampaign(Campaign campaign, HttpServletRequest httpRequest) {
		Timer time = new Timer(); // Instantiate Timer Object
		RemoveInactiveCampaign st = new RemoveInactiveCampaign(getCampaignsMap(httpRequest), campaign); // Instantiate SheduledTask class
		time.schedule(st, campaign.getDuration() * 1000);
	}
}
