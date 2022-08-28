package org.wangpai.mathlab.basic.operand;

import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.basic.operation.FigureOperation;
import org.wangpai.mathlab.exception.checked.OverflowException;
import org.wangpai.mathlab.exception.unchecked.LogicalException;

/**
 * 整数
 *
 * 因为除法对整数有余数，因此此类不支持除法运算
 *
 * @since 2021-7-22
 */
public class Figure implements Operand {
    private static int MAX_SMALL = Integer.MAX_VALUE;
    private static int MIN_SMALL = Integer.MIN_VALUE;

    @Getter
    private boolean isSmall = true; // 标记本对象是小整数还是大整数。为了提高效率，没有必要全都用大整数
    private int small;
    private BigInteger big;

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
    public final static Figure TWO = new Figure(2).setFinal(true);

    protected Figure() {
        super();
    }

    /**
     * 此方法使用的是深克隆
     *
     * @since 2021-8-3
     * @lastModified 2022-9-9
     */
    public Figure(Figure other) {
        super();
        this.isFinal = false; // 凡是手动创建的都不算做常量
        this.isSmall = other.isSmall;
        if (other.isSmall) {
            this.small = other.small;
        } else {
            this.big = Figure.cloneBigInteger(other.big);
        }
    }

    /**
     * 此方法使用的是深克隆
     *
     * @since 2021-8-3
     * @lastModified 2022-9-9
     */
    public Figure(BigInteger num) {
        super();
        this.isFinal = false; // 凡是手动创建的都不算做常量
        long longResult = 0;
        try {
            longResult = num.longValueExact();
        } catch (ArithmeticException exception) {
            this.big = Figure.cloneBigInteger(num); // 如果抛出异常说明是大整数
            this.isSmall = false;
            return;
        }
        this.valueOf(longResult);
    }

    /**
     * @since 2022-9-9
     */
    public Figure(long num) {
        super();
        this.valueOf(num);
    }

    /**
     * 注意：此方法不会涉及对字段 isFinal 的初始化
     *
     * @since 2022-9-9
     */
    private void valueOf(long num) {
        if (couldSmall(num) == 0) {
            this.small = (int) num;
            this.isSmall = true;
        } else {
            this.big = BigInteger.valueOf(num);
            this.isSmall = false;
        }
    }

    /**
     * 判断 long 类型是否可以转换为 int。如果可以，返回 0；如果正数溢出，返回 1；如果负数溢出，返回 -1
     *
     * @since 2022-9-9
     */
    private static int couldSmall(long num) {
        if (num > MAX_SMALL) {
            return 1;
        } else if (num < MIN_SMALL) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 此方法只会返回数据副本
     *
     * @since 2022-9-9
     */
    public BigInteger toBigInteger() {
        if (this.isSmall) {
            return BigInteger.valueOf(this.small);
        } else {
            return cloneBigInteger(this.big);
        }
    }

    /**
     * 此方法可能返回原始 BigInteger 数据，因此有数据被意外修改的风险。只有对效率要求很高时才能调用本方法！
     *
     * @since 2022-9-9
     */
    public BigInteger getOriginBigInteger() {
        if (this.isSmall) {
            return BigInteger.valueOf(this.small);
        } else {
            return this.big;
        }
    }

    private static BigInteger cloneBigInteger(BigInteger bigInteger) {
        return new BigInteger(bigInteger.toString());
    }

    /**
     * @since 2022-9-9
     */
    @Override
    public Figure clone() {
        if (this.isSmall) {
            return new Figure(this.small);
        } else {
            return new Figure(this.big);
        }
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
    public String toString() {
        if (this.isSmall) {
            return String.valueOf(this.small);
        } else {
            return this.big.toString();
        }
    }

    /**
     * 判断是否可以转换为 long 类型
     *
     * @since 2022-9-9
     */
    public boolean tryToLong() {
        if (this.isSmall) {
            return true;
        }

        try {
            this.big.longValueExact();
        } catch (ArithmeticException exception) {
            return false;
        }
        return true; // 如果不抛出异常就认为可以转换为 long 类型
    }

    /**
     * 将 Figure 转化成 long
     *
     * @since 2022-8-29
     * @lastModified 2022-9-9
     */
    public long toLong() throws OverflowException {
        if (this.isSmall) {
            return this.small;
        }

        long result;
        try {
            result = this.big.longValueExact();
        } catch (ArithmeticException exception) {
            throw new OverflowException("本 Figure 对象超出 long 类型的范围，转换失败");
        }
        return result;
    }

    /**
     * 将 Figure 转化成 int
     *
     * @since 2022-9-9
     */
    public int toInt() throws OverflowException {
        if (!this.isSmall) {
            throw new OverflowException("本 Figure 对象超出 int 类型的范围，转换失败");
        }

        return this.small;
    }

    @Override
    public boolean isZero() {
        if (this.isSmall) {
            return this.small == 0;
        } else {
            return this.big.equals(BigInteger.ZERO);
        }
    }

    @Override
    public boolean isPositive() {
        if (this.isSmall) {
            return this.small > 0;
        } else {
            /**
             * 对于 BigInteger 的函数 signum 的返回值：
             *   > 1：代表正数
             *   > 0：代表 0
             *   > -1：代表 负数
             */
            return this.big.signum() == 1;
        }
    }

    @Override
    public boolean isNegative() {
        return Operand.super.isNegative();
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

        if (this.isSmall) { // 如果现在是小整数
            if (this.small == MAX_SMALL) {
                this.isSmall = false;
                this.big = BigInteger.valueOf(MAX_SMALL).add(BigInteger.ONE);
            } else {
                ++this.small;
            }
        } else { // 如果现在是大整数
            long longResult;
            try {
                longResult = this.toLong();
            } catch (OverflowException exception) {
                /**
                 * 抛出异常说明现在已经超出 long 的范围，
                 * 此时加 1 不管怎样都是不可能缩小到 int 的范围的
                 */
                this.big = this.big.add(BigInteger.ONE);
                return this;
            }
            if (longResult == MIN_SMALL - (long) 1) { // 如果此时不把 1 强制转换为 long，会发生无声溢出
                this.small = MIN_SMALL;
                this.isSmall = true;
            } else {
                this.big = this.big.add(BigInteger.ONE);
            }
        }
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

        if (this.isSmall) { // 如果现在是小整数
            if (this.small == MIN_SMALL) {
                this.isSmall = false;
                this.big = BigInteger.valueOf(MIN_SMALL).subtract(BigInteger.ONE);
            } else {
                --this.small;
            }
        } else { // 如果现在是大整数
            long longResult;
            try {
                longResult = this.toLong();
            } catch (OverflowException exception) {
                /**
                 * 抛出异常说明现在已经超出 long 的范围，
                 * 此时加 1 不管怎样都是不可能缩小到 int 的范围的
                 */
                this.big = this.big.subtract(BigInteger.ONE);
                return this;
            }
            if (longResult == MAX_SMALL + (long) 1) { // 如果此时不把 1 强制转换为 long，会发生无声溢出
                this.small = MAX_SMALL;
                this.isSmall = true;
            } else {
                this.big = this.big.subtract(BigInteger.ONE);
            }
        }
        return this;
    }
}
