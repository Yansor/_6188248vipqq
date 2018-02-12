package fh2229.com.thread;

import fh2229.com.exception.GetNewPeriodException;
import fh2229.com.exception.OperationTooMuchException;
import fh2229.com.exception.PayOrderException;
import fh2229.com.exception.PeriodIdWrongException;
import fh2229.com.util.FHAPI;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class PayOrderThread extends Thread {
    @Override
    public void run() {
        FHAPI fhapi = new FHAPI();
        while(true){
            try {

//                Thread.sleep(GameConfig.PAYTIME);  //1分钟轮循环一次 由配置项决定

//                String threadName = Thread.currentThread().getName();
//
//                System.out.println("下注死线程名称为:"+threadName);

                fhapi.payOrder();



            } catch (PeriodIdWrongException e) {
                e.printStackTrace();
                //线程中断 重启线程

                //大都由于并发问题 延迟解决问题
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }catch (GetNewPeriodException e){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            catch (OperationTooMuchException e) {
                e.printStackTrace();
                //大都由于并发问题 延迟解决问题
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
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
