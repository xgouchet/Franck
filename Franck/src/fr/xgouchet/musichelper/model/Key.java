package fr.xgouchet.musichelper.model;

/**
 * Defines the different possible keys on a staff
 */
public enum Key {
	treble, bass, alto;

	public int cOffset() {
		switch (this) {
		case treble:
			return -2;
		case bass:
			return 3;
		case alto:
			return 4;
		default:
			throw new IllegalArgumentException();
		}

	}
}
