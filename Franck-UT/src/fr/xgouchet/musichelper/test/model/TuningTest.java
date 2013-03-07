package fr.xgouchet.musichelper.test.model;

import junit.framework.TestCase;
import fr.xgouchet.musichelper.model.Accidental;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Pitch;
import fr.xgouchet.musichelper.model.Tuning;

public class TuningTest extends TestCase {

	public void testGeneratedTunings() {
		Tuning expected, generated;

		// Standard tuning
		expected = new Tuning(new Note(Pitch.E, Accidental.natural, 2),
				new Note(Pitch.A, Accidental.natural, 2), //
				new Note(Pitch.D, Accidental.natural, 3), //
				new Note(Pitch.G, Accidental.natural, 3), //
				new Note(Pitch.B, Accidental.natural, 3), //
				new Note(Pitch.E, Accidental.natural, 4)); //
		generated = Tuning.standardGuitarTuning();
		assertEquals("Standard Tuning : " + expected.toString(), expected,
				generated);
	}

}
