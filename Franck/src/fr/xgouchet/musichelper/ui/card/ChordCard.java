package fr.xgouchet.musichelper.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.common.Settings;

public class ChordCard extends Card {

	private final Chord mChord;

	/**
	 * @param chord
	 */
	public ChordCard(final Chord chord) {
		mChord = chord;
	}

	/**
	 * @see com.fima.cardsui.objects.Card#getCardContent(android.content.Context)
	 */
	@Override
	public View getCardContent(final Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_chord,
				null);
		((TextView) view.findViewById(android.R.id.title)).setText(mChord
				.getChordFullName(Settings.getNotation()));

		((TextView) view.findViewById(android.R.id.text1)).setText(mChord
				.getChordNotesNames(Settings.getNotation()));

		return view;
	}

}
