package util;

import org.junit.Test;

public class ArgsProPerties {
    //这里填入你的cookie;
    public static String cookie = "pgv_pvi=7672666112; RK=vioAGuC2EF; ptcz=715daf50b4092dd8266c1e891f0a18ebb6c18b2572b627cfb97dce7b3f76991a; pgv_pvid=5736518295; tvfe_boss_uuid=6aee9dab9aff1a20; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; __Q_w_s__QZN_TodoMsgCnt=1; o_cookie=1124209551; pac_uid=1_1124209551; cpu_performance_v8=5; _qpsvr_localtk=0.7108266978152225; pgv_info=ssid=s186826342; uin=o1124209551; skey=@K8T9bQqpo; p_uin=o1124209551; pt4_token=xcBBdkVv92KdwJ8V3ALzAl140gUkY3OBH4RcvMuwc74_; p_skey=C1MKrjxL*5*UL33fwP6ukeMUl*Vm8rJTJiAjtsljlEc_; Loading=Yes; x-stgw-ssl-info=5659d2242b168fe69cae71805e23bf58|0.134|-|1|.|Y|TLSv1.2|ECDHE-RSA-AES128-GCM-SHA256|11500|h2|0";
    //这里填入你的浏览器头;
    public static String user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";
    //这里填入你的qq号
    public static String qq = "1124209551";

    public static String g_tk = getTk(cookie.substring(cookie.indexOf("p_skey=") + "p_skey=".length(), cookie.indexOf("p_skey=") + "p_skey=".length() + "f3a6guHTTCkxMrofXtyactIZKNTtE2kQ-8mXQFP6fWI_".length()));


    @Test
    public void test() {


    }


    public static String getTk(String skey) {
        int hash = 5381;
        for (int i = 0, len = skey.length(); i < len; ++i) {
            hash += (hash << 5) + (int) (char) skey.charAt(i);
        }
        return (hash & 0x7fffffff) + "";
    }
}
