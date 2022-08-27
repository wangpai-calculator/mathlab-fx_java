package org.wangpai.mathlab.exception.tool;

import org.wangpai.mathlab.exception.MathlabException;
import org.wangpai.mathlab.exception.UnknownException;

/**
 * @since 2021-7-19
 */
public class ExceptionTool {
    public static MathlabException pkgException(Throwable exception) throws UnknownException {
        return pkgException(exception, null);
    }

    public static MathlabException pkgException(Throwable exception, String str) throws UnknownException {
        String msg = "";
        if (str != null && str != "") {
            msg = "。" + str;
        }
        throw new UnknownException("错误：发生了 " + exception.getClass().getName() + " 异常" + msg, exception);
    }
}
