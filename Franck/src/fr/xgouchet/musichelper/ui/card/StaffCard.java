package fr.xgouchet.musichelper.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fima.cardsui.objects.Card;

import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.ui.view.StaffView;

public class StaffCard extends Card {

	private final Chord mChord;
	private StaffView mStaffView;

	/**
	 * @param chord
	 */
	public StaffCard(final Chord chord) {
		mChord = chord;
	}

	/**
	 * @see com.fima.cardsui.objects.Card#getCardContent(android.content.Context)
	 */
	@Override
	public View getCardContent(final Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_staff,
				null);

		mStaffView = (StaffView) view.findViewById(R.id.staffView);
		mStaffView.setChord(mChord);

		return view;
	}

}
