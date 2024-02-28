package ru.perm.v.el59.office.dao.impl.plan;

import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.plan.UserZP;

public interface ICalculatorForBonusK {
	/**
	 * Расчет зп. Расчитанная ЗП помещается в соответствующее поле userZP и возвращается
	 * @param userZP - сотрудник
	 * @param plan - план
	 * @param move - движение (МЕНЯЕТСЯ ПОЛЕ ДЛЯ РАСЧЕТА ЗП!!!)
	 * @return
	 * @throws Exception 
	 */
	public UserZP calcZP(UserZP userZP,Move move) throws Exception;
	
	/**
	 * Добавить продажу
	 * @param u
	 * @param m
	 * @return
	 */
	public UserZP addSumInOut(UserZP u, Move m);
}
