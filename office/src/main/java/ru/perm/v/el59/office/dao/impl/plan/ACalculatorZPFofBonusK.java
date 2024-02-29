package ru.perm.v.el59.office.dao.impl.plan;

import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.plan.UserZP;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class ACalculatorZPFofBonusK implements ICalculatorForBonusK {
	
	/**
	 * Получение вх.суммы. Может отлтчаться от себес-ти. 
	 * По приказу №2 от 1.04.2016 расчет ЗП ведется от цены последнего прихода
	 * @param move
	 * @return
	 */
	protected BigDecimal getSummaIn(Move move) {
		BigDecimal summaIn = move.getSummain();
//		if (move.getCenaInOnDate() != null
//				&& move.getCenaInOnDate().compareTo(BigDecimal.ZERO) != 0) {
//			summaIn = move.getCenaInOnDate().multiply(move.getQty())
//					.setScale(2, RoundingMode.HALF_UP);
//		}
		return summaIn.setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public UserZP addSumInOut(UserZP u,Move m) {
//		BigDecimal znak = new BigDecimal(-m.getOperation().getZnak());
//		// Изменение сумм по типу продаж
//		BigDecimal summaIn = getSummaIn(m);
//		u=changeSumInOut(u,m,summaIn);
//		// Изменение сумм "Всего продаж"
//		u.setSummaIn(u.getSummaIn().add(m.getSummain().multiply(znak)).setScale(2, RoundingMode.HALF_UP));
//		u.setSummaOut(u.getSummaOut().add(m.getSummaout().multiply(znak)).setScale(2, RoundingMode.HALF_UP));
		return u;
	}

	/**
	 * Корректировка сумм в зависимости от зарплатной группы товара
	 * @param u
	 * @param m
	 * @param summaIn
	 * @return
	 */
	protected abstract UserZP changeSumInOut(UserZP u, Move m, BigDecimal summaIn);
}
