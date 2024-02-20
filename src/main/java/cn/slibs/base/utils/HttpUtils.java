package cn.slibs.base.utils;


import com.iofairy.top.S;

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
    public static boolean isJsonContentType(String contentType) {
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
     * 判断 contentType 是否是Xml格式的
     *
     * @param contentType {@code Content-Type}
     * @return {@code Content-Type}是Xml格式则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isXmlContentType(String contentType) {
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
    public static boolean isNonTextContentType(final String contentType) {
        if (S.isBlank(contentType)) return true;
        final String localContentType = contentType.toLowerCase();

        if (isJsonContentType(localContentType) || isXmlContentType(localContentType) || localContentType.startsWith("text/")) return false;

        long count = Stream.of("-stream", "+gzip", "+zip", "-zip", "/zip", "/pdf").filter(localContentType::endsWith).count();

        return !localContentType.startsWith("application/")
                || localContentType.startsWith("application/vnd")
                || count != 0;
    }

}
