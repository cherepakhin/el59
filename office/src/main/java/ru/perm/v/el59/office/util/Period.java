package ru.perm.v.el59.office.util;

import java.util.Date;

public class Period {
	public Date fromDate;
	public Date toDate;
	public Period() {
		super();
		fromDate = Helper.getNullHour(new Date());
		toDate = Helper.getNullHour(new Date());
	}
}
