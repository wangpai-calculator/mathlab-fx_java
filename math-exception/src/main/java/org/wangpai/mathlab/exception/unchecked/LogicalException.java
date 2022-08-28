package org.wangpai.mathlab.exception.unchecked;

/**
 * LogicalException 是一种禁止开发者进行某些操作的异常。因为 Java 语法有些缺陷，
 * 有些行为我们希望禁止开发者去做，但是无法从 Java 语法中保证这一点，于是有了这个类
 *
 * 此类与 SyntaxException 的区别在于，SyntaxException 是进行物理数据上无法进行的操作，
 * 而 LogicalException 是进行不管物理数据上可不可以，都要禁止的操作。
 * 这种禁止往往是事先就决定的，而不是依据程序实际运行时，数据值的变化情况而定
 *
 * 例如：
 * 本类包括：在数学上约定俗成的东西，如：0 不能作除数、整数范围内没有除法运算。在编程中，常量不能自增
 * 本类不包括：空指针异常、数组越界
 *
 * @since 2021-7-29
 */
public class LogicalException extends MathlabUncheckedException {
    public LogicalException() {
        super("异常：不符逻辑");
    }

    public LogicalException(String msg) {
        super(msg);
    }

    public LogicalException(String msg, Object obj) {
        super(msg, obj);
    }
}
