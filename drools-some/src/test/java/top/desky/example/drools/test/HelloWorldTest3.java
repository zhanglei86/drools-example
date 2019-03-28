package top.desky.example.drools.test;

import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.junit.Test;
import org.kie.api.event.rule.*;
import top.desky.example.drools.some.BaseCommon;

/**
 * Created by zealous on 2019-03-27.
 */
public class HelloWorldTest3 {

    /**
     * simple
     */
    @Test
    public void test1() {
        new Hw("helloSession").execute();
    }

    /**
     * AgendaFilter
     */
    @Test
    public void test2() {
        new Hw("helloSession").execute(new RuleNameStartsWithAgendaFilter("hello"));
    }

    /**
     * listener
     */
    @Test
    public void test3() {
        new Hw("helloSession").listenerTest();
    }

}

class Hw extends BaseCommon {
    public Hw(String kSessionName) {
        super(kSessionName);
    }

    /**
     * listener测试
     * 例子有:RuleRuntimeEventListener, AgendaEventListener.
     */
    public void listenerTest() {
        kSession.addEventListener(new AgendaEventListener() {
            @Override
            public void matchCreated(MatchCreatedEvent event) {
                printInfo("matchCreated");
            }

            @Override
            public void matchCancelled(MatchCancelledEvent event) {
                printInfo("matchCancelled");
            }

            @Override
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
                printInfo("beforeRuleFlowGroupDeactivated");
            }

            @Override
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
                printInfo("beforeRuleFlowGroupActivated");
            }

            @Override
            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                printInfo("beforeMatchFired");
            }

            @Override
            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
                printInfo("agendaGroupPushed");
            }

            @Override
            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
                printInfo("agendaGroupPopped");
            }

            @Override
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
                printInfo("afterRuleFlowGroupDeactivated");
            }

            @Override
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
                printInfo("afterRuleFlowGroupActivated");
            }

            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                printInfo("afterMatchFired");
            }
        });
        execute();
    }
}