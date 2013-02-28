package fr.xgouchet.musichelper;

import fr.xgouchet.musichelper.model.ChordType;
import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CircleActivity extends Activity {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);

		generateTabs();
		setChordType(ChordType.major);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
	public boolean onOptionsItemSelected(MenuItem item) {

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

	private Tab addTab(String name) {
		Tab tab = getActionBar().newTab();
		tab.setText(name);
		tab.setTabListener(new ChordTabListener(name));
		View view = LayoutInflater.from(this).inflate(R.layout.tab_label, null);
		((TextView) view.findViewById(android.R.id.title)).setText(name);

		tab.setCustomView(view);

		getActionBar().addTab(tab);
		return tab;
	}

	private class ChordTabListener implements TabListener {

		public ChordTabListener(String tag) {
			mTag = tag;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		private final String mTag;
	}

	public void setChordType(ChordType chordType) {
		mChordType = chordType;
		invalidateOptionsMenu();
	}

	private ChordType mChordType;
}
