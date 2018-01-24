/**
 * 
 */
package com.honeybuns.serverless.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeybuns.serverless.api.dao.RegistrationDAO;
import com.honeybuns.serverless.api.model.RegistrationRequest;
import com.honeybuns.serverless.api.model.RegistrationResponse;
import com.honeybuns.serverless.api.service.RegistrationService;

/**
 * @author shardulsrivastava
 *
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger LOGGER = Logger.getLogger(RegistrationServiceImpl.class);

	@Autowired
	private RegistrationDAO registrationDAO;

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) {
		if (registrationDAO != null) {
			return registrationDAO.saveRegistration(registrationRequest);
		} else {
			LOGGER.debug("RegistrationDAO bean is null");
			return new RegistrationResponse();
		}
	}

}
