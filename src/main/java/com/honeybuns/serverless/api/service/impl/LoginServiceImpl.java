/**
 * 
 */
package com.honeybuns.serverless.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeybuns.serverless.api.constant.Status;
import com.honeybuns.serverless.api.dao.RegistrationDAO;
import com.honeybuns.serverless.api.model.AuthenticationResponse;
import com.honeybuns.serverless.api.model.LoginRequest;
import com.honeybuns.serverless.api.model.LoginResponse;
import com.honeybuns.serverless.api.service.AuthTokenService;
import com.honeybuns.serverless.api.service.LoginService;

/**
 * @author shardulsrivastava
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private RegistrationDAO registrationDAO;

	@Autowired
	private AuthTokenService authTokenService;

	@Override
	@Transactional
	public AuthenticationResponse login(LoginRequest loginRequest) {

		String authToken = null;
		LoginResponse loginResponse = registrationDAO.login(loginRequest);
		if (loginResponse.getLoginSatus().equalsIgnoreCase(Status.SUCCESS.name())) {
			authToken = authTokenService.registerAuthToken(loginRequest.getUsername());
		}

		return AuthenticationResponse.builder().authToken(authToken).loginResponse(loginResponse).build();
	}

}
