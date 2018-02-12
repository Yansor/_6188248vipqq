package fh2229.com.config;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class GameConfig {
    //止盈点
    public static int stopMoneyTop  = 500;

    //止损点
    public static int stopMoneyFlow = 230 ;


    //查看新期数的线程时间 , 需要 先 下注时间  官方设置了3秒
    public static int NEWPERIODTIME = 3000;


    //投注间隔时间 影响投注大小 可能造成投注多次  单位为毫秒
    public static int PAYTIME = 60000 ;

    //查看金额的线程间隔时间,影响CPU卡顿和性能 , 建议1分钟左右或者以上 单位为毫秒
    public static int WATCHMONEYTIME = 10000;



    //以下策略是加倍策略 未加平台抽水
    //策略1  每把赢一块  成本 247
    public static int [] payAmount1 = {1, 3 , 7 , 15 , 31 , 63 , 127 };

    //下注策略2 每把赢三块 成本 741
    public static int [] payAmount2 = {3 , 9 , 21 , 45 ,  93 , 189 , 381 };

    //下注策略3 每把赢10块 成本 2470
    public static int [] payAmount3 = {10 , 30 , 70 ,150 , 310 , 630 , 1270 };

    //下注策略4 每把赢30  成本  7410
    public static int [] payAmount4 = {30 , 90 , 210 , 450 , 930 , 1890 , 3810 };

    //成本226元
    public static int [] payAmount6 = {3  , 12 , 27 , 57 , 129  };
    //以下策略是平倍策略
    public static int [] payAmount5 = {5 , 5 , 5 , 5 , 5 , 5 , 5 };


    //QQ分分彩单双赔率
    public static String QQFenFenCaiDanChangeOdds = "1.97";



    //缓存条数的阈值, 到了这个值 就进行垃圾清理
    public static int CACHECOUNTTOP = 30 ;

    //进行垃圾清理之后理应遗留的条数
    public static int CACHECOUNT = 5;

    public static boolean LOGENABLE = true;

}
