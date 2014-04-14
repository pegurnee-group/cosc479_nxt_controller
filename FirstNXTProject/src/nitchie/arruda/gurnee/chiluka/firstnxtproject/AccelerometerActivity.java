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

	private SensorManager mgr;
	private Sensor accelerometer;
	private TextView text;
	private int mRotation;

	private View rootView;
	private DeviceData myObject;

	private final int MOTOR_A = 0;
	private final int MOTOR_B = 1;
	private final int MOTOR_C = 2;

	private final int ON_MOTOR = 0x20;
	private final int OFF_MOTOR = 0x00;

	private int drivePower = 45;

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
		this.mRotation = window.getDefaultDisplay().getRotation();

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

	/**
	 * Get the command
	 * 
	 * @param call
	 */
	public void onCommand(char call) {

		char myDir = call;

		switch (myDir) {
		// Go Fwd
		case 'f':
			MoveMotor(this.MOTOR_A, this.drivePower, this.ON_MOTOR);
			MoveMotor(this.MOTOR_B, this.drivePower, this.ON_MOTOR);
			break;

		// Go Bwd
		case 'b':
			MoveMotor(this.MOTOR_A, -this.drivePower, this.ON_MOTOR);
			MoveMotor(this.MOTOR_B, -this.drivePower, this.ON_MOTOR);
			break;

		// Go Right
		case 'r':
			MoveMotor(this.MOTOR_A, -this.drivePower, this.ON_MOTOR);
			MoveMotor(this.MOTOR_B, this.drivePower, this.ON_MOTOR);
			break;

		// Go Left
		case 'l':
			MoveMotor(this.MOTOR_A, this.drivePower, this.ON_MOTOR);
			MoveMotor(this.MOTOR_B, -this.drivePower, this.ON_MOTOR);
			break;

		// Stop!
		case 's':
			MoveMotor(this.MOTOR_A, this.drivePower, this.OFF_MOTOR);
			MoveMotor(this.MOTOR_B, this.drivePower, this.OFF_MOTOR);
			break;

		// Motor 3 Fwd
		case 'F':
			MoveMotor(this.MOTOR_C, -this.drivePower, this.ON_MOTOR);
			break;

		// Motor3 Rev
		case 'R':
			MoveMotor(this.MOTOR_C, this.drivePower, this.ON_MOTOR);
			break;

		// Stop Motor 3!
		case 'S':
			MoveMotor(this.MOTOR_C, this.drivePower, this.OFF_MOTOR);
			break;
		}
	}

	/*
	 * Drive the Robot
	 */
	private void MoveMotor(int motor, int speed, int state) {
		try {
			byte[] buffer = new byte[15];

			buffer[0] = (byte) (15 - 2); // length lsb
			buffer[1] = 0; // length msb
			buffer[2] = 0; // direct command (with response)
			buffer[3] = 0x04; // set output state
			buffer[4] = (byte) motor; // output 1 (motor B)
			buffer[5] = (byte) speed; // power
			buffer[6] = 1 + 2; // motor on + brake between PWM
			buffer[7] = 0; // regulation
			buffer[8] = 0; // turn ration??
			buffer[9] = (byte) state; // 0x20; // run state
			buffer[10] = 0;
			buffer[11] = 0;
			buffer[12] = 0;
			buffer[13] = 0;
			buffer[14] = 0;

			this.myObject.getOs().write(buffer);
			this.myObject.getOs().flush();

		} catch (Exception e) {
		}
	}

}
