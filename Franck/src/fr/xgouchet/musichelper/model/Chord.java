package fr.xgouchet.musichelper.model;

public class Chord {

	/**
	 * TODO m7, 7dim, 7aug, halfdim7, minmaj7, maj7, augmaj7
	 */
	public enum Type {
		major, minor, diminished, augmented, seventh
	}

	/**
	 * TODO parse a string with a chord in it like A7, B#dim or Bbm
	 * 
	 * @return
	 */
	public static Tone[] parse() {
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
		}
		return null;
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
