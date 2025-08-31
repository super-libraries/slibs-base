package cn.slibs.base.map;

import com.iofairy.except.GeneralException;
import com.iofairy.top.G;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 更容易操作的 {@code LinkedHashMap}
 *
 * @since 0.2.0
 */
public class EasyLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    public EasyLinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public EasyLinkedHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public EasyLinkedHashMap() {
    }

    public EasyLinkedHashMap(Map<? extends K, ? extends V> map) {
        super(map);
    }

    public EasyLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    public static <K, V> EasyLinkedHashMap<K, V> of() {
        return new EasyLinkedHashMap<>();
    }


    public static <K, V> EasyLinkedHashMap<K, V> of(Object... kvs) {
        return new EasyLinkedHashMap<K, V>().putData(kvs);
    }


    public static <K, V> EasyLinkedHashMap<K, V> of(Map<? extends K, ? extends V> map) {
        return new EasyLinkedHashMap<>(map);
    }

    public EasyLinkedHashMap<K, V> putData(K key, V value) {
        put(key, value);
        return this;
    }


    public EasyLinkedHashMap<K, V> putData(Object... kvs) {
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

    public EasyLinkedHashMap<K, V> putData(Map<? extends K, ? extends V> map) {
        putAll(map);
        return this;
    }

    public void removeKey(K... keys) {
        if (G.isEmpty(keys)) return;
        for (K key : keys)
            remove(key);
    }

}
