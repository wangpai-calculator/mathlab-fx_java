package org.wangpai.mathlab.exception;

/**
 * @since 2021-7-9
 */
public class ZeroDivisorException extends MathlabException {
    public ZeroDivisorException() {
        super("异常：发生了 0 除");
    }

    public ZeroDivisorException(String msg) {
        super(msg);
    }

    public ZeroDivisorException(String msg, Object obj) {
        super(msg, obj);
    }
}
