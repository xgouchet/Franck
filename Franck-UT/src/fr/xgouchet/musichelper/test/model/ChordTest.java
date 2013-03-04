package fr.xgouchet.musichelper.test.model;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Note;
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
		Note middleC = new Note();

		// Major
		staticChord = Chord.buildMajorChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.major, middleC);
		assertEquals("Static vs Enum build chords : major", staticChord,
				enumChord);

		// Minor
		staticChord = Chord.buildMinorChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.minor, middleC);
		assertEquals("Static vs Enum build chords : minor", staticChord,
				enumChord);

		// Aug
		staticChord = Chord.buildAugmentedChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.augmented, middleC);
		assertEquals("Static vs Enum build chords : aug", staticChord,
				enumChord);

		// Dim
		staticChord = Chord.buildDiminishedChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.diminished, middleC);
		assertEquals("Static vs Enum build chords : dim", staticChord,
				enumChord);

		// 7th
		staticChord = Chord.buildDominant7thChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.dominant7, middleC);
		assertEquals("Static vs Enum build chords : 7th", staticChord,
				enumChord);

		// Major 7th
		staticChord = Chord.buildMajor7thChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.major7, middleC);
		assertEquals("Static vs Enum build chords : Maj 7th", staticChord,
				enumChord);

		// Minor 7th
		staticChord = Chord.buildMinor7thChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.minor7, middleC);
		assertEquals("Static vs Enum build chords : min 7th", staticChord,
				enumChord);

		// Augmented 7th
		staticChord = Chord.buildAugmented7thChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.augmented7, middleC);
		assertEquals("Static vs Enum build chords : Aug 7th", staticChord,
				enumChord);

		// Diminished 7th
		staticChord = Chord.buildDiminished7thChord(middleC);
		enumChord = Chord.buildChord(Chord.Type.diminished7, middleC);
		assertEquals("Static vs Enum build chords : dim 7th", staticChord,
				enumChord);
	}

	/**
	 * Tests the generation of known chords
	 */
	public void testChordValues() {
		Chord value, expected;

		Note middleC = new Note();

		// C major
		value = Chord.buildChord(Chord.Type.major, middleC);
		expected = new Chord(middleC, Chord.Type.major, new Note[] {
				new Note(0), new Note(4), new Note(7) });
		assertEquals("Chord C", value, expected);

		// C minor
		value = Chord.buildChord(Chord.Type.minor, middleC);
		expected = new Chord(middleC, Chord.Type.minor, new Note[] {
				new Note(0), new Note(3), new Note(7) });
		assertEquals("Chord Cm", value, expected);

		// C Aug
		value = Chord.buildChord(Chord.Type.augmented, middleC);
		expected = new Chord(middleC, Chord.Type.augmented, new Note[] {
				new Note(0), new Note(4), new Note(8) });
		assertEquals("Chord Caug", value, expected);

		// C Dim
		value = Chord.buildChord(Chord.Type.diminished, middleC);
		expected = new Chord(middleC, Chord.Type.diminished, new Note[] {
				new Note(0), new Note(3), new Note(6) });
		assertEquals("Chord Cdim", value, expected);

		// C dominant 7th
		value = Chord.buildChord(Chord.Type.dominant7, middleC);
		expected = new Chord(middleC, Chord.Type.dominant7, new Note[] {
				new Note(0), new Note(4), new Note(7), new Note(10) });
		assertEquals("Chord C7", value, expected);

		// C major 7th
		value = Chord.buildChord(Chord.Type.major7, middleC);
		expected = new Chord(middleC, Chord.Type.major7, new Note[] {
				new Note(0), new Note(4), new Note(7), new Note(11) });
		assertEquals("Chord CM7", value, expected);

		// C minor 7th
		value = Chord.buildChord(Chord.Type.minor7, middleC);
		expected = new Chord(middleC, Chord.Type.minor7, new Note[] {
				new Note(0), new Note(3), new Note(7), new Note(10) });
		assertEquals("Chord Cm7", value, expected);

		// C augmented 7th
		value = Chord.buildChord(Chord.Type.augmented7, middleC);
		expected = new Chord(middleC, Chord.Type.augmented7, new Note[] {
				new Note(0), new Note(4), new Note(8), new Note(10) });
		assertEquals("Chord Caug7", value, expected);

		// C diminished 7th
		value = Chord.buildChord(Chord.Type.diminished7, middleC);
		expected = new Chord(middleC, Chord.Type.diminished7, new Note[] {
				new Note(0), new Note(3), new Note(6), new Note(9) });
		assertEquals("Chord Cdim7", value, expected);

	}
}
