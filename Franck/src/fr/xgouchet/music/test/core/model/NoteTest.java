package fr.xgouchet.music.test.core.model;

import junit.framework.TestCase;
import fr.xgouchet.music.core.model.Accidental;
import fr.xgouchet.music.core.model.Note;
import fr.xgouchet.music.core.model.Pitch;

public class NoteTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testEquals() {

		Note middleC = new Note(Pitch.C, Accidental.natural, 4);

		// Test All constructor
		assertEquals("Note()", middleC, new Note());
		assertEquals("Note(int)", middleC, new Note(0));
		assertEquals("Note(int, int)", middleC, new Note(0, 4));
		assertEquals("Note(Pitch, Accidental)", middleC, new Note(Pitch.C,
				Accidental.natural));
		assertEquals("Note(Pitch, Accidental, int)", middleC, new Note(Pitch.C,
				Accidental.natural, 4));

		// Severall Halftones variations
		assertEquals("Note(int, int) // 0th octave", middleC, new Note(48, 0));
		assertEquals("Note(int, int) // 3rd octave", middleC, new Note(12, 3));
		assertEquals("Note(int, int) // 5th octave", middleC, new Note(-12, 5));
		assertEquals("Note(int, int) // 8th octave", middleC, new Note(-48, 8));
	}
}
