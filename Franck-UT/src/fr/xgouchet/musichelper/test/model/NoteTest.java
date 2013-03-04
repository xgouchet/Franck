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

	/**
	 * Test that, for any note x and its fourth y, the fifth of y is x
	 */
	public void testFifthFourth() {
		Note note;

		for (int i = 0; i < 12; i++) {
			note = new Note(i);
			assertEquals("Testing 4th/5th : " + note.toDisplayString(),
					note.getPitch(), note.fourth().fifth().getPitch());
		}
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
		assertEquals("Circle of 5th : C 3rd", middleC.thirdMajor(), new Note(
				Pitch.E, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : C 7th", middleC.seventhMajor(), new Note(
				Pitch.B, Accidental.natural, 4, 1));

		// Test on Middle F
		Note middleF = new Note(Pitch.F, Accidental.natural, 4, 1);
		assertEquals("Circle of 5th : F 4th", middleF.fourth(), new Note(
				Pitch.B, Accidental.flat, 4, 1));
		assertEquals("Circle of 5th : F 5th", middleF.fifth(), new Note(
				Pitch.C, Accidental.natural, 5, 1));
		assertEquals("Circle of 5th : F 2nd", middleF.secondMajor(), new Note(
				Pitch.G, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : F 6th", middleF.sixth(), new Note(
				Pitch.D, Accidental.natural, 5, 1));
		assertEquals("Circle of 5th : F 3rd", middleF.thirdMajor(), new Note(
				Pitch.A, Accidental.natural, 4, 1));
		assertEquals("Circle of 5th : F 7th", middleF.seventhMajor(), new Note(
				Pitch.E, Accidental.natural, 5, 1));

		// Test on Middle Bb
		Note middleBb = new Note(Pitch.B, Accidental.flat, 4, 1);
		assertEquals("Circle of 5th : Bb 4th", middleBb.fourth(), new Note(
				Pitch.E, Accidental.flat, 5, 1));
		assertEquals("Circle of 5th : Bb 5th", middleBb.fifth(), new Note(
				Pitch.F, Accidental.natural, 5, 1));
		assertEquals("Circle of 5th : Bb 2nd", middleBb.secondMajor(),
				new Note(Pitch.C, Accidental.natural, 5, 1));
		assertEquals("Circle of 5th : Bb 6th", middleBb.sixth(), new Note(
				Pitch.G, Accidental.natural, 5, 1));
		assertEquals("Circle of 5th : Bb 3rd", middleBb.thirdMajor(), new Note(
				Pitch.D, Accidental.natural, 5, 1));
		assertEquals("Circle of 5th : Bb 7th", middleBb.seventhMajor(),
				new Note(Pitch.A, Accidental.natural, 5, 1));
	}

	public void testToDisplayString() {
		assertEquals("Note.toString() C", "C", new Note().toDisplayString());
		assertEquals("Note.toString() E♭", "E♭", new Note(Pitch.E,
				Accidental.flat, 4, 1).toDisplayString());
		assertEquals("Note.toString() F#", "F#", new Note(Pitch.F,
				Accidental.sharp, 4, 1).toDisplayString());
	}
}
