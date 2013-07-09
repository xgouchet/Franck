package fr.xgouchet.musicgeneration.source.filters;

import fr.xgouchet.musicgeneration.source.SoundSource;

public class FadeOut implements SoundSource {

	/** Fade out duration in ms */
	private final double mDuration;
	/** Total duration in ms */
	private final double mTotal;
	/** SoundSource to fade out */
	private final SoundSource mSource;

	public FadeOut(final SoundSource source) {
		this(source, 1000);
	}

	/** Fade out duration in ms */
	public FadeOut(final SoundSource source, final double duration) {
		mSource = source;
		mDuration = duration;
		mTotal = mSource.getDuration();
	}

	@Override
	public double getDuration() {

		return mSource.getDuration();
	}

	@Override
	public double getValue(final double time) {
		double value = mSource.getValue(time);

		if (time > mTotal) {
			return 0;
		}

		if (time > (mTotal - mDuration)) {
			double t = time - (mTotal - mDuration);

			value = value * ((mDuration - t) / mDuration);
		}

		return value;
	}
}
