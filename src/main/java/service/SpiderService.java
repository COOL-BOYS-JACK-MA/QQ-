package service;

import org.junit.Test;
import util.InitUtil;

public class SpiderService {
    private String cookie = InitUtil.getValueByKey("cookie");
    public String g_tk;

    //开始
    public void start() {

    }


    @Test
    public void test_1() {
        System.out.println(String.valueOf(System.currentTimeMillis()).substring(0,10));
    }
}
