package util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.text.StrBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InitUtil {

    //请求头
    public static Map<String, String> headers = new HashMap<>();
    //异常处理类
    public static UnexpectedOfSelfException excep = new UnexpectedOfSelfException();
    //统计重复点赞说说ID
    public static Map<String, String> repeatTitleMap = new HashMap<>();

    static {
        headers.put("user-agent", ArgsProPerties.user_agent);
        headers.put("cookie", ArgsProPerties.cookie);
        File file = new File(System.getProperty("user.dir") + File.separator + "properties");
        try {
            for (File listFile : file.listFiles()) {
                if (listFile.getName().equals("cunDang.txt")) {
                    repeatTitleMap = read();
                    break;
                }
            }
        } catch (Exception e) {
            excep.doSomething();
            e.printStackTrace();
        }
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
    public static Document postUrl(String uri, Map headers, Map formDatas) {
        Document post = null;
        try {
            Connection connect = Jsoup.connect(uri);
            connect.headers(headers);
            connect.data(formDatas);
            // 带参数结束
            post = connect.ignoreContentType(true).post();
            return post;
        } catch (Exception e) {
            excep.doSomething();
            e.printStackTrace();
        } finally {
            return post;
        }
    }

    //发送get请求
    public static Document getUrl(String uri, Map headers, Map formDatas) {
        Document document = null;
        try {
            Connection connect = Jsoup.connect(uri);
            connect.headers(headers);
            connect.data(formDatas);
            // 带参数结束
            document = connect.ignoreContentType(true).get();
        } catch (Exception e) {
            excep.doSomething();
            e.printStackTrace();
        } finally {
            return document;
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
            //判断是否登录
            excep.loginException(data);
            System.out.println("qq列表获取成功");
            String s = data.body().text();
            JSONObject jsonObject = JSONObject.parseObject(s.substring(s.indexOf("_Callback(") + "_Callback(".length(), s.length() - 3));
            list = (List) jsonObject.getJSONObject("data").get("items");
        } catch (Exception e) {
            excep.doSomething();
            System.out.println("qq列表获取失败，具体错误请查看日志信息");
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    //获取目标qq说说第一个编号ID
    public static String getArticleNumber(String tarQQ, String selfQQ, String g_tk) {
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
            Document data = getUrl("https://user.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds_html_module", headers, formData);
            //判断是否登录
            excep.loginException(data);
            Elements e = data.getElementsByTag("script");
            Pattern p = Pattern.compile("(?:key:')(\\w+)'");
            Matcher m = null;
            try {
                //没获取到编号key就是访问被拒绝啦，单独捕获下异常
                m = p.matcher(e.get(1).toString());
            } catch (Exception e1) {
                return null;
            }
            if (m.find())
                number = m.group(1);
//            int sta = text.indexOf("key:'") + "key:'".length();
//            number = text.substring(sta, sta + "3a3bc5cc4833e45f3bef0500".length());
        } catch (Exception e) {
            excep.doSomething();
            e.printStackTrace();
        } finally {
            return number;
        }
    }

    //点赞说说,成功返回true;
    public static Boolean doLike(String tarQQ, String selfQQ, String fid, String g_tk) {
        String uri1 = "https://user.qzone.qq.com/proxy/domain/w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk=" + g_tk;
        Map<String, String> zanformData = InitUtil.getHeaderOrFormDataMap(
                "opuin: " + selfQQ + "\n" +
                        "unikey: http://user.qzone.qq.com/" + tarQQ + "/mood/" + fid + "\n" +
                        "curkey: http://user.qzone.qq.com/" + tarQQ + "/mood/" + fid + "\n" +
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
            Document data = postUrl(uri1, headers, zanformData);
            //判断是否登录
            excep.loginException(data);
            //到这步的话点赞已经成功
            repeatTitleMap.put(tarQQ, fid);
        } catch (Exception e) {
            excep.doSomething();
            System.out.println("qq说说点赞失败，具体请看日志信息");
            e.printStackTrace();
            accessd = false;
        } finally {
            return accessd;
        }
    }

    //重复说说统计
    public static boolean isTitleRepeated(String qq, String fid) {
        if (repeatTitleMap.containsKey(qq) && repeatTitleMap.get(qq).equals(fid)) return true;
        return false;
    }


    //把点赞过说说ID序列化到硬盘中,为了防止程序出现意外，导致先前点赞过的说说丢失；
    public static void baocun(Map<String, String> map) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir") + File.separator + "properties" + File.separator + "cunDang.txt"));
            objectOutputStream.writeObject(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //读取硬盘说说ID的操作
    public static Map<String, String> read() {
        Map<String, String> map = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir") + File.separator + "properties" + File.separator + "cunDang.txt"))) {
            map = (HashMap<String, String>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }


    //异常处理类
    public static class UnexpectedOfSelfException {
        public int errorCounter = 0;

        //cookie失效异常
        public void loginException(Document document) {
            if (document.toString().contains("请先登录")) {
                System.out.println("您的cookie信息已失效或还未设置cookie，请重新登录qq空间获取cookie,程序即将退出");
                System.exit(-1);
            }
        }

        public void doSomething() {
            errorCounter++;
        }
    }
}
