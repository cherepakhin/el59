package ru.el59.office.iproviders.shopmodel;

import ru.el59.dao.IGenericDao;
import ru.el59.office.shopmodel.BonusCardMove;
import ru.perm.v.el59.dto.BonusCardMoveDTO;

public interface IBonusCardMoveProvider extends IGenericDao<BonusCardMove, Long> {
   BonusCardMove getByDTO(BonusCardMoveDTO var1, String var2) throws Exception;
}
