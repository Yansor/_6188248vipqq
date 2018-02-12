package fh2229.com.config;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class URLConfig {

//    //若快接口识别验证码地址
//    public static String ruoKuaiCodeURL = "http://api.ruokuai.com/create.json";
    //验证码图片地址
    public static String validateCodeURL = "http://fh2229.com/Home/ValidateCode";
    //登录地址
    public static String loginURL = "http://fh2229.com/Home/login";

    //查看用户余额地址
    public static String yuEURL = "http://fh2229.com/UserCenter/UserInfo";

    //下单地址
    public static String payURL = "http://fh2229.com/AddOrders/OtherOrder";

    //获取下期投注地址
    public static String getPeriodIdURL = "http://fh2229.com/Shared/GetNewPeriod?gameID=75&_=1518166938337";

    //获取赔率变化地址
    public static String getChangeOddsURL = "http://fh2229.com/Shared/GetChangeOdds?periodID=%s&gameid=%s&_=1518166938336";

}
