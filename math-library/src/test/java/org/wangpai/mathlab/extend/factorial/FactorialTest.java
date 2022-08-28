package org.wangpai.mathlab.extend.factorial;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.exception.checked.SyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactorialTest {
    @Test
    public void factorialForSmallResult() throws SyntaxException {
        assertEquals(new Figure(24), Factorial.factorialForSmallResult(4));
    }

    @Test
    public void factorialForBigResult() throws SyntaxException {
        Figure num = new Figure(4);
        assertEquals(new Figure(24), Factorial.factorialForBigResult(num));
    }
}