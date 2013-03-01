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
		major, minor, augmented, diminished,
		// common 7th chords
		dominant7, major7, minor7, augmented7, diminished7,
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
			case dominant7:
				return "7";
			case major7:
				return "M7";
			case minor7:
				return "m7";
			case diminished7:
				return "dim7";
			case augmented7:
				return "aug7";
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
		case dominant7:
			return buildDominant7thChord(dominant);
		case major7:
			return buildMajor7thChord(dominant);
		case minor7:
			return buildMinor7thChord(dominant);
		case augmented7:
			return buildAugmented7thChord(dominant);
		case diminished7:
			return buildDiminished7thChord(dominant);
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
	public static Chord buildDominant7thChord(final Tone dominant) {
		Tone[] chord = new Tone[4];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth();
		chord[3] = dominant.seventh().diminished();
		return new Chord(dominant, Type.dominant7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major chord corresponidng to the dominant
	 */
	public static Chord buildMajor7thChord(final Tone dominant) {
		Tone[] chord = new Tone[4];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth();
		chord[3] = dominant.seventh();
		return new Chord(dominant, Type.major7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor chord corresponidng to the dominant
	 */
	public static Chord buildMinor7thChord(final Tone dominant) {
		Tone[] chord = new Tone[4];
		chord[0] = dominant;
		chord[1] = dominant.third().diminished();
		chord[2] = dominant.fifth();
		chord[3] = dominant.seventh().diminished();
		return new Chord(dominant, Type.minor7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major augmented chord corresponidng to the dominant
	 */
	public static Chord buildAugmented7thChord(final Tone dominant) {
		Tone[] chord = new Tone[4];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth().augmented();
		chord[3] = dominant.seventh().diminished();
		return new Chord(dominant, Type.augmented7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor diminished chord corresponidng to the dominant
	 */
	public static Chord buildDiminished7thChord(final Tone dominant) {
		Tone[] chord = new Tone[4];
		chord[0] = dominant;
		chord[1] = dominant.third().diminished();
		chord[2] = dominant.fifth().diminished();
		chord[3] = dominant.seventh().diminished().diminished();
		return new Chord(dominant, Type.diminished7, chord);
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
	 * @return a user friendly String (eg: "Am", "E7")
	 */
	public String toPrettyString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mDominant.toPrettyString());
		builder.append(mType.asSuffix());
		return builder.toString();
	}

	/**
	 * Describes this chord (eg: "Am [A - C - E]", "E7 [E - G# - B - D]"
	 * 
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
