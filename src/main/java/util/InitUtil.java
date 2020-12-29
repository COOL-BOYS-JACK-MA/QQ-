package util;

import com.alibaba.fastjson.JSONObject;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InitUtil {
    //js执行引擎
    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
    //直接F12复制你的请求url传进去就行
    public static String uri = "https://user.qzone.qq.com/proxy/domain/w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk=548294653";

    //直接F12复制你的请求头传进函数里就行
    public static Map<String, String> headers = InitUtil.getHeaderOrFormDataMap(
            "cookie: pgv_pvi=7347086336; pgv_pvid=1001896312; RK=PqpAHsCHGn; ptcz=0eb06414f594e28d65ebcb72085c371dbb8fd7e0d39e0bf8ab3cbfdad63befc1; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; __Q_w_s__QZN_TodoMsgCnt=1; Loading=Yes; __layoutStat=29; __Q_w_s_hat_seed=1; ptui_loginuin=1124209551; _qpsvr_localtk=0.6984470103132014; pgv_info=ssid=s8457721243; uin=o1124209551; skey=@6ug8cVCji; p_uin=o1124209551; pt4_token=G8ECSUEfCdR9QqR8Sx3aWoFmKGpOw3SDvsa-zLO4OrQ_; p_skey=JdIZ6nFY0PUplRUUGiX256g-sc1P4Jo7RRVIUnTuUeU_; x-stgw-ssl-info=e0460cd5991ed72d569ec1e7ab98f4aa|0.134|-|1|.|Y|TLSv1.2|ECDHE-RSA-AES128-GCM-SHA256|13500|h2|0; cpu_performance_v8=33\n" +
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
                    "pagenum: 46\n" +
                    "externparam: basetime=1607069905&pagenum=46&dayvalue=1&getadvlast=0&hasgetadv=&lastentertime=0&LastAdvPos=0&UnReadCount=0&UnReadSum=363&LastIsADV=0&UpdatedFollowUins=&UpdatedFollowCount=0&LastRecomBrandID=&TRKPreciList=\n" +
                    "firstGetGroup: 0\n" +
                    "icServerTime: 0\n" +
                    "mixnocache: 0\n" +
                    "scene: 0\n" +
                    "begintime: 1607069905\n" +
                    "count: 10\n" +
                    "dayspac: 1\n" +
                    "sidomain: qzonestyle.gtimg.cn\n" +
                    "useutf8: 1\n" +
                    "outputhtmlfeed: 1\n" +
                    "rd: 0.7871806806906332\n" +
                    "usertime: 1609234899712\n" +
                    "windowId: 0.8886017413904825\n" +
                    "g_tk: 1456495607\n" +
                    "g_tk: 1456495607"
    );


    //返回headers请求体；
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
    public static Boolean postUrl(String uri, Map headers, Map formDatas) throws Exception {
        Connection connect = Jsoup.connect(uri);
        connect.headers(headers);
        connect.data(formData);
        // 带参数结束
        Element body = connect.ignoreContentType(true).post().body();
        System.out.println(body);
        return (body == null || body.equals("")) ? false : true;
    }

    //设置自定义请求体
    public static void setFormData(Map<String, String> changedFormData) {
        //自定义输入data;
        changedFormData.forEach((key, value) -> {
            formData.put(key, value);
        });
    }

    public static String getSalt(String s) throws Exception {
        engine.eval(new FileReader(System.getProperty("user.dir") + File.separator + "js" + File.separator + "method.js"));
        Invocable invoke = (Invocable) engine;    // 调用merge方法，并传入两个参数
        Object c = (Object) invoke.invokeFunction("e", s);
        return c.toString();
    }


    public static List getFids(int page,int uin) throws Exception {
        String uri = "https://user.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds3_html_more";
        Connection connect = Jsoup.connect(uri);
        HashMap<String, String> map = new HashMap<>();
        map.put("windowId", String.valueOf(Math.random()));
        map.put("rd", String.valueOf(Math.random()));
        map.put("externparam", "");
        map.put("usertime", String.valueOf(System.currentTimeMillis()));
        connect.data(map);
        Document post = connect.ignoreContentType(true).get();
        System.out.println("success" + post.body());
        return null;
    }


    //js引擎方式调用
    @Test
    public void test() throws Exception {
        engine.eval(new FileReader(System.getProperty("user.dir") + File.separator + "js" + File.separator + "method.js"));
        Invocable invoke = (Invocable) engine;    // 调用merge方法，并传入两个参数
        Object c = (Object) invoke.invokeFunction("stack_1", "https://user.qzone.qq.com/proxy/domain/boss.qzone.qq.com/fcg-bin/fcg_get_multiple_strategy?uin=1124209551&board_id=2420&need_cnt=65536");
    }

    //node.js方式调用
    @Test
    public void test_1() throws Exception {

    }
}
