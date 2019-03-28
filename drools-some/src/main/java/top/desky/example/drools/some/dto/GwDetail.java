package top.desky.example.drools.some.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by zealous on 2019-03-27.
 */
@ToString
@Data
public class GwDetail {
    public final static String CONSUME = "P1001";
    public final static String PRESENTED = "P1002";

    private long uid;
    private long gw;
    private String txnType;
    private String opt;
    private String desc;
}
