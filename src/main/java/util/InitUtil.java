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


    public static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("user-agent", ArgsProPerties.user_agent);
    }

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


    //根据node.js获取token,有java实现的版本,弃用
//    public static String getToken(String cookie) {
//        String g_tk = null;
//        try {
//            String uri = "http://localhost:8083/getGtk";
//            Connection connect = Jsoup.connect(uri);
//            HashMap<String, String> map = new HashMap<>();
//            map.put("cookie", cookie);
//            connect.data(map);
//            Document post = connect.ignoreContentType(true).post();
//            g_tk = post.body().text();
//            System.out.println("成功运算出token->" + g_tk);
//        } catch (Exception e) {
//            System.out.println("出错啦，请重试");
//            System.out.println("错误信息->" + e.getMessage());
//        } finally {
//            return g_tk;
//        }
//    }

    //点赞说说,成功返回true;
    public static Boolean doLike(String qq, String fid, String g_tk) {
        String uri1 = "https://user.qzone.qq.com/proxy/domain/w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk=" + g_tk;
        Map<String, String> zanformData = InitUtil.getHeaderOrFormDataMap(
                "qzreferrer: https://user.qzone.qq.com/" + qq + "\n" +
                        "opuin: " + qq + "\n" +
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

    //返回qq好友列表
    public static List getQQNumberList(String qq, String g_tk) {
        List list = null;
        Map<String, String> formData = InitUtil.getHeaderOrFormDataMap(
                "uin: " + qq + "\n" +
                        "follow_flag: 1\n" +
                        "groupface_flag: 0\n" +
                        "fupdate: 1\n" +
                        "g_tk: " + g_tk + "\n" +
                        "g_tk: " + g_tk + "\n"
        );
        try {
            Document data = getUrl("https://user.qzone.qq.com/proxy/domain/r.qzone.qq.com/cgi-bin/tfriend/friend_show_qqfriends.cgi",
                    headers, formData);
            System.out.println("qq列表获取成功");
            JSONObject jsonObject = JSONObject.parseObject(data.body().text());
            list = (List) jsonObject.getJSONObject("data").get("items");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    //获取说说列表
    public static String getArticleNumberLis(String tarQQ, String selfQQ, String g_tk) {
        String number = null;
        Map<String, String> formData = InitUtil.getHeaderOrFormDataMap(
                "g_iframeUser: 1\n" +
                        "i_uin: " + tarQQ + "\n" +
                        "i_login_uin: " + selfQQ + "\n" +
                        "mode: 4\n" +
                        "previewV8: 1\n" +
                        "style: 35\n" +
                        "version: 8\n" +
                        "needDelOpr: true\n" +
                        "transparence: true\n" +
                        "hideExtend: false\n" +
                        "showcount: 5\n" +
                        "MORE_FEEDS_CGI: http://ic2.s8.qzone.qq.com/cgi-bin/feeds/feeds_html_act_all\n" +
                        "refer: 2\n" +
                        "paramstring: os-winxp|100"
        );
        try {
            Document data = getUrl("tps://user.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds_html_module", headers, formData);
            String text = data.body().text();
            int sta = text.indexOf("key:'") + "key:'".length();
            number = text.substring(sta, sta + "3a3bc5cc4833e45f3bef0500".length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return number;
        }
    }
}
