package fr.xgouchet.musicgeneration.source;

public interface SoundSource {

	/**
	 * Computes the value of the sound source at a given time
	 * 
	 * @param time
	 *            the current time (in milliseconds)
	 * @return the value of the sound
	 */
	double getValue(double time);

	/**
	 * @return the duration of this sound source (in seconds)
	 */
	double getDuration();

}