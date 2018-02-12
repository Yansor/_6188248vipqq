package fh2229.com.exception;

/**
 * Created by fushiyong on 2018/2/11.
 */
public class UndefinedPayStrategyException extends Exception{
    public UndefinedPayStrategyException(){
        super("下注数目未定义~~~");
    }
}
