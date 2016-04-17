package org.study.util

/**
 * Created by Administrator on 2016/4/15.
 */
class StringUtil {
    /**
     *  首字母小写
     * @param name
     * @return
     */
    static String lowerCaseFirstLetter(String v) {
        char[] cs=v.toCharArray()
        cs[0]+=32
        String.valueOf(cs)
    }
}
