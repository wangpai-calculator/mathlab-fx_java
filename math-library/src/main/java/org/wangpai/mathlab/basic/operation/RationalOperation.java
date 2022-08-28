package org.wangpai.mathlab.basic.operation;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.basic.operand.Rational;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;
import org.wangpai.mathlab.exception.checked.SyntaxException;
import org.wangpai.mathlab.exception.unchecked.LogicalException;
import org.wangpai.mathlab.exception.unchecked.UnexpectedException;

/**
 * @since 2021-8-1
 */
@Slf4j
public final class RationalOperation {
    private static final Random random = new Random();
    private static final long randomOffset = ((long) Integer.MAX_VALUE) + 1; // 2 ^ 31
    private static final long randomDenominator = ((long) Integer.MAX_VALUE) * 2 + 1; // (2 ^ 32) - 1

    /*--------------- 加减乘除基本运算 ---------------*/

    /**
     * 算法如下：
     * 先得出两个分母的最大公约数，然后将这两个分母分别除以最大公约数，得到两个质因子（prime factor）
     * 最终的分子为：第一个分子乘以第二个分母的质因子，第二个分子乘以第一个分母的质因子，然后把得到的结果相加
     * 最终的分母为：其中一个分母乘以另一个分母的质因子（也即这两个分母的最小公倍数）
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational add(Rational first, Rational second) {
        final var firstNumerator = first.getNumerator();
        final var firstDenominator = first.getDenominator();
        final var secondNumerator = second.getNumerator();
        final var secondDenominator = second.getDenominator();

        Figure gcd = FigureOperation.findGcd(firstDenominator, secondDenominator);

        Figure firstPrimeFactor = FigureOperation.modsQuotient(firstDenominator, gcd);
        Figure secondPrimeFactor = FigureOperation.modsQuotient(secondDenominator, gcd);

        Rational result = null;
        try {
            result = new Rational(
                    FigureOperation.add(
                            FigureOperation.multiply(firstNumerator, secondPrimeFactor),
                            FigureOperation.multiply(secondNumerator, firstPrimeFactor)),
                    FigureOperation.multiply(firstDenominator, secondPrimeFactor))
                    .reduceFraction();
        } catch (Exception exception) {
            log.error("异常：", exception);
        }

        return result;
    }

    /**
     * 算法：第一个数加上第二个数的相反数
     *
     * @since before 2021-8-5
     */
    public static Rational subtract(Rational first, Rational second) {
        return RationalOperation.add(first, getOpposite(second));
    }

    /**
     * 算法：两个数的分子、分母分别相乘
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational multiply(Rational first, Rational second) {
        Rational result = null;
        try {
            result = new Rational(
                    FigureOperation.multiply(first.getNumerator(), second.getNumerator()),
                    FigureOperation.multiply(first.getDenominator(), second.getDenominator()))
                    .reduceFraction();
        } catch (Exception exception) {
            log.error("异常：", exception);

        }
        return result;
    }

    /**
     * 算法：将第二个整数与第一个有理数的分子相乘
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational multiply(Rational first, Figure second) {
        Rational result = null;
        try {
            result = new Rational(
                    FigureOperation.multiply(first.getNumerator(), second),
                    first.getDenominator())
                    .reduceFraction();
        } catch (Exception exception) {
            log.error("异常：", exception);

        }
        return result;
    }

    /**
     * @since before 2021-8-5
     */
    public static Rational multiply(Rational first, long second) {
        return RationalOperation.multiply(first, new Figure(second));
    }

    /**
     * 算法：两个整数直接相乘
     *
     * @since before 2021-8-5
     */
    public static Rational multiply(Figure first, Figure second) {
        return new Rational(FigureOperation.multiply(first, second));
    }

    /**
     * 算法：第一个数乘以第二个数的倒数
     *
     * @since before 2021-8-5
     */
    public static Rational divide(Rational first, Rational second)
            throws SyntaxException {
        if (second.isZero()) {
            throw new SyntaxException("错误：0 不能作除数");
        }
        return RationalOperation.multiply(first, getReciprocal(second));
    }

    /**
     * @since before 2021-8-5
     */
    public static Rational divide(Figure first, Figure second)
            throws SyntaxException {
        if (second.isZero()) {
            throw new SyntaxException("错误：0 不能作除数");
        }
        return RationalOperation.multiply(new Rational(first),
                RationalOperation.getReciprocal(new Rational(second)));
    }

    /**************** 加减乘除基本运算 ****************/

    /**
     * 求相反数
     *
     * @since 2021-8-5
     * @lastModified 2021-10-12
     */
    public static Rational getOpposite(Rational rational) {
        Rational result = null;
        try {
            result = new Rational(
                    FigureOperation.getOpposite(rational.getNumerator()),
                    rational.getDenominator().clone());
        } catch (Exception exception) {
            log.error("异常：", exception);
        }
        return result;
    }

    /**
     * 求绝对值
     *
     * @since 2022-8-23
     */
    public static Rational getAbsolute(Rational rational)
            throws SyntaxException {
        return new Rational(FigureOperation.getAbsolute(rational.getNumerator()),
                FigureOperation.getAbsolute(rational.getDenominator()));
    }

    /**
     * 求倒数
     *
     * @since before 2021-8-5
     */
    public static Rational getReciprocal(Rational rational)
            throws SyntaxException {
        if (rational.isZero()) {
            throw new SyntaxException("错误：0 没有倒数");
        }

        try {
            return new Rational(
                    rational.getDenominator().clone(),
                    rational.getNumerator().clone());
        } catch (SyntaxException ignored) {
            return null; // 仅用于占位
        }
    }

    /**
     * 小于等于：first <= second
     *
     * @since 2022-8-24
     */
    public static boolean lessOrEqual(Rational first, Rational second) {
        var subtractResult = RationalOperation.subtract(first, second);
        return subtractResult.isNegative() || subtractResult.isZero();
    }

    /**
     * 小于：first < second
     *
     * @since 2022-8-29
     */
    public static boolean lessThan(Rational first, Rational second) {
        return RationalOperation.subtract(first, second).isNegative();
    }

    /**
     * 大于等于：first >= second
     *
     * @since 2022-8-24
     */
    public static boolean greaterOrEqual(Rational first, Rational second) {
        var subtractResult = RationalOperation.subtract(first, second);
        return subtractResult.isPositive() || subtractResult.isZero();
    }

    /**
     * 大于：first > second
     *
     * @since 2022-8-24
     */
    public static boolean greaterThan(Rational first, Rational second) {
        return RationalOperation.subtract(first, second).isPositive();
    }

    /**
     * 占位空方法
     *
     * @since before 2021-8-5
     */
    public static Rational power(Rational base, Rational exponent)
            throws LogicalException {
        throw new LogicalException("错误：不支持指数为有理数的乘方"); // 原因是，当指数为有理数时，其结果为实数，不一定为有理数
    }

    /**
     * 有数数的整数乘方。注意：指数 exponent 不能太大
     *
     * @since 2022-8-23
     */
    public static Rational power(Rational base, Figure exponent)
            throws SyntaxException {
        if (base.isZero() && exponent.isZero()) {
            throw new SyntaxException("错误：不能计算 0 的 0 次方");
        }
        if (base.isZero() && exponent.isNegative()) {
            throw new SyntaxException("错误：不能计算 0 的负数次方");
        }
        if (exponent.isZero()) {
            return new Rational(1);
        }

        boolean needReciprocal = false;
        if (exponent.isNegative()) {
            needReciprocal = true;
        }
        Figure multiplyTimes = FigureOperation.getAbsolute(exponent);
        Rational result = new Rational(
                FigureOperation.power(base.getNumerator(), multiplyTimes),
                FigureOperation.power(base.getDenominator(), multiplyTimes));

        if (needReciprocal) {
            return getReciprocal(result);
        } else {
            return result;
        }
    }

    /**
     * 向上取整。得到一个不小于 rational 的最小整数。如 对 1.5 的向上取整结果为 2
     *
     * @since 2022-8-24
     */
    public static Figure roundUp(Rational rational) {
        if (rational.getDenominator().equals(Figure.ONE)) {
            return rational.getNumerator().clone();
        } else {
            var quotient = FigureOperation.modsQuotient(rational.getNumerator(),
                    rational.getDenominator());
            return FigureOperation.add(quotient, Figure.ONE);
        }
    }

    /**
     * 向下取整。得到一个不大于 rational 的最大整数。如 对 1.5 的向上取整结果为 1
     *
     * @since 2022-8-24
     */
    public static Figure roundDown(Rational rational) {
        return FigureOperation.modsQuotient(rational.getNumerator(), rational.getDenominator());
    }

    /**
     * 得到 0 至 1 的分数随机数
     *
     * 此方法运行 10 万次的总耗时约为 9s。与上个版本相比，本版本的用时约只有它的 30%。
     * 运行 100 万次的总耗时约为 19s。与上个版本相比，本版本的用时约只有它的 8%。
     * 此比例如此不对称，说明运行次数很少时，其它操作所占时间占比过高。因此本方法随运行次数增大时，效率的优势会更明显
     *
     * @since 2022-8-24
     * @firstVersion 0.0：2022-8-24
     * @lastVersion 1.0：2022-9-10
     */
    public static Rational random0To1() {
        // random.nextInt() 的范围为 [- 2 ^ 31, (2 ^ 31) - 1]
        long randomInt = random.nextInt() + randomOffset; // 得到一个非负随机数，范围为 [0, (2 ^ 32) - 1]
        try {
            return new Rational(randomInt, randomDenominator);
        } catch (MathlabCheckedException exception) {
            log.error("错误：发生了意料之外的异常。", exception);
            throw new UnexpectedException(exception); // 此处不会抛出异常
        }
    }

    /**
     * 得到范围为 [0, maxRange] 的分数随机数
     *
     * @since 2022-9-10
     */
    public static Rational random(int maxRange) {
        // random.nextInt() 的范围为 [- 2 ^ 31, (2 ^ 31) - 1]
        long randomInt = random.nextInt() + randomOffset; // 得到一个非负随机数，范围为 [0, (2 ^ 32) - 1]
        try {
            return new Rational(randomInt * maxRange, randomDenominator);
        } catch (MathlabCheckedException exception) {
            log.error("错误：发生了意料之外的异常。", exception);
            throw new UnexpectedException(exception); // 此处不会抛出异常
        }
    }
}
