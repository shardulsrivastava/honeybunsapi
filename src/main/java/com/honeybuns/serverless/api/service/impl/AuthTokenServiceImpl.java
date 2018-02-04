/**
 * 
 */
package com.honeybuns.serverless.api.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeybuns.serverless.api.dao.RegistrationDAO;
import com.honeybuns.serverless.api.entity.Customer;
import com.honeybuns.serverless.api.entity.LoginDetails;
import com.honeybuns.serverless.api.service.AuthTokenService;

/**
 * @author shardulsrivastava
 *
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

	@Autowired
	private RegistrationDAO registrationDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.honeybuns.serverless.api.service.AuthTokenService#generateAuthToken()
	 */
	@Override
	public String registerAuthToken(String userName) {

		String authToken = UUID.randomUUID().toString();
		Customer customer = registrationDAO.getCustomerByEmailId(userName);
		LoginDetails loginDetails = LoginDetails.builder().authToken(authToken).customerId(customer.getId()).build();

		return registrationDAO.saveAuthToken(loginDetails) ? authToken : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.honeybuns.serverless.api.service.AuthTokenService#validateAuthToken(java.
	 * lang.String)
	 */
	@Override
	public boolean validateAuthToken(String customerId) {
		// TODO Auto-generated method stub
		return false;
	}

}
