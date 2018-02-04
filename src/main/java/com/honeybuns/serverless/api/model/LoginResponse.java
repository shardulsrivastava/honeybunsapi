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
public class LoginResponse {

	private String loginSatus;
	
	private String errorMessage;

}
