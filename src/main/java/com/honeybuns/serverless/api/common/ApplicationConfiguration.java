/**
 * 
 */
package com.honeybuns.serverless.api.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * @author shardulsrivastava
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("com.honeybuns.serverless.api.service.impl"),
		@ComponentScan("com.honeybuns.serverless.api.dao") })
public class ApplicationConfiguration {

	@Value("${database.url}")
	private String databaseUrl;

	@Value("${database.username}")
	private String databaseUsername;

	@Value("${database.password}")
	private String databasePassword;

	@Value("${database.driver}")
	private String databaseDriver;

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(databaseDriver);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());

		Properties props = new Properties();
		props.put("hibernate.show_sql", Boolean.TRUE);
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");

		factoryBean.setHibernateProperties(props);
		factoryBean.setPackagesToScan("com.honeybuns.serverless.api.entity");
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
