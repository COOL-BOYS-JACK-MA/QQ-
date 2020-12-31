package util;

import org.junit.Test;

public class ArgsProPerties {
    //这里填入你的cookie;
    public static String cookie = "pgv_pvi=7347086336; pgv_pvid=1001896312; RK=PqpAHsCHGn; ptcz=0eb06414f594e28d65ebcb72085c371dbb8fd7e0d39e0bf8ab3cbfdad63befc1; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; __Q_w_s__QZN_TodoMsgCnt=1; __Q_w_s_hat_seed=1; ptui_loginuin=1124209551; _qpsvr_localtk=0.3575083992472141; uin=o1124209551; skey=@fJ3t2qKXz; p_uin=o1124209551; pt4_token=cIjfwAgKZyg6*VGoP7ihKWaSrvjup-FEwvpBd-Dw1II_; p_skey=f3a6guHTTCkxMrofXtyxptIZKNTtE2kQ-8mXQFP6fWI_; Loading=Yes; pgv_info=ssid=s2176009662; x-stgw-ssl-info=bc6bbc72b35735e08cc8694b41a29499|0.133|-|1|.|Y|TLSv1.2|ECDHE-RSA-AES128-GCM-SHA256|7500|h2|0; cpu_performance_v8=6";
    //这里填入你的浏览器头;
    public static String user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";
    //这里填入你的qq号
    public static String qq = "1124209551";

    public static String g_tk = cookie.substring(cookie.indexOf("p_skey=") + "p_skey=".length(), cookie.indexOf("p_skey=") + "p_skey=".length() + "f3a6guHTTCkxMrofXtyactIZKNTtE2kQ-8mXQFP6fWI_".length());


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
