package fr.xgouchet.musichelper.model;

/**
 * <p>
 * An immutable class representing a Note, including a Pitch (ie : the "name" of
 * the note), an accidental alteration (flat, sharp, ...) an octave and a
 * duration.
 * </p>
 *
 * <p>
 * New notes can be constructed as derived from a dominant note, using the
 * {@link #secondMajor()}, {@link #secondMinor()}, {@link #thirdMajor()},
 * {@link #thirdMinor()}, {@link #fourth()}, {@link #fifth()}, {@link #sixth()},
 * {@link #seventhMajor()} and {@link #seventhMinor()} method, as well as the
 * {@link #diminished()} and {@link #augmented()} methods.
 * </p>
 */
public final class Note {

	/**
	 * Parses a note from string
	 *
	 * @param noteName
	 *            the note name (eg : "C#", "Eb", "B♭", "F", "D##", "G♭♭")
	 * @return a Note instance of the given note, at the 4th octave, as a whole
	 */
	public static Note parse(final String noteName) {

		Pitch pitch = Pitch.valueOf(noteName.substring(0, 1));

		int alterations = 0, length = noteName.length();
		char alt;
		for (int i = 1; i < length; ++i) {
			alt = noteName.charAt(i);
			switch (alt) {
			case '#':
				alterations++;
				break;
			case 'b':
			case '♭':
				alterations--;
				break;
			default:
				throw new IllegalArgumentException("Unknown alteration : "
						+ alt);
			}
		}

		return new Note(pitch.halfTones() + alterations);
	}

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
	 */
	public Note(final Pitch pitch, final Accidental accidental) {
		this(pitch, accidental, 4);
	}

	/**
	 * Constructs a note by the given pitch and accidental alteration, at the
	 * given octave and duration of a whole
	 *
	 * @param pitch
	 * @param accidental
	 * @param octave
	 */
	public Note(final Pitch pitch, final Accidental accidental, final int octave) {
		this(pitch, accidental, octave, 1);
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
	 * Constructs a note by the number of halftones from a natural C 4, at the
	 * given duration
	 *
	 * @param halfTones
	 * @param fraction
	 */
	public Note(final int halfTones, final int fraction) {
		this(halfTones, 4, fraction);
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

		while (ht < 0) {
			ht += 12;
			hto -= 1;
		}

		mPitch = Pitch.nearestPitch(ht);
		mAccidental = Accidental.fromHalfTones(ht - mPitch.halfTones());

		mOctave = octave + hto;
	}

	/**
	 * @return the number of half tone from a Natural C4 to this note
	 */
	public int getHalfTones() {
		return mPitch.halfTones() + mAccidental.halfTones()
				+ ((mOctave - 4) * 12);
	}

	/**
	 * @return the major 2nd note based on this dominant
	 */
	public Note secondMajor() {
		return new Note(getHalfTones() + 2, mFraction);
	}

	/**
	 * @return the minor 2nd note based on this dominant
	 */
	public Note secondMinor() {
		return secondMajor().diminished();
	}

	/**
	 * @return the major 3rd note based on this dominant
	 */
	public Note thirdMajor() {
		return new Note(getHalfTones() + 4, mFraction);
	}

	/**
	 * @return the minor 3rd note based on this dominant
	 */
	public Note thirdMinor() {
		return thirdMajor().diminished();
	}

	/**
	 * @return the 4th note based on this dominant
	 */
	public Note fourth() {
		return new Note(getHalfTones() + 5, mFraction);
	}

	/**
	 * @return the perfect 5th note based on this dominant
	 */
	public Note fifth() {
		return new Note(getHalfTones() + 7, mFraction);
	}

	/**
	 * @return the 6th note based on this dominant
	 */
	public Note sixth() {
		return new Note(getHalfTones() + 9, mFraction);
	}

	/**
	 * @return the major 7th note based on this dominant
	 */
	public Note seventhMajor() {
		return new Note(getHalfTones() + 11, mFraction);
	}

	/**
	 * @return the minor 7th note based on this dominant
	 */
	public Note seventhMinor() {
		return seventhMajor().diminished();
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
			return new Note(getHalfTones() + 1, mFraction);
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
			return new Note(getHalfTones() - 1, mFraction);
		}
	}

	/**
	 * @return the same note at a lower octave (eg : C4 -> C3)
	 */
	public Note lowerOctave() {
		return new Note(mPitch, mAccidental, mOctave - 1, mFraction);
	}

	/**
	 * @return the same note at a lower octave (eg : C4 -> C5)
	 */
	public Note higherOctave() {
		return new Note(mPitch, mAccidental, mOctave + 1, mFraction);
	}

	/**
	 * @return a new Note with a simplified representation of the current note
	 *         (eg : E# -> F, Cbb -> Bb)
	 */
	public Note simplify() {
		switch (mAccidental) {
		case doubleFlat:
		case doubleSharp:
			return new Note(getHalfTones(), mFraction);
		default:
			break;
		}

		switch (mPitch) {
		case E:
		case B:
		case C:
		case F:
			return new Note(getHalfTones(), mFraction);
		default:
			break;
		}

		return this;
	}

	/**
	 * @return the staff position (assuming middle C is 0, middle D is 1, middle
	 *         E 2 and so forth)
	 */
	public int getOffsetFromC4() {
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
	 * @return a user friendly display string
	 */
	public String toDisplayString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mPitch.toDisplayString());
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
		builder.append(' ');
		builder.append(mAccidental.name());
		builder.append("(1/");
		builder.append(mFraction);
		builder.append(" - ");
		builder.append(mOctave);
		builder.append(')');
		return builder.toString();
	}

	/**
	 * Test if two notes are equivalent (ie : the same note on different
	 * octaves)
	 *
	 * @param other
	 *            the note to compare to this one
	 * @return if they are equivalent
	 */
	public boolean isEquivalent(final Note other) {
		int diff = getHalfTones() - other.getHalfTones();
		return (diff % 12) == 0;
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
		int ht, oht;
		ht = getHalfTones();
		oht = note.getHalfTones();

		return (note.mFraction == mFraction) && (oht == ht);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 11;
		hash = (41 * hash) + getHalfTones();
		hash = (41 * hash) + mFraction;
		return hash;
	}

	private final Pitch mPitch;
	private final Accidental mAccidental;
	private final int mOctave;

	// whole is 1, half is 2, quarter is 4 etc...
	private final int mFraction;

}
