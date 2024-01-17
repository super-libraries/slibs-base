package cn.slibs.base.map;

import com.iofairy.top.O;

import java.util.Map;

/**
 * 等同于 {@code HashMap<String, Object>}
 *
 * @since 0.0.1
 */
public class SOHashMap extends SOMap {

    public SOHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SOHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SOHashMap() {
        super();
    }

    public SOHashMap(Map<? extends String, ?> map) {
        super(map);
    }

    public static SOHashMap of() {
        return new SOHashMap();
    }

    /**
     * 从key-value对中创建map
     *
     * @param kvs key-value对
     * @return map
     * @since 0.0.2
     */
    public static SOHashMap of(Object... kvs) {
        O.verifyMapKV(true, true, false, kvs);

        SOHashMap soMap = new SOHashMap();
        for (int i = 0; i < kvs.length; ) {
            soMap.put((String) kvs[i], kvs[i + 1]);
            i += 2;
        }

        return soMap;
    }


    public static SOHashMap of(Map<? extends String, ?> map) {
        return new SOHashMap(map);
    }

    @Override
    public SOHashMap putData(String key, Object value) {
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
    @Override
    public SOHashMap putData(Object... kvs) {
        O.verifyMapKV(true, true, false, kvs);

        for (int i = 0; i < kvs.length; ) {
            put((String) kvs[i], kvs[i + 1]);
            i += 2;
        }
        return this;
    }

    @Override
    public SOHashMap putData(Map<String, Object> map) {
        putAll(map);
        return this;
    }
}
