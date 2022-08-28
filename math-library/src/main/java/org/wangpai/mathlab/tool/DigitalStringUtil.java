package org.wangpai.mathlab.tool;

/**
 * @since 2022-9-24
 */
public class DigitalStringUtil {
    /**
     * 向 digitalStr 加入逗号分隔符
     *
     * @param digitalStr 无逗号的数字，可包含小数点
     * @param interval 逗号之间的间隔
     * @since 2022-9-24
     */
    public static String addComma(String digitalStr, int interval) {
        if (digitalStr == null) {
            digitalStr = "";
        }

        String integerPart = "";
        String decimalPart = "";
        if (digitalStr.contains(".")) {
            integerPart = digitalStr.substring(0, digitalStr.indexOf("."));
            decimalPart = digitalStr.substring(digitalStr.indexOf("."));
        } else {
            integerPart = digitalStr;
        }

        int integerPartLength = integerPart.length();
        int commaNum = (integerPartLength - 1) / interval;
        int rest = integerPartLength % interval; // 在第一个逗号（如果有逗号的话）左边的部分
        if (rest == 0) {
            rest = interval;
        }
        StringBuilder result = new StringBuilder();
        result.append(integerPart, 0, rest);
        if (commaNum > 0) {
            result.append(",");
            int num = 0;
            for (; num < commaNum - 1; ++num) {
                result.append(integerPart, rest + interval * num, rest + interval * (num + 1));
                result.append(",");
            }
            result.append(integerPart, rest + interval * num, rest + interval * (num + 1));
        }
        return result.append(decimalPart).toString();
    }

    /**
     * 将 str 中的逗号去掉
     *
     * @since 2022-9-24
     */
    public static String removeComma(String str) {
        if (str == null) {
            str = "";
        }
        return str.replaceAll(",", "");
    }
}
