package fr.xgouchet.musichelper.test.model;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Accidental;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Pitch;

/**
 * Test all methods corresponding to notes
 */
public class NoteTest extends TestCase {

	public void testEquals() {
		Note middleC = new Note(Pitch.C, Accidental.natural, 4, 1);

		assertEquals("Note()", middleC, new Note());

		// by pitch / acc
		assertEquals("Note(Pitch, Accidental, int)", middleC, new Note(Pitch.C,
				Accidental.natural, 4));

		// by halftones
		assertEquals("Note(int)", middleC, new Note(0));
		assertEquals("Note(int, int)", middleC, new Note(0, 1));
		assertEquals("Note(int, int, int) // 4th octave", middleC, new Note(0,
				4, 1));
		assertEquals("Note(int, int, int) // 0th octave", middleC, new Note(48,
				0, 1));
		assertEquals("Note(int, int, int) // 3rd octave", middleC, new Note(12,
				3, 1));
		assertEquals("Note(int, int, int) // 5th octave", middleC, new Note(
				-12, 5, 1));
		assertEquals("Note(int, int, int) // 8th octave", middleC, new Note(
				-48, 8, 1));

		assertFalse("Not equals",
				new Note().equals(new Note().toDisplayString()));

	}

	/**
	 * Test field getters
	 */
	public void testGetters() {
		Note middleC = new Note();

		assertEquals("getPitch", Pitch.C, middleC.getPitch());
		assertEquals("getAccidental", Accidental.natural,
				middleC.getAccidental());
		assertEquals("getOctave", 4, middleC.getOctave());
		assertEquals("getFraction", 1, middleC.getFraction());

		assertFalse("isAltered (natural C)", middleC.isAltered());
		assertTrue("isAltered (C#)", middleC.augmented().isAltered());
		assertTrue("isAltered (C♭)", middleC.diminished().isAltered());
		assertTrue("isAltered (C##)", middleC.augmented().augmented()
				.isAltered());
		assertTrue("isAltered (C♭♭)", middleC.diminished().diminished()
				.isAltered());

		assertFalse("isAltered (C## simplified)", middleC.augmented()
				.augmented().simplify().isAltered());
		assertTrue("isAltered (C♭♭ simplified)", middleC.diminished()
				.diminished().simplify().isAltered());

		assertTrue("isAltered (C### = D#)", middleC.augmented().augmented()
				.augmented().isAltered());
		assertFalse("isAltered (C♭♭♭ = A)", middleC.diminished().diminished()
				.diminished().isAltered());
	}

	/**
	 * Test constructors and derivators
	 */
	public void testConstructorsAndDerivators() {
		Note middleC = new Note(Pitch.C, Accidental.natural, 4, 1);

		assertEquals("Note(int, int, int)", middleC,
				new Note(-1, 4, 1).secondMinor());
		assertEquals("Note(int, int, int)", middleC,
				new Note(-2, 4, 1).secondMajor());
		assertEquals("Note(int, int, int)", middleC,
				new Note(-3, 4, 1).thirdMinor());
		assertEquals("Note(int, int, int)", middleC,
				new Note(-4, 4, 1).thirdMajor());
		assertEquals("Note(int, int, int)", middleC,
				new Note(-5, 4, 1).fourth());
		assertEquals("Note(int, int, int)", middleC, new Note(-6, 4, 1).fifth()
				.diminished());
		assertEquals("Note(int, int, int)", middleC, new Note(-7, 4, 1).fifth());
		assertEquals("Note(int, int, int)", middleC, new Note(-8, 4, 1).fifth()
				.augmented());
		assertEquals("Note(int, int, int)", middleC, new Note(-9, 4, 1).sixth());
		assertEquals("Note(int, int, int)", middleC,
				new Note(-10, 4, 1).seventhMinor());
		assertEquals("Note(int, int, int)", middleC,
				new Note(-11, 4, 1).seventhMajor());
		assertEquals("Note(int, int, int)", middleC,
				new Note(12, 4, 1).lowerOctave());
		assertEquals("Note(int, int, int)", middleC,
				new Note(-12, 4, 1).higherOctave());
	}

	/**
	 * Test that, for any note x and its fourth y, the fifth of y is x
	 */
	public void testFifthFourth() {
		Note note;

		for (int i = 0; i < 12; i++) {
			note = new Note(i);
			assertEquals("Testing 4th/5th : " + note.toString(),
					note.getPitch(), note.fourth().fifth().getPitch());
		}
	}

	/**
	 * Test known derivation (circle of fifth) from natural middle C
	 */
	public void testCircleOf5thOnC() {
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

	}

	/**
	 * Test known derivation (circle of fifth) from natural middle F
	 */
	public void testCircleOf5thOnF() {
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
	}

	/**
	 * Test known derivation (circle of fifth) from middle B flat
	 */
	public void testCircleOf5thOnBb() {
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

	/**
	 * Test the generation of String
	 */
	public void testToDisplayString() {
		assertEquals("Note.toString() C", "C", new Note().toDisplayString());
		assertEquals("Note.toString() E♭", "E♭", new Note(Pitch.E,
				Accidental.flat, 4, 1).toDisplayString());
		assertEquals("Note.toString() F#", "F#", new Note(Pitch.F,
				Accidental.sharp, 4, 1).toDisplayString());
		assertEquals("Note.toString() B♭♭", "B♭♭", new Note(Pitch.B,
				Accidental.doubleFlat, 4, 1).toDisplayString());
		assertEquals("Note.toString() G##", "G##", new Note(Pitch.G,
				Accidental.doubleSharp, 4, 1).toDisplayString());
	}

	/**
	 * Test note simplification
	 */
	public void testSimplify() {
		assertEquals("Simplify E#", "F", new Note(Pitch.E, Accidental.sharp, 4)
		.simplify().toDisplayString());

		assertEquals("Simplify B#", "C", new Note(Pitch.B, Accidental.sharp, 4)
		.simplify().toDisplayString());

		assertEquals("Simplify C#", "C#",
				new Note(Pitch.C, Accidental.sharp, 4).simplify()
				.toDisplayString());

		assertEquals("Simplify F##", "G", new Note(Pitch.F,
				Accidental.doubleSharp, 4).simplify().toDisplayString());

		assertEquals("Simplify D♭♭", "C", new Note(Pitch.D,
				Accidental.doubleFlat, 4).simplify().toDisplayString());
	}

	/**
	 * Run the same tests as {@link #testEquals()} but comparing hashcodes
	 */
	public void testHashCode() {
		int middleC = new Note(Pitch.C, Accidental.natural, 4, 1).hashCode();

		assertEquals("hashcode", middleC, new Note(-1, 4, 1).secondMinor()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(-2, 4, 1).secondMajor()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(-3, 4, 1).thirdMinor()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(-4, 4, 1).thirdMajor()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(-5, 4, 1).fourth()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(-6, 4, 1).fifth()
				.diminished().hashCode());
		assertEquals("hashcode", middleC, new Note(-7, 4, 1).fifth().hashCode());
		assertEquals("hashcode", middleC, new Note(-8, 4, 1).fifth()
				.augmented().hashCode());
		assertEquals("hashcode", middleC, new Note(-9, 4, 1).sixth().hashCode());
		assertEquals("hashcode", middleC, new Note(-10, 4, 1).seventhMinor()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(-11, 4, 1).seventhMajor()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(12, 4, 1).lowerOctave()
				.hashCode());
		assertEquals("hashcode", middleC, new Note(-12, 4, 1).higherOctave()
				.hashCode());

	}

	/**
	 * Test some correspondance in alterations
	 */
	public void testAlterations() {
		assertEquals("Altered C#", 1, new Note().augmented().getHalfTones());
		assertEquals("Altered C##", 2, new Note().augmented().augmented()
				.getHalfTones());
		assertEquals("Altered C♭", -1, new Note().diminished().getHalfTones());
		assertEquals("Altered C♭♭", -2, new Note().diminished().diminished()
				.getHalfTones());
		assertEquals("Altered C", 0, new Note().augmented().diminished()
				.getHalfTones());
		assertEquals("Altered C", 0, new Note().augmented().augmented()
				.diminished().diminished().getHalfTones());
		assertEquals("Altered C", 0, new Note().augmented().augmented()
				.augmented().diminished().diminished().diminished()
				.getHalfTones());
		assertEquals("Altered C", 0, new Note().getHalfTones());
		assertEquals("Altered C", 0, new Note().diminished().diminished()
				.augmented().augmented().getHalfTones());
		assertEquals("Altered C", 0, new Note().diminished().diminished()
				.diminished().augmented().augmented().augmented()
				.getHalfTones());
	}

	/**
	 * Test some note parsing
	 */
	public void testParse() {
		assertEquals("Parse E♭", Note.parse("E♭"), new Note(Pitch.E,
				Accidental.flat, 4));
		assertEquals("Parse Bb", Note.parse("Bb"), new Note(Pitch.B,
				Accidental.flat, 4));
		assertEquals("Parse C#", Note.parse("C#"), new Note(Pitch.C,
				Accidental.sharp, 4));
		assertEquals("Parse F##", Note.parse("F##"), new Note(Pitch.F,
				Accidental.doubleSharp, 4));
		assertEquals("Parse B♭♭", Note.parse("B♭♭"), new Note(Pitch.B,
				Accidental.doubleFlat, 4));
		assertEquals("Parse Dbb", Note.parse("Dbb"), new Note(Pitch.D,
				Accidental.doubleFlat, 4));

		try {
			Note.parse("W#");
			fail("Parse should have raised exception on \"w#\"");
		} catch (IllegalArgumentException e) {

		}

		try {
			Note.parse("CSharp");
			fail("Parse should have raised exception on \"CSharp\"");
		} catch (IllegalArgumentException e) {

		}
	}

	/**
	 * Test that the display string is parsed correctly
	 */
	public void testDisplayStringParseCoherence() {
		Note middleC = new Note(Pitch.C, Accidental.natural, 4, 1);

		Note note;

		note = middleC.secondMinor();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.secondMajor();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.thirdMinor();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.thirdMajor();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.fourth();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.fifth();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.sixth();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.seventhMinor();
		assertEquals("DisplayString parsed", note,
				Note.parse(note.toDisplayString()));
		note = middleC.seventhMajor();

	}

	/**
	 * Tests the offset on a staff from the C4
	 */
	public void testOffsetFromC4() {
		assertEquals("OffsetFromC4 : F4", 3, new Note(Pitch.F,
				Accidental.natural).getOffsetFromC4());
		assertEquals("OffsetFromC4 : B♭4", 6,
				new Note(Pitch.B, Accidental.flat).getOffsetFromC4());
		assertEquals("OffsetFromC4 : C5", 7, new Note(Pitch.C,
				Accidental.natural, 5).getOffsetFromC4());
		assertEquals("OffsetFromC4 : D#3", -6, new Note(Pitch.D,
				Accidental.sharp, 3).getOffsetFromC4());
		assertEquals("OffsetFromC4 : c3", -7, new Note(Pitch.C,
				Accidental.natural, 3).getOffsetFromC4());
	}

}
