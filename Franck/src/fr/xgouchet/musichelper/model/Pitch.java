package fr.xgouchet.musichelper.model;

public enum Pitch {
	C, D, E, F, G, A, B;

	private static final int[] NATURAL_HALFTONES = new int[] { 0, 2, 4, 5, 7,
		9, 11 };
	private static final Pitch[] NEAREST_PITCH = new Pitch[] { C, C, D, E, E,
		F, F, G, G, A, B, B };

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
		int half;

		half = halfTones % NEAREST_PITCH.length;

		return NEAREST_PITCH[half];

	}
}
