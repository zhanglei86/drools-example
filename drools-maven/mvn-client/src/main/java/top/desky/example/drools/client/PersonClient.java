package top.desky.example.drools.client;

import lombok.extern.slf4j.Slf4j;
import org.appformer.maven.integration.embedder.MavenSettings;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import top.desky.example.drools.fact.Person;

@Slf4j
public class PersonClient {
    private static final String MVN_SETTING = "/Volumes/IntelSSD/Dev/apache-maven-3.5.0/conf/settings.xml";

    public static void main(final String[] args) {
        System.setProperty(MavenSettings.CUSTOM_SETTINGS_PROPERTY, MVN_SETTING);

        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId("top.desky.example.drools", "mvn-rules", "0.0.1");
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);

        KieSession ksession = kieContainer.newKieSession("PersonRules");

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
