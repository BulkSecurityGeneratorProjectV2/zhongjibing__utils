package icezhg.util;

/**
 * Created by zhongjibing on 2016/11/2.
 */
class StringUtil {

    static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }
}
