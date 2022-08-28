package org.wangpai.mathlab.exception.checked;

/**
 * @since 2021-7-29
 */
public class UnknownException extends MathlabCheckedException {
    public UnknownException() {
        super("错误：发生了未知异常");
    }

    public UnknownException(String msg) {
        super(msg);
    }

    public UnknownException(String msg, Object obj) {
        super(msg, obj);
    }
}
