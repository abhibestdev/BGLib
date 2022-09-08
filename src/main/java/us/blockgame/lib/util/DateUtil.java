package us.blockgame.lib.util;

import java.text.DateFormat;
import java.util.Date;

public class DateUtil {

    public static String millisToDate(long time) {
        Date resultdate = new Date(time);
        return DateFormat.getDateTimeInstance().format(resultdate);
    }
}
