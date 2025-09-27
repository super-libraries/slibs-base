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

    private static List<Integer> unitIndex = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 10, 11, 12, 13, 14, 15, 16, 17,
            20, 21, 22, 23, 24, 25, 26, 30, 31, 32, 33, 34, 35, 36, 37, 40, 41, 42, 43, 44, 45, 46, 47, 50, -1);


    @Test
    void testTimeScale() {
        System.out.println("INDEX_TS       : " + TimeScale.INDEX_TS);
        System.out.println("OFTEN1_INDEX_TS: " + TimeScale.OFTEN1_INDEX_TS);
        System.out.println("OFTEN2_INDEX_TS: " + TimeScale.OFTEN2_INDEX_TS);
        System.out.println("UNIT_INDEX_TS  : " + TimeScale.UNIT_INDEX_TS);
        System.out.println("EN_NAME_TS     : " + TimeScale.EN_NAME_TS);
        System.out.println("CH_NAME_TS     : " + TimeScale.CH_NAME_TS);
        System.out.println(TimeScale.EN_NAME_TS.keySet());
        System.out.println(TimeScale.CH_NAME_TS.keySet());

        assertEquals(TimeScale.INDEX_TS.size(), 40);
        assertEquals(TimeScale.OFTEN1_INDEX_TS.size(), 40);
        assertEquals(TimeScale.OFTEN2_INDEX_TS.size(), 40);
        assertEquals(TimeScale.UNIT_INDEX_TS.size(), 40);
        assertEquals(TimeScale.EN_NAME_TS.size(), 41);
        assertEquals(TimeScale.CH_NAME_TS.size(), 102);
        assertEquals(TimeScale.EN_NAME_TS.keySet().toString(), "" +
                "[second, second2, second3, second5, second10, second15, second30, " +
                "min, min2, min3, min4, min5, min10, min15, quarter, min30, " +
                "hour, hour2, hour3, hour4, hour5, hour6, hour12, " +
                "day, day2, day3, day5, day10, day15, week, week2, " +
                "month, month2, month3, month6, monthDay, monthDay2, monthDay3, monthDay6, " +
                "year, " +
                "irregular]");
        assertEquals(TimeScale.CH_NAME_TS.keySet().toString(), "" +
                "[秒, 每秒, 1秒, 2秒, 两秒, 3秒, 5秒, 10秒, 15秒, 30秒, " +
                "分, 每分, 1分, 每分钟, 1分钟, 2分钟, 2分, 两分钟, 3分钟, 3分, 4分钟, 4分, 5分钟, 5分, 10分钟, 10分, 15分钟, 15分, 刻, 每刻, 每刻钟, 1刻钟, 一刻钟, 30分钟, 30分, " +
                "时, 每时, 1时, 每小时, 1小时, 2小时, 2时, 时辰, 每时辰, 每个时辰, 1时辰, 1个时辰, 一时辰, 一个时辰, 两小时, 3小时, 3时, 4小时, 4时, 5小时, 5时, 6小时, 6时, 12小时, 12时, " +
                "天, 日, 每天, 1天, 每日, 1日, 2天, 2日, 两天, 3天, 3日, 5天, 5日, 10天, 10日, 15天, 15日, 周, 每周, 1周, 2周, 两周, " +
                "月, 每月, 每个月, 1个月, 2个月, 两个月, 3个月, 6个月, 月01日, 每月01日, 每个月01日, 1个月01日, 2个月01日, 两个月01日, 3个月01日, 6个月01日, " +
                "年, 每年, 1年, " +
                "不定时]");
        assertEquals(TimeScale.CH_NAME_TS.toString(), "" +
                "{秒=SECOND, 每秒=SECOND, 1秒=SECOND, 2秒=SECOND2, 两秒=SECOND2, 3秒=SECOND3, 5秒=SECOND5, 10秒=SECOND10, 15秒=SECOND15, 30秒=SECOND30, " +
                "分=MIN, 每分=MIN, 1分=MIN, 每分钟=MIN, 1分钟=MIN, 2分钟=MIN2, 2分=MIN2, 两分钟=MIN2, 3分钟=MIN3, 3分=MIN3, 4分钟=MIN4, 4分=MIN4, 5分钟=MIN5, 5分=MIN5, " +
                "10分钟=MIN10, 10分=MIN10, 15分钟=MIN15, 15分=MIN15, 刻=MIN15, 每刻=MIN15, 每刻钟=MIN15, 1刻钟=MIN15, 一刻钟=MIN15, 30分钟=MIN30, 30分=MIN30, " +
                "时=HOUR, 每时=HOUR, 1时=HOUR, 每小时=HOUR, 1小时=HOUR, 2小时=HOUR2, 2时=HOUR2, 时辰=HOUR2, 每时辰=HOUR2, 每个时辰=HOUR2, 1时辰=HOUR2, 1个时辰=HOUR2, 一时辰=HOUR2, 一个时辰=HOUR2, 两小时=HOUR2, " +
                "3小时=HOUR3, 3时=HOUR3, 4小时=HOUR4, 4时=HOUR4, 5小时=HOUR5, 5时=HOUR5, 6小时=HOUR6, 6时=HOUR6, 12小时=HOUR12, 12时=HOUR12, " +
                "天=DAY, 日=DAY, 每天=DAY, 1天=DAY, 每日=DAY, 1日=DAY, 2天=DAY2, 2日=DAY2, 两天=DAY2, 3天=DAY3, 3日=DAY3, 5天=DAY5, 5日=DAY5, 10天=DAY10, 10日=DAY10, 15天=DAY15, 15日=DAY15, " +
                "周=WEEK, 每周=WEEK, 1周=WEEK, 2周=WEEK2, 两周=WEEK2, " +
                "月=MONTH, 每月=MONTH, 每个月=MONTH, 1个月=MONTH, 2个月=MONTH2, 两个月=MONTH2, 3个月=MONTH3, 6个月=MONTH6, " +
                "月01日=MONTH_DAY, 每月01日=MONTH_DAY, 每个月01日=MONTH_DAY, 1个月01日=MONTH_DAY, 2个月01日=MONTH_DAY2, 两个月01日=MONTH_DAY2, 3个月01日=MONTH_DAY3, 6个月01日=MONTH_DAY6, " +
                "年=YEAR, 每年=YEAR, 1年=YEAR, " +
                "不定时=IRREGULAR}");


        Set<Integer> indexSet = new HashSet<>(unitIndex);
        assertEquals(indexSet.size(), unitIndex.size());
        for (Integer i : indexSet) {
            assertNotNull(TimeScale.unitIndex2TS(i));
        }

        for (int i = -1; i <= 38; i++) {
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

        try {
            TimeScale.INDEX_TS.put(1, null);

            throw new RuntimeException();
        } catch (Exception e) {
            assertSame(UnsupportedOperationException.class, e.getClass());
        }

    }

    @Test
    void testTimeScaleTable() {
        System.out.println("=================================================================================\n");

        System.out.println("● 序号与时间粒度关系表\n" + TimeScale.TS_TABLE);
        assertEquals(TimeScale.TS_TABLE, "" +
                "-------------------------------------------------------------------------\n" +
                "|        TS       |    OFTEN1_TS    |    OFTEN2_TS    |     UNIT_TS     |\n" +
                "-------------------------------------------------------------------------\n" +
                "| -1: IRREGULAR   | -1: IRREGULAR   | -1: IRREGULAR   | -1: IRREGULAR   |\n" +
                "|  0: SECOND      |  0: SECOND      |  0: SECOND      |  0: SECOND      |\n" +
                "|  1: SECOND2     |  1: MIN         |  1: SECOND2     |  1: SECOND2     |\n" +
                "|  2: SECOND3     |  2: MIN5        |  2: SECOND3     |  2: SECOND3     |\n" +
                "|  3: SECOND5     |  3: MIN10       |  3: SECOND5     |  3: SECOND5     |\n" +
                "|  4: SECOND10    |  4: MIN15       |  4: SECOND10    |  4: SECOND10    |\n" +
                "|  5: SECOND15    |  5: MIN30       |  5: SECOND15    |  5: SECOND15    |\n" +
                "|  6: SECOND30    |  6: HOUR        |  6: SECOND30    |  6: SECOND30    |\n" +
                "|  7: MIN         |  7: DAY         |  7: MIN         | 10: MIN         |\n" +
                "|  8: MIN2        |  8: WEEK        |  8: MIN2        | 11: MIN2        |\n" +
                "|  9: MIN3        |  9: MONTH       |  9: MIN3        | 12: MIN3        |\n" +
                "| 10: MIN4        | 10: MONTH_DAY   | 10: MIN5        | 13: MIN4        |\n" +
                "| 11: MIN5        | 11: YEAR        | 11: MIN10       | 14: MIN5        |\n" +
                "| 12: MIN10       | 12: SECOND2     | 12: MIN15       | 15: MIN10       |\n" +
                "| 13: MIN15       | 13: SECOND3     | 13: MIN30       | 16: MIN15       |\n" +
                "| 14: MIN30       | 14: SECOND5     | 14: HOUR        | 17: MIN30       |\n" +
                "| 15: HOUR        | 15: SECOND10    | 15: HOUR2       | 20: HOUR        |\n" +
                "| 16: HOUR2       | 16: SECOND15    | 16: HOUR3       | 21: HOUR2       |\n" +
                "| 17: HOUR3       | 17: SECOND30    | 17: HOUR6       | 22: HOUR3       |\n" +
                "| 18: HOUR4       | 18: MIN2        | 18: HOUR12      | 23: HOUR4       |\n" +
                "| 19: HOUR5       | 19: MIN3        | 19: DAY         | 24: HOUR5       |\n" +
                "| 20: HOUR6       | 20: MIN4        | 20: DAY2        | 25: HOUR6       |\n" +
                "| 21: HOUR12      | 21: HOUR2       | 21: DAY3        | 26: HOUR12      |\n" +
                "| 22: DAY         | 22: HOUR3       | 22: DAY5        | 30: DAY         |\n" +
                "| 23: DAY2        | 23: HOUR4       | 23: WEEK        | 31: DAY2        |\n" +
                "| 24: DAY3        | 24: HOUR5       | 24: MONTH       | 32: DAY3        |\n" +
                "| 25: DAY5        | 25: HOUR6       | 25: MONTH2      | 33: DAY5        |\n" +
                "| 26: DAY10       | 26: HOUR12      | 26: MONTH3      | 34: DAY10       |\n" +
                "| 27: DAY15       | 27: DAY2        | 27: MONTH6      | 35: DAY15       |\n" +
                "| 28: WEEK        | 28: DAY3        | 28: MONTH_DAY   | 36: WEEK        |\n" +
                "| 29: WEEK2       | 29: DAY5        | 29: MONTH_DAY2  | 37: WEEK2       |\n" +
                "| 30: MONTH       | 30: DAY10       | 30: MONTH_DAY3  | 40: MONTH       |\n" +
                "| 31: MONTH2      | 31: DAY15       | 31: MONTH_DAY6  | 41: MONTH2      |\n" +
                "| 32: MONTH3      | 32: WEEK2       | 32: YEAR        | 42: MONTH3      |\n" +
                "| 33: MONTH6      | 33: MONTH2      | 33: MIN4        | 43: MONTH6      |\n" +
                "| 34: MONTH_DAY   | 34: MONTH3      | 34: HOUR4       | 44: MONTH_DAY   |\n" +
                "| 35: MONTH_DAY2  | 35: MONTH6      | 35: HOUR5       | 45: MONTH_DAY2  |\n" +
                "| 36: MONTH_DAY3  | 36: MONTH_DAY2  | 36: DAY10       | 46: MONTH_DAY3  |\n" +
                "| 37: MONTH_DAY6  | 37: MONTH_DAY3  | 37: DAY15       | 47: MONTH_DAY6  |\n" +
                "| 38: YEAR        | 38: MONTH_DAY6  | 38: WEEK2       | 50: YEAR        |\n" +
                "-------------------------------------------------------------------------");

        System.out.println("\n=================================================================================");
    }

}
