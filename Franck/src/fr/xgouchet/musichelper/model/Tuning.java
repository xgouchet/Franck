package fr.xgouchet.musichelper.model;

import java.util.Arrays;

public class Tuning {

	public static Tuning standardGuitarTuning() {
		Note[] notes = new Note[6];

		notes[0] = new Note(Pitch.E, Accidental.natural, 2);
		notes[0] = new Note(Pitch.A, Accidental.natural, 2);
		notes[0] = new Note(Pitch.D, Accidental.natural, 3);
		notes[0] = new Note(Pitch.G, Accidental.natural, 3);
		notes[0] = new Note(Pitch.B, Accidental.natural, 3);
		notes[0] = new Note(Pitch.E, Accidental.natural, 4);

		return new Tuning(notes);
	}

	public Tuning(final Note... notes) {
		if (notes == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		mTuning = Arrays.copyOf(notes, notes.length);
	}

	private Note[] mTuning;
}
