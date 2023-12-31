package cn.slibs.base.map;

import com.iofairy.falcon.time.DateTime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 等同于 {@code HashMap<String, Object>}
 */
public class SOMap extends HashMap<String, Object> {

    public SOMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SOMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SOMap() {
        super();
    }

    public SOMap(Map<? extends String, ?> map) {
        super(map);
    }

    public static SOMap of() {
        return new SOMap();
    }

    public static SOMap of(Map<? extends String, ?> map) {
        return new SOMap(map);
    }

    public SOMap putData(String key, Object value) {
        put(key, value);
        return this;
    }

    public SOMap putData(Map<String, Object> map) {
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
}
