package fr.xgouchet.musichelper.ui.card;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fima.cardsui.objects.Card;

import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Combo;
import fr.xgouchet.musichelper.model.Tuning;
import fr.xgouchet.musichelper.solver.AsyncGuitarSolver;
import fr.xgouchet.musichelper.solver.AsyncGuitarSolver.SolverListener;

public class GuitarCard extends Card implements SolverListener {

	private final Chord mChord;

	/**
	 * @param chord
	 */
	public GuitarCard(final Chord chord) {
		mChord = chord;
	}

	/**
	 * @see com.fima.cardsui.objects.Card#getCardContent(android.content.Context)
	 */
	@Override
	public View getCardContent(final Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_guitar,
				null);

		generateGuitarChords();
		return view;
	}

	/**
	 * @param chord
	 */
	private void generateGuitarChords() {

		AsyncGuitarSolver solver = new AsyncGuitarSolver();
		solver.setTuning(Tuning.standardGuitarTuning());
		solver.setChord(mChord);
		solver.setSolverListener(this);

		solver.execute();

	}

	@Override
	public void onSolved(final List<List<Combo>> allCombos) {

	}
}
