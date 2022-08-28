package org.wangpai.mathlab.extend.factorial;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.exception.checked.SyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombinationTest {
    @Test
    void combinationForBigResult() throws SyntaxException {
        Figure n = new Figure(4);
        Figure m = new Figure(2);
        assertEquals(new Figure(6), Combination.combinationForBigResult(n, m));
        assertEquals(n, Combination.combinationForBigResult(n, Figure.ONE));
    }
}