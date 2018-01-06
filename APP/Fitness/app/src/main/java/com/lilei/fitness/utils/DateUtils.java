package com.lilei.fitness.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by djzhao on 17/05/06.
 */

public class DateUtils {

    public static String getCurrentDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
