package fr.xgouchet.musichelper.model;

import java.util.ArrayList;

import JaCoP.constraints.Or;
import JaCoP.constraints.PrimitiveConstraint;
import JaCoP.constraints.XeqC;
import JaCoP.core.Domain;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.search.DepthFirstSearch;
import JaCoP.search.IndomainMin;
import JaCoP.search.InputOrderSelect;
import JaCoP.search.Search;
import JaCoP.search.SelectChoicePoint;
import android.util.Log;
import fr.xgouchet.musichelper.constraints.AbsXminYltC;
import fr.xgouchet.musichelper.constraints.XmodCeqC;

public class JacopGuitarChord extends GuitarChord {

	public JacopGuitarChord(final Chord chord, final Tuning tuning) {
		super(chord, tuning);
	}

	/**
	 * Generate all probable guitar chords
	 *
	 * @param chord
	 * @return
	 */
	@Override
	protected void generateGuitarChords(final Chord chord) {
		int size = mTuning.getStringsCount();

		// define Finite Domain store
		Store store = new Store();

		// define Domain variables
		IntVar[] vars = new IntVar[size];
		for (int i = 0; i < size; i++) {
			vars[i] = new IntVar(store, STR_VAR + i, -1, MAX_FRET);
		}

		// Add constraints on notes
		generateChordConstraints(store, vars, chord);
		generateHandConstraints(store, vars);

		// check store consistency
		if (!store.consistency()) {
			throw new IllegalStateException(
					"Current settings generate inconsistencies");
		}

		// Search for solutions
		Search<IntVar> search = new DepthFirstSearch<IntVar>();
		SelectChoicePoint<IntVar> select = new InputOrderSelect<IntVar>(store,
				vars, new IndomainMin<IntVar>());
		// search.setSolutionListener(new PrintOutListener<IntVar>());
		// search.getSolutionListener().searchAll(true);
		// search.getSolutionListener().recordSolutions(true);

		if (!search.labeling(store, select)) {
			throw new IllegalStateException("Unable to find guitar chord");
		}

		Domain[] solution = search.getSolution();
		Log.i("GuitarChord", solution.toString());
	}

	/**
	 * Generates constraints for each string to produce a note in the given
	 * chord, or not be played
	 *
	 * @param store
	 * @param vars
	 * @param chord
	 */
	private void generateChordConstraints(final Store store,
			final IntVar[] vars, final Chord chord) {

		IntVar var;

		// we need at least one string for each note
		for (Note note : chord.getNotes()) {
			if (note.equals(chord.getDominant())) {
				addDominantConstraints(store, vars, note);
			} else {
				addNoteConstraints(store, vars, note);
			}
		}
	}

	/**
	 *
	 * @param store
	 * @param vars
	 * @param note
	 */
	private void addDominantConstraints(final Store store, final IntVar[] vars,
			final Note note) {
		ArrayList<PrimitiveConstraint> consts = new ArrayList<PrimitiveConstraint>();
		int base, tones;
		tones = note.getHalfTones();

		for (int i = 0; i < vars.length; ++i) {
			base = mTuning.getNote(i).getHalfTones();
			consts.add(new XmodCeqC(vars[i], 12, tones - base));

		}

		store.impose(new Or(consts));
	}

	/**
	 *
	 * @param store
	 * @param vars
	 * @param note
	 */
	private void addNoteConstraints(final Store store, final IntVar[] vars,
			final Note note) {
		ArrayList<PrimitiveConstraint> consts = new ArrayList<PrimitiveConstraint>();
		int base, tones;
		tones = note.getHalfTones();

		for (int i = 0; i < vars.length; ++i) {
			base = mTuning.getNote(i).getHalfTones();
			consts.add(new XmodCeqC(vars[i], 12, tones - base));
		}

		store.impose(new Or(consts));
	}

	/**
	 *
	 * @param store
	 * @param vars
	 */
	private void generateHandConstraints(final Store store, final IntVar[] vars) {
		int i, j;
		ArrayList<PrimitiveConstraint> consts = new ArrayList<PrimitiveConstraint>();

		// each combo must be in the same fret range
		for (i = 0; i < vars.length; ++i) {
			consts.clear();

			consts.add(new XeqC(vars[i], X));
			consts.add(new XeqC(vars[i], 0));

			for (j = i + 1; j < vars.length; ++j) {
				consts.add(new AbsXminYltC(vars[i], vars[j], MAX_FRET_RANGE));
			}

			store.impose(new Or(consts));
		}
	}
}
