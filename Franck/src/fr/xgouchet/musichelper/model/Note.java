package fr.xgouchet.musichelper.model;

public class Note {

	/**
	 * Constructs a Whole Natural Middle C (4th octave)
	 */
	public Note() {
		this(0);
	}

	/**
	 * Constructs a note by the given pitch and accidental alteration, at the
	 * 4th octave and duration of a whole
	 * 
	 * @param pitch
	 * @param accidental
	 * @param octave
	 */
	public Note(final Pitch pitch, final Accidental accidental, final int octave) {
		this(pitch, accidental, 4, 1);
	}

	/**
	 * Constructs a note by the given pitch and accidental alteration, at the
	 * given octave and duration as fraction of a whole
	 * 
	 * @param pitch
	 * @param accidental
	 * @param octave
	 * @param fraction
	 */
	public Note(final Pitch pitch, final Accidental accidental,
			final int octave, final int fraction) {
		mFraction = fraction;
		mPitch = pitch;
		mAccidental = accidental;
		mOctave = octave;
	}

	/**
	 * Constructs a note by the number of halftones from a natural C on the 4th
	 * octave
	 * 
	 * @param halfTones
	 */
	public Note(final int halfTones) {
		this(halfTones, 4, 1);
	}

	/**
	 * Constructs a note by the number of halftones from a natural C, at the
	 * given octave
	 * 
	 * @param halfTones
	 * @param octave
	 */
	public Note(final int halfTones, final int octave) {
		this(halfTones, octave, 1);
	}

	/**
	 * Constructs a note by the number of halftones from a natural C, at the
	 * given octave and duration as fraction of a whole
	 * 
	 * @param halfTones
	 * @param octave
	 * @param fraction
	 */
	public Note(final int halfTones, final int octave, final int fraction) {
		mFraction = fraction;

		int hto = ((halfTones - (halfTones % 12)) / 12);
		int ht = halfTones - (hto * 12);

		mPitch = Pitch.nearestPitch(ht);
		mAccidental = Accidental.fromHalfTones(ht - mPitch.halfTones());

		mOctave = octave + hto;
	}

	/**
	 * @return the number of half tone from a Natural C to this note
	 */
	public int halfTones() {
		return mPitch.halfTones() + mAccidental.halfTones()
				+ ((mOctave - 4) * 12);
	}

	/**
	 * @return the major 2nd note based on this dominant
	 */
	public Note secondMajor() {
		return new Note(halfTones() + 2, mOctave, mFraction);
	}

	/**
	 * @return the minor 2nd note based on this dominant
	 */
	public Note secondMinor() {
		return new Note(halfTones() + 1, mOctave, mFraction);
	}

	/**
	 * @return the major 3rd note based on this dominant
	 */
	public Note thirdMajor() {
		return new Note(halfTones() + 4, mOctave, mFraction);
	}

	/**
	 * @return the minor 3rd note based on this dominant
	 */
	public Note thirdMinor() {
		return new Note(halfTones() + 3, mOctave, mFraction);
	}

	/**
	 * @return the 4th note based on this dominant
	 */
	public Note fourth() {
		return new Note(halfTones() + 5, mOctave, mFraction);
	}

	/**
	 * @return the perfect 5th note based on this dominant
	 */
	public Note fifth() {
		return new Note(halfTones() + 7, mOctave, mFraction);
	}

	/**
	 * @return the 6th note based on this dominant
	 */
	public Note sixth() {
		return new Note(halfTones() + 9, mOctave, mFraction);
	}

	/**
	 * @return the major 7th note based on this dominant
	 */
	public Note seventhMajor() {
		return new Note(halfTones() + 11, mOctave, mFraction);
	}

	/**
	 * @return the minor 7th note based on this dominant
	 */
	public Note seventhMinor() {
		return new Note(halfTones() + 10, mOctave, mFraction);
	}

	/**
	 * @return the augmented note based on this dominant
	 */
	public Note augmented() {
		switch (mAccidental) {
		case sharp:
			return new Note(mPitch, Accidental.doubleSharp, mOctave, mFraction);
		case natural:
			return new Note(mPitch, Accidental.sharp, mOctave, mFraction);
		case flat:
			return new Note(mPitch, Accidental.natural, mOctave, mFraction);
		case doubleFlat:
			return new Note(mPitch, Accidental.flat, mOctave, mFraction);
		case doubleSharp:
		default:
			return new Note(halfTones() + 1, mOctave, mFraction);
		}
	}

	/**
	 * @return the diminished note based on this dominant
	 */
	public Note diminished() {
		switch (mAccidental) {
		case doubleSharp:
			return new Note(mPitch, Accidental.sharp, mOctave, mFraction);
		case sharp:
			return new Note(mPitch, Accidental.natural, mOctave, mFraction);
		case natural:
			return new Note(mPitch, Accidental.flat, mOctave, mFraction);
		case flat:
			return new Note(mPitch, Accidental.doubleFlat, mOctave, mFraction);
		case doubleFlat:
		default:
			return new Note(halfTones() - 1, mOctave, mFraction);
		}
	}

	/**
	 * @return the staff position (assuming middle C is 0, middle D is 1, middle
	 *         E 2 and so forth)
	 */
	public int offsetFromC4() {
		int octaveDiff = mOctave - 4;

		int pitchDiff = mPitch.ordinal();
		return pitchDiff + (octaveDiff * 7);
	}

	/**
	 * @return if the current note should display an alteration
	 */
	public boolean isAltered() {
		return (!mAccidental.equals(Accidental.natural));
	}

	/**
	 * @return the accidental
	 */
	public Accidental getAccidental() {
		return mAccidental;
	}

	/**
	 * @return the fraction
	 */
	public int getFraction() {
		return mFraction;
	}

	/**
	 * @return the octave
	 */
	public int getOctave() {
		return mOctave;
	}

	/**
	 * @return the pitch
	 */
	public Pitch getPitch() {
		return mPitch;
	}

	/**
	 * @param accidental
	 *            the accidental to set
	 */
	public void setAccidental(final Accidental accidental) {
		mAccidental = accidental;
	}

	/**
	 * @param fraction
	 *            the fraction to set
	 */
	public void setFraction(final int fraction) {
		mFraction = fraction;
	}

	/**
	 * @param octave
	 *            the octave to set
	 */
	public void setOctave(final int octave) {
		mOctave = octave;
	}

	/**
	 * @param pitch
	 *            the pitch to set
	 */
	public void setPitch(final Pitch pitch) {
		mPitch = pitch;
	}

	/**
	 * @return a user friendly display string
	 */
	public String toDisplayString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mPitch.name());
		builder.append(mAccidental.toString());
		return builder.toString();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mPitch.name());
		builder.append(mAccidental.name());
		builder.append("(1/");
		builder.append(mFraction);
		builder.append(')');
		return builder.toString();
	}

	/**
	 * An implementation of equals which is lenient according to
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		// check for self-comparison
		if (this == other) {
			return true;
		}

		// check for type
		if (!(other instanceof Note)) {
			return false;
		}

		// check fields
		Note note = (Note) other;
		return (note.mFraction == mFraction)
				&& (note.halfTones() == halfTones());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 11;
		hash = (41 * hash) + halfTones();
		hash = (41 * hash) + mFraction;
		return hash;
	}

	private Pitch mPitch;
	private Accidental mAccidental;
	private int mOctave;

	// whole is 1, half is 2, quarter is 4 etc...
	private int mFraction;

}
