package org.wangpai.mathlab.basic.operand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.basic.algorithm.AlgorithmUtil;
import org.wangpai.mathlab.basic.operation.FigureOperation;
import org.wangpai.mathlab.basic.operation.RationalOperation;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;
import org.wangpai.mathlab.exception.checked.SyntaxException;
import org.wangpai.mathlab.tool.DigitalStringUtil;

/**
 * Rational Number：有理数
 *
 * @since 2021-8-1
 */
public class Rational implements Operand {
    @Getter(AccessLevel.PUBLIC)
    private Figure numerator; // 分子

    @Getter(AccessLevel.PUBLIC)
    private Figure denominator; // 分母

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 Rational 常量不被破坏，因此只能以此加以逻辑限制
     */
    @Accessors(chain = true)
    @Setter(AccessLevel.PRIVATE)
    private boolean isFinal = false;

    /**
     * 注意：不能直接使用 Rational 常量进行赋值！必须使用它的 clone 方法
     */
    public final static Rational ZERO = new Rational(0).setFinal(true);
    public final static Rational ONE = new Rational(1).setFinal(true);

    protected Rational() {
        super();
    }

    public Rational(Rational other) {
        super();
        this.numerator = other.numerator;
        this.denominator = other.denominator;

        this.reduceFraction();
    }

    /**
     * @param numerator   分子
     * @param denominator 分母
     */
    public Rational(Figure numerator, Figure denominator)
            throws SyntaxException {
        super();
        if (denominator.isZero()) {
            throw new SyntaxException("错误：0 不能作分母");
        }
        this.numerator = numerator.clone();
        this.denominator = denominator.clone();

        this.reduceFraction();
    }

    public Rational(long numerator, long denominator)
            throws MathlabCheckedException {
        super();
        if (denominator == 0) {
            throw new SyntaxException("错误：0 不能作分母");
        }
        this.numerator = new Figure(numerator);
        this.denominator = new Figure(denominator);

        this.reduceFraction();
    }

    public Rational(Figure numerator) {
        super();
        this.numerator = numerator.clone();
        this.denominator = new Figure(1);
    }

    public Rational(long numerator) {
        this(new Figure(numerator));
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
        return Operand.super.isNegative();
    }

    @Override
    public Rational clone() {
        Rational cloned = new Rational();
        cloned.numerator = this.numerator.clone();
        cloned.denominator = this.denominator.clone();

        return cloned;
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
            if (other instanceof Rational) {
                return this.equals((Rational) other);
            }
            if (other instanceof Figure) {
                return this.equals(new Rational((Figure) other));
            }
        }

        return false;
    }

    /**
     * 算法：相减结果为 0 即为相等
     *
     * @since 2021-8-5
     */
    public boolean equals(Rational other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        try {
            return RationalOperation.subtract(this, other).isZero();
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

    /**
     * @since 2021-8-1
     * @lastModified 2022-9-1
     */
    public double toDouble() {
        if (FigureOperation.greaterThan(this.numerator, new Figure(Long.MAX_VALUE))
                || FigureOperation.greaterThan(this.denominator, new Figure(Long.MAX_VALUE))) {
            return this.toDoubleForBig();
        } else {
            return this.toDoubleForSmall();
        }
    }

    /**
     * 此方法不能用于分子或分母超出 long 类型的分数，但此方法的转换结果可能更精确
     *
     * @since 2021-8-1
     */
    public double toDoubleForSmall() {
        /**
         * 将分子、分母中较大的那个数转换为类型 double 来运算
         */
        if (this.isProperFraction()) {
            return this.numerator.getOriginBigInteger().longValueExact()
                    / ((double) this.denominator.getOriginBigInteger().longValueExact());
        } else {
            return ((double) this.numerator.getOriginBigInteger().longValueExact())
                    / this.denominator.getOriginBigInteger().longValueExact();
        }
    }

    /**
     * 此方法可以用于分子或分母很大的情形，但此方法的转换结果可能会导致精确度下降
     *
     * @since 2022-8-25
     */
    public double toDoubleForBig() {
        // 这里使用了笔者自研的“大整数相除防溢出递归算法”
        return AlgorithmUtil.dividedBetweenBigIntegers(
                this.getNumerator(), 0, this.getDenominator(), 0);
    }

    /**
     * 将有理数转化为 double 字符串。参数 format 用于控制显示的样式，这与 String.format(...) 中的规则相同
     *
     * @since 2022-8-24
     * @lastModified 2022-9-24
     */
    public String toDoubleString(String format) {
        return this.toDoubleString(format, -1);
    }

    /**
     * 将有理数转化为 double 字符串，并向结果添加逗号分隔符。参数 format 用于控制显示的样式，这与 String.format(...) 中的规则相同
     *
     * @param commaInterval 逗号之间的间隔
     * @since 2022-9-24
     */
    public String toDoubleString(String format, int commaInterval) {
        String result = String.format(format, this.toDouble());
        if (commaInterval == -1) {
            return result;
        } else {
            return DigitalStringUtil.addComma(result, commaInterval);
        }
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
    public Rational reduceFraction() {
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
