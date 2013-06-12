package fr.xgouchet.musichelper;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;

public class AsyncSoundTask extends AsyncTask<Void, Void, Void> {

	private AudioTrack mTrack;
	private int mBufferSize, mSamplingRate;
	private short mSamples[];

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

	@Override
	protected Void doInBackground(Void... params) {
		double duration = 2;
		int neededSamples = (int) (mSamplingRate * duration);
		int playedSamples = 0;

		mSamples = new short[mBufferSize];

		// parametric
		double twopi = Math.PI * 2;
		double frequency = 440.f;
		double phase = 0.0;
		int amplitude = 10000;

		while (playedSamples < neededSamples) {
			for (int i = 0; i < mBufferSize; i++) {
				mSamples[i] = (short) (amplitude * Math.sin(phase));
				phase += twopi * frequency / mSamplingRate;
			}

			playedSamples += mBufferSize;
			

			mTrack.write(mSamples, 0, mSamples.length);
		}

		mTrack.stop();
		mTrack.release();
		return null;
	}
}
