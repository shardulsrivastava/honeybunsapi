/**
 * 
 */
package com.honeybuns.serverless.api.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.honeybuns.serverless.api.entity.CourseRegistration;
import com.honeybuns.serverless.api.model.RegistrationRequest;
import com.honeybuns.serverless.api.model.RegistrationResponse;

/**
 * @author shardulsrivastava
 *
 */
@Repository
public class RegistrationDAO {

	private static final Logger LOGGER = Logger.getLogger(RegistrationDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.openSession();
	}

	public RegistrationResponse saveRegistration(RegistrationRequest request) {

		CourseRegistration courseRegistration = new CourseRegistration();
		courseRegistration.setCourseName(request.getCourseName());
		courseRegistration.setEmail(request.getEmail());
		courseRegistration.setFirstName(request.getFirstName());
		courseRegistration.setLastName(request.getLastName());
		courseRegistration.setPhoneNumber(request.getPhoneNumber());
		courseRegistration.setMessage(request.getMessage());
		if (sessionFactory != null) {
			getSession().save(courseRegistration);
		} else {
			LOGGER.debug("Session Factory Bean is null");
		}

		return new RegistrationResponse();

	}

}
