package fr.xgouchet.musichelper.ui.card;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.fima.cardsui.objects.Card;

import fr.xgouchet.musichelper.AsyncSoundTask;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Chord;

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
	public void onClick(View v) {
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
		AsyncSoundTask sound = new AsyncSoundTask();

		sound.execute();
	}

	private void playArpeggio() {
		// TODO Auto-generated method stub

	}

}
