package ru.perm.v.el59.office.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Vars {
   public static final String[] typeTovar = new String[]{"Основной", "Дополнительный", "Разное", "Другое"};
   public static String[] matrix = new String[0];
   public static final String[] ABCD = new String[]{"A", "B", "C", "D"};
   public static final String[] system = new String[]{"BEST", "TRADE"};
   public static final String[] stocks = new String[]{"Основной товар", "Виртуальный", "Уцененный", "Клиентской техники", "Предродажный ремонт", "Брак", "Транзитный"};
   public static final String[] typesstock = new String[]{"Основной товар", "Брак"};
   public static final String[] kadds = new String[]{"", "20", "90", "70", "00", "30", "10", "99"};
   public static final Integer qtyBasePrise = 3;
   private static DecimalFormat df = null;
   private static DecimalFormat stringdf;
   private static SimpleDateFormat sdf = null;
   private static Manager currentManager = new Manager();
   public static String[] startArgs;
   public static String[] attr = new String[0];
   public static String currentShop;
   public static String currentTypedoc;
   public static String currentSupplier;
   private static String ftp = "";
   private static String ftpuser = "";
   private static String ftppass = "";
   private static String tdocfolder = "";
   public static final String DirForCommerceML = "DirForCommerceML";

   public static SimpleDateFormat getDateFormat() {
      if (sdf == null) {
         sdf = new SimpleDateFormat("dd.MM.yyyy");
      }

      return sdf;
   }

   public static Long getDiffDate(Date fromdate, Date todate) {
      return (todate.getTime() - fromdate.getTime()) / 86400000L;
   }

   public static DecimalFormat getDecimalFormat() {
      if (df == null) {
         df = new DecimalFormat("#,###,##0.00");
      }

      return df;
   }

   public static BigDecimal getBigDecimal(Object value) {
      new BigDecimal("0.00");

      BigDecimal newValue;
      try {
         newValue = new BigDecimal((String)value);
      } catch (Exception var5) {
         try {
            newValue = (BigDecimal)getDecimalFormat().parseObject((String)value);
         } catch (Exception var4) {
            newValue = new BigDecimal("0.00");
         }
      }

      return newValue;
   }

   public static DecimalFormat getStringDecimalFormat() {
      if (stringdf == null) {
         stringdf = new DecimalFormat("#,###,##0.00");
         stringdf.setGroupingSize(3);
         stringdf.setGroupingUsed(true);
      }

      return stringdf;
   }

   public static Manager getCurrentManager() {
      return currentManager;
   }

   public static void setCurrentManager(Manager currentManager) {
      Vars.currentManager = currentManager;
   }

   public static String format(Object o) {
      if (o == null) {
         return "";
      } else if (o.getClass().equals(BigDecimal.class)) {
         return getDecimalFormat().format((BigDecimal)o);
      } else if (o.getClass().equals(Date.class)) {
         return getDateFormat().format((Date)o);
      } else if (o.getClass().equals(java.sql.Date.class)) {
         return getDateFormat().format((Date)o);
      } else if (o.getClass().equals(Timestamp.class)) {
         return getDateFormat().format((Date)o);
      } else if (o.getClass().equals(Boolean.class)) {
         return (Boolean)o ? "Да" : "Нет";
      } else {
         return o.toString();
      }
   }

   public static String formatSimpleBigDecimal(Object o) {
      DecimalFormat simpleformat = new DecimalFormat("0");
      if (o == null) {
         return "";
      } else {
         return o.getClass().equals(BigDecimal.class) ? simpleformat.format((BigDecimal)o) : o.toString();
      }
   }

   public static BigDecimal getNewValue(Object value) {
      BigDecimal newValue = getBigDecimal(value);
      return newValue;
   }

   public static Date getTodayAdd(Integer qtyDays) {
      Calendar date = Calendar.getInstance();
      int year = date.get(1);
      int month = date.get(2);
      int day = date.get(5);
      date.clear();
      date.set(year, month, day);
      date.add(6, qtyDays);
      return date.getTime();
   }

   public static Date addDay(Date date, Integer qtyDays) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(6, qtyDays);
      return calendar.getTime();
   }

   public static String copyFile(String src, String dst) throws IOException {
      FileInputStream fr = new FileInputStream(src);
      FileOutputStream fw = new FileOutputStream(dst);
      int c = fr.available();
      byte[] b = new byte[c];
      fr.read(b);
      fw.write(b);
      fr.close();
      fw.close();
      return dst;
   }

   public static Date getFirstDayPrevMonth(Date date) {
      Calendar fromdateCalendar = Calendar.getInstance();
      fromdateCalendar.setTime(date);
      int month = fromdateCalendar.get(2);
      int year = fromdateCalendar.get(1);
      if (month == 0) {
         month = 11;
         --year;
      } else {
         --month;
      }

      fromdateCalendar.set(year, month, 1);
      return fromdateCalendar.getTime();
   }

   public static Date getLastDayOfPrevMonth(Date date) {
      Calendar todateCalendar = Calendar.getInstance();
      todateCalendar.setTime(date);
      int month = todateCalendar.get(2);
      int year = todateCalendar.get(1);
      todateCalendar.set(year, month, 1);
      Date todate = addDay(todateCalendar.getTime(), -1);
      return todate;
   }

   public static String getFtp() {
      return ftp;
   }

   public static void setFtp(String ftp) {
      Vars.ftp = ftp;
   }

   public static String getFtpuser() {
      return ftpuser;
   }

   public static void setFtpuser(String ftpuser) {
      Vars.ftpuser = ftpuser;
   }

   public static String getFtppass() {
      return ftppass;
   }

   public static void setFtppass(String ftppass) {
      Vars.ftppass = ftppass;
   }

   public static String getTdocfolder() {
      return tdocfolder;
   }

   public static void setTdocfolder(String tdocfolder) {
      Vars.tdocfolder = tdocfolder;
   }
}
