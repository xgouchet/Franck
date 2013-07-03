package fr.xgouchet.musicgeneration.model;

/**
 * A utility class used to generate new notes
 * 
 * @author Xavier Gouchet
 */
public class NoteFactory {

	/**
	 * @return the same note at a lower octave (eg : C4 -> C3)
	 */
	public static Note lowerOctave(final Note note) {
		return new Note(note.getPitch(), note.getAccidental(),
				note.getOctave() - 1);
	}

	/**
	 * @return the same note at a lower octave (eg : C4 -> C5)
	 */
	public static Note higherOctave(final Note note) {
		return new Note(note.getPitch(), note.getAccidental(),
				note.getOctave() + 1);
	}

	/**
	 * @return the augmented note based on this dominant
	 */
	public static Note augmented(final Note note) {
		switch (note.getAccidental()) {
		case sharp:
			return new Note(note.getPitch(), Accidental.doubleSharp,
					note.getOctave());
		case natural:
			return new Note(note.getPitch(), Accidental.sharp, note.getOctave());
		case flat:
			return new Note(note.getPitch(), Accidental.natural,
					note.getOctave());
		case doubleFlat:
			return new Note(note.getPitch(), Accidental.flat, note.getOctave());
		case doubleSharp:
			return new Note(note.getSemiTones() + 1);
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return the diminished note based on this dominant
	 */
	public static Note diminished(final Note note) {
		switch (note.getAccidental()) {
		case doubleSharp:
			return new Note(note.getPitch(), Accidental.sharp, note.getOctave());
		case sharp:
			return new Note(note.getPitch(), Accidental.natural,
					note.getOctave());
		case natural:
			return new Note(note.getPitch(), Accidental.flat, note.getOctave());
		case flat:
			return new Note(note.getPitch(), Accidental.doubleFlat,
					note.getOctave());
		case doubleFlat:
			return new Note(note.getSemiTones() - 1);
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return the minor 3<sup>rd</sup> note based on this dominant
	 */
	public static Note minorThird(final Note note) {
		return new Note(note.getSemiTones() + 3);
	}

	/**
	 * @return the major 3<sup>rd</sup> note based on this dominant
	 */
	public static Note majorThird(final Note note) {
		return new Note(note.getSemiTones() + 4);
	}

	/**
	 * @return the 4<sup>th</sup> note based on this dominant
	 */
	public static Note fourth(final Note note) {
		return new Note(note.getSemiTones() + 5);
	}

	/**
	 * @return the tritone note based on this dominant
	 */
	public static Note tritone(final Note note) {
		return new Note(note.getSemiTones() + 6);
	}

	/**
	 * @return the perfect 5<sup>th</sup> note based on this dominant
	 */
	public static Note perfectFifth(final Note note) {
		return new Note(note.getSemiTones() + 7);
	}

	/**
	 * @return the minor 6<sup>th</sup> note based on this dominant
	 */
	public static Note minorSixth(final Note note) {
		return new Note(note.getSemiTones() + 8);
	}

	/**
	 * @return the major 4<sup>th</sup> note based on this dominant
	 */
	public static Note majorSixth(final Note note) {
		return new Note(note.getSemiTones() + 9);
	}

	/**
	 * @return the minor 7<sup>th</sup> note based on this dominant
	 */
	public static Note minorSeventh(final Note note) {
		return new Note(note.getSemiTones() + 10);
	}

	/**
	 * @return the major 7<sup>th</sup> note based on this dominant
	 */
	public static Note majorSeventh(final Note note) {
		return new Note(note.getSemiTones() + 11);
	}

}
