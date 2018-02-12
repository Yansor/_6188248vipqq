package fh2229.com.strategy;

/**
 * Created by fushiyong on 2018/2/9.
 */

import fh2229.com.enums.OddEvenEnum;
import fh2229.com.util.NumberUtil;
import fh2229.com.util.UnrepeatedList;

/**
 * 看盘下注策略
 *
 * 策略1 :
 * 看前三盘数据,第一位打单双
 * 遇到单打单 , 遇到双打双 , 遇到跳打跳  用自动机理论解决
 */
public class PayType1Strategy implements PayTypeStrategy {

    //数据
    //
    private  UnrepeatedList data = new UnrepeatedList(3);
    //已排序后的数据
    public void  setData( UnrepeatedList data){

        this.data = data;
        if(data.size() < 3 ){
            return ;
        }


        this.number1 = Integer.valueOf( data.get(0).split(",")[0]);

        this.number2 = Integer.valueOf( data.get(1).split(",")[0]);

        this.number3 = Integer.valueOf( data.get(2).split(",")[0]);
    }

    private int number1 ;

    private int number2 ;

    private int number3 ;

    @Override
    public OddEvenEnum doStrategy(){
        System.out.println("根据以下数字推论 下注 "+number1+number2+number3);

        if(data.size() < 3 ){
            return null;
        }

        //奇数
        boolean odd1 = NumberUtil.isOdd(this.number1) ;

        //奇数
        boolean odd2 = NumberUtil.isOdd(this.number2) ;

        //奇数
        boolean odd3 = NumberUtil.isOdd(this.number3) ;




        //
//        1 2 3
//        第一期
//                第二期
//        第三期
//
//        第二期 和 第三期 亦或操作
//
//        亦或结果 为 1  需要判断  第一期 和 第二期亦或  结果 为 1 打第二期的结果
//        否则 打 第三期 的结果
//
//        亦或结果 为 0  则 直接打第三期 相同的

        int flag = -1 ;
        if(odd2 ^ odd3 == true  ){
            if(odd1 ^ odd2 == true ){
                flag = 2 ;
            }else{
                flag = 3 ;
            }
        }else{
            flag = 3 ;
        }

        switch (flag){
            case -1 : return null;
            case 2 : return NumberUtil.isEven(Integer.valueOf( data.get(1).split(",")[0]))  == false ? OddEvenEnum.ODD : OddEvenEnum.EVEN;
            case 3 : return NumberUtil.isEven(Integer.valueOf( data.get(2).split(",")[0]))  == false ? OddEvenEnum.ODD : OddEvenEnum.EVEN;
        }
        return null;
    }

}
