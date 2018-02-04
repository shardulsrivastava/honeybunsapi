/**
 * 
 */
package com.honeybuns.serverless.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shardulsrivastava
 *
 */
@Getter
@Setter
@Builder
public class AuthenticationResponse {

	private String authToken;

	private LoginResponse loginResponse;

}
