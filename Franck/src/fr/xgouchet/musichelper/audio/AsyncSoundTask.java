package fr.xgouchet.musichelper.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.util.Log;
import fr.xgouchet.musichelper.model.Chord;

public class AsyncSoundTask extends AsyncTask<Void, Void, Void> {

	public static enum Type {
		chord, arpeggio
	}

	private AudioTrack mTrack;
	private int mBufferSize, mSamplingRate;
	private short mSamples[];

	private final Chord mChord;
	private final Type mType;

	/**
	 * 
	 * @param chord
	 * @param type
	 */
	public AsyncSoundTask(Chord chord, Type type) {
		mChord = chord;
		mType = type;
	}

	/**
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		mSamplingRate = 44100;
		mBufferSize = AudioTrack.getMinBufferSize(mSamplingRate,
				AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);

		mTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mSamplingRate,
				AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
				mBufferSize, AudioTrack.MODE_STREAM);
		mTrack.play();
	}

	/**
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {
		switch (mType) {
		case chord:
			playChord();
			break;
		default:
			break;
		}

		return null;
	}

	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		mTrack.stop();
		mTrack.release();
		super.onPostExecute(result);
	}

	/**
	 * 
	 */
	private void playChord() {
		//
		double duration = 2;
		int neededSamples = (int) (mSamplingRate * duration);
		int playedSamples = 0, remaining;

		mSamples = new short[mBufferSize];

		// parametric
		double twopi = Math.PI * 2;
		int amplitude = 7500;

		int notes = mChord.getNotes().length;
		double[] phases = new double[notes];
		double[] frequencies = new double[notes];

		for (int n = 0; n < notes; ++n) {
			frequencies[n] = mChord.getNotes()[n].getFrequency();
			phases[n] = n;
			Log.i("Sample", frequencies[n] + "Hz");
		}

		while (playedSamples < neededSamples) {
			remaining = neededSamples - playedSamples;
			remaining = Math.min(remaining, mBufferSize);

			for (int i = 0; i < remaining; i++) {
				mSamples[i] = 0;
				for (int n = 0; n < notes; ++n) {
					mSamples[i] += (short) amplitude
							* ((Math.sin(phases[n]) > 0) ? 1 : -1);

					phases[n] += twopi * frequencies[n] / mSamplingRate;

				}

			}

			playedSamples += remaining;

			mTrack.write(mSamples, 0, remaining);
		}

	}
}
