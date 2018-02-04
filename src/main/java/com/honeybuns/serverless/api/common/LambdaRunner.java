/**
 * 
 */
package com.honeybuns.serverless.api.common;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;

/**
 * @author shardulsrivastava
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.honeybuns.serverless.api.util.ContextUtils;

public final class LambdaRunner<I, O> {

	private static final Logger LOGGER = Logger.getLogger(LambdaRunner.class);

	/**
	 * Main class to start the Lambda Handler Execution.
	 * 
	 * @param args
	 *            - Fully Qualified Name of the class eg :
	 *            com.honeybuns.serverless.api.handler.RegistrationHandler
	 * @throws Exception
	 */
	public static <I, O> void main(final String[] args) throws Exception {

		Context context = ContextUtils.getContext();
		if (args.length < 1) {
			throw new RuntimeException("You should give handler class name as an argument");
		}

		String handlerClassName = args[0];

		Object object;
		try {
			Class<?> clazz = Class.forName(handlerClassName);
			Constructor<?> ctor = clazz.getConstructor();
			object = ctor.newInstance();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(handlerClassName + " not found in classpath");
		}

		if (!(object instanceof RequestHandler) && !(object instanceof RequestStreamHandler)) {
			LOGGER.debug("Object is of Type" + object.getClass());
			throw new RuntimeException("Request handler class does not implement " + RequestHandler.class + " or "
					+ RequestStreamHandler.class + "interface");
		}

		if (object instanceof RequestHandler) {
			@SuppressWarnings("unchecked")
			RequestHandler<I, O> requestHandler = (RequestHandler<I, O>) object;
			I requestObject = getRequestObject(requestHandler);

			try {
				O output = requestHandler.handleRequest(requestObject, context);
				LOGGER.debug(
						"SUCCESS: " + (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(output)));
			} catch (RuntimeException e) {
				LOGGER.debug("FAIL:", e);
			}
		} else if (object instanceof RequestStreamHandler) {
			RequestStreamHandler requestStreamHandler = (RequestStreamHandler) object;
			InputStream inputStream = new FileInputStream(args[1]);
			OutputStream outputStream = new BufferedOutputStream(new PipedOutputStream());

			try {
				requestStreamHandler.handleRequest(inputStream, outputStream, context);
				LOGGER.debug("SUCCESS: "
						+ (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(outputStream)));
			} catch (RuntimeException e) {
				LOGGER.debug("FAIL:", e);
			}
		}
	}

	private static <I, O> I getRequestObject(RequestHandler<I, O> handler) throws IOException {

		Type requestClass = null;

		for (Type genericInterface : handler.getClass().getGenericInterfaces()) {
			if (genericInterface instanceof ParameterizedType) {
				Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
				requestClass = genericTypes[0];
			}
		}

		if (null == requestClass) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			Method exampleEventMethod = handler.getClass().getMethod("getMockEvent", (Class<?>[]) null);

			if (exampleEventMethod.getReturnType() != String.class) {
				throw new RuntimeException("Mock Event Return Type should be a string");
			}

			String json = (String) exampleEventMethod.invoke(handler);
			return mapper.readValue((String) json, mapper.getTypeFactory().constructType(requestClass));
		} catch (NoSuchMethodException | RuntimeException | IllegalAccessException | InvocationTargetException e) {
			return mapper.readValue("{}", mapper.getTypeFactory().constructType(requestClass));
		}
	}
}