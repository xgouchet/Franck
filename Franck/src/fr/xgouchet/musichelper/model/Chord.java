package fr.xgouchet.musichelper.model;

public class Chord {

	/**
	 * TODO m7, 7dim, 7aug, halfdim7, minmaj7, maj7, augmaj7
	 */
	public enum Type {
		// triad chords
		major, minor, diminished, augmented,
		// common 7th chords
		seventh, majorSeventh, minorSeventh, diminishedSeventh, augmentedSeventh,
		// advanced 7th chords
		halfDiminishedSeventh, minorMajorSeventh, augmentedMajorSeventh;
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
	 * <dd>C�, Cdim</dd>
	 * </dl>
	 * </li>
	 * 
	 * <li>7th chords :
	 * <dl>
	 * <dt>Diminished 7th</dt>
	 * <dd>C�7, Cdim7</dd>
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
	public static Tone[] parse(final String value) {

		return null;
	}

	/**
	 * Given a dominant tone, builds a chord (ie : a list of tones)
	 * corresponding to this chord type
	 */
	public static Tone[] buildChord(final Type type, final Tone dominant) {
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
	public static Tone[] buildMajorChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth();
		return chord;
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor chord corresponidng to the dominant
	 */
	public static Tone[] buildMinorChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third().diminished();
		chord[2] = dominant.fifth();
		return chord;
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major augmented chord corresponidng to the dominant
	 */
	public static Tone[] buildAugmentedChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth().augmented();
		return chord;
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor diminished chord corresponidng to the dominant
	 */
	public static Tone[] buildDiminishedChord(final Tone dominant) {
		Tone[] chord = new Tone[3];
		chord[0] = dominant;
		chord[1] = dominant.third().diminished();
		chord[2] = dominant.fifth().diminished();
		return chord;
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the dominant seventh chord corresponidng to the dominant
	 */
	public static Tone[] buildSeventhChord(final Tone dominant) {
		Tone[] chord = new Tone[4];
		chord[0] = dominant;
		chord[1] = dominant.third();
		chord[2] = dominant.fifth();
		chord[3] = dominant.seventh().diminished();
		return chord;
	}
}
