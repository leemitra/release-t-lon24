package com.groupproject.moneproject;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AppDatasourceConfig {

	@Primary
	@Bean("appDataSource")
	public DataSource getAppDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		/*dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mysql://localhost:3306/sms_app?serverTimezone=UTC");
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("root");*/
		dataSourceBuilder.driverClassName("org.h2.Driver");
		dataSourceBuilder.url("jdbc:h2:mem:testapp");
		dataSourceBuilder.username("sa");
		dataSourceBuilder.password("sa");
		return dataSourceBuilder.build();
	}
	
	@Primary
    @Bean(name = "appEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(getAppDataSource());
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setPackagesToScan("com.groupproject.moneproject.model");
        emf.setPersistenceUnitName("default");   // <- giving 'default' as name
       // emf.afterPropertiesSet();
        emf.setJpaProperties(additionalProperties());
        return emf;
    }
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.max_fetch_depth", "3");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		//properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		return properties;
	}
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        return tm;
    }
}
