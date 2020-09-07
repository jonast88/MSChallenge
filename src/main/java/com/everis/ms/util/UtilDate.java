package com.everis.ms.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class UtilDate {

    private static String dateFormat = "dd/MM/yyyy";
    private static String dateDataBase = "yyyy'-'MM'-'dd HH:mm:ss";
    private static String timeFormat = "hh:mm aaa";
    public static String dateTramaFormat="yyyyMMdd";

    public static String dateFormatURI="yyyy-MM-dd";
    private static final Locale locale = new Locale("PE", "es");

    public static String getDateTime()
    {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        return ts.toString();
    }
    public static Timestamp parseTimeStampSQL(String date)
    {
        Timestamp timestamp=null;
       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
           Date parsedDate = dateFormat.parse(date);
           timestamp = new Timestamp(parsedDate.getTime());
       }catch (Exception e){

       }
        return timestamp;
    }

    public static Timestamp parseTimeStampSQL(String date,String format) throws Exception
    {
        Timestamp timestamp=null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date parsedDate = dateFormat.parse(date);
            timestamp = new Timestamp(parsedDate.getTime());
        }catch (Exception e){
        throw e;
        }
        return timestamp;
    }

    public static String getDate()
    {
        return getDateTime().substring(0, 10);
    }
    public static java.sql.Date getDateSQL()
    {
        return new java.sql.Date(new Date().getTime());
    }
    public static String formatDate(Date date)
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(dateFormat)).format(date);
        else
            System.out.println("[SIGN]: The param date is null");
        return ret;
    }
    public static String formatDate(long timeFromDate)
    {
        Date date = new Date(timeFromDate);
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(dateFormat)).format(date);
        else
            System.out.println("[SIGN]: The param date is null");
        return ret;
    }
    public static String formatDate(Date date, String mask)
            throws Exception
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(mask)).format(date);
        else
            System.out.println("[SIGN]: The param date is null");
        return ret;
    }

    public static String formatTime(Date date)
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(timeFormat)).format(date);
        else
            System.out.println("[SIGN]: The param date is null");
        return ret;
    }

    public static String formatTime(Date date, String mask)
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(mask)).format(date);
        else
            System.out.println("[SIGN]: The param date is null");
        return ret;
    }

    public static Date parseDate(String date)
            throws Exception
    {
        Date result = null;
        try
        {
            result = (new SimpleDateFormat(dateFormat)).parse(date);
            return result;
        }
        catch(ParseException e)
        {
            System.out.println("[SIGN]: "+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    public static boolean parseDateBoolean(String date)
    {
        boolean  estado=false;
        Date result = null;
        try
        {
            result = (new SimpleDateFormat(dateFormat)).parse(date);
            estado=true;
        }
        catch(ParseException e)
        {
            //logger.error(e.getMessage(), e);
            //throw new Exception(e.getMessage());
            estado=false;
        }
        return estado;
    }






    public static Date parseDate(String date, String format)
            throws Exception
    {
        Date result = null;
        try
        {
            result = (new SimpleDateFormat(format)).parse(date);
            return result;
        }
        catch(ParseException e)
        {
            System.out.println("[SIGN]: "+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static java.sql.Date parseDateSQL(String date, String format)
            throws Exception
    {
        Date result = null;
        java.sql.Date sqlResult = null;
        try
        {
            result = (new SimpleDateFormat(format)).parse(date);
            System.out.println("Resultado formateado a util.date = " + result);
            sqlResult = new java.sql.Date(result.getTime());

            return sqlResult;
        }
        catch(ParseException e)
        {
            System.out.println("[SIGN]: "+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static java.sql.Date parseDateSQL(String date)
            throws Exception
    {
        Date result = null;
        java.sql.Date sqlResult = null;
        try
        {
            result = (new SimpleDateFormat(dateFormat)).parse(date);
            System.out.println("Resultado formateado a util.date = " + result);
            sqlResult = new java.sql.Date(result.getTime());

            return sqlResult;
        }
        catch(ParseException e)
        {
            System.out.println("[SIGN]: "+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public static String getYear()
    {
        return getDate().substring(0, 4);
    }

    public static String getMonth()
    {
        return getDate().substring(5, 7);
    }

    public static String getDay()
    {
        return getDate().substring(8, 10);
    }

    public static String getYear(String date)
    {
        String res = "";
        if(date != null && date.length() >= 10)
            res = date.substring(6, 10);
        else
            System.out.println("[SIGN]: The param date is null or its length not is >= 10");
        return res;
    }

    public static String getMonth(String date)
    {
        String res = "";
        if(date != null && date.length() >= 10)
            res = date.substring(3, 5);
        else
            System.out.println("[SIGN]: The param date is null or its length not is >= 10");
        return res;
    }

    public static String getDay(String date)
            throws Exception
    {
        String res = "";
        if(date != null && date.length() >= 10)
            res = date.substring(0, 2);
        else
            System.out.println("[SIGN]: The param date is null or its length not is >= 10");
        return res;
    }

    public static String getLetterHeaderSpa(String city)
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, d MMMM 'del 'yyyy", locale)).format(new Date()));
        return res.toString();
    }

    public static String getLetterHeaderEng(String city)
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, MMMM d, yyyyy ", Locale.US)).format(new Date()));
        return res.toString();
    }

    public static String getLetterHeaderSpa(String city, Date date)
            throws Exception
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, d MMMM 'del 'yyyy", locale)).format(date));
        return res.toString();
    }

    public static String getLetterHeaderEng(String city, Date date)
            throws Exception
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, MMMM d, yyyyy ", Locale.US)).format(date));
        return res.toString();
    }

    public static Timestamp getTimestamp()
    {
        return new Timestamp((new Date()).getTime());
    }

    private static GregorianCalendar getCalendar()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setLenient(false);
        return calendar;
    }

    public static Date addMonths(Date date, int months)
            throws Exception
    {
        GregorianCalendar gCalendar = getCalendar();
        gCalendar.setTime(date);
        gCalendar.add(2, months);
        return gCalendar.getTime();
    }

    public static Date addDays(Date date, int days)
            throws Exception
    {
        GregorianCalendar gCalendar = getCalendar();
        gCalendar.setTime(date);
        gCalendar.add(5, days);
        return gCalendar.getTime();
    }

    public static Date addYears(Date date, int years)
            throws Exception
    {
        GregorianCalendar gCalendar = getCalendar();
        gCalendar.setTime(date);
        gCalendar.add(1, years);
        return gCalendar.getTime();
    }

    public static long changeDateToLong(Date date)
            throws Exception
    {
        String ans = "";
        GregorianCalendar cal = new GregorianCalendar();
        try
        {
            cal.setTime(date);
            ans = "" + cal.get(1);
            if(("" + (1 + cal.get(2))).length() < 2)
                ans = "" + Integer.parseInt(ans + "0" + (1 + cal.get(2)));
            else
                ans = ans + (1 + cal.get(2));
            if(("" + cal.get(5)).length() < 2)
                ans = "" + Integer.parseInt(ans + "0" + cal.get(5));
            else
                ans = ans + cal.get(5);
        }
        catch(Throwable e)
        {
            ans = "0";
            throw new Exception(e.getMessage());
        }
        return Long.parseLong(ans);
    }

    public static long changeTimeToLong(Time time)
            throws Exception
    {
        String hour = time.toString();
        long ans = 0L;
        hour = hour.substring(0, 2) + hour.substring(3, 5) + hour.substring(6);
        ans = Long.parseLong(hour);
        return ans;
    }

    public static boolean isDateEqualOrGreater(Date startDate, Date endDate)
            throws Exception
    {
        boolean resul = false;
        try
        {
            long date1 = changeDateToLong(startDate);
            long date2 = changeDateToLong(endDate);
            if(date1 >= date2)
                resul = true;
            return resul;
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean isDateGreater(Date startDate, Date endDate)
            throws Exception
    {
        boolean resul = false;
        try
        {
            long date1 = changeDateToLong(startDate);
            long date2 = changeDateToLong(endDate);
            return date1 > date2;
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static int difDays(Date startDate, Date endDate)
    {
        if(startDate != null && endDate != null)
        {
            long dateInitial = startDate.getTime();
            long dateFinal = endDate.getTime();
            long diffMillis = dateInitial - dateFinal;
            long diffDays = diffMillis / 0x5265c00L;
            return (int)diffDays;
        } else
        {
            System.out.println("[SIGN]: Dates received: " + startDate + "-" + endDate);
            return -1;
        }
    }

    public static Date getEndDay(Date date)
            throws Exception
    {
        Date result = null;
        if(date != null)
        {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.set(11, calendar.getMaximum(11));
            calendar.set(12, calendar.getMaximum(12));
            calendar.set(13, calendar.getMaximum(13));
            calendar.set(14, calendar.getMaximum(14));
            result = calendar.getTime();
        }
        return result;
    }

    public static Date getStartDay(Date date)
            throws Exception
    {
        Date result = null;
        if(date != null)
        {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.set(11, calendar.getMinimum(11));
            calendar.set(12, calendar.getMinimum(12));
            calendar.set(13, calendar.getMinimum(13));
            calendar.set(14, calendar.getMinimum(14));
            result = calendar.getTime();
        }
        return result;
    }

    public static int getYearsOld(Date dateBirthday)
            throws Exception
    {
        int yearsOld = 0;
        try
        {
            GregorianCalendar calendarDateBirthday = new GregorianCalendar();
            calendarDateBirthday.setTime(dateBirthday);
            GregorianCalendar calendarActualDate = new GregorianCalendar();
            int actualYear = calendarActualDate.get(1);
            int birthdayYear = calendarDateBirthday.get(1);
            int actualMonth = calendarActualDate.get(2);
            int birthdayMonth = calendarDateBirthday.get(2);
            int actualDay = calendarActualDate.get(5);
            int birthdayDay = calendarDateBirthday.get(5);
            yearsOld = actualYear - 1 - birthdayYear;
            if(actualMonth == birthdayMonth && actualDay >= birthdayDay)
                yearsOld++;
            if(actualMonth > birthdayMonth)
                yearsOld++;
        }
        catch(Exception e)
        {
            System.out.println("[SIGN]: "+dateBirthday.toString());
            return -1;
        }
        return yearsOld;
    }

    public static boolean validateDate(String strDate, String mask)
            throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat(mask);
        sdf.setLenient(false);
        try
        {
            Date date = sdf.parse(strDate);
            return true;
        }
        catch(Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }

    public static String convertTimestampToString(Timestamp time)
    {
        String tOld = time.toString();
        if(tOld.length() < 21)
        {
            return "";
        } else
        {
            String tNew = tOld.substring(8, 10) + "/" + tOld.substring(5, 7) + "/" + tOld.substring(0, 4);
            return tNew;
        }
    }

    public static int difDateMinutes(Date startDate, Date endDate)
    {
        /*long l1 = date1.getTime();
        long l2 = date2.getTime();

        long l3 = l1-l2;
        return (int)(l3/1000*60); */

        if(startDate != null && endDate != null)
        {
            long dateInitial = startDate.getTime();
            long dateFinal = endDate.getTime();
            long diffMillis = dateInitial - dateFinal;
            long diffDays = diffMillis / 0xea60L ;
            return (int)diffDays;
        } else
        {
            System.out.println("[SIGN]: Dates received: " + startDate + "-" + endDate);
            return -1;
        }
    }
    public static Date getDateYesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    public static Date getDateMenosDias(Integer nroDias) {
        final Calendar cal = Calendar.getInstance();
        nroDias=nroDias*-1;
        cal.add(Calendar.DATE, nroDias);
        return cal.getTime();
    }

}
