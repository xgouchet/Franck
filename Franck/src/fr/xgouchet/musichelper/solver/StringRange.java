package fr.xgouchet.musichelper.solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.xgouchet.musichelper.model.Combo;
import fr.xgouchet.musichelper.model.Note;

/**
 * A string range is used to solve a chord generation, by setting the possible
 * frets used on a given string
 * 
 * @author Xavier Gouchet
 * 
 */
public class StringRange implements Iterable<Combo> {

	/**
	 * An iterator to loop through each combo created by this string range
	 * 
	 */
	private class StringComboIterator implements Iterator<Combo> {

		private final Iterator<Integer> mIterator;

		/**
		 * 
		 */
		public StringComboIterator() {
			mIterator = mRange.iterator();
		}

		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return mIterator.hasNext();
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Combo next() {
			int fret = mIterator.next().intValue();
			return new Combo(mStringIndex, fret, getNote(fret));
		}

		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public static final int MAX_FRET = 15;

	/**
	 * Builds a default range where all frets are enabled
	 */
	public StringRange(Note base, int index) {
		mBase = base;
		mRange = new ArrayList<Integer>(MAX_FRET);
		mStringIndex = index;
	}

	/**
	 * Limit the range to the given notes
	 * 
	 * @param notes
	 *            the list of notes this string should be doing
	 */
	public void limitNotes(Note[] notes) {
		Note temp;

		for (int i = 0; i <= MAX_FRET; ++i) {

			temp = getNote(i);

			for (Note note : notes) {
				if (note.isEquivalent(temp)) {
					mRange.add(i);
				}
			}
		}
		mRange.add(-1);
	}

	/**
	 * @return the number of options on this string, including not playing it
	 */
	public int getOptionsCount() {
		return mRange.size() + 1;
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Combo> iterator() {
		return new StringComboIterator();
	}

	/**
	 * @param fret
	 * @return
	 */
	private Note getNote(int fret) {
		if (fret < 0) {
			return null;
		} else {
			return new Note(mBase.getHalfTones() + fret);
		}
	}

	private final int mStringIndex;
	private final Note mBase;
	private final List<Integer> mRange;
}
