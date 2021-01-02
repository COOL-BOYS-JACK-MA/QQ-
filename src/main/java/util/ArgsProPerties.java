package util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgsProPerties {

    //这里填入你的cookie;
    public static String cookie = "";
    //这里填入你的浏览器头;
    public static String user_agent = "";
    //这里填入你的qq号
    public static String qq = "";

    static {
        String fileStr = readFile(new File(System.getProperty("user.dir") + File.separator + "properties" + File.separator + "properties.txt"), "utf8");
        String[] regexs = {"cookie:.?\\{(?:\\s+)?(.*?)(?:\\s+)?\\}", "qq:.?\\{(?:\\s+)?(.*?)(?:\\s+)?\\}", "user_agent:.?\\{(?:\\s+)?(.*?)(?:\\s+)?\\}"};
        for (String regex : regexs) {
            Pattern compile = Pattern.compile(regex);
            Matcher m = compile.matcher(fileStr);
            if (m.find()) {
                String group = m.group(1);
                if (regex.contains("cookie")) cookie = group;
                if (regex.contains("qq")) qq = group;
                if (regex.contains("user_agent")) user_agent = group;
            }
        }
        if (cookie == null || cookie.length() < 500) {
            System.out.println("还未设置cookie或cookie不合法，请登录qq空间获取cookie,程序即将退出");
            System.exit(-1);
        }
    }

    public static String g_tk = getTk(cookie.substring(cookie.indexOf("p_skey=") + "p_skey=".length(), cookie.indexOf("p_skey=") + "p_skey=".length() + "f3a6guHTTCkxMrofXtyactIZKNTtE2kQ-8mXQFP6fWI_".length()));


    @Test
    public void test() {
        String fileStr = readFile(new File(System.getProperty("user.dir") + File.separator + "properties" + File.separator + "properties.txt"), "utf8");
        String[] regexs = {"cookie:.?\\{(?:\\s+)?(.*?)(?:\\s+)?\\}", "qq:.?\\{(?:\\s+)?(.*?)(?:\\s+)?\\}", "user_agent:.?\\{(?:\\s+)?(.*?)(?:\\s+)?\\}"};
        for (String regex : regexs) {
            Pattern compile = Pattern.compile(regex);
            Matcher m = compile.matcher(fileStr);
            if (m.find()) {
                String group = m.group(1);
                if (regex.contains("cookie")) cookie = group;
                if (regex.contains("qq")) qq = group;
                if (regex.contains("user_agent")) user_agent = group;
            }
        }
        System.out.println(qq);
        System.out.println(user_agent);
        System.out.println(cookie);
    }


    public static String getTk(String skey) {
        int hash = 5381;
        for (int i = 0, len = skey.length(); i < len; ++i) {
            hash += (hash << 5) + (int) (char) skey.charAt(i);
        }
        return (hash & 0x7fffffff) + "";
    }

    //文件读写
    public static String readFile(File file, String code) {
        try {
            FileInputStream fInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, code);
            BufferedReader in = new BufferedReader(inputStreamReader);
            String strTmp = "";
            StringBuffer sBuffer = new StringBuffer();
            while ((strTmp = in.readLine()) != null) {
                String s = strTmp.replaceAll("\t", "").replaceAll(" ", "");
                sBuffer.append(s);
            }
            return sBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
