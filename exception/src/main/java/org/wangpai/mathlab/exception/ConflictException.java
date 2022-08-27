package org.wangpai.mathlab.exception;

/**
 * @since 2021-9-27
 */
public class ConflictException extends MathlabException {
    public ConflictException() {
        super("异常：引发冲突");
    }

    public ConflictException(String msg) {
        super(msg);
    }

    public ConflictException(String msg, Object obj) {
        super(msg, obj);
    }
}