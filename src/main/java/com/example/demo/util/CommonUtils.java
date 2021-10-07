package com.example.demo.util;

import com.example.demo.exception.CustomException;
import com.example.demo.model.BaseModel;
import com.example.demo.model.enu.Code;
import com.example.demo.model.search.BaseSearch;
import com.example.demo.repository.BaseRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class CommonUtils {

    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String timeZone = "GMT+7";
    public static final int PAGE_DEFAULT = 1;
    public static final int PAGE_SIZE_DEFAULT = 5;

    public static Timestamp getCurrentTime() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return currentTime;
    }

    public static void setDefaultPageIfNull(BaseSearch search) {
        if (search.getPage() == null)
            search.setPage(CommonUtils.PAGE_DEFAULT);
        if (search.getPageSize() == null)
            search.setPageSize(CommonUtils.PAGE_SIZE_DEFAULT);
    }

    public static Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat;
    }

    public static List<Locale> getSupportedLocale() {
        return Arrays.asList(new Locale("vi"), new Locale("en"), new Locale("jp"));
    }

    public static CustomException createException(Code errorCode){
        return new CustomException(errorCode);
    }

}
