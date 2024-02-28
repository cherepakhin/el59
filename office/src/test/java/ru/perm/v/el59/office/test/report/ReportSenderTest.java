package ru.perm.v.el59.office.test.report;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.dao.IGenericDao;
import ru.el59.office.report.ReportSender;
import ru.el59.office.util.Helper;
import ru.el59.office.util.Period;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class ReportSenderTest {

	@Autowired
	protected ApplicationContext context;

	//	@Test
	public void createMoveWeek() {
		fail("Not yet implemented");
	}

	@Test
	public void getPeriod() {
		ReportSender sender= new ReportSender();
		Period period = sender.getPeriod(7);
		Date expectToDate = Helper.getNullHour(new Date());
		System.out.println(period.fromDate);
		System.out.println(period.toDate);
		assertTrue(period.toDate.compareTo(expectToDate)==0);
	}
	
	@Test
	public void getSubjectForRealWeek() {
		ReportSender sender= new ReportSender();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH,Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_YEAR, 7);
		Period period = new Period();
		period.toDate=Helper.getNullHour(cal.getTime());
		
		cal.add(Calendar.DAY_OF_YEAR, -7);
		period.fromDate=cal.getTime();
		
		System.out.println(period.fromDate);
		System.out.println(period.toDate);
		String subj = sender.getSubjectForRealWeek(period);
		System.out.println(subj);
		assertTrue(subj.compareTo("Реализация (01.01.2017-08.01.2017)")==0);
	}

	@Test
	public void sendRealWeek() {
		ReportSender sender= (ReportSender) context.getBean("reportSender");
		sender.sendRealWeek();
	}
}
