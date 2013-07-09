package fr.xgouchet.musichelper.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musichelper.solver.AsyncGuitarSolver;

/**
 * @author Xavier Gouchet
 */
@Deprecated
public class GuitarChord {
	public static final String STR_VAR = "str";

	// Classical guitar has 19 frets, electric can go up to 27
	public static final int MAX_FRET = 19;
	public static final int MAX_FRET_RANGE = 4;

	public static final int X = -1;

	public GuitarChord(final Chord chord, final Tuning tuning) {
		mAllCombos = new LinkedList<Set<Combo>>();
		mTuning = tuning;

		mChord = chord;
		generateGuitarChords();
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
	protected void generateGuitarChords() {

		AsyncGuitarSolver solver = new AsyncGuitarSolver();
		solver.setTuning(mTuning);
		solver.setChord(mChord);

		solver.execute();
	}

	protected final List<Set<Combo>> mAllCombos;
	protected final Tuning mTuning;
	protected final Chord mChord;
}
