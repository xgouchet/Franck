package fr.xgouchet.musichelper.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fima.cardsui.objects.Card;

import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.ui.view.PianoView;

public class PianoCard extends Card {

	/**
	 * @param chord
	 */
	public PianoCard(final Chord chord) {
		mChord = chord;
	}

	/**
	 * @see com.fima.cardsui.objects.Card#getCardContent(android.content.Context)
	 */
	@Override
	public View getCardContent(final Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_piano,
				null);

		mPianoView = (PianoView) view.findViewById(R.id.pianoView);
		mPianoView.setChord(mChord);

		return view;
	}

	private final Chord mChord;
	private PianoView mPianoView;
}
