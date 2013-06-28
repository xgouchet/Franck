package fr.xgouchet.musicgeneration.source;

/**
 * A standard sine wave source
 */
public class TriangleSource implements SoundSource {

	private final double mDuty;
	private final double mPeriod;
	private final int mFrequency;
	private final double mDuration;

	public TriangleSource(final int frequency, final double duration) {
		this(frequency, duration, 0.5);
	}

	public TriangleSource(final int frequency, final double duration,
			final double duty) {
		mDuty = duty;
		mFrequency = frequency;
		mPeriod = 1000.0 / mFrequency;
		mDuration = duration;
	}

	@Override
	public double getDuration() {
		return mDuration;
	}

	@Override
	public double getValue(final double time) {
		int cycles = (int) (time / mPeriod);
		double phase = ((time - (cycles * mPeriod)) * Math.PI * 2.0) / mPeriod;

		if (phase <= mDuty) {
			return ((2.0 * phase) / mDuty) - 1.0;
		} else {
			return 1.0 - ((2.0 * (phase - mDuty)) / (1.0 - mDuty));
		}
	}
}