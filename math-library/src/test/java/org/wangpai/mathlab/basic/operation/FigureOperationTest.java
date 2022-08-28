package org.wangpai.mathlab.basic.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.basic.algorithm.AlgorithmForTest;
import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @since 2021-7-30
 */
public class FigureOperationTest {
    private long firstInt = 234234;
    private long secondInt = 2341;

    private long firstLong = this.firstInt;
    private long secondLong = this.secondInt;

    private final long maxInt = Integer.MAX_VALUE;

    @Test
    public void add() {
        var result1 = FigureOperation.add(
                new Figure(this.firstLong), new Figure(this.secondLong));
        assertEquals(new Figure(this.firstLong + this.secondLong), result1);

        var result2 = FigureOperation.add(
                new Figure(this.maxInt), new Figure(this.maxInt));
        assertEquals(new Figure(this.maxInt * 2), result2);
    }

    @Test
    public void subtract() {
        var result1 = FigureOperation.subtract(
                new Figure(this.firstLong), new Figure(this.secondLong));
        assertEquals(new Figure(this.firstLong - this.secondLong), result1);

        var result2 = FigureOperation.subtract(
                new Figure(this.maxInt), new Figure(this.maxInt * (-1)));
        assertEquals(new Figure(this.maxInt * 2), result2);
    }

    @Test
    public void multiply() {
        var result1 = FigureOperation.multiply(
                new Figure(this.firstLong), new Figure(this.secondLong));
        assertEquals(new Figure(this.firstLong * this.secondLong), result1);

        var result2 = FigureOperation.multiply(
                new Figure(this.maxInt), new Figure(this.maxInt));
        assertEquals(new Figure(this.maxInt * this.maxInt), result2);
    }

    @Test
    public void divideAndRemainder_Fraction() {
        var result = FigureOperation.divideAndRemainder(
                new Figure(this.firstLong), new Figure(this.secondLong));
        // 测试商是否正确
        assertEquals(new Figure(this.firstLong / this.secondLong), result[0]);
        // 测试余数是否正确
        assertEquals(new Figure(this.firstLong % this.secondLong), result[1]);
    }

    @Test
    public void divideAndRemainder_long() {
        var result = FigureOperation.divideAndRemainder(
                new Figure(this.firstLong), this.secondLong);
        // 测试商是否正确
        assertEquals(new Figure(this.firstLong / this.secondLong), result[0]);
        // 测试余数是否正确
        assertEquals(new Figure(this.firstLong % this.secondLong), result[1]);
    }

    @Test
    public void mod_Fraction() {
        assertEquals(new Figure(this.firstLong % this.secondLong),
                FigureOperation.mod(
                        new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void mod_long() {
        assertEquals(new Figure(this.firstLong % this.secondLong),
                FigureOperation.mod(
                        new Figure(this.firstLong), this.secondLong));
    }

    @Test
    public void modsQuotient_Fraction() {
        assertEquals(new Figure(this.firstLong / this.secondLong),
                FigureOperation.modsQuotient(
                        new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void modsQuotient_long() {
        assertEquals(new Figure(this.firstLong / this.secondLong),
                FigureOperation.modsQuotient(
                        new Figure(this.firstLong), this.secondLong));
    }

    @Test
    public void getOpposite_Fraction() {
        assertEquals(new Figure(-this.firstLong),
                FigureOperation.getOpposite(new Figure(this.firstLong)));
    }

    @Test
    public void getAbsolute() {
        assertEquals(new Figure(this.firstLong),
                FigureOperation.getAbsolute(new Figure(-this.firstLong)));
    }

    @Test
    public void getOpposite_long() {
        assertEquals(new Figure(-this.firstLong),
                FigureOperation.getOpposite(this.firstLong));
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
                        new Figure(this.firstLong), new Figure(this.secondLong)),
                FigureOperation.findGcd(
                        new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void findLcm() {
        assertEquals(AlgorithmForTest.findLcm(
                        new Figure(this.firstLong), new Figure(this.secondLong)),
                FigureOperation.findLcm(new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void power() throws MathlabCheckedException {
        // 8 = 2 ^ 3
        assertEquals(new Figure(8),
                FigureOperation.power(2, 3));
        // ? = (?) ^ (1)
        assertEquals(new Figure(this.firstLong),
                FigureOperation.power(new Figure(this.firstLong), Figure.ONE));
        // 1 = (?) ^ (0)
        assertEquals(Figure.ONE,
                FigureOperation.power(new Figure(this.firstLong), Figure.ZERO));
    }
}
