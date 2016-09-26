package fr.xgouchet.franck.core.model;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import static fr.xgouchet.franck.core.model.Accidental.doubleFlat;
import static fr.xgouchet.franck.core.model.Accidental.doubleSharp;
import static fr.xgouchet.franck.core.model.Accidental.flat;
import static fr.xgouchet.franck.core.model.Accidental.natural;
import static fr.xgouchet.franck.core.model.Accidental.sharp;
import static fr.xgouchet.franck.core.model.Pitch.B;
import static fr.xgouchet.franck.core.model.Pitch.C;

/**
 * An immutable class representing a Note, including a Pitch (eg : C, G, A), an
 * accidental alteration (eg : flat, sharp, ...) and an octave
 *
 * @author Xavier Gouchet
 */
public class Note {

    private static final int SEMI_TONES_PER_OCTAVE = 12;
    public static final int MIDDLE_OCTAVE = 4;

    @NonNull
    private final Pitch pitch;
    @NonNull
    private final Accidental accidental;
    @IntRange(from = 0, to = 16)
    private final int octave;

    /**
     * Constructs a Middle C (natural C on the 4<sup>th</sup> octave)
     */
    public Note() {
        this(0);
    }

    /**
     * Constructs a note by the number of half tones from a natural C on the
     * 4<sup>th</sup> octave (ie : middle C)
     *
     * @param semiTones the number of half tones from a middle C
     */
    public Note(int semiTones) {
        this(semiTones, MIDDLE_OCTAVE);
    }

    /**
     * Constructs a note by the number of half tones from a natural C, at the
     * given octave
     *
     * @param semiTones the number of half tones from a natural C
     * @param octave    the octave to use as reference
     */
    public Note(int semiTones, int octave) {
        int localSemiTones = semiTones % SEMI_TONES_PER_OCTAVE;
        if (localSemiTones < 0) {
            localSemiTones += SEMI_TONES_PER_OCTAVE;
        }
        int octaveOffset = (semiTones - localSemiTones) / SEMI_TONES_PER_OCTAVE;

        switch (localSemiTones) {
            case 0:
            case 1:
                pitch = C;
                break;
            case 2:
                pitch = Pitch.D;
                break;
            case 3:
            case 4:
                pitch = Pitch.E;
                break;
            case 5:
            case 6:
                pitch = Pitch.F;
                break;
            case 7:
            case 8:
                pitch = Pitch.G;
                break;
            case 9:
                pitch = Pitch.A;
                break;
            case 10:
            case 11:
                pitch = B;
                break;
            default:
                throw new IllegalStateException();
        }

        accidental = Accidental.fromSemiTones(localSemiTones - pitch.getSemiTones());

        this.octave = octave + octaveOffset;
    }

    /**
     * Constructs a note by the given pitch and accidental alteration, at the
     * 4<sup>th</sup> octave
     *
     * @param pitch      the pitch (C, D, E, ...)
     * @param accidental the accidental (♮, ♯, ♭, ...)
     */
    public Note(@NonNull Pitch pitch, @NonNull Accidental accidental) {
        this(pitch, accidental, MIDDLE_OCTAVE);
    }

    /**
     * Constructs a note by the given pitch and accidental alteration, at the
     * given octave
     *
     * @param pitch      the pitch (C, D, E, ...)
     * @param accidental the accidental (♮, ♯, ♭, ...)
     * @param octave     the octabe (0 .. 16)
     */
    public Note(@NonNull Pitch pitch, @NonNull Accidental accidental, int octave) {
        this.pitch = pitch;
        this.accidental = accidental;
        this.octave = octave;
    }

    /**
     * Constructs a note identical to the given one
     *
     * @param note the note to copy
     */
    public Note(Note note) {
        this(note.getPitch(), note.getAccidental(), note.getOctave());
    }


    /**
     * @return the pitch (C, D, E, ...)
     */
    @NonNull public Pitch getPitch() {
        return pitch;
    }

    /**
     * @return the accidental (♮, ♯, ♭, ...)
     */
    @NonNull public Accidental getAccidental() {
        return accidental;
    }

    /**
     * @return the octave
     */
    public int getOctave() {
        return octave;
    }

    /**
     * @return the number of semi tones from a Middle C to this note
     */
    public int getSemiTones() {
        int semiTones = 0;

        // half tones from natural pitch to this note
        semiTones += accidental.getSemiTones();

        // half tones from natural C to natural pitch
        semiTones += pitch.getSemiTones();

        // half tones from middle C to natural C in same octave
        semiTones += (octave - MIDDLE_OCTAVE) * SEMI_TONES_PER_OCTAVE;

        return semiTones;
    }

    /**
     * @return if the current note should display an alteration
     */
    public boolean isAltered() {
        return (!accidental.equals(natural));
    }

    /**
     * Verify that the given note is equivalent to this one (ie : they both have
     * the same pitch, even on different octaves).
     * <p/>
     * For example checks that a B# on the 2nd octave is equivalent to a middle
     * C.
     *
     * @param other the note to check
     * @return if the given note is equivalent
     */
    public boolean isSimilar(@NonNull Note other) {
        int diff = getSemiTones() - other.getSemiTones();
        return (diff % SEMI_TONES_PER_OCTAVE) == 0;
    }

    /**
     * Verify that the given note is the same as this one (ie : they both resolve to the same simplified note).
     * <p/>
     * For example checks that a E# on the 3rd octave is equivalent to a F on the 3rd octave.
     *
     * @param other the note to check
     * @return if the given note is the same
     */
    public boolean isEquivalent(@NonNull Note other) {
        return getSemiTones() == other.getSemiTones();
    }

    /**
     * @param full if true, will try to simplify unusual alterations (B♯ → C, F♭ → E, …)
     * @return a new Note with a simplified representation of the current note
     * (eg : G♯♯ -> A, D♭♭ -> C)
     */
    @NonNull public Note simplify(boolean full) {
        switch (accidental) {
            case doubleFlat:
            case doubleSharp:
                return new Note(getSemiTones());
            default:
                break;
        }

        if (full) {
            switch (pitch) {
                case E:
                case B:
                case C:
                case F:
                    return new Note(getSemiTones());
                default:
                    break;
            }
        }

        return new Note(this);
    }


    /**
     * @return the same note at a lower octave (eg : C4 -> C3)
     */
    @NonNull public Note lowerOctave() {
        return new Note(pitch, accidental, octave - 1);
    }

    /**
     * @return the same note at a lower octave (eg : C4 -> C5)
     */
    @NonNull public Note higherOctave() {
        return new Note(pitch, accidental, octave + 1);
    }

    /**
     * @return the augmented note based on this dominant (eg : C -> C#)
     */
    @NonNull public Note augmented() {
        switch (accidental) {
            case sharp:
                return new Note(pitch, doubleSharp, octave);
            case natural:
                return new Note(pitch, sharp, octave);
            case flat:
                return new Note(pitch, natural,
                        octave);
            case doubleFlat:
                return new Note(pitch, flat, octave);
            case doubleSharp:
                return new Note(getSemiTones() + 1);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * @return the diminished note based on this dominant (eg : C -> B)
     */
    @NonNull public Note diminished() {
        switch (accidental) {
            case doubleSharp:
                return new Note(pitch, sharp, octave);
            case sharp:
                return new Note(pitch, natural,
                        octave);
            case natural:
                return new Note(pitch, flat, octave);
            case flat:
                return new Note(pitch, doubleFlat,
                        octave);
            case doubleFlat:
                return new Note(getSemiTones() - 1);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * @return the minor 3<sup>rd</sup> note based on this dominant (eg : C ->
     * Eb)
     */
    @NonNull public Note second() {
        return new Note(getSemiTones() + 2);
    }

    /**
     * @return the minor 3<sup>rd</sup> note based on this dominant (eg : C ->
     * Eb)
     */
    @NonNull public Note minorThird() {
        return new Note(getSemiTones() + 4).diminished().simplify(false);
    }

    /**
     * @return the major 3<sup>rd</sup> note based on this dominant (eg : C ->
     * E)
     */
    @NonNull public Note majorThird() {
        return new Note(getSemiTones() + 3).augmented().simplify(false);
    }

    /**
     * @return the 4<sup>th</sup> note based on this dominant (eg : C -> F)
     */
    @NonNull public Note fourth() {
        return new Note(getSemiTones() + 5);
    }

    /**
     * @return the perfect 5<sup>th</sup> note based on this dominant (eg : C ->
     * G)
     */
    @NonNull public Note perfectFifth() {
        return new Note(getSemiTones() + 7);
    }

    /**
     * @return the minor 6<sup>th</sup> note based on this dominant (eg : C ->
     * Ab)
     */
    @NonNull public Note minorSixth() {
        return new Note(getSemiTones() + 9).diminished().simplify(false);
    }

    /**
     * @return the major 6<sup>th</sup> note based on this dominant (eg : C ->
     * A)
     */
    @NonNull public Note majorSixth() {
        return new Note(getSemiTones() + 8).augmented().simplify(false);
    }

    /**
     * @return the minor 7<sup>th</sup> note based on this dominant (eg : C ->
     * Bb)
     */
    @NonNull public Note minorSeventh() {
        return new Note(getSemiTones() + 11).diminished().simplify(false);
    }

    /**
     * @return the major 7<sup>th</sup> note based on this dominant (eg : C ->
     * B)
     */
    @NonNull public Note majorSeventh() {
        return new Note(getSemiTones() + 10).augmented().simplify(false);
    }


    @Override
    public boolean equals(Object other) {
        // check for self-comparison
        if (this == other) {
            return true;
        }

        // check for type / null
        if (!(other instanceof Note)) {
            return false;
        }

        // check fields
        Note that = (Note) other;

        return (this.getPitch() == that.getPitch())
                && (this.getAccidental() == that.getAccidental())
                && (this.getOctave() == that.getOctave());
    }

    @Override
    public int hashCode() {
        return getSemiTones();
    }

    @Override
    public String toString() {
        return pitch.name() + " " + accidental.name() + " (" + octave + ")";
    }
}
