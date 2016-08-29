package fr.xgouchet.franck.core.utils;

import fr.xgouchet.franck.core.model.Note;

/**
 * 
 * Class used to generate a frequency for a given Note
 * 
 * @author Xavier Gouchet
 * 
 */
public class FrequencyFactory {

	/** The equal temperament constant factor (~ 1,059463...) */
	private static final double EQUAL_FACTOR = Math.pow(2, (1.0 / 12.0));
	/** The Reference frequency for equal temperament (A4, which is 440.0 Hz) */
	private static final double A4_FREQ = 440.0;

	/** The Reference frequency for Just intonation (C4, which is 264.0 Hz) */
	private static final double C4_FREQ = 264.0;

	/** the just intonation ratios */
	private static final double[] JUST_INTONATION_RATIOS = new double[] {
			//
			1.0, //
			16.0 / 15.0, //
			9.0 / 8.0, //
			6.0 / 5.0, //
			5.0 / 4.0, //
			4.0 / 3.0, //
			7.0 / 5.0, //
			3.0 / 2.0, //
			8.0 / 5.0, //
			5.0 / 3.0, //
			9.0 / 5.0, //
			15.0 / 8.0, //
			2.0, //
	};

	/** the Pythagorean scale ratios */
	private static final double[] PYTHAGOREAN_RATIOS = new double[] { 1.0,
			256.0 / 243.0, 9.0 / 8.0, 32.0 / 27.0, 81.0 / 64.0, 4.0 / 3.0,
			729.0 / 512.0, 3.0 / 2.0, 128.0 / 81.0, 27.0 / 16.0, 16.0 / 9.0,
			243.0 / 128.0, 2.0 };

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
	public static double getEqualTemperredFrequency(Note note) {

		int halfTonesFromA4 = note.getHalfTones() - 9;

		return A4_FREQ * Math.pow(EQUAL_FACTOR, halfTonesFromA4);
	}

	/**
	 * The just intonation note frequency is found using fractions with small
	 * numbers (eg : <sup>3</sup>/<sub>2</sub> for a perfect 5<sup>th</sup>,
	 * exactly 1.5, instead of 1.49830 in equal temperament).
	 * 
	 * @return the current note's frequency (in hertz)
	 */
	public static double getJustIntonationFrequency(Note note) {

		// get natural c frequency in the note's octave
		double naturalCFreq = C4_FREQ * Math.pow(2, note.getOctave() - 4);

		// get the half tones between a C and the note (in the same octave)
		int halfTones = note.getPitch().getHalfTones()
				+ note.getAccidental().getHalfTones();

		//
		while (halfTones < 0) {
			naturalCFreq /= 2;
			halfTones += 12;
		}

		return naturalCFreq * JUST_INTONATION_RATIOS[halfTones];
	}

	/**
	 * The pythagorean scale note frequency is found using fractions using high
	 * numbers (eg : <sup>243</sup>/<sub>128</sub> for a major 7<sup>th</sup>,
	 * instead of <sup>15</sup>/<sub>8</sub> in just intonation).
	 * 
	 * @return the current note's frequency (in hertz)
	 */
	public static double getPythagoreanFrequency(Note note) {

		// get natural c frequency in the note's octave
		double naturalCFreq = C4_FREQ * Math.pow(2, note.getOctave() - 4);

		// get the half tones between a C and the note (in the same octave)
		int halfTones = note.getPitch().getHalfTones()
				+ note.getAccidental().getHalfTones();

		//
		while (halfTones < 0) {
			naturalCFreq /= 2;
			halfTones += 12;
		}

		return naturalCFreq * PYTHAGOREAN_RATIOS[halfTones];
	}

}
