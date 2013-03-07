package fr.xgouchet.musichelper.model;

/**
 * <p>
 * A combo (for lack of a better name) is a pair of int, describing a string and
 * fret to produce a Note on a String instrument (eg : a guitar)
 * </p>
 *
 * <p>
 * <em>This element doesn't mean a thing without the linked tuning information .</em>
 * </p>
 * 
 * @author Xavier Gouchet
 */
public class Combo {

	/**
	 * @param string
	 *            the string this combo is on
	 * @param fret
	 *            the fret to press
	 * @param note
	 *            the exact note produced
	 */
	public Combo(final int string, final int fret, final Note note) {
		mString = string;
		mFret = fret;
		mNote = note;
	}

	/**
	 * @return
	 */
	public int getFret() {
		return mFret;
	}

	/**
	 * @return the string
	 */
	public int getString() {
		return mString;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mNote.toDisplayString());
		builder.append(" : Str ");
		builder.append(mString);
		builder.append(", Fret ");
		builder.append(mFret);

		return builder.toString();
	}

	private final int mString, mFret;
	private final Note mNote;
}