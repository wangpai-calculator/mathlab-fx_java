package org.wangpai.mathlab.exception.unchecked;

import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

/**
 * 这指的是一个断定不会发生的异常。
 * 有些时候，某个方法的签名中带有异常，但实际上这个异常在本次一定不会产生。
 * 但由于 Java 语法的限制，不想异常传播的话就必须使用 try 块，
 * 由于本异常是非检查型异常，此时就可以使用本异常来代替包装那个一定不会发生的异常
 *
 * @since 2022-9-3
 */
public class UnexpectedException extends MathlabUncheckedException {
    public UnexpectedException() {
        super("错误：发生了意料之外的异常");
    }

    public UnexpectedException(MathlabCheckedException exception) {
        super("错误：发生了意料之外的异常。" + exception.getExceptionMsg(), exception.getData());
    }

    public UnexpectedException(String msg) {
        super(msg);
    }

    public UnexpectedException(String msg, Object obj) {
        super(msg, obj);
    }
}
