import org.jsoup.Connection;
import org.jsoup.Jsoup;
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
            String fid = (String) friend.get("uin");
            Boolean hasSend = InitUtil.doLike(ArgsProPerties.qq, fid, ArgsProPerties.g_tk);
        }
        System.out.println("耗时" + (System.currentTimeMillis() - startTime));
    }
}
