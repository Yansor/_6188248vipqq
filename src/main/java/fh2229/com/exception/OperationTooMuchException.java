package fh2229.com.exception;

/**
 * Created by fushiyong on 2018/2/9.
 */
public class OperationTooMuchException extends Exception {
    public OperationTooMuchException() {
        super("操作过于频繁异常~~~");
    }
}
