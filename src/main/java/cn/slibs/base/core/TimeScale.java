package cn.slibs.base.core;

import com.iofairy.top.G;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间粒度
 *
 * @since 0.1.2
 */
public enum TimeScale {
    SECOND     ( 0,   0,   0,   0,    ChronoUnit.SECONDS,   1,   "yyyyMMddHHmmss",      "second",    "秒"),
    SECOND5    ( 1,  12,   1,   1,    ChronoUnit.SECONDS,   5,   "yyyyMMddHHmmss",     "second5",    "5秒"),
    SECOND10   ( 2,  13,   2,   2,    ChronoUnit.SECONDS,  10,   "yyyyMMddHHmmss",    "second10",    "10秒"),
    SECOND15   ( 3,  14,   3,   3,    ChronoUnit.SECONDS,  15,   "yyyyMMddHHmmss",    "second15",    "15秒"),
    SECOND30   ( 4,  15,   4,   4,    ChronoUnit.SECONDS,  30,   "yyyyMMddHHmmss",    "second30",    "30秒"),
    MIN        ( 5,   1,   5,  10,    ChronoUnit.MINUTES,   1,     "yyyyMMddHHmm",         "min",    "分"),
    MIN2       ( 6,  16,  23,  11,    ChronoUnit.MINUTES,   2,     "yyyyMMddHHmm",        "min2",    "2分钟"),
    MIN3       ( 7,  17,  24,  12,    ChronoUnit.MINUTES,   3,     "yyyyMMddHHmm",        "min3",    "3分钟"),
    MIN4       ( 8,  18,  25,  13,    ChronoUnit.MINUTES,   4,     "yyyyMMddHHmm",        "min4",    "4分钟"),
    MIN5       ( 9,   2,   6,  14,    ChronoUnit.MINUTES,   5,     "yyyyMMddHHmm",        "min5",    "5分钟"),
    MIN10      (10,   3,   7,  15,    ChronoUnit.MINUTES,  10,     "yyyyMMddHHmm",       "min10",    "10分钟"),
    MIN15      (11,   4,   8,  16,    ChronoUnit.MINUTES,  15,     "yyyyMMddHHmm",       "min15",    "15分钟"),
    MIN30      (12,   5,   9,  17,    ChronoUnit.MINUTES,  30,     "yyyyMMddHHmm",       "min30",    "30分钟"),
    HOUR       (13,   6,  10,  20,      ChronoUnit.HOURS,   1,       "yyyyMMddHH",        "hour",    "时"),
    HOUR2      (14,  19,  26,  21,      ChronoUnit.HOURS,   2,       "yyyyMMddHH",       "hour2",    "2小时"),
    HOUR3      (15,  20,  27,  22,      ChronoUnit.HOURS,   3,       "yyyyMMddHH",       "hour3",    "3小时"),
    HOUR4      (16,  21,  28,  23,      ChronoUnit.HOURS,   4,       "yyyyMMddHH",       "hour4",    "4小时"),
    HOUR5      (17,  22,  29,  24,      ChronoUnit.HOURS,   5,       "yyyyMMddHH",       "hour5",    "5小时"),
    HOUR6      (18,  23,  11,  25,      ChronoUnit.HOURS,   6,       "yyyyMMddHH",       "hour6",    "6小时"),
    HOUR12     (19,  24,  12,  26,      ChronoUnit.HOURS,  12,       "yyyyMMddHH",      "hour12",    "12小时"),
    DAY        (20,   7,  13,  30,       ChronoUnit.DAYS,   1,         "yyyyMMdd",         "day",    "天"),
    DAY5       (21,  25,  14,  31,       ChronoUnit.DAYS,   5,         "yyyyMMdd",        "day5",    "5天"),
    DAY10      (22,  26,  30,  32,       ChronoUnit.DAYS,  10,         "yyyyMMdd",       "day10",    "10天"),
    DAY15      (23,  27,  31,  33,       ChronoUnit.DAYS,  15,         "yyyyMMdd",       "day15",    "15天"),
    WEEK       (24,   8,  15,  34,      ChronoUnit.WEEKS,   1,         "yyyyMMdd",        "week",    "周"),
    MONTH      (25,   9,  16,  40,     ChronoUnit.MONTHS,   1,           "yyyyMM",       "month",    "月"),
    MONTH2     (26,  28,  32,  41,     ChronoUnit.MONTHS,   2,           "yyyyMM",      "month2",    "2个月"),
    MONTH3     (27,  29,  17,  42,     ChronoUnit.MONTHS,   3,           "yyyyMM",      "month3",    "3个月"),
    MONTH6     (28,  30,  18,  43,     ChronoUnit.MONTHS,   6,           "yyyyMM",      "month6",    "6个月"),
    MONTH_DAY  (29,  10,  19,  44,     ChronoUnit.MONTHS,   1,         "yyyyMM01",    "monthDay",    "月01日"),
    MONTH_DAY2 (30,  31,  33,  45,     ChronoUnit.MONTHS,   2,         "yyyyMM01",   "monthDay2",    "2个月01日"),
    MONTH_DAY3 (31,  32,  20,  46,     ChronoUnit.MONTHS,   3,         "yyyyMM01",   "monthDay3",    "3个月01日"),
    MONTH_DAY6 (32,  33,  21,  47,     ChronoUnit.MONTHS,   6,         "yyyyMM01",   "monthDay6",    "6个月01日"),
    YEAR       (33,  11,  22,  50,      ChronoUnit.YEARS,   1,             "yyyy",        "year",    "年"),
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
    public static final Map<String, TimeScale> EN_NAME_TS;
    public static final Map<String, TimeScale> CH_NAME_TS;

    static {
        Map<Integer, TimeScale> valueMap = new HashMap<>();
        Map<Integer, TimeScale> often1IndexMap = new HashMap<>();
        Map<Integer, TimeScale> often2IndexMap = new HashMap<>();
        Map<Integer, TimeScale> unitIndexMap = new HashMap<>();
        Map<String, TimeScale>  enNameMap = new HashMap<>();
        Map<String, TimeScale>  chNameMap = new HashMap<>();
        for (TimeScale ts : values()) {
            valueMap.put(ts.value, ts);
            often1IndexMap.put(ts.often1Index, ts);
            often2IndexMap.put(ts.often2Index, ts);
            unitIndexMap.put(ts.unitIndex, ts);
            enNameMap.put(ts.enName, ts);
            chNameMap.put(ts.chName, ts);
        }
        chNameMap.put("日", DAY);
        chNameMap.put("刻", MIN15);

        INDEX_TS        = Collections.unmodifiableMap(valueMap);
        OFTEN1_INDEX_TS = Collections.unmodifiableMap(often1IndexMap);
        OFTEN2_INDEX_TS = Collections.unmodifiableMap(often2IndexMap);
        UNIT_INDEX_TS   = Collections.unmodifiableMap(unitIndexMap);
        EN_NAME_TS      = Collections.unmodifiableMap(enNameMap);
        CH_NAME_TS      = Collections.unmodifiableMap(chNameMap);
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


}
