import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import util.ArgsProPerties;
import util.InitUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.InitUtil.excep;
import static util.InitUtil.repeatTitleMap;

public class Main {

    public static void main(String[] args) {

        //维护一个程序错误计数器，超过10次就终止程序，因为设计到网络等波动问题
        new Thread(() -> {
            long t = System.currentTimeMillis();
            while (true) {
                if (excep.errorCounter >= 5) {
                    System.out.println("程序终止");
                    InitUtil.baocun(repeatTitleMap);
                    System.exit(-1);
                }
                try {
                    //每5秒进行一次程序异常参数判断
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //每隔1小时重置一次程序错误计数器
                if (System.currentTimeMillis() - t >= 60 * 60 * 1000) {
                    t = System.currentTimeMillis();
                    excep.errorCounter = 0;
                }
            }
        }).start();

        //程序主体
        while (true) {
            start();
            //每1小时进行好友全部点赞；
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void start() {
        try {
            //获取qq好友列表
            List qqNumberList = InitUtil.getQQNumberList(ArgsProPerties.qq, ArgsProPerties.g_tk);
            //获取说说id
            for (int i = 0; i < qqNumberList.size(); i++) {
                long startTime = System.currentTimeMillis();
                Map friend = (Map) qqNumberList.get(i);
                String tarQQ = friend.get("uin").toString();
                System.out.println("获取到qq好友: " + tarQQ + "->" + friend.get("name").toString());
                String fid = InitUtil.getArticleNumber(tarQQ, ArgsProPerties.qq, ArgsProPerties.g_tk);
                if (fid == null) {
                    System.out.println("qq编号获取失败，可能是由于对方设置啦访问权限导致，具体错误请查看日志信息");
                    System.out.println("--------------------------------------------");
                    continue;
                }
                System.out.println("获取到说说id: " + fid);
                if (InitUtil.isTitleRepeated(tarQQ, fid)) {
                    System.out.println("该好友说说过去已点赞，将跳过");
                    System.out.println("--------------------------------------------");
                    continue;
                }
                Boolean hasSend = InitUtil.doLike(tarQQ, ArgsProPerties.qq, fid, ArgsProPerties.g_tk);
                if (hasSend) System.out.println("该好友最新说说点赞成功");
                System.out.println("耗时" + (System.currentTimeMillis() - startTime));
                System.out.println("--------------------------------------------");
                //每点赞一说说睡眠随机睡几秒；
                Thread.sleep((long) (Math.random() * 6 * 1000));
            }
        } catch (Exception e) {
            excep.doSomething();
            e.printStackTrace();
        }
    }

    @Test
    public void Test1() {

    }

    @Test
    public void Test2() {

    }
}
