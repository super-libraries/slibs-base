package cn.slibs.test;


import cn.slibs.base.map.SOHashMap;
import cn.slibs.base.map.SOLinkedHashMap;
import cn.slibs.base.map.SOMap;
import com.iofairy.falcon.time.DateTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SOMapTest {

    @Test
    void testSOMap1() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");
        SOMap map = SOMap.of().putData("date", dateTime);
        DateTime<LocalDateTime> date = map.getDateTime("date");

        System.out.println(date);

        assertEquals(date.toString(), "2023-01-01 11:00:15.003");
    }

    @Test
    void testSOMap2() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");

        SOMap map1 = SOMap.of();
        SOMap map2 = SOMap.of("a", dateTime, "b", 2);

        System.out.println(map1);
        System.out.println(map2);

        assertEquals("{}", map1.toString());
        assertEquals("{a=2023-01-01 11:00:15.003, b=2}", map2.toString());

        try {
            SOMap.of("a", dateTime, "b");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("The parameters length must be even. 参数个数必须为偶数。", e.getMessage());
        }
        try {
            SOMap.of("a", dateTime, dateTime, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("Index: 2. This parameter is a key, the key must be `String` type. ", e.getMessage());
        }

        map2.putData("d", 4, "e", 5);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, d=4, e=5}", map2.toString());

        map2.putData("c", 3);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, c=3, d=4, e=5}", map2.toString());

    }

    @Test
    void testSOHashMap1() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");

        SOHashMap map1 = SOHashMap.of();
        SOHashMap map2 = SOHashMap.of("a", dateTime, "b", 2);

        System.out.println(map1);
        System.out.println(map2);

        assertEquals("{}", map1.toString());
        assertEquals("{a=2023-01-01 11:00:15.003, b=2}", map2.toString());

        try {
            SOHashMap.of("a", dateTime, "b");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("The parameters length must be even. 参数个数必须为偶数。", e.getMessage());
        }
        try {
            SOHashMap.of("a", dateTime, dateTime, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("Index: 2. This parameter is a key, the key must be `String` type. ", e.getMessage());
        }

        map2.putData("d", 4, "e", 5);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, d=4, e=5}", map2.toString());

        map2.putData("c", 3);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, c=3, d=4, e=5}", map2.toString());

    }

    @Test
    void testSOLinkedHashMap1() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");

        SOLinkedHashMap map1 = SOLinkedHashMap.of();
        SOLinkedHashMap map2 = SOLinkedHashMap.of("a", dateTime, "b", 2);

        System.out.println(map1);
        System.out.println(map2);

        assertEquals("{}", map1.toString());
        assertEquals("{a=2023-01-01 11:00:15.003, b=2}", map2.toString());

        try {
            SOLinkedHashMap.of("a", dateTime, "b");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("The parameters length must be even. 参数个数必须为偶数。", e.getMessage());
        }
        try {
            SOLinkedHashMap.of("a", dateTime, dateTime, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("Index: 2. This parameter is a key, the key must be `String` type. ", e.getMessage());
        }

        map2.putData("d", 4, "e", 5);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, d=4, e=5}", map2.toString());

        map2.putData("c", 3);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, d=4, e=5, c=3}", map2.toString());

    }


}
