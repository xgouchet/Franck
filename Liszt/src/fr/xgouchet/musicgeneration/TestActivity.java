package fr.xgouchet.musicgeneration;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import fr.xgouchet.musicgeneration.source.SineSource;
import fr.xgouchet.musicgeneration.source.SoundSource;
import fr.xgouchet.musicgeneration.source.SquareSource;
import fr.xgouchet.musicgeneration.source.TriangleSource;
import fr.xgouchet.musicgeneration.source.filters.FadeOut;
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
		int frequency = mSeekFrequency.getProgress();

		SoundSource sound;
		switch (mSoundType.getCheckedRadioButtonId()) {
		case R.id.radio1:
			sound = new SquareSource(frequency, duration);
			break;
		case R.id.radio2:
			sound = new TriangleSource(frequency, duration);
			break;
		case R.id.radio0:
		default:
			sound = new SineSource(frequency, duration);
			break;
		}

		FadeOut  fade = new FadeOut(sound);
		
		AsyncSoundPlayer.createAsyncSoundPlayer(Quality.medium).execute(fade);
		Log.i("Liszt", "Playing sound at " + frequency + "Hz for " + duration
				+ "seconds");

	}
}
