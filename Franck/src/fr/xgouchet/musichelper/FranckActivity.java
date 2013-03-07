package fr.xgouchet.musichelper;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;
import fr.xgouchet.musichelper.common.Settings;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.GuitarChord;
import fr.xgouchet.musichelper.model.Key;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Tuning;
import fr.xgouchet.musichelper.ui.card.GrandStaffCard;
import fr.xgouchet.musichelper.ui.card.PianoCard;
import fr.xgouchet.musichelper.ui.card.StaffCard;

/**
 *
 *
 * @author Xavier Gouchet
 */
public class FranckActivity extends Activity implements OnQueryTextListener {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Card view
		mCardView = (CardUI) findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);

		// Default values
		generateTabs();
		setChordType(Chord.Type.major);
		setDominant(new Note());

		GuitarChord test = new GuitarChord(Chord.parse("C"),
				Tuning.standardGuitarTuning());
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.chords, menu);

		if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
			mSearchView = new SearchView(getActionBar().getThemedContext());
		} else {
			mSearchView = new SearchView(this);
		}
		mSearchView.setQueryHint(getString(R.string.menu_search_chord));
		mSearchView.setOnQueryTextListener(this);

		menu.findItem(R.id.menu_search_chord).setActionView(mSearchView);

		return true;
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		boolean res = true;

		switch (item.getItemId()) {
		case R.id.menu_major:
			setChordType(Chord.Type.major);
			break;
		case R.id.menu_minor:
			setChordType(Chord.Type.minor);
			break;
		case R.id.menu_augmented:
			setChordType(Chord.Type.augmented);
			break;
		case R.id.menu_diminished:
			setChordType(Chord.Type.diminished);
			break;
		case R.id.menu_dominant7:
			setChordType(Chord.Type.dominant7);
			break;
		case R.id.menu_major7:
			setChordType(Chord.Type.major7);
			break;
		case R.id.menu_minor7:
			setChordType(Chord.Type.minor7);
			break;
		case R.id.menu_augmented7:
			setChordType(Chord.Type.augmented7);
			break;
		case R.id.menu_diminished7:
			setChordType(Chord.Type.diminished7);
			break;
		default:
			res = super.onOptionsItemSelected(item);
			break;
		}

		return res;
	}

	/**
	 * @see android.widget.SearchView.OnQueryTextListener#onQueryTextChange(java.lang.String)
	 */
	@Override
	public boolean onQueryTextChange(final String newText) {
		Log.i("Franck", newText);
		return false;
	}

	/**
	 * @see android.widget.SearchView.OnQueryTextListener#onQueryTextSubmit(java.lang.String)
	 */
	@Override
	public boolean onQueryTextSubmit(final String query) {
		Log.i("Franck", "Query = " + query);

		mSearchView.setQuery("", false);
		mSearchView.setIconified(true);

		Chord chord;

		try {
			chord = Chord.parse(query);
		} catch (Exception e) {
			Log.i("alert", "alert");
			Crouton.showText(this, "Unable to find a Chord with this name",
					Style.ALERT);
			chord = null;
		}

		if (chord != null) {
			int ht = chord.getDominant().getHalfTones();
			Tab tab = getActionBar().getTabAt(ht);
			getActionBar().selectTab(tab);
			setChordType(chord.getType());
		}

		return true;
	}

	/**
	 * Generate all the Dominant note tabs
	 */
	private void generateTabs() {
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < 12; ++i) {
			addTab(new Note(i));
		}
	}

	/**
	 * Add a tab to the action bar
	 *
	 * @param note
	 *            the dominant note for this tab
	 * @return the created tab
	 */
	private Tab addTab(final Note note) {
		String name = note.toDisplayString();

		Tab tab = getActionBar().newTab();
		tab.setText(name);
		tab.setTabListener(new ChordTabListener(note));
		View view = LayoutInflater.from(this).inflate(R.layout.tab_label, null);
		((TextView) view.findViewById(android.R.id.title)).setText(name);
		tab.setCustomView(view);

		getActionBar().addTab(tab);
		return tab;
	}

	/**
	 * Listens to change in the dominant note
	 */
	private class ChordTabListener implements TabListener {

		/**
		 * @param tag
		 *            the name of the dominant
		 */
		public ChordTabListener(final Note note) {
			mNote = note;
		}

		/**
		 * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab,
		 *      android.app.FragmentTransaction)
		 */
		@Override
		public void onTabReselected(final Tab tab, final FragmentTransaction ft) {
		}

		/**
		 * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab,
		 *      android.app.FragmentTransaction)
		 */
		@Override
		public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
			setDominant(mNote);
		}

		/**
		 * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab,
		 *      android.app.FragmentTransaction)
		 */
		@Override
		public void onTabUnselected(final Tab tab, final FragmentTransaction ft) {
		}

		private final Note mNote;
	}

	/**
	 *
	 * @param chordType
	 */
	public void setChordType(final Chord.Type chordType) {
		mChordType = chordType;
		updateContent();
	}

	/**
	 * @param dominant
	 */
	public void setDominant(final Note dominant) {
		mDominant = dominant;
		updateContent();
	}

	/**
	 * Update all views content to display the current chord
	 */
	private void updateContent() {
		if ((mChordType == null) || (mDominant == null)) {
			return;
		}

		mChord = Chord.buildChord(mChordType, mDominant);
		if (Settings.shouldSimplify()) {
			mChord.simplify();
		}

		updateTitle();

		mCardView.clearCards();

		CardStack piano = new CardStack();

		// Piano
		mCardView.addCard(new GrandStaffCard(mChord));
		mCardView.addCard(new PianoCard(mChord));

		// Guitar
		mCardView.addCard(new StaffCard(mChord, Key.treble));

	}

	/**
	 * Update the action bar title with the current chord name
	 */
	private void updateTitle() {
		// ((TextView) findViewById(R.id.textPlaceHolder)).setText(mChord
		// .toString());
		setTitle(getString(R.string.title_chord, mChord.toString()));
	}

	private Note mDominant;
	private Chord.Type mChordType;
	private Chord mChord;
	private SearchView mSearchView;
	private CardUI mCardView;
}
