package fr.xgouchet.franck.core.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.xgouchet.franck.asserts.core.NoteAssert.assertThat;

public class NoteTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void should_allow_different_constructors_middleC() {

        Note middleC = new Note(Pitch.C, Accidental.natural, 4);

        // Test All constructor
        assertThat(middleC)
                .overridingErrorMessage("Note()").isEqualTo(new Note())
                .overridingErrorMessage("Note()").isEqualTo(new Note())
                .overridingErrorMessage("Note(int)").isEqualTo(new Note(0))
                .overridingErrorMessage("Note(int, int)").isEqualTo(new Note(0, 4))
                .overridingErrorMessage("Note(Pitch, Accidental)").isEqualTo(new Note(Pitch.C, Accidental.natural))
                .overridingErrorMessage("Note(Pitch, Accidental, int)").isEqualTo(new Note(Pitch.C, Accidental.natural, 4))

                // Severall semiTones variations
                .overridingErrorMessage("Note(int, int) // 0th octave").isEqualTo(new Note(48, 0))
                .overridingErrorMessage("Note(int, int) // 3rd octave").isEqualTo(new Note(12, 3))
                .overridingErrorMessage("Note(int, int) // 5th octave").isEqualTo(new Note(-12, 5))
                .overridingErrorMessage("Note(int, int) // 8th octave").isEqualTo(new Note(-48, 8));

        System.out.println("Hello world");
    }

    @Test
    public void should_construct_note_from_negative_halftones_and_octave() {
        Note note = new Note(-1, 4);
        assertThat(note)
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.natural)
                .hasOctave(3);
    }

    @Test
    public void should_construct_note_from_large_negative_halftones_and_octave() {
        Note note = new Note(-13, 5);
        assertThat(note)
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.natural)
                .hasOctave(3);
    }


    @Test
    public void should_construct_note_from_large_halftones_and_octave() {
        Note note = new Note(31, 2);
        assertThat(note)
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
    }

    @Test
    public void should_construct_note_from_halftones_and_negative_octave() {
        Note note = new Note(2, -2);
        assertThat(note)
                .hasPitch(Pitch.D)
                .hasAccidental(Accidental.natural)
                .hasOctave(-2);
    }

    @Test
    public void should_simplify() {
        // E♯
        assertThat(new Note(Pitch.E, Accidental.sharp, 4).simplify())
                .hasPitch(Pitch.F)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        // F♭
        assertThat(new Note(Pitch.F, Accidental.flat, 4).simplify())
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
        // G♯♯
        assertThat(new Note(Pitch.G, Accidental.doubleSharp, 4).simplify())
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        // c♭♭
        assertThat(new Note(Pitch.C, Accidental.doubleFlat, 4).simplify())
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.flat)
                .hasOctave(3);

        // No simplification : C
        assertThat(new Note(Pitch.G, Accidental.natural, 4).simplify())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
    }

    @Test
    public void should_detect_altered() {
        // D
        assertThat(new Note(Pitch.D, Accidental.natural, 4))
                .isAltered(false);

        // F♯
        assertThat(new Note(Pitch.F, Accidental.sharp, 4))
                .isAltered(true);

        // D♯♯
        assertThat(new Note(Pitch.F, Accidental.doubleSharp, 4))
                .isAltered(true);

        // A♭
        assertThat(new Note(Pitch.A, Accidental.flat, 4))
                .isAltered(true);

        // G♭♭
        assertThat(new Note(Pitch.G, Accidental.doubleFlat, 4))
                .isAltered(true);
    }

    @Test
    public void should_detect_equivalent() {
        // E♯ == F
        assertThat(new Note(Pitch.E, Accidental.sharp, 1))
                .isEquivalent(new Note(Pitch.F, Accidental.natural, 4));

        // A♯♯ == B
        assertThat(new Note(Pitch.A, Accidental.doubleSharp, 4))
                .isEquivalent(new Note(Pitch.B, Accidental.natural, 1));
    }

    @Test
    public void should_detect_not_equivalent() {
        // E♯ == F
        assertThat(new Note(Pitch.E, Accidental.sharp, 1))
                .isNotEquivalent(new Note(Pitch.G, Accidental.natural, 4));

        // A♯♯ == B
        assertThat(new Note(Pitch.A, Accidental.doubleSharp, 4))
                .isNotEquivalent(new Note(Pitch.C, Accidental.natural, 1));

        // Nothing is equivalent to null
        assertThat(new Note(Pitch.C, Accidental.doubleFlat, 3))
                .isNotEquivalent(null);
    }

    // TODO isSame, getHalfTones, getNoteName
    // TODO ? equals, hashcode
}
