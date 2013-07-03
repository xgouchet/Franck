package fr.xgouchet.musicgeneration.test;

import junit.framework.TestCase;
import fr.xgouchet.musicgeneration.model.Accidental;
import fr.xgouchet.musicgeneration.model.Note;
import fr.xgouchet.musicgeneration.model.Pitch;

public class NoteTestCase extends TestCase {

	/**
	 * Test possible ways of creating a natural C4
	 */
	public void testMiddleC() {
		Note reference = new Note();

		assertEquals(reference, new Note(Pitch.C, Accidental.natural, 4));
		assertEquals(reference, new Note(Pitch.B, Accidental.sharp, 3));
	}

}
