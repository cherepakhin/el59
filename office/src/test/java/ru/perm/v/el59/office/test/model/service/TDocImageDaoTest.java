package ru.perm.v.el59.office.test.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import ru.el59.office.db.service.TDoc;
import ru.el59.office.db.service.TDocImage;
import ru.el59.office.db.service.TypeTDocImage;
import ru.el59.office.iproviders.service.ITDocImageProvider;
import ru.el59.office.iproviders.service.ITDocProvider;
import ru.el59.office.iproviders.service.ITypeTDocImageProvider;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TDocImageDaoTest extends DaoTest<TDocImage,Long> {

	@Override
	protected String getNameDao() {
		return "tdocImageDao";
	}

	@Override
	public void read() {
		TDocImage image = getDao().read(new Long(200));
		assertNotNull(image);
	}
	
	@Test
	public void create() {
		ITDocImageProvider tdocImageProvider=(ITDocImageProvider) getDao();
		ITDocProvider tdocProvider = context.getBean(ITDocProvider.class);
		ITypeTDocImageProvider typeTDocImageProvider = context.getBean(ITypeTDocImageProvider.class);
		List<TypeTDocImage> list = typeTDocImageProvider.getAll();
		if(list.size()==0) {
			fail("Не найдены TypeTDocImage");
		}
		
		Long n = tdocProvider.getMax();
		TDoc tdoc = tdocProvider.read(n-1);
		if(tdoc==null) {
			fail("Не найден тестовый tdoc");
		}
		TDocImage tdocImage = new TDocImage();
		tdocImage.setTdoc(tdoc);
		String filename="/home/vasi/temp/00135441.dbf";
		tdocImage.setFilename(filename);
		tdocImage.setName(list.get(0));
		Long tdocImage_n=new Long(-1);
		try {
			byte[] data = FileUtils.readFileToByteArray(new File(filename));
			tdocImage.setBody(data);
			tdocImage_n=tdocImageProvider.create(tdocImage);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Ошибка при сохранении");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ошибка при создании");
		}
		assertTrue(tdocImage_n>0);
		
		try {
			byte[] body = tdocImageProvider.getBody(tdocImage);
			assertTrue(body.length>0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ошибка чтения файла");
		}
		
	}
}
