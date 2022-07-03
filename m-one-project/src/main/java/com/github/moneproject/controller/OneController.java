package com.github.moneproject.controller;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OneController {

    @Autowired
    private RuntimeManager runtimeManager;
 

    @GetMapping("/")
    public String getHome(){
        RuntimeEngine engine= runtimeManager.getRuntimeEngine(EmptyContext.get());
        KieSession kieSession= engine.getKieSession();
        ProcessInstance instance =  kieSession.startProcess("com.example.sample");
        System.out.println(instance.getId());
        runtimeManager.disposeRuntimeEngine(engine);
        kieSession.dispose();
        return "hello world";
    }
    
}
