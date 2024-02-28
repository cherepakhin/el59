package ru.el59.office.db.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import ru.el59.office.db.FeaturePrice;

public class Annotation {
   private String annotation = "";
   private List<String> s_annot = new ArrayList();
   private static HashMap<String, Integer> hashPos = new HashMap();
   private HashMap<String, String> hashVal = new HashMap();
   private static HashMap<String, String> hashRus = new HashMap();
   private static ArrayList<String> listMainFeatures = new ArrayList();
   private static final String ENDLINE = "\r\n";

   public Annotation() {
      for(int i = 1; i < 36; ++i) {
         this.s_annot.add(this.getFormatAnnotString(i, ""));
      }

      this.putHash("ZNAMEALL", "ОБОБЩЕННОЕ НАИМЕНОВАНИЕ", 17);
      listMainFeatures.add("ZNAMEALL");
      this.putHash("ZBRAND", "НАЗВАНИЕ ФИРМЫ НА АНГЛИЙСКОМ", 18);
      listMainFeatures.add("ZBRAND");
      this.putHash("ZKODMOD", "КОД МОДЕЛИ", 19);
      listMainFeatures.add("ZKODMOD");
      this.putHash("ZNAMERUSSIA", "НАЗВАНИЕ ФИРМЫ НА РУССКОМ", 20);
      listMainFeatures.add("ZNAMERUSSIA");
      this.putHash("ZPROPERTIES", "ОСНОВНАЯ ХАРАКТЕРИСТИКА", 26);
      listMainFeatures.add("ZPROPERTIES");
      this.putHash("ZANNOTACIA", "АННОТАЦИЯ 1-Я СТРОКА", 21);
      listMainFeatures.add("ZANNOTACIA");
      this.putHash("ZANNOTACIA2", "АННОТАЦИЯ 2-Я СТРОКА", 22);
      listMainFeatures.add("ZANNOTACIA2");
      this.putHash("ZANNOTACIA3", "АННОТАЦИЯ 3-Я СТРОКА", 23);
      listMainFeatures.add("ZANNOTACIA3");
      this.putHash("ZANNOTACIA4", "АННОТАЦИЯ 4-Я СТРОКА", 24);
      listMainFeatures.add("ZANNOTACIA4");
      this.putHash("ZCOUNTRY", "СТРАНА ПРОИЗВОДИТЕЛЬ ТОВАРА", 25);
      listMainFeatures.add("ZCOUNTRY");
      this.putHash("ZDNTFORGET1", "НЕ ЗАБУДЬТЕ строка 1", 3);
      listMainFeatures.add("ZDNTFORGET1");
      this.putHash("ZDNTFORGET2", "НЕ ЗАБУДЬТЕ строка 2", 4);
      listMainFeatures.add("ZDNTFORGET2");
      this.putHash("ZDNTFORGET3", "НЕ ЗАБУДЬТЕ строка 3", 5);
      listMainFeatures.add("ZDNTFORGET3");
      this.putHash("ZWARRANTY", "СРОК ГАРАНТИИ", 6);
      listMainFeatures.add("ZWARRANTY");
      this.hashVal.put("ZSGAB", "");
      hashPos.put("ZSGAB", 27);
      hashRus.put("ZSGAB", "");
      this.hashVal.put("ZSMOT", "");
      hashPos.put("ZSMOT", 28);
      hashRus.put("ZSMOT", "");
      this.hashVal.put("ZSMZAG", "");
      hashPos.put("ZSMZAG", 29);
      hashRus.put("ZSMZAG", "");
      this.hashVal.put("ZANNOTACIA0", "");
      hashPos.put("ZANNOTACIA0", 30);
      hashRus.put("ZANNOTACIA0", "");
      this.hashVal.put("ZANNOTACIA20", "");
      hashPos.put("ZANNOTACIA20", 31);
      hashRus.put("ZANNOTACIA20", "");
      this.hashVal.put("ZANNOTACIA30", "");
      hashPos.put("ZANNOTACIA30", 32);
      hashRus.put("ZANNOTACIA30", "");
      this.hashVal.put("ZANNOTACIA40", "");
      hashPos.put("ZANNOTACIA40", 33);
      hashRus.put("ZANNOTACIA40", "");
      this.hashVal.put("ZFRIDKK", "");
      hashPos.put("ZFRIDKK", 35);
      hashRus.put("ZFRIDKK", "");
   }

   public String getAnnotation(Collection<FeaturePrice> listFeaturePrice) {
      Iterator i$ = listFeaturePrice.iterator();

      while(i$.hasNext()) {
         FeaturePrice featurePrice = (FeaturePrice)i$.next();
         this.setFeature(featurePrice.getCode(), featurePrice.getVal());
      }

      return this.annotation;
   }

   private String getFormatAnnotString(Integer i, String s) {
      return !s.contains("\r\n") ? i.toString() + "." + s + "\r\n" : s;
   }

   private void putHash(String nameEng, String nameRus, int pos) {
      this.hashVal.put(nameEng, "");
      hashPos.put(nameEng, pos);
      hashRus.put(nameEng, nameRus);
   }

   public void setFeature(String name, String val) {
      if (this.hashVal.containsKey(name)) {
         this.hashVal.put(name, val);
         this.s_annot.set((Integer)hashPos.get(name) - 1, this.getFormatAnnotString((Integer)hashPos.get(name), val));
         if (name.contains("ZANNOTACIA")) {
            String name0 = name + "0";
            this.hashVal.put(name0, val);
            this.s_annot.set((Integer)hashPos.get(name0) - 1, this.getFormatAnnotString((Integer)hashPos.get(name0), val));
         }

         this.annotation = "";

         for(int i = 0; i < 35; ++i) {
            this.annotation = this.annotation + (String)this.s_annot.get(i);
         }
      }

   }

   private void splitAnnotation() {
      String[] split = this.annotation.split("\r\n");

      for(int i = 0; i < split.length; ++i) {
         if (hashPos.containsValue(i + 1)) {
            Iterator iter = hashPos.keySet().iterator();

            while(iter.hasNext()) {
               String feature = (String)iter.next();
               if ((Integer)hashPos.get(feature) == i + 1) {
                  String s = split[i];
                  String snum = String.valueOf(i + 1) + ".";
                  s = s.replaceFirst(snum, "");
                  s = s.replaceFirst("\r\n", "");
                  this.hashVal.put(feature, s);
               }
            }
         }
      }

   }

   public String getFeature(String val) {
      this.splitAnnotation();
      return this.hashVal.containsKey(val) ? (String)this.hashVal.get(val) : "";
   }

   public List<FeaturePrice> getListFeaturePrice(String annot) {
      List<FeaturePrice> list = new ArrayList();
      if (annot == null) {
         return list;
      } else {
         this.annotation = annot;
         this.splitAnnotation();
         Iterator iter = this.hashVal.entrySet().iterator();

         while(iter.hasNext()) {
            Entry<String, String> e = (Entry)iter.next();
            String code = (String)e.getKey();
            if (listMainFeatures.contains(code)) {
               String val = (String)e.getValue();
               FeaturePrice f = new FeaturePrice();
               f.setCode(code);
               f.setVal(val);
               f.setName((String)hashRus.get(code));
               list.add(f);
            }
         }

         return list;
      }
   }
}
