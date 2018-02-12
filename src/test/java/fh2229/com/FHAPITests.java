package fh2229.com;

import fh2229.com.util.FHAPI;
import org.junit.Test;

/**
 * Created by fushiyong on 2018/2/9.
 */

public class FHAPITests {

    //凤凰彩票获取用户余额
    @Test
    public void getMoney(){
        System.out.println(new FHAPI().getMoney());
    }

    //凤凰彩票获取下期投注ID
//    @Test
    public void getPeriodId(){
        System.out.println(FHAPI.NEWPERIODID);
    }

    //凤凰彩投注1元
//    @Test
    public void payOrder(){
        try {
            new FHAPI().payOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
