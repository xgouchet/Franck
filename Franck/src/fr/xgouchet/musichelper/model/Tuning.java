package fr.xgouchet.musichelper.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Tuning {

	public static final int MAX_FRET = 20;

	/**
	 * A combo (for lack of a better name) is a pair of int, describing a string
	 * and fret to produce a Note
	 */
	public class Combo {

		/**
		 * @param string
		 *            the string this combo is on
		 * @param fret
		 *            the fret to press
		 * @param note
		 *            the exact note produced
		 */
		public Combo(final int string, final int fret, final Note note) {
			mString = string;
			mFret = fret;
			mNote = note;
		}

		/**
		 * @return
		 */
		public int getFret() {
			return mFret;
		}

		/**
		 * @return the string
		 */
		public int getString() {
			return mString;
		}

		/**
		 * 
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(mNote.toDisplayString());
			builder.append(" : Str ");
			builder.append(mString);
			builder.append(", Fret ");
			builder.append(mFret);

			return builder.toString();
		}

		private final int mString, mFret;
		private final Note mNote;
	}

	/**
	 * @return a standard guitar tuning (E-A-d-g-b-e')
	 */
	public static Tuning standardGuitarTuning() {
		Note[] notes = new Note[6];

		notes[0] = new Note(Pitch.E, Accidental.natural, 2);
		notes[1] = new Note(Pitch.A, Accidental.natural, 2);
		notes[2] = new Note(Pitch.D, Accidental.natural, 3);
		notes[3] = new Note(Pitch.G, Accidental.natural, 3);
		notes[4] = new Note(Pitch.B, Accidental.natural, 3);
		notes[5] = new Note(Pitch.E, Accidental.natural, 4);

		return new Tuning(notes);
	}

	/**
	 * @param notes
	 *            the notes of the given tuning
	 */
	public Tuning(final Note... notes) {
		if (notes == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		mTuning = Arrays.copyOf(notes, notes.length);
		mStringsCount = mTuning.length;
	}

	public int[] getFrets(final Chord chord) {
		// get dominant on all strings
		List<Combo> dominantCombos = listCombos(chord.getDominant());

		return null;
	}

	private List<Combo> listCombos(final Note note) {
		List<Combo> combos = new LinkedList<Tuning.Combo>();

		Note fretNote, baseNote;
		// search the given note on all strings
		for (int string = 0; string < mStringsCount; string++) {
			baseNote = mTuning[string];

			// search the given note on all frets
			for (int fret = 0; fret < MAX_FRET; ++fret) {
				fretNote = new Note(baseNote.getHalfTones() + fret);
				if (fretNote.isEquivalent(note)) {
					combos.add(new Combo(string, fret, fretNote));
				}
			}
		}

		return combos;
	}

	private final Note[] mTuning;
	private final int mStringsCount;
}
