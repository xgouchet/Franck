package fr.xgouchet.musicgeneration.source.filters;

import fr.xgouchet.musicgeneration.source.SoundSource;

public class FadeIn implements SoundSource {

	/** Fade in duration in ms */
	private final double mDuration;
	private final SoundSource mSource;

	public FadeIn(final SoundSource source) {
		this(source, 1000);
	}

	/** Fade in duration in ms */
	public FadeIn(final SoundSource source, final double duration) {
		mSource = source;
		mDuration = duration;
	}

	@Override
	public double getDuration() {
		return mSource.getDuration();
	}

	@Override
	public double getValue(final double time) {
		double value = mSource.getValue(time);

		if (time < 0) {
			return 0;
		}

		if (time < mDuration) {
			value = (value * time) / mDuration;
		}

		return value;
	}
}
