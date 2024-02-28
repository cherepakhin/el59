package ru.perm.v.el59.office.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConstAnnotFeature implements Serializable {
   private static final long serialVersionUID = -6986015225682044725L;
   public static final String ZNAMEALL = "ZNAMEALL";
   public static final String ZBRAND = "ZBRAND";
   public static final String ZKODMOD = "ZKODMOD";
   public static final String ZNAMERUSSIA = "ZNAMERUSSIA";
   public static final String ZPROPERTIES = "ZPROPERTIES";
   public static final String ZANNOTACIA = "ZANNOTACIA";
   public static final String ZANNOTACIA2 = "ZANNOTACIA2";
   public static final String ZANNOTACIA3 = "ZANNOTACIA3";
   public static final String ZANNOTACIA4 = "ZANNOTACIA4";
   public static final String ZCOUNTRY = "ZCOUNTRY";
   public static final String ZDNTFORGET1 = "ZDNTFORGET1";
   public static final String ZDNTFORGET2 = "ZDNTFORGET2";
   public static final String ZDNTFORGET3 = "ZDNTFORGET3";
   public static final String ZWARRANTY = "ZWARRANTY";
   public static final Map<String, String> hash = new HashMap();

   public static String getName(String code) {
      return hash.containsKey(code) ? (String)hash.get(code) : "";
   }

   static {
      hash.put("ZNAMEALL", "ОБОБЩЕННОЕ НАИМЕНОВАНИЕ");
      hash.put("ZBRAND", "НАЗВАНИЕ ФИРМЫ НА АНГЛИЙСКОМ");
      hash.put("ZKODMOD", "КОД МОДЕЛИ");
      hash.put("ZNAMERUSSIA", "НАЗВАНИЕ ФИРМЫ НА РУССКОМ");
      hash.put("ZPROPERTIES", "ОСНОВНАЯ ХАРАКТЕРИСТИКА");
      hash.put("ZANNOTACIA", "АННОТАЦИЯ 1-Я СТРОКА");
      hash.put("ZANNOTACIA2", "АННОТАЦИЯ 2-Я СТРОКА");
      hash.put("ZANNOTACIA3", "АННОТАЦИЯ 3-Я СТРОКА");
      hash.put("ZANNOTACIA4", "АННОТАЦИЯ 4-Я СТРОКА");
      hash.put("ZCOUNTRY", "СТРАНА ПРОИЗВОДИТЕЛЬ ТОВАРА");
      hash.put("ZDNTFORGET1", "НЕ ЗАБУДЬТЕ строка 1");
      hash.put("ZDNTFORGET2", "НЕ ЗАБУДЬТЕ строка 2");
      hash.put("ZDNTFORGET3", "НЕ ЗАБУДЬТЕ строка 3");
      hash.put("ZWARRANTY", "СРОК ГАРАНТИИ");
   }
}
