package ru.perm.v.el59.office.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import ru.perm.v.el59.office.critery.MoveCritery;
import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.OpGroup;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.IMoveProvider;
import ru.perm.v.el59.office.iproviders.IOpGroupProvider;
import ru.perm.v.el59.office.util.ExcelReport;

/**
 * Конструктор отчетов
 * @author vasi
 *
 */
public class ReportBuilder {
	private IMoveProvider moveProvider;
	private IOpGroupProvider opGroupProvider;
	private ExcelReport excelReport;

	private String realTemplReport = "";
	private String nameReal = "";
	private List<Integer> excludeNnums = new ArrayList<Integer>();

	/**
	 * Отчет по реализации (xls)
	 * 
	 * @param shop
	 *            - магазин
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws ParsePropertyException
	 */
	public byte[] createXlsRealWeek(Shop shop, Date fromDate, Date toDate)
			throws ParsePropertyException, InvalidFormatException, IOException {
		// Загрузка движений
		List<Move> moves = getMoves(shop, getNameReal(), fromDate, toDate);
		HashMap<String, Object> beans = new HashMap<String, Object>();
		beans.put("shop", shop);
		beans.put("fromDate", fromDate);
		beans.put("toDate", toDate);
		beans.put("moves", moves);
        ClassLoader classLoader = this.getClass().getClassLoader();
        String templPath = classLoader.getResource(getRealTemplReport()).getFile();
		byte[] ret = getExcelReport().build(templPath, beans);
		return ret;
	}

	private List<Move> getMoves(Shop shop, String nameOpGroup, Date fromDate,
			Date toDate) {
		MoveCritery moveCritery = new MoveCritery();
		moveCritery.fromDate = fromDate;
		moveCritery.toDate = toDate;
		moveCritery.arrshops.add(shop);
//		moveCritery.sort.add("ddate");
		moveCritery.sort.add("tovar.name");
		moveCritery.excludennum.addAll(getExcludeNnums());
		OpGroup opGroup = getOpGroupProvider().getByEqName(nameOpGroup);
		moveCritery.arrOpgroup.add(opGroup);
		List<Move> moves = getMoveProvider().getByCritery(moveCritery);
		return moves;
	}

	public IMoveProvider getMoveProvider() {
		return moveProvider;
	}

	public void setMoveProvider(IMoveProvider moveProvider) {
		this.moveProvider = moveProvider;
	}

	public IOpGroupProvider getOpGroupProvider() {
		return opGroupProvider;
	}

	public void setOpGroupProvider(IOpGroupProvider opGroupProvider) {
		this.opGroupProvider = opGroupProvider;
	}

	public String getNameReal() {
		return nameReal;
	}

	public void setNameReal(String nameReal) {
		this.nameReal = nameReal;
	}

	public ExcelReport getExcelReport() {
		return excelReport;
	}

	public void setExcelReport(ExcelReport excelReport) {
		this.excelReport = excelReport;
	}

	public String getRealTemplReport() {
		return realTemplReport;
	}

	public void setRealTemplReport(String realTemplReport) {
		this.realTemplReport = realTemplReport;
	}

	public List<Integer> getExcludeNnums() {
		return excludeNnums;
	}

	public void setExcludeNnums(List<Integer> excludeNnums) {
		this.excludeNnums = excludeNnums;
	}

}
