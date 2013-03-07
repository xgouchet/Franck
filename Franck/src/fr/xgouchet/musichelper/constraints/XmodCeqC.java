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
 * Constraints X % M = C
 *
 * Domain consistency is used.
 *
 * @author Xavier Gouchet
 * @author Krzysztof Kuchcinski and Radoslaw Szymanek
 * @version 3.1
 */
public class XmodCeqC extends PrimitiveConstraint {

	static int idNumber = 1;

	/**
	 * It specifies the constants to which a specified variable should be equal
	 * to.
	 */
	public int c;

	/**
	 * It specifies the constants used as modulo for the comparison
	 */
	public int m;

	/**
	 * It specifies the variable which is constrained to be equal to the
	 * specified value.
	 */
	public IntVar x;

	/**
	 * It specifies the arguments required to be saved by an XML format as well
	 * as the constructor being called to recreate an object from an XML format.
	 */
	public static String[] xmlAttributes = { "x", "c", "m" };

	/**
	 * It constructs the constraint X % M = C.
	 *
	 * @param x
	 *            variable x.
	 * @param m
	 *            constant modulo m.
	 * @param c
	 *            constant c.
	 */
	public XmodCeqC(final IntVar x, final int m, final int c) {

		assert (x != null) : "Variable x is null";

		numberId = idNumber++;
		numberArgs = 1;
		this.x = x;
		this.c = ((c % m) >= 0) ? (c % m) : (m + (c % m));
		this.m = m;

	}

	@Override
	public ArrayList<Var> arguments() {

		ArrayList<Var> variables = new ArrayList<Var>(1);
		variables.add(x);

		return variables;
	}

	@Override
	public void consistency(final Store store) {

		int i, max, mod;
		max = x.domain.max();
		for (i = x.domain.min(); i < max; ++i) {
			mod = i % m;
			if (mod < 0) {
				mod += m;
			}

			if (mod != c) {
				x.domain.inComplement(store.level, x, i);
			}
		}
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

		int i, max, mod;
		max = x.domain.max();
		for (i = x.domain.min(); i < max; ++i) {
			mod = i % m;
			if (mod < 0) {
				mod += m;
			}

			if (mod == c) {
				x.domain.inComplement(store.level, x, i);
			}
		}

	}

	@Override
	public boolean notSatisfied() {
		ValueEnumeration xEnum = x.domain.valueEnumeration();

		int mod;
		while (xEnum.hasMoreElements()) {
			int value = xEnum.nextElement();

			mod = value % m;
			if (mod < 0) {
				mod += m;
			}

			// if at least one value satisfies xmodMeqC
			// then constraint can not not be satisfied (for now)
			if (mod == c) {
				return false;
			}
		}

		// no mod value in domain, constraint is NOT satisfied
		return true;
	}

	@Override
	public void removeConstraint() {
		x.removeConstraint(this);
	}

	@Override
	public boolean satisfied() {
		ValueEnumeration xEnum = x.domain.valueEnumeration();

		int mod;
		while (xEnum.hasMoreElements()) {
			int value = xEnum.nextElement();

			mod = value % m;
			if (mod < 0) {
				mod += m;
			}

			// if at least one value satisfies xmodMeqC
			// then constraint can not not be satisfied (for now)
			if (mod == c) {
				return true;
			}
		}

		// no mod value in domain, constraint is NOT satisfied
		return false;
	}

	@Override
	public String toString() {
		return id() + " : XmodCeqC(" + x + " % " + m + ", " + c + " )";
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

	/**
	 * It returns the constant to which a given variable should be equal to.
	 *
	 * @return the constant to which the variable should be equal to.
	 */
	public int getM() {
		return m;
	}
}
