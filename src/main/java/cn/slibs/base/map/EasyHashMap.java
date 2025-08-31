package cn.slibs.base.map;

import com.iofairy.except.GeneralException;
import com.iofairy.top.G;

import java.util.HashMap;
import java.util.Map;

/**
 * 更容易操作的 {@code HashMap}
 *
 * @since 0.2.0
 */
public class EasyHashMap<K, V> extends HashMap<K, V> {

    public EasyHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public EasyHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public EasyHashMap() {
        super();
    }

    public EasyHashMap(Map<? extends K, ? extends V> map) {
        super(map);
    }

    public static <K, V> EasyHashMap<K, V> of() {
        return new EasyHashMap<>();
    }

    public static <K, V> EasyHashMap<K, V> of(Object... kvs) {
        EasyHashMap<K, V> soMap = new EasyHashMap<>();
        return soMap.putData(kvs);
    }


    public static <K, V> EasyHashMap<K, V> of(Map<? extends K, ? extends V> map) {
        return new EasyHashMap<>(map);
    }

    public EasyHashMap<K, V> putData(K key, V value) {
        put(key, value);
        return this;
    }

    public EasyHashMap<K, V> putData(Object... kvs) {
        if (G.isEmpty(kvs)) return this;
        if (kvs.length % 2 != 0) throw new GeneralException("The parameters length must be even. ");

        for (int i = 0; i < kvs.length; ) {
            @SuppressWarnings("unchecked")
            K k = (K) kvs[i];
            @SuppressWarnings("unchecked")
            V v = (V) kvs[i + 1];
            put(k, v);
            i += 2;
        }
        return this;
    }

    public EasyHashMap<K, V> putData(Map<? extends K, ? extends V> map) {
        putAll(map);
        return this;
    }

    public void removeKey(K... keys) {
        if (G.isEmpty(keys)) return;
        for (K key : keys)
            remove(key);
    }

}
