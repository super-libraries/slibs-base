package cn.slibs.base.utils;

import cn.slibs.base.misc.Const;
import com.iofairy.top.S;

/**
 * string工具类
 *
 * @since 0.0.2
 */
public class StringTool {

    /**
     * 字符编码转换
     *
     * @param str         字符串
     * @param fromCharset 原始编码
     * @param toCharset   目标编码
     * @return 转换编码后的字符串
     */
    public static String convertEncode(String str, String fromCharset, String toCharset) {
        try {
            return str == null ? null : new String(str.getBytes(fromCharset), toCharset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在字符串两侧加上双引号
     *
     * @param str 字符串
     * @return 带双引号的字符串
     */
    public static String addQuote(String str) {
        return null == str ? null : Const.QUOTE + str + Const.QUOTE;
    }

    /**
     * 对字符串前后加 {@code %}，用于SQL模糊查询
     *
     * @param keyword 关键词
     * @return 加上 {@code %} 的关键词
     */
    public static String forSqlLike(String keyword) {
        return S.isEmpty(keyword) ? null : "%" + keyword + "%";
    }

    /**
     * 将 sql 转成查询条数的sql
     *
     * @param sql sql语句
     * @return 查询条数的sql语句
     */
    public static String toCountSql(String sql) {
        return "select count(*) cnt from ( \n\t" + sql + "\n) t";
    }

}
