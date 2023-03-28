package com.PCThanhCong.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    public final static String DEFAULT_FORMAT = "yyyy-MM-dd";
    public final static String HHmmss_ddMMyyyy = "HH:mm:ss dd/MM/yyyy";
    public final static String ddMMyyyy_HHmmss = "dd/MM/yyyy HH:mm:ss";
    public final static String ddMMyyyy = "dd/MM/yyyy";
    public final static String HHmmss = "HH:mm:ss";
    public final static String timeZone = "+07:00";
    public static Date parse(String strDate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
        return formatter.parse(strDate);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date tryParse(String strDate, Date defaultVal) {

        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            return defaultVal;
        }
    }

    public static String serialize(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
        return formatter.format(date);
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getCurrentTimeStampWithFormat(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static Instant convertToInstant(String date)  {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
        Date dates = null;
        try {
            dates = formatter.parse(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates.toInstant();
    }


    public static LocalDateTime convertToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
            .atZone(ZoneId.systemDefault() )
            .toLocalDateTime();
    }
}
