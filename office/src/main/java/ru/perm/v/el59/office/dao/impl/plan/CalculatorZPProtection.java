package ru.perm.v.el59.office.dao.impl.plan;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.plan.UserZP;

/**
 * <b>Калькулятор ЗП для карт ЗАЩИТА</b>
 * 
 * <pre>
 *  	Мотивация при продаже программы ЗАЩИТА
 * 	1.	План продаж Программы ЗАЩИТА - 3% от индивидуального объема продаж продавца-консультанта.
 * 	2.	Отчисление в ПРЭФ от объема продаж Программы ЗАЩИТА – 10%
 * 	3.	При выполнении плана продаж Программы ЗАЩИТА от 100% и более 
 * 		коэффициент отчисления в премиальный фонд - 15%.
 * </pre>
 * 
 * @author vasi
 * 
 */
public class CalculatorZPProtection extends ACalculatorZPFofBonusK implements
		ICalculatorForBonusK {

	private BigDecimal bigK = new BigDecimal("0.15");
	private BigDecimal smallK = new BigDecimal("0.10");

	@Override
	public UserZP calcZP(UserZP userZP, Move move) throws Exception {
		BigDecimal saleAll = userZP.getSummaOut();
		BigDecimal saleProtection = userZP.getSummaPDS();

		// Вычисление процента от плана
		BigDecimal planSale = saleAll.multiply(userZP.getPlan().getPlanPercentPDS()).setScale(2,
				RoundingMode.HALF_UP);
		BigDecimal resultK = smallK;
		if (planSale.compareTo(saleProtection) <= 0) {
			resultK = bigK;
		}
		BigDecimal zp = move.getSummaout().multiply(resultK)
				.setScale(2, RoundingMode.HALF_UP);
		if (move.getOperation().getZnak() < 0) {
			userZP.setSummaPDSBonus(userZP.getSummaPDSBonus().add(zp));
			move.setZpPDS(zp);
		} else {
			userZP.setSummaPDSBonus(userZP.getSummaPDSBonus().subtract(zp));
			move.setZpPDS(zp.negate());
		}
		return userZP;
	}

	@Override
	protected UserZP changeSumInOut(UserZP u, Move m, BigDecimal summaIn) {
		if (m.getOperation().getZnak() < 0) {
			u.setSummaPDS(u.getSummaPDS().add(m.getSummaout()));
		} else {
			u.setSummaPDS(u.getSummaPDS().subtract(m.getSummaout()));
		}
		return u;
	}

}
