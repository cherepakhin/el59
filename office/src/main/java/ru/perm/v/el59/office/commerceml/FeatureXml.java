package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import ru.perm.v.el59.office.db.Feature;

@XStreamAlias("ХарактеристикаТовара")
public class FeatureXml {
	@XStreamAlias("Наименование")
	private String name;
	@XStreamAlias("Значение")
	private String val;
	@XStreamAlias("Индекс")
	private Integer idx;
	@XStreamAlias("Основная")
	private Boolean prmr = false;
	@XStreamAlias("ИндексОсновной")
	private Integer positionPrmry = -1;

	public FeatureXml(Feature f, int position) {
		super();
		if (f == null) {
			System.out.println("Feature=null in FeatureXml");
		}
		name = f.getGrp() + ";" + f.getName();
		idx = position;
		prmr = f.getPrmry();
		val = f.getVal();
//		this.positionPrmry=f.getPositionPrmry();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public Boolean getPrmr() {
		return prmr;
	}

	public void setPrmr(Boolean prmr) {
		this.prmr = prmr;
	}

	public Integer getPositionPrmry() {
		return positionPrmry;
	}

	public void setPositionPrmry(Integer positionPrmry) {
		this.positionPrmry = positionPrmry;
	}
}
