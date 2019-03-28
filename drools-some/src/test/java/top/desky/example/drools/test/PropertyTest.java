package top.desky.example.drools.test;

import org.junit.Test;
import top.desky.example.drools.some.BaseCommon;
import top.desky.example.drools.some.dto.GwDetail;

import java.util.List;

/**
 * Created by zealous on 2019-03-28.
 */
public class PropertyTest {

    /**
     * 该属性的作用是将若干个规则划分成一个组，用一个字符串来给这个组命名，
     * 这样在执行的时候，具有相同 activation-group 属性的规则中只要有一个被执行，其它的规则都将不再执行
     */
    @Test
    public void testActivationGroup() {
        new Pty("activationGroupSession").execute();
    }

    /**
     * 1. 如果没有指定agenda-group 则默认把所有未指定agenda-group的 rules 都执行一遍
     * 2. 如果指定了agenda-group 使用的时候必须指定该name才能被使用，默认是不能使用的
     * 3. agenda-group name可以重复
     * 4. agenda-group 用于区分rule
     */
    @Test
    public void testAgendaGroup() {
        new Pty("agendaGroupSession").testAgendaGroup();
    }

    @Test
    public void testDateEffectiveFormat() {
        System.setProperty("drools.dateformat", "yyyy-MM-dd");
        new Pty("dateEffectiveFormatSession").execute();
    }

    /**
     * 规则的生效时间
     * 在规则运行时，引擎会自动拿当前操作系统的时候与date-effective设置的时间值进行比对，
     * 只有当系统时间>=date-effective设置的时间值时，规则才会触发执行，否则执行将不执行。
     */
    // FIXME 单独运行ok，批量运行报错，时间格式问题
    //@Test
    public void testDateEffective() {
        System.setProperty("drools.dateformat", "dd-MMM-yyyy");
        new Pty("dateEffectiveSession").execute();
    }

    /**
     * 规则的失效时间
     * 该属性的作用与 date-effective 属性恰恰相反（注：当前系统时间<date-expires 值），
     * 如果大于系统时间，那么规则就执行，否则就不执行。
     * 该属性的值同样也是一个日期类型，默认格式也是“dd-MMM-yyyy”，
     */
    @Test
    public void testDateExpires() {
        System.setProperty("drools.dateformat", "yyyy-MM-dd");
        new Pty("dateExpiresSession").execute();
    }

    /**
     * 设置DRL文件开始执行之后延迟多长时间开始执行这条规则, long型，无默认值
     */
    @Test
    public void testDuration() {
        new Pty("durationSession").execute();
    }

    /**
     * enabled 属性比较简单，它是用来定义一个规则是否可用的。
     * 该属性的值是一个布尔值， 默认该属性的值为 true，表示规则是可用的，
     * 如果手工设置其 enabled属性值为 false，那么引擎就不会执行该规则。
     */
    @Test
    public void testEnabled() {
        new Pty("enabledSession").execute();
    }

    /**
     * 规则可能会被其它规则调用而反复执行；
     * 将lock-on-active置为true可以强制使规则在任何条件下都只会执行一次，
     * 可视为no-loop的加强版
     * boolean型，默认值为false
     */
    @Test
    public void testLockOnActive() {
        new Pty("lockOnActiveSession").test1();
    }

    /**
     * 定义当前的规则是否不允许多次循环执行，默认是 false，
     * 也就是当前的规则只要满足条件，可以无限次执行
     */
    @Test
    public void testNoLoop() {
        new Pty("noLoopSession").test1();
    }

    @Test
    public void testRuleFlowGroup() {
        new Pty("ruleFlowGroupSession").execute();
    }

    /**
     * 作用是用来设置规则执行的优先级，
     * salience 属性的值是一个数字，数字越大执行优先级越高，同时它的值可以是一个负数。
     * 默认情况下，规则的 salience 默认值为 0
     */
    @Test
    public void testSalience() {
        new Pty("salienceSession").execute();
    }
}

class Pty extends BaseCommon {
    public Pty(String kSessionName) {
        super(kSessionName);
    }

    public void testAgendaGroup() {
        kSession.getAgenda().getAgendaGroup("agendaGroup3").setFocus();
        execute();
    }

    public void test1() {
        List<GwDetail> list = GwDetail.genGws();
        list.forEach(gw -> insertObject(gw));
        execute();
    }

}
