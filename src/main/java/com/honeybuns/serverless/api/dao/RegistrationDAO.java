/**
 * 
 */
package com.honeybuns.serverless.api.dao;

import static com.honeybuns.serverless.api.constant.Constants.EMPTY_STRING;
import static com.honeybuns.serverless.api.constant.Constants.USERNAME_PASSWORD_WRONG;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.honeybuns.serverless.api.constant.Status;
import com.honeybuns.serverless.api.entity.Customer;
import com.honeybuns.serverless.api.entity.LoginDetails;
import com.honeybuns.serverless.api.model.LoginRequest;
import com.honeybuns.serverless.api.model.LoginResponse;
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

		String errorMessage = "";
		Status status = Status.FAILED;
		try {
			Customer customer = Customer.builder().firstName(request.getEmail()).lastName(request.getLastName())
					.email(request.getEmail()).phoneNumber(request.getPhoneNumber()).password(request.getPassword())
					.lastUpdated(Timestamp.valueOf(LocalDateTime.now())).build();

			getSession().save(customer);
			status = Status.SUCCESS;
		} catch (Exception ex) {
			errorMessage = ex.getMessage();
			status = Status.FAILED;
		}

		return RegistrationResponse.builder().errorMessage(errorMessage).status(status.name()).build();

	}

	public boolean checkIfEmailAlreadyExists(String emailId) {
		return !(getCustomerByEmailId(emailId) == null);
	}

	public Customer getCustomerByEmailId(String emailId) {
		Customer customer = null;
		try {
			if (sessionFactory != null) {
				Session session = getSession();
				String queryString = "FROM com.honeybuns.serverless.api.entity.Customer where email = :email_id";
				Query<Customer> query = session.createQuery(queryString, Customer.class);
				query.setParameter("email_id", emailId);
				customer = query.uniqueResult();

			} else {
				LOGGER.debug("Session Factory Bean is null");
			}
		} catch (Exception ex) {
			LOGGER.debug("Exception Occured while getting customer =>", ex);
		}

		return customer;
	}

	public LoginResponse login(LoginRequest loginRequest) {

		String errorMessage = USERNAME_PASSWORD_WRONG;
		Status loginStatus = Status.FAILED;
		Customer customer = getCustomerByEmailId(loginRequest.getUsername());

		if ((customer != null) && (customer.getPassword().equalsIgnoreCase(loginRequest.getPassword()))) {
			errorMessage = EMPTY_STRING;
			loginStatus = Status.SUCCESS;
		}

		return LoginResponse.builder().errorMessage(errorMessage).loginSatus(loginStatus.name()).build();
	}

	public boolean saveAuthToken(LoginDetails loginDetails) {

		boolean tokenSaved = true;
		try {
			Session session = getSession();
			session.save(loginDetails);
		} catch (Exception ex) {
			LOGGER.debug("Error Occured while saving AuthToken =>", ex);
			tokenSaved = false;
		}
		return tokenSaved;
	}

}
