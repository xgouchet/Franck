package fr.xgouchet.franck.core.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Not;

import static org.assertj.core.api.Assertions.assertThat;

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

                // Severall Halftones variations
                .overridingErrorMessage("Note(int, int) // 0th octave").isEqualTo(new Note(48, 0))
                .overridingErrorMessage("Note(int, int) // 3rd octave").isEqualTo(new Note(12, 3))
                .overridingErrorMessage("Note(int, int) // 5th octave").isEqualTo(new Note(-12, 5))
                .overridingErrorMessage("Note(int, int) // 8th octave").isEqualTo(new Note(-48, 8));

        System.out.println("Hello world");
    }

    @Test
    public void should_construct_note_from_negative_halftones_and_octave() {
        Note note = new Note(-1, 4);
        assertThat(note).isEqualTo(new Note(Pitch.B, Accidental.natural, 3));
    }

    @Test
    public void should_construct_note_from_large_negative_halftones_and_octave() {
        Note note = new Note(-13, 5);
        assertThat(note).isEqualTo(new Note(Pitch.B, Accidental.natural, 3));
    }


    @Test
    public void should_construct_note_from_large_halftones_and_octave() {
        Note note = new Note(31, 2);
        assertThat(note).isEqualTo(new Note(Pitch.G, Accidental.natural, 4));
    }

    @Test
    public void should_simplify() {
        // E♯
        assertThat(new Note(Pitch.E, Accidental.sharp, 4).simplify())
                .isEqualTo(new Note(Pitch.F, Accidental.natural, 4));
        // F♭
        assertThat(new Note(Pitch.F, Accidental.flat, 4).simplify())
                .isEqualTo(new Note(Pitch.E, Accidental.natural, 4));
        // G♯♯
        assertThat(new Note(Pitch.G, Accidental.doubleSharp, 4).simplify())
                .isEqualTo(new Note(Pitch.A, Accidental.natural, 4));

    }
}
