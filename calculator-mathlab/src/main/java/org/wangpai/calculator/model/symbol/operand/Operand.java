package org.wangpai.calculator.model.symbol.operand;

import org.wangpai.calculator.exception.CalculatorException;

/**
 * @since 2021-8-1
 */
public interface Operand extends Cloneable {
    /**
     * 此方法不能依赖于方法 equals，否则会造成循环依赖
     */
    boolean isZero() throws CalculatorException; // 此处的异常签名是提供给不想实现本方法的子类

    /**
     * 此方法不能依赖于方法 isNegative，否则会造成循环依赖
     *
     * @since 2022-8-24
     */
    boolean isPositive() throws CalculatorException; // 此处的异常签名是提供给不想实现本方法的子类

    /**
     * @since 2021-8-5
     * @lastModified 2022-8-24
     */
    default boolean isNegative() throws CalculatorException {
        return !(this.isZero() || this.isPositive());

//        此方法子类重写模板：
//        @Override
//        public boolean isNegative() {
//            try {
//                return Operand.super.isNegative();
//            } catch (CalculatorException exception) {
//                throw new RuntimeException(exception); // 此异常不会发生
//            }
//        }
    }
}
