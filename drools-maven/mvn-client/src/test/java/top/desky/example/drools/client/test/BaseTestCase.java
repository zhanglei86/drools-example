package top.desky.example.drools.client.test;

import com.alibaba.fastjson.JSON;
import org.appformer.maven.integration.embedder.MavenSettings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTestCase {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    private long beginTime;
    private long endTime;
    private static final String MVN_SETTING = "/Volumes/IntelSSD/Dev/apache-maven-3.5.0/conf/settings.xml";

    static {
        System.setProperty(MavenSettings.CUSTOM_SETTINGS_PROPERTY, MVN_SETTING);
    }

    @Before
    public void begin() {
        beginTime = System.currentTimeMillis();
    }

    @After
    public void end() {
        endTime = System.currentTimeMillis();

        System.err.println("");
        System.err.println("#######################################################");
        System.err.println("elapsed time : " + (endTime - beginTime) + "ms");
        System.err.println("#######################################################");
        System.err.println("");
    }

    public void printData(Object data) {
        System.err.println("data ==> " + JSON.toJSONString(data));
    }

    @Test
    public void testCase() {
        System.out.println("base testCase finish!");
    }

}
