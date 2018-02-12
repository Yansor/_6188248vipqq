package www6188248.vip.util;

import www6188248.vip.thread.DaemonThread;
import www6188248.vip.thread.PayOrderThread;

/**
 * Created by fushiyong on 2018/2/8.
 */
public class CPMain {
    public static void  main(String [] args){
        //下注线程开启
        new PayOrderThread().start();

        //一分钟刷新一次余额 , 满足 条件 退出线程
        new DaemonThread().start();
    }
}
