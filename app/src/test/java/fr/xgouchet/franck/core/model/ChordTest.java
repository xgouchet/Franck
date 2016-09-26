package fr.xgouchet.franck.core.model;

import org.junit.Test;

import static fr.xgouchet.franck.asserts.core.NoteAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Xavier Gouchet
 */
public class ChordTest {

    @Test
    public void should_simplify_chord_full() {
        // Given
        Note note0 = mock(Note.class);
        Note note1 = mock(Note.class);
        Note note2 = mock(Note.class);
        Note note3 = mock(Note.class);
        Chord chord = new Chord(note0, note1, note2, note3);

        // when
        chord.simplify(true);

        // Then
        verify(note0).simplify(true);
        verify(note1).simplify(true);
        verify(note2).simplify(true);
        verify(note3).simplify(true);
    }

    @Test
    public void should_simplify_chord() {
        // Given
        Note note0 = mock(Note.class);
        Note note1 = mock(Note.class);
        Note note2 = mock(Note.class);
        Note note3 = mock(Note.class);
        Chord chord = new Chord(note0, note1, note2, note3);

        // when
        chord.simplify(false);

        // Then
        verify(note0).simplify(false);
        verify(note1).simplify(false);
        verify(note2).simplify(false);
        verify(note3).simplify(false);
    }

    @Test
    public void should_create_immutable_chord() {
        Chord c = new Chord(new Note(Pitch.C, Accidental.natural, 4));

        // modify the array
        Note[] notes = c.getNotes();
        notes[0] = new Note(Pitch.F, Accidental.doubleSharp, 8);

        // get the notes again
        assertThat(c.getNotes()[0])
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
    }


}