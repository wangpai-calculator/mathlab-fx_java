package org.wangpai.mathlab.basic.operand;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @since 2021-7-31
 */
public class RationalTest {
    private final int numeratorForTest = 10;
    private final int denominatorForTest = 20;
    private final Rational rational =
            new Rational(this.numeratorForTest, this.denominatorForTest);

    /**
     * 此构造器不能去掉，因为字段初始化抛出了异常
     *
     * @since 2021-8-3
     */
    public RationalTest() throws MathlabCheckedException {
    }

    @Test
    public void clone_test() {
        var cloned = this.rational.clone();

        assertEquals(this.rational, cloned); // 意义上相等
        assertFalse(cloned == this.rational); // 物理上不是同一个
    }

    @Test
    public void isZero() {
        assertTrue(new Rational(0).isZero());

        assertFalse(new Rational(1).isZero());
        assertFalse(this.rational.isZero());
    }

    @Test
    public void isNegative() throws MathlabCheckedException {
        assertTrue(new Rational(-1).isNegative());
        assertFalse(new Rational(0).isNegative());
        assertFalse(new Rational(1).isNegative());

        assertTrue(new Rational(-1,
                this.denominatorForTest * this.denominatorForTest)
                .isNegative());
        assertTrue(new Rational(
                this.numeratorForTest * this.numeratorForTest,
                -1).isNegative());
    }

    @Test
    public void equals() {
        // 自身比较返回 true
        assertTrue(this.rational.equals(this.rational));

        // 与 null 比较返回 false
        assertFalse(this.rational.equals(null));

        // 不是类 Operand 及其子类就返回 false
        assertFalse(this.rational.equals(BigInteger.ONE));

        var RationalOther = this.rational.clone();
        // 符合相等意义返回 true
        assertTrue(this.rational.equals(RationalOther));

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
        assertFalse(this.rational.equals(illegalStubObj));
    }

    @Test
    public void toString_test() {
        assertEquals("[" + this.rational.getNumerator().toString()
                        + "/" + this.rational.getDenominator().toString() + "]",
                this.rational.toString());
    }

    @Test
    public void isProperFraction() throws MathlabCheckedException {
        assertTrue(new Rational(2, 8).isProperFraction());
        assertFalse(new Rational(16, 8).isProperFraction());
    }

    @Test
    public void toDouble() throws MathlabCheckedException {
        /**
         * 一般来说，浮点数是不能直接比较大小的，但不知为何，此处可以进行符合直觉的比较。
         * 个人猜测，这可能是将浮点数智能转化为字符串类型来比较的
         */
        assertEquals(0.25,
                new Rational(1, 4).toDouble());
        assertEquals(0.5,
                new Rational(8, 16).toDouble());

        assertNotEquals(0.25001,
                new Rational(1, 4).toDouble());
        assertNotEquals(0.25001,
                new Rational(1, 4).toDouble());
        assertNotEquals(0.33333,
                new Rational(1, 3).toDouble());

        assertEquals(4,
                new Rational(16, 4).toDouble());

        assertNotEquals(3.33333,
                new Rational(10, 3).toDouble());
    }

    @Test
    public void reduceFraction() throws MathlabCheckedException {
        // 测试分子
        assertEquals(new Figure(-10), new Rational(100, -10).reduceFraction().getNumerator());
        // 测试分母
        assertEquals(new Figure(10), new Rational(10, -100).reduceFraction().getDenominator());
    }

    @Test
    public void getNumerator() throws MathlabCheckedException {
        assertEquals(new Figure(3),
                new Rational(21, 28).getNumerator());
    }

    @Test
    public void getDenominator() throws MathlabCheckedException {
        assertEquals(new Figure(4),
                new Rational(21, 28).getDenominator());
    }
}
