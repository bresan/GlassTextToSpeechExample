package com.bresan.glass.examples.TextToSpeechExample;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;

public class TextToSpeechActivity extends Activity {

	private TextToSpeech speech;
	private static final String TEXT_TO_SPEECH = "TEXT TO SPEECH TEST! ARE YOU HEARING ME?";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_main);

		// Reuse the main layout and change the text
		TextView titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.setText("Tap to listen!");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER: // if tap touchpad
		case KeyEvent.KEYCODE_ENTER: {
			AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			audio.playSoundEffect(Sounds.TAP);

			// here the magic happens!
			speech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
				@Override
				public void onInit(int status) {
					speech.speak(TEXT_TO_SPEECH, TextToSpeech.QUEUE_FLUSH, null);  //speak it
				}
			});

			return true;
		}
		default: {
			return super.onKeyDown(keyCode, event);
		}
		}
	}

}
