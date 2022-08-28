package org.wangpai.mathlab.basic.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.basic.operand.Decimal;
import org.wangpai.mathlab.basic.operand.Figure;
import org.wangpai.mathlab.basic.operand.Rational;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;
import org.wangpai.mathlab.exception.checked.SyntaxException;
import org.wangpai.mathlab.exception.checked.UndefinedException;
import org.wangpai.mathlab.exception.unchecked.UnexpectedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 2021-7-22
 */
public class RationalOperationTest {
    private static final long START_TIME = System.currentTimeMillis();

    private int firstInt = 234234;
    private int secondInt = 2341;

    @Test
    public void getOpposite() throws MathlabCheckedException {
        assertEquals(new Rational(-this.firstInt, this.secondInt),
                RationalOperation.getOpposite
                        (new Rational(this.firstInt, this.secondInt)));
    }

    @Test
    public void getAbsolute() throws MathlabCheckedException {
        assertEquals(new Rational(this.firstInt, this.secondInt),
                RationalOperation.getAbsolute
                        (new Rational(this.firstInt, -this.secondInt)));
    }

    @Test
    public void getReciprocal() throws MathlabCheckedException {
        assertEquals(new Rational(this.secondInt, this.firstInt),
                RationalOperation.getReciprocal
                        (new Rational(this.firstInt, this.secondInt)));
    }

    @Test
    public void power() throws MathlabCheckedException {
        // 8 = 2 ^ 3
        assertEquals(new Rational(8),
                RationalOperation.power(new Rational(2), new Figure(3)));
        // 1/8 = 2 ^ (-3)
        assertEquals(new Rational(1, 8),
                RationalOperation.power(new Rational(2), new Figure(-3)));
        // -1/8 = (-2) ^ (-3)
        assertEquals(new Rational(-1, 8),
                RationalOperation.power(new Rational(-2), new Figure(-3)));
        // ? = (?) ^ (1)
        assertEquals(new Rational(this.firstInt, this.secondInt),
                RationalOperation.power(new Rational(this.firstInt, this.secondInt), Figure.ONE));
        // 1 = (?) ^ (0)
        assertEquals(new Rational(1),
                RationalOperation.power(new Rational(this.firstInt, this.secondInt), Figure.ZERO));

        // 0 的负数次方引发异常
        assertThrows(SyntaxException.class,
                () -> RationalOperation.power(new Rational(0), new Figure(-3)));
    }

    @Test
    public void roundUp() throws MathlabCheckedException {
        var test1 = new Rational(3, 2);
        assertEquals(new Rational(2),
                RationalOperation.roundUp(test1));

        var test2 = new Rational(3, 1);
        assertEquals(test2, RationalOperation.roundUp(test2));

        var test111 = new Rational(20, 13);
        var test333 = RationalOperation.roundUp(test111);
        assertEquals(test2, RationalOperation.roundUp(test2));
    }

    @Test
    public void roundDown() throws MathlabCheckedException {
        var test1 = new Rational(3, 2);
        assertEquals(new Rational(1),
                RationalOperation.roundDown(test1));

        var test2 = new Rational(3, 1);
        assertEquals(test2, RationalOperation.roundDown(test2));
    }

    @Test
    void random0To1_void() throws SyntaxException {
        int maxTimes = 1000000;
        Rational sum = Rational.ZERO.clone();
        for (int time = 0; time < maxTimes; ++time) {
            Rational random0To1 = RationalOperation.random0To1();
            sum = RationalOperation.add(sum, random0To1);
//            System.out.println(random0To1.toDouble());
        }
//        System.out.println("-----------");
        System.out.println(RationalOperation.divide(sum, new Rational(maxTimes)).toDouble());
        System.out.println(String.format("******** 应用后台程序退出：%dms ********",
                System.currentTimeMillis() - START_TIME));
    }

    @Test
    void random0To1_int() throws SyntaxException {
        int maxTimes = 1000000;
        Rational sum = Rational.ZERO.clone();
        for (int time = 0; time < maxTimes; ++time) {
            Rational random0To1 = RationalOperation.random(100);
            sum = RationalOperation.add(sum, random0To1);
//            System.out.println(random0To1.toDouble());
        }
//        System.out.println("-----------");
        System.out.println(RationalOperation.divide(sum, new Rational(maxTimes)).toDouble());
        System.out.println(String.format("******** 应用后台程序退出：%dms ********",
                System.currentTimeMillis() - START_TIME));
    }

    @Test
    void random0To1_old() throws SyntaxException {
        int maxTimes = 100000;
        Rational sum = Rational.ZERO.clone();
        int accuracy = 10;
        for (int time = 0; time < maxTimes; ++time) {
            Rational random0To1 = random0To1_old(accuracy);
            sum = RationalOperation.add(sum, random0To1);
//            System.out.println(random0To1.toDouble());
        }
//        System.out.println("-----------");
        System.out.println(RationalOperation.divide(sum, new Rational(maxTimes)).toDouble());
        System.out.println(String.format("******** 应用后台程序退出：%dms ********",
                System.currentTimeMillis() - START_TIME));
    }

    @Test
    void random0To1_NewCompareOld() throws SyntaxException {
        int maxTimes = 1000000;
        long newInterval;
        long oldInterval;
        {
            final long startTimeNew = System.currentTimeMillis();
            for (int time = 0; time < maxTimes; ++time) {
                RationalOperation.random0To1();
            }
            newInterval = System.currentTimeMillis() - startTimeNew;
            System.out.println(String.format("******** 应用后台程序退出：%dms ********", newInterval));
        }

        {
            final long startTimeOld = System.currentTimeMillis();
            for (int time = 0; time < maxTimes; ++time) {
                random0To1_old(10);
            }
            oldInterval = System.currentTimeMillis() - startTimeOld;
            System.out.println(String.format("******** 应用后台程序退出：%dms ********", oldInterval));
        }
        System.out.println(oldInterval * 1.0 / newInterval);
    }

    /**
     * 这是 RationalOperation.random0To1() 的以前版本
     *
     * 得到 0 至 1 的随机数
     *
     * @param accuracy ：它代表产生小数点后保留多少位的小数。这种保留是一种四舍五入。
     *                 如当 accuracy 为 1 时，只会产生如 0.1、0.2 这样的随机数
     * @since 2022-8-24
     */
    public static Rational random0To1_old(int accuracy) {
        // Math.random() 会得到 0 至 1 的浮点数
        var doubleString = String.format("%." + accuracy + "f", Math.random());
        Decimal decimal = null;
        try {
            decimal = new Decimal(doubleString);
        } catch (UndefinedException exception) {
            // 这个异常应该不会产生
            throw new UnexpectedException(exception);
        }
        return decimal.toRational();
    }
}
