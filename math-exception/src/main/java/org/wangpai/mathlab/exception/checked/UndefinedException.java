package org.wangpai.mathlab.exception.checked;

/**
 * 当进行的操作、使用的符号未定义或不支持时，使用该异常
 *
 * @since 2021-7-29
 */
public class UndefinedException extends MathlabCheckedException {
    public UndefinedException() {
        super("错误：发生了未定义异常");
    }

    public UndefinedException(String msg) {
        super(msg);
    }

    public UndefinedException(String msg, Object obj) {
        super(msg, obj);
    }
}
