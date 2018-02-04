/**
 * 
 */
package com.honeybuns.serverless.api.service.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeybuns.serverless.api.dao.RegistrationDAO;
import com.honeybuns.serverless.api.model.RegistrationRequest;
import com.honeybuns.serverless.api.model.RegistrationResponse;
import com.honeybuns.serverless.api.service.RegistrationService;
import com.honeybuns.serverless.api.constant.Status;

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
	@Transactional
	public RegistrationResponse register(RegistrationRequest registrationRequest) {
		String errorMessage;

		if (registrationDAO != null) {
			if (!registrationDAO.checkIfEmailAlreadyExists(registrationRequest.getEmail())) {
				return registrationDAO.saveRegistration(registrationRequest);
			} else {
				errorMessage = "Email Already Exists";
			}
		} else {
			errorMessage = "RegistrationDAO bean is null";
			LOGGER.debug("RegistrationDAO bean is null");
		}

		return RegistrationResponse.builder().errorMessage(errorMessage).status(Status.FAILED.name()).build();
	}

}
