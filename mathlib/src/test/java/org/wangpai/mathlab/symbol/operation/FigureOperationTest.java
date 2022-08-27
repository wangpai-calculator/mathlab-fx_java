package org.wangpai.mathlab.symbol.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.exception.MathlabException;
import org.wangpai.mathlab.symbol.algorithm.AlgorithmForTest;
import org.wangpai.mathlab.symbol.operand.Figure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @since 2021-7-30
 */
public class FigureOperationTest {
    private int firstInt = 234234;
    private int secondInt = 2341;

    @Test
    public void divideAndRemainder_Fraction() {
        var result = FigureOperation.divideAndRemainder(
                new Figure(this.firstInt), new Figure(this.secondInt));
        // 测试商是否正确
        assertEquals(new Figure(this.firstInt / this.secondInt), result[0]);
        // 测试余数是否正确
        assertEquals(new Figure(this.firstInt % this.secondInt), result[1]);
    }

    @Test
    public void divideAndRemainder_long() {
        var result = FigureOperation.divideAndRemainder(
                new Figure(this.firstInt), this.secondInt);
        // 测试商是否正确
        assertEquals(new Figure(this.firstInt / this.secondInt), result[0]);
        // 测试余数是否正确
        assertEquals(new Figure(this.firstInt % this.secondInt), result[1]);
    }

    @Test
    public void mod_Fraction() {
        assertEquals(new Figure(this.firstInt % this.secondInt),
                FigureOperation.mod(
                        new Figure(this.firstInt), new Figure(this.secondInt)));
    }

    @Test
    public void mod_long() {
        assertEquals(new Figure(this.firstInt % this.secondInt),
                FigureOperation.mod(
                        new Figure(this.firstInt), this.secondInt));
    }

    @Test
    public void modsQuotient_Fraction() {
        assertEquals(new Figure(this.firstInt / this.secondInt),
                FigureOperation.modsQuotient(
                        new Figure(this.firstInt), new Figure(this.secondInt)));
    }

    @Test
    public void modsQuotient_long() {
        assertEquals(new Figure(this.firstInt / this.secondInt),
                FigureOperation.modsQuotient(
                        new Figure(this.firstInt), this.secondInt));
    }

    @Test
    public void getOpposite_Fraction() {
        assertEquals(new Figure(-this.firstInt),
                FigureOperation.getOpposite(new Figure(this.firstInt)));
    }

    @Test
    public void getAbsolute() {
        assertEquals(new Figure(this.firstInt),
                FigureOperation.getAbsolute(new Figure(-this.firstInt)));
    }

    @Test
    public void getOpposite_long() {
        assertEquals(new Figure(-this.firstInt),
                FigureOperation.getOpposite(this.firstInt));
    }

    @Test
    public void lessOrEqual() {
        assertTrue(FigureOperation.lessOrEqual(new Figure(-1), new Figure(1)));
        assertFalse(FigureOperation.lessOrEqual(new Figure(1), new Figure(-1)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(-1), new Figure(-1)));
    }

    @Test
    public void greaterOrEqual() {
        assertTrue(FigureOperation.greaterOrEqual(new Figure(1), new Figure(-1)));
        assertFalse(FigureOperation.greaterOrEqual(new Figure(-1), new Figure(1)));
        assertTrue(FigureOperation.greaterOrEqual(new Figure(-1), new Figure(-1)));
    }

    @Test
    public void findGcd() {
        assertEquals(AlgorithmForTest.findGcd(
                        new Figure(this.firstInt), new Figure(this.secondInt)),
                FigureOperation.findGcd(
                        new Figure(this.firstInt), new Figure(this.secondInt)));
    }

    @Test
    public void findLcm() {
        assertEquals(AlgorithmForTest.findLcm(
                        new Figure(this.firstInt), new Figure(this.secondInt)),
                FigureOperation.findLcm(new Figure(this.firstInt), new Figure(this.secondInt)));
    }

    @Test
    public void power() throws MathlabException {
        // 8 = 2 ^ 3
        assertEquals(new Figure(8),
                FigureOperation.power(2, 3));
        // ? = (?) ^ (1)
        assertEquals(new Figure(this.firstInt),
                FigureOperation.power(new Figure(this.firstInt), Figure.ONE));
        // 1 = (?) ^ (0)
        assertEquals(Figure.ONE,
                FigureOperation.power(new Figure(this.firstInt), Figure.ZERO));
    }
}
