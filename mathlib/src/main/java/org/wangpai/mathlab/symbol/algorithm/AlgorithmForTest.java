package org.wangpai.mathlab.symbol.algorithm;

import org.wangpai.mathlab.symbol.operand.Figure;
import org.wangpai.mathlab.symbol.operation.FigureOperation;

/**
 * 为了测试类使用而编写的算法
 *
 * @since 2022-8-27
 */
public class AlgorithmForTest {
    /**
     *
     * 求两个数的最大公约数。GCD：Greatest Common Divisor
     *
     * 注意事项：
     * > 当这两个数只有一个为 0 时，结果为另一个数的绝对值。
     * > 特别地，当这两个数均为 0 时，结果为 0。
     * > 其它情况下，结果为正数
     *
     * @since 2021-7-30
     */
    public static Figure findGcd(Figure first, Figure second) {
        if (first.isZero()) {
            return second;
        }
        if (second.isZero()) {
            return first;
        }

        var firstClone = first.clone();
        var secondClone = second.clone();

        if (firstClone.isNegative()) {
            firstClone = FigureOperation.getOpposite(firstClone);
        }
        if (secondClone.isNegative()) {
            secondClone = FigureOperation.getOpposite(secondClone);
        }

        while (!secondClone.isZero()) {
            var middleResult = FigureOperation.mod(firstClone, secondClone);
            firstClone = secondClone;
            secondClone = middleResult;
        }

        return firstClone;
    }

    /**
     * 求两个数的最小公倍数。LCM：Least Common Multiple
     *
     * 算法如下：先得出这两个数的最大公约数，
     * 然后将其中一个数除以最大公约数，得到其中一个质因子（prime factor）
     * 最后将该质因子另外一个没有除以过公约数的数相乘
     *
     * @since 2021-7-30
     */
    public static Figure findLcm(Figure first, Figure second) {
        return FigureOperation.multiply(first,
                FigureOperation.subtract(
                        second, AlgorithmForTest.findGcd(first, second)));
    }
}
