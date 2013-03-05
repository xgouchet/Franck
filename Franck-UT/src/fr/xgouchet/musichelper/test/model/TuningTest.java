package fr.xgouchet.musichelper.test.model;

import java.util.Arrays;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Accidental;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Pitch;
import fr.xgouchet.musichelper.model.Tuning;

public class TuningTest extends TestCase {

	public void testEChords() {
		Note e = new Note(Pitch.E, Accidental.natural);

		int[] expected = new int[] { 0, 2, 2, 1, 0, 0 };
		int[] generated = Tuning.standardGuitarTuning().getFrets(
				Chord.buildMajorChord(e));
		assertTrue("E Chord", Arrays.equals(expected, generated));
	}
}
