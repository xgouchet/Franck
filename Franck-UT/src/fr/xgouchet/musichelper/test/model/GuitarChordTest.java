package fr.xgouchet.musichelper.test.model;

import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Combo;
import fr.xgouchet.musichelper.model.GuitarChord;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Tuning;
import junit.framework.TestCase;

public class GuitarChordTest extends TestCase {

	private static final int X = -1;

	public void testStandardMajorChords() {
		Tuning standard = Tuning.standardGuitarTuning();
		GuitarChord guitar;
		int expected[], actual[];

		// C major on standard guitar
		guitar = new GuitarChord(Chord.parse("C"), standard);
		expected = new int[] { X, 3, 2, 0, 1, 0 };
		actual = guitar.getFingers();
		assertEquals(guitar.toString(), expected, actual);

		// D major on standard guitar
		guitar = new GuitarChord(Chord.parse("D"), standard);
		expected = new int[] { X, X, 0, 2, 3, 2 };
		actual = guitar.getFingers();
		assertEquals(guitar.toString(), expected, actual);

		// E major on standard guitar
		guitar = new GuitarChord(Chord.parse("E"), standard);
		expected = new int[] { 0, 2, 2, 1, 0, 0 };
		actual = guitar.getFingers();
		assertEquals(guitar.toString(), expected, actual);

		// F major on standard guitar
		guitar = new GuitarChord(Chord.parse("F"), standard);
		expected = new int[] { 1, 3, 3, 2, 1, 1 };
		actual = guitar.getFingers();
		assertEquals(guitar.toString(), expected, actual);

		// G major on standard guitar
		guitar = new GuitarChord(Chord.parse("G"), standard);
		expected = new int[] { 3, 2, 0, 0, 0, 3 };
		actual = guitar.getFingers();
		assertEquals(guitar.toString(), expected, actual);

		// A major on standard guitar
		guitar = new GuitarChord(Chord.parse("A"), standard);
		expected = new int[] { X, 0, 2, 2, 2, 0 };
		actual = guitar.getFingers();
		assertEquals(guitar.toString(), expected, actual);

		// B major on standard guitar
		guitar = new GuitarChord(Chord.parse("B"), standard);
		expected = new int[] { X, 2, 4, 4, 4, 2 };
		actual = guitar.getFingers();
		assertEquals(guitar.toString(), expected, actual);

	}
}
