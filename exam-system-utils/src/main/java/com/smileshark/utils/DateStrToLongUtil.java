package com.smileshark.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateStrToLongUtil {
    public static long dateStrToLong(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        OffsetDateTime odt = OffsetDateTime.parse(dateString, formatter);
        return odt.toInstant().toEpochMilli();
    }
}
