package fr.xgouchet.musichelper;

import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import fr.xgouchet.musichelper.common.Settings;
import fr.xgouchet.musichelper.model.Accidental;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Note;
import fr.xgouchet.musichelper.model.Pitch;
import fr.xgouchet.musichelper.ui.view.PianoView;
import fr.xgouchet.musichelper.ui.view.StaffView;

/**
 * 
 */
public class FranckActivity extends Activity {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		generateTabs();
		setChordType(Chord.Type.major);
		setDominant(new Note());
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.chords, menu);
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
	 * Generate all the Dominant note tabs
	 */
	private void generateTabs() {
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		addTab(new Note(Pitch.C, Accidental.natural, 4));
		addTab(new Note(Pitch.D, Accidental.natural, 4));
		addTab(new Note(Pitch.E, Accidental.natural, 4));
		addTab(new Note(Pitch.F, Accidental.natural, 4));
		addTab(new Note(Pitch.G, Accidental.natural, 4));
		addTab(new Note(Pitch.A, Accidental.natural, 4));
		addTab(new Note(Pitch.B, Accidental.natural, 4));
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

		List<Chord> chords = new LinkedList<Chord>();
		chords.add(mChord);

		((StaffView) findViewById(R.id.staffView)).setChords(chords);
		((PianoView) findViewById(R.id.pianoView)).setChord(mChord);

		updateTitle();
	}

	/**
	 * Update the action bar title with the current chord name
	 */
	private void updateTitle() {
		((TextView) findViewById(R.id.textPlaceHolder)).setText(mChord
				.toString());
		setTitle(getString(R.string.title_chord, mChord.toDisplayString()));
	}

	private Note mDominant;
	private Chord.Type mChordType;
	private Chord mChord;
}
