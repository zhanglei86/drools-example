package top.desky.example.drools.test;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by zealous on 2019-03-27.
 */
public class HelloWorldTest1 {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("helloSession");
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
