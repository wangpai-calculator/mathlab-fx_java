package org.wangpai.mathlab.exception.checked;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 本项目的检查型异常基类
 *
 * @since 2021-7-9
 * @lastModified 2022-8-29
 */
@Accessors(chain = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class MathlabCheckedException extends Exception {
    private String exceptionMsg;
    private Object data;

    /**
     * 因为本类是抽象类，所以此构造器可以声明为 protected，
     * 但它的非抽象子类的构造器只能声明为 public
     */
    protected MathlabCheckedException() {
        super();
    }

    protected MathlabCheckedException(String msg) {
        this(msg, null);
    }

    protected MathlabCheckedException(String msg, Object obj) {
        super(msg);
        this.setExceptionMsg(msg);
        this.setData(obj);
    }
}
