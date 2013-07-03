package fr.xgouchet.musicgeneration.task;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import fr.xgouchet.musicgeneration.source.SoundSource;

/**
 * An async task playing several sound sources at the same time.
 * 
 * Some sources are real source, generating sound, other are filters or effects
 * using other sources as input
 * 
 * @author Xavier Gouchet
 */
public class AsyncSoundPlayer extends AsyncTask<SoundSource, Void, Void> {

	/**
	 * The Sound Quality.
	 * 
	 * @author Xavier Gouchet
	 */
	public enum Quality {

		/** The lowest. */
		lowest,
		/** The low. */
		low,
		/** The medium. */
		medium,
		/** The high. */
		high,
		/** The highest. */
		highest
	}

	/** The Maximum sound Amplitude */
	private static final double MAX_AMPLITUDE = 7000.0;

	/** Size of the buffer sent to the Audio track. */
	private final int mBufferSize;

	/** Sampling rate of the sound signal. */
	private final int mSamplingRate;

	/** Duration (in ms) of a sample. */
	private final double mSampleDuration;

	/** The samples buffer */
	private final short mSamples[];

	/** the Audio track linked to this task. */
	private AudioTrack mTrack;

	/**
	 * Creates a sound player.
	 * 
	 * @return the async sound player
	 */
	public static AsyncSoundPlayer createAsyncSoundPlayer() {
		return new AsyncSoundPlayer(44100);
	}

	/**
	 * Creates a sound player.
	 * 
	 * @param quality
	 *            the quality of the sound sampling
	 * @return the async sound player
	 */
	public static AsyncSoundPlayer createAsyncSoundPlayer(final Quality quality) {

		int samplingRate;
		switch (quality) {
		case highest:
			samplingRate = 96000;
			break;
		case high:
			samplingRate = 48000;
			break;
		case low:
			samplingRate = 16000;
			break;
		case lowest:
			samplingRate = 8000;
			break;
		case medium:
		default:
			samplingRate = 32000;
			break;
		}

		return new AsyncSoundPlayer(samplingRate);
	}

	/**
	 * Instantiates a new async sound player.
	 * 
	 * @param samplingRate
	 *            the sound sampling rate (default is 44100)
	 */
	public AsyncSoundPlayer(final int samplingRate) {
		mSamplingRate = samplingRate;

		mSampleDuration = 1000.0 / mSamplingRate;

		mBufferSize = AudioTrack.getMinBufferSize(mSamplingRate,
				AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
		mSamples = new short[mBufferSize];
	}

	/**
	 * On pre execute.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {

		mTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mSamplingRate,
				AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
				mBufferSize, AudioTrack.MODE_STREAM);
		mTrack.play();
	}

	/**
	 * Plays the given SoundSource instance simultaneously
	 * 
	 * @param sources
	 *            The sound sources to play
	 * @return
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(final SoundSource... sources) {
		int neededSamples = (int) (mSamplingRate * getDuration(sources));
		int playedSamples = 0, remaining;
		double value, time = 0;

		while (playedSamples < neededSamples) {
			remaining = neededSamples - playedSamples;
			remaining = Math.min(remaining, mBufferSize);

			for (int i = 0; i < remaining; i++) {
				time += mSampleDuration;
				value = 0;
				//
				for (SoundSource source : sources) {
					value += source.getValue(time);
				}

				value /= sources.length;
				mSamples[i] = (short) (value * MAX_AMPLITUDE);
			}

			playedSamples += remaining;
			mTrack.write(mSamples, 0, remaining);
		}

		return null;
	}

	/**
	 * On post execute.
	 * 
	 * @param result
	 *            the result
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(final Void result) {
		mTrack.stop();
		mTrack.release();
		super.onPostExecute(result);
	}

	/**
	 * Computes the total duration of the sounds to play.
	 * 
	 * @param params
	 *            the list of sound sources
	 * @return the sound duration in seconds
	 */
	private double getDuration(final SoundSource... params) {
		double max = 0.0;

		for (SoundSource source : params) {
			double duration = source.getDuration();
			if (duration > max) {
				max = duration;
			}
		}
		return max;
	}
}
