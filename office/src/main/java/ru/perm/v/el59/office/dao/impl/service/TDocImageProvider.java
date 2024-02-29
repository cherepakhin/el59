package ru.perm.v.el59.office.dao.impl.service;

import org.apache.commons.io.FileUtils;
//import java.util.logging.Logger; 
import org.jboss.logging.Logger;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.TDocImage;
import ru.perm.v.el59.office.iproviders.service.ITDocImageProvider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Calendar;

public class TDocImageProvider extends GenericDaoHibernateImpl<TDocImage, Long>
		implements ITDocImageProvider {

	private String dirForDoc="";
	
	public TDocImageProvider(Class<TDocImage> type) {
		super(type);
	}

	@Override
	public byte[] getBody(TDocImage tdocImage) throws Exception {
		byte[] data = FileUtils.readFileToByteArray(getFile(tdocImage));
		return data;
	}

	private static String getFileExtension(String f) {
		String ext = "";
		int i = f.lastIndexOf('.');
		if (i > 0 && i < f.length() - 1) {
			ext = f.substring(i + 1).toLowerCase();
		}
		return ext;
	}
	
	private void saveBody(TDocImage tdocImage) throws Exception {
		if(tdocImage.getBody()!=null) {
			ByteArrayInputStream input = new ByteArrayInputStream(
					tdocImage.getBody());
			try {
				FileUtils.copyInputStreamToFile(input, getFile(tdocImage));
				Logger.getLogger(this.getClass().getName()).info(
						"Сохранение файла " + tdocImage.getFilename());
			} catch (Exception e) {
				Logger.getLogger(this.getClass().getName()).severe(
						"Сохранение файла " + tdocImage.getFilename(), e);
			}
		}
	}
	
	private File getFile(TDocImage tdocImage) {
		String filename = getDirForDoc() + File.separator + tdocImage.getFilename();
		filename=filename.replace("\\", "/");
		return new File(filename);
	}

	public String getDirForDoc() {
		return dirForDoc;
	}

	public void setDirForDoc(String dirForDoc) {
		this.dirForDoc = dirForDoc;
	}

	@Override
	public Long create(TDocImage o) throws Exception {
		String filename = o.getFilename();
		if (o.getBody() != null) {
			int pos = filename.lastIndexOf("\\");
			if (pos < 0) {
				pos = filename.lastIndexOf("/");
			}
			if (pos > 0) {
				filename = filename.substring(pos + 1);
			}
		
			Calendar cal = Calendar.getInstance();
			Integer year = cal.get(Calendar.YEAR);
			Integer month = cal.get(Calendar.MONTH) + 1;
			Integer day = cal.get(Calendar.DAY_OF_MONTH);
			String ext = getFileExtension(filename);
			int ii = filename.lastIndexOf(".");
			String name = "";
			if (ii > 0) {
				name = filename.substring(0, ii);
			}
			Long i = getMax();
			String newname = year.toString() + File.separator
					+ month.toString() + File.separator + day.toString()
					+ File.separator + name + "(" + i.toString() + ")" + "."
					+ ext;
			o.setFilename(newname);
			saveBody(o);
		}
		return super.create(o);
	}
	

}
