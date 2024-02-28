package ru.perm.v.el59.office.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

//import ru.perm.v.el59.office.critery.GroupTCritery;
//import ru.perm.v.el59.office.db.GroupT;
//import ru.perm.v.el59.office.db.Tovar;
//import ru.perm.v.el59.office.db.Var;
//import ru.perm.v.el59.office.iproviders.IGroupTProvider;
//import ru.perm.v.el59.office.iproviders.ITovarProvider;
//import ru.perm.v.el59.office.iproviders.IVarProvider;
import ru.perm.v.el59.dto.office.critery.GroupTCritery;
import ru.el59.office.db.GroupT;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.Var;
import ru.perm.v.el59.dto.office.iproviders.IGroupTProvider;
import ru.perm.v.el59.dto.office.iproviders.ITovarProvider;
import ru.perm.v.el59.dto.office.iproviders.IVarProvider;
import ru.perm.v.el59.office.util.Helper;

public class GroupTProvider extends GenericDaoHibernateImpl<GroupT, String>
		implements IGroupTProvider {

	private static final long serialVersionUID = 2912354696641820594L;
	List<GroupT> list;
	HashMap<String, GroupT> hash = new HashMap<String, GroupT>();
	private IVarProvider varProvider;
	private ITovarProvider tovarProvider;
	private String nameVarDirForPhoto;

	@Override
	public GroupT initialize(String id) {
		GroupT o = (GroupT) getSession().get(GroupT.class, id);
		if(o.getParent()!=null) {
			Hibernate.initialize(o.getParent());
		}
		if(o.getChilds()!=null) {
			Hibernate.initialize(o.getChilds());
		}
		if(o.getRelationTovar()!=null) {
			Hibernate.initialize(o.getRelationTovar());
		}
		return o;
	}

	public GroupTProvider(Class<GroupT> type) {
		super(type);
	}

	public List<GroupT> getByCritery(GroupTCritery critery) {
		Criteria criteryGroup = getSession().createCriteria(GroupT.class);
		Criteria parentGroup = criteryGroup.createCriteria("parent");
		criteryGroup.addOrder(Order.asc("cod"));
		criteryGroup.addOrder(Order.asc("name"));
		if (critery.cod != null)
			criteryGroup.add(Restrictions.eq("cod", critery.cod));
		if (critery.name != null)
			criteryGroup.add(Restrictions.eq("name", critery.name));
		if (critery.parent_cod != null)
			parentGroup.add(Restrictions.eq("cod", critery.parent_cod));
		ArrayList<GroupT> retList = (ArrayList<GroupT>) criteryGroup.list();
		return retList;
	}

	public ArrayList<GroupT> getByEnd(String suffix) {
		getAll();
		ArrayList<GroupT> retlist = new ArrayList<GroupT>();
		for (String key : hash.keySet()) {
			if (key.endsWith(suffix)) {
				if (!key.endsWith("00" + suffix))
					retlist.add(hash.get(key));
			}
		}
		return retlist;
	}

	public List<GroupT> getAll() {
		if (list == null) {
			list = new ArrayList<GroupT>();
			list = (ArrayList) getSession().createCriteria(GroupT.class)
			// .addOrder(Order.asc("parent"))
					.addOrder(Order.asc("name")).list();
		}
		for (int i = 0; i < list.size(); i++) {
			hash.put(list.get(i).getCod(), list.get(i));
		}
		return list;
	}

	public GroupT getByCod(String cod) {
		getAll();
		return hash.get(cod);
	}

	// Заполнение
	// insert into db.groupt_groupt(groupt_cod,childs_cod) select parent_cod,cod
	// from db.groupt where cod is not null and parent_cod is not null
	// delete from db.groupt_groupt

	public GroupT getTree(GroupT parent) {
		// / Формирование через запросы
		// getAll();
		if (parent.getCod().equals("")) {
			parent.setCod("0000000000");
		}
		parent=read(parent.getCod());
		parent = initChilds(parent);
		return parent;
	}

	@Override
	public GroupT initChilds(GroupT parent) {
//		Logger.getLogger(this.getClass()).info("parent-"+parent.getCod());
//		System.out.println("parent-"+parent.getCod());
		Hibernate.initialize(parent.getChilds());
		// Hibernate.initialize(parent.getRelationTovar());
		if (parent.getChilds().size() > 0) {
			for (GroupT g : parent.getChilds()) {
//				System.out.println(g.getCod());
				if(g!=null) {
//					Hibernate.initialize(g.getChilds());
					// Hibernate.initialize(g.getRelationTovar());
					if(g.getCod().equals("0904120000")) {
						System.out.println("0904120000");
					}
/*					GroupT _g = new GroupT();
					_g.setCod(g.getCod());
					_g.setName(g.getName());
					_g.setChilds(g.getChilds());
					_g.setParent(g.getParent());*/
					g.setCurrentParent(parent);
					
					if(g.getChilds()!=null && g.getChilds().size()>0) {
						initChilds(g);
					}
				}
			}
		}
		// System.out.println("end parent-"+parent.getCod());
		return parent;
	}

	private String getNewCod(String s) {
		if (s.endsWith("00")) {
			s = s.substring(0, s.lastIndexOf("00"));
			s = getNewCod(s);
			s = s + "00";
		} else {
			Long cod = Long.valueOf(s);
			cod++;
			s = cod.toString();
		}
		return s;
	}

	private String getBeginCod(String s) {
		if (s.endsWith("00")) {
			s = s.substring(0, s.lastIndexOf("00"));
			s = getBeginCod(s);
			s = s + "00";
		} else {
			s = s + "01";
		}
		return s;
	}

	public String getNewCodChildForParent(GroupT parent) {
		String cod = "";
		String sql = "select max(cod) from GroupT where parent_cod=:parent_cod";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("parent_cod", parent.getCod());
		ArrayList<String> _cod = (ArrayList<String>) q1.list();
		cod = _cod.get(0);
		if (cod == null) {
			cod = getBeginCod(parent.getCod());
			cod = cod.substring(0, cod.lastIndexOf("00"));
		}
		cod = getNewCod(cod);
		if (cod.length() < 10)
			cod = '0' + cod;
		return cod;
	}

	@Override
	public GroupT checkGroup(GroupT g) throws Exception {
		getAll();
		if (!hash.containsKey(g.getCod()) || (hash.get(g.getCod()) == null)) {
			int pos = g.getCod().indexOf("00");
			if (pos > 2) {
				String parentcod = g.getCod();
				parentcod = parentcod.substring(0, pos - 2);
				for (int i = parentcod.length(); i < g.getCod().length(); i++) {
					parentcod = parentcod + "0";
				}
				// System.out.println("Check parent:          ,"+parentcod);
				GroupT parent = new GroupT();
				parent.setCod(parentcod);
				parent = checkGroup(parent);
				g.setParent(parent);
				// System.out.println("create:"+g+" parent:"+g.getParent());
				create(g);
				// hash.put(g.getCod(), g);
				return g;
			} else {
				// create g
				g.setParent(hash.get("0000000000"));
				create(g);
				// System.out.println("create:"+g+" parent:"+g.getParent());
				hash.put(g.getCod(), g);
				return g;
			}
		} else {
			return hash.get(g.getCod());
		}
	}

	@Override
	public String create(GroupT o) throws Exception {
		list = null;
		String cod = super.create(o);
		o.getParent().addChild(o);
		update(o.getParent());
		return cod;
	}

	@Override
	public void delete(GroupT o) throws Exception {
		/*
		 * if(o.getParent().getChilds().contains(o)) {
		 * o.getParent().getChilds().remove(o); update(o.getParent()); } String
		 * sql = "update GroupTovar set groupT=null where groupT=:groupT"; Query
		 * q1 = getSession().createQuery(sql); q1.setParameter("groupT", o);
		 * q1.executeUpdate();
		 */
		GroupT parent = o.getParent();
		parent.getChilds().remove(o);
		update(parent);
		List<GroupT> childs = o.getChilds();
		while (childs.size() > 0) {
			delete(childs.get(0));
		}
		super.delete(o);
	}

	@Override
	public void update(GroupT o) throws Exception {
		list = null;
		super.update(o);
	}

	public String getDirForPhoto() {
		Var var = (Var) getVarProvider().getByEqName(getNameVarDirForPhoto());
		return var.getVal();
	}

	@Override
	public byte[] getImage(String path) throws IOException {
		return Helper.getImageData(path, getDirForPhoto());
	}

	public IVarProvider getVarProvider() {
		return varProvider;
	}

	public void setVarProvider(IVarProvider varProvider) {
		this.varProvider = varProvider;
	}

	public String getNameVarDirForPhoto() {
		return nameVarDirForPhoto;
	}

	public void setNameVarDirForPhoto(String nameVarDirForPhoto) {
		this.nameVarDirForPhoto = nameVarDirForPhoto;
	}

	@Override
	public void setPhoto(GroupT selectedGroup, byte[] data) throws Exception {
		String basepath = getDirForPhoto();
		// сохранение в базе
		selectedGroup.setPathImage(selectedGroup.getName() + ".jpg");
		update(selectedGroup);
		// загрузка файла
		String filename = FilenameUtils.concat(basepath,
				selectedGroup.getPathImage());
		FileUtils.writeByteArrayToFile(new File(filename), data);
	}

	@Override
	public GroupT init(GroupT groupT) {
		Hibernate.initialize(groupT.getChilds());
//		Hibernate.initialize(groupT.getRelationTovar());
		return groupT;
	}

	@Override
	public void removeGroupFromParent(GroupT movedGroupT, GroupT parentGroup)
			throws Exception {
		parentGroup = initialize(parentGroup.getCod());
		parentGroup.getChilds().remove(movedGroupT);
		update(parentGroup);
	}

	@Override
	public GroupT addGroupToParent(GroupT movedGroupT, GroupT parentGroup)
			throws Exception {
		parentGroup = initialize(parentGroup.getCod());
		parentGroup.getChilds().add(movedGroupT);
		update(parentGroup);
		return parentGroup.getChilds().get(parentGroup.getChilds().size() - 1);
	}

	@Override
	public void removeIndexFromParent(int i, GroupT parentGroup)
			throws Exception {
		GroupT sourceParentGroupT = initialize(parentGroup.getCod());
		sourceParentGroupT.getChilds().remove(i);
		update(sourceParentGroupT);
	}

	@Override
	public void removeIndexFromParent(int i, String cod) throws Exception {
		GroupT parentGroup = initialize(cod);
		// Hibernate.initialize(sourceParentGroupT);
		parentGroup.getChilds().remove(i);
		update(parentGroup);
	}

	/*
	 * @Override public void addGroupToParent(GroupT movedGroupT, GroupT
	 * parentGroup, Integer index) { parentGroup=init(parentGroup);
	 * parentGroup.getChilds().add(index, movedGroupT); update(parentGroup); }
	 */
	@Override
	public void moveDown(GroupT movedGroup, GroupT parent) throws Exception {
		moveTo(movedGroup, parent, 1);
	}

	private void moveTo(GroupT movedGroup, GroupT parent, Integer step)
			throws Exception {
		parent = initialize(parent.getCod());
		int index = parent.getChilds().indexOf(movedGroup);
		movedGroup = parent.getChilds().get(index);
		GroupT nextGroup = parent.getChilds().get(index + step);
		parent.getChilds().set(index + step, movedGroup);
		parent.getChilds().set(index, nextGroup);
		update(parent);
	}

	@Override
	public void moveUp(GroupT movedGroup, GroupT parent) throws Exception {
		moveTo(movedGroup, parent, -1);
	}

	@Override
	public void addRelationTovar(String cod, Integer nnum) throws Exception {
		GroupT g = read(cod);
		Tovar t = getTovarProvider().read(nnum);
		g.getRelationTovar().add(t);
		update(g);
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	/*
	 * @Override public void moveGroupT(GroupT targetParent, GroupT
	 * moveGroupt,boolean copy) { GroupT sourceParent =
	 * moveGroupt.getCurrentParent(); if(!copy) {
	 * sourceParent.getChilds().remove(moveGroupt); }
	 * targetParent.getChilds().add(moveGroupt); if(copy) {
	 * cutedGroupT.setParent(selectedGroup); } }
	 */
}
