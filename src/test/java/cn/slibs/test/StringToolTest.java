package cn.slibs.test;

import cn.slibs.base.utils.StringTool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2024/1/1 18:11
 */
public class StringToolTest {
    @Test
    void testAddQuote() {
        String str = "ab\"c";
        String s1 = StringTool.addQuote(str);
        System.out.println(s1);
        assertEquals("\"ab\"c\"", s1);
    }

    @Test
    void testForSqlLike() {
        String str = "abc";
        String s1 = StringTool.forSqlLike(str);
        System.out.println(s1);
        assertEquals("%abc%", s1);
    }

    @Test
    void testToCountSql() {
        String str = "select * from table1";
        String s1 = StringTool.toCountSql(str);
        System.out.println(s1);
        assertEquals("select count(*) cnt from ( \n\tselect * from table1\n) t", s1);
    }

}
