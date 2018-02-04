/**
 * 
 */
package com.honeybuns.serverless.api.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.honeybuns.serverless.api.common.ApplicationConfiguration;
import com.honeybuns.serverless.api.model.AuthenticationResponse;
import com.honeybuns.serverless.api.model.LoginRequest;
import com.honeybuns.serverless.api.service.LoginService;

/**
 * @author shardulsrivastava
 *
 */
public class LoginHandler implements RequestStreamHandler {

	private static final Logger LOGGER = Logger.getLogger(LoginHandler.class);

	/**
	 * Here we create the Spring ApplicationContext that will be used throughout our
	 * application.
	 */
	private static final ApplicationContext context = new AnnotationConfigApplicationContext(
			ApplicationConfiguration.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.amazonaws.services.lambda.runtime.RequestStreamHandler#handleRequest(java
	 * .io.InputStream, java.io.OutputStream,
	 * com.amazonaws.services.lambda.runtime.Context)
	 */
	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> responseStream = new HashMap<>();
		Map<String, String> headers = new HashMap<String, String>();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			Map<String, String> requestStream = mapper.readValue(bufferedReader,
					new TypeReference<Map<String, String>>() {
					});
			if (requestStream.containsKey("body")) {
				String requestBody = requestStream.get("body");
				LoginRequest loginRequest = mapper.readValue(requestBody, LoginRequest.class);
				LoginService loginService = getApplicationContext().getBean(LoginService.class);

				AuthenticationResponse authenticationResponse = loginService.login(loginRequest);
				String authToken = authenticationResponse.getAuthToken();
				if (authToken != null) {
					headers.put("x-amz-auth-token", authenticationResponse.getAuthToken());
				}
				responseStream.put("body", mapper.writeValueAsString(authenticationResponse.getLoginResponse()));
			} else {
				responseStream.put("statusCode", "400");
			}

		} catch (Exception pex) {

		}

		responseStream.put("headers", mapper.writeValueAsString(headers));

		LOGGER.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseStream));
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		writer.write(mapper.writeValueAsString(responseStream));
		writer.close();
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

}
