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
            failWithMessage("Expected note's octave to be <%s> but was <%s>",
                    Integer.toString(expected),
                    Integer.toString(actual.getOctave()));
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

    public NoteAssert isSimilarTo(Note expected) {
        isNotNull();

        if (!actual.isSimilar(expected)) {
            failWithMessage("Expected note <%s> to be similar to <%s> but was not",
                    actual,
                    expected);
        }

        return this;
    }

    public NoteAssert isNotSimilarTo(Note expected) {
        isNotNull();

        if (actual.isSimilar(expected)) {
            failWithMessage("Expected note <%s> to not be similar to <%s> but was",
                    actual,
                    expected);
        }

        return this;
    }

    public NoteAssert isEquivalentTo(Note expected) {
        isNotNull();

        if (!actual.isEquivalent(expected)) {
            failWithMessage("Expected note <%s> to be equivalent to <%s> but was not",
                    actual,
                    expected);
        }

        return this;
    }

    public NoteAssert isNotEquivalentTo(Note expected) {
        isNotNull();

        if (actual.isEquivalent(expected)) {
            failWithMessage("Expected note <%s> to not be equivalent to <%s> but was",
                    actual,
                    expected);
        }

        return this;
    }
}
