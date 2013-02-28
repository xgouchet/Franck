package fr.xgouchet.musichelper.test.model;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Tone;

public class ToneTest extends TestCase {

	/**
	 * Test the {@link Tone} helper methods on the C circle of fifth
	 */
	public void testCircleOfFifth() {
		assertEquals("Circle of 5th : C 4th", Tone.C.fourth(), Tone.F);
		assertEquals("Circle of 5th : C 5th", Tone.C.fifth(), Tone.G);
		assertEquals("Circle of 5th : C 2nd", Tone.C.second(), Tone.D);
		assertEquals("Circle of 5th : C 6th", Tone.C.sixth(), Tone.A);
		assertEquals("Circle of 5th : C 6rd", Tone.C.third(), Tone.E);
		assertEquals("Circle of 5th : C 7th", Tone.C.seventh(), Tone.B);
	}

	/**
	 * Test that, for any note x and its fourth y, the fifth of y is x
	 */
	public void testFifthFourth() {
		Tone[] values = Tone.values();

		for (Tone tone : values) {
			assertEquals("Testing 4th/5th : " + tone.toString(), tone, tone
					.fourth().fifth());
		}
	}

	/**
	 * Test some known alterations
	 */
	public void testAlterations() {
		assertEquals("A# == Bb ?", Tone.A.augmented(), Tone.B.diminished());
		assertEquals("B# == C ?", Tone.B.augmented(), Tone.C);
		assertEquals("C# == Db ?", Tone.C.augmented(), Tone.D.diminished());
		assertEquals("D# == Eb ?", Tone.D.augmented(), Tone.E.diminished());
		assertEquals("E# == F ?", Tone.E.augmented(), Tone.F);
		assertEquals("F# == Gb ?", Tone.F.augmented(), Tone.G.diminished());
		assertEquals("G# == Ab ?", Tone.G.augmented(), Tone.A.diminished());
	}

	public void testToneParsing() {
		// Existing values
		assertEquals("Parsing : \"A\"", Tone.parse("A"), Tone.A);
		assertEquals("Parsing : \"CSharp\"", Tone.parse("CSharp"), Tone.CSharp);

		// Simple values
		assertEquals("Parsing : \"E#\"", Tone.parse("E#"), Tone.F);
		assertEquals("Parsing : \"Cb\"", Tone.parse("Cb"), Tone.B);
		assertEquals("Parsing : \"F#\"", Tone.parse("F#"), Tone.FSharp);
		assertEquals("Parsing : \"Ab\"", Tone.parse("Ab"), Tone.GSharp);

		// double / multiple alterations
		assertEquals("Parsing : \"F###\"", Tone.parse("F###"), Tone.GSharp);
		assertEquals("Parsing : \"Abbb\"", Tone.parse("Abbb"), Tone.FSharp);
		assertEquals("Parsing : \"E#b#b#b\"", Tone.parse("E#b#b#b"), Tone.E);

		// Errors
		try {
			Tone.parse(null);
			fail("NPE should have been thrown when parsing a null String");
		} catch (NullPointerException e) {
		}

		try {
			Tone.parse("Sol#");
			fail("Illegal Argument Exception should have been thrown when parsing an invalid value");
		} catch (IllegalArgumentException e) {
		}

	}
}
