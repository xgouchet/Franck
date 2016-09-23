package fr.xgouchet.franck.asserts.core;

import org.assertj.core.api.AbstractAssert;

import fr.xgouchet.franck.core.model.Accidental;
import fr.xgouchet.franck.core.model.Note;
import fr.xgouchet.franck.core.model.Pitch;

/**
 * @author Xavier Gouchet
 */

public class NoteAssert extends AbstractAssert<NoteAssert, Note> {

    public static NoteAssert assertThat(Note actual) {
        return new NoteAssert(actual);
    }

    private NoteAssert(Note actual) {
        super(actual, NoteAssert.class);
    }

    public NoteAssert hasOctave(int expected) {
        isNotNull();

        if (actual.getOctave() != expected) {
            failWithMessage("Expected note's octave to be <%d> but was <%d>",
                    expected,
                    actual.getOctave());
        }

        return this;
    }

    public NoteAssert hasPitch(Pitch expected) {
        isNotNull();

        if (actual.getPitch() != expected) {
            failWithMessage("Expected note's pitch to be <%s> but was <%s>",
                    expected,
                    actual.getPitch());
        }

        return this;
    }

    public NoteAssert hasAccidental(Accidental expected) {
        isNotNull();

        if (actual.getAccidental() != expected) {
            failWithMessage("Expected note's alteration to be <%s> but was <%s>",
                    expected,
                    actual.getAccidental());
        }

        return this;
    }

    public NoteAssert isAltered(boolean expected) {
        isNotNull();

        if (actual.isAltered() != expected) {
            failWithMessage("Expected note's alteration to be <%b> but was <%b>",
                    expected,
                    actual.isAltered());
        }

        return this;
    }

    public NoteAssert isEquivalent(Note expected) {
        isNotNull();

        if (!actual.isEquivalent(expected)) {
            failWithMessage("Expected note to be equivalent to <%s> but was not",
                    expected);
        }

        return this;
    }

    public NoteAssert isNotEquivalent(Note expected) {
        isNotNull();

        if (actual.isEquivalent(expected)) {
            failWithMessage("Expected note to not be equivalent to <%s> but was",
                    expected);
        }

        return this;
    }
}
