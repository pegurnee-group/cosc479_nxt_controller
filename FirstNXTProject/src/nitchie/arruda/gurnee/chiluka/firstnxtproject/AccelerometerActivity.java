package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class AccelerometerActivity extends Fragment implements
		SensorEventListener {

	private View rootView;
	private DeviceData myObject;

	private SensorManager mgr;
	private Sensor accelerometer;
	private TextView text;
	private int mRotation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.accel_view, container, false);
		this.myObject = (DeviceData) DeviceData.getInstance();

		Activity somethingNew = this.getActivity();

		this.mgr = (SensorManager) somethingNew
				.getSystemService(Context.SENSOR_SERVICE);
		this.accelerometer = this.mgr
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		WindowManager window = (WindowManager) somethingNew
				.getSystemService(Context.WINDOW_SERVICE);
		int apiLevel = Integer.parseInt(Build.VERSION.SDK);

		if (apiLevel < 8) {
			mRotation = window.getDefaultDisplay().getOrientation();
		} else {
			mRotation = window.getDefaultDisplay().getRotation();
		}

		return rootView;
	}

	@Override
	public void onResume() {
		mgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}

	@Override
	public void onPause() {
		mgr.unregisterListener(this, accelerometer);
		super.onPause();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore
	}

	public void onSensorChanged(SensorEvent event) {
		String msg = String.format(
				"X: %8.4f\nY: %8.4f\nZ: %8.4f\nRotation: %d", event.values[0],
				event.values[1], event.values[2], mRotation);
		text.setText(msg);
		text.invalidate();
	}
}
