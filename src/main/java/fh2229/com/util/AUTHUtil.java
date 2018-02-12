package fh2229.com.util;

import fh2229.com.config.AUTHConfig;
import fh2229.com.util.test.RuoKuai;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class AUTHUtil {

    /**
     * 构造cookie的map
     * @return
     */
    public static Map<String,String> buildCookieMapOnly(String cookieStr){
        Map<String,String> header = new HashMap<String, String>();
        header.put("Cookie", cookieStr);
        return  header;
    }
    /**
     * 构造仅带cookie的map
     * @return
     */
    public static Map<String,String> buildCookieMapOnly(){
        Map<String,String> header = new HashMap<String, String>();
        header.put("Cookie", AUTHConfig.COOKIE);
        return  header;
    }



}
