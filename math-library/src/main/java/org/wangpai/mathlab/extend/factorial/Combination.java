package org.wangpai.mathlab.extend.factorial;

import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.basic.operation.FigureOperation;
import org.wangpai.mathlab.exception.checked.SyntaxException;

/**
 * @since 2022-8-29
 */
public class Combination {
    public static Figure combination(final Figure n, final Figure m) throws SyntaxException {
        return combinationForBigResult(n, m);
    }

    public static Figure combination(final int n, final int m) throws SyntaxException {
        return combination(new Figure(n), new Figure(m));
    }

    /**
     * 计算组合数，从 n 个数中选 m 个数合成一组，因此需要 n >= m。
     * 公式：A(n,m) / A(m,m) = [n*(n-1)*...*(n-m+1)] / (m!)
     *
     * @since 2022-8-29
     */
    public static Figure combinationForBigResult(final Figure n, final Figure m) throws SyntaxException {
        if (n.isPositive() && m.isZero()) {
            return Figure.ONE; // 数学规定：C(m,0) = 1
        }
        return FigureOperation.modsQuotient(Arrangement.arrangementForBigResult(n, m)
                , Factorial.factorial(m));
    }
}
