package com.yangfang.aries.common.util;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类，使用Java8推荐类，LocalDateTime和Instance
 *
 * @author 幽明
 * @serial 2019/6/10
 */
public class DateUtils {

    /**
     * 默认的时间格式
     */
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 日期格式 YYYYMMDD
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 格式化日期为"yyyy-MM-dd hh:mm:ss"字符串
     *
     * @param date 日期
     * @return "yyyy-MM-dd hh:mm:ss"
     */
    public static String format(Date date) {
        return formatToString(date, DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * 格式化日期为pattern样式的字符串
     *
     * @param date 日期
     * @param pattern 输出格式
     * @return pattern格式的日期
     */
    public static String format(Date date, String pattern) {
        return formatToString(date, pattern);
    }

    /**
     * 将日期字符串转换为Date
     *
     * @param dateStr 日期字符串
     * @return 日期Date
     */
    public static Date valueOf(String dateStr) {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.from(dft.parse(dateStr));
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

    }

    private static String formatToString(Date date, String pattern) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dtf);
    }

}
