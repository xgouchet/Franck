package fr.xgouchet.musichelper.constraints;

import java.util.ArrayList;

import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.core.Domain;
import JaCoP.core.IntDomain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.ValueEnumeration;
import JaCoP.core.Var;

/**
 * Constraints |X - Y| &lt; C
 *
 * Domain consistency is used.
 *
 * @author Xavier Gouchet
 * @author Krzysztof Kuchcinski and Radoslaw Szymanek
 * @version 3.1
 */
public class AbsXminYltC extends PrimitiveConstraint {

	static int idNumber = 1;

	public int c;

	public IntVar x;

	public IntVar y;

	/**
	 * It specifies the arguments required to be saved by an XML format as well
	 * as the constructor being called to recreate an object from an XML format.
	 */
	public static String[] xmlAttributes = { "x", "y", "c" };

	/**
	 * It constructs the constraint |X - Y| &lt; C
	 *
	 * @param x
	 *            variable x.
	 * @param y
	 *            variable y.
	 * @param c
	 *            constant c.
	 */
	public AbsXminYltC(final IntVar x, final IntVar y, final int c) {

		assert (x != null) : "Variable x is null";
		assert (y != null) : "Variable y is null";
		assert (c > 0) : "Constant c " + c + " is not in a valid range";

		numberId = idNumber++;
		numberArgs = 1;
		this.x = x;
		this.y = y;
		this.c = c;

	}

	@Override
	public ArrayList<Var> arguments() {

		ArrayList<Var> variables = new ArrayList<Var>(1);
		variables.add(x);

		return variables;
	}

	@Override
	public void consistency(final Store store) {
		// TODO
	}

	@Override
	public int getNestedPruningEvent(final Var var, final boolean mode) {

		// If satisfied function mode
		if (mode) {
			if (consistencyPruningEvents != null) {
				Integer possibleEvent = consistencyPruningEvents.get(var);
				if (possibleEvent != null) {
					return possibleEvent;
				}
			}

			return IntDomain.ANY;

		}
		// If notSatisfied function mode
		else {
			if (notConsistencyPruningEvents != null) {
				Integer possibleEvent = notConsistencyPruningEvents.get(var);
				if (possibleEvent != null) {
					return possibleEvent;
				}
			}
			return IntDomain.ANY;

		}
	}

	@Override
	public int getConsistencyPruningEvent(final Var var) {

		// If consistency function mode
		if (consistencyPruningEvents != null) {
			Integer possibleEvent = consistencyPruningEvents.get(var);
			if (possibleEvent != null) {
				return possibleEvent;
			}
		}

		return Domain.NONE;

	}

	@Override
	public int getNotConsistencyPruningEvent(final Var var) {

		// If notConsistency function mode
		if (notConsistencyPruningEvents != null) {
			Integer possibleEvent = notConsistencyPruningEvents.get(var);
			if (possibleEvent != null) {
				return possibleEvent;
			}
		}
		return Domain.NONE;

	}

	@Override
	public String id() {
		if (id != null) {
			return id;
		} else {
			return this.getClass().getSimpleName() + numberId;
		}
	}

	@Override
	public void impose(final Store store) {
		x.putModelConstraint(this, getConsistencyPruningEvent(x));
		store.addChanged(this);
		store.countConstraint();
	}

	@Override
	public void notConsistency(final Store store) {
		// TODO
	}

	@Override
	public boolean notSatisfied() {
		ValueEnumeration xEnum, yEnum;

		int xValue, yValue;

		xEnum = x.domain.valueEnumeration();
		while (xEnum.hasMoreElements()) {
			xValue = xEnum.nextElement();

			yEnum = y.domain.valueEnumeration();

			while (yEnum.hasMoreElements()) {
				yValue = yEnum.nextElement();

				if (Math.abs(xValue - yValue) < c) {
					return false;
				}
			}

		}

		return true;
	}

	@Override
	public void removeConstraint() {
		x.removeConstraint(this);
	}

	@Override
	public boolean satisfied() {
		ValueEnumeration xEnum, yEnum;

		int xValue, yValue;

		xEnum = x.domain.valueEnumeration();
		while (xEnum.hasMoreElements()) {
			xValue = xEnum.nextElement();

			yEnum = y.domain.valueEnumeration();

			while (yEnum.hasMoreElements()) {
				yValue = yEnum.nextElement();

				if (Math.abs(xValue - yValue) < c) {
					return true;
				}
			}

		}

		return false;
	}

	@Override
	public String toString() {
		return id() + " : AbsXminYltC(" + x + ", " + y + ", " + c + " )";
	}

	@Override
	public void increaseWeight() {
		if (increaseWeight) {
			x.weight++;
		}
	}

	/**
	 * It returns the constant to which a given variable should be equal to.
	 *
	 * @return the constant to which the variable should be equal to.
	 */
	public int getC() {
		return c;
	}

}
