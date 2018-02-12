package www6188248.vip.config;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class GameConfig {

    //止盈点
    public static int stopMoneyTop  = 330;

    //止损点
    public static int stopMoneyFlow = 200 ;


    //投注间隔时间 影响投注大小 可能造成投注多次  单位为毫秒
    public static int PAYTIME = 50000 ;

    //查看金额的线程间隔时间,影响CPU卡顿和性能 , 建议1分钟左右或者以上 单位为毫秒
    public static int WATCHMONEYTIME = 60000;

    //以下策略是加倍策略 未加平台抽水
    //策略1  每把赢一块  成本 247
    public static int [] payStrategy1 = {1, 3 , 7 , 15 , 31 , 63 , 127 };

    //下注策略2 每把赢三块 成本 741
    public static int [] payStrategy2 = {3 , 9 , 21 , 45 ,  93 , 189 , 381 };

    //下注策略3 每把赢10块 成本 2470
    public static int [] payStrategy3 = {10 , 30 , 70 ,150 , 310 , 630 , 1270 };

    //下注策略4 每把赢30  成本  7410
    public static int [] payStrategy4 = {30 , 90 , 210 , 450 , 930 , 1890 , 3810 };


    //以下策略是平倍策略
    public static int [] payStrategy5 = {5 , 5 , 5 , 5 , 5 , 5 , 5 };
}
