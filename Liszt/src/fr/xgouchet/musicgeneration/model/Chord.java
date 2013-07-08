package fr.xgouchet.musicgeneration.model;

import java.util.Arrays;

/**
 * TODO write equals / hash
 * 
 * A chord has 3 elements :
 * <ul>
 * <li>a dominant Note;</li>
 * <li>a chord type (major, minor, ...);</li>
 * <li>a corresponding list of notes</li>
 * </ul>
 * 
 * @author Xavier Gouchet
 */
public class Chord {

	private final Note mDominant;
	private final ChordType mType;
	private final Note[] mNotes;

	/**
	 * Builds a Chord object
	 * 
	 * @param dominant
	 *            the dominant tone
	 * @param type
	 *            the chord type
	 * @param notes
	 *            the list of notes in the chord
	 */
	public Chord(final Note dominant, final ChordType type, final Note[] notes) {
		mDominant = dominant;
		mType = type;
		mNotes = notes;
	}

	/**
	 * Simplifies the note used in this chord
	 */
	public void simplify() {
		for (int i = 0; i < mNotes.length; ++i) {
			mNotes[i] = mNotes[i].simplify();
		}
	}

	/**
	 * @return the notes
	 */
	public Note[] getNotes() {
		return mNotes;
	}

	/**
	 * @return the dominant
	 */
	public Note getDominant() {
		return mDominant;
	}

	/**
	 * @return the type
	 */
	public ChordType getType() {
		return mType;
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
		if (!(other instanceof Chord)) {
			return false;
		}

		// check fields
		Chord chord = (Chord) other;
		return chord.mDominant.equals(mDominant) && chord.mType.equals(mType)
				&& Arrays.equals(chord.mNotes, mNotes);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 13;
		hash = (37 * hash) + mType.ordinal();
		hash = (37 * hash) + mDominant.hashCode();
		hash = (37 * hash) + Arrays.hashCode(mNotes);
		return hash;
	}
}
