package ru.perm.v.el59.office.dao.impl.plan;

import ru.perm.v.el59.office.db.BonusK;
import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.plan.UserZP;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <b>Калькулятор для мелкой бытовки (МБТ, Основной товар 2-5)</b>
 * 
 * <pre>
 *  Мотивация при продаже МБТ
 * 1. 	МБТ на списки не делится.
 * 2. 	При продаже МБТ с наценкой выше установленного минимума, 
 * 	применяется повышающий коэффициент отчисления в ПРЭФ - 5%. 
 * 	В остальных случаях – стандартный 2%.
 * 3. 	При продаже МБТ по Прайсу интернет-магазина, наценка будет считаться, 
 * 	как разница между продажной ценой и ценой поставщика.
 * </pre>
 * 
 * @author vasi
 * 
 */
public class CalculatorZPMBT extends ACalculatorZPFofBonusK implements
		ICalculatorForBonusK {

	@Override
	public UserZP calcZP(UserZP userZP, Move move) throws Exception {
		GroupTovar grp = move.getTovar().getGroup();
		BonusK bonusk = move.getTovar().getGroup().getBonusk();
		BigDecimal percent = new BigDecimal("0.00");
		if (move.getSummain().compareTo(BigDecimal.ZERO) == 0) {
			percent = bonusk.getMaxK();
		} else {
			BigDecimal nacenka = move.getSummaout().divide(move.getSummain(),RoundingMode.HALF_UP)
					.subtract(BigDecimal.ONE);
			if (nacenka.compareTo(grp.getMinNacenka()) >= 0) {
				percent = bonusk.getMaxK();
			} else {
				percent = bonusk.getMinK();
			}
		}
		BigDecimal zp = move.getSummaout().multiply(percent)
				.setScale(2, RoundingMode.HALF_UP);
//		if (move.getOperation().getZnak() < 0) {
//			userZP.setSummaMainTovarBonus(userZP.getSummaMainTovarBonus().add(
//					zp));
//			move.setZpMBT(zp);
//		} else {
//			userZP.setSummaMainTovarBonus(userZP.getSummaMainTovarBonus()
//					.subtract(zp));
//			move.setZpMBT(zp.negate());
//		}
		return userZP;
	}

	@Override
	protected UserZP changeSumInOut(UserZP u, Move m, BigDecimal summaIn) {
//		if (m.getOperation().getZnak() < 0) {
//			u.setSummaMainTovar(u.getSummaMainTovar().add(m.getSummaout()));
//		} else {
//			u.setSummaMainTovar(u.getSummaMainTovar().subtract(m.getSummaout()));
//		}
		return u;
	}

}
