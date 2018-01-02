package org.qcode.qskinloader.base.utils;

import java.lang.reflect.Field;

/***
 * 反射帮助类
 * created at 2017/12/31
 */
public class ReflectUtils {

    private static final String TAG = "ReflectUtils";

    private static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
        for (Class clz = object.getClass(); Object.class != clz; clz = clz.getSuperclass()) {
            try {
                return clz.getDeclaredField(fieldName);
            } catch (Exception ex) {
                Logging.d(TAG, "getDeclaredField()| field " + fieldName + " is not in class: " + clz.getSimpleName());
            }
        }

        throw new NoSuchFieldException("field " + fieldName + " NOT found");
    }

    /***
     * 获取指定field的value
     *
     * @param object
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getFieldValue(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        if (null == object || StringUtils.isEmpty(fieldName)) {
            return null;
        }

        Field field = getDeclaredField(object, fieldName);
        field.setAccessible(true);
        Object value = field.get(object);
        return (T) value;
    }

    /***
     * 获取指定field的value，无异常
     *
     * @param obj
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getFieldValueOpt(Object obj, String fieldName) {
        try {
            return getFieldValue(obj, fieldName);
        } catch (Exception ex) {
            Logging.d(TAG, "getFieldValueOpt()| error happened", ex);
            return null;
        }
    }

    /***
     * 设置field的值
     *
     * @param object
     * @param fieldName
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Object object, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        if (null == object || StringUtils.isEmpty(fieldName)) {
            return;
        }

        Field field = getDeclaredField(object, fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    /***
     * 设置field的值，无异常
     *
     * @param object
     * @param fieldName
     * @param value
     */
    public static void setFieldValueOpt(Object object, String fieldName, Object value) {
        try {
            setFieldValue(object, fieldName, value);
        } catch (Exception ex) {
            Logging.d(TAG, "setFieldValueOpt()| error happened", ex);
        }
    }
}
