import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import util.ArgsProPerties;
import util.InitUtil;

import java.util.List;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //获取qq好友列表
        List qqNumberList = InitUtil.getQQNumberList(ArgsProPerties.qq, ArgsProPerties.g_tk);
        //获取说说id
        for (int i = 0; i < qqNumberList.size(); i++) {
            Map friend = (Map) qqNumberList.get(i);
            String tarQQ = friend.get("uin").toString();
            String fid = InitUtil.getArticleNumber(tarQQ, ArgsProPerties.qq, ArgsProPerties.g_tk);
            Boolean hasSend = InitUtil.doLike(ArgsProPerties.qq, fid, ArgsProPerties.g_tk);
        }
        System.out.println("耗时" + (System.currentTimeMillis() - startTime));
    }


    //执行到这步啦;获取说说ID;
    @Test
    public void Test() {
        InitUtil.getArticleNumber("804301822", ArgsProPerties.qq, ArgsProPerties.g_tk);
    }
}
