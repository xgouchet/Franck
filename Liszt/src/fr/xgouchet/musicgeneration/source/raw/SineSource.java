package fr.xgouchet.musicgeneration.source.raw;

import fr.xgouchet.musicgeneration.model.Note;
import fr.xgouchet.musicgeneration.source.SoundSource;

/**
 * A standard sine wave source
 */
public class SineSource implements SoundSource {

	private final double mPeriod;
	private final double mFrequency;
	private final double mDuration;

	public SineSource(final Note note, final double duration) {
		this(note.getEqualTemperredFrequency(), duration);
	}

	public SineSource(final double frequency, final double duration) {
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

		return Math.sin(phase);
	}
}
