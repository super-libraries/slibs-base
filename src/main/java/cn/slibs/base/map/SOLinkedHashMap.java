package cn.slibs.base.map;

import com.iofairy.falcon.time.DateTime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 等同于 {@code LinkedHashMap<String, Object>}
 *
 * @since 0.0.1
 */
public class SOLinkedHashMap extends LinkedHashMap<String, Object> {

    public SOLinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SOLinkedHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SOLinkedHashMap() {
    }

    public SOLinkedHashMap(Map<? extends String, ?> map) {
        super(map);
    }

    public SOLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    public static SOLinkedHashMap of() {
        return new SOLinkedHashMap();
    }

    /**
     * 从key-value对中创建map
     *
     * @param kvs key-value对
     * @return map
     * @since 0.0.2
     */
    public static SOLinkedHashMap of(Object... kvs) {
        verifyPairWithStringKey(kvs);

        SOLinkedHashMap soMap = new SOLinkedHashMap();
        for (int i = 0; i < kvs.length; ) {
            soMap.put((String) kvs[i], kvs[i + 1]);
            i += 2;
        }

        return soMap;
    }


    public static SOLinkedHashMap of(Map<? extends String, ?> map) {
        return new SOLinkedHashMap(map);
    }

    public SOLinkedHashMap putData(String key, Object value) {
        put(key, value);
        return this;
    }

    /**
     * 批量 put 多个键值对
     *
     * @param kvs key-value对
     * @return map
     * @since 0.0.2
     */
    public SOLinkedHashMap putData(Object... kvs) {
        verifyPairWithStringKey(kvs);

        for (int i = 0; i < kvs.length; ) {
            put((String) kvs[i], kvs[i + 1]);
            i += 2;
        }
        return this;
    }

    public SOLinkedHashMap putData(Map<String, Object> map) {
        putAll(map);
        return this;
    }

    /*
     * get 方法
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(String key) {
        return (T) get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public Date getDate(String key) {
        return (Date) get(key);
    }

    public Calendar getCalendar(String key) {
        return (Calendar) get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> DateTime<T> getDateTime(String key) {
        return (DateTime<T>) get(key);
    }

    public BigDecimal getBigDecimal(String key) {
        return (BigDecimal) get(key);
    }

    public BigInteger getBigInteger(String key) {
        return (BigInteger) get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) get(key);
    }

    public int getIntValue(String key) {
        return (int) get(key);
    }

    public Long getLong(String key) {
        return (Long) get(key);
    }

    public long getLongValue(String key) {
        return (long) get(key);
    }

    public Float getFloat(String key) {
        return (Float) get(key);
    }

    public float getFloatValue(String key) {
        return (float) get(key);
    }

    public Double getDouble(String key) {
        return (Double) get(key);
    }

    public double getDoubleValue(String key) {
        return (double) get(key);
    }

    public Short getShort(String key) {
        return (Short) get(key);
    }

    public short getShortValue(String key) {
        return (short) get(key);
    }

    public Byte getByte(String key) {
        return (Byte) get(key);
    }

    public byte[] getBytes(String key) {
        return (byte[]) get(key);
    }

    public byte getByteValue(String key) {
        return (byte) get(key);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) get(key);
    }

    public boolean getBooleanValue(String key) {
        return (boolean) get(key);
    }


    static void verifyPairWithStringKey(Object... kvs) {
        if (kvs != null) {
            if (kvs.length % 2 != 0)
                throw new RuntimeException("The parameters length must be even. 参数个数必须为偶数。");
            for (int i = 0; i < kvs.length; i++) {
                if (i % 2 == 0) {
                    try {
                        String k = (String) kvs[i];
                    } catch (ClassCastException castException) {
                        throw new ClassCastException("Index: " + i + ". This parameter is a key, the key must be `String` type. ");
                    }
                }
            }
        }
    }
}
