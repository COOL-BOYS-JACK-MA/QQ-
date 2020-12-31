package util;

import org.junit.Test;

public class ArgsProPerties {
    //这里填入你的cookie;
    public static String cookie = "pgv_pvi=7347086336; pgv_pvid=1001896312; RK=PqpAHsCHGn; ptcz=0eb06414f594e28d65ebcb72085c371dbb8fd7e0d39e0bf8ab3cbfdad63befc1; qz_screen=1920x1080; QZ_FE_WEBP_SUPPORT=1; __Q_w_s__QZN_TodoMsgCnt=1; __Q_w_s_hat_seed=1; ptui_loginuin=1124209551; _qpsvr_localtk=0.3575083992472141; Loading=Yes; pgv_info=ssid=s2176009662; cpu_performance_v8=43; zzpaneluin=; zzpanelkey=; uin=o1124209551; skey=@EnQsRcrjP; p_uin=o1124209551; pt4_token=iqmOO-rAc7*7U0hFa0mlhwJARkGkSbxsuou-49hSDFg_; p_skey=BP67py*0fQIPmsWgctnj6AvVVSbXLKUbj51wZswacY8_; rv2=80EDAC0078996221798B52E530716173924A0C19D80FBAA72D; property20=74D4AE8001FA8EB6DCCBC6560242E7A68018E6886EC9961C32C5FAB388BDC36C5CD43397D06E8ED0; x-stgw-ssl-info=0a2c174c66ad073915fa6403388a33f7|0.030|-|1|.|Y|TLSv1.2|ECDHE-RSA-AES128-GCM-SHA256|8000|h2|0";
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
