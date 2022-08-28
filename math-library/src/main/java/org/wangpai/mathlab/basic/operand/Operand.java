package org.wangpai.mathlab.basic.operand;

import org.wangpai.mathlab.exception.unchecked.LogicalException;

/**
 * @since 2021-8-1
 */
public interface Operand extends Cloneable {
    /**
     * 此方法不能依赖于方法 equals，否则会造成循环依赖
     */
    /**
     * @since 2021-8-1
     * @lastModified 2022-8-29
     */
    boolean isZero() throws LogicalException; // 此处的异常签名是提供给不想实现本方法的子类

    /**
     * 此方法不能依赖于方法 isNegative，否则会造成循环依赖
     *
     * @since 2022-8-24
     * @lastModified 2022-8-29
     */
    boolean isPositive() throws LogicalException; // 此处的异常签名是提供给不想实现本方法的子类

    /**
     * @since 2021-8-5
     * @lastModified 2022-8-24
     */
    default boolean isNegative() throws LogicalException {
        return !(this.isZero() || this.isPositive());
    }
}
