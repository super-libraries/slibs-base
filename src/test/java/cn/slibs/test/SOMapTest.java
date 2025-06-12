package cn.slibs.test;


import cn.slibs.base.map.SOHashMap;
import cn.slibs.base.map.SOLinkedHashMap;
import cn.slibs.base.map.SOMap;
import com.iofairy.falcon.time.DateTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SOMapTest {

    @Test
    void testSOMap1() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");
        SOMap map = SOMap.of().putData("date", dateTime);
        DateTime<LocalDateTime> date = map.getDateTime("date");

        System.out.println("date: " + date);

        assertEquals(date.toString(), "2023-01-01 11:00:15.003");

        SOMap soMap = SOMap.of().putData("date", new Date(), "calendar", Calendar.getInstance());
        DateTime<Date> date1 = soMap.getDateTime("date");
        DateTime<Calendar> date2 = soMap.getDateTime("calendar");
        System.out.println("date1: " + date1);
        System.out.println("date2: " + date2);
    }

    @Test
    void testSOMap2() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");

        SOMap map1 = SOMap.of();
        SOMap map2 = SOMap.of(null, "null Key's Value", null, null, "a", dateTime, "b", 2);

        System.out.println(map1);
        System.out.println(map2);

        assertEquals("{}", map1.toString());
        assertEquals("{null=null, a=2023-01-01 11:00:15.003, b=2}", map2.toString());

        try {
            SOMap.of("a", dateTime, "b");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("The parameters length must be even. ", e.getMessage());
        }
        try {
            SOMap.of("a", dateTime, dateTime, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("Index: 2. This parameter is a key, the key must be `java.lang.String` type. ", e.getMessage());
        }

        map2.putData("d", 4, "e", 5);
        System.out.println(map2);
        assertEquals("{null=null, a=2023-01-01 11:00:15.003, b=2, d=4, e=5}", map2.toString());

        map2.putData("c", 3);
        System.out.println(map2);
        assertEquals("{null=null, a=2023-01-01 11:00:15.003, b=2, c=3, d=4, e=5}", map2.toString());

    }

    @Test
    void testSOHashMap1() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");

        SOHashMap map1 = SOHashMap.of();
        SOHashMap map2 = SOHashMap.of(null, "null Key's Value", null, null, "a", dateTime, "b", 2);

        System.out.println(map1);
        System.out.println(map2);

        assertEquals("{}", map1.toString());
        assertEquals("{null=null, a=2023-01-01 11:00:15.003, b=2}", map2.toString());

        try {
            SOHashMap.of("a", dateTime, "b");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("The parameters length must be even. ", e.getMessage());
        }
        try {
            SOHashMap.of("a", dateTime, dateTime, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("Index: 2. This parameter is a key, the key must be `java.lang.String` type. ", e.getMessage());
        }

        map2.putData("d", 4, "e", 5);
        System.out.println(map2);
        assertEquals("{null=null, a=2023-01-01 11:00:15.003, b=2, d=4, e=5}", map2.toString());

        map2.putData("c", 3);
        System.out.println(map2);
        assertEquals("{null=null, a=2023-01-01 11:00:15.003, b=2, c=3, d=4, e=5}", map2.toString());

    }

    @Test
    void testSOLinkedHashMap1() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");

        SOLinkedHashMap map1 = SOLinkedHashMap.of();
        SOLinkedHashMap map2 = SOLinkedHashMap.of("a", dateTime, "b", 2);

        System.out.println(map1);
        System.out.println(map2);

        assertEquals("{}", map1.toString());
        // assertEquals("{a=2023-01-01 11:00:15.003, b=2}", map2.toString());

        try {
            SOLinkedHashMap.of("a", dateTime, "b");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("The parameters length must be even. ", e.getMessage());
        }
        try {
            SOLinkedHashMap.of("a", dateTime, dateTime, 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("Index: 2. This parameter is a key, the key must be `java.lang.String` type. ", e.getMessage());
        }

        map2.putData(null, "null Key's Value", "d", 4, "e", 5);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, null=null Key's Value, d=4, e=5}", map2.toString());

        map2.putData("c", 3, null, null);
        System.out.println(map2);
        assertEquals("{a=2023-01-01 11:00:15.003, b=2, null=null, d=4, e=5, c=3}", map2.toString());

    }


}
