/**
 * 
 */
package com.honeybuns.serverless.api.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author shardulsrivastava
 *
 */
public class JsonUtils {

	private static final Logger LOGGER = Logger.getLogger(JsonUtils.class);

	public static <T> String getObjectAsString(T object) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			LOGGER.debug(String.format("Json Conversion => %s ", jsonString));

		} catch (JsonGenerationException e) {
			LOGGER.debug("Error Occured During Json Conversion", e);
		} catch (JsonMappingException e) {
			LOGGER.debug("Error Occured During Json Conversion", e);
		} catch (IOException e) {
			LOGGER.debug("Error Occured During Json Conversion", e);
		}

		return jsonString;
	}

}
