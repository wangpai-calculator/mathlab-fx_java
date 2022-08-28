package org.wangpai.mathlab.basic.operand;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.exception.unchecked.LogicalException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @since 2021-7-22
 */
public class FigureTest {
    private final int numForTest = 1;

    @Test
    public void getFraction() {
        assertEquals(BigInteger.valueOf(this.numForTest),
                new Figure(this.numForTest).toBigInteger());
    }

    @Test
    public void clone_test() throws CloneNotSupportedException {
        var figure = new Figure(this.numForTest);
        var cloned = figure.clone();

        assertEquals(figure, cloned); // 意义上相等
        assertFalse(cloned == figure); // 物理上不是同一个
    }

    @Test
    public void toString_test() {
        assertEquals(new Figure(this.numForTest).toBigInteger().toString(),
                new Figure(this.numForTest).toString());
    }

    @Test
    public void equals() throws CloneNotSupportedException {
        var figure = new Figure(this.numForTest);

        // 自身比较返回 true
        assertTrue(figure.equals(figure));

        // 与 null 比较返回 false
        assertFalse(figure.equals(null));

        // 不是类 Operand 及其子类就返回 false
        assertFalse(figure.equals(BigInteger.ONE));

        var figureOther = figure.clone();
        // 符合相等意义返回 true
        assertTrue(figure.equals(figureOther));

        var illegalStubObj = new Operand() {
            @Override
            public boolean isZero() {
                return false;
            }

            @Override
            public boolean isPositive() {
                return false;
            }
        };
        // 与非法对象比较返回 false
        assertFalse(figure.equals(illegalStubObj));
    }

    @Test
    public void isZero() {
        assertTrue(new Figure(0).isZero());
        assertFalse(new Figure(1).isZero());
    }

    @Test
    public void isPositive() {
        assertTrue(new Figure(1).isPositive());
        assertFalse(new Figure(0).isPositive());
        assertFalse(new Figure(-1).isPositive());
    }

    @Test
    public void isNegative() {
        assertTrue(new Figure(-1).isNegative());
        assertFalse(new Figure(0).isNegative());
        assertFalse(new Figure(1).isNegative());
    }

    @Test
    public void increaseOne() throws LogicalException {
        int num = 23;
        assertEquals(new Figure(num + 1), new Figure(num).increaseOne());

        long one = 1;
        assertEquals(new Figure(Integer.MAX_VALUE + one), new Figure(Integer.MAX_VALUE).increaseOne());
        assertEquals(new Figure(Integer.MIN_VALUE), new Figure(Integer.MIN_VALUE - one).increaseOne());
    }

    @Test
    public void decreaseOne() throws LogicalException {
        int num = 23;
        assertEquals(new Figure(num - 1), new Figure(num).decreaseOne());

        long one = 1;
        assertEquals(new Figure(Integer.MIN_VALUE - one), new Figure(Integer.MIN_VALUE).decreaseOne());
        assertEquals(new Figure(Integer.MAX_VALUE), new Figure(Integer.MAX_VALUE + one).decreaseOne());
    }
}
