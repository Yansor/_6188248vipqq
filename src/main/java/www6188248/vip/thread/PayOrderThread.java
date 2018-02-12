package www6188248.vip.thread;

import www6188248.vip.config.GameConfig;
import www6188248.vip.exception.OperationTooMuchException;
import www6188248.vip.exception.PayOrderException;
import www6188248.vip.exception.PeriodIdWrongException;
import www6188248.vip.util.CPAPI;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class PayOrderThread extends Thread {
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(GameConfig.PAYTIME);  //1分钟轮循环一次 由配置项决定

               new CPAPI().payOrder();


            } catch (PeriodIdWrongException e) {
                e.printStackTrace();
                //线程中断 重启线程
                new PayOrderThread().start();
            }catch (OperationTooMuchException e) {
                e.printStackTrace();
                //线程中断 重启线程
                new PayOrderThread().start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (PayOrderException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
