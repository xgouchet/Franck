package fr.xgouchet.musichelper.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.cp.solver.constraints.integer.ModuloXYC2;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

/**
 * @author Xavier Gouchet
 */
public class GuitarChord {
	public static final String STR_VAR = "str";

	// Classical guitar has 19 frets, electric can go up to 27
	public static final int MAX_FRET = 19;
	public static final int MAX_FRET_RANGE = 4;

	public static final int X = -1;

	public GuitarChord(final Chord chord, final Tuning tuning) {
		mAllCombos = new LinkedList<Set<Combo>>();
		mTuning = tuning;

		mChord = null;

		generateGuitarChords(chord);
	}

	/**
	 * @return where strings should be pressed to produce this chord : 0 means
	 *         an open string, 1 means pressing on the 1st fret, -1 means not
	 *         playing the string (same as X on guitar tabs)
	 */
	public int[] getFingers() {
		return null;
	}

	/**
	 * @param chord
	 */
	protected void generateGuitarChords(final Chord chord) {

		// The Model
		Model model = new CPModel();

		// The variables
		IntegerVariable[] strings = new IntegerVariable[mTuning
				.getStringsCount()];
		for (int i = 0; i < strings.length; i++) {
			strings[i] = Choco.makeIntVar(STR_VAR + i, -1, MAX_FRET);
		}

		// the constraints
		generateChordConstraints(model, strings, chord);

		// solve
		Solver solver = new CPSolver();
		solver.read(model);
		solver.solve();

		for (int i = 0; i < strings.length; i++) {
			System.out.print(solver.getVar(strings[i]).getVal() + " ");
		}
	}

	/**
	 * Generates constraints for each string to produce a note in the given
	 * chord, or not be played
	 * 
	 * @param store
	 * @param vars
	 * @param chord
	 */
	private void generateChordConstraints(final Model model,
			final IntegerVariable[] vars, final Chord chord) {

		List<Constraint> constraints = new LinkedList<Constraint>();
		IntegerVariable var;

		// we need at least one string for each note
		for (Note note : chord.getNotes()) {
			if (note.equals(chord.getDominant())) {
				// addDominantConstraints(store, vars, note);
				addNoteConstraints(model, vars, note);
			} else {
				addNoteConstraints(model, vars, note);
			}
		}
	}

	/**
	 * 
	 * @param model
	 * @param vars
	 * @param note
	 */
	private void addNoteConstraints(final Model model,
			final IntegerVariable[] vars, final Note note) {
		Constraint consts[] = new Constraint[vars.length];
		int base, tones;
		tones = note.getHalfTones();

		for (int i = 0; i < vars.length; ++i) {
			base = mTuning.getNote(i).getHalfTones();
			consts[i] = Choco.mod(vars[i], Choco.constant(tones - base), 12);
		}

		model.addConstraint(Choco.or(consts));
	}

	protected final List<Set<Combo>> mAllCombos;
	protected final Tuning mTuning;
	protected final Chord mChord;
}
