package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import ru.el59.office.dao.impl.shopmodel.RewardCreditProvider;
import ru.el59.office.db.Shop;
import ru.el59.office.db.UserShop;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.iproviders.IUserShopProvider;
import ru.el59.office.shopmodel.RewardCredit;
import ru.el59.office.shopmodel.RewardCreditXLS;

public class RewardContractTest {

	private String REWARD_FILE = getClass().getResource("/reward-test.xls").getFile();
	private String REWARD_TEMPL_FILE = getClass().getResource("/rewardtempl.xml").getFile();

	@Test
	public void test() throws IOException {
		File file = new File(REWARD_FILE);
		byte[] b = FileUtils.readFileToByteArray(file);
		assertTrue(b.length > 0);
	}

	@Test
	public void readXlsTest() throws IOException, SAXException, InvalidFormatException {
		XLSReader mainReader = ReaderBuilder.buildFromXML(new File(REWARD_TEMPL_FILE));
		Map<String, List<?>> beans = new HashMap<String, List<?>>();
		final List<RewardCreditXLS> listRewardContract = new ArrayList<RewardCreditXLS>();
		beans.put("arr", listRewardContract);
		BufferedInputStream inputXLS = new BufferedInputStream(
				new FileInputStream(REWARD_FILE));
		mainReader.read(inputXLS, beans);
		for (RewardCreditXLS rewardCreditXLS : listRewardContract) {
			System.out.println(rewardCreditXLS);
		}
		assertTrue(listRewardContract.size()==39);
	}
	
	private class RewardCreditProviderForTest extends RewardCreditProvider {

		public RewardCreditProviderForTest(Class<RewardCredit> type) {
			super(type);
		}

		@Override
		public List<RewardCredit> getByCritery(Object critery) {
			return null;
		}

		@Override
		public Long create(RewardCredit o) throws Exception {
			o.setN(new Long(1));
			return new Long(1);
		}

		@Override
		public void update(RewardCredit o) throws Exception {
		}
	}
	
	//1532000000
	@Test
	public void loadXlsFile() throws Exception {
		File file = new File(REWARD_FILE);
		byte[] b = FileUtils.readFileToByteArray(file);
		
		RewardCreditProvider provider = new RewardCreditProviderForTest(RewardCredit.class);
		
		IShopProvider shopProvider = Mockito.mock(IShopProvider.class);
		Shop shop = new Shop();
		shop.setCod("00000");
		Mockito.when(shopProvider.getNullShop()).thenReturn(shop);
		provider.setShopProvider(shopProvider);
		
		IUserShopProvider userShopProvider = Mockito.mock(IUserShopProvider.class);
		UserShop user = new UserShop();
		user.setN(new Long(1));
		Mockito.when(userShopProvider.getNullUserShop()).thenReturn(user);
		provider.setUserShopProvider(userShopProvider);
		
		provider.setFileNameTemplXls(REWARD_TEMPL_FILE);
		List<RewardCredit> list = provider.loadXlsFile(b);
		for (RewardCredit rewardCredit : list) {
			System.out.println(rewardCredit);
			assertTrue(rewardCredit.getShop().compareTo(shop)==0);
			assertTrue(rewardCredit.getUserShop().equals(user));
			assertTrue(rewardCredit.getUserShop().equals(user));
			System.out.println(rewardCredit.getN());
			assertTrue(rewardCredit.getN().equals(new Long(1)));
		}
		assertTrue(list.size()==39);
	}
}
