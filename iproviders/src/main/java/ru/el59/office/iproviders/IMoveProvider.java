package ru.el59.office.iproviders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.el59.dao.IGenericDao;
import ru.el59.office.critery.MoveCritery;
import ru.el59.office.db.Move;
import ru.el59.office.db.MoveSummary;
import ru.el59.office.db.PriceType;
import ru.el59.office.db.Shop;
import ru.el59.office.db.StringSummaInOut;
import ru.el59.office.db.dto.MoveOperationSum;

public interface IMoveProvider extends IGenericDao<Move, Long> {
   ArrayList<MoveSummary> getSumByCritery(MoveCritery var1);

   List<StringSummaInOut> getSumByOperation(MoveCritery var1);

   List<MoveOperationSum> getSumByOperationWithRest(MoveCritery var1);

   void deleteMoveShopInPeriod(String var1, Date var2, Date var3) throws Exception;

   List<Move> getControlPrice(MoveCritery var1, PriceType var2);

   ArrayList<Shop> getShopAndParther(Shop var1);

   List<Move> getResultCompetition(Date var1, Date var2);

   BigDecimal getCenaInOnDate(Integer var1, Date var2);
}
