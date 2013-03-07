package fr.xgouchet.musichelper.model;

/**
 * @author Xavier Gouchet
 */
public enum Accidental {
	natural, sharp, flat, doubleSharp, doubleFlat;

	private static final int[] ACCIDENTAL_HALFTONES = new int[] { 0, 1, -1, 2,
			-2 };

	/**
	 * @return the added (positive or negative) halftones from this accidental
	 *         alteration
	 */
	public int halfTones() {
		return ACCIDENTAL_HALFTONES[ordinal()];
	}

	/**
	 * @param halftones
	 * @return the alteration corresponding to the given number of halftones
	 */
	public static Accidental fromHalfTones(final int halftones) {
		switch (halftones) {
		case 0:
			return natural;
		case 1:
			return sharp;
		case 2:
			return doubleSharp;
		case -1:
			return flat;
		case -2:
			return doubleFlat;
		default:
			throw new IllegalArgumentException("Unknown alteration : "
					+ halftones);
		}
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		switch (this) {
		case natural:
			return "";
		case sharp:
			return "#";
		case doubleSharp:
			return "##";
		case flat:
			return "♭";
		case doubleFlat:
			return "♭♭";
		default:
			throw new IllegalArgumentException("Unknown alteration");
		}
	}
}
