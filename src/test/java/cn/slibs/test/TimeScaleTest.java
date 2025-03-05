package cn.slibs.test;

import cn.slibs.base.core.TimeScale;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2025/3/5 8:32
 */
public class TimeScaleTest {
    private static List<Integer> unitIndex = Arrays.asList(0, 1, 2, 3, 4, 10, 11, 12, 13, 14, 15, 16, 17,
            20, 21, 22, 23, 24, 25, 26, 30, 31, 32, 33, 34, 40, 41, 42, 43, 44, 45, 46, 47, 50, -1);

    @Test
    void testTimeScale() {
        System.out.println(TimeScale.INDEX_TS);
        System.out.println(TimeScale.OFTEN1_INDEX_TS);
        System.out.println(TimeScale.OFTEN2_INDEX_TS);
        System.out.println(TimeScale.UNIT_INDEX_TS);
        System.out.println(TimeScale.EN_NAME_TS);
        System.out.println(TimeScale.CH_NAME_TS);
        System.out.println(TimeScale.CH_NAME_TS.keySet());

        assertEquals(TimeScale.INDEX_TS.size(), 35);
        assertEquals(TimeScale.OFTEN1_INDEX_TS.size(), 35);
        assertEquals(TimeScale.OFTEN2_INDEX_TS.size(), 35);
        assertEquals(TimeScale.UNIT_INDEX_TS.size(), 35);
        assertEquals(TimeScale.EN_NAME_TS.size(), 36);
        assertEquals(TimeScale.CH_NAME_TS.size(), 59);
        assertEquals(TimeScale.CH_NAME_TS.keySet().toString(),
                "[秒, 5秒, 10秒, 15秒, 30秒, 分, 2分钟, 2分, 3分钟, 3分, 4分钟, 4分, 5分钟, 5分, 10分钟, 10分, 15分钟, 15分, 刻, 30分钟, 30分, " +
                        "时, 2小时, 2时, 3小时, 3时, 4小时, 4时, 5小时, 5时, 6小时, 6时, 12小时, 12时, 天, 日, 5天, 5日, 10天, 10日, 15天, 15日, " +
                        "周, 月, 2个月, 2月, 3个月, 3月, 6个月, 6月, 月01日, 2个月01日, 2月01日, 3个月01日, 3月01日, 6个月01日, 6月01日, 年, 不定时]");


        Set<Integer> indexSet = new HashSet<>(unitIndex);
        assertEquals(indexSet.size(), unitIndex.size());
        for (Integer i : indexSet) {
            assertNotNull(TimeScale.unitIndex2TS(i));
        }

        for (int i = -1; i <= 33; i++) {
            assertNotNull(TimeScale.index2TS(i));
            assertNotNull(TimeScale.often1Index2TS(i));
            assertNotNull(TimeScale.often2Index2TS(i));
        }


        assertEquals(TimeScale.enName2TS("Quarter"), TimeScale.MIN15);
        assertEquals(TimeScale.enName2TS("MIN15"), TimeScale.MIN15);
        assertEquals(TimeScale.chName2TS("5天"), TimeScale.DAY5);
        assertEquals(TimeScale.chName2TS("5日"), TimeScale.DAY5);
        assertEquals(TimeScale.chName2TS("刻"), TimeScale.MIN15);
        assertEquals(TimeScale.chName2TS("15分钟"), TimeScale.MIN15);
        assertEquals(TimeScale.chName2TS("15分"), TimeScale.MIN15);
        assertEquals(TimeScale.chName2TS("3小时"), TimeScale.HOUR3);
        assertEquals(TimeScale.chName2TS("3时"), TimeScale.HOUR3);
        assertEquals(TimeScale.chName2TS("6个月"), TimeScale.MONTH6);
        assertEquals(TimeScale.chName2TS("6月"), TimeScale.MONTH6);

        try {
            TimeScale.INDEX_TS.put(1, null);

            throw new RuntimeException();
        } catch (Exception e) {
            assertSame(UnsupportedOperationException.class, e.getClass());
        }

    }

}
