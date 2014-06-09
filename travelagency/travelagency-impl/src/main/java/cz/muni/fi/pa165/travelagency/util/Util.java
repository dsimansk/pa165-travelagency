package cz.muni.fi.pa165.travelagency.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Michal Jurc
 */
public class Util {
    
    public static Date newDate(int year, int month, int day) {
        Date result;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        result = cal.getTime();
        return result;
    }
    
}
