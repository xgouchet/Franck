package fr.xgouchet.musicgeneration.model;

/**
 * Describes the 7 different base pitches known in music
 * 
 * @author Xavier Gouchet
 */
public enum Pitch {

	C, D, E, F, G, A, B;

	/** The neo-latin names of pitches */
	public static final String[] NEOLATIN_NAMES = new String[] { "Do", "Re",
			"Mi", "Fa", "Sol", "La", "Si" };

	/** The byzantine names of pitches */
	public static final String[] BYZANTINE_NAMES = new String[] { "Ni", "Pa",
			"Vu", "Ga", "Di", "Ke", "Zo" };

	/** The japanese names of pitches */
	public static final String[] JAPANESE_NAMES = new String[] { "Ha", "Ni",
			"Ho", "He", "To", "I", "Ro" };

	/** The natural semitones from a natural C */
	private static final int[] NATURAL_SEMITONES = new int[] { 0, 2, 4, 5, 7,
			9, 11 };

	/**
	 * @return the semitones count from a natural C to this pitch
	 */
	public int getSemiTones() {
		return NATURAL_SEMITONES[ordinal()];
	}

	/**
	 * @param notation
	 *            the notation system to use
	 * @return the name of the current pitch in the given notation
	 */
	public String getPitchName(final Notation notation) {
		switch (notation) {
		case neolatin:
			return NEOLATIN_NAMES[ordinal()];
		case japanese:
			return JAPANESE_NAMES[ordinal()];
		case byzantine:
			return BYZANTINE_NAMES[ordinal()];
		default:
			return name();
		}
	}

}
