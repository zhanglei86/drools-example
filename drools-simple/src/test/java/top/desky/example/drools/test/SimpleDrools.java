package top.desky.example.drools.test;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import top.desky.example.drools.simple.Person;

@Slf4j
public class SimpleDrools {

    public static void main(final String[] args) {
        // KieServices is the factory for all KIE services
        KieServices ks = KieServices.Factory.get();

        // From the kie services, a container is created from the classpath
        KieContainer kc = ks.getKieClasspathContainer();

        KieSession ksession = kc.newKieSession("SimpleSession");

        ksession.addEventListener(new DebugAgendaEventListener());
        ksession.addEventListener(new DebugRuleRuntimeEventListener());

        Person zhangsan = new Person("张三", (byte) 30);
        ksession.insert(zhangsan);

        // and fire the rules
        int total = ksession.fireAllRules();
        ksession.dispose();

        log.info("执行了 {} 条规则", total);
        log.info("执行后的姓名:{}", zhangsan.getName());
    }

}
