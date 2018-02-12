package fh2229.com.util;

/**
 * Created by fushiyong on 2018/2/8.
 */
public class CPMain {


    public static void  main(String [] args){
        new FHAPI().login();
        //下注线程开启
//        new PayOrderThread().start();
//
//        //一分钟刷新一次余额 , 满足 条件 退出线程
//        new GetMoneyThread().start();
//
//        new GetNewPeriodThread().start(); //由线程刷新改为启动时载入前三次记录
    }
}
