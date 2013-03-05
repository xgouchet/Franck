package fr.xgouchet.musichelper.model;

import java.util.Arrays;

public class Tuning {

	/**
	 * A combo (for lack of a better name) is a pair of int, describing a string
	 * and fret to produce a Note
	 */
	public class Combo {
		int mString, mFret;
	}

	/**
	 * @return a standard guitar tuning (E-A-d-g-b-e')
	 */
	public static Tuning standardGuitarTuning() {
		Note[] notes = new Note[6];

		notes[0] = new Note(Pitch.E, Accidental.natural, 2);
		notes[0] = new Note(Pitch.A, Accidental.natural, 2);
		notes[0] = new Note(Pitch.D, Accidental.natural, 3);
		notes[0] = new Note(Pitch.G, Accidental.natural, 3);
		notes[0] = new Note(Pitch.B, Accidental.natural, 3);
		notes[0] = new Note(Pitch.E, Accidental.natural, 4);

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
	}

	public int[] getFrets(final Chord chord) {
		mFrets = new int[mTuning.length];

		return mFrets;
	}

	private Note[] mTuning;
	private int[] mFrets;
}
