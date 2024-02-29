package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.db.GroupT;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Группа")
public class GroupTXml {
	@XStreamAlias("Ид")
	private String cod;
	@XStreamAlias("Наименование")
	private String name = "";
	/*
	 * @XStreamAlias("Родитель") private GroupTXml parent;
	 */
	@XStreamAlias("Родитель")
	private String codParent = "";
	@XStreamAlias("Группы")
	private List<GroupTXml> childs = new ArrayList<GroupTXml>();
	@XStreamAlias("СопутствующиеТовары")
	private ListNnum relationTovarNnum;
	// private List<String> relationTovarNnum=new ArrayList<String>();
	@XStreamAlias("Картинка")
	private String pathImage = "";
	@XStreamAlias("Индекc")
	private Integer ord;

	public GroupTXml(GroupT g) {
		super();
		Logger.getLogger(this.getClass().getName()).info("Create GroupTXml:"+g.getCod());
		cod = g.getCod();
		relationTovarNnum = new ListNnum();
/*		for (Tovar t : g.getRelationTovar()) {
			relationTovarNnum.getList().add(t.getNnum());
		}
*/		name = g.getName();
		if (g.getParent() != null) {
			codParent = g.getParent().getCod();
		}
		pathImage = g.getPathImage();
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public List<GroupTXml> getChilds() {
		return childs;
	}

	public void setChilds(List<GroupTXml> childs) {
		this.childs = childs;
	}

	public ListNnum getRelationTovarNnum() {
		return relationTovarNnum;
	}

	public void setRelationTovarNnum(ListNnum relationTovarNnum) {
		this.relationTovarNnum = relationTovarNnum;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	public String getCodParent() {
		return codParent;
	}

	public void setCodParent(String codParent) {
		this.codParent = codParent;
	}

}
