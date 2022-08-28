package org.wangpai.mathlab.exception.checked;

/**
 * 本类特指无法直接预料的 0 除问题。
 * 这种 0 除不是开发者主动导致的，而是程序运行时意外产生。
 * 如果是开发者主动导致的，如主动将 0 作为除数，应归到 LogicalException 中
 *
 * @since 2021-7-9
 */
public class ZeroDivisorException extends MathlabCheckedException {
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
