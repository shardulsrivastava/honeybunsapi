package com.honeybuns.serverless.api.service;

public interface AuthTokenService {

	/*
	 * Generate the Authentication Token after login.
	 */
	public String registerAuthToken(String userName);

	/**
	 * Validate the Authentication Token in subsequent requests.
	 * 
	 * @param customerId
	 *            - Id of a customer
	 * @return true or false
	 */
	public boolean validateAuthToken(String customerId);

}
