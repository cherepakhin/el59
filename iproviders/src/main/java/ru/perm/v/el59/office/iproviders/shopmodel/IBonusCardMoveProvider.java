package ru.perm.v.el59.office.iproviders.shopmodel;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.dto.BonusCardMoveDTO;
import ru.perm.v.el59.office.shopmodel.BonusCardMove;

public interface IBonusCardMoveProvider extends IGenericDao<BonusCardMove, Long> {
   BonusCardMove getByDTO(BonusCardMoveDTO var1, String var2) throws Exception;
}
