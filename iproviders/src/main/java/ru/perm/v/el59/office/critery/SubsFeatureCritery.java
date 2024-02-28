package ru.perm.v.el59.office.critery;

import java.io.Serializable;

import ru.el59.office.db.web.TypeProperty;
import ru.perm.v.el59.dto.dao.CommonCritery;

public class SubsFeatureCritery extends CommonCritery implements Serializable {
   private static final long serialVersionUID = 8043937284901463628L;
   public TypeProperty typeProperty;
   public String seekPhrase;
}
