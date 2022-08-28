package org.wangpai.mathlab.exception.checked;

/**
 * 这指的是一种类型转换时发生的溢出异常
 *
 * @since 2022-9-9
 */
public class OverflowException extends MathlabCheckedException {
    public OverflowException() {
        super("异常：发生溢出");
    }

    public OverflowException(String msg) {
        super(msg);
    }

    public OverflowException(String msg, Object obj) {
        super(msg, obj);
    }
}