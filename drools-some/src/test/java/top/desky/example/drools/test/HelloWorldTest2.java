package top.desky.example.drools.test;

import top.desky.example.drools.some.BaseCommon;

/**
 * Created by zealous on 2019-03-27.
 */
public class HelloWorldTest2 extends BaseCommon {

    private HelloWorldTest2(String kSessionName) {
        super(kSessionName);
    }

    public static void main(String[] args) {
        new HelloWorldTest2("helloSession").execute();
    }
}
