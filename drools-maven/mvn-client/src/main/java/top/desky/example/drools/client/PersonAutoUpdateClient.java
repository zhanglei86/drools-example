package top.desky.example.drools.client;

import lombok.extern.slf4j.Slf4j;
import org.appformer.maven.integration.embedder.MavenSettings;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.event.kiescanner.KieScannerEventListener;
import org.kie.api.event.kiescanner.KieScannerStatusChangeEvent;
import org.kie.api.event.kiescanner.KieScannerUpdateResultsEvent;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import top.desky.example.drools.fact.Person;

import java.io.IOException;

/**
 * https://docs.jboss.org/drools/release/7.7.0.Final/drools-docs/html_single/index.html#_maven_versions_and_dependencies
 */
@Slf4j
public class PersonAutoUpdateClient {
    private static final String MVN_SETTING = "/Volumes/IntelSSD/Dev/apache-maven-3.5.0/conf/settings.xml";

    public static void main(final String[] args) {
        System.setProperty(MavenSettings.CUSTOM_SETTINGS_PROPERTY, MVN_SETTING);

        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId("top.desky.example.drools", "mvn-rules", "[0.0.1,)");
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);

        //监控更新
        KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
        kieScanner.addListener(new KieScannerEventListener() {
            @Override
            public void onKieScannerStatusChangeEvent(KieScannerStatusChangeEvent statusChange) {
                //固定间隔时间反复触发
            }

            @Override
            public void onKieScannerUpdateResultsEvent(KieScannerUpdateResultsEvent updateResults) {
                log.info("状态发生变化, 执行一次规则");
                runRule(kieContainer.newKieSession("PersonRules"));
                log.info("按下任何键退出!");
            }
        });
        kieScanner.start(10000L);

        log.info("第一次主动执行:");
        runRule(kieContainer.newKieSession("PersonRules"));
        log.info("测试时，你可以修改 drools-rules 项目，然后 mvn install 到本地仓库来查看变化");
        log.info("按下任何键退出！");
        try {
            System.in.read();
            log.info("退出！");
        } catch (IOException e) {
        }
    }

    public static void runRule(KieSession ksession) {
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
