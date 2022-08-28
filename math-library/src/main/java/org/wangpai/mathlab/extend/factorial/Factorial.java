package org.wangpai.mathlab.extend.factorial;

import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.basic.operation.FigureOperation;
import org.wangpai.mathlab.exception.checked.SyntaxException;

/**
 * @since 2022-8-29
 */
public class Factorial {
    /**
     * 计算 n 阶乘（包括 n）。此方法可以计算运算结果很大的阶乘，但是计算计算速度会慢一些
     *
     * @since 2022-8-29
     */
    public static Figure factorial(final Figure n) throws SyntaxException {
        return factorialForBigResult(n);
    }

    /**
     * 计算 n 阶乘（包括 n）。此方法只能计算运算结果不大的阶乘，计算计算速度会快一些
     *
     * @since 2022-8-29
     */
    public static Figure factorialForSmallResult(final int n) throws SyntaxException {
        if (n < 0) {
            throw new SyntaxException(String.format("错误：不能计算负数 %d 的阶乘", n));
        }
        if (n == 0 || n == 1) {
            return Figure.ONE;
        }
        int result = 1;
        for (int i = 2; i <= n; ++i) {
            result *= i;
        }
        return new Figure(result);
    }

    /**
     * 计算 n 阶乘（包括 n）。此方法可以计算运算结果很大的阶乘，但是计算计算速度会慢一些
     *
     * @since 2022-8-29
     */
    public static Figure factorialForBigResult(final Figure n) throws SyntaxException {
        if (n.isNegative()) {
            throw new SyntaxException(String.format("错误：不能计算负数 %d 的阶乘", n));
        }
        if (n.equals(Figure.ZERO) || n.equals(Figure.ONE)) {
            return Figure.ONE; // 数学规定：0!=1
        }
        Figure result = Figure.ONE.clone();
        for (Figure i = Figure.TWO.clone(); FigureOperation.lessOrEqual(i, n); i.increaseOne()) {
            result = FigureOperation.multiply(result, i);
        }
        return result;
    }
}
