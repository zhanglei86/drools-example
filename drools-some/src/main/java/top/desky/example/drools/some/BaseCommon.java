package top.desky.example.drools.some;

import com.alibaba.fastjson.JSON;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.desky.example.drools.some.dto.GwDetail;

import java.util.List;

public class BaseCommon {
    protected static Logger logger;

    protected KieSession kSession;

    public BaseCommon(String kSessionName) {
        logger = LoggerFactory.getLogger(this.getClass());
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        kSession = kContainer.newKieSession(kSessionName);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void printInfo(Object info) {
        logger.info(JSON.toJSONString(info));
    }

    public static void printInfo(String info) {
        logger.info(info);
    }

    protected FactHandle insertObject(Object obj) {
        return kSession.insert(obj);
    }

    protected void insertObjectList(List<?> objList) {
        if (objList.isEmpty()) {
            return;
        }
        for (Object obj : objList) {
            kSession.insert(obj);
        }
    }

    protected void setGlobal(String identifier, Object value) {
        kSession.setGlobal(identifier, value);
    }

    public void execute() {
        try {
            int count = kSession.fireAllRules();
            printInfo("触发规则数：" + count);
        } catch (Throwable t) {
            logger.error("执行规则出错：", t);
        }
    }

    public void execute(AgendaFilter agendaFilter) {
        try {
            int count = kSession.fireAllRules(agendaFilter);
            printInfo("触发规则数：" + count);
        } catch (Throwable t) {
            logger.error("执行规则出错：", t);
        }
    }

    protected void queryResult(String queryName, String identifierName) {
        QueryResults results = kSession.getQueryResults(queryName, GwDetail.CONSUME);
        printInfo(queryName + "查询结果条数：" + results.size());
        for (QueryResultsRow row : results) {
            GwDetail product = (GwDetail) row.get(identifierName);
            printInfo(product);
        }
    }
}
