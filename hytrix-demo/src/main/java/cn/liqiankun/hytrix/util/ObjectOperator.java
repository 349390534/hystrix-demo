package cn.liqiankun.hytrix.util;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * @author wangxiaotian 用unsafe类操作对象
 */
@SuppressWarnings("restriction")
public final class ObjectOperator {
    private static Unsafe unsafe;

    static {
        try {
            unsafe = getUnsafeInstance();
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectOperator() {

    }

    public static Object getPrivateFieldValue(Object obj, @SuppressWarnings("rawtypes") Class clz, String fieldName) {
        @SuppressWarnings("unused")
        Field fieldColor;
        try {
            Field field = clz.getDeclaredField(fieldName);
            Long offset = unsafe.objectFieldOffset(field);
            return unsafe.getObject(obj, offset);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static Unsafe getUnsafeInstance()
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }
}
