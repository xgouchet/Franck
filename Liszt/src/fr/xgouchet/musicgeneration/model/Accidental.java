package fr.xgouchet.musicgeneration.model;

/**
 * Describes the different accidentals a base {@link Pitch} can have
 * 
 * @author Xavier Gouchet
 */
public enum Accidental {
	natural, sharp, flat, doubleSharp, doubleFlat;

	/** The common names of accidentals */
	public static final String[] COMMON_NAMES = new String[] { "%s", "%s#",
			"%s♭", "%s##", "%s♭♭" };

	/** The byzantine names of accidentals */
	public static final String[] BYZANTINE_NAMES = new String[] { "%s",
			"%s diesis", "%s hyphesis", "%s diesis diesis",
			"%s hyphesis hyphesis" };

	/** The japanese names of accidentals */
	public static final String[] JAPANESE_NAMES = new String[] { "%s", "Ei-%s",
			"Hen-%s", "Ei-Ei-%s", "Hen-Hen-%s" };

	/** the semitones each accidental add to a base pitch */
	private static final int[] ACCIDENTAL_SEMITONES = new int[] { 0, 1, -1, 2,
			-2 };

	/**
	 * @return the semitones added by this accidental
	 */
	public int getSemiTones() {
		return ACCIDENTAL_SEMITONES[ordinal()];
	}

	/**
	 * @param semitones
	 *            the number of semitones alteration (must be between -2 and 2)
	 * @return the alteration corresponding to the given number of semitones
	 */
	public static Accidental fromSemitones(final int semitones) {

		switch (semitones) {
		case 0:
			return natural;
		case 1:
			return sharp;
		case -1:
			return flat;
		case 2:
			return doubleSharp;
		case -2:
			return doubleFlat;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @param notation
	 *            the notation system to use
	 * @return the name of the current accidental in the given notation
	 */
	public String getAccidentalFormat(final Notation notation) {
		switch (notation) {
		case byzantine:
			return BYZANTINE_NAMES[ordinal()];
		case japanese:
			return JAPANESE_NAMES[ordinal()];
		case english:
		case neolatin:
		default:
			return COMMON_NAMES[ordinal()];
		}
	}
}
