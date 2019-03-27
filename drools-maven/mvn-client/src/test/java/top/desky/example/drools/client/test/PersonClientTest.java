package top.desky.example.drools.client.test;

import org.junit.Test;
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

import java.util.concurrent.TimeUnit;

/**
 * Created by zealous on 2019-03-27.
 */
public class PersonClientTest extends BaseTestCase {

    /**
     * simple
     */
    @Test
    public void test1() {
        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId("top.desky.example.drools", "mvn-rules", "0.0.1");
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);

        KieSession ksession = kieContainer.newKieSession("PersonRules");
        runRule(ksession);
    }

    /**
     * auto update
     * https://docs.jboss.org/drools/release/7.7.0.Final/drools-docs/html_single/index.html#_maven_versions_and_dependencies
     */
    @Test
    public void test2() throws Exception {
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
        log.warn("睡5分钟后退出！");
        TimeUnit.SECONDS.sleep(10);
    }

    private void runRule(KieSession ksession) {
        ksession.addEventListener(new DebugAgendaEventListener());
        ksession.addEventListener(new DebugRuleRuntimeEventListener());

        Person zhang3 = new Person("张三", (byte) 30);
        ksession.insert(zhang3);

        // and fire the rules
        int total = ksession.fireAllRules();
        ksession.dispose();

        log.info("执行了 {} 条规则", total);
        log.info("执行后的姓名:{}", zhang3.getName());
    }

}
