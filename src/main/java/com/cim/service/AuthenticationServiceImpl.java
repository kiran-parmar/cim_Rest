package com.cim.service;

import org.jboss.resteasy.logging.Logger;

import com.cim.util.Constants;

public class AuthenticationServiceImpl implements AuthenticationService {

	private static Logger log = Logger.getLogger(AuthenticationServiceImpl.class); 
	
	public boolean authenticateRequest(String username, String password) {
		
		log.debug("Authenicating request with username, password : " + username + ", " + password);
		if(Constants.USERNAME.equals(username) && Constants.PASSWORD.equals(password)) {
			return true;
		}

		return false;
	}

}
