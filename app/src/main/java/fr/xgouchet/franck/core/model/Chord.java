package fr.xgouchet.franck.core.model;

import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * An immutable class representing a Chord, basically a list of Notes together
 *
 * @author Xavier Gouchet
 */
public class Chord {


    @NonNull private final Note[] notes;

    /**
     * @param notes the notes composing this chord. The root note should be the
     *              first given
     */
    public Chord(Note... notes) {
        if (notes.length == 0) {
            throw new IllegalArgumentException(
                    "A chord should have at least one Note in it");
        }

        this.notes = notes;
    }

    /**
     * Simplifies the note used in this chord
     *
     * @param full
     */
    public void simplify(boolean full) {
        for (int i = 0; i < notes.length; ++i) {
            notes[i] = notes[i].simplify(full);
        }
    }

    /**
     * @return the notes in this chord
     */
    @NonNull
    public Note[] getNotes() {
        Note[] res = new Note[notes.length];
        for (int i = 0; i < notes.length; ++i) {
            res[i] = new Note(notes[i]);
        }
        return res;
    }

    @Override
    public boolean equals(final Object other) {
        // check for self-comparison
        if (this == other) {
            return true;
        }

        // check for type
        if (!(other instanceof Chord)) {
            return false;
        }

        // check fields
        Chord that = (Chord) other;
        return Arrays.equals(that.notes, this.notes);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = (37 * hash) + Arrays.hashCode(notes);
        return hash;
    }

    @Override
    public String toString() {
        return Arrays.toString(notes);
    }
}
