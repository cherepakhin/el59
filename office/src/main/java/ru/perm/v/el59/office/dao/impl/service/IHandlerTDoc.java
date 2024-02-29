package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.db.service.ATDoc;
import ru.perm.v.el59.office.db.service.ITDoc;
import ru.perm.v.el59.office.db.service.LOPT;
import ru.perm.v.el59.office.db.service.TDoc;
import ru.perm.v.el59.office.iproviders.RequestItem;
import ru.perm.v.el59.office.iproviders.RequestMessage;

import java.util.ArrayList;
import java.util.List;

public interface IHandlerTDoc {

	public String getTypeDocName();

	public ATDoc getContent();

	public void setContent(ATDoc content);

	public ArrayList<RequestItem> getListRequestItem();

	public void setListRequestItem(ArrayList<RequestItem> listRequestItem);

	public IGenericDao getDao();

	public void setDao(IGenericDao dao);

	// public ATDoc createContent(TDoc tdoc);
	public ATDoc createContent();

	public ITDoc loadContent(TDoc tdoc);

	public void delete(ITDoc _content) throws Exception;

	public RequestMessage create(ITDoc _content) throws Exception;

	public void update(ITDoc _content) throws Exception;

	public RequestMessage afterCreate();

	public boolean isComplete(ATDoc content);

	public RequestMessage tune(ATDoc content, RequestMessage requestMessage)
			throws Exception;

	public List<IHandlerTDoc> getNextTDoc(LOPT lopt, ATDoc content);
}