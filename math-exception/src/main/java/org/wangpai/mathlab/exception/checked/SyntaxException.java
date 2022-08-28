package org.wangpai.mathlab.exception.checked;

/**
 * @since 2021-7-9
 */
public class SyntaxException extends MathlabCheckedException {
    public SyntaxException() {
        super("异常：不符语法");
    }

    public SyntaxException(String msg) {
        super(msg);
    }

    public SyntaxException(String msg, Object obj) {
        super(msg, obj);
    }
}
