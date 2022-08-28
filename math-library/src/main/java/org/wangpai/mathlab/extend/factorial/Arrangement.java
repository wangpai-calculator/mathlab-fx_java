package org.wangpai.mathlab.extend.factorial;

import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.basic.operation.FigureOperation;
import org.wangpai.mathlab.exception.checked.SyntaxException;

/**
 * @since 2022-8-29
 */
public class Arrangement {
    public static Figure arrangement(final Figure n, final Figure m) throws SyntaxException {
        return arrangementForBigResult(n, m);
    }

    public static Figure arrangement(final int n, final int m) throws SyntaxException {
        return arrangementForBigResult(new Figure(n), new Figure(m));
    }

    /**
     * 计算排列数，从 n 个数中选 m 个数进行全排列，因此需要 n >= m。
     * 公式：n! / [(n-m)!] = n*(n-1)*...*(n-m+1)
     *
     * @since 2022-8-29
     */
    public static Figure arrangementForBigResult(final Figure n, final Figure m) throws SyntaxException {
        if (!(n.isPositive() && m.isPositive())) {
            throw new SyntaxException("错误：计算排列数时发现非正数。其中：n = " + n + "，m = " + m);
        }
        if (FigureOperation.lessThan(n, m)) {
            throw new SyntaxException("错误：计算排列数时发现大小关系（n < m）有误。其中：n = " + n + "，m = " + m);
        }
        Figure result = Figure.ONE.clone();
        for (Figure i = FigureOperation.subtract(n, m.clone().decreaseOne()); // start = n - (m - 1)
             FigureOperation.lessOrEqual(i, n);
             i.increaseOne()) {
            result = FigureOperation.multiply(result, i);
        }
        return result;
    }
}
