package fr.xgouchet.musichelper.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.fima.cardsui.objects.Card;

import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musicgeneration.model.Note;
import fr.xgouchet.musicgeneration.source.SoundSource;
import fr.xgouchet.musicgeneration.source.filters.FadeOut;
import fr.xgouchet.musicgeneration.source.filters.TimeOffset;
import fr.xgouchet.musicgeneration.source.raw.SineSource;
import fr.xgouchet.musicgeneration.task.AsyncSoundPlayer;
import fr.xgouchet.musicgeneration.task.AsyncSoundPlayer.Quality;
import fr.xgouchet.musichelper.R;

public class SoundCard extends Card implements OnClickListener {

	private final Chord mChord;

	/**
	 * @param chord
	 */
	public SoundCard(final Chord chord) {
		mChord = chord;
	}

	/**
	 * @see com.fima.cardsui.objects.Card#getCardContent(android.content.Context)
	 */
	@Override
	public View getCardContent(final Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_sound,
				null);
		view.findViewById(R.id.chordSound).setOnClickListener(this);
		view.findViewById(R.id.arpeggioSound).setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.arpeggioSound:
			playArpeggio();
			break;
		case R.id.chordSound:
			playChord();
			break;
		}
	}

	private void playChord() {
		Note[] notes = mChord.getNotes();
		SoundSource sources[] = new SoundSource[notes.length];

		for (int n = 0; n < notes.length; ++n) {
			sources[n] = new TimeOffset(new FadeOut(new SineSource(
					notes[n].getEqualTemperredFrequency(), 1500)), (n * 25));
		}

		AsyncSoundPlayer.createAsyncSoundPlayer(Quality.high).execute(sources);
	}

	private void playArpeggio() {
		Note[] notes = mChord.getNotes();
		SoundSource sources[] = new SoundSource[notes.length];

		for (int n = 0; n < notes.length; ++n) {
			sources[n] = new TimeOffset(new FadeOut(new SineSource(
					notes[n].getEqualTemperredFrequency(), 1500)), (n * 750));
		}

		AsyncSoundPlayer.createAsyncSoundPlayer(Quality.high).execute(sources);
	}

}
