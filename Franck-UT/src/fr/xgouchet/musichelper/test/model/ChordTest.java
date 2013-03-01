package fr.xgouchet.musichelper.test.model;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Tone;

/**
 * TODO test Parse, tostring->parse
 */
public class ChordTest extends TestCase {

	/**
	 * Test that each static buildXXXChord method returns exactly the same thing
	 * as the {@link Chord#buildChord(Tone)} from the corresponding chord type
	 */
	public void testStaticChords() {
		Chord staticChord, enumChord;

		// Major
		staticChord = Chord.buildMajorChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.major, Tone.C);
		assertEquals("Static vs Enum build chords : major", staticChord,
				enumChord);

		// Minor
		staticChord = Chord.buildMinorChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.minor, Tone.C);
		assertEquals("Static vs Enum build chords : minor", staticChord,
				enumChord);

		// Aug
		staticChord = Chord.buildAugmentedChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.augmented, Tone.C);
		assertEquals("Static vs Enum build chords : aug", staticChord,
				enumChord);

		// Dim
		staticChord = Chord.buildDiminishedChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.diminished, Tone.C);
		assertEquals("Static vs Enum build chords : dim", staticChord,
				enumChord);

		// 7th
		staticChord = Chord.buildDominant7thChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.dominant7, Tone.C);
		assertEquals("Static vs Enum build chords : 7th", staticChord,
				enumChord);

		// Major 7th
		staticChord = Chord.buildMajor7thChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.major7, Tone.C);
		assertEquals("Static vs Enum build chords : Maj 7th", staticChord,
				enumChord);

		// Minor 7th
		staticChord = Chord.buildMinor7thChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.minor7, Tone.C);
		assertEquals("Static vs Enum build chords : min 7th", staticChord,
				enumChord);

		// Augmented 7th
		staticChord = Chord.buildAugmented7thChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.augmented7, Tone.C);
		assertEquals("Static vs Enum build chords : Aug 7th", staticChord,
				enumChord);

		// Diminished 7th
		staticChord = Chord.buildDiminished7thChord(Tone.C);
		enumChord = Chord.buildChord(Chord.Type.diminished7, Tone.C);
		assertEquals("Static vs Enum build chords : dim 7th", staticChord,
				enumChord);
	}

	/**
	 * Tests the generation of known chords
	 */
	public void testChordValues() {
		Chord value, expected;

		// C major
		value = Chord.buildChord(Chord.Type.major, Tone.C);
		expected = new Chord(Tone.C, Chord.Type.major, new Tone[] { Tone.C,
				Tone.E, Tone.G });
		assertEquals("Chord C", value, expected);

		// A minor
		value = Chord.buildChord(Chord.Type.minor, Tone.A);
		expected = new Chord(Tone.A, Chord.Type.minor, new Tone[] { Tone.A,
				Tone.C, Tone.E });
		assertEquals("Chord Am", value, expected);

		// C Aug
		value = Chord.buildChord(Chord.Type.augmented, Tone.C);
		expected = new Chord(Tone.C, Chord.Type.augmented, new Tone[] { Tone.C,
				Tone.E, Tone.GSharp });
		assertEquals("Chord Faug", value, expected);

		// D Dim
		value = Chord.buildChord(Chord.Type.diminished, Tone.D);
		expected = new Chord(Tone.D, Chord.Type.diminished, new Tone[] {
				Tone.D, Tone.F, Tone.GSharp });
		assertEquals("Chord Ddim", value, expected);

		// E dominant 7th
		value = Chord.buildChord(Chord.Type.dominant7, Tone.E);
		expected = new Chord(Tone.E, Chord.Type.dominant7, new Tone[] { Tone.E,
				Tone.GSharp, Tone.B, Tone.D });
		assertEquals("Chord E7", value, expected);

		// C major 7th
		value = Chord.buildChord(Chord.Type.major7, Tone.C);
		expected = new Chord(Tone.C, Chord.Type.major7, new Tone[] { Tone.C,
				Tone.E, Tone.G, Tone.B });
		assertEquals("Chord CM7", value, expected);

		// C minor 7th
		value = Chord.buildChord(Chord.Type.minor7, Tone.C);
		expected = new Chord(Tone.C, Chord.Type.minor7, new Tone[] { Tone.C,
				Tone.EFlat, Tone.G, Tone.BFlat });
		assertEquals("Chord CM7", value, expected);

		// C augmented 7th
		value = Chord.buildChord(Chord.Type.augmented7, Tone.C);
		expected = new Chord(Tone.C, Chord.Type.augmented7, new Tone[] {
				Tone.C, Tone.E, Tone.GSharp, Tone.BFlat });
		assertEquals("Chord CM7", value, expected);

		// C diminished 7th
		value = Chord.buildChord(Chord.Type.diminished7, Tone.C);
		expected = new Chord(Tone.C, Chord.Type.diminished7, new Tone[] {
				Tone.C, Tone.EFlat, Tone.FSharp, Tone.A });
		assertEquals("Chord CM7", value, expected);

	}
}
