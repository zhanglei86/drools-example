package top.desky.example.drools.some.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zealous on 2019-03-27.
 */
@Builder
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

    public static List<GwDetail> genGws() {
        GwDetail p1 = GwDetail.builder().uid(11).gw(13).txnType(GwDetail.CONSUME).build();
        GwDetail p2 = GwDetail.builder().uid(22).gw(23).txnType(GwDetail.PRESENTED).build();
        GwDetail p3 = GwDetail.builder().uid(33).gw(33).txnType(GwDetail.CONSUME).build();
        return Arrays.asList(p1, p2, p3);
    }

}
