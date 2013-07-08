package fr.xgouchet.musicgeneration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import fr.xgouchet.musicgeneration.model.Chord;
import fr.xgouchet.musicgeneration.model.ChordFactory;
import fr.xgouchet.musicgeneration.model.Note;
import fr.xgouchet.musicgeneration.source.SoundSource;
import fr.xgouchet.musicgeneration.source.filters.FadeOut;
import fr.xgouchet.musicgeneration.source.filters.TimeOffset;
import fr.xgouchet.musicgeneration.source.raw.SineSource;
import fr.xgouchet.musicgeneration.source.raw.SquareSource;
import fr.xgouchet.musicgeneration.source.raw.TriangleSource;
import fr.xgouchet.musicgeneration.task.AsyncSoundPlayer;
import fr.xgouchet.musicgeneration.task.AsyncSoundPlayer.Quality;

public class TestActivity extends Activity {

	SeekBar mSeekFrequency, mSeekDuration;
	RadioGroup mSoundType;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		mSeekFrequency = (SeekBar) findViewById(R.id.seekBar1);
		mSeekDuration = (SeekBar) findViewById(R.id.seekBar2);
		mSoundType = (RadioGroup) findViewById(R.id.radioGroup1);

	}

	public void playSound(final View v) {

		int duration = mSeekDuration.getProgress();

		Chord chord = ChordFactory.buildMajorChord(new Note());
		Note[] notes = chord.getNotes();
		SoundSource[] sources = new SoundSource[notes.length];

		for (int i = 0; i < notes.length; ++i) {

			SoundSource raw;

			switch (mSoundType.getCheckedRadioButtonId()) {

			case R.id.radio1:
				raw = new SquareSource(notes[i].getEqualTemperredFrequency(),
						duration);
				break;
			case R.id.radio2:
				raw = new TriangleSource(notes[i].getEqualTemperredFrequency(),
						duration);
				break;
			case R.id.radio0:
			default:
				raw = new SineSource(notes[i].getEqualTemperredFrequency(),
						duration);
				break;
			}

			sources[i] = new TimeOffset(new FadeOut(raw), i * 50);
		}

		AsyncSoundPlayer.createAsyncSoundPlayer(Quality.medium)
				.execute(sources);

	}
}
