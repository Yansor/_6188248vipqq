package fh2229.com.util;

import java.util.*;

/**
 * 通用去重,带索引容器
 * 功能 :
 * 带索引放置元素
 * Created by fushiyong on 2018/2/10.
 */
public class UnrepeatedList {

    private int capcity ;
    public UnrepeatedList(int capcity){
        this.capcity = capcity;
    }

    public UnrepeatedList(){
        this.capcity = Integer.MAX_VALUE;
    }

    private Map<Integer,String> map = new HashMap<Integer, String>();

    public void set(int index, String ele){
        ensureCapcity();
        map.put(index,ele);
    }

    private void ensureCapcity() {
        if(map.size() > this.capcity){
            throw new IndexOutOfBoundsException();
        }
    }

    public void add(String ele){
        ensureCapcity();

                                    for(Map.Entry entry : map.entrySet()){
                                        if(entry.getValue().equals(ele)){
                                            return ;
                                        }
                                    }

            map.put(map.size() , ele);


    }
    public String get(int index){
        return map.get(index);
    }

    public int size(){
        return map.size();
    }
}
