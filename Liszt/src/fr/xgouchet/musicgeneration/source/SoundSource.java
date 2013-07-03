package fr.xgouchet.musicgeneration.source;

/**
 * A {@link SoundSource} is an entity able to output a sound signal, to use as
 * sound on a device
 * 
 * @author Xavier Gouchet
 */
public interface SoundSource {

	/**
	 * Computes the value of the sound source at a given time (this is the level
	 * of the signal, in a [-1;1] range)
	 * 
	 * @param time
	 *            the current time (in milliseconds)
	 * @return the value of the sound
	 */
	double getValue(double time);

	/**
	 * Gets the duration.
	 * 
	 * @return the duration of this sound source (in seconds)
	 */
	double getDuration();

}