package cn.slibs.test;

import cn.slibs.base.utils.StringTool;
import com.iofairy.except.ConditionsNotMetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GG
 * @version 1.0
 * @date 2024/1/1 18:11
 */
public class StringToolTest {
    @Test
    void testConvertEncode() {
        try {
            StringTool.convertEncode("abc", "utf-8", " ");
        } catch (Exception e) {
            assertEquals(ConditionsNotMetException.class, e.getClass());
            assertEquals(e.getMessage(), "None of these parameters [fromCharset, toCharset] can be blank! But parameter [toCharset] is blank! ");
        }
    }

    @Test
    void testAddQuote() {
        System.out.println("===============================testAddQuote=============================");
        String str = "ab\"c";
        String s1 = StringTool.addQuote(str);
        System.out.println(s1);
        assertEquals("\"ab\"c\"", s1);
    }

    @Test
    void testForSqlLike() {
        System.out.println("===============================testForSqlLike=============================");
        String str = "abc";
        String s1 = StringTool.forSqlLike(str);
        System.out.println(s1);
        assertEquals("%abc%", s1);
    }

    @Test
    void testToCountSql() {
        System.out.println("===============================testToCountSql=============================");
        String str = "select * from table1";
        String s1 = StringTool.toCountSql(str);
        System.out.println(s1);
        assertEquals("select count(*) cnt from ( \n\tselect * from table1\n) t", s1);

        try {
            StringTool.toCountSql(" ");
        } catch (Exception e) {
            assertEquals(ConditionsNotMetException.class, e.getClass());
            assertEquals(e.getMessage(), "The parameter `sql` must be non-blank! ");
        }

    }

}
