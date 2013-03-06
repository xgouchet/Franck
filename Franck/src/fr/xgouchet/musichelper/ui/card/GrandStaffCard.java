package fr.xgouchet.musichelper.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fima.cardsui.objects.Card;

import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Key;
import fr.xgouchet.musichelper.ui.view.StaffView;

public class GrandStaffCard extends Card {

	/**
	 * @param chord
	 */
	public GrandStaffCard(final Chord chord) {
		mChord = chord;
	}

	/**
	 * @see com.fima.cardsui.objects.Card#getCardContent(android.content.Context)
	 */
	@Override
	public View getCardContent(final Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.card_grand_staff, null);

		mTrebleStaffView = (StaffView) view.findViewById(R.id.trebleStaffView);
		mTrebleStaffView.setChord(mChord);
		mTrebleStaffView.setKey(Key.treble);

		mBassStaffView = (StaffView) view.findViewById(R.id.bassStaffView);
		mBassStaffView.setChord(Chord.buildChord(mChord.getType(), mChord
				.getDominant().lowerOctave().lowerOctave()));
		mBassStaffView.setKey(Key.bass);

		return view;
	}

	private final Chord mChord;
	private StaffView mTrebleStaffView, mBassStaffView;
}
