package ru.perm.v.el59.office.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.perm.v.el59.office.db.Feature;

public class DimensionRecognizer {

	private final static String NAME_GROUP="Габариты";
	private Pattern pname;
	private Pattern pval;

	public DimensionRecognizer() {
		super();
		pname = Pattern.compile("(\\().*(Ш|Г|В|Т).(Ш|Г|В|Т).(Ш|Г|В|Т).*(\\))");
		pval = Pattern.compile("(\\d*\\.*\\d*).(\\d*\\.*\\d*).(\\d*\\.*\\d*).*");
		
	}

	public Dimension getDimension(String name,String val) {
//		System.out.println(name);
//		System.out.println(val);
		
		Matcher mname = pname.matcher(name);
		
		ArrayList<String> listName = new ArrayList<String>();
		while (mname.find()) {
			listName.add(mname.group(2));
//			System.out.println(mname.group(2));
			listName.add(mname.group(3));
//			System.out.println(mname.group(3));
			listName.add(mname.group(4));
//			System.out.println(mname.group(4));
		}

		Matcher mval = pval.matcher(val);
		ArrayList<String> listVal = new ArrayList<String>();
		while (mval.find()) {
			listVal.add(mval.group(1));
//			System.out.println(mval.group(1));
			listVal.add(mval.group(2));
//			System.out.println(mval.group(2));
			listVal.add(mval.group(3));
//			System.out.println(mval.group(3));
		}
		
		Dimension ret = new Dimension();
		for (int i =0;i<listName.size();i++) {
			ret=setVal(ret, listName.get(i), listVal.get(i));
		}
		
		return ret;
	}
	
	private Dimension setVal(Dimension d , String nameVal,String val) {
		if(nameVal.equals("Ш")) {
			d.width=val;
		}
		if(nameVal.equals("В")) {
			d.height=val;
		}
		if(nameVal.equals("Г")) {
			d.depth=val;
		}
		return d;
	}
	
	public List<Feature> recognize(Feature f) {
		ArrayList<Feature> ret = new ArrayList<Feature>();
		if(f.getName().toLowerCase().trim().contains("ширина")) {
			Feature _f = new Feature();
			_f.setTovarInfo(f.getTovarInfo());
			_f.setGrp(NAME_GROUP);
			_f.setName("Ширина");
			_f.setVal(f.getVal().replaceAll("\\D*", ""));
			ret.add(_f);
			return ret;
		}

		if(f.getName().toLowerCase().trim().contains("глубина")) {
			Feature _f = new Feature();
			_f.setTovarInfo(f.getTovarInfo());
			_f.setGrp(NAME_GROUP);
			_f.setName("Глубина");
			_f.setVal(f.getVal().replaceAll("\\D*", ""));
			ret.add(_f);
			return ret;
		}

		if(f.getName().toLowerCase().trim().contains("высота")) {
			Feature _f = new Feature();
			_f.setTovarInfo(f.getTovarInfo());
			_f.setGrp(NAME_GROUP);
			_f.setName("Высота");
			_f.setVal(f.getVal().replaceAll("\\D*", ""));
			ret.add(_f);
			return ret;
		}

		Dimension d = getDimension(f.getName(), f.getVal());
		if(d.width.equals("") && d.height.equals("") && d.depth.equals("")) {
			ret.add(f);
		} else {
			Feature fwidth = new Feature();
			fwidth.setTovarInfo(f.getTovarInfo());
			fwidth.setGrp(NAME_GROUP);
			fwidth.setName("Ширина");
			fwidth.setVal(d.width);
			ret.add(fwidth);
			
			Feature fheight = new Feature();
			fheight.setTovarInfo(f.getTovarInfo());
			fheight.setGrp(NAME_GROUP);
			fheight.setName("Высота");
			fheight.setVal(d.height);
			ret.add(fheight);
			
			Feature fdepth = new Feature();
			fdepth.setTovarInfo(f.getTovarInfo());
			fdepth.setGrp(NAME_GROUP);
			fdepth.setName("Глубина");
			fdepth.setVal(d.depth);
			ret.add(fdepth);
		}
		return ret;
	}
}
