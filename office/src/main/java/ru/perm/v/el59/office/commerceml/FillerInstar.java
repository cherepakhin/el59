package ru.perm.v.el59.office.commerceml;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.commerceml.model.Good;
import ru.perm.v.el59.office.commerceml.model.Property;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Photo;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;

import java.io.File;

public class FillerInstar implements IFillerFeatures {

	private final static String OSNOVNOE = "{ОСНОВНОЕ}";
	private final static String SCLAD = "{СКЛАД}";
	private ITovarInfoProvider tovarInfoProvider;

	@Override
	public TovarInfo fillInfo(TovarInfo tovarInfo, Good good) {
		String nameGroupMain = "Основные характеристики";
		String nameGroupAdd = "Дополнительно";
		for (Property property : good.getListProperty()) {
			String nameFeature = property.getName();
			String valNode = property.getVal();
			if (nameFeature.contains(SCLAD)) {
				continue;
			}
			Feature f = new Feature();
			if (nameFeature.contains(OSNOVNOE)) {
				f.setGrp(nameGroupMain);
				nameFeature = nameFeature.replace(OSNOVNOE, "");
			} else {
				f.setGrp(nameGroupAdd);
			}
			f.setName(nameFeature);
			f.setVal(valNode);
			tovarInfo.addFeature(f);
			Logger.getLogger(this.getClass().getName()).info(
					String.format("Для товара добавлена %s хар-ка  %s:%s:%s",
							good.getName(), f.getGrp(), nameFeature, valNode));
		}

		return tovarInfo;
	}

	@Override
	public TovarInfo fillPhoto(String pathRootDirCommerceML,
			TovarInfo tovarInfo, Good good) throws Exception {
		if (good.getPicture() == null || good.getPicture().isEmpty()) {
			return tovarInfo;
		}
		Photo photo = new Photo();
		String filename = loadPhoto(pathRootDirCommerceML, tovarInfo,
				good.getPicture());
		photo.setPath(filename.replace(getBaseDirForPhoto(), ""));
		String info = "";
		photo.setName(info);
		tovarInfo.addPhoto(photo);
		return tovarInfo;
	}

	private String loadPhoto(String pathRootDirCommerceML, TovarInfo tovarInfo,
			String pathPicture) throws Exception {
		String ret = null;
		int pos = pathPicture.lastIndexOf(".");
		String ext = pathPicture.substring(pos);
		ret = getFullFileName(getDirForPhoto(tovarInfo.getNnum()),
				tovarInfo.getNnum() + ext);
		Logger.getLogger(this.getClass().getName()).info(
				String.format("%s %s", pathPicture, ret));
		FileUtils.copyFile(
				new File(getFullFileName(pathRootDirCommerceML, pathPicture)),
				new File(ret));
		return ret;
	}

	protected static String getFullFileName(String outputDir, String filename) {
		String fullname = FilenameUtils.concat(outputDir, filename);
		return fullname;
	}

	public String getBaseDirForPhoto() throws Exception {
		return getTovarInfoProvider().getDirForPhoto();
	}

	public String getDirForPhoto(Integer nnum) throws Exception {
		return getTovarInfoProvider().getDirForPhoto(nnum);
	}

	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

}
