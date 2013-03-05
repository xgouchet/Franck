package fr.xgouchet.musichelper.test.model;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Chord.Type;
import fr.xgouchet.musichelper.model.Note;

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

		try {
			Chord.buildChord(Type.halfDiminishedSeventh, new Note());
			fail("Exception should have been raised in Chord.buildChord(null, null)");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests the generation of known chords hashcodes (including some simplified
	 */
	public void testChordHascode() {
		Chord value, expected;

		Note middleC = new Note();

		// C major
		value = Chord.buildChord(Chord.Type.major, middleC);
		expected = new Chord(middleC, Chord.Type.major, new Note[] {
				new Note(0), new Note(4), new Note(7) });
		assertEquals("Chord C", value.hashCode(), expected.hashCode());

		// C minor
		value = Chord.buildChord(Chord.Type.minor, middleC);
		expected = new Chord(middleC, Chord.Type.minor, new Note[] {
				new Note(0), new Note(3), new Note(7) });
		assertEquals("Chord Cm", value.hashCode(), expected.hashCode());

		// C Aug
		value = Chord.buildChord(Chord.Type.augmented, middleC);
		expected = new Chord(middleC, Chord.Type.augmented, new Note[] {
				new Note(0), new Note(4), new Note(8) });
		assertEquals("Chord Caug", value.hashCode(), expected.hashCode());

		// C Dim
		value = Chord.buildChord(Chord.Type.diminished, middleC);
		expected = new Chord(middleC, Chord.Type.diminished, new Note[] {
				new Note(0), new Note(3), new Note(6) });
		assertEquals("Chord Cdim", value.hashCode(), expected.hashCode());

		// C dominant 7th
		value = Chord.buildChord(Chord.Type.dominant7, middleC);
		expected = new Chord(middleC, Chord.Type.dominant7, new Note[] {
				new Note(0), new Note(4), new Note(7), new Note(10) });
		expected.simplify();
		assertEquals("Chord C7", value.hashCode(), expected.hashCode());

		// C major 7th
		value = Chord.buildChord(Chord.Type.major7, middleC);
		expected = new Chord(middleC, Chord.Type.major7, new Note[] {
				new Note(0), new Note(4), new Note(7), new Note(11) });
		expected.simplify();
		assertEquals("Chord CM7 ", value.hashCode(), expected.hashCode());

		// C minor 7th
		value = Chord.buildChord(Chord.Type.minor7, middleC);
		expected = new Chord(middleC, Chord.Type.minor7, new Note[] {
				new Note(0), new Note(3), new Note(7), new Note(10) });
		expected.simplify();
		assertEquals("Chord Cm7", value.hashCode(), expected.hashCode());

		// C augmented 7th
		value = Chord.buildChord(Chord.Type.augmented7, middleC);
		expected = new Chord(middleC, Chord.Type.augmented7, new Note[] {
				new Note(0), new Note(4), new Note(8), new Note(10) });
		expected.simplify();
		assertEquals("Chord Caug7", value.hashCode(), expected.hashCode());

		// C diminished 7th
		value = Chord.buildChord(Chord.Type.diminished7, middleC);
		expected = new Chord(middleC, Chord.Type.diminished7, new Note[] {
				new Note(0), new Note(3), new Note(6), new Note(9) });
		expected.simplify();
		assertEquals("Chord Cdim7", value.hashCode(), expected.hashCode());
	}

	/**
	 * Tests the generation of known chords simplified
	 */
	public void testChordSimplifiedValues() {
		Chord value, expected;

		Note middleC = new Note();

		// C major
		value = Chord.buildChord(Chord.Type.major, middleC);
		expected = new Chord(middleC, Chord.Type.major, new Note[] {
				new Note(0), new Note(4), new Note(7) });
		expected.simplify();
		assertEquals("Chord C", value, expected);

		// C minor
		value = Chord.buildChord(Chord.Type.minor, middleC);
		expected = new Chord(middleC, Chord.Type.minor, new Note[] {
				new Note(0), new Note(3), new Note(7) });
		expected.simplify();
		assertEquals("Chord Cm", value, expected);

		// C Aug
		value = Chord.buildChord(Chord.Type.augmented, middleC);
		expected = new Chord(middleC, Chord.Type.augmented, new Note[] {
				new Note(0), new Note(4), new Note(8) });
		expected.simplify();
		assertEquals("Chord Caug", value, expected);

		// C Dim
		value = Chord.buildChord(Chord.Type.diminished, middleC);
		expected = new Chord(middleC, Chord.Type.diminished, new Note[] {
				new Note(0), new Note(3), new Note(6) });
		expected.simplify();
		assertEquals("Chord Cdim", value, expected);

		// C dominant 7th
		value = Chord.buildChord(Chord.Type.dominant7, middleC);
		expected = new Chord(middleC, Chord.Type.dominant7, new Note[] {
				new Note(0), new Note(4), new Note(7), new Note(10) });
		expected.simplify();
		assertEquals("Chord C7", value, expected);

		// C major 7th
		value = Chord.buildChord(Chord.Type.major7, middleC);
		expected = new Chord(middleC, Chord.Type.major7, new Note[] {
				new Note(0), new Note(4), new Note(7), new Note(11) });
		expected.simplify();
		assertEquals("Chord CM7", value, expected);

		// C minor 7th
		value = Chord.buildChord(Chord.Type.minor7, middleC);
		expected = new Chord(middleC, Chord.Type.minor7, new Note[] {
				new Note(0), new Note(3), new Note(7), new Note(10) });
		expected.simplify();
		assertEquals("Chord Cm7", value, expected);

		// C augmented 7th
		value = Chord.buildChord(Chord.Type.augmented7, middleC);
		expected = new Chord(middleC, Chord.Type.augmented7, new Note[] {
				new Note(0), new Note(4), new Note(8), new Note(10) });
		expected.simplify();
		assertEquals("Chord Caug7", value, expected);

		// C diminished 7th
		value = Chord.buildChord(Chord.Type.diminished7, middleC);
		expected = new Chord(middleC, Chord.Type.diminished7, new Note[] {
				new Note(0), new Note(3), new Note(6), new Note(9) });
		expected.simplify();
		assertEquals("Chord Cdim7", value, expected);

	}

	/**
	 * Test misc getters to chord fields
	 */
	public void testGetters() {
		Note middleC = new Note();

		// C augmented 7th
		Chord chord = Chord.buildAugmented7thChord(middleC);

		assertEquals("getDominant", middleC, chord.getDominant());
		assertEquals("getType", Chord.Type.augmented7, chord.getType());
		assertTrue("hasAlteration", chord.hasAlteration());
		Note[] notes = chord.getNotes();
		assertEquals("getNotes lenght", 4, notes.length);

		// F Major 7th
		Note middleF = new Note(5);
		chord = Chord.buildMajor7thChord(middleF);
		assertEquals("getDominant", middleF, chord.getDominant());
		assertEquals("getType", Chord.Type.major7, chord.getType());
		assertFalse("hasAlteration", chord.hasAlteration());

	}

	/**
	 * 
	 */
	public void testParse() {
		Note middleC = new Note();

		Chord chord;

		// MAJOR
		chord = Chord.buildChord(Chord.Type.major, middleC);
		assertEquals("Parsed C", chord, Chord.parse("C"));
		assertEquals("Parsed CM", chord, Chord.parse("CM"));
		assertEquals("Parsed Cma", chord, Chord.parse("Cma"));
		assertEquals("Parsed Cmaj", chord, Chord.parse("Cmaj"));

		// MINOR
		chord = Chord.buildChord(Chord.Type.minor, middleC);
		assertEquals("Parsed Cm", chord, Chord.parse("Cm"));
		assertEquals("Parsed Cmi", chord, Chord.parse("Cmi"));
		assertEquals("Parsed Cmin", chord, Chord.parse("Cmin"));

		// AUGMENTED
		chord = Chord.buildChord(Chord.Type.augmented, middleC);
		assertEquals("Parsed C+", chord, Chord.parse("C+"));
		assertEquals("Parsed Caug", chord, Chord.parse("Caug"));

		// DIMINISHED
		chord = Chord.buildChord(Chord.Type.diminished, middleC);
		assertEquals("Parsed C°", chord, Chord.parse("C°"));
		assertEquals("Parsed Cdim", chord, Chord.parse("Cdim"));

		// DOMINANT 7th
		chord = Chord.buildChord(Chord.Type.dominant7, middleC);
		assertEquals("Parsed C7", chord, Chord.parse("C7"));

		// MAJOR 7th
		chord = Chord.buildChord(Chord.Type.major7, middleC);
		assertEquals("Parsed CM7", chord, Chord.parse("CM7"));
		assertEquals("Parsed Cmaj7", chord, Chord.parse("Cmaj7"));

		// MINOR 7th
		chord = Chord.buildChord(Chord.Type.minor7, middleC);
		assertEquals("Parsed Cm7", chord, Chord.parse("Cm7"));
		assertEquals("Parsed Cmin7", chord, Chord.parse("Cmin7"));

		// AUGMENTED 7th
		chord = Chord.buildChord(Chord.Type.augmented7, middleC);
		assertEquals("Parsed C+7", chord, Chord.parse("C+7"));
		assertEquals("Parsed Caug7", chord, Chord.parse("Caug7"));

		// DIMINISHED 7th
		chord = Chord.buildChord(Chord.Type.diminished7, middleC);
		assertEquals("Parsed C°7", chord, Chord.parse("C°7"));
		assertEquals("Parsed Cdim7", chord, Chord.parse("Cdim7"));

		try {
			Chord.parse("C0");
			fail("An exception should have been thrown");
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * Test that the display string is parsed correctly
	 */
	public void testDisplayStringParseCoherence() {
		Note middleC = new Note();

		Chord chord, parsed;

		chord = Chord.buildChord(Chord.Type.major, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.minor, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.augmented, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.diminished, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.dominant7, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.major7, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.minor7, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.augmented7, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);

		chord = Chord.buildChord(Chord.Type.diminished7, middleC);
		parsed = Chord.parse(chord.toDisplayString());
		assertEquals("DisplayString parsed " + chord.toString(), chord, parsed);
	}
}
