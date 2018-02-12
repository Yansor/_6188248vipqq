package www6188248.vip.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by fushiyong on 2018/2/8.
 */
public class RequestURLUtils {

    public static String postUrl (String requestUrl , Map<String,String> header , Map<String,Object> requestBody  ){

        try {

            HttpURLConnection urlConnection = (HttpURLConnection)new URL(requestUrl).openConnection();

            urlConnection.setRequestMethod("POST");

            //请求头
            for(Map.Entry<String,String> entry : header.entrySet()){

                urlConnection.setRequestProperty(entry.getKey(),entry.getValue());
            }

            //post数据
            StringBuffer postBody = new StringBuffer();
            for(Map.Entry<String,Object> entry : requestBody.entrySet()){

                postBody.append(entry.getKey());
                postBody.append("=");
                postBody.append(URLEncoder.encode(entry.getValue().toString()));
                postBody.append("&");
            }

            //设置流读写权限
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            postBody.deleteCharAt(postBody.length()-1);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            pw.print(postBody);
            pw.flush();

            StringBuffer result = new StringBuffer();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String buffer = null;

            while((buffer = br.readLine()) != null){
                result.append(buffer);
            }

            return result.toString();


        } catch (IOException e) {

            e.printStackTrace();

            return null ;
        }
    }


    public static String getUrl (String requestUrl , Map<String,String> header ){

        try {

            HttpURLConnection urlConnection = (HttpURLConnection)new URL(requestUrl).openConnection();

            urlConnection.setRequestMethod("GET");

            //请求头
            for(Map.Entry<String,String> entry : header.entrySet()){

                urlConnection.setRequestProperty(entry.getKey(),entry.getValue());
            }


            StringBuffer result = new StringBuffer();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String buffer = null;

            while((buffer = br.readLine()) != null){
                result.append(buffer);
            }

            return result.toString();


        } catch (IOException e) {

            e.printStackTrace();

            return null ;
        }
    }

}
