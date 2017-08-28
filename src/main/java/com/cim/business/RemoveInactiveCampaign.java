package com.cim.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.jboss.resteasy.logging.Logger;

import com.cim.model.Campaign;

public class RemoveInactiveCampaign extends TimerTask {

	private static Logger log = Logger.getLogger(RemoveInactiveCampaign.class); 
	private Map<String, List<Campaign>> allCampaigns;
	private Campaign campaign;
	
	public RemoveInactiveCampaign(Map<String, List<Campaign>> allCampaigns, Campaign campaign) {
		this.allCampaigns = allCampaigns;
		this.campaign = campaign;
	}
	
	@Override
	public void run() {
		if(allCampaigns != null) {
			Set<String> keys = allCampaigns.keySet();
			if(keys.contains(campaign.getPartner_id())) {
				List<Campaign> campaignList = allCampaigns.get(campaign.getPartner_id());
				log.debug("Removing campaign : " + campaign);
				campaignList.remove(campaign);
				
				if(campaignList.isEmpty()) {
					log.debug("Removing all campaigns for partner id : " + campaign.getPartner_id());
					allCampaigns.remove(campaign.getPartner_id());
				}
			}
		}
	}

}
