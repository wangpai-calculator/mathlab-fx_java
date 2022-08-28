package org.wangpai.mathlab.basic.operand;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.basic.enumeration.Symbol;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;
import org.wangpai.mathlab.exception.checked.UndefinedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wangpai.mathlab.basic.enumeration.Symbol.ADD;
import static org.wangpai.mathlab.basic.enumeration.Symbol.DOT;
import static org.wangpai.mathlab.basic.enumeration.Symbol.FIVE;
import static org.wangpai.mathlab.basic.enumeration.Symbol.FOUR;
import static org.wangpai.mathlab.basic.enumeration.Symbol.ONE;
import static org.wangpai.mathlab.basic.enumeration.Symbol.SUBTRACT;
import static org.wangpai.mathlab.basic.enumeration.Symbol.THREE;
import static org.wangpai.mathlab.basic.enumeration.Symbol.TWO;
import static org.wangpai.mathlab.basic.enumeration.Symbol.ZERO;

public class DecimalTest {
    Symbol[] zero = {ZERO}; // 0
    Symbol[] positive = {ONE, TWO, THREE}; // 正数
    Symbol[] negative = {SUBTRACT, ONE, TWO, THREE}; // 负数
    Symbol[] properFraction = {ZERO, DOT, ONE, TWO, THREE}; // 真分数
    Symbol[] decimal = {SUBTRACT, ONE, DOT, TWO, THREE}; // 小数

    Symbol[] wrongFirst = {ADD, ONE, TWO, THREE}; // 第一个为加号，错误
    Symbol[] doubleSubtract = {SUBTRACT, ONE, SUBTRACT, TWO, THREE}; // 有两个减号，错误
    Symbol[] innerSubtract = {ONE, SUBTRACT, TWO, THREE}; // 中间有减号，错误
    Symbol[] doublePoint = {DOT, ONE, DOT, TWO, THREE}; // 有两个减号，错误
    Symbol[] beginDot = {DOT, ONE, TWO, DOT, THREE}; // 小数点位置错误

    /**
     * @since 2021-8-2
     */
    @Test
    public void arrayIsDecimal() {
        assertTrue(Decimal.arrayIsDecimal(this.zero));
        assertTrue(Decimal.arrayIsDecimal(this.positive));
        assertTrue(Decimal.arrayIsDecimal(this.negative));
        assertTrue(Decimal.arrayIsDecimal(this.properFraction));
        assertTrue(Decimal.arrayIsDecimal(this.decimal));

        assertFalse(Decimal.arrayIsDecimal(this.wrongFirst));
        assertFalse(Decimal.arrayIsDecimal(this.doubleSubtract));
        assertFalse(Decimal.arrayIsDecimal(this.innerSubtract));
        assertFalse(Decimal.arrayIsDecimal(this.doublePoint));
        assertFalse(Decimal.arrayIsDecimal(this.beginDot));
        assertFalse(Decimal.arrayIsDecimal(null));
    }

    @Test
    public void toRational() throws MathlabCheckedException {
        Symbol[] lessThanZero = {ZERO, DOT, FIVE}; // 0.5
        Symbol[] largerThanZero = {ONE, DOT, FIVE}; // 1.5
        Symbol[] lessThanZeroNegative = {SUBTRACT, ZERO, DOT, FIVE}; // -0.5
        Symbol[] largerThanZeroNegative = {SUBTRACT, ONE, DOT, FIVE}; // -1.5

        assertEquals(new Rational(0),
                new Decimal(this.zero).toRational());
        assertEquals(new Rational(1, 2),
                new Decimal(lessThanZero).toRational());
        assertEquals(new Rational(3, 2),
                new Decimal(largerThanZero).toRational());
        assertEquals(new Rational(-1, 2),
                new Decimal(lessThanZeroNegative).toRational());
        assertEquals(new Rational(-3, 2),
                new Decimal(largerThanZeroNegative).toRational());
    }

    public static void main(String[] args) throws UndefinedException {
        Symbol[] symbols = {ONE, DOT, FOUR}; // 小数

        Decimal decimal = new Decimal(symbols);
        var rational = decimal.toRational();

        Decimal decimal2 = new Decimal().init("4.6");
        var rational2 = decimal2.toRational();

        System.out.println(rational);
    }
}
