package org.wangpai.mathlab.exception.unchecked;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 本项目的非检查型异常基类
 *
 * @since 2022-8-29
 */
@Accessors(chain = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class MathlabUncheckedException extends RuntimeException {
    private String exceptionMsg;
    private Object data;

    /**
     * 因为本类是抽象类，所以此构造器可以声明为 protected，
     * 但它的非抽象子类的构造器只能声明为 public
     */
    protected MathlabUncheckedException() {
        super();
    }

    protected MathlabUncheckedException(String msg) {
        this(msg, null);
    }

    protected MathlabUncheckedException(String msg, Object obj) {
        super(msg);
        this.setExceptionMsg(msg);
        this.setData(obj);
    }
}
