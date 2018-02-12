package fh2229.com.strategy;

import fh2229.com.enums.OddEvenEnum;
import fh2229.com.exception.UndefinedPayStrategyException;
import fh2229.com.util.FHAPI;
import fh2229.com.util.NumberUtil;

/**
 * Created by fushiyong on 2018/2/11.
 */
public class PayAmount1Strategy implements PayAmountStrategy {
    @Override
    public int doStrategy() {


        return 0;
    }

    /**
     *
     * @param previousPayType 开奖结果
     * @param step 当前进行到第几步
     * @param previousResult 下注正确或者错误标志
     * @param payStrategy 下注多少集合
     * @return 返回下次应该下注多少
     */
    public int doStrategy0(OddEvenEnum previousPayType, int step  , String previousResult , int[] payStrategy)throws UndefinedPayStrategyException {

        if(step == 0){
            return Integer.valueOf(payStrategy[0]);
        }
        if(wrongOrRight(previousPayType, previousResult )){
            //初始化投注步数
            FHAPI.payStep = 0 ;
            return Integer.valueOf(payStrategy[0]);
        }else{
            if(step + 1 <= payStrategy.length){
                return Integer.valueOf(payStrategy[step]);
            }else{
                throw new UndefinedPayStrategyException();
            }

        }
    }

    private boolean wrongOrRight(OddEvenEnum previousPayType, String previousResult) {

        String firstResult  = previousResult.split(",")[0];

        return previousPayType.equals(NumberUtil.isEven(Integer.valueOf(firstResult)) == true ?  OddEvenEnum.EVEN : OddEvenEnum.ODD );
    }
}
