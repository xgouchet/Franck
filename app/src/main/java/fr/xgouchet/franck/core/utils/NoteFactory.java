package fr.xgouchet.franck.core.utils;

import fr.xgouchet.franck.core.model.Accidental;
import fr.xgouchet.franck.core.model.Note;

/**
 * Class used to derive a Note from another one (usefull to build harmonies and
 * melodies)
 * 
 * @author Xavier Gouchet
 * 
 */
public final class NoteFactory {

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
	 * @return the augmented note based on this dominant (eg : C -> C#)
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
			return new Note(note.getHalfTones() + 1);
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return the diminished note based on this dominant (eg : C -> B)
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
			return new Note(note.getHalfTones() - 1);
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return the minor 3<sup>rd</sup> note based on this dominant (eg : C ->
	 *         Eb)
	 */
	public static Note minorThird(final Note note) {
		return new Note(note.getHalfTones() + 3);
	}

	/**
	 * @return the major 3<sup>rd</sup> note based on this dominant (eg : C ->
	 *         E)
	 */
	public static Note majorThird(final Note note) {
		return new Note(note.getHalfTones() + 4);
	}

	/**
	 * @return the 4<sup>th</sup> note based on this dominant (eg : C -> F)
	 */
	public static Note fourth(final Note note) {
		return new Note(note.getHalfTones() + 5);
	}

	/**
	 * @return the tritone note based on this dominant (eg : C -> F#)
	 */
	public static Note tritone(final Note note) {
		return new Note(note.getHalfTones() + 6);
	}

	/**
	 * @return the perfect 5<sup>th</sup> note based on this dominant (eg : C ->
	 *         G)
	 */
	public static Note perfectFifth(final Note note) {
		return new Note(note.getHalfTones() + 7);
	}

	/**
	 * @return the minor 6<sup>th</sup> note based on this dominant (eg : C ->
	 *         Ab)
	 */
	public static Note minorSixth(final Note note) {
		return new Note(note.getHalfTones() + 8);
	}

	/**
	 * @return the major 6<sup>th</sup> note based on this dominant (eg : C ->
	 *         A)
	 */
	public static Note majorSixth(final Note note) {
		return new Note(note.getHalfTones() + 9);
	}

	/**
	 * @return the minor 7<sup>th</sup> note based on this dominant (eg : C ->
	 *         Bb)
	 */
	public static Note minorSeventh(final Note note) {
		return new Note(note.getHalfTones() + 10);
	}

	/**
	 * @return the major 7<sup>th</sup> note based on this dominant (eg : C ->
	 *         B)
	 */
	public static Note majorSeventh(final Note note) {
		return new Note(note.getHalfTones() + 11);
	}

	/**
	 * @return the 9<sup>th</sup> note based on this dominant (eg : C4 -> D5)
	 */
	public static Note nineth(final Note note) {
		return new Note(note.getHalfTones() + 14);
	}

	/**
	 * @return the 11<sup>th</sup> note based on this dominant (eg : C4 -> F5)
	 */
	public static Note eleventh(final Note note) {
		return new Note(note.getHalfTones() + 17);
	}

	/**
	 * @return the 12<sup>th</sup> note based on this dominant (eg : C4 -> G5)
	 */
	public static Note twelveth(final Note note) {
		return new Note(note.getHalfTones() + 17);
	}

	/**
	 * @return the 13<sup>th</sup> note based on this dominant (eg : C4 -> A5)
	 */
	public static Note thirteenth(final Note note) {
		return new Note(note.getHalfTones() + 17);
	}

	/**
	 * Private constructor
	 */
	private NoteFactory() {
	}

}
