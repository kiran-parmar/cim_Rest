package com.cim.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cim.model.Campaign;
import com.cim.model.ServiceResponse;
import com.cim.service.AdCampaignService;
import com.cim.service.AdCampaignServiceImpl;
import com.cim.util.Constants;

public class AdCampaignServiceTest {

	public static EmbeddedServer server;
	public static AdCampaignService adCampaignService = new AdCampaignServiceImpl();
	
	@BeforeClass
    public static void beforeClass() throws Exception {
        server = EmbeddedServer.create(adCampaignService);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        server.close();
    }
    
    @Test
    public void testCreateCampaign_badRequest() throws Exception {

        Response response = server.newRequest(Constants.SERVICE_ROOT + Constants.CREATE_AD_CAMPAIGN).request().
        		buildPost(Entity.json("{\"test\":\"ff\"}")).invoke();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void testCreateCampaign_success() throws Exception {

        Response response = server.newRequest(Constants.SERVICE_ROOT + Constants.CREATE_AD_CAMPAIGN).request().
        		buildPost(Entity.json(requestJson())).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        ServiceResponse serviceResponse = response.readEntity(ServiceResponse.class);
        assertEquals(null, serviceResponse.getErrorCode());
    }
    
    @Test
    public void testCreateCampaign_failure() throws Exception {

    	Response response = server.newRequest(Constants.SERVICE_ROOT + Constants.CREATE_AD_CAMPAIGN).request().
        		buildPost(Entity.json(requestJson())).invoke();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        ServiceResponse serviceResponse = response.readEntity(ServiceResponse.class);
        assertNotNull(serviceResponse.getErrorCode());
    }
    
    @Test
    public void test_getCampaign_failure() {
    	Response response = server.newRequest(Constants.SERVICE_ROOT).path("ad/invalid_partner").request().
        		buildGet().invoke();
        
        ServiceResponse serviceResponse = response.readEntity(ServiceResponse.class);
        assertNotNull(serviceResponse.getErrorCode());
    }
    
    @Test
    public void test_getCampaign_success() {
    	Response response = server.newRequest(Constants.SERVICE_ROOT).path("ad/unit_test").request().
        		buildGet().invoke();
        
    	List<Campaign> campaigns = response.readEntity(List.class);
        assertNotNull(campaigns);
        
        Campaign campaign = campaigns.get(0);
        assertNotNull(campaign.getAd_content());
    }
    
    private String requestJson() {
    	StringBuilder sb = new StringBuilder("{\"partner_id\" : \"unit_test\",");
    	sb.append("\"ad_content\" : \"Unit testing the ad campaign service!!\",");
    	sb.append("\"duration\" : \"60\"}");
    	
    	return sb.toString();
    }
}
