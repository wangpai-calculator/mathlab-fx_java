package org.wangpai.calculator.model.symbol.operand;

import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.calculator.exception.CalculatorException;
import org.wangpai.calculator.exception.LogicalException;
import org.wangpai.calculator.exception.UndefinedException;
import org.wangpai.calculator.model.symbol.operation.FigureOperation;

/**
 * 整数
 *
 * 因为除法对整数有余数，因此此类不支持除法运算
 *
 * @since 2021-7-22
 */
public class Figure implements Operand {
    @Getter(AccessLevel.PUBLIC)
    private BigInteger integer;

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 Figure 常量不被破坏，因此只能以此加以逻辑限制
     */
    @Accessors(chain = true)
    @Setter(AccessLevel.PRIVATE)
    private boolean isFinal = false;

    /**
     * 注意：不能直接使用 Figure 常量进行赋值！必须使用它的 clone 方法
     */
    public final static Figure ZERO = new Figure(0).setFinal(true);
    public final static Figure ONE = new Figure(1).setFinal(true);

    protected Figure() {
        super();
    }

    /**
     * 此方法使用的是深克隆
     *
     * @since 2021-8-3
     */
    public Figure(Figure other) {
        this(other.integer);
    }

    /**
     * 此方法使用的是深克隆
     *
     * @since 2021-8-3
     */
    public Figure(BigInteger num) {
        super();
        this.integer = Figure.cloneBigInteger(num);
    }

    public Figure(long num) {
        this.integer = BigInteger.valueOf(num);
    }

    /**
     * 此方法使用的是深克隆
     *
     * @since 2021-8-3
     */
    @Deprecated
    public Figure(Operand other) throws UndefinedException {
        super();

        /**
         * 因为 Java 规定构造器 this(...) 调用必须位于第一行，
         * 所以这里只能先构造一个临时的变量，然后将其拷贝至 this
         */
        Figure temp;

        /**
         * 由于 Operand 与普通的类没有继承关系（无法将普通的类型强制转换为 Operand 类型），
         * 所以 other 不可能为类型 long、BigInteger
         */
        switch (other.getClass().getSimpleName()) {
            case "Figure":
                temp = new Figure((Figure) other);
                break;

            default:
                throw new UndefinedException("异常：不支持此类的初始化");
        }

        // 将 temp 浅拷贝至 this
        this.integer = temp.integer;
    }

    public static Figure valueOf(long num) {
        return new Figure(BigInteger.valueOf(num));
    }

    public Figure setInteger(BigInteger integer) {
        this.integer = integer;
        return this;
    }

    public Figure setInteger(long num) {
        return this.setInteger(BigInteger.valueOf(num));
    }

    private static BigInteger cloneBigInteger(BigInteger other) {
        return new BigInteger(other.toString());
    }

    @Override
    public Figure clone() {
        return new Figure(cloneBigInteger(this.integer));
    }

    @Override
    public String toString() {
        return this.integer.toString();
    }

    /**
     * 因为这个方法是重写方法，所以这个方法不能抛出异常
     *
     * 注意：other 不可能为基本类型
     *
     * @since 2021-8-5
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof Operand) {
            if (other instanceof Figure) {
                return this.equals((Figure) other);
            }
        }

        return false;
    }

    /**
     * 算法：相减结果为 0 即为相等
     *
     * @since 2021-8-5
     */
    public boolean equals(Figure other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        try {
            return FigureOperation.subtract(this, other).isZero();
        } catch (Exception exception) {
            return false;  // 只要此处抛出了异常，均视为相等判断失败
        }
    }

    @Override
    public boolean isZero() {
        return this.integer.equals(BigInteger.ZERO);
    }

    @Override
    public boolean isPositive() {
        /**
         * 对于 BigInteger 的函数 signum 的返回值：
         *   > 1：代表正数
         *   > 0：代表 0
         *   > -1：代表 负数
         */
        return this.integer.signum() == 1;
    }

    @Override
    public boolean isNegative() {
        try {
            return Operand.super.isNegative();
        } catch (CalculatorException exception) {
            throw new RuntimeException(exception); // 此异常不会发生
        }
    }

    /**
     * 自增 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-8-24
     */
    public Figure increaseOne() throws LogicalException {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this + "】进行自增");
        }
        this.integer = this.integer.add(BigInteger.ONE);
        return this;
    }

    /**
     * 自减 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-8-24
     */
    public Figure decreaseOne() throws LogicalException {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this + "】进行自减");
        }
        this.integer = this.integer.subtract(BigInteger.ONE);
        return this;
    }
}
