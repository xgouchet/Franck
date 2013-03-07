package fr.xgouchet.musichelper.model;

/**
 * Defines the different possible keys on a staff
 *
 * @author Xavier Gouchet
 */
public enum Key {
	treble, bass, alto;

	/**
	 * @return the offset from the 5th line of the staff to the middle C (C 4)
	 */
	public int c4Offset() {
		switch (this) {
		case treble:
			return -2;
		case bass:
			return 10;
		case alto:
			return 4;
		default:
			throw new IllegalArgumentException();
		}

	}
}
