package fr.xgouchet.musicgeneration.source.raw;

import fr.xgouchet.musicgeneration.source.SoundSource;

/**
 * A standard square signal
 */
public class SquareSource implements SoundSource {

	private final double mPeriod;
	private final int mFrequency;
	private final double mDuration;
	private final double mDuty;

	public SquareSource(final int frequency, final double duration) {
		this(frequency, duration, 0.5);
	}

	public SquareSource(final int frequency, final double duration,
			final double duty) {
		mFrequency = frequency;
		mPeriod = 1000.0 / mFrequency;
		mDuration = duration;
		mDuty = duty;
	}

	@Override
	public double getDuration() {
		return mDuration;
	}

	@Override
	public double getValue(final double time) {
		int cycles = (int) (time / mPeriod);
		double phase = (time - (cycles * mPeriod)) / mPeriod;

		return (phase > mDuty) ? 1 : -1;
	}
}
