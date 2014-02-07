package fr.xgouchet.music.core.model;

import fr.xgouchet.music.core.utils.Symbols;

/**
 * Describes the different accidentals a base {@link Pitch} can have
 * 
 * @author Xavier Gouchet
 */
public enum Accidental {

	/** */
	natural(0),
	/** */
	sharp(1),
	/** */
	flat(-1),
	/** */
	doubleSharp(2),
	/** */
	doubleFlat(-2);

	/** The common names of accidentals */
	public static final String[] COMMON_NAMES = new String[] { "%s", "%s#",
			"%s♭", "%s##", "%s♭♭" };

	private final int mHalfTones;

	/**
	 * 
	 */
	private Accidental(int halfTones) {
		mHalfTones = halfTones;
	}

	/**
	 * @return the half tones added by this accidental
	 */
	public int getHalfTones() {
		return mHalfTones;
	}

	/**
	 * @param halfTones
	 *            the number of half tones alteration (must be between -2 and 2)
	 * @return the alteration corresponding to the given number of half tones
	 */
	public static Accidental fromHalfTones(final int halfTones) {

		switch (halfTones) {
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

	public String getSymbol(boolean showNatural) {
		StringBuilder builder = new StringBuilder(2);

		switch (this) {
		case natural:
			if (showNatural) {
				builder.append(Symbols.NATURAL_SIGN);
			}
			break;
		case doubleFlat:
			builder.append(Symbols.FLAT_SIGN);
			builder.append(Symbols.FLAT_SIGN);
			break;
		case doubleSharp:
			builder.append(Symbols.SHARP_SIGN);
			builder.append(Symbols.SHARP_SIGN);
			break;
		case flat:
			builder.append(Symbols.FLAT_SIGN);
			break;
		case sharp:
			builder.append(Symbols.SHARP_SIGN);
			break;
		default:
			break;

		}

		return builder.toString();
	}
}