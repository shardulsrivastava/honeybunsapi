/**
 * 
 */
package com.honeybuns.serverless.api.service;

import com.honeybuns.serverless.api.model.RegistrationRequest;
import com.honeybuns.serverless.api.model.RegistrationResponse;

/**
 * @author shardulsrivastava
 *
 */
public interface RegistrationService {
	
	public RegistrationResponse register(RegistrationRequest registrationRequest);

}
