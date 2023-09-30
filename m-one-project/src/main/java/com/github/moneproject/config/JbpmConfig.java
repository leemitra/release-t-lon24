package com.github.moneproject.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.spring.factorybeans.RuntimeEnvironmentFactoryBean;
import org.kie.spring.factorybeans.RuntimeManagerFactoryBean;
import org.kie.spring.factorybeans.TaskServiceFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JbpmConfig {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private PlatformTransactionManager tm;

    @Bean
    public RuntimeEnvironmentFactoryBean runtimeEnvironment() {

        RuntimeEnvironmentFactoryBean ref = new RuntimeEnvironmentFactoryBean();

        ref.setType("DEFAULT");
        ref.setEntityManagerFactory(emf);
        ref.setTransactionManager(tm);
        ref.setUserGroupCallback(getUserCallBack());
        // ref.setKbaseName("kbase");
        ref.setAssets(getAssets());
        return ref;

    }

    @Bean(name = "runtimeManager", destroyMethod = "close")
    public RuntimeManagerFactoryBean runtimeManager() {
        RuntimeManagerFactoryBean rmf = new RuntimeManagerFactoryBean();
        rmf.setIdentifier("spring-rm");
        try {
            rmf.setRuntimeEnvironment((RuntimeEnvironment) runtimeEnvironment()
                    .getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rmf.setType("PER_PROCESS_INSTANCE");
        return rmf;
    }

    @Bean
    public TaskServiceFactoryBean taskServiceFactoryBean() {
        TaskServiceFactoryBean tsf = new TaskServiceFactoryBean();
        tsf.setEntityManagerFactory(emf);
        tsf.setTransactionManager(tm);
        return tsf;
    }

    @Bean
    public RuntimeEngine getRuntimeEngine(RuntimeManager runtimeManager) {
        return runtimeManager.getRuntimeEngine(EmptyContext.get());
    }
    /*
     * @Bean
     * public RuntimeEngine runtimeEngine(RuntimeManager runtimeManager) {
     * 
     * RuntimeEngine runtimeEngine =
     * runtimeManager.getRuntimeEngine(EmptyContext.get());
     * return runtimeEngine;
     * }
     */

    private Map<Resource, ResourceType> getAssets() {
        Map<Resource, ResourceType> assets = new HashMap<Resource, ResourceType>();
        assets.put(ResourceFactory
                .newClassPathResource("jbpm/processes/sample.bpmn"),
                ResourceType.BPMN2);
        assets.put(ResourceFactory.newClassPathResource("jbpm/processes/bankLoadProcess.bpmn2"), ResourceType.BPMN2);
        return assets;
    }

    @Bean
    public UserGroupCallback getUserCallBack() {
        return new CustomUserGroupCallback();
    }

}
