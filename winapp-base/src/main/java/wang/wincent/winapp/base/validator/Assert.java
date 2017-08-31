package wang.wincent.winapp.base.validator;

import org.apache.commons.lang.StringUtils;
import wang.wincent.winapp.common.utils.WinAppException;


public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new WinAppException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new WinAppException(message);
        }
    }
}
