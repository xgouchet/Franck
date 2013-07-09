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
import android.widget.Toast;

import com.fima.cardsui.views.CardUI;

import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musicgeneration.model.ChordFactory;
import fr.xgouchet.musicgeneration.model.ChordType;
import fr.xgouchet.musicgeneration.model.Notation;
import fr.xgouchet.musicgeneration.model.Note;
import fr.xgouchet.musichelper.common.Settings;
import fr.xgouchet.musichelper.ui.card.ChordCard;
import fr.xgouchet.musichelper.ui.card.GuitarCard;
import fr.xgouchet.musichelper.ui.card.PianoCard;
import fr.xgouchet.musichelper.ui.card.SoundCard;
import fr.xgouchet.musichelper.ui.card.StaffCard;

/**
 * 
 * 
 * @author Xavier Gouchet
 */
public class FranckActivity extends Activity implements OnQueryTextListener {

	private Note mDominant;
	private ChordType mChordType;
	private Chord mChord;
	private SearchView mSearchView;
	private CardUI mCardView;

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
		setChordType(ChordType.major);
		setDominant(new Note());

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
			setChordType(ChordType.major);
			break;
		case R.id.menu_minor:
			setChordType(ChordType.minor);
			break;
		case R.id.menu_augmented:
			setChordType(ChordType.augmented);
			break;
		case R.id.menu_diminished:
			setChordType(ChordType.diminished);
			break;
		case R.id.menu_dominant7:
			setChordType(ChordType.dominant7);
			break;
		case R.id.menu_major7:
			setChordType(ChordType.major7);
			break;
		case R.id.menu_minor7:
			setChordType(ChordType.minor7);
			break;
		case R.id.menu_augmented7:
			setChordType(ChordType.augmented7);
			break;
		case R.id.menu_diminished7:
			setChordType(ChordType.diminished7);
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
			chord = ChordFactory.buildMajor7thChord(new Note());// ChordFactory.parse(query);
		} catch (Exception e) {
			Toast.makeText(this, "Unable to create such a chord",
					Toast.LENGTH_LONG).show();
			chord = null;
		}

		if (chord != null) {
			int ht = chord.getDominant().getSemiTones();
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
		String name = note.getNoteName(Notation.english);

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
	public void setChordType(final ChordType chordType) {
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

		mChord = ChordFactory.buildChord(mChordType, mDominant);
		if (Settings.shouldSimplify()) {
			mChord.simplify();
		}

		updateTitle();

		mCardView.clearCards();

		// Chord
		mCardView.addCard(new ChordCard(mChord));

		// Sound
		mCardView.addCard(new SoundCard(mChord));

		// Staff
		mCardView.addCard(new StaffCard(mChord));

		// Piano
		mCardView.addCard(new PianoCard(mChord));

		// Guitar
		mCardView.addCard(new GuitarCard(mChord));

	}

	/**
	 * Update the action bar title with the current chord name
	 */
	private void updateTitle() {
		String chord = mChord.getChordName(Settings.getNotation());
		setTitle(getString(R.string.title_chord, chord));
	}

}
