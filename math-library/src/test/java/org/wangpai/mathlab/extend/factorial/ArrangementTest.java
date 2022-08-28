package org.wangpai.mathlab.extend.factorial;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.exception.checked.SyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrangementTest {
    @Test
    public void arrangementForBigResult() throws SyntaxException {
        Figure n = new Figure(4);
        Figure m = new Figure(2);
        assertEquals(new Figure(12), Arrangement.arrangementForBigResult(n, m));
        assertEquals(n, Arrangement.arrangementForBigResult(n, Figure.ONE));
    }
}