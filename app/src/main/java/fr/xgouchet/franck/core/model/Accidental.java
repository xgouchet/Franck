package fr.xgouchet.franck.core.model;


/**
 * Describes the different accidentals a base {@link Pitch} can have
 *
 * @author Xavier Gouchet
 */
public enum Accidental {

    natural(0),
    sharp(1),
    flat(-1),
    doubleSharp(2),
    doubleFlat(-2);

    private final int semiTones;

    Accidental(int semiTones) {
        this.semiTones = semiTones;
    }

    /**
     * @return the semi tones added by this accidental
     */
    public int getSemiTones() {
        return semiTones;
    }

    /**
     * @param semiTones the number of semi tones alteration (must be between -2 and 2)
     * @return the alteration corresponding to the given number of half tones
     */
    public static Accidental fromSemiTones(final int semiTones) {

        switch (semiTones) {
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

}