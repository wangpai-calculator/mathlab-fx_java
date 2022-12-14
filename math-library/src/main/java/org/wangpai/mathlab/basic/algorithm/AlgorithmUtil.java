package org.wangpai.mathlab.basic.algorithm;

import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.basic.operand.Rational;
import org.wangpai.mathlab.basic.operation.FigureOperation;

/**
 * @since 2022-8-25
 */
public class AlgorithmUtil {
    /**
     * 大整数相除防溢出快速算法（一层）。将两个大整数相除，并把结果转化为 double 类型
     *
     * 其中，rational 的分子、分母可以各自很大
     *
     * @since 2022-8-25
     */
    public static double rational2doubleQuickly(Rational rational) {
        Figure dividend = rational.getNumerator(); // dividend：被除数
        Figure divisor = rational.getDenominator(); // divisor：除数
        final long maxLong = Long.MAX_VALUE;
        final Figure max = new Figure(maxLong);
        if (FigureOperation.greaterOrEqual(dividend, max) || FigureOperation.greaterOrEqual(divisor, max)) {
            Figure[] a1 = FigureOperation.divideAndRemainder(dividend, max);
            long b1 = a1[0].getOriginBigInteger().longValueExact();
            double c1 = (double) (a1[1].getOriginBigInteger().longValueExact());

            Figure[] a2 = FigureOperation.divideAndRemainder(divisor, max);
            long b2 = a2[0].getOriginBigInteger().longValueExact();
            double c2 = (double) (a2[1].getOriginBigInteger().longValueExact());

            return (b1 + c1 / maxLong) / (b2 + c2 / maxLong);
        } else {
            // 将分子、分母中较大的那个数转换为类型 double 来运算，因为 double 比 long 的范围大
            if (rational.isProperFraction()) {
                return rational.getNumerator().getOriginBigInteger().longValueExact()
                        / (double) (rational.getDenominator().getOriginBigInteger().longValueExact());
            } else {
                return (double) (rational.getNumerator().getOriginBigInteger().longValueExact())
                        / rational.getDenominator().getOriginBigInteger().longValueExact();
            }
        }
    }

    /**
     * 大整数相除防溢出递归算法。将两个大整数相除，并把结果转化为 double 类型
     *
     * 计算 (a1 + b1) / (a2 + b2)。其中，a1、a2 为大数，b1、b2 均小于 max
     *
     * 本递归版可以计算位数更多的大数相除
     *
     * @since 2022-8-25
     */
    public static double dividedBetweenBigIntegers(Figure a1, double b1, Figure a2, double b2) {
        final long maxLong = Long.MAX_VALUE;
        final Figure max = new Figure(maxLong);
        if (FigureOperation.greaterOrEqual(a1, max) || FigureOperation.greaterOrEqual(a2, max)) {
            Figure[] a1Group = FigureOperation.divideAndRemainder(a1, max);
            Figure c1 = a1Group[0];
            double d1 = a1Group[1].getOriginBigInteger().longValueExact() + b1;

            Figure[] a2Group = FigureOperation.divideAndRemainder(a2, max);
            Figure c2 = a2Group[0];
            double d2 = a2Group[1].getOriginBigInteger().longValueExact() + b2;

            return dividedBetweenBigIntegers(c1, d1 / maxLong, c2, d2 / maxLong);
        } else {
            return (a1.getOriginBigInteger().longValueExact() + b1) / (a2.getOriginBigInteger().longValueExact() + b2);
        }
    }
}
