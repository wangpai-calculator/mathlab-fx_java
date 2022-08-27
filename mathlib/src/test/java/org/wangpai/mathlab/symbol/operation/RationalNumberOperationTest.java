package org.wangpai.mathlab.symbol.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.exception.MathlabException;
import org.wangpai.mathlab.exception.SyntaxException;
import org.wangpai.mathlab.symbol.operand.Figure;
import org.wangpai.mathlab.symbol.operand.RationalNumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 2021-7-22
 */
public class RationalNumberOperationTest {
    private int firstInt = 234234;
    private int secondInt = 2341;

    @Test
    public void getOpposite() throws MathlabException {
        assertEquals(new RationalNumber(-this.firstInt, this.secondInt),
                RationalNumberOperation.getOpposite
                        (new RationalNumber(this.firstInt, this.secondInt)));
    }

    @Test
    public void getAbsolute() throws MathlabException {
        assertEquals(new RationalNumber(this.firstInt, this.secondInt),
                RationalNumberOperation.getAbsolute
                        (new RationalNumber(this.firstInt, -this.secondInt)));
    }

    @Test
    public void getReciprocal() throws MathlabException {
        assertEquals(new RationalNumber(this.secondInt, this.firstInt),
                RationalNumberOperation.getReciprocal
                        (new RationalNumber(this.firstInt, this.secondInt)));
    }

    @Test
    public void power() throws MathlabException {
        // 8 = 2 ^ 3
        assertEquals(new RationalNumber(8),
                RationalNumberOperation.power(new RationalNumber(2), new Figure(3)));
        // 1/8 = 2 ^ (-3)
        assertEquals(new RationalNumber(1, 8),
                RationalNumberOperation.power(new RationalNumber(2), new Figure(-3)));
        // -1/8 = (-2) ^ (-3)
        assertEquals(new RationalNumber(-1, 8),
                RationalNumberOperation.power(new RationalNumber(-2), new Figure(-3)));
        // ? = (?) ^ (1)
        assertEquals(new RationalNumber(this.firstInt, this.secondInt),
                RationalNumberOperation.power(new RationalNumber(this.firstInt, this.secondInt), Figure.ONE));
        // 1 = (?) ^ (0)
        assertEquals(new RationalNumber(1),
                RationalNumberOperation.power(new RationalNumber(this.firstInt, this.secondInt), Figure.ZERO));

        // 0 的负数次方引发异常
        assertThrows(SyntaxException.class,
                () -> RationalNumberOperation.power(new RationalNumber(0), new Figure(-3)));
    }

    @Test
    public void roundUp() throws MathlabException {
        var test1 = new RationalNumber(3, 2);
        assertEquals(new RationalNumber(2),
                RationalNumberOperation.roundUp(test1));

        var test2 = new RationalNumber(3, 1);
        assertEquals(test2, RationalNumberOperation.roundUp(test2));

        var test111 = new RationalNumber(20, 13);
        var test333 = RationalNumberOperation.roundUp(test111);
        assertEquals(test2, RationalNumberOperation.roundUp(test2));
    }

    @Test
    public void roundDown() throws MathlabException {
        var test1 = new RationalNumber(3, 2);
        assertEquals(new RationalNumber(1),
                RationalNumberOperation.roundDown(test1));

        var test2 = new RationalNumber(3, 1);
        assertEquals(test2, RationalNumberOperation.roundDown(test2));
    }
}
