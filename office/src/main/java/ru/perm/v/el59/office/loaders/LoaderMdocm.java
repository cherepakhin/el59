package ru.perm.v.el59.office.loaders;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.critery.TovarCritery;
import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.office.iproviders.*;
import ru.perm.v.el59.office.util.Helper;
import ru.perm.v.el59.office.util.UnZip;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * Загрузчик движений из mdocm.dbf
 * 
 * @author vasi
 * 
 */
public class LoaderMdocm implements ILoaderMdocm {
	private static Logger LOGGER = Logger.getLogger(LoaderMdocm.class);
	private IMoveProvider moveProvider;
	private IOperationProvider operationProvider;
	private ITypeStockProvider typeStockProvider;
	private ITovarProvider tovarProvider;
	private IShopProvider shopProvider;
	private IHistoryTagProvider historyTagProvider;
	private IHistoryPriceProvider historyPriceProvider;
	private Connection connection;
	private String nameReal ="";
	private final static String PREFIX_DBF_PATH = "jdbc:dbf:////";

	@Override
	public String load(String shopcod, byte[] filedata, Integer countday)
			throws IOException, SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, Exception {

		Shop shop = (Shop) getShopProvider().read(shopcod);
		if (shop == null) {
			Logger.getLogger(this.getClass().getName()).info(
					"Нет магазина с кодом " + shopcod);
			return "Нет магазина с кодом " + shopcod;
		}
		Date todate = Helper.getNullHour(new Date());
		Date fromdate = Helper.getNullHour(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromdate);
		cal.roll(Calendar.DAY_OF_YEAR, -countday);
		fromdate = cal.getTime();
		String ret = load(shop, filedata, fromdate, todate);
		return ret;
	}

	/**
	 * Загрузить движения для магазина shop из данных файла filedata(zip c
	 * mdocm.dbf,rbookm.dbf, ragent.dbf) с даты fromdate по дату todate
	 * 
	 * @param shop
	 *            - магазин
	 * @param filedata
	 *            - данные файла zip c mdocm.dbf,rbookm.dbf,ragent.dbf
	 * @param fromdate
	 *            - с даты >=
	 * @param todate
	 *            - по дату <=
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public String load(Shop shop, byte[] filedata, Date fromdate, Date todate)
			throws IOException, SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, Exception {
		try {
			deleteMove(shop, fromdate, todate);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).info(
					"Ошибка при удалении " + e.getMessage());
			throw e;
		}
		List<Move> listMove;
		try {
			listMove = getMoveFromFile(shop, filedata, fromdate, todate);
			createMovies(listMove);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).info(
					"Ошибка чтения dbf ввода-вывода " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).info(
					"Ошибка sql-запроса dbf  " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (InstantiationException e) {
			Logger.getLogger(this.getClass().getName()).info(
					"Ошибка обработки dbf  " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (IllegalAccessException e) {
			Logger.getLogger(this.getClass().getName()).info(
					"Ошибка обработки dbf  " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			Logger.getLogger(this.getClass().getName()).info(
					"Ошибка обработки dbf. Класс не найден. " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return "";
	}

	/**
	 * Вставка движений
	 * 
	 * @param listMove
	 * @throws Exception
	 */
	private void createMovies(List<Move> listMove) throws Exception {
		for (Move move : listMove) {
			// Установка меток ценника, текущей прайсовой цены, категории
			if(move.getOperation().getOpgroup().getName().equals(getNameReal())) {
				HistoryTag tag = getHistoryTagProvider().getByTovarShopDate(move.getTovar(),move.getShop(),move.getDdate());
				if(tag!=null) {
					move.setCategory(tag.getCategory());
					move.setLabel(tag.getLabel());
					move.setComment(TagInfo.getLabelInfo(tag.getLabel()));
				} else {
					LOGGER.info(String.format("Не найден ценник для магазина=%s, товар=%d, дата=%tF", move.getShop().getCod(),move.getTovar().getNnum(),move.getDdate()));
				}
				// Поиск прайса
				HistoryPrice historyPrice = getHistoryPriceProvider().getByTovarShopDate(move.getTovar().getNnum(), move.getShop().getCod(), move.getDdate());
				if(historyPrice!=null) {
					move.setPrice(historyPrice.getNewcena());
					move.setPriceName(historyPrice.getPricetype().getName());
				} else {
					move.setPrice(BigDecimal.ZERO);
					move.setPriceName("");
				}
				// Поиск цены последнего прихода
				BigDecimal cenainOnDate = getMoveProvider().getCenaInOnDate(move.getTovar().getNnum(), move.getDdate());
				if(cenainOnDate.compareTo(BigDecimal.ZERO)==0) {
					cenainOnDate=move.getSummain().divide(move.getQty(),RoundingMode.HALF_DOWN).setScale(2,RoundingMode.HALF_UP);
				}
				move.setCenaInOnDate(cenainOnDate);
			}
			getMoveProvider().create(move);
		}

	}

	/**
	 * Импорт из dbf
	 * 
	 * @param filedata
	 * @param fromdate
	 * @param todate
	 * @return
	 * @throws Exception
	 */
	private List<Move> getMoveFromFile(Shop shop, byte[] filedata,
			Date fromdate, Date todate) throws Exception {
		List<Move> ret = new ArrayList<Move>();
		
		String tmpDir = FileUtils.getTempDirectoryPath();
		tmpDir = tmpDir + File.separator+ shop.getCod();
		File dir = new File(tmpDir);
		if(dir.exists()) {
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error(e);
			}
		}
		File tmpFile = new File("");
		try {
			boolean isCreate = dir.mkdir();
			if(!isCreate) {
				LOGGER.error("Ошибка при создании каталога "+tmpDir);
				throw new Exception("Ошибка при создании каталога "+tmpDir);
			}
			tmpFile = File.createTempFile("out", ".zip",dir);
			FileUtils.writeByteArrayToFile(tmpFile, filedata);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e);
		}

		System.out.println("tmpDid-" + tmpDir);
		LOGGER.info("Temp loading dir" + tmpDir);
		setConnection(tmpDir);
		List<String> listDbf = UnZip.unzipMyZip(tmpFile.getAbsolutePath(),
				tmpDir);
		
		// Map<номер выписки><Номер продавца>
		HashMap<String, String> hashorderuser = getHashOrderUser(tmpDir);
		PreparedStatement pstmt = getConnection()
				.prepareStatement(
						"select date,typestr,grup,nnum,kol,sum,sumout,numdoc,sclad,agentcode,dopcode,codeval,vid,type,codeoper,cena0 from \"mdocm.dbf\" where date >= ? and date <= ? and \"delete\" =false");
		pstmt.setDate(1, new java.sql.Date(fromdate.getTime()));
		pstmt.setDate(2, new java.sql.Date(todate.getTime()));
		ResultSet rs = pstmt.executeQuery();
		Map<String, Operation> hashBestChr = getOperationProvider()
				.getAllAsHashBestChr();
		Map<Long, TypeStock> typestocks = getTypeStockProvider()
				.getAllAsHashBestChr();
		Operation operation = new Operation();
		while (rs.next()) {
			Move move = new Move();
			move.setDdate(rs.getDate(1));
			Integer nnum = rs.getInt(4);
			TovarCritery critery = new TovarCritery();
			critery.nnum.add(nnum);
			critery.withoutDuplicates=false;
			List<Tovar> tovars = getTovarProvider().getByCritery(critery);
			// Ищем товар по н.номеру и типу
			if (tovars.size() > 0) {
				Tovar t = tovars.get(0);
				move.setTovar(t);
				move.setQty(rs.getBigDecimal(5));
				move.setSummain(rs.getBigDecimal(6));

				move.setSummaout(rs.getBigDecimal(7));
				move.setNumdoc(rs.getString(8).trim());
				String _stock = rs.getString(9).substring(3);
				move.setFromstock(typestocks.get(Long.parseLong(_stock)));
				/*
				 * Убрал работу с агентами. Агент - это поставщик или розничный
				 * покупатель move.setAgentcode(agent);
				 * move.setTostock(move.getAgentcode().getN());
				 */
				// Ставит цифру вместо имени
				if (rs.getString(11) == null)
					move.setSeller("-");
				else
					move.setSeller(hashorderuser.get(rs.getString(11).trim()));
				move.setShop(shop);
				move.setVid(rs.getString(13).trim());
				move.setTypeoper(rs.getString(14).trim());
				Integer codeoper = rs.getInt(15);
				move.setCodeoper(codeoper.toString());
				try {
					operation = hashBestChr.get(move.getVid()
							+ move.getTypeoper() + move.getCodeoper());
				} catch (Exception e) {
					Logger.getLogger(this.getClass().getName()).info(
							"Не найдена операция " + move.getVid()
									+ move.getTypeoper() + move.getCodeoper(),
							e);
					operation = hashBestChr.get("-");
				}
				if (operation == null) {
					Logger.getLogger(this.getClass().getName()).info(
							"Не найдена операция " + move.getVid()
									+ move.getTypeoper() + move.getCodeoper(),
							null);
					operation = hashBestChr.get("-");
				}
				move.setOperation(operation);
				ret.add(move);

			} else {
				Logger.getLogger(this.getClass().getName()).error(
						"Не найден товар nnum " + nnum, null);
			}
		}
		rs.close();
		pstmt.close();
		closeConnection();
		for (String string : listDbf) {
			File f = new File(string);
			if (f.exists()) {
				LOGGER.info("Удаление:" + string);
				try {
					FileUtils.forceDelete(f);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		LOGGER.info("Удаление:" + tmpFile.getAbsolutePath());
		try {
			FileUtils.forceDelete(tmpFile);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return ret;
	}

	private void closeConnection() throws SQLException {
		if (connection != null) {
			connection.commit();
			connection.close();
			if (!connection.isClosed()) {
				LOGGER.error("Соединение с dbf не закрылось");
			}
		}
	}

	/**
	 * Удаление движений
	 * 
	 * @param shop
	 * @param fromdate
	 * @param todate
	 * @throws Exception
	 */
	@Override
	public void deleteMove(Shop shop, Date fromdate, Date todate)
			throws Exception {
		getMoveProvider().deleteMoveShopInPeriod(shop.getCod(), fromdate,
				todate);
	}

	private HashMap<String, String> getHashOrderUser(String tmpDir)
			throws Exception {
		HashMap<String, String> hashUser = getHashUser(tmpDir);
		HashMap<String, String> ret = new HashMap<String, String>();
		PreparedStatement pstmt = getConnection().prepareStatement(
				"select dopcode,agentcod from \"rbookm.dbf\"");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			String dopcode = rs.getString(1).trim();
			String user = rs.getString(2).trim();
			ret.put(dopcode, hashUser.get(user));
		}
		rs.close();
		pstmt.close();

		return ret;
	}

	private HashMap<String, String> getHashUser(String tmpDir) throws Exception {
		// Загрузка продавцов
		PreparedStatement pstmt = getConnection().prepareStatement(
				"select code,name from \"ragent.dbf\" ");
		ResultSet rs = pstmt.executeQuery();
		HashMap<String, String> hashUser = new HashMap<String, String>();
		while (rs.next()) {
			String code = rs.getString(1).trim();
			String user = "";
			if (rs.getString(2) != null) {
				user = rs.getString(2).trim();
			}
			hashUser.put(code, user);
		}
		rs.close();
		pstmt.close();
		return hashUser;
	}

	private void setConnection(String tmpDir) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
		connection = DriverManager.getConnection(PREFIX_DBF_PATH + tmpDir
				+ "?charSet=Cp866", "", "");
	}

	private Connection getConnection() throws Exception {
		if (connection == null) {
			throw new Exception("Connection DBF is null");
		}
		return connection;
	}

	public IMoveProvider getMoveProvider() {
		return moveProvider;
	}

	public void setMoveProvider(IMoveProvider moveProvider) {
		this.moveProvider = moveProvider;
	}

	public IOperationProvider getOperationProvider() {
		return operationProvider;
	}

	public void setOperationProvider(IOperationProvider operationProvider) {
		this.operationProvider = operationProvider;
	}

	public ITypeStockProvider getTypeStockProvider() {
		return typeStockProvider;
	}

	public void setTypeStockProvider(ITypeStockProvider typeStockProvider) {
		this.typeStockProvider = typeStockProvider;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public String getNameReal() {
		return nameReal;
	}

	public void setNameReal(String nameReal) {
		this.nameReal = nameReal;
	}

	public IHistoryTagProvider getHistoryTagProvider() {
		return historyTagProvider;
	}

	public void setHistoryTagProvider(IHistoryTagProvider historyTagProvider) {
		this.historyTagProvider = historyTagProvider;
	}

	public IHistoryPriceProvider getHistoryPriceProvider() {
		return historyPriceProvider;
	}

	public void setHistoryPriceProvider(IHistoryPriceProvider historyPriceProvider) {
		this.historyPriceProvider = historyPriceProvider;
	}
}
