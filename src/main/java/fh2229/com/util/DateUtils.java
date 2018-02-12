package fh2229.com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fushiyong on 2018/2/12.
 */
public class DateUtils {
    //2018/02/12 13:21:57
    public static String FORMATTER1 = "YYYY/MM/dd HH:mm:ss";


    public static Date format(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATTER1);

        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
}
