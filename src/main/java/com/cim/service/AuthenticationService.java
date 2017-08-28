package com.cim.service;

public interface AuthenticationService {
	boolean authenticateRequest(String username, String password);
}
