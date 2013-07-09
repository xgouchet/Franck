package fr.xgouchet.musichelper.common;

import fr.xgouchet.musicgeneration.model.Note;

/**
 * @author Xavier Gouchet
 */
public class MusicUtils {

	public static final char FLAT = '♭';

	/**
	 * @param note
	 *            the note to draw
	 * @return the number of lines above a C4 on a staff to draw this note
	 */
	public static int getOffsetFromC4(final Note note) {
		int octaveDiff = note.getOctave() - 4;

		int pitchDiff = note.getPitch().ordinal();
		return pitchDiff + (octaveDiff * 7);
	}
}
