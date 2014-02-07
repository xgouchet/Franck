package fr.xgouchet.music.core.model;

import java.util.Arrays;

import android.text.TextUtils;

/**
 * An immutable class representing a Chord, basically a list of Notes together
 * 
 * @author Xavier Gouchet
 */
public class Chord {

	/**
	 * Utility class used to build a chord
	 * 
	 * TODO maybe something based on the Chord App ?
	 */
	public class Builder {

	}

	/** The name of the chord (can be null) */
	private final String mName;

	/** The list of notes */
	private final Note[] mNotes;

	/**
	 * @param notes
	 *            the notes composing this chord. The root note should be the
	 *            first given
	 */
	public Chord(Note... notes) {
		this(null, notes);
	}

	/**
	 * @param name
	 *            the name of the chord (can be null)
	 * @param notes
	 *            the notes composing this chord. The root note should be the
	 *            first given
	 */
	public Chord(String name, Note... notes) {
		mNotes = notes;
		mName = name;

		if (notes.length == 0) {
			throw new IllegalArgumentException(
					"A chord should have at least one Note in it");
		}
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
	 * @return the name of this chord (can be null)
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @return the notes in this chord
	 */
	public Note[] getNotes() {
		return mNotes;
	}

	/**
	 * Compare this object with another object. Only the notes composing the
	 * chord are checked in this test, the name is irrelevant.
	 * 
	 * @see java.lang.Object#equals(Object)
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
		return Arrays.equals(chord.mNotes, mNotes);
	}

	/**
	 * Computes a hash code based on the notes composing the chord. The name is
	 * note used to stay coherent with the equals method.
	 * 
	 * @see java.lang.Object#hashCode()
	 * @see Chord#equals(Object)
	 */
	@Override
	public int hashCode() {
		int hash = 13;
		hash = (37 * hash) + Arrays.hashCode(mNotes);
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (!TextUtils.isEmpty(mName)) {
			builder.append(mName);
			builder.append(' ');
		}

		builder.append(Arrays.toString(mNotes));

		return builder.toString();
	}
}
