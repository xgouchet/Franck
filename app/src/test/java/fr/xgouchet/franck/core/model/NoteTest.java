package fr.xgouchet.franck.core.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEquals() {

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
    }
}
