package fr.xgouchet.franck.core.model;

/**
 * Describes the 7 different base pitches known in music
 * 
 * @author Xavier Gouchet
 */
public enum Pitch {

	/** */
	C(0, "Do"),
	/** */
	D(2, "Re"),
	/** */
	E(4, "Mi"),
	/** */
	F(5, "Fa"),
	/** */
	G(7, "Sol"),
	/** */
	A(9, "La"),
	/** */
	B(11, "Si");

	/** The number of half tones between this pitch and the natural C */
	private final int mHalfTones;

	/** The neolatin name of the pitch */
	private final String mNeoLatinName;

	private Pitch(int halfTones, String neoLatinName) {
		mHalfTones = halfTones;
		mNeoLatinName = neoLatinName;
	}

	/**
	 * @return the halftones count from a natural C to this pitch (assuming they
	 *         are in the same octave)
	 */
	public int getHalfTones() {
		return mHalfTones;
	}

	/**
	 * @return the name of the current pitch in the given notation
	 */
	public String getPitchName(boolean neolatinName) {
		if (neolatinName) {
			return mNeoLatinName;
		} else {
			return name();
		}
	}

}