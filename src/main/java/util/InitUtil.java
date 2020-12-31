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
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InitUtil {
    //直接F12复制你的请求头传进函数里就行
    public static Map<String, String> headers = new HashMap<>();

    //根据浏览器复制来的数据返回请求头或请求体工具类
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

    //    传入page和用户qq号，返回说说编号列表；1次获取6条，通过循环，自己设置获取的条数
    public static Set<String> getFids(String qq, int page, String g_tk) throws Exception {
        String uri = "https://user.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds3_html_more";
        Map<String, String> formData = InitUtil.getHeaderOrFormDataMap(
                "uin: " + qq + "\n" +
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
                        "pagenum: 3\n" +
                        "externparam: \n" +
                        "firstGetGroup: 0\n" +
                        "icServerTime: 0\n" +
                        "mixnocache: 0\n" +
                        "scene: 0\n" +
                        "begintime: " + "1609325151" + "\n" +
                        "count: 10\n" +
                        "dayspac: 0\n" +
                        "sidomain: qzonestyle.gtimg.cn\n" +
                        "useutf8: 1\n" +
                        "outputhtmlfeed: 1\n" +
                        "rd: 0.31904723711117655\n" +
                        "usertime: " + System.currentTimeMillis() + "\n" +
                        "windowId: 0.6744105566418135\n" +
                        "g_tk: " + g_tk + "\n" +
                        "g_tk: " + g_tk + "1846091378"
        );
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

    //点赞功能；
    public static Boolean doLike(String qq, String fid, String g_tk) {
        String uri1 = "https://user.qzone.qq.com/proxy/domain/w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk=" + g_tk;
        Map<String, String> zanformData = InitUtil.getHeaderOrFormDataMap(
                "qzreferrer: https://user.qzone.qq.com/" + qq + "\n" +
                        "opuin: 1124209551\n" +
                        "unikey: http://user.qzone.qq.com/1304060952/mood/" + fid + "\n" +
                        "curkey: http://user.qzone.qq.com/1304060952/mood/" + fid + "\n" +
                        "from: 1\n" +
                        "appid: 311\n" +
                        "typeid: 0\n" +
                        "abstime: " + System.currentTimeMillis() + "\n" +
                        "fid: " + fid + "\n" +
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

    //文件读取根据Key读取Value
    public static String getValueByKey(String key) {
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + File.separator + "configure" + File.separator + "paramter.properties"));
            pps.load(in);
            String value = pps.getProperty(key);
            System.out.println(key + " = " + value);
            return value;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取token
    public static String getToken(String cookie) {
        String g_tk = null;
        try {
            String uri = "http://localhost:8083/getGtk";
            Connection connect = Jsoup.connect(uri);
            HashMap<String, String> map = new HashMap<>();
            map.put("cookie", cookie);
            connect.data(map);
            Document post = connect.ignoreContentType(true).post();
            g_tk = post.body().text();
            System.out.println("成功运算出token->" + g_tk);
        } catch (Exception e) {
            System.out.println("出错啦，请重试");
            System.out.println("错误信息->" + e.getMessage());
        } finally {
            return g_tk;
        }
    }
}
