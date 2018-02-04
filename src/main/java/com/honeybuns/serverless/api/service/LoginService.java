/**
 * 
 */
package com.honeybuns.serverless.api.service;

import com.honeybuns.serverless.api.model.AuthenticationResponse;
import com.honeybuns.serverless.api.model.LoginRequest;

/**
 * @author shardulsrivastava
 *
 */
public interface LoginService {

	public AuthenticationResponse login(LoginRequest loginRequest);
}
