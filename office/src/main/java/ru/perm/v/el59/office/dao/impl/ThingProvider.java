package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.db.Thing;
import ru.perm.v.el59.office.iproviders.IThingProvider;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.util.HashMap;
import java.util.List;

//TODO: fix methods
public class ThingProvider extends GenericDaoHibernateImpl<Thing, Long>
		implements IThingProvider {

	protected HashMap<String, String> hash;

	public ThingProvider(Class<Thing> type) {
		super(type);
	}

	public List<Thing> getAll() {
		CommonCritery critery = new CommonCritery("");
		return getByCritery(critery);
	}

	@Override
	public String createFullDescription(String name) {
		if (hash == null) {
			List<Thing> list = getAll();
			hash = new HashMap<String, String>();
			for (Thing thing : list) {
				hash.put(thing.getName(), thing.getNameFull());
			}
		}
		/*
		 * String[] s = name.trim().split(" "); if(s.length==0) return name;
		 * for(int l=s.length-1;l>=0;l--) { String testname = ""; for(int
		 * i=0;i<=l;i++) { testname=testname+" "+s[i]; } String rest = "";
		 * if(hash.containsKey(testname.trim())) { for(int
		 * i=l+1;i<=s.length-1;i++) { rest=rest+" "+s[i]; } String r =
		 * hash.get(testname.trim()); String out =r.trim()+" "+rest.trim();
		 * System.out.println(name+"->"+out); return out; } }
		 */
		// String[] s = name.trim().split(" ");
		// if(s.length==0) return name;
		
		for (String shortName : hash.keySet()) {
			if(name.startsWith(shortName)) {
				String newName = name.replace(shortName, hash.get(shortName));
				return newName;
			}
		}
/*		for (int l = name.length() - 1; l >= 0; l--) {
			String testname = name.substring(0, l);
			if (hash.containsKey(testname.trim())) {
				String r = hash.get(testname.trim());
				String rest = name.substring(l);
				String out = r.trim() + " " + rest.trim();
				// System.out.println(name+"->"+out);
				return out;
			}
		}
*/		return name;
	}

}
