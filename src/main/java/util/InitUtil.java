package util;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
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
    public static String uri = "https://fanyi.baidu.com/v2transapi?from=en&to=zh";

    //直接F12复制你的请求头传进函数里就行
    public static Map<String, String> headers = InitUtil.getHeaderOrFormDataMap(
            "accept: */*\n" +
                    "accept-encoding: gzip, deflate, br\n" +
                    "accept-language: zh-CN,zh;q=0.9,zh-TW;q=0.8,en;q=0.7\n" +
                    "content-length: 136\n" +
                    "content-type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                    "cookie: PSTM=1603618879; BIDUPSID=C2E1C2CA5C18AC3906A61FF7AE4D5B5D; BAIDUID=F54464E97D15B88A9339D44E56354EE4:FG=1; BDUSS=WZtSUxyWDB0d1NGTVR4aHRFdnk2eEtIdi01UlRIVDItZHRFbnB2blZ0MGZWdHhmSVFBQUFBJCQAAAAAAAAAAAEAAADkGp3Bt6K36LXEOTC688zs0KsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB~JtF8fybRfU; BDUSS_BFESS=WZtSUxyWDB0d1NGTVR4aHRFdnk2eEtIdi01UlRIVDItZHRFbnB2blZ0MGZWdHhmSVFBQUFBJCQAAAAAAAAAAAEAAADkGp3Bt6K36LXEOTC688zs0KsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB~JtF8fybRfU; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; __yjs_duid=1_b596c2b33e79d5b6d2576a0a409355fc1608902698192; BAIDUID_BFESS=F54464E97D15B88A9339D44E56354EE4:FG=1; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; delPer=0; PSINO=1; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1609038952; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1609038952; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; yjs_js_security_passport=313e2461c6c60229a0e741401a62cc9f79c75519_1609038963_js; H_PS_PSSID=1442_33241_33306_31254_32972_33285_33286_33350_33313_33312_33169_33311_33310_33309_33319_33308_33307_33145_33381_33370; BA_HECTOR=8k8400202l208g0gbh1fufvbs0q\n" +
                    "origin: https://fanyi.baidu.com\n" +
                    "referer: https://fanyi.baidu.com/?aldtype=16047\n" +
                    "sec-fetch-dest: empty\n" +
                    "sec-fetch-mode: cors\n" +
                    "sec-fetch-site: same-origin\n" +
                    "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36\n" +
                    "x-requested-with: XMLHttpRequest"
    );


    //直接F12复制你的请求体数据传进去就行
    public static Map<String, String> formData = InitUtil.getHeaderOrFormDataMap(
            "from: en\n" +
                    "to: zh\n" +
                    "query: cat\n" +
                    "transtype: realtime\n" +
                    "simple_means_flag: 3\n" +
                    "sign: 738505.1026040\n" +
                    "token: 7980626369566ce5a31851d528f25bb0\n" +
                    "domain: common"
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
    public static JSONObject postUrl(String uri, Map headers, Map formDatas) throws Exception {
        Connection connect = Jsoup.connect(uri);
        connect.headers(headers);
        connect.data(formData);
        // 带参数结束
        JSONObject jsonObject = JSONObject.parseObject(connect.ignoreContentType(true).post().body().text());
        System.out.println("post请求响应打印" + jsonObject);
        return jsonObject;
    }

    //设置自定义请求体
    public static void setFormData(Map<String, String> changedFormData) {
        //自定义输入data;
        changedFormData.forEach((key, value) -> {
            formData.put(key, value);
        });
    }

    public static String getSalt(String s) throws Exception {
        engine.eval(new FileReader(System.getProperty("user.dir") + File.separator + "js" + File.separator + "baiDuFanYi.js"));
        Invocable invoke = (Invocable) engine;    // 调用merge方法，并传入两个参数
        Object c = (Object) invoke.invokeFunction("e", s);
        return c.toString();
    }

    @Test
    public void test() throws Exception {

    }


}
