package fr.xgouchet.musichelper.model;

import java.util.Arrays;

/**
 * TODO write equals / hash
 * 
 * A chord has 3 elements :
 * <ul>
 * <li>a dominant tone;</li>
 * <li>a chord type (major, minor, ...);</li>
 * <li>a corresponding list of notes</li>
 * </ul>
 */
public class Chord {

	/**
	 * 
	 */
	public enum Type {
		// triad chords
		major, minor, diminished, augmented,
		// common 7th chords
		seventh, majorSeventh, minorSeventh, diminishedSeventh, augmentedSeventh,
		// advanced 7th chords
		halfDiminishedSeventh, minorMajorSeventh, augmentedMajorSeventh;

		public String asSuffix() {
			switch (this) {
			case major:
				return "";
			case minor:
				return "m";
			case augmented:
				return "aug";
			case diminished:
				return "dim";
			case seventh:
				return "7";
			default:
				return toString();
			}
		}
	}

	/**
	 * TODO Parses a string with a chord name
	 * 
	 * Here are sample notations based on a C dominant. Replacing C by any note
	 * will work accordingly.
	 * <ul>
	 * <li>Basic chords :
	 * <dl>
	 * <dt>Major</dt>
	 * <dd>C, CM, Cma, Cmaj</dd>
	 * <dt>Minor</dt>
	 * <dd>Cm, Cmi, Cmin</dd>
	 * <dt>Augmented</dt>
	 * <dd>C+, Caug</dd>
	 * <dt>Diminished</dt>
	 * <dd>C°, Cdim</dd>
	 * </dl>
	 * </li>
	 * 
	 * <li>7th chords :
	 * <dl>
	 * <dt>Diminished 7th</dt>
	 * <dd>C°7, Cdim7</dd>
	 * <dt>Half Diminished 7th</dt>
	 * <dd>Cm7b5</dd>
	 * <dt>Minor 7th</dt>
	 * <dd>Cm7, Cmin7</dd>
	 * <dt>Minor Major 7th</dt>
	 * <dd>Cm(M7), Cm maj7</dd>
	 * <dt>Dominant 7th</dt>
	 * <dd>C7</dd>
	 * <dt>Major 7th</dt>
	 * <dd>CM7, Cmaj7</dd>
	 * <dt>Augmented 7th</dt>
	 * <dd>C+7, Caug7, C7+5</dd>
	 * <dt>Augmented Major 7th</dt>
	 * <dd>C+(M7), CM7+5</dd>
	 * </dl>
	 * </li>
	 * </ul>
	 * 
	 * @param value
	 *            any non null String value (see examples above)
	 * @return the parse Chord as a list of Tones
	 */
	public static Chord parse(final String value) {

		return null;
	}

	/**
	 * Given a dominant tone, builds a chord (ie : a list of tones)
	 * corresponding to this chord type
	 */
	public static Chord buildChord(final Type type, final Tone dominant) {
		switch (type) {
		case major:
			return buildMajorChord(dominant);
		case minor:
			return buildMinorChord(dominant);
		case augmented:
			return buildAugmentedChord(dominant);
		case diminished:
			return buildDiminishedChord(dominant);
		case seventh:
			return buildSeventhChord(dominant);
		default:
			throw new IllegalArgumentException("Unable to build "
					+ type.toString() + " chord : method not implemented");
		}
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major chord corresponidng to the dominant
	 */
	public static Chord buildMajorChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth();
		return new Chord(dominant, Type.major, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor chord corresponidng to the dominant
	 */
	public static Chord buildMinorChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third().diminished();
		chord[2] = dominant.fifth();
		return new Chord(dominant, Type.minor, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major augmented chord corresponidng to the dominant
	 */
	public static Chord buildAugmentedChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth().augmented();
		return new Chord(dominant, Type.augmented, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor diminished chord corresponidng to the dominant
	 */
	public static Chord buildDiminishedChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third().diminished();
		chord[2] = dominant.fifth().diminished();
		return new Chord(dominant, Type.diminished, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the dominant seventh chord corresponidng to the dominant
	 */
	public static Chord buildSeventhChord(final Tone dominant) {
		Tone[] chord = new Tone[4];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth();
		chord[3] = dominant.seventh().diminished();
		return new Chord(dominant, Type.seventh, chord);
	}

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
	public Chord(final Tone dominant, final Type type, final Tone[] notes) {
		mDominant = dominant;
		mType = type;
		mNotes = notes;
	}

	/**
	 * @return the notes
	 */
	public Tone[] getNotes() {
		return mNotes;
	}

	/**
	 * @return the dominant
	 */
	public Tone getDominant() {
		return mDominant;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return mType;
	}

	/**
	 * @return if any note in this chord is altered (Flat or Sharp)
	 */
	public boolean hasAlteration() {

		for (Tone note : mNotes) {
			if (note.isAltered()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return a user friendly String
	 */
	public String toPrettyString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mDominant.toPrettyString());
		builder.append(mType.asSuffix());
		return null;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mDominant.toPrettyString());
		builder.append(mType.asSuffix());
		builder.append(" [");
		for (Tone tone : mNotes) {
			builder.append(tone.toPrettyString());
			builder.append(" - ");
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
		hash = (37 * hash) + mDominant.ordinal();
		hash = (37 * hash) + mType.ordinal();
		hash = (37 * hash) + Arrays.hashCode(mNotes);

		return hash;
	}

	private final Tone[] mNotes;
	private final Tone mDominant;
	private final Type mType;
}
