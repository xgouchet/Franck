package fr.xgouchet.musichelper.test.model;

import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.GuitarChord;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Tuning;
import junit.framework.TestCase;

public class GuitarChordTest extends TestCase {

	public void testStandardChords() {
		GuitarChord guitar = new GuitarChord(Chord.parse("C"),
				Tuning.standardGuitarTuning());

	}
}
