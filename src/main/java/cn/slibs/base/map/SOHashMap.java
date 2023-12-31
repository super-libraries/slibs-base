package cn.slibs.base.map;

import java.util.Map;

/**
 * 等同于 {@code HashMap<String, Object>}
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

    public static SOHashMap of(Map<? extends String, ?> map) {
        return new SOHashMap(map);
    }

    @Override
    public SOHashMap putData(String key, Object value) {
        put(key, value);
        return this;
    }

    @Override
    public SOHashMap putData(Map<String, Object> map) {
        putAll(map);
        return this;
    }
}
