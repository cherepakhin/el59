package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.db.TypeDoc;
import ru.perm.v.el59.office.iproviders.ITypeDocProvider;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TypeDocProvider extends GenericDaoHibernateImpl<TypeDoc, Long>
        implements ITypeDocProvider {

    public TypeDocProvider(Class<TypeDoc> type) {
        super(type);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<TypeDoc> getAll() {
        List<TypeDoc> ret = getByCritery(new CommonCritery(""));
        Collections.sort(ret, new Comparator<TypeDoc>() {
            @Override
            public int compare(TypeDoc lhs, TypeDoc rhs) {
                return lhs.getN() > rhs.getN() ? -1 : (lhs.getN() < rhs.getN()) ? 1 : 0;
            }
        });
        return ret;
    }

}
