package fr.xgouchet.musichelper.test.model;

import java.util.Arrays;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Tone;

public class ChordTest extends TestCase {

	/**
	 * Test that each static buildXXXChord method returns exactly the same thing
	 * as the {@link Chord#buildChord(Tone)} from the corresponding chord type
	 */
	public void testStaticChords() {
		Tone staticChord[], enumChord[];

		// Major
		staticChord = Chord.buildMajorChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.major, Tone.C);
		assertTrue("Static vs Enum build chords : major",
				Arrays.equals(staticChord, enumChord));

		// Minor
		staticChord = Chord.buildMinorChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.minor, Tone.C);
		assertTrue("Static vs Enum build chords : minor",
				Arrays.equals(staticChord, enumChord));

		// Aug
		staticChord = Chord.buildAugmentedChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.augmented, Tone.C);
		assertTrue("Static vs Enum build chords : aug",
				Arrays.equals(staticChord, enumChord));

		// Dim
		staticChord = Chord.buildDiminishedChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.diminished, Tone.C);
		assertTrue("Static vs Enum build chords : dim",
				Arrays.equals(staticChord, enumChord));

		// 7th
		staticChord = Chord.buildSeventhChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.seventh, Tone.C);
		assertTrue("Static vs Enum build chords : 7th",
				Arrays.equals(staticChord, enumChord));
	}

	/**
	 * Tests the generation of known chords
	 */
	public void testChordValues() {
		Tone value[], expected[];

		// C major
		value = Chord.buildChord(Chord.Type.major, Tone.C);
		expected = new Tone[] { Tone.C, Tone.E, Tone.G };
		assertTrue("Chord C", Arrays.equals(value, expected));

		// A minor
		value = Chord.buildChord(Chord.Type.minor, Tone.A);
		expected = new Tone[] { Tone.A, Tone.C, Tone.E };
		assertTrue("Chord Am", Arrays.equals(value, expected));

		// F Aug
		value = Chord.buildChord(Chord.Type.augmented, Tone.F);
		expected = new Tone[] { Tone.F, Tone.A, Tone.CSharp };
		assertTrue("Chord Faug", Arrays.equals(value, expected));

		// D Dim
		value = Chord.buildChord(Chord.Type.diminished, Tone.D);
		expected = new Tone[] { Tone.D, Tone.F, Tone.GSharp };
		assertTrue("Chord Ddim", Arrays.equals(value, expected));

		// E dominant 7th
		value = Chord.buildChord(Chord.Type.seventh, Tone.E);
		expected = new Tone[] { Tone.E, Tone.GSharp, Tone.B, Tone.D };
		assertTrue("Chord E7", Arrays.equals(value, expected));
	}
}
