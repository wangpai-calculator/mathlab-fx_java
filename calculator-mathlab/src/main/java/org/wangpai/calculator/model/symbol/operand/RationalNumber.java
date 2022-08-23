package org.wangpai.calculator.model.symbol.operand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.calculator.exception.CalculatorException;
import org.wangpai.calculator.exception.SyntaxException;
import org.wangpai.calculator.exception.UndefinedException;
import org.wangpai.calculator.model.symbol.operation.FigureOperation;
import org.wangpai.calculator.model.symbol.operation.OperationUtil;
import org.wangpai.calculator.model.symbol.operation.RationalNumberOperation;

/**
 * @since 2021-8-1
 */
public class RationalNumber implements Operand {
    @Getter(AccessLevel.PUBLIC)
    private Figure numerator; // 分子

    @Getter(AccessLevel.PUBLIC)
    private Figure denominator; // 分母

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 RationalNumber 常量不被破坏，因此只能以此加以逻辑限制
     */
    @Accessors(chain = true)
    @Setter(AccessLevel.PRIVATE)
    private boolean isFinal = false;

    /**
     * 注意：不能直接使用 RationalNumber 常量进行赋值！必须使用它的 clone 方法
     */
    public final static RationalNumber ZERO = new RationalNumber(0).setFinal(true);
    public final static RationalNumber ONE = new RationalNumber(1).setFinal(true);

    protected RationalNumber() {
        super();
    }

    public RationalNumber(RationalNumber other) {
        super();
        this.numerator = other.numerator;
        this.denominator = other.denominator;

        this.reduceFraction();
    }

    /**
     * @param numerator   分子
     * @param denominator 分母
     */
    public RationalNumber(Figure numerator, Figure denominator)
            throws SyntaxException {
        super();
        if (denominator.isZero()) {
            throw new SyntaxException("错误：0 不能作分母");
        }
        this.numerator = numerator.clone();
        this.denominator = denominator.clone();

        this.reduceFraction();
    }

    public RationalNumber(long numerator, long denominator)
            throws CalculatorException {
        super();
        if (denominator == 0) {
            throw new SyntaxException("错误：0 不能作分母");
        }
        this.numerator = new Figure(numerator);
        this.denominator = new Figure(denominator);

        this.reduceFraction();
    }

    public RationalNumber(Figure numerator) {
        super();
        this.numerator = numerator.clone();
        this.denominator = new Figure(1);
    }

    public RationalNumber(long numerator) {
        this(new Figure(numerator));
    }

    /**
     * 此方法默认深拷贝
     *
     * @since 2021-8-3
     */
    @Deprecated
    public RationalNumber(Operand other) throws CalculatorException {
        super();

        /**
         * 因为 Java 规定构造器 this(...) 调用必须位于第一行，
         * 所以这里只能先构造一个临时的变量，然后将其拷贝至 this
         */
        RationalNumber temp;

        /**
         * 由于 Operand 与普通的类没有继承关系（无法将普通的类型强制转换为 Operand 类型），
         * 所以 other 不可能为类型 long、BigInteger
         */
        switch (other.getClass().getSimpleName()) {
            case "RationalNumber":
                temp = new RationalNumber((RationalNumber) other);
                break;
            case "Figure":
                temp = new RationalNumber((Figure) other);
                break;

            default:
                throw new UndefinedException("异常：不支持此类的初始化");
        }

        // 将 temp 浅拷贝至 this
        this.numerator = temp.numerator;
        this.denominator = new Figure(1);
    }

    @Override
    public boolean isZero() {
        return this.numerator.isZero();
    }

    @Override
    public boolean isPositive() {
        return this.reduceFraction().numerator.isPositive();
    }

    @Override
    public boolean isNegative() {
        try {
            return Operand.super.isNegative();
        } catch (CalculatorException exception) {
            throw new RuntimeException(exception); // 此异常不会发生
        }
    }

    @Override
    public RationalNumber clone() {
        RationalNumber cloned = new RationalNumber();
        cloned.numerator = this.numerator.clone();
        cloned.denominator = this.denominator.clone();

        return cloned;
    }

    /**
     * 因为这个方法是重写方法，所以这个方法不能抛出异常
     * <p>
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
            if (other instanceof RationalNumber) {
                return this.equals((RationalNumber) other);
            }
            if (other instanceof Figure) {
                return this.equals(new RationalNumber((Figure) other));
            }
        }

        return false;
    }

    /**
     * 算法：相减结果为 0 即为相等
     *
     * @since 2021-8-5
     */
    public boolean equals(RationalNumber other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        try {
            return RationalNumberOperation.subtract(this, other).isZero();
        } catch (Exception exception) {
            return false;  // 只要此处抛出了异常，均视为相等判断失败
        }
    }

    /**
     * @since 2021-10-12
     */
    public String toString(boolean needShowBrackets) {
        if (needShowBrackets) {
            /**
             * 规定外加括号的样式
             */
            final var LEFT_BRACKET = "[";
            final var RIGHT_BRACKET = "]";

            // 如果此有理数为整数，不输出分母
            if (this.denominator.equals(new Figure(1))) {
                // 如果此整数为负数，外加括号
                if (this.numerator.isNegative()) {
                    return new StringBuilder()
                            .append(LEFT_BRACKET)
                            .append(this.numerator)
                            .append(RIGHT_BRACKET)
                            .toString();
                }
                // 如果此整数为正数，直接转化，不外加括号
                return this.numerator.toString();
            } else {
                return new StringBuilder()
                        .append(LEFT_BRACKET)
                        .append(this.numerator).append("/").append(this.denominator)
                        .append(RIGHT_BRACKET)
                        .toString();
            }
        } else {
            // 如果此有理数为整数，不输出分母
            if (this.denominator.equals(new Figure(1))) {
                return this.numerator.toString();
            } else {
                return new StringBuilder()
                        .append(this.numerator).append("/").append(this.denominator)
                        .toString();
            }
        }
    }

    /**
     * @since 2021-8-1
     * @lastModified 2021-10-12
     */
    @Override
    public String toString() {
        return this.toString(true);
    }

    public double toDouble() {
        // 这里使用了笔者自研的“大整数相除防溢出快速算法”
        return OperationUtil.dividedBetweenBigIntegers(
                this.getNumerator(), 0, this.getDenominator(), 0);
    }

    /**
     * 将有理数转化为 double 字符串。参数 format 用于控制显示的样式，这与 String.format(...) 中的规则相同
     *
     * @since 2022-8-24
     */
    public String toDoubleString(String format) {
        return String.format(format, this.toDouble());
    }

    /**
     * 是否是真分数
     *
     * 算法：如果分子与分母小，说明是真分数，反之不是
     *
     * @since 2021-8-5
     */
    public boolean isProperFraction() {
        try {
            this.reduceFraction();
            if (this.isZero()) {
                return true;
            } else if (this.denominator.equals(new Figure(1))) {
                return false;
            }

            if (FigureOperation.subtract(
                            this.numerator, this.denominator)
                    .isNegative()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false; // 此语句只用于占位
        }
    }

    /**
     * 约分
     *
     * 约分后，分母恒为正数
     *
     * 算法：
     * 1. 求分子、分母的最大公约数
     * 2. 将分子、分母分别除以最大公约数
     * 3. 如果结果分母为负数，将分子、分母同时取反
     *
     * @since before 2021-8-5
     */
    public RationalNumber reduceFraction() {
        Figure commonDivisor = FigureOperation.findGcd(this.numerator, this.denominator);
        this.numerator = FigureOperation.modsQuotient(this.numerator, commonDivisor);
        this.denominator = FigureOperation.modsQuotient(this.denominator, commonDivisor);

        if (this.denominator.isNegative()) {
            this.denominator = FigureOperation.getOpposite(this.denominator);
            this.numerator = FigureOperation.getOpposite(this.numerator);
        }

        return this;
    }
}
