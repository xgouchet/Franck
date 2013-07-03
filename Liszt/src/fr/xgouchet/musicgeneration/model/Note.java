package fr.xgouchet.musicgeneration.model;

/**
 * <p>
 * An immutable class representing a Note, including a Pitch (ie : the "name" of
 * the note), an accidental alteration (flat, sharp, ...) and an octave
 * </p>
 * 
 * @author Xavier Gouchet
 */
public class Note {

	/** The equal temperament constant factor (~ 1,059463...) */
	private static final double EQUAL_FACTOR = Math.pow(2, (1.0 / 12.0));
	/** The Reference frequency for equal temperament (A4, which is 440.0 Hz) */
	private static final double A4_FREQ = 440.0;

	/** The Reference frequency for Just intonation (C4, which is 264.0 Hz) */
	private static final double C4_FREQ = 264.0;
	/** the just intonation ratios */
	private static final double[] JUST_INTONATION_RATIOS = new double[] { 1.0,
			16.0 / 15.0, 9.0 / 8.0, 6.0 / 5.0, 5.0 / 4.0, 4.0 / 3.0, 7.0 / 5.0,
			3.0 / 2.0, 8.0 / 5.0, 5.0 / 3.0, 9.0 / 5.0, 15.0 / 8.0, 2.0 };
	/** the Pythagorean scale ratios */
	private static final double[] PYTHAGOREAN_RATIOS = new double[] { 1.0,
			256.0 / 243.0, 9.0 / 8.0, 32.0 / 27.0, 81.0 / 64.0, 4.0 / 3.0,
			729.0 / 512.0, 3.0 / 2.0, 128.0 / 81.0, 27.0 / 16.0, 16.0 / 9.0,
			243.0 / 128.0, 2.0 };

	/** the base pitch of the note (eg : C, E, B, ...) */
	private final Pitch mPitch;
	/** the accidental (eg: flat, sharp, natural, ...) */
	private final Accidental mAccidental;
	/** the octave this note is played on (default is 4<sup>th</sup> ) */
	private final int mOctave;

	/**
	 * Constructs a Middle C (natural C on the 4<sup>th</sup> octave)
	 */
	public Note() {
		this(0);
	}

	/**
	 * Constructs a note by the number of semitones from a natural C on the
	 * 4<sup>th</sup> octave (ie : middle C)
	 * 
	 * @param semitones
	 *            the number of semitones from a middle C
	 */
	public Note(final int semitones) {
		this(semitones, 4);
	}

	/**
	 * Constructs a note by the number of semitones from a natural C, at the
	 * given octave
	 * 
	 * @param semitones
	 *            the number of semitones from a natural C
	 * @param octave
	 */
	public Note(final int semitones, final int octave) {
		int octaveOffset = ((semitones - (semitones % 12)) / 12);
		int st = semitones - (octaveOffset * 12);

		while (st < 0) {
			st += 12;
			octaveOffset -= 1;
		}

		switch (semitones) {
		case 0:
		case 1:
			mPitch = Pitch.C;
			break;
		case 2:
			mPitch = Pitch.D;
			break;
		case 3:
		case 4:
			mPitch = Pitch.E;
			break;
		case 5:
		case 6:
			mPitch = Pitch.F;
			break;
		case 7:
		case 8:
			mPitch = Pitch.G;
			break;
		case 9:
			mPitch = Pitch.A;
			break;
		case 10:
		case 11:
			mPitch = Pitch.B;
			break;
		default:
			throw new IllegalStateException();
		}

		mAccidental = Accidental.fromSemitones(st - mPitch.getSemiTones());

		mOctave = octave + octaveOffset;
	}

	/**
	 * Constructs a note by the given pitch and accidental alteration, at the
	 * 4<sup>th</sup> octave
	 * 
	 * @param pitch
	 * @param accidental
	 */
	public Note(final Pitch pitch, final Accidental accidental) {
		this(pitch, accidental, 4);
	}

	/**
	 * Constructs a note by the given pitch and accidental alteration, at the
	 * given octave
	 * 
	 * @param pitch
	 * @param accidental
	 * @param octave
	 */
	public Note(final Pitch pitch, final Accidental accidental, final int octave) {
		mPitch = pitch;
		mAccidental = accidental;
		mOctave = octave;
	}

	/**
	 * @return the pitch
	 */
	public Pitch getPitch() {
		return mPitch;
	}

	/**
	 * @return the accidental
	 */
	public Accidental getAccidental() {
		return mAccidental;
	}

	/**
	 * @return the octave
	 */
	public int getOctave() {
		return mOctave;
	}

	/**
	 * @return the number of semitones from a Middle C to this note
	 */
	public int getSemiTones() {
		int semitones = 0;

		// semitones from natural pitch to this note
		semitones += mAccidental.getSemiTones();

		// semitones from natural C to natural pitch
		semitones += mPitch.getSemiTones();

		// semitones from middle C to natural C in same octave
		semitones += (mOctave - 4) * 12;

		return semitones;
	}

	/**
	 * <p>
	 * The frequency of a note is given by the formula (in the equally tempered
	 * scale) : f<sub>n</sub> = f<sub>0</sub> * a<sup>n</sup>
	 * </p>
	 * 
	 * <dl>
	 * <dt>f<sub>0</sub></dt>
	 * <dd>the frequency of a reference note</dd>
	 * <dt>a</dt>
	 * <dd>the constant factor for an equal tempered scale =
	 * 2<sup><small>1/12</small></sup></ds>
	 * <dt>n</dt>
	 * <dd>the number of semitones between the reference note and the current
	 * one</dd>
	 * </dl>
	 * 
	 * <p>
	 * Here, we compute the frequency based on the natural A on the 4th octave,
	 * with a 440.0Hz frequency.
	 * </p>
	 * 
	 * @return the current note's frequency (in hertz)
	 */
	public double getEqualTemperredFrequency() {
		int semitonesFromA4 = getSemiTones() - 9;

		return A4_FREQ * Math.pow(EQUAL_FACTOR, semitonesFromA4);
	}

	/**
	 * The just intonation note frequency is found using fractions with small
	 * numbers (eg : <sup>3</sup>/<sub>2</sub> for a perfect 5<sup>th</sup>,
	 * exactly 1.5, instead of 1.49830 in equal temperament).
	 * 
	 * @return the current note's frequency (in hertz)
	 */
	public double getJustIntonationFrequency() {

		double naturalCFreq = C4_FREQ * Math.pow(2, mOctave - 4);

		int semitones = mPitch.getSemiTones() + mAccidental.getSemiTones();

		while (semitones < 0) {
			naturalCFreq /= 2;
			semitones += 12;
		}

		return naturalCFreq * JUST_INTONATION_RATIOS[semitones];
	}

	/**
	 * The pythagorean scale note frequency is found using fractions using high
	 * numbers (eg : <sup>243</sup>/<sub>128</sub> for a major 7<sup>th</sup>,
	 * instead of <sup>15</sup>/<sub>8</sub> in just intonation).
	 * 
	 * @return the current note's frequency (in hertz)
	 */
	public double getPythagoreanFrequency() {

		double naturalCFreq = C4_FREQ * Math.pow(2, mOctave - 4);

		int semitones = mPitch.getSemiTones() + mAccidental.getSemiTones();

		while (semitones < 0) {
			naturalCFreq /= 2;
			semitones += 12;
		}

		return naturalCFreq * PYTHAGOREAN_RATIOS[semitones];
	}

	/**
	 * @param notation
	 *            the notation system to use
	 * @return the note name in the given notation
	 */
	public String getNoteName(final Notation notation) {
		return String.format(mAccidental.getAccidentalFormat(notation),
				mPitch.getPitchName(notation));
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d)", mPitch.toString(),
				mAccidental.toString(), mOctave);
	}

	@Override
	public boolean equals(final Object other) {
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

		return (this.getSemiTones() == that.getSemiTones());
	}

	@Override
	public int hashCode() {
		return getSemiTones();
	}
}
