package fr.xgouchet.franck.core.model;

import java.util.Locale;

/**
 * An immutable class representing a Note, including a Pitch (eg : C, G, A), an
 * accidental alteration (eg : flat, sharp, ...) and an octave
 * 
 * @author Xavier Gouchet
 */
public class Note {

	/** the base pitch of the note (eg : C, E, B, ...) */
	private final Pitch mPitch;
	/** the accidental (eg: flat, sharp, natural, ...) */
	private final Accidental mAccidental;
	/** the octave this note is played on (default is 4<sup>th</sup> ) */
	private final int mOctave;

	/**
	 * Constructs a Middle C (natural C on the 4<sup>th</sup> octave)
	 */
	public Note() {
		this(0);
	}

	/**
	 * Constructs a note by the number of half tones from a natural C on the
	 * 4<sup>th</sup> octave (ie : middle C)
	 * 
	 * @param halfTones
	 *            the number of half tones from a middle C
	 */
	public Note(final int halfTones) {
		this(halfTones, 4);
	}

	/**
	 * Constructs a note by the number of half tones from a natural C, at the
	 * given octave
	 * 
	 * @param halfTones
	 *            the number of half tones from a natural C
	 * @param octave
	 */
	public Note(final int halfTones, final int octave) {
		int octaveOffset = ((halfTones - (halfTones % 12)) / 12);
		int st = halfTones - (octaveOffset * 12);

		while (st < 0) {
			st += 12;
			octaveOffset -= 1;
		}

		switch (st) {
		case 0:
		case 1:
			mPitch = Pitch.C;
			break;
		case 2:
			mPitch = Pitch.D;
			break;
		case 3:
		case 4:
			mPitch = Pitch.E;
			break;
		case 5:
		case 6:
			mPitch = Pitch.F;
			break;
		case 7:
		case 8:
			mPitch = Pitch.G;
			break;
		case 9:
			mPitch = Pitch.A;
			break;
		case 10:
		case 11:
			mPitch = Pitch.B;
			break;
		default:
			throw new IllegalStateException();
		}

		mAccidental = Accidental.fromHalfTones(st - mPitch.getHalfTones());

		mOctave = octave + octaveOffset;
	}

	/**
	 * Constructs a note by the given pitch and accidental alteration, at the
	 * 4<sup>th</sup> octave
	 * 
	 * @param pitch
	 * @param accidental
	 */
	public Note(final Pitch pitch, final Accidental accidental) {
		this(pitch, accidental, 4);
	}

	/**
	 * Constructs a note by the given pitch and accidental alteration, at the
	 * given octave
	 * 
	 * @param pitch
	 * @param accidental
	 * @param octave
	 */
	public Note(final Pitch pitch, final Accidental accidental, final int octave) {
		mPitch = pitch;
		mAccidental = accidental;
		mOctave = octave;
	}

	/**
	 * @return a new Note with a simplified representation of the current note
	 *         (eg : E# -> F, Cbb -> Bb)
	 */
	public Note simplify() {
		switch (mAccidental) {
		case doubleFlat:
		case doubleSharp:
			return new Note(getHalfTones());
		default:
			break;
		}

		switch (mPitch) {
		case E:
		case B:
		case C:
		case F:
			return new Note(getHalfTones());
		default:
			break;
		}

		return this;
	}

	/**
	 * @return the pitch
	 */
	public Pitch getPitch() {
		return mPitch;
	}

	/**
	 * @return the accidental
	 */
	public Accidental getAccidental() {
		return mAccidental;
	}

	/**
	 * @return the octave
	 */
	public int getOctave() {
		return mOctave;
	}

	/**
	 * @return the number of half tones from a Middle C to this note
	 */
	public int getHalfTones() {
		int halfTones = 0;

		// half tones from natural pitch to this note
		halfTones += mAccidental.getHalfTones();

		// half tones from natural C to natural pitch
		halfTones += mPitch.getHalfTones();

		// half tones from middle C to natural C in same octave
		halfTones += (mOctave - 4) * 12;

		return halfTones;
	}

	/**
	 * @return if the current note should display an alteration
	 */
	public boolean isAltered() {
		return (!mAccidental.equals(Accidental.natural));
	}

	/**
	 * Verify that the given note is equivalent to this one (ie : they both have
	 * the same pitch, even on different octaves).
	 * 
	 * For example checks that a B# on the 2nd octave is equivalent to a middle
	 * C.
	 * 
	 * @param other
	 *            the note to check
	 * @return if the given note is equivalent
	 */
	public boolean isEquivalent(final Note other) {
		if (other == null) {
			return false;
		} else {
			int diff = getHalfTones() - other.getHalfTones();
			return (diff % 12) == 0;
		}
	}

	/**
	 * @param notation
	 *            the notation system to use
	 * @return the note name in the given notation
	 */
	public String getNoteName(boolean neoLatinNames, boolean showNatural) {
		return String.format("%s%s", mPitch.getPitchName(neoLatinNames),
				mAccidental.getSymbol(showNatural));
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%s %s (%d)", mPitch.toString(),
				mAccidental.toString(), mOctave);
	}

	@Override
	public boolean equals(final Object other) {
		// check for self-comparison
		if (this == other) {
			return true;
		}

		// check for type / null
		if (!(other instanceof Note)) {
			return false;
		}

		// check fields
		Note that = (Note) other;

		return (this.getHalfTones() == that.getHalfTones());
	}

	@Override
	public int hashCode() {
		return getHalfTones();
	}
}
