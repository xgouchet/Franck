package fr.xgouchet.musicgeneration.source.filters;

import fr.xgouchet.musicgeneration.source.SoundSource;

public class FadeOut implements SoundSource {

	/** Fade in duration in seconds */
	private final double mDuration, mTotal;
	private final SoundSource mSource;

	public FadeOut(final SoundSource source) {
		this(source, 1000);
	}

	public FadeOut(final SoundSource source, final double duration) {
		mSource = source;
		mDuration = duration;
		mTotal = mSource.getDuration() * 1000.0;
	}

	@Override
	public double getDuration() {

		return mSource.getDuration();
	}

	@Override
	public double getValue(final double time) {
		double value = mSource.getValue(time);

		if (time > (mTotal - mDuration)) {
			double t = time - (mTotal - mDuration);

			value = value * ((mDuration - t) / mDuration);
		}

		return value;
	}
}