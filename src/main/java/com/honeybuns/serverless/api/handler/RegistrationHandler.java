package com.honeybuns.serverless.api.handler;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.honeybuns.serverless.api.model.RegistrationRequest;
import com.honeybuns.serverless.api.model.RegistrationResponse;

public class RegistrationHandler implements RequestHandler<RegistrationRequest, RegistrationResponse> {

	private static final Logger LOGGER = Logger.getLogger(RegistrationHandler.class);

	@Override
	public RegistrationResponse handleRequest(RegistrationRequest input, Context context) {
		RegistrationResponse result = null;
		try {
			// Handle the Registration.
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return result;
	}
}
