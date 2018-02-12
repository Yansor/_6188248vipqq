package www6188248.vip.thread;

import www6188248.vip.config.GameConfig;
import www6188248.vip.util.CPAPI;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class DaemonThread extends Thread {

    @Override
    public void run() {

        while(true){
            try {
                Thread.sleep(GameConfig.WATCHMONEYTIME);  //1分钟轮循环一次 由配置项决定

                String money = new CPAPI().getMoney();

                float moneyInt = Float.valueOf(money);
                System.out.println("当前余额:"+moneyInt);
                if(moneyInt > GameConfig.stopMoneyTop ){
                    System.out.println("任务完成!!!");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
