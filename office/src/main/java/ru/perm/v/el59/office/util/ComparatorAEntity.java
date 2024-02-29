package ru.perm.v.el59.office.util;

import ru.perm.v.el59.dao.IEntitySimple;

import java.util.Comparator;


public class ComparatorAEntity implements Comparator<IEntitySimple> {

    @Override
    public int compare(IEntitySimple o1, IEntitySimple o2) {
        return o1.getName().compareTo(o2.getName());
    }

}
