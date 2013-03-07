package fr.xgouchet.musichelper.model;

import java.util.Arrays;

/**
 * A {@link Tuning} describes how a String instrument (let's say, a guitar) is
 * tuned. It containes an array of {@link Note} objects, corresponding to each
 * string.
 */
public class Tuning {

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

	/**
	 * @return the tuning
	 */
	public Note[] getTuning() {
		return mTuning;
	}

	/**
	 * @param string
	 *            the string number
	 * @return the note on the empty n<sup>th</sup> string
	 */
	public Note getNote(final int string) {
		return mTuning[string];
	}

	/**
	 * @return the stringsCount
	 */
	public int getStringsCount() {
		return mStringsCount;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Tuning [");
		for (Note note : mTuning) {
			builder.append(note.toFullDisplayString());
			builder.append(" | ");
		}

		builder.setLength(builder.length() - 3);
		builder.append("]");

		return builder.toString();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		// check for self-comparison
		if (this == other) {
			return true;
		}

		// check for type
		if (!(other instanceof Tuning)) {
			return false;
		}
		Tuning tuning = (Tuning) other;

		// check fields
		return Arrays.equals(mTuning, tuning.getTuning());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return mTuning.hashCode();
	}

	private final Note[] mTuning;
	private final int mStringsCount;
}
