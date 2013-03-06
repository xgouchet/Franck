package fr.xgouchet.musichelper.model;

import java.util.LinkedList;
import java.util.List;

public class GuitarChord {

	public static final int MAX_FRET = 20;

	public GuitarChord(final Chord chord, final Tuning tuning) {
		mCombos = new Combo[tuning.getStringsCount()];
		mTuning = tuning;

		mChord = generateGuitarChords(chord);
	}

	private Chord generateGuitarChords(final Chord chord) {

		return null;
	}

	private List<Combo> listCombos(final Note note) {
		List<Combo> combos = new LinkedList<Combo>();

		Note fretNote, baseNote;
		// search the given note on all strings
		for (int string = 0; string < mTuning.getStringsCount(); string++) {
			baseNote = mTuning.getNote(string);

			// search the given note on all frets
			for (int fret = 0; fret < MAX_FRET; ++fret) {
				fretNote = new Note(baseNote.getHalfTones() + fret);
				if (fretNote.isEquivalent(note)) {
					combos.add(new Combo(string, fret, fretNote));
				}
			}
		}

		return combos;
	}

	private final Combo[] mCombos;
	private final Tuning mTuning;
	private final Chord mChord;
}
