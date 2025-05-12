package cn.slibs.test;

import cn.slibs.base.core.TimeScale;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        System.out.println("INDEX_TS       : " + TimeScale.INDEX_TS);
        System.out.println("OFTEN1_INDEX_TS: " + TimeScale.OFTEN1_INDEX_TS);
        System.out.println("OFTEN2_INDEX_TS: " + TimeScale.OFTEN2_INDEX_TS);
        System.out.println("UNIT_INDEX_TS  : " + TimeScale.UNIT_INDEX_TS);
        System.out.println("EN_NAME_TS     : " + TimeScale.EN_NAME_TS);
        System.out.println("CH_NAME_TS     : " + TimeScale.CH_NAME_TS);
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

    @Test
    void testTimeScaleTable() {
        System.out.println("● 序号与时间粒度关系表\n" + TimeScale.TS_TABLE);
        assertEquals(TimeScale.TS_TABLE,
                "-------------------------------------------------------------------------\n" +
                "|        TS       |    OFTEN1_TS    |    OFTEN2_TS    |     UNIT_TS     |\n" +
                "-------------------------------------------------------------------------\n" +
                "| -1: IRREGULAR   | -1: IRREGULAR   | -1: IRREGULAR   | -1: IRREGULAR   |\n" +
                "|  0: SECOND      |  0: SECOND      |  0: SECOND      |  0: SECOND      |\n" +
                "|  1: SECOND5     |  1: MIN         |  1: SECOND5     |  1: SECOND5     |\n" +
                "|  2: SECOND10    |  2: MIN5        |  2: SECOND10    |  2: SECOND10    |\n" +
                "|  3: SECOND15    |  3: MIN10       |  3: SECOND15    |  3: SECOND15    |\n" +
                "|  4: SECOND30    |  4: MIN15       |  4: SECOND30    |  4: SECOND30    |\n" +
                "|  5: MIN         |  5: MIN30       |  5: MIN         | 10: MIN         |\n" +
                "|  6: MIN2        |  6: HOUR        |  6: MIN5        | 11: MIN2        |\n" +
                "|  7: MIN3        |  7: DAY         |  7: MIN10       | 12: MIN3        |\n" +
                "|  8: MIN4        |  8: WEEK        |  8: MIN15       | 13: MIN4        |\n" +
                "|  9: MIN5        |  9: MONTH       |  9: MIN30       | 14: MIN5        |\n" +
                "| 10: MIN10       | 10: MONTH_DAY   | 10: HOUR        | 15: MIN10       |\n" +
                "| 11: MIN15       | 11: YEAR        | 11: HOUR2       | 16: MIN15       |\n" +
                "| 12: MIN30       | 12: SECOND5     | 12: HOUR3       | 17: MIN30       |\n" +
                "| 13: HOUR        | 13: SECOND10    | 13: HOUR6       | 20: HOUR        |\n" +
                "| 14: HOUR2       | 14: SECOND15    | 14: HOUR12      | 21: HOUR2       |\n" +
                "| 15: HOUR3       | 15: SECOND30    | 15: DAY         | 22: HOUR3       |\n" +
                "| 16: HOUR4       | 16: MIN2        | 16: DAY5        | 23: HOUR4       |\n" +
                "| 17: HOUR5       | 17: MIN3        | 17: DAY10       | 24: HOUR5       |\n" +
                "| 18: HOUR6       | 18: MIN4        | 18: DAY15       | 25: HOUR6       |\n" +
                "| 19: HOUR12      | 19: HOUR2       | 19: WEEK        | 26: HOUR12      |\n" +
                "| 20: DAY         | 20: HOUR3       | 20: MONTH       | 30: DAY         |\n" +
                "| 21: DAY5        | 21: HOUR4       | 21: MONTH2      | 31: DAY5        |\n" +
                "| 22: DAY10       | 22: HOUR5       | 22: MONTH3      | 32: DAY10       |\n" +
                "| 23: DAY15       | 23: HOUR6       | 23: MONTH6      | 33: DAY15       |\n" +
                "| 24: WEEK        | 24: HOUR12      | 24: MONTH_DAY   | 34: WEEK        |\n" +
                "| 25: MONTH       | 25: DAY5        | 25: MONTH_DAY2  | 40: MONTH       |\n" +
                "| 26: MONTH2      | 26: DAY10       | 26: MONTH_DAY3  | 41: MONTH2      |\n" +
                "| 27: MONTH3      | 27: DAY15       | 27: MONTH_DAY6  | 42: MONTH3      |\n" +
                "| 28: MONTH6      | 28: MONTH2      | 28: YEAR        | 43: MONTH6      |\n" +
                "| 29: MONTH_DAY   | 29: MONTH3      | 29: MIN2        | 44: MONTH_DAY   |\n" +
                "| 30: MONTH_DAY2  | 30: MONTH6      | 30: MIN3        | 45: MONTH_DAY2  |\n" +
                "| 31: MONTH_DAY3  | 31: MONTH_DAY2  | 31: MIN4        | 46: MONTH_DAY3  |\n" +
                "| 32: MONTH_DAY6  | 32: MONTH_DAY3  | 32: HOUR4       | 47: MONTH_DAY6  |\n" +
                "| 33: YEAR        | 33: MONTH_DAY6  | 33: HOUR5       | 50: YEAR        |\n" +
                "-------------------------------------------------------------------------");
    }

}
