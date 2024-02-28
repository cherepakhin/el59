package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.HistoryTovar;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.dto.ChangeGroup;
import ru.perm.v.el59.office.iproviders.IHistoryTovarProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HistoryTovarProvider extends
        GenericDaoHibernateImpl<HistoryTovar, Long> implements
        IHistoryTovarProvider {

    private ITovarProvider tovarProvider;

    public HistoryTovarProvider(Class<HistoryTovar> type) {
        super(type);
    }

    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }

    @Override
    public List<ChangeGroup> getChangeGroup(Date fromdate, Date todate) {
        ArrayList<Integer> usedNnums = new ArrayList<Integer>();
        HashMap<Integer, ChangeGroup> hash = new HashMap<Integer, ChangeGroup>();
        ArrayList<ChangeGroup> ret = new ArrayList<ChangeGroup>();
        Criteria criteria = getSession().createCriteria(HistoryTovar.class);
        criteria.add(Restrictions.ge("ddatechange", fromdate));
        criteria.add(Restrictions.le("ddatechange", todate));
        criteria.addOrder(Order.asc("ddatechange"));
        List<HistoryTovar> list = criteria.list();
        if (list.size() > 0) {
            for (HistoryTovar historyTovar : list) {
                Tovar tovar = historyTovar.getTovar();
                if (!tovar.getGroup().equals(historyTovar.getGroup())) {
                    GroupTovar oldGroup = historyTovar.getGroup();
                    GroupTovar newGroup = tovar.getGroup();
                    if (!usedNnums.contains(tovar.getNnum())) {
                        if (!oldGroup.equals(newGroup)) {
                            ChangeGroup changeGroup = new ChangeGroup();
                            changeGroup.tovar = tovar;
                            changeGroup.oldBestGroup = oldGroup;
                            changeGroup.newBestGroup = newGroup;
                            ret.add(changeGroup);
                            hash.put(tovar.getNnum(), changeGroup);
                            usedNnums.add(tovar.getNnum());
                        }
                    } else {
                        ChangeGroup prevChangeGroup = hash.get(tovar.getNnum());
                        prevChangeGroup.newBestGroup = oldGroup;
                        if (prevChangeGroup.oldBestGroup.equals(oldGroup)) {
                            ret.remove(prevChangeGroup);
                        }
                        ChangeGroup changeGroup = new ChangeGroup();
                        changeGroup.tovar = tovar;
                        changeGroup.oldBestGroup = oldGroup;
                        changeGroup.newBestGroup = newGroup;
                        ret.add(changeGroup);
                        hash.put(tovar.getNnum(), changeGroup);
                    }
                }
            }
        }
        return ret;
    }

}
