package test;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import util.InitUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//测试主函数
public class Main {


    public static void main(String[] args) throws Exception {
        HashMap<String, String> data = new HashMap<>();
//        String s = "flash";
//        data.put("query", s);
//        data.put("sign", InitUtil.getSalt(s));
//        InitUtil.setFormData(data);
        InitUtil.postUrl(InitUtil.uri, InitUtil.headers, InitUtil.formData);
    }

    @Test
    public void test_1() {

    }
}
