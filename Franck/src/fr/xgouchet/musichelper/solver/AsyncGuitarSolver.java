package fr.xgouchet.musichelper.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musicgeneration.model.Note;
import fr.xgouchet.musichelper.model.Combo;
import fr.xgouchet.musichelper.model.Tuning;

/**
 * Guitar Solver is a CSP solver to generate a chord
 * 
 * 
 * Reminder, nothing to do with anything but ...
 * 
 * F = (1 / (2L)) √ (T / µ)
 * 
 * where L = String length, T = Tension µ = radius * density
 * 
 * @author Xavier Gouchet
 * 
 */
public class AsyncGuitarSolver extends AsyncTask<Void, Void, Void> {

	private final List<List<Combo>> mAllCombos;
	private Tuning mTuning;
	private Note[] mNotes;
	private StringRange[] mStrings;

	private final Comparator<List<Combo>> mComparator = new Comparator<List<Combo>>() {

		@Override
		public int compare(final List<Combo> lhs, final List<Combo> rhs) {
			double diff = getAveragePosition(lhs) - getAveragePosition(rhs);

			if (diff < 0) {
				return -1;
			} else if (diff > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	/**
	 * A Solver listener with callback on solver events
	 */
	public static interface SolverListener {
		void onSolved(List<List<Combo>> allCombos);
	}

	private SolverListener mListener;

	/**
	 * 
	 */
	public AsyncGuitarSolver() {
		mAllCombos = new ArrayList<List<Combo>>();
	}

	/**
	 * @param listener
	 *            the solver listener
	 */
	public void setSolverListener(final SolverListener listener) {
		mListener = listener;
	}

	/**
	 * @param tuning
	 *            the guitar tuning
	 */
	public void setTuning(final Tuning tuning) {
		mTuning = tuning;
	}

	/**
	 * @param chord
	 *            the chord to solve
	 */
	public void setChord(final Chord chord) {
		mNotes = chord.getNotes();
	}

	/**
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(final Void... params) {
		solve();
		return null;
	}

	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(final Void result) {
		super.onPostExecute(result);
		printSolvedChords();
		if (mListener != null) {
			mListener.onSolved(mAllCombos);
		}
	}

	/**
	 * @return if at least one solution was found for the given chord and tuning
	 */
	private void solve() {
		mAllCombos.clear();

		long start = System.currentTimeMillis();

		generateStrings();

		generateConstraints();

		generateCombos();

		Collections.sort(mAllCombos, mComparator);

		Log.i("Franck", "Found " + mAllCombos.size() + " solutions in "
				+ (System.currentTimeMillis() - start) + " ms");
	}

	/**
	 * Generates the base string ranges for this solver
	 */
	private void generateStrings() {
		int count = mTuning.getStringsCount();
		mStrings = new StringRange[count];

		for (int i = 0; i < count; ++i) {
			mStrings[i] = new StringRange(mTuning.getNote(i), i);
		}
	}

	/**
	 * Generates the basic constraints for this solver
	 */
	private void generateConstraints() {
		for (StringRange string : mStrings) {
			string.limitNotes(mNotes);
		}
	}

	/**
	 * Generate all valid combos
	 */
	private void generateCombos() {
		List<Combo> set = new ArrayList<Combo>();
		generateCombos(set, 0);
	}

	/**
	 * Generates all valid combos
	 * 
	 * @param combos
	 *            the first elements of the combo
	 * @param level
	 *            the level to try out
	 */
	private boolean generateCombos(final List<Combo> list, final int level) {
		if (level == mStrings.length) {
			return checkValidCombosAndAddToList(list);
		} else {
			boolean hasValid = false;
			StringRange string = mStrings[level];
			for (Combo combo : string) {

				if (!checkXsCount(list)) {
					continue;
				}

				// Prevent last 3 strings from being X'ed
				if ((combo.getFret() < 0)
						&& (combo.getString() >= (mStrings.length - 3))) {
					continue;
				}

				list.add(combo);
				hasValid |= checkAndGenerateCombos(list, level + 1);
				list.remove(combo);
			}
			return hasValid;
		}
	}

	/**
	 * @param list
	 *            the list to test
	 * @return if this chord is valid
	 */
	private boolean checkXsCount(final List<Combo> list) {
		int count = 0;
		for (Combo combo : list) {
			if (combo.getFret() < 0) {
				count++;
			}
		}
		return (count < (mStrings.length / 2));
	}

	/**
	 * Checks that the list is still valid before adding combos to it
	 * 
	 * @param list
	 *            the list
	 * @param level
	 *            the level
	 */
	private boolean checkAndGenerateCombos(final List<Combo> list,
			final int level) {
		if (!checkFirstStringIsDominant(list)) {
			return false;
		}

		if (!checkHumanHand(list)) {
			return false;
		}

		generateCombos(list, level);
		return true;
	}

	/**
	 * checks if a set of combos is valid
	 * 
	 * @param list
	 *            the list to test
	 * @return if this chord is valid
	 */
	private boolean checkValidCombosAndAddToList(final List<Combo> list) {

		if (!checkCombosSpansFullChord(list)) {
			return false;
		}

		if (!checkUnnecessaryX(list)) {
			Log.i("GUITAR", "Xs in combo... ");
			return false;
		}

		mAllCombos.add(new ArrayList<Combo>(list));
		return true;
	}

	/**
	 * Check that the chord does not copy another valid chord with just an X
	 * somewhere. This test can be quite long though
	 * 
	 * @param list
	 *            the list to test
	 * @return if this chord is valid
	 */
	private boolean checkUnnecessaryX(final List<Combo> list) {

		int count = list.size();
		boolean hasXs = false;

		previousloop: for (List<Combo> previous : mAllCombos) {

			hasXs = false;
			comboloop: for (int c = 0; c < count; ++c) {
				if (list.get(c).getFret() == previous.get(c).getFret()) {
					continue comboloop;
				} else if (list.get(c).getFret() == -1) {
					hasXs = true;
				} else {
					// different frets, then it must be valid!
					continue previousloop;
				}
			}

			if (hasXs) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check that the lowest note is the dominant
	 * 
	 * @param list
	 *            the list to test
	 * @return if this chord is valid
	 */
	private boolean checkFirstStringIsDominant(final List<Combo> list) {
		for (Combo combo : list) {
			if (combo.getFret() < 0) {
				continue;
			} else if (combo.getNote().isEquivalent(mNotes[0])) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks that a human hand can play this chord
	 * 
	 * @param list
	 *            the list to test
	 * @return if this chord is valid
	 */
	private boolean checkHumanHand(final List<Combo> list) {
		int min, max, fret;
		boolean allowBarred = true;
		min = StringRange.MAX_FRET + 1;
		max = -1;

		for (Combo combo : list) {
			fret = combo.getFret();
			if (fret < 0) {
				continue;
			} else if (fret == 0) {
				allowBarred = false;
				continue;
			}

			if (fret < min) {
				min = fret;
			}
			if (fret > max) {
				max = fret;
			}
		}

		// Check finger span
		if ((max - min) >= 4) {
			return false;
		}

		// check number of fingers needed
		int fingers = allowBarred ? 1 : 0;
		for (Combo combo : list) {
			fret = combo.getFret();
			if (fret <= 0) {
				continue;
			}
			if (allowBarred && (fret == min)) {
				continue;
			}
			if (allowBarred && (combo.getString() == 0)) {
				return false;
			}
			fingers++;
		}

		return (fingers <= 4);
	}

	/**
	 * Checks that a given combos spans over the whole chord, and that the
	 * dominant is really dominant
	 * 
	 * @param list
	 *            the list to test
	 * @return if this chord is valid
	 */
	private boolean checkCombosSpansFullChord(final List<Combo> list) {
		int[] notes = new int[mNotes.length];
		int max = 0;
		Arrays.fill(notes, 0);

		for (Combo combo : list) {
			for (int i = 0; i < mNotes.length; ++i) {
				if (mNotes[i].isEquivalent(combo.getNote())) {
					notes[i]++;
					if (notes[i] > max) {
						max = notes[i];
					}
					break;
				}
			}
		}

		// dominant is not well represented
		if (notes[0] < max) {
			return false;
		} else {
			for (int n : notes) {
				if (n == 0) {
					return false;
				}
			}
		}

		// average on each note
		double average = ((mStrings.length * 1.0) / (mNotes.length * 1.0f));
		for (int note : notes) {
			if ((note < (average - 1)) || (note > (average + 1))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Prints the solved chords to the log cat
	 */
	private void printSolvedChords() {

		StringBuilder builder = new StringBuilder();
		for (List<Combo> list : mAllCombos) {
			builder.append('-');
			for (Combo combo : list) {
				builder.append(combo.getFretString());
				builder.append('-');
			}

			Log.i("Franck", builder.toString());
			builder.setLength(0);
		}
	}

	/**
	 * @param list
	 *            the list of combos
	 * @return the average fret position in the combo
	 */
	private double getAveragePosition(final List<Combo> list) {
		double sum;
		int count;

		sum = count = 0;

		for (Combo combo : list) {
			if (combo.getFret() >= 0) {
				sum += combo.getFret();
				count++;
			}
		}

		if (sum == 0) {
			sum = 1;
		}

		return sum / count;
	}
}
