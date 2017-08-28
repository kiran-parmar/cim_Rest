package com.cim.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.cim.service.AdCampaignServiceImpl;

public class CimApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public CimApplication() {
		singletons.add(new AdCampaignServiceImpl());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
