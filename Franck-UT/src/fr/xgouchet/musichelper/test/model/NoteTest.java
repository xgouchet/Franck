package fr.xgouchet.musichelper.test.model;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Accidental;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Pitch;

/**
 * Test all methods corresponding to notes
 */
public class NoteTest extends TestCase {

	public void testMiddleCConstructors() {
		Note middleC = new Note(Pitch.C, Accidental.natural, 4, 1);

		assertEquals("Note()", middleC, new Note());

		// by pitch / acc
		assertEquals("Note(Pitch, Accidental, int)", middleC, new Note(Pitch.C,
				Accidental.natural, 4));

		// by halftones
		assertEquals("Note(int)", middleC, new Note(0));
		assertEquals("Note(int, int) // 4th octave", middleC, new Note(0, 4));
		assertEquals("Note(int, int) // 0th octave", middleC, new Note(48, 0));
		assertEquals("Note(int, int) // 3rd octave", middleC, new Note(12, 3));
		assertEquals("Note(int, int) // 5th octave", middleC, new Note(-12, 5));
		assertEquals("Note(int, int) // 8th octave", middleC, new Note(-48, 8));
		assertEquals("Note(int, int, int)", middleC, new Note(0, 4, 1));
	}

	public void testCircleOf5th() {

		// Test on Middle C
		Note middleC = new Note(Pitch.C, Accidental.natural, 4, 1);

		assertEquals("Circle of 5th : C 4th", middleC.fourth(), new Note(
				Pitch.F, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : C 5th", middleC.fifth(), new Note(
				Pitch.G, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : C 2nd", middleC.secondMajor(), new Note(
				Pitch.D, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : C 6th", middleC.sixth(), new Note(
				Pitch.A, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : C 6rd", middleC.thirdMajor(), new Note(
				Pitch.E, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : C 7th", middleC.seventhMajor(), new Note(
				Pitch.B, Accidental.natural, 4, 1));

	}
}
