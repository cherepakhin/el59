package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.logging.Logger; 
import ru.perm.v.el59.office.db.FeaturePrice;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.util.Helper;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Товар")
public class TovarInfoXml {
	private static final String ZBRAND = "ZBRAND";
	@XStreamAlias("Ид")
	private Integer nnum;
	@XStreamAlias("Наименование")
	private String name;
	@XStreamAlias("КраткоеОписание")
	private String shortinfo = "";
	@XStreamAlias("ТорговаяМарка")
	private String brand = "";
	@XStreamAlias("БазоваяЕдиница")
	private UnitXml unit;
	@XStreamAlias("ХарактеристикиТовара")
	private List<FeatureXml> listFeature;
	@XStreamAlias("Картинки")
	private List<PhotoXml> listPhoto;
	@XStreamAlias("Группы")
	private GroupTovarXml groupTovarXml;
	@XStreamAlias("ДатаИзменения")
	private String dateChanged = "";
	@XStreamAlias("Лидер")
	private String lider = "";

	public Integer getNnum() {
		return nnum;
	}

	public void setNnum(Integer nnum) {
		this.nnum = nnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TovarInfoXml(TovarInfo tovarInfo) {
		super();
		nnum = tovarInfo.getNnum();
		name = tovarInfo.getTovar().getName();
		shortinfo = tovarInfo.getInfo();
		unit = new UnitXml();
		if (tovarInfo.getTovar().getBrand() != null
				&& !tovarInfo.getTovar().getBrand().isEmpty()) {
			brand = tovarInfo.getTovar().getBrand();
		} else {
			for (FeaturePrice f : tovarInfo.getListFeaturePrice()) {
				if (f.getCode().equals(ZBRAND)) {
					brand = f.getVal();
					break;
				}
			}
		}
		listFeature = new ArrayList<FeatureXml>();
		for (int i = 0; i < tovarInfo.getListFeature().size(); i++) {
			if (tovarInfo.getListFeature().get(i) != null) {
				listFeature.add(new FeatureXml(tovarInfo.getListFeature()
						.get(i), i));
			}
		}
		listPhoto = new ArrayList<PhotoXml>();

		for (int i = 0; i < tovarInfo.getListPhoto().size(); i++) {
			if (tovarInfo.getListPhoto().get(i) != null) {
				listPhoto.add(new PhotoXml(tovarInfo.getListPhoto().get(i), i));
			} else {
				Logger.getLogger(this.getClass().getName()).severe(String.format("Null Photo for nnum=%d", tovarInfo.getNnum()));
			}
		}
		groupTovarXml = new GroupTovarXml(tovarInfo.getTovar().getGroup()
				.getGroupT());
		dateChanged = Helper.getDateFornmatter().format(
				tovarInfo.getTovar().getDateChanged());
	}

	public UnitXml getUnit() {
		return unit;
	}

	public void setUnit(UnitXml unit) {
		this.unit = unit;
	}

	public List<FeatureXml> getListFeature() {
		return listFeature;
	}

	public void setListFeature(List<FeatureXml> listFeature) {
		this.listFeature = listFeature;
	}

	public List<PhotoXml> getListPhoto() {
		return listPhoto;
	}

	public void setListPhoto(List<PhotoXml> listPhoto) {
		this.listPhoto = listPhoto;
	}

	public String getShortinfo() {
		return shortinfo;
	}

	public void setShortinfo(String shortinfo) {
		this.shortinfo = shortinfo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public GroupTovarXml getGroupTovarXml() {
		return groupTovarXml;
	}

	public void setGroupTovarXml(GroupTovarXml groupTovarXml) {
		this.groupTovarXml = groupTovarXml;
	}

	public String getDateChanged() {
		return dateChanged;
	}

	public void setDateChanged(String dateChanged) {
		this.dateChanged = dateChanged;
	}

	public String getLider() {
		return lider;
	}

	public void setLider(String lider) {
		this.lider = lider;
	}
}
