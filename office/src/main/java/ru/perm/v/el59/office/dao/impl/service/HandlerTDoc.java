package ru.perm.v.el59.office.dao.impl.service;

import java.util.ArrayList;
import java.util.List;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.TypeDoc;
import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.ITDoc;
import ru.perm.v.el59.office.db.service.LOPT;
import ru.perm.v.el59.office.db.service.TDoc;
import ru.perm.v.el59.office.iproviders.ITypeDocProvider;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;
import ru.perm.v.el59.office.iproviders.service.ITDocProvider;
import ru.perm.v.el59.ui.AUIBean;

public class HandlerTDoc<T extends AUIBean> implements IHandlerTDoc {
	private ArrayList<RequestItem> listRequestItem;
	private Class<T> type;
	private IGenericDao dao;
	private ITDocProvider tdocProvider;
	private ATDoc content;
	private List<IHandlerTDoc> requestNextTDoc;
	private String typeDocName ="";
	protected ITypeDocProvider typeDocProvider;

	public List<IHandlerTDoc> getRequestNextTDoc() {
		return requestNextTDoc;
	}

	public void setRequestNextTDoc(List<IHandlerTDoc> requestNextTDoc) {
		this.requestNextTDoc = requestNextTDoc;
	}

	public HandlerTDoc(Class<T> type) {
		this.type = type;
//		createContent();
	}

	public ATDoc getContent() {
		return content;
	}

	public void setContent(ATDoc content) {
		this.content = content;
	}

	public ArrayList<RequestItem> getListRequestItem() {
		return listRequestItem;
	}

	public void setListRequestItem(ArrayList<RequestItem> listRequestItem) {
		this.listRequestItem = listRequestItem;
	}

	public IGenericDao getDao() {
		return dao;
	}

	public void setDao(IGenericDao dao) {
		this.dao = dao;
	}

	public ATDoc createContent() {
		try {
			content = (ATDoc) type.newInstance();
			TypeDoc typeDoc = getTypeDocProvider().getByEqName(getTypeDocName());
			content.getTdoc().setTypeDoc(typeDoc);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	public ITDoc loadContent(TDoc tdoc) {
		ITDoc ret = (ITDoc) dao.read(tdoc.getN());
		return ret;
	}

	public void delete(ITDoc _content) throws Exception {
		_content=(ITDoc) dao.read(_content.getN());
		dao.delete(_content);
		getTdocProvider().delete(_content.getTdoc());
	}

	public RequestMessage create(ITDoc _content) throws Exception {
		dao.create(_content);
		return afterCreate();

	}

	public RequestMessage afterCreate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(ITDoc _content) throws Exception {
		dao.update(_content);
	}

	@Override
	public boolean isComplete(ATDoc content) {
		return true;
	}

	@Override
	public RequestMessage tune(ATDoc content, RequestMessage requestMessage)
			throws Exception {
		return null;
	}

	@Override
	public List<IHandlerTDoc> getNextTDoc(LOPT lopt, ATDoc content) {
		return getRequestNextTDoc();
	}

	public String getTypeDocName() {
		return typeDocName;
	}

	public void setTypeDocName(String typeDocName) {
		this.typeDocName = typeDocName;
	}

	public ITypeDocProvider getTypeDocProvider() {
		return typeDocProvider;
	}

	public void setTypeDocProvider(ITypeDocProvider typeDocProvider) {
		this.typeDocProvider = typeDocProvider;
	}

	public ITDocProvider getTdocProvider() {
		return tdocProvider;
	}

	public void setTdocProvider(ITDocProvider tdocProvider) {
		this.tdocProvider = tdocProvider;
	}


}
