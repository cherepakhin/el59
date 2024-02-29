package ru.perm.v.el59.office.report;

import net.sf.jxls.exception.ParsePropertyException;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.dto.FileAttach;
import ru.perm.v.el59.office.emailer.IEmailer;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.util.Helper;
import ru.perm.v.el59.office.util.Period;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Формирование отчета реализации и отправка по почте
 * @author vasi
 *
 */
public class ReportSender {

	private static Logger LOG = Logger.getLogger(ReportSender.class);
	private IEmailer emailer;
	private IShopProvider shopProvider;
	private ReportBuilder reportBuilder;

	private List<String> shopCodsForWeekReport = new ArrayList<String>();
	private List<String> emailsForWeek = new ArrayList<String>();
	private Integer lastCountDays=7;

	/**
	 * Отчет по реализации за неделю
	 */
	public void sendRealWeek() {
		Period period = getPeriod(getLastCountDays());
		List<FileAttach> attaches = new ArrayList<FileAttach>();
		for (String shopCod : shopCodsForWeekReport) {
			Shop shop = getShopProvider().read(shopCod);
			if (shop != null) {
				try {
					FileAttach attach = new FileAttach();
					byte[] data = getReportBuilder().createXlsRealWeek(shop,
							period.fromDate, period.toDate);
					attach.name = shop.getName() + ".xls";
					attach.body = data;
					attaches.add(attach);
				} catch (ParsePropertyException e) {
					LOG.error(e);
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					LOG.error(e);
					e.printStackTrace();
				} catch (IOException e) {
					LOG.error(e);
					e.printStackTrace();
				}
			}
		}
		String emails = "";
		for (String email : emailsForWeek) {
			emails = emails + email + ";";
		}
		
		getEmailer().send(null, emails, "", getSubjectForRealWeek(period), attaches,true);
	}

	public String getSubjectForRealWeek(Period period) {
		String subject = "Реализация ("
				+ Helper.getDateFornmatter().format(period.fromDate) 
				+ "-"
				+ Helper.getDateFornmatter().format(period.toDate) 
				+ ")";
		return subject;
	}
	
	public Period getPeriod(int lastDays) {
		Period period = new Period();
		Calendar cal = Calendar.getInstance();
		period.toDate = Helper.getNullHour(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, lastDays * -1);
		period.fromDate = Helper.getNullHour(cal.getTime());
		return period;
	}

	public IEmailer getEmailer() {
		return emailer;
	}

	public void setEmailer(IEmailer emailer) {
		this.emailer = emailer;
	}

	public List<String> getShopCodsForWeekReport() {
		return shopCodsForWeekReport;
	}

	public void setShopCodsForWeekReport(List<String> shopCodsForWeekReport) {
		this.shopCodsForWeekReport = shopCodsForWeekReport;
	}

	public ReportBuilder getReportBuilder() {
		return reportBuilder;
	}

	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public List<String> getEmailsForWeek() {
		return emailsForWeek;
	}

	public void setEmailsForWeek(List<String> emailsForWeek) {
		this.emailsForWeek = emailsForWeek;
	}

	public Integer getLastCountDays() {
		return lastCountDays;
	}

	public void setLastCountDays(Integer lastCountDays) {
		this.lastCountDays = lastCountDays;
	}
}
