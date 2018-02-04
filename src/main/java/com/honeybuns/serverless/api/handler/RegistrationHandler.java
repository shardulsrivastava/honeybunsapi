package com.honeybuns.serverless.api.handler;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.honeybuns.serverless.api.common.ApplicationConfiguration;
import com.honeybuns.serverless.api.model.RegistrationRequest;
import com.honeybuns.serverless.api.model.RegistrationResponse;
import com.honeybuns.serverless.api.service.RegistrationService;
import com.honeybuns.serverless.api.util.JsonUtils;

public class RegistrationHandler implements RequestHandler<RegistrationRequest, RegistrationResponse> {

	private static final Logger LOGGER = Logger.getLogger(RegistrationHandler.class);

	/**
	 * Here we create the Spring ApplicationContext that will be used throughout our
	 * application.
	 */
	private static final ApplicationContext context = new AnnotationConfigApplicationContext(
			ApplicationConfiguration.class);

	@Override
	public RegistrationResponse handleRequest(RegistrationRequest input, Context context) {
		RegistrationResponse result = null;
		try {
			if (getApplicationContext() != null) {
				RegistrationService registrationService = getApplicationContext().getBean(RegistrationService.class);
				if (registrationService != null) {
					LOGGER.debug(String.format("Input is {}", input.toString()));
					result = registrationService.register(input);
				} else {
					LOGGER.debug("RegistrationService Bean didn't load properly");
				}
			} else {
				LOGGER.debug("Application Context is null");
			}
		} catch (Exception e) {
			LOGGER.debug(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

	public String getMockEvent() {

		RegistrationRequest registrationRequest = RegistrationRequest.builder()
				.email(String.format("%s@gmail.com", RandomStringUtils.randomAlphanumeric(8))).firstName("test")
				.lastName("test").phoneNumber("0132490538").password("password").build();

		return JsonUtils.getObjectAsString(registrationRequest);

	}
}
