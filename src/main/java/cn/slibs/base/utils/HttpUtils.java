package cn.slibs.base.utils;


import com.iofairy.top.G;
import com.iofairy.top.O;
import com.iofairy.top.S;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

/**
 * http相关的工具类
 *
 * @since 0.0.4
 */
public class HttpUtils {
    /**
     * 判断 contentType 是否是Json格式的
     *
     * @param contentType {@code Content-Type}
     * @return {@code Content-Type}是Json格式则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isJson(String contentType) {
        if (contentType == null) return false;
        contentType = contentType.toLowerCase();
        /*
         * application/xxx+json 如：application/problem+json
         * application/xxx-json 如：application/vnd.ipld.dag-json
         * model/gltf+json 用于描述3D模型信息的Json数据
         */
        boolean containsJson = contentType.endsWith("+json")
                || contentType.contains("+json;")
                || contentType.endsWith("-json")
                || contentType.contains("-json;");

        return contentType.equals("application/json")
                || contentType.startsWith("application/json;")
                || (contentType.startsWith("application/") && containsJson)
                || contentType.startsWith("model/gltf+json");
    }

    /**
     * 判断 contentType 是否是文本类型的流式数据（通常比较大）
     *
     * @param contentType {@code Content-Type}
     * @return true or false
     */
    public static boolean isTextStream(String contentType) {
        if (contentType == null) return false;
        contentType = contentType.toLowerCase();

        return contentType.startsWith("application/json-seq")
                || contentType.startsWith("application/x-json-stream")
                || (contentType.startsWith("application/stream+json"))
                || contentType.startsWith("text/event-stream");
    }

    /**
     * 判断 contentType 是否是二进制数据（通常为文件）
     *
     * @param contentType {@code Content-Type}
     * @return true or false
     */
    public static boolean isBinary(String contentType) {
        if (contentType == null) return false;
        contentType = contentType.toLowerCase();

        return contentType.startsWith("application/octet-stream")
                || contentType.startsWith("application/pdf")
                || (contentType.startsWith("video/"))
                || (contentType.startsWith("audio/"))
                || contentType.startsWith("image/");
    }

    /**
     * 判断 contentType 是否是 {@code multipart} 数据
     *
     * @param contentType {@code Content-Type}
     * @return true or false
     */
    public static boolean isMultipart(String contentType) {
        if (contentType == null) return false;
        contentType = contentType.toLowerCase();

        return contentType.startsWith("multipart/");
    }

    /**
     * 判断 contentType 是否是Xml格式的
     *
     * @param contentType {@code Content-Type}
     * @return {@code Content-Type}是Xml格式则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isXml(String contentType) {
        if (contentType == null) return false;
        contentType = contentType.toLowerCase();

        return contentType.equals("application/xml")
                || contentType.startsWith("application/xml;")
                || (contentType.startsWith("application/") && contentType.contains("+xml"));
    }

    /**
     * 判断 contentType 是否是<b>非文本</b>的类型 <br>
     * 注：<br>
     * <b>仅粗略判断，不够精确，对于要求高的场景，请自定义逻辑</b>
     *
     * @param contentType {@code Content-Type}
     * @return {@code Content-Type}是非文本格式则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isNonText(final String contentType) {
        if (S.isBlank(contentType)) return true;
        final String localContentType = contentType.toLowerCase();

        if (isJson(localContentType) || isXml(localContentType) || localContentType.startsWith("text/")) return false;

        long count = Stream.of("-stream", "+gzip", "+zip", "-zip", "/zip", "/pdf").filter(localContentType::endsWith).count();

        return !localContentType.startsWith("application/")
                || localContentType.startsWith("application/vnd")
                || count != 0;
    }

    /**
     * 拼接URL
     *
     * @param url 相对URL
     * @param kvs 参数列表（必须为偶数）
     * @return 拼接后的URL
     * @since 0.2.4
     */
    public static String concatUrl(String url, Object... kvs) {
        if (G.isEmpty(kvs)) {
            return url;
        } else {
            O.verifyMapKV(false, true, false, kvs);
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < kvs.length; i += 2) {
                @SuppressWarnings("unchecked")
                Set<Object> objSet = (Set<Object>) (map.computeIfAbsent((String) kvs[i], k -> new HashSet<>()));
                objSet.add(kvs[i + 1]);
            }
            return concatUrl(url, map);
        }
    }

    /**
     * 拼接URL
     *
     * @param url       相对URL
     * @param paramsMap 参数map
     * @return 拼接后的URL
     * @since 0.2.4
     */
    public static String concatUrl(String url, Map<String, ?> paramsMap) {
        if (G.isEmpty(paramsMap)) {
            return url;
        } else {
            url = url + "?";
            Set<String> params = new HashSet<>();
            paramsMap.forEach((k, v) -> {
                String key = encodeUrlUTF8(k);
                if (G.isEmpty(v)) {
                    params.add(key + "=");
                } else {
                    if (v instanceof Collection) {
                        for (Object o : ((Collection<?>) v)) {
                            params.add(key + "=" + (G.isEmpty(o) ? "" : encodeUrlUTF8(o.toString())));
                        }
                    } else if (v.getClass().isArray()) {
                        int length = Array.getLength(v);
                        for (int i = 0; i < length; i++) {
                            Object o = Array.get(v, i);
                            params.add(key + "=" + (G.isEmpty(o) ? "" : encodeUrlUTF8(o.toString())));
                        }
                    } else {
                        params.add(key + "=" + (G.isEmpty(v) ? "" : encodeUrlUTF8(v.toString())));
                    }
                }
            });
            return url + String.join("&", params);
        }
    }

    /**
     * 将URL中参数部分转换为Map
     *
     * @param queryString 查询字符串
     * @return 参数Map
     * @since 0.2.4
     */
    public static Map<String, String> getMapFromQuery(String queryString) {
        final Map<String, String[]> mapMultiValueFromQuery = getMapMultiValueFromQuery(queryString);
        final Map<String, String> map = new LinkedHashMap<>();
        mapMultiValueFromQuery.forEach((k, v) -> map.put(k, G.isEmpty(v) ? "" : v[0]));
        return map;
    }

    /**
     * 将URL中参数部分转换为Map
     *
     * @param queryString 查询字符串
     * @return 参数Map
     * @since 0.2.4
     */
    public static Map<String, String[]> getMapMultiValueFromQuery(String queryString) {
        if (S.isBlank(queryString)) return new LinkedHashMap<>();

        Map<String, Set<String>> tempMap = new LinkedHashMap<>();

        String[] kvs = queryString.split("&");
        for (String kv : kvs) {
            final String[] kvArr = kv.split("=", 2);
            String key = decodeUrlUTF8(kvArr[0]);
            String value = kvArr.length > 1 ? decodeUrlUTF8(kvArr[1]) : "";

            tempMap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        }

        Map<String, String[]> map = new LinkedHashMap<>();
        for (Map.Entry<String, Set<String>> entry : tempMap.entrySet()) {
            Set<String> values = entry.getValue();
            map.put(entry.getKey(), values.toArray(new String[0]));
        }
        return map;
    }

    /**
     * 对UTF-8字符进行url编码
     *
     * @param url url
     * @return 编码后的url
     * @since 0.2.4
     */
    public static String encodeUrlUTF8(final String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return url;
        }
    }

    /**
     * 对UTF-8编码后的url解码
     *
     * @param url url
     * @return 解码后的url
     * @since 0.2.4
     */
    public static String decodeUrlUTF8(final String url) {
        try {
            return URLDecoder.decode(url, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return url;
        }
    }

    /**
     * 先解码再编码
     *
     * @param url url
     * @return 解码再编码后的url
     * @since 0.2.4
     */
    public static String decodeAndEncodeUrlUTF8(final String url) {
        return encodeUrlUTF8(decodeUrlUTF8(url));
    }

}
