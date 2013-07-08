package fr.xgouchet.musicgeneration.model;

/**
 * A utility class used to generate new chords
 * 
 * @author Xavier Gouchet
 */
public class ChordFactory {

	/**
	 * Given a dominant tone, builds a chord (ie : a list of tones)
	 * corresponding to this chord type
	 */
	public static Chord buildChord(final ChordType type, final Note dominant) {
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
	 * @return the major chord corresponding to the dominant
	 */
	public static Chord buildMajorChord(final Note dominant) {
		Note[] chord = new Note[3];
		chord[0] = dominant;
		chord[1] = NoteFactory.majorThird(dominant);
		chord[2] = NoteFactory.perfectFifth(dominant);
		return new Chord(dominant, ChordType.major, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor chord corresponding to the dominant
	 */
	public static Chord buildMinorChord(final Note dominant) {
		Note[] chord = new Note[3];
		chord[0] = dominant;
		chord[1] = NoteFactory.minorThird(dominant);
		chord[2] = NoteFactory.perfectFifth(dominant);
		return new Chord(dominant, ChordType.minor, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major augmented chord corresponding to the dominant
	 */
	public static Chord buildAugmentedChord(final Note dominant) {
		Note[] chord = new Note[3];
		chord[0] = dominant;
		chord[1] = NoteFactory.majorThird(dominant);
		chord[2] = NoteFactory.augmented(NoteFactory.perfectFifth(dominant));
		return new Chord(dominant, ChordType.augmented, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor diminished chord corresponding to the dominant
	 */
	public static Chord buildDiminishedChord(final Note dominant) {
		Note[] chord = new Note[3];
		chord[0] = dominant;
		chord[1] = NoteFactory.minorThird(dominant);
		chord[2] = NoteFactory.diminished(NoteFactory.perfectFifth(dominant));
		return new Chord(dominant, ChordType.diminished, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the dominant 7<sup>th</sup> chord corresponding to the dominant
	 */
	public static Chord buildDominant7thChord(final Note dominant) {
		Note[] chord = new Note[4];
		chord[0] = dominant;
		chord[1] = NoteFactory.majorThird(dominant);
		chord[2] = NoteFactory.perfectFifth(dominant);
		chord[3] = NoteFactory.minorSeventh(dominant);
		return new Chord(dominant, ChordType.dominant7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major 7<sup>th</sup> chord corresponding to the dominant
	 */
	public static Chord buildMajor7thChord(final Note dominant) {
		Note[] chord = new Note[4];
		chord[0] = dominant;
		chord[1] = NoteFactory.majorThird(dominant);
		chord[2] = NoteFactory.perfectFifth(dominant);
		chord[3] = NoteFactory.majorSeventh(dominant);
		return new Chord(dominant, ChordType.major7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor 7<sup>th</sup> chord corresponding to the dominant
	 */
	public static Chord buildMinor7thChord(final Note dominant) {
		Note[] chord = new Note[4];
		chord[0] = dominant;
		chord[1] = NoteFactory.minorThird(dominant);
		chord[2] = NoteFactory.perfectFifth(dominant);
		chord[3] = NoteFactory.minorSeventh(dominant);
		return new Chord(dominant, ChordType.minor7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the major augmented 7<sup>th</sup> chord corresponding to the
	 *         dominant
	 */
	public static Chord buildAugmented7thChord(final Note dominant) {
		Note[] chord = new Note[4];
		chord[0] = dominant;
		chord[1] = NoteFactory.majorThird(dominant);
		chord[2] = NoteFactory.augmented(NoteFactory.perfectFifth(dominant));
		chord[3] = NoteFactory.minorSeventh(dominant);
		return new Chord(dominant, ChordType.augmented7, chord);
	}

	/**
	 * @param dominant
	 *            the dominant tone for this chord
	 * @return the minor diminished 7<sup>th</sup> chord corresponding to the
	 *         dominant
	 */
	public static Chord buildDiminished7thChord(final Note dominant) {
		Note[] chord = new Note[4];
		chord[0] = dominant;
		chord[1] = NoteFactory.minorThird(dominant);
		chord[2] = NoteFactory.diminished(NoteFactory.perfectFifth(dominant));
		chord[3] = NoteFactory.diminished(NoteFactory.minorSeventh(dominant));
		return new Chord(dominant, ChordType.diminished7, chord);
	}
}
