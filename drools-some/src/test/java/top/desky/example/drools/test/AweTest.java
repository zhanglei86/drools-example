package top.desky.example.drools.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import top.desky.example.drools.some.BaseCommon;
import top.desky.example.drools.some.dto.GwDetail;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zealous on 2019-03-28.
 */
@Slf4j
public class AweTest {

    @Test
    public void testFact() {
        new Awe("factHandleSession").testFact();
    }

    @Test
    public void testFunction() {
        new Awe("functionSession").execute();
    }

    @Test
    public void testGlobal() {
        new Awe("globalSession").testGlobal();
    }

    // FIXME 死循环了。。
    //@Test
    public void testInternally() {
        new Awe("internallySession").test1();
    }

    @Test
    public void testQuery() {
        new Awe("querySession").testQuery();
    }

    @Test
    public void testKieFileSystem() {
        final String kBaseName = "FileSystemKBase";
        final String kSessionName = "FileSystemKSession";

        KieServices kieServices = KieServices.Factory.get();
        KieResources resources = kieServices.getResources();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        KieBaseModel baseModel = kieModuleModel.newKieBaseModel(kBaseName).addPackage("drl");
        baseModel.newKieSessionModel(kSessionName);
        KieFileSystem fileSystem = kieServices.newKieFileSystem();

        String xml = kieModuleModel.toXML();
        log.info("\n输出xml文件是==>\n{}", xml);
        fileSystem.writeKModuleXML(xml);
        fileSystem.write("src/main/resources/drl/rule.drl", resources.newClassPathResource("drl/kiefile/kfs.drl"));

        KieBuilder kb = kieServices.newKieBuilder(fileSystem);
        kb.buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
        KieContainer kContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        log.info(kContainer.getKieBase(kBaseName).toString());
        KieSession kSession = kContainer.newKieSession(kSessionName);
        kSession.fireAllRules();
    }

}

class Awe extends BaseCommon {
    public Awe(String kSessionName) {
        super(kSessionName);
    }

    public void testFact() {
        GwDetail p = GwDetail.builder().uid(11).gw(13).txnType(GwDetail.CONSUME).build();
        FactHandle factHandle = insertObject(p);
        printInfo(factHandle.toExternalForm());
        execute();
        p.setGw(15);
        kSession.getAgenda().getAgendaGroup("fact-handler-group").setFocus();
        kSession.update(factHandle, p);
        execute();
    }

    public void testGlobal() {
        setGlobal("strParam", "测试参数");
        setGlobal("listParam", Arrays.asList(new String[]{"test1", "test2"}));
        execute();
    }

    public void test1() {
        List<GwDetail> list = GwDetail.genGws();
        list.forEach(gw -> insertObject(gw));
        execute();
    }

    public void testQuery() {
        List<GwDetail> list = GwDetail.genGws();
        list.forEach(gw -> insertObject(gw));

        queryResult("query-1", "gwDetail");
        queryResult("query-2", "gwDetail");
        queryResult("query-3", "gwDetail");
    }

}
