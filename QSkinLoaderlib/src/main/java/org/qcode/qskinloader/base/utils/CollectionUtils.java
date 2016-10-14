package org.qcode.qskinloader.base.utils;

import java.util.Collection;

/**
 * qqliu
 * 2016/9/25.
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection<?> collection){
        return null == collection || collection.size() <= 0;
    }

    public static <T> boolean isEmpty(T... array){
        return null == array || array.length <= 0;
    }
}
