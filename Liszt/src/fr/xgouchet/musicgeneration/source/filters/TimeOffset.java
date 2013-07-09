package fr.xgouchet.musicgeneration.source.filters;

import fr.xgouchet.musicgeneration.source.SoundSource;

public class TimeOffset implements SoundSource {

	/** Offset in duration in ms */
	private final double mOffset;
	private final SoundSource mSource;

	public TimeOffset(final SoundSource source) {
		this(source, 1000);
	}

	/** Offset in duration in ms */
	public TimeOffset(final SoundSource source, final double offset) {
		mSource = source;
		mOffset = offset;
	}

	@Override
	public double getDuration() {
		return mSource.getDuration() + mOffset;
	}

	@Override
	public double getValue(final double time) {
		if (time < mOffset) {
			return 0;
		}
		
		return mSource.getValue(time - mOffset);
	}
}
