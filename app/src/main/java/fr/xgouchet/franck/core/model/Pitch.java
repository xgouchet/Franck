package fr.xgouchet.franck.core.model;

/**
 * Describes the 7 different base pitches known in (occidental) music
 *
 * @author Xavier Gouchet
 */
public enum Pitch {

    C(0),
    D(2),
    E(4),
    F(5),
    G(7),
    A(9),
    B(11);

    /**
     * The number of half tones between this pitch and the natural C
     */
    private final int semiTones;

    Pitch(int semiTones) {
        this.semiTones = semiTones;
    }

    /**
     * @return the semiTones count from a natural C to this pitch (assuming they
     * are in the same octave)
     */
    public int getSemiTones() {
        return semiTones;
    }

}