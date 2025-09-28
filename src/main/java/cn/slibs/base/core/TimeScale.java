package cn.slibs.base.core;

import cn.slibs.base.map.EasyLinkedHashMap;
import com.iofairy.annos.Beta;
import com.iofairy.falcon.map.MapKit;
import com.iofairy.top.G;
import com.iofairy.top.S;

import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 时间粒度
 * <h3 id="predefined">序号与时间粒度对应关系表</h3>
 * <table summary="序号与时间粒度对应关系表" border="1" cellpadding="5" cellspacing="2" border="1">
 *   <tr>
 *     <th>TS</th><th>OFTEN1_TS</th><th>OFTEN2_TS</th><th>UNIT_TS</th>
 *   </tr>
 *   <tr><td>-1: IRREGULAR</td><td>-1: IRREGULAR</td><td>-1: IRREGULAR</td><td>-1: IRREGULAR</td></tr>
 *   <tr><td>&nbsp;0: SECOND</td><td>&nbsp;0: SECOND</td><td>&nbsp;0: SECOND</td><td>&nbsp;0: SECOND</td></tr>
 *   <tr><td>&nbsp;1: SECOND2</td><td>&nbsp;1: MIN</td><td>&nbsp;1: SECOND2</td><td>&nbsp;1: SECOND2</td></tr>
 *   <tr><td>&nbsp;2: SECOND3</td><td>&nbsp;2: MIN5</td><td>&nbsp;2: SECOND3</td><td>&nbsp;2: SECOND3</td></tr>
 *   <tr><td>&nbsp;3: SECOND5</td><td>&nbsp;3: MIN10</td><td>&nbsp;3: SECOND5</td><td>&nbsp;3: SECOND5</td></tr>
 *   <tr><td>&nbsp;4: SECOND10</td><td>&nbsp;4: MIN15</td><td>&nbsp;4: SECOND10</td><td>&nbsp;4: SECOND10</td></tr>
 *   <tr><td>&nbsp;5: SECOND15</td><td>&nbsp;5: MIN30</td><td>&nbsp;5: SECOND15</td><td>&nbsp;5: SECOND15</td></tr>
 *   <tr><td>&nbsp;6: SECOND30</td><td>&nbsp;6: HOUR</td><td>&nbsp;6: SECOND30</td><td>&nbsp;6: SECOND30</td></tr>
 *   <tr><td>&nbsp;7: MIN</td><td>&nbsp;7: DAY</td><td>&nbsp;7: MIN</td><td>10: MIN</td></tr>
 *   <tr><td>&nbsp;8: MIN2</td><td>&nbsp;8: WEEK</td><td>&nbsp;8: MIN2</td><td>11: MIN2</td></tr>
 *   <tr><td>&nbsp;9: MIN3</td><td>&nbsp;9: MONTH</td><td>&nbsp;9: MIN3</td><td>12: MIN3</td></tr>
 *   <tr><td>10: MIN4</td><td>10: MONTH_DAY</td><td>10: MIN5</td><td>13: MIN4</td></tr>
 *   <tr><td>11: MIN5</td><td>11: YEAR</td><td>11: MIN10</td><td>14: MIN5</td></tr>
 *   <tr><td>12: MIN10</td><td>12: SECOND2</td><td>12: MIN15</td><td>15: MIN10</td></tr>
 *   <tr><td>13: MIN15</td><td>13: SECOND3</td><td>13: MIN30</td><td>16: MIN15</td></tr>
 *   <tr><td>14: MIN30</td><td>14: SECOND5</td><td>14: HOUR</td><td>17: MIN30</td></tr>
 *   <tr><td>15: HOUR</td><td>15: SECOND10</td><td>15: HOUR2</td><td>20: HOUR</td></tr>
 *   <tr><td>16: HOUR2</td><td>16: SECOND15</td><td>16: HOUR3</td><td>21: HOUR2</td></tr>
 *   <tr><td>17: HOUR3</td><td>17: SECOND30</td><td>17: HOUR6</td><td>22: HOUR3</td></tr>
 *   <tr><td>18: HOUR4</td><td>18: MIN2</td><td>18: HOUR12</td><td>23: HOUR4</td></tr>
 *   <tr><td>19: HOUR5</td><td>19: MIN3</td><td>19: DAY</td><td>24: HOUR5</td></tr>
 *   <tr><td>20: HOUR6</td><td>20: MIN4</td><td>20: DAY2</td><td>25: HOUR6</td></tr>
 *   <tr><td>21: HOUR12</td><td>21: HOUR2</td><td>21: DAY3</td><td>26: HOUR12</td></tr>
 *   <tr><td>22: DAY</td><td>22: HOUR3</td><td>22: DAY5</td><td>30: DAY</td></tr>
 *   <tr><td>23: DAY2</td><td>23: HOUR4</td><td>23: WEEK</td><td>31: DAY2</td></tr>
 *   <tr><td>24: DAY3</td><td>24: HOUR5</td><td>24: MONTH</td><td>32: DAY3</td></tr>
 *   <tr><td>25: DAY5</td><td>25: HOUR6</td><td>25: MONTH2</td><td>33: DAY5</td></tr>
 *   <tr><td>26: DAY10</td><td>26: HOUR12</td><td>26: MONTH3</td><td>34: DAY10</td></tr>
 *   <tr><td>27: DAY15</td><td>27: DAY2</td><td>27: MONTH6</td><td>35: DAY15</td></tr>
 *   <tr><td>28: WEEK</td><td>28: DAY3</td><td>28: MONTH_DAY</td><td>36: WEEK</td></tr>
 *   <tr><td>29: WEEK2</td><td>29: DAY5</td><td>29: MONTH_DAY2</td><td>37: WEEK2</td></tr>
 *   <tr><td>30: MONTH</td><td>30: DAY10</td><td>30: MONTH_DAY3</td><td>40: MONTH</td></tr>
 *   <tr><td>31: MONTH2</td><td>31: DAY15</td><td>31: MONTH_DAY6</td><td>41: MONTH2</td></tr>
 *   <tr><td>32: MONTH3</td><td>32: WEEK2</td><td>32: YEAR</td><td>42: MONTH3</td></tr>
 *   <tr><td>33: MONTH6</td><td>33: MONTH2</td><td>33: MIN4</td><td>43: MONTH6</td></tr>
 *   <tr><td>34: MONTH_DAY</td><td>34: MONTH3</td><td>34: HOUR4</td><td>44: MONTH_DAY</td></tr>
 *   <tr><td>35: MONTH_DAY2</td><td>35: MONTH6</td><td>35: HOUR5</td><td>45: MONTH_DAY2</td></tr>
 *   <tr><td>36: MONTH_DAY3</td><td>36: MONTH_DAY2</td><td>36: DAY10</td><td>46: MONTH_DAY3</td></tr>
 *   <tr><td>37: MONTH_DAY6</td><td>37: MONTH_DAY3</td><td>37: DAY15</td><td>47: MONTH_DAY6</td></tr>
 *   <tr><td>38: YEAR</td><td>38: MONTH_DAY6</td><td>38: WEEK2</td><td>50: YEAR</td></tr>
 * </table>
 *
 * @since 0.1.2
 */
@Beta
public enum TimeScale {
    SECOND     ( 0,   0,   0,   0,    ChronoUnit.SECONDS,   1,   "yyyyMMddHHmmss",      "second",    "秒"),
    SECOND2    ( 1,  12,   1,   1,    ChronoUnit.SECONDS,   2,   "yyyyMMddHHmmss",     "second2",    "2秒"),
    SECOND3    ( 2,  13,   2,   2,    ChronoUnit.SECONDS,   3,   "yyyyMMddHHmmss",     "second3",    "3秒"),
    SECOND5    ( 3,  14,   3,   3,    ChronoUnit.SECONDS,   5,   "yyyyMMddHHmmss",     "second5",    "5秒"),
    SECOND10   ( 4,  15,   4,   4,    ChronoUnit.SECONDS,  10,   "yyyyMMddHHmmss",    "second10",    "10秒"),
    SECOND15   ( 5,  16,   5,   5,    ChronoUnit.SECONDS,  15,   "yyyyMMddHHmmss",    "second15",    "15秒"),
    SECOND30   ( 6,  17,   6,   6,    ChronoUnit.SECONDS,  30,   "yyyyMMddHHmmss",    "second30",    "30秒"),
    MIN        ( 7,   1,   7,  10,    ChronoUnit.MINUTES,   1,     "yyyyMMddHHmm",         "min",    "分"),
    MIN2       ( 8,  18,   8,  11,    ChronoUnit.MINUTES,   2,     "yyyyMMddHHmm",        "min2",    "2分钟"),
    MIN3       ( 9,  19,   9,  12,    ChronoUnit.MINUTES,   3,     "yyyyMMddHHmm",        "min3",    "3分钟"),
    MIN4       (10,  20,  33,  13,    ChronoUnit.MINUTES,   4,     "yyyyMMddHHmm",        "min4",    "4分钟"),
    MIN5       (11,   2,  10,  14,    ChronoUnit.MINUTES,   5,     "yyyyMMddHHmm",        "min5",    "5分钟"),
    MIN10      (12,   3,  11,  15,    ChronoUnit.MINUTES,  10,     "yyyyMMddHHmm",       "min10",    "10分钟"),
    MIN15      (13,   4,  12,  16,    ChronoUnit.MINUTES,  15,     "yyyyMMddHHmm",       "min15",    "15分钟"),
    MIN30      (14,   5,  13,  17,    ChronoUnit.MINUTES,  30,     "yyyyMMddHHmm",       "min30",    "30分钟"),
    HOUR       (15,   6,  14,  20,      ChronoUnit.HOURS,   1,       "yyyyMMddHH",        "hour",    "时"),
    HOUR2      (16,  21,  15,  21,      ChronoUnit.HOURS,   2,       "yyyyMMddHH",       "hour2",    "2小时"),
    HOUR3      (17,  22,  16,  22,      ChronoUnit.HOURS,   3,       "yyyyMMddHH",       "hour3",    "3小时"),
    HOUR4      (18,  23,  34,  23,      ChronoUnit.HOURS,   4,       "yyyyMMddHH",       "hour4",    "4小时"),
    HOUR5      (19,  24,  35,  24,      ChronoUnit.HOURS,   5,       "yyyyMMddHH",       "hour5",    "5小时"),
    HOUR6      (20,  25,  17,  25,      ChronoUnit.HOURS,   6,       "yyyyMMddHH",       "hour6",    "6小时"),
    HOUR12     (21,  26,  18,  26,      ChronoUnit.HOURS,  12,       "yyyyMMddHH",      "hour12",    "12小时"),
    DAY        (22,   7,  19,  30,       ChronoUnit.DAYS,   1,         "yyyyMMdd",         "day",    "天"),
    DAY2       (23,  27,  20,  31,       ChronoUnit.DAYS,   2,         "yyyyMMdd",        "day2",    "2天"),
    DAY3       (24,  28,  21,  32,       ChronoUnit.DAYS,   3,         "yyyyMMdd",        "day3",    "3天"),
    DAY5       (25,  29,  22,  33,       ChronoUnit.DAYS,   5,         "yyyyMMdd",        "day5",    "5天"),
    DAY10      (26,  30,  36,  34,       ChronoUnit.DAYS,  10,         "yyyyMMdd",       "day10",    "10天"),
    DAY15      (27,  31,  37,  35,       ChronoUnit.DAYS,  15,         "yyyyMMdd",       "day15",    "15天"),
    WEEK       (28,   8,  23,  36,      ChronoUnit.WEEKS,   1,         "yyyyMMdd",        "week",    "周"),
    WEEK2      (29,  32,  38,  37,      ChronoUnit.WEEKS,   2,         "yyyyMMdd",       "week2",    "2周"),
    MONTH      (30,   9,  24,  40,     ChronoUnit.MONTHS,   1,           "yyyyMM",       "month",    "月"),
    MONTH2     (31,  33,  25,  41,     ChronoUnit.MONTHS,   2,           "yyyyMM",      "month2",    "2个月"),
    MONTH3     (32,  34,  26,  42,     ChronoUnit.MONTHS,   3,           "yyyyMM",      "month3",    "3个月"),
    MONTH6     (33,  35,  27,  43,     ChronoUnit.MONTHS,   6,           "yyyyMM",      "month6",    "6个月"),
    MONTH_DAY  (34,  10,  28,  44,     ChronoUnit.MONTHS,   1,         "yyyyMM01",    "monthDay",    "月01日"),
    MONTH_DAY2 (35,  36,  29,  45,     ChronoUnit.MONTHS,   2,         "yyyyMM01",   "monthDay2",    "2个月01日"),
    MONTH_DAY3 (36,  37,  30,  46,     ChronoUnit.MONTHS,   3,         "yyyyMM01",   "monthDay3",    "3个月01日"),
    MONTH_DAY6 (37,  38,  31,  47,     ChronoUnit.MONTHS,   6,         "yyyyMM01",   "monthDay6",    "6个月01日"),
    YEAR       (38,  11,  32,  50,      ChronoUnit.YEARS,   1,             "yyyy",        "year",    "年"),
    IRREGULAR  (-1,  -1,  -1,  -1,                  null,  -1,               null,   "irregular",    "不定时"),
    ;


    public final int value;                 // 枚举值
    public final int often1Index;           // 按使用频率排序的序号1
    public final int often2Index;           // 按使用频率排序的序号2
    public final int unitIndex;             // 按单位排序的序号
    public final ChronoUnit chronoUnit;     // 时间单位
    public final int timeAmountUnit;        // 时间步长
    public final String dateFormat;         // 时间格式化
    public final String enName;             // 英文名
    public final String chName;             // 中文名

    public static final Map<Integer, TimeScale> INDEX_TS;
    public static final Map<Integer, TimeScale> OFTEN1_INDEX_TS;
    public static final Map<Integer, TimeScale> OFTEN2_INDEX_TS;
    public static final Map<Integer, TimeScale> UNIT_INDEX_TS;
    public static final Map<String, TimeScale>  EN_NAME_TS;
    public static final Map<String, TimeScale>  CH_NAME_TS;
    /**
     * 序号与时间粒度关系表
     */
    public static final String TS_TABLE;

    static {
        EasyLinkedHashMap<Integer, TimeScale> valueMap       = new EasyLinkedHashMap<>();
        EasyLinkedHashMap<Integer, TimeScale> often1IndexMap = new EasyLinkedHashMap<>();
        EasyLinkedHashMap<Integer, TimeScale> often2IndexMap = new EasyLinkedHashMap<>();
        EasyLinkedHashMap<Integer, TimeScale> unitIndexMap   = new EasyLinkedHashMap<>();
        EasyLinkedHashMap<String, TimeScale> enNameMap       = new EasyLinkedHashMap<>();
        EasyLinkedHashMap<String, TimeScale> chNameMap       = new EasyLinkedHashMap<>();

        for (TimeScale ts : values()) {
            valueMap.put(ts.value, ts);
            often1IndexMap.put(ts.often1Index, ts);
            often2IndexMap.put(ts.often2Index, ts);
            unitIndexMap.put(ts.unitIndex, ts);
            enNameMap.put(ts.enName, ts);
            chNameMap.put(ts.chName, ts);

            // “月”中的【个】不要去除，去除反而容易有歧义
            chNameMap.put(ts.chName.replaceAll("[钟小]", "").replace("天", "日"), ts);

            if (ts == MIN15) {
                enNameMap.put("quarter", ts);
                chNameMap.putData("刻", ts, "每刻", ts, "每刻钟", ts, "1刻钟", ts, "一刻钟", ts);
            }
            if (ts == HOUR2) {
                chNameMap.putData("时辰", ts, "每时辰", ts, "每个时辰", ts, "1时辰", ts, "1个时辰", ts, "一时辰", ts, "一个时辰", ts);
            }

            if (ts.timeAmountUnit == 1) {
                chNameMap.put("每" + ts.chName, ts);
                if (ts.chronoUnit != ChronoUnit.MONTHS) {
                    chNameMap.put("1" + ts.chName, ts);
                }

                switch (ts.chronoUnit) {
                    case MINUTES:
                        chNameMap.putData("每分钟", ts, "1分钟", ts);
                        break;
                    case HOURS:
                        chNameMap.putData("每小时", ts, "1小时", ts);
                        break;
                    case DAYS:
                        chNameMap.putData("每日", ts, "1日", ts);
                        break;
                    case MONTHS:
                        if (ts == MONTH) {
                            chNameMap.putData("每个月", ts, "1个月", ts);
                        } else {    // MONTH_DAY
                            chNameMap.putData("每个月01日", ts, "1个月01日", ts);
                        }
                        break;
                }

            }

            if (ts.timeAmountUnit == 2) {
                chNameMap.put(ts.chName.replace('2', '两'), ts);
            }
        }


        INDEX_TS        = Collections.unmodifiableMap(valueMap);
        OFTEN1_INDEX_TS = Collections.unmodifiableMap(often1IndexMap);
        OFTEN2_INDEX_TS = Collections.unmodifiableMap(often2IndexMap);
        UNIT_INDEX_TS   = Collections.unmodifiableMap(unitIndexMap);
        EN_NAME_TS      = Collections.unmodifiableMap(enNameMap);
        CH_NAME_TS      = Collections.unmodifiableMap(chNameMap);

        TS_TABLE = generateTsTable();
    }

    TimeScale(int value, int often1Index, int often2Index, int unitIndex, ChronoUnit chronoUnit, int timeAmount, String dateFormat, String enName, String chName) {
        this.value = value;
        this.often1Index = often1Index;
        this.often2Index = often2Index;
        this.unitIndex = unitIndex;
        this.chronoUnit = chronoUnit;
        this.timeAmountUnit = timeAmount;
        this.dateFormat = dateFormat;
        this.enName = enName;
        this.chName = chName;
    }


    /**
     * 时间粒度序号 转 TimeScale
     *
     * @param index index
     * @return TimeScale
     */
    public static TimeScale index2TS(int index) {
        return INDEX_TS.get(index);
    }

    /**
     * 使用频率排序的序号1 转 TimeScale
     *
     * @param index index
     * @return TimeScale
     */
    public static TimeScale often1Index2TS(int index) {
        return OFTEN1_INDEX_TS.get(index);
    }

    /**
     * 使用频率排序的序号2 转 TimeScale
     *
     * @param index index
     * @return TimeScale
     */
    public static TimeScale often2Index2TS(int index) {
        return OFTEN2_INDEX_TS.get(index);
    }

    /**
     * 单位排序的序号 转 TimeScale
     *
     * @param index index
     * @return TimeScale
     */
    public static TimeScale unitIndex2TS(int index) {
        return UNIT_INDEX_TS.get(index);
    }

    /**
     * 英文名称转 TimeScale
     *
     * @param enName enName
     * @return TimeScale
     */
    public static TimeScale enName2TS(String enName) {
        return G.isEmpty(enName) ? null : EN_NAME_TS.get(enName.toLowerCase());
    }

    /**
     * 中文名称转 TimeScale
     *
     * @param chName chName
     * @return TimeScale
     */
    public static TimeScale chName2TS(String chName) {
        return G.isEmpty(chName) ? null : CH_NAME_TS.get(chName);
    }

    /**
     * 获取序号与时间粒度关系表
     *
     * @return 序号与时间粒度关系表
     */
    private static String generateTsTable() {
        try {
            List<Map.Entry<Integer, TimeScale>> indexTsEntries = MapKit.sortBy(TimeScale.INDEX_TS, Comparator.comparingInt(Map.Entry::getKey));
            List<Map.Entry<Integer, TimeScale>> often1IndexTsEntries = MapKit.sortBy(TimeScale.OFTEN1_INDEX_TS, Comparator.comparingInt(Map.Entry::getKey));
            List<Map.Entry<Integer, TimeScale>> often2IndexTsEntries = MapKit.sortBy(TimeScale.OFTEN2_INDEX_TS, Comparator.comparingInt(Map.Entry::getKey));
            List<Map.Entry<Integer, TimeScale>> unitIndexTsEntries = MapKit.sortBy(TimeScale.UNIT_INDEX_TS, Comparator.comparingInt(Map.Entry::getKey));

            final String tableLine = "-------------------------------------------------------------------------";
            final String tableTitle = "|        TS       |    OFTEN1_TS    |    OFTEN2_TS    |     UNIT_TS     |";

            final StringBuilder tsTable = new StringBuilder();
            tsTable.append(tableLine).append("\n");
            tsTable.append(tableTitle).append("\n");
            tsTable.append(tableLine).append("\n");

            for (int i = 0; i < TimeScale.values().length; i++) {
                indexTsToColumn(tsTable, indexTsEntries.get(i));
                indexTsToColumn(tsTable, often1IndexTsEntries.get(i));
                indexTsToColumn(tsTable, often2IndexTsEntries.get(i));
                indexTsToColumn(tsTable, unitIndexTsEntries.get(i));
                tsTable.append("|").append("\n");
            }

            tsTable.append(tableLine);

            return tsTable.toString();
        } catch (Throwable e) {
            return "";
        }
    }

    private static void indexTsToColumn(final StringBuilder tsTable, final Map.Entry<Integer, TimeScale> indexTsEntry) {
        final int indexLength = 2;
        final int tsNameLength = 12;
        final char padChar = ' ';
        final String separatorForIndexAndTs = ": ";

        tsTable.append("| ")
                .append(S.padLeftChars(indexTsEntry.getKey(), padChar, indexLength))
                .append(separatorForIndexAndTs)
                .append(S.padRightChars(indexTsEntry.getValue().toString(), padChar, tsNameLength));
    }


}
