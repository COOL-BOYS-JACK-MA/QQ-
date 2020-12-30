package util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.text.StrBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InitUtil {
    //js执行引擎
    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
    //直接F12复制你的请求url传进去就行
    public static String uri = "https://user.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds3_html_more";

    //直接F12复制你的请求头传进函数里就行
    public static Map<String, String> headers = InitUtil.getHeaderOrFormDataMap(
            "cookie: pgv_pvi=7347086336; pgv_pvid=1001896312; RK=PqpAHsCHGn; ptcz=0eb06414f594e28d65ebcb72085c371dbb8fd7e0d39e0bf8ab3cbfdad63befc1; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; __Q_w_s__QZN_TodoMsgCnt=1; __Q_w_s_hat_seed=1; ptui_loginuin=1124209551; _qpsvr_localtk=0.5580919956107437; pgv_info=ssid=s7570832874; uin=o1124209551; skey=@fJMIo3xfB; p_uin=o1124209551; pt4_token=7inBc53O*QlyKguRDyUFJnzbwrjcMELFHgYqUBPeYH8_; p_skey=nN9hvKUT*An6tFDEto1SFjFwcjfc9etTKEIOmw7joRw_; Loading=Yes; cpu_performance_v8=42; x-stgw-ssl-info=7299119dc1827669654754cd38f97ed3|0.214|-|1|.|Y|TLSv1.2|ECDHE-RSA-AES128-GCM-SHA256|8000|h2|0\n" +
                    "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
    );


    //直接F12复制你的请求体数据传进去就行
    public static Map<String, String> formData = InitUtil.getHeaderOrFormDataMap(
            "uin: 1124209551\n" +
                    "scope: 0\n" +
                    "view: 1\n" +
                    "daylist: \n" +
                    "uinlist: \n" +
                    "gid: \n" +
                    "flag: 1\n" +
                    "filter: all\n" +
                    "applist: all\n" +
                    "refresh: 0\n" +
                    "aisortEndTime: 0\n" +
                    "aisortOffset: 0\n" +
                    "getAisort: 0\n" +
                    "aisortBeginTime: 0\n" +
                    "pagenum: 2\n" +
                    "externparam: \n" +
                    "firstGetGroup: 0\n" +
                    "icServerTime: 0\n" +
                    "mixnocache: 0\n" +
                    "scene: 0\n" +
                    "begintime: 1609254457\n" +
                    "count: 10\n" +
                    "dayspac: 0\n" +
                    "sidomain: qzonestyle.gtimg.cn\n" +
                    "useutf8: 1\n" +
                    "outputhtmlfeed: 1\n" +
                    "rd: 0.2942543076090369\n" +
                    "usertime: 1609319358471\n" +
                    "windowId: 0.7230321091338092\n" +
                    "g_tk: 430156626\n" +
                    "g_tk: 430156626"
    );


    //返回请求头或请求体；
    public static Map getHeaderOrFormDataMap(String HeaderOrFormData) {
        HashMap<String, String> headers = new HashMap<>();
        for (String s : HeaderOrFormData.split("\n")) {
            if (s.split(":\\s").length == 1) {
                headers.put(s.split(":\\s")[0], "");
                continue;
            }
            String key = s.split(":\\s")[0], val = s.split(":\\s")[1];
            headers.put(key, val);
        }
        return headers;
    }


    //发送post请求
    public static Document postUrl(String uri, Map headers, Map formDatas) throws Exception {
        Connection connect = Jsoup.connect(uri);
        connect.headers(headers);
        connect.data(formDatas);
        // 带参数结束
        Document post = connect.ignoreContentType(true).post();
        return post;
    }


    //发送get请求
    public static Document getUrl(String uri, Map headers, Map formDatas) throws Exception {
        Connection connect = Jsoup.connect(uri);
        connect.headers(headers);
        connect.data(formDatas);
        // 带参数结束
        Document document = connect.ignoreContentType(true).get();
        System.out.println(document);
        return document;
    }


    //设置自定义请求体map,会覆盖部分原请求体的数据；
    public static void setFormData(Map<String, String> changedFormData) {
        //自定义输入data;
        changedFormData.forEach((key, value) -> {
            formData.put(key, value);
        });
    }

    //    传入page和用户qq号，返回说说编号列表；
    public static Set<String> getFids(int page, String uin) throws Exception {
        String uri = "https://user.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds3_html_more";
        HashMap<String, String> map = new HashMap<>();
//        map.put("windowId", String.valueOf(Math.random()));
//        map.put("rd", String.valueOf(Math.random()));
//        map.put("usertime", String.valueOf(System.currentTimeMillis()));
        map.put("pagenum", String.valueOf(page));
        map.put("uin", uin);
        map.put("dayspac", "0");
        setFormData(map);
        Document doc = getUrl(uri, headers, formData);
        Pattern pattern = Pattern.compile("(data-key=\\\\x).{26}");
        Matcher matcher = pattern.matcher(doc.toString());
        StrBuilder strBuilder = new StrBuilder();
        HashSet<String> set = new HashSet<>();
        while (matcher.find()) {
            strBuilder.append(matcher.group());
            strBuilder.replaceAll("data-key=\\x22", "");
            System.out.println("获得文章编号是" + strBuilder.toString());
            set.add(strBuilder.toString());
            strBuilder.clear();
        }
        return set;
    }

    //点赞，完成；
    public static Boolean doLike() {
        String uri1 = "https://user.qzone.qq.com/proxy/domain/w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk=1846091378";
        Map<String, String> zanformData = InitUtil.getHeaderOrFormDataMap(
                "qzreferrer: https://user.qzone.qq.com/1124209551\n" +
                        "opuin: 1124209551\n" +
                        "unikey: http://user.qzone.qq.com/1304060952/mood/1864ba4d4fd3de5fd40b0700\n" +
                        "curkey: http://user.qzone.qq.com/1304060952/mood/1864ba4d4fd3de5fd40b0700\n" +
                        "from: 1\n" +
                        "appid: 311\n" +
                        "typeid: 0\n" +
                        "abstime: 1608438607\n" +
                        "fid: 1864ba4d4fd3de5fd40b0700\n" +
                        "active: 0\n" +
                        "fupdate: 1"
        );
        Boolean accessd = true;
        try {
            System.out.println(postUrl(uri1, headers, zanformData));
            System.out.println("success");
        } catch (Exception e) {
            System.out.println("啊哦，失败啦");
            System.out.println(e.getMessage());
            accessd = false;
        } finally {
            return accessd;
        }
    }

    //node.js方式调用
    @Test
    public void test_1() throws Exception {
        getFids(3, "1124209551");
    }
}
