/**
 * 
 */
package com.honeybuns.serverless.api.service.impl;

import org.springframework.stereotype.Service;

import com.honeybuns.serverless.api.model.RegistrationResponse;
import com.honeybuns.serverless.api.service.RegistrationService;

/**
 * @author shardulsrivastava
 *
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Override
	public RegistrationResponse register() {
		// TODO Auto-generated method stub
		return new RegistrationResponse();
	}

}
