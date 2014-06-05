package com.bresan.glass.examples.AccelerometerExample;

import java.text.DecimalFormat;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerActivity extends Activity {

	private TextView txtX;
	private TextView txtY;
	private TextView txtZ;
	private TextView txtLinearX;
	private TextView txtLinearY;
	private TextView txtLinearZ;

	private SensorManager sensorManager;
	private Sensor accelerometer;
	private AccelerometerListener accelerometerListener;

	private static final int xPos = 0;
	private static final int yPos = 1;
	private static final int zPos = 2;
	
	private static final int DIMENSIONS_SIZE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_accelerometer);

		txtX = (TextView) findViewById(R.id.gravityXValueTextView);
		txtY = (TextView) findViewById(R.id.gravityYValueTextView);
		txtZ = (TextView) findViewById(R.id.gravityZValueTextView);
		txtLinearX = (TextView) findViewById(R.id.linearAccelerationXValueTextView);
		txtLinearY = (TextView) findViewById(R.id.linearAccelerationYValueTextView);
		txtLinearZ = (TextView) findViewById(R.id.linearAccelerationZValueTextView);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		accelerometerListener = new AccelerometerListener();
	}

	@Override
	public void onResume() {
		super.onResume();

		// Restart the accelerometer
		sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onPause() {
		super.onPause();

		// Stop the accelerometer
		sensorManager.unregisterListener(accelerometerListener);
	}

	private class AccelerometerListener implements SensorEventListener {
		@Override
		public void onSensorChanged(SensorEvent event) {
			// Example extracted from Android API (http://developer.android.com/reference/android/hardware/SensorEvent.html)

			// alpha is calculated as t / (t + dT)
			// with t, the low-pass filter's time-constant
			// and dT, the event delivery rate

			// each position on gravity array indicates the position on space (X, Y and Z)

			final double alpha = 0.8;
			double gravity[] = new double[DIMENSIONS_SIZE];
			double linearAcceleration[] = new double[DIMENSIONS_SIZE];

			gravity[xPos] = alpha * gravity[xPos] + (1 - alpha) * event.values[xPos];
			gravity[yPos] = alpha * gravity[yPos] + (1 - alpha) * event.values[yPos];
			gravity[zPos] = alpha * gravity[zPos] + (1 - alpha) * event.values[zPos];

			linearAcceleration[xPos] = event.values[xPos] - gravity[xPos];
			linearAcceleration[yPos] = event.values[yPos] - gravity[yPos];
			linearAcceleration[zPos] = event.values[zPos] - gravity[zPos];

			DecimalFormat df = new DecimalFormat("0.0");

			// update the text views with the read data from sensor
			txtX.setText(df.format(gravity[xPos]));
			txtY.setText(df.format(gravity[1]));
			txtZ.setText(df.format(gravity[2]));
			txtLinearX.setText(df.format(linearAcceleration[xPos]));
			txtLinearY.setText(df.format(linearAcceleration[yPos]));
			txtLinearZ.setText(df.format(linearAcceleration[zPos]));
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	}
}
