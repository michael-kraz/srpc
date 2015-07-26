package io.srpc.util;

/**
 * Created by michaelkraz on 15/7/26.
 */
public class AssertUtil {

    public static void assertNotNull( Object obj, String msgTemplate, Object... params ) {

        if (obj == null) {
            throw new NullPointerException(
                    getMsg("Assert fail: should not be null", msgTemplate, params));
        }

    }

    private static String getMsg(String defaultMsg, String msgTemplate, Object[] params) {
        return msgTemplate == null ? defaultMsg : String.format( msgTemplate, params );
    }

    public static void assertTrue(boolean b, String msgTemplate, Object... params) {

        if (!b) {
            throw new IllegalArgumentException(getMsg("Assert fail: not true.", msgTemplate, params));
        }

    }


}
