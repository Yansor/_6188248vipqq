package fh2229.com.util;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fushiyong on 2018/2/12.
 */
public class CloseableHttpClientUtils {

    public static  CloseableHttpResponse getResponse(String urlStr){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlStr);

        try {
            return httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCookie(CloseableHttpResponse response){
        StringBuffer setCookie = new StringBuffer();

        Header[] setCookieHeader = response.getHeaders("Set-Cookie");
        for(int index = 0 ; index < setCookieHeader.length ; index++){
            if(index == 0){
                setCookie.append(setCookieHeader[index].getValue());
            }else{
                setCookie.append("," +setCookieHeader[index].getValue());
            }
        }
        return setCookie.toString();
    }
    public static byte[] getByteFromResponse(CloseableHttpResponse response){

        byte[] buffer = new byte[10  * 1024];


        InputStream in = null;
        try {
            in = response.getEntity().getContent();


            while(in.read(buffer) != -1 ){
            }
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
