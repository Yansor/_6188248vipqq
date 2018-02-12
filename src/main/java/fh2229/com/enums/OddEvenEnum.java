package fh2229.com.enums;

import lombok.Getter;

/**
 * Created by fushiyong on 2018/2/9.
 */
@Getter
public enum OddEvenEnum {
    ODD ("707","ODD"),
    EVEN ("708","EVEN");

     OddEvenEnum(String code , String name){
        this.code = code;
        this.name = name;
    }

    private String code ;
    private String name ;

}
