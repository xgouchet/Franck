package fr.xgouchet.musichelper.model;

public enum Pitch {
	C, D, E, F, G, A, B;

	private static final int[] NATURAL_HALFTONES = new int[] { 0, 2, 4, 5, 7,
		9, 11 };

	/**
	 * @return the halftones from a natural C to this unaltered pitch
	 */
	public int halfTones() {
		return NATURAL_HALFTONES[ordinal()];
	}

	/**
	 * @return the nearest whole pitch to the given half tones (from a natural
	 *         C)
	 */
	public static Pitch nearestPitch(final int halfTones) {
		int index, half;

		half = halfTones % 12;

		for (index = 0; index < NATURAL_HALFTONES.length; index++) {
			if (NATURAL_HALFTONES[index] >= half) {
				break;
			}
		}

		index = index % NATURAL_HALFTONES.length;

		return values()[index];

	}
}
