package ru.perm.v.el59.office.loaders;

import java.io.File;
import java.io.FilenameFilter;

public class XmlFilenameFilter implements FilenameFilter {
	@Override
	public boolean accept(File arg0, String arg1) {
		if (arg1.toUpperCase().indexOf(".XML") > 0)
			return true;
		else
			return false;
	}
}
