package fh2229.com.thread;

import fh2229.com.config.GameConfig;
import fh2229.com.util.FHAPI;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class GetMoneyThread extends Thread {

    @Override
    public void run() {

        while(true){
            try {


                String money = new FHAPI().getMoney();

                float moneyInt = 0f;
                if(money != null){
                     moneyInt= Float.valueOf(money);
                }else{
                    moneyInt = 0 ;
                }

                System.out.println("\n当前余额:"+moneyInt);
                if(moneyInt > GameConfig.stopMoneyTop ){
                    System.out.println("\n任务完成!!!");
                    System.exit(0);
                }else if(moneyInt < GameConfig.stopMoneyFlow ){
                    System.out.println("\n任务完成!!!");
                    System.exit(0);
                }

                Thread.sleep(GameConfig.WATCHMONEYTIME);  //1分钟轮循环一次 由配置项决定
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
