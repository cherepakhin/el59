package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import ru.el59.dao.IGenericDao;
import ru.el59.office.dao.impl.shopmodel.RewardCreditProvider;
import ru.el59.office.db.Shop;
import ru.el59.office.db.UserShop;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.iproviders.IUserShopProvider;
import ru.el59.office.iproviders.shopmodel.IRewardCreditProvider;
import ru.el59.office.shopmodel.RewardCredit;
import ru.el59.office.shopmodel.RewardCreditXLS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class RewardContractImplTest {

	@Autowired
	protected ApplicationContext context;
	IRewardCreditProvider provider;
	
	@Before 
	public void setUp() {
		provider = context.getBean(IRewardCreditProvider.class);
	}
	
	@Test
	public void create() {
		RewardCredit r = provider.read(new Long(1));
		System.out.println(r.getN());
		assertTrue(r.getN().equals(new Long(1)));
		try {
			provider.update(r);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
