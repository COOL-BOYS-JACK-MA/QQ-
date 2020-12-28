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
import java.util.Map;

public class InitUtil {
    //js执行引擎
    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
    //直接F12复制你的请求url传进去就行
    public static String uri = "https://user.qzone.qq.com/proxy/domain/w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk=548294653";

    //直接F12复制你的请求头传进函数里就行
    public static Map<String, String> headers = InitUtil.getHeaderOrFormDataMap(
            "authority: user.qzone.qq.com\n" +
                    "method: POST\n" +
                    "path: /proxy/domain/w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk=548294653\n" +
                    "scheme: https\n" +
                    "accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                    "accept-encoding: gzip, deflate, br\n" +
                    "accept-language: zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7\n" +
                    "cache-control: max-age=0\n" +
                    "content-length: 335\n" +
                    "content-type: application/x-www-form-urlencoded\n" +
                    "cookie: pgv_pvi=7672666112; RK=vioAGuC2EF; ptcz=715daf50b4092dd8266c1e891f0a18ebb6c18b2572b627cfb97dce7b3f76991a; pgv_pvid=5736518295; tvfe_boss_uuid=6aee9dab9aff1a20; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; __Q_w_s__QZN_TodoMsgCnt=1; o_cookie=1124209551; pac_uid=1_1124209551; pgv_si=s2738489344; _qpsvr_localtk=0.5018834502867462; uin=o1124209551; skey=@emz40ysoT; p_uin=o1124209551; pt4_token=xlnxqPkr2KU3t8*wMZT7emIRgzK15wa7oA0vIpzBfoI_; p_skey=YqoSpgA-hOl9mED7FpOmR8qcrspcVBX2GVIyFHhDGRc_; Loading=Yes; x-stgw-ssl-info=29b0a7fa68ac0f1d48fd0975a1ba79e7|0.137|-|1|.|Y|TLSv1.2|ECDHE-RSA-AES128-GCM-SHA256|13500|h2|0; pgv_info=ssid=s4212518098; cpu_performance_v8=2; rv2=80CFBB060AC12287AFCA42AA2DDF4FBEA0EE7D4CADEC56704C; property20=75EC2D64C9AE5167BDB19B9DD4B10346515FC042D66A1732AE6BAAEA2FB4066BFCBC3367443C2438\n" +
                    "origin: https://user.qzone.qq.com\n" +
                    "referer: https://user.qzone.qq.com/1124209551\n" +
                    "sec-fetch-dest: iframe\n" +
                    "sec-fetch-mode: navigate\n" +
                    "sec-fetch-site: same-origin\n" +
                    "sec-fetch-user: ?1\n" +
                    "upgrade-insecure-requests: 1\n" +
                    "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
    );


    //直接F12复制你的请求体数据传进去就行
    public static Map<String, String> formData = InitUtil.getHeaderOrFormDataMap(
            "qzreferrer: https://user.qzone.qq.com/1124209551\n" +
                    "opuin: 1124209551\n" +
                    "unikey: http://user.qzone.qq.com/1173290080/mood/60fcee453148e85f18a80100\n" +
                    "curkey: http://user.qzone.qq.com/1173290080/mood/60fcee453148e85f18a80100\n" +
                    "from: 1\n" +
                    "appid: 311\n" +
                    "typeid: 0\n" +
                    "abstime: 1609058353\n" +
                    "fid: 60fcee453148e85f18a80100\n" +
                    "active: 0\n" +
                    "fupdate: 1"
    );


    //返回headers请求体；
    public static Map getHeaderOrFormDataMap(String HeaderOrFormData) {
        HashMap<String, String> headers = new HashMap<>();
        for (String s : HeaderOrFormData.split("\n")) {
            headers.put(s.split(":\\s")[0], s.split(":\\s")[1]);
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

    @Test
    public void test() throws Exception {
        engine.eval(new FileReader(System.getProperty("user.dir") + File.separator + "js" + File.separator + "method.js"));
        Invocable invoke = (Invocable) engine;    // 调用merge方法，并传入两个参数
        Object c = (Object) invoke.invokeFunction("stack_1", "https://user.qzone.qq.com/proxy/domain/boss.qzone.qq.com/fcg-bin/fcg_get_multiple_strategy?uin=1124209551&board_id=2420&need_cnt=65536");
    }

}
