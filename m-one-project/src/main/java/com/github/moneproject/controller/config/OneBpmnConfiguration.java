package com.github.moneproject.controller.config;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class OneBpmnConfiguration {

	@Primary
	@Bean("appDataSource")
	public DataSource getAppDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mariadb://localhost:3306/moneproject?createDatabaseIfNotExist=true");
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("root");
		return dataSourceBuilder.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(getAppDataSource());
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		emf.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		emf.setPackagesToScan("org.jbpm.persistence.processinstance",
				"org.drools.persistence.info", "org.jbpm.process.audit",
				"org.jbpm.persistence.correlation", "org.jbpm.executor.entities",
				"org.jbpm.runtime.manager.impl.jpa",
				"org.jbpm.services.task.impl.model",
				"org.jbpm.services.task.audit.impl.model");
		emf.setJpaPropertyMap(getJpaProperties());
		emf.setMappingResources("META-INF/JBPMorm.xml", "META-INF/Taskorm.xml",
				"META-INF/TaskAuditorm.xml");
		return emf;
	}

	private Map<String, ?> getJpaProperties() {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("hibernate.max_fetch_depth", 3);
		p.put("hibernate.hbm2ddl.auto", "update");
		p.put("hibernate.dialect", "org.hibernate.dialect.MariaDB106Dialect");
		p.put("hibernate.id.new_generator_mappings", false);
		p.put("hibernate.show_sql", true);
		return p;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws NamingException {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	/*
	 * private Map<Resource, ResourceType> getAssets() {
	 * Map<Resource, ResourceType> assets = new HashMap<Resource, ResourceType>();
	 * assets.put(ResourceFactory
	 * .newClassPathResource("jbpm/processes/sample.bpmn2"),
	 * ResourceType.BPMN2);
	 * assets.put(ResourceFactory
	 * .newClassPathResource("jbpm/processes/loanProcess.bpmn2"),
	 * ResourceType.BPMN2);
	 * return assets;
	 * }
	 */
}
