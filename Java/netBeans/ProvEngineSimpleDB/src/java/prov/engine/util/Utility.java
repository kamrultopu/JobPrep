/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Shams
 */
public class Utility {

    public static SimpleDateFormat DBDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    public static Date addTime(Date date, int duration, Config.EnumEpochUnit unit)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch(unit)
        {
            case Second:
                cal.add(Calendar.SECOND, duration); 
                break; 
            case Minute:
                cal.add(Calendar.MINUTE, duration); 
                break;
            case Hour:
                cal.add(Calendar.HOUR, duration); 
                break; 
            case Day:
                cal.add(Calendar.DATE, duration); 
                break;
            default: 
                cal.add(Calendar.MINUTE, duration); 
                break;
        }
        return cal.getTime();
    }
}
