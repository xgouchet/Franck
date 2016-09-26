package fr.xgouchet.franck.core.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.xgouchet.franck.asserts.core.NoteAssert.assertThat;
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
                .overridingErrorMessage("Note(nOTE)").isEqualTo(new Note(middleC))

                // Severall semiTones variations
                .overridingErrorMessage("Note(int, int) // 0th octave").isEqualTo(new Note(48, 0))
                .overridingErrorMessage("Note(int, int) // 3rd octave").isEqualTo(new Note(12, 3))
                .overridingErrorMessage("Note(int, int) // 5th octave").isEqualTo(new Note(-12, 5))
                .overridingErrorMessage("Note(int, int) // 8th octave").isEqualTo(new Note(-48, 8))

                // equal to self
                .overridingErrorMessage("this").isEqualTo(middleC);

        assertThat(middleC)
                .isNotEqualTo(null)
                .isNotEqualTo("middle C")
                .isNotEqualTo(middleC.toString());

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
    public void should_detect_similar() {

        // C4 ~= C6
        final Note c6 = new Note(Pitch.C, Accidental.natural, 6);
        final Note c4 = new Note(Pitch.C, Accidental.natural, 4);
        assertThat(c6)
                .isSimilarTo(c4)
                .isNotEqualTo(c4);
        assertThat(c6.hashCode()).isNotEqualTo(c4.hashCode());

        // E♯ ~= F
        final Note eSharp1 = new Note(Pitch.E, Accidental.sharp, 1);
        final Note f4 = new Note(Pitch.F, Accidental.natural, 4);
        assertThat(eSharp1)
                .isSimilarTo(f4)
                .isNotEqualTo(f4);
        assertThat(eSharp1.hashCode()).isNotEqualTo(f4.hashCode());

        // A♯♯ ~= B
        final Note aDoubleSharp3 = new Note(Pitch.A, Accidental.doubleSharp, 3);
        final Note b1 = new Note(Pitch.B, Accidental.natural, 1);
        assertThat(aDoubleSharp3)
                .isSimilarTo(b1)
                .isNotEqualTo(b1);
        assertThat(aDoubleSharp3.hashCode()).isNotEqualTo(b1.hashCode());

        // G♭ ~= G♭
        final Note g2 = new Note(Pitch.G, Accidental.flat, 2);
        final Note g5 = new Note(Pitch.G, Accidental.flat, 5);
        assertThat(g2)
                .isSimilarTo(g5)
                .isNotEqualTo(g5);
        assertThat(g2.hashCode()).isNotEqualTo(g5.hashCode());
    }

    @Test
    public void should_detect_not_similar() {
        // E♯ == F
        final Note eSharp1 = new Note(Pitch.E, Accidental.sharp, 1);
        final Note g4 = new Note(Pitch.G, Accidental.natural, 4);
        assertThat(eSharp1)
                .isNotSimilarTo(g4)
                .isNotEqualTo(g4);
        assertThat(eSharp1.hashCode()).isNotSameAs(g4.hashCode());

        // A♯♯ == B
        final Note aDoubleSharp4 = new Note(Pitch.A, Accidental.doubleSharp, 4);
        final Note c1 = new Note(Pitch.C, Accidental.natural, 1);
        assertThat(aDoubleSharp4)
                .isNotSimilarTo(c1)
                .isNotEqualTo(c1);
        assertThat(aDoubleSharp4.hashCode()).isNotSameAs(c1.hashCode());
    }

    @Test
    public void should_detect_equivalent() {

        // F♯ ~= G♭
        final Note fSharp1 = new Note(Pitch.F, Accidental.sharp, 1);
        final Note gFlat1 = new Note(Pitch.G, Accidental.flat, 1);
        assertThat(fSharp1)
                .isEquivalentTo(gFlat1)
                .isNotEqualTo(gFlat1);
        assertThat(fSharp1.hashCode()).isEqualTo(gFlat1.hashCode());

        // B♯♯ == C♯
        final Note bDoubleSharp4 = new Note(Pitch.B, Accidental.doubleSharp, 4);
        final Note cSharp5 = new Note(Pitch.C, Accidental.sharp, 5);
        assertThat(bDoubleSharp4)
                .isEquivalentTo(cSharp5)
                .isNotEqualTo(cSharp5);
        assertThat(bDoubleSharp4.hashCode()).isEqualTo(cSharp5.hashCode());

        // E♭♭ == D
        final Note eDoubleFlat6 = new Note(Pitch.E, Accidental.doubleFlat, 6);
        final Note d6 = new Note(Pitch.D, Accidental.natural, 6);
        assertThat(eDoubleFlat6)
                .isEquivalentTo(d6)
                .isNotEqualTo(d6);
        assertThat(eDoubleFlat6.hashCode()).isEqualTo(d6.hashCode());

        // A♭ == A♭
        final Note aFlat2 = new Note(Pitch.A, Accidental.flat, 2);
        final Note aFlat2_bis = new Note(Pitch.A, Accidental.flat, 2);
        assertThat(aFlat2)
                .isEquivalentTo(aFlat2_bis)
                .isEquivalentTo(aFlat2_bis);
        assertThat(aFlat2.hashCode()).isEqualTo(aFlat2_bis.hashCode());
    }

    @Test
    public void should_detect_not_equivalent() {
        // different octave
        final Note fSharp1 = new Note(Pitch.F, Accidental.sharp, 1);
        final Note gFlat3 = new Note(Pitch.G, Accidental.flat, 3);
        assertThat(fSharp1)
                .isNotEquivalentTo(gFlat3)
                .isNotEqualTo(gFlat3);
        assertThat(fSharp1.hashCode()).isNotEqualTo(gFlat3.hashCode());

        // not even similar
        final Note bDoubleSharp4 = new Note(Pitch.B, Accidental.doubleSharp, 4);
        final Note eSharp5 = new Note(Pitch.E, Accidental.sharp, 5);
        assertThat(bDoubleSharp4)
                .isNotEquivalentTo(eSharp5)
                .isNotEqualTo(eSharp5);
        assertThat(bDoubleSharp4.hashCode()).isNotEqualTo(eSharp5);
    }

    @Test
    public void should_clone() {
        // D♯
        Note dSharp3 = new Note(Pitch.D, Accidental.sharp, 3);
        Note dSharp3_clone = new Note(dSharp3);
        assertThat(dSharp3)
                .isEqualTo(dSharp3_clone)
                .isNotSameAs(dSharp3_clone);

        // D♯
        Note fFlat6 = new Note(Pitch.F, Accidental.flat, 6);
        Note fFlat6_clone = new Note(fFlat6);
        assertThat(fFlat6)
                .isEqualTo(fFlat6_clone)
                .isNotSameAs(fFlat6_clone);
    }

    @Test
    public void should_simplify_basic() {
        // E♯
        final Note eSharp4 = new Note(Pitch.E, Accidental.sharp, 4);
        assertThat(eSharp4.simplify(false))
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.sharp)
                .hasOctave(4)
                .isEquivalentTo(eSharp4);

        // F♭
        final Note fFlat4 = new Note(Pitch.F, Accidental.flat, 4);
        assertThat(fFlat4.simplify(false))
                .hasPitch(Pitch.F)
                .hasAccidental(Accidental.flat)
                .hasOctave(4)
                .isEquivalentTo(fFlat4);

        // G♯♯
        final Note gDoubleSharp4 = new Note(Pitch.G, Accidental.doubleSharp, 4);
        assertThat(gDoubleSharp4.simplify(false))
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.natural)
                .hasOctave(4)
                .isEquivalentTo(gDoubleSharp4);

        // c♭♭
        final Note cDoubleFlat4 = new Note(Pitch.C, Accidental.doubleFlat, 4);
        assertThat(cDoubleFlat4.simplify(false))
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.flat)
                .hasOctave(3)
                .isEquivalentTo(cDoubleFlat4);

        // No simplification : C
        final Note g4 = new Note(Pitch.G, Accidental.natural, 4);
        assertThat(g4.simplify(false))
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4)
                .isEquivalentTo(g4);
    }

    @Test
    public void should_simplify_full() {
        // E♯
        assertThat(new Note(Pitch.E, Accidental.sharp, 4).simplify(true))
                .hasPitch(Pitch.F)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        // F♭
        assertThat(new Note(Pitch.F, Accidental.flat, 4).simplify(true))
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
        // G♯♯
        assertThat(new Note(Pitch.G, Accidental.doubleSharp, 4).simplify(true))
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        // c♭♭
        assertThat(new Note(Pitch.C, Accidental.doubleFlat, 4).simplify(true))
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.flat)
                .hasOctave(3);

        // No simplification : C
        assertThat(new Note(Pitch.G, Accidental.natural, 4).simplify(true))
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
    }

    @Test
    public void should_generate_lower_octave() {
        assertThat(new Note(Pitch.B, Accidental.flat, 2).lowerOctave())
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.flat)
                .hasOctave(1);

        assertThat(new Note(Pitch.E, Accidental.doubleFlat, 6).lowerOctave())
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.doubleFlat)
                .hasOctave(5);

        assertThat(new Note(Pitch.C, Accidental.natural, 9).lowerOctave())
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.natural)
                .hasOctave(8);

        assertThat(new Note(Pitch.G, Accidental.doubleSharp, 4).lowerOctave())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.doubleSharp)
                .hasOctave(3);
    }

    @Test
    public void should_generate_higher_octave() {
        assertThat(new Note(Pitch.C, Accidental.flat, 2).higherOctave())
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.flat)
                .hasOctave(3);

        assertThat(new Note(Pitch.F, Accidental.doubleFlat, 6).higherOctave())
                .hasPitch(Pitch.F)
                .hasAccidental(Accidental.doubleFlat)
                .hasOctave(7);

        assertThat(new Note(Pitch.D, Accidental.natural, 9).higherOctave())
                .hasPitch(Pitch.D)
                .hasAccidental(Accidental.natural)
                .hasOctave(10);

        assertThat(new Note(Pitch.A, Accidental.doubleSharp, 4).higherOctave())
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.doubleSharp)
                .hasOctave(5);
    }

    @Test
    public void should_generate_augmented() {
        assertThat(new Note(Pitch.D, Accidental.doubleFlat, 5).augmented())
                .hasPitch(Pitch.D)
                .hasAccidental(Accidental.flat)
                .hasOctave(5);

        assertThat(new Note(Pitch.A, Accidental.flat, 2).augmented())
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.natural)
                .hasOctave(2);

        assertThat(new Note(Pitch.C, Accidental.natural, 8).augmented())
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.sharp)
                .hasOctave(8);

        assertThat(new Note(Pitch.G, Accidental.sharp, 4).augmented())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.doubleSharp)
                .hasOctave(4);

        assertThat(new Note(Pitch.E, Accidental.doubleSharp, 4).augmented())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
    }

    @Test
    public void should_generate_diminished() {

        assertThat(new Note(Pitch.D, Accidental.doubleFlat, 5).diminished())
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(new Note(Pitch.A, Accidental.flat, 2).diminished())
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.doubleFlat)
                .hasOctave(2);

        assertThat(new Note(Pitch.C, Accidental.natural, 8).diminished())
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.flat)
                .hasOctave(8);

        assertThat(new Note(Pitch.G, Accidental.sharp, 4).diminished())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(new Note(Pitch.E, Accidental.doubleSharp, 4).diminished())
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.sharp)
                .hasOctave(4);
    }

    @Test
    public void should_build_C_major_scale() {
        final Note c4 = new Note(Pitch.C, Accidental.natural, 4);

        assertThat(c4.second())
                .hasPitch(Pitch.D)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(c4.majorThird())
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(c4.fourth())
                .hasPitch(Pitch.F)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(c4.perfectFifth())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(c4.majorSixth())
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(c4.majorSeventh())
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);
    }


    @Test
    public void should_build_A_minor_scale() {
        final Note a4 = new Note(Pitch.A, Accidental.natural, 4);

        assertThat(a4.second())
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(a4.minorThird())
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.natural)
                .hasOctave(5);

        assertThat(a4.fourth())
                .hasPitch(Pitch.D)
                .hasAccidental(Accidental.natural)
                .hasOctave(5);

        assertThat(a4.perfectFifth())
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.natural)
                .hasOctave(5);

        assertThat(a4.minorSixth())
                .hasPitch(Pitch.F)
                .hasAccidental(Accidental.natural)
                .hasOctave(5);

        assertThat(a4.minorSeventh())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(5);
    }

    @Test
    public void should_build_E_major_scale() {
        final Note e4 = new Note(Pitch.E, Accidental.natural, 4);

        assertThat(e4.second())
                .hasPitch(Pitch.F)
                .hasAccidental(Accidental.sharp)
                .hasOctave(4);

        assertThat(e4.majorThird())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.sharp)
                .hasOctave(4);

        assertThat(e4.fourth())
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(e4.perfectFifth())
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(e4.majorSixth())
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.sharp)
                .hasOctave(5);

        assertThat(e4.majorSeventh())
                .hasPitch(Pitch.D)
                .hasAccidental(Accidental.sharp)
                .hasOctave(5);
    }


    @Test
    public void should_build_F_minor_scale() {
        final Note f4 = new Note(Pitch.F, Accidental.natural, 4);

        assertThat(f4.second())
                .hasPitch(Pitch.G)
                .hasAccidental(Accidental.natural)
                .hasOctave(4);

        assertThat(f4.minorThird())
                .hasPitch(Pitch.A)
                .hasAccidental(Accidental.flat)
                .hasOctave(4);

        assertThat(f4.fourth())
                .hasPitch(Pitch.B)
                .hasAccidental(Accidental.flat)
                .hasOctave(4);

        assertThat(f4.perfectFifth())
                .hasPitch(Pitch.C)
                .hasAccidental(Accidental.natural)
                .hasOctave(5);

        assertThat(f4.minorSixth())
                .hasPitch(Pitch.D)
                .hasAccidental(Accidental.flat)
                .hasOctave(5);

        assertThat(f4.minorSeventh())
                .hasPitch(Pitch.E)
                .hasAccidental(Accidental.flat)
                .hasOctave(5);
    }
}
