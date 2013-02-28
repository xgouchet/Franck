package fr.xgouchet.musichelper;

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
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Tone;

/**
 * 
 */
public class CircleActivity extends Activity {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);

		generateTabs();
		setChordType(Chord.Type.major);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.chords, menu);

		int chordTitle;
		switch (mChordType) {
		case major:
			chordTitle = R.string.menu_major;
			break;
		case minor:
			chordTitle = R.string.menu_minor;
			break;
		case augmented:
			chordTitle = R.string.menu_augmented;
			break;
		case diminished:
			chordTitle = R.string.menu_diminished;
			break;
		default:
			chordTitle = R.string.menu_chord;
			break;
		}
		menu.findItem(R.id.menu_current_chord).setTitle(chordTitle);

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
		case R.id.menu_seventh:
			setChordType(Chord.Type.seventh);
			break;
		default:
			res = super.onOptionsItemSelected(item);
			break;
		}

		return res;
	}

	private void generateTabs() {

		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		addTab("C");
		addTab("C#");
		addTab("D");
		addTab("Eb");
		addTab("E");
		addTab("F");
		addTab("F#");
		addTab("G");
		addTab("C#");
		addTab("A");
		addTab("Bb");
		addTab("B");
	}

	private Tab addTab(final String name) {
		Tab tab = getActionBar().newTab();
		tab.setText(name);
		tab.setTabListener(new ChordTabListener(name));
		View view = LayoutInflater.from(this).inflate(R.layout.tab_label, null);
		((TextView) view.findViewById(android.R.id.title)).setText(name);

		tab.setCustomView(view);

		getActionBar().addTab(tab);
		return tab;
	}

	/**
	 * 
	 */
	private class ChordTabListener implements TabListener {

		public ChordTabListener(final String tag) {
			mTag = tag;
		}

		@Override
		public void onTabReselected(final Tab tab, final FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabUnselected(final Tab tab, final FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		private final String mTag;
	}

	public void setChordType(final Chord.Type chordType) {
		mChordType = chordType;
		invalidateOptionsMenu();
	}

	private Tone mDominant;
	private Chord.Type mChordType;
}
