package com.groupproject.moneproject;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.jbpm.bpmn2.handler.SignallingTaskHandlerDecorator;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.TaskService;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.spring.factorybeans.RuntimeEnvironmentFactoryBean;
import org.kie.spring.factorybeans.RuntimeManagerFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.groupproject.moneproject.config.CustomUserGroupCallback;
import com.groupproject.moneproject.handler.ExceptionWorkItemHandler;
import com.groupproject.moneproject.handler.LoanLimitWorkItemHandler;

@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class JbpmDataSourceConfig {
	
	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		/*dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mysql://localhost:3306/sms?serverTimezone=UTC");
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("root");*/
		dataSourceBuilder.driverClassName("org.h2.Driver");
		dataSourceBuilder.url("jdbc:h2:mem:testjbpm");
		dataSourceBuilder.username("sa");
		dataSourceBuilder.password("sa");
		return dataSourceBuilder.build();
	}

	/*
	 * @Bean public EntityManagerFactory entityManagerFactory() {
	 * LocalContainerEntityManagerFactoryBean emf = new
	 * LocalContainerEntityManagerFactoryBean(); emf.setDataSource(getDataSource());
	 * emf.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
	 * emf.setPersistenceUnitName("org.jbpm.persistence.jpa");
	 * 
	 * return emf.getObject(); }
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("org.jbpm.persistence.jpa");
		em.setDataSource(getDataSource());
		em.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
		// em.setPackagesToScan(new String[] { "com.group.model" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		//em.setJpaProperties(additionalProperties());

		return em;
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
	//	properties.setProperty("hibernate.hbm2ddl.auto", "create");
		//properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		return properties;
	}
	   
	@Bean
	public JpaTransactionManager getTransactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return jpaTransactionManager;
	}
	
	/*
	 * @Bean public CustomUserGroupCallback getUserCallBack() {
	 * 
	 * return new CustomUserGroupCallback(); }
	 */
	
	@Bean
	public RuntimeEnvironmentFactoryBean runtimeEnvironment() {

	    RuntimeEnvironmentFactoryBean ref = new RuntimeEnvironmentFactoryBean();

	    ref.setType("DEFAULT");
	    ref.setEntityManagerFactory(entityManagerFactory().getObject());
	    ref.setTransactionManager(getTransactionManager());
	    ref.setUserGroupCallback(getUserCallBack());
	    // ref.setKbaseName("kbase");
	     ref.setAssets(getAssets());
	    return ref;

	}
 
	@Bean
	public UserGroupCallback getUserCallBack() {
		 
		return new CustomUserGroupCallback();
	}

	private Map<Resource, ResourceType> getAssets() {
	    Map<Resource, ResourceType> assets = new HashMap<Resource, ResourceType>();
	    assets.put(ResourceFactory
	            .newClassPathResource("jbpm/processes/sample.bpmn2"),
	            ResourceType.BPMN2);
	    assets.put(ResourceFactory
	            .newClassPathResource("jbpm/processes/loanProcess.bpmn2"),
	            ResourceType.BPMN2);
	    return assets;
	}
	// if ("PER_REQUEST".equalsIgnoreCase(type)) { 
//("PER_PROCESS_INSTANCE".equalsIgnoreCase(type)) {
	
	@Bean(name = "runtimeManager", destroyMethod = "close")
	public RuntimeManagerFactoryBean runtimeManager() {
		//RuntimeManagerFactory factory = RuntimeManagerFactory.Factory.get(runtimeEnvironment().getClassLoader());
		RuntimeManagerFactoryBean rmf = new RuntimeManagerFactoryBean();
	    rmf.setType("SINGLETON");
	    rmf.setIdentifier("mortgage-loans-v1");
	    try {
	        rmf.setRuntimeEnvironment((RuntimeEnvironment) runtimeEnvironment()
	                .getObject());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return rmf;
	}

	@Bean
	public RuntimeEngine runtimeEngine(RuntimeManager runtimeManager) {

		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
		return runtimeEngine;
	}

	@Bean
	public KieSession kieSession(RuntimeEngine runtimeEngine) {
		KieSession kieSession = runtimeEngine.getKieSession();

		String eventType = "Error-code";

		SignallingTaskHandlerDecorator signallingTaskWrapper

				= new SignallingTaskHandlerDecorator(ServiceTaskHandler.class, eventType);

		signallingTaskWrapper.setWorkItemExceptionParameterName(ExceptionService.exceptionParameterName);

		kieSession.getWorkItemManager().registerWorkItemHandler("Service Task", signallingTaskWrapper);

		kieSession.getWorkItemManager().registerWorkItemHandler("LoanLimitCheck", new LoanLimitWorkItemHandler());
		kieSession.getWorkItemManager().registerWorkItemHandler("Error-Handling", new ExceptionWorkItemHandler());
		return kieSession;
	}

	@Bean
	public TaskService taskService(RuntimeEngine runtimeEngine) {
		TaskService taskService = runtimeEngine.getTaskService();
		return taskService;
	} 
	
	 
	/*
	 * @Bean public TaskServiceFactoryBean taskServiceFactoryBean() {
	 * TaskServiceFactoryBean tsf = new TaskServiceFactoryBean();
	 * tsf.setEntityManagerFactory(entityManagerFactory().getObject());
	 * tsf.setTransactionManager(getTransactionManager()); return tsf; }
	 */
}