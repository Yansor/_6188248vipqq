package fh2229.com.thread;

import fh2229.com.config.GameConfig;
import fh2229.com.config.URLConfig;
import fh2229.com.data.NewPeriodData;
import fh2229.com.util.FHAPI;
import fh2229.com.util.JackSonUtils;
import fh2229.com.util.RequestURLUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fh2229.com.config.GameConfig.LOGENABLE;

/**
 * Created by fushiyong on 2018/2/10.
 */
public class GetNewPeriodThread extends Thread{

    public  static List<String> loggedFid = new ArrayList<String>();
    @Override
    public void run(){
        long countL = 0;
            while(true) {

                try {
                        Thread.sleep(GameConfig.NEWPERIODTIME);


                    Map<String, String> header = new HashMap<String, String>();

                    String result = RequestURLUtils.getUrl(URLConfig.getPeriodIdURL, header);

                    try {
                        NewPeriodData newPeriodData = JackSonUtils.objectMapper.readValue(result, NewPeriodData.class);


                        String previous = newPeriodData.getFpreviousresult();

                        FHAPI.previousResult = previous;

                        FHAPI.STARTTIME = newPeriodData.getFstarttime();

                        FHAPI.CLOSETIME = newPeriodData.getFclosetime();

                        FHAPI.SERVERTIME = newPeriodData.getServerTime();
                        //解析前三次记录
                        if (FHAPI.unrepeatedList.size() < 3) {
                            //直接放入
                            FHAPI.unrepeatedList.add(previous);
                        } else {
                            //将记录1 移到 记录2
                            //将记录0 移到 记录1
                            if(countL == -1){
                                System.out.println("bug 以后修复 第一次请求直接略过...");

                            }else{
                                if(loggedFid.contains(newPeriodData.getFnumberofperiod())){
                                    //不插入记录
                                }else {
                                    String record1 = FHAPI.unrepeatedList.get(1);
                                    String record0 = FHAPI.unrepeatedList.get(0);
                                    String record2 = FHAPI.unrepeatedList.get(2);

                                    //0是倒数第三名 1是倒数第二名 2是倒数第一名
                                    FHAPI.unrepeatedList.set(0, record1);

                                    FHAPI.unrepeatedList.set(1, record2);

                                    FHAPI.unrepeatedList.set(2, previous);

                                    loggedFid.add(newPeriodData.getFnumberofperiod());

                                    if(LOGENABLE){
                                        System.out.println("\n更新前三局QQ分分彩结果:");

                                        System.out.println("接收到当前ID: "+newPeriodData.getFnumberofperiod() + " "+previous);

                                        System.out.println(FHAPI.unrepeatedList.get(0));

                                        System.out.println(FHAPI.unrepeatedList.get(1));

                                        System.out.println(FHAPI.unrepeatedList.get(2));
                                    }

                                    //gc
                                    //in order to  system security ,etc ,  collection garbage and release them
                                    if(loggedFid.size() > GameConfig.CACHECOUNTTOP){
                                        if(LOGENABLE){
                                            System.out.println("进行GC 垃圾回收:");
                                            System.out.println("现在缓存游戏ID条数:"+loggedFid.size());
                                        }
                                        for(int count = 0 ; count < loggedFid.size() - GameConfig.CACHECOUNT; count++){
                                            loggedFid.remove(count);
                                        }
                                        if(LOGENABLE){
                                            System.out.println("回收后缓存游戏ID条数:"+loggedFid.size());
                                        }
                                    }

                                }
                            }
                        }
                        FHAPI.NEWPERIODID = newPeriodData.getFid();
                        countL++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}
