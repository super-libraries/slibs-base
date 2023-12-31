package cn.slibs.test;


import cn.slibs.base.map.SOMap;
import com.iofairy.falcon.time.DateTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SOMapTest {

    @Test
    void testSOMap() {
        DateTime<LocalDateTime> dateTime = DateTime.parse("2023/1/01 11:00:15.003");
        SOMap map = SOMap.of().putData("date", dateTime);
        DateTime<LocalDateTime> date = map.getDateTime("date");

        System.out.println(date);

        assertEquals(date.toString(), "2023-01-01 11:00:15.003");
    }

}
