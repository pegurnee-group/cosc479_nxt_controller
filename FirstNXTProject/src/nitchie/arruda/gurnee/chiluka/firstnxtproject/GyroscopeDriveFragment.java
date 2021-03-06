package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class GyroscopeDriveFragment extends Fragment implements
		SensorEventListener {

	private SensorManager mgr;
	private Sensor gyroscope;
	private int mRotation;

	private View rootView;
	private DeviceData myObject;

	private final int MOTOR_A = 0;
	private final int MOTOR_B = 1;
	private final int MOTOR_C = 2;

	private final int ON_MOTOR = 0x20;
	private final int OFF_MOTOR = 0x00;

	private boolean enabled;
	private boolean forward;
	private int direction;

	private int drivePower = 75;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.gyro_view_layout, container,
				false);
		this.myObject = (DeviceData) DeviceData.getInstance();

		Activity somethingNew = this.getActivity();

		this.mgr = (SensorManager) somethingNew
				.getSystemService(Context.SENSOR_SERVICE);
		this.gyroscope = this.mgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		WindowManager window = (WindowManager) somethingNew
				.getSystemService(Context.WINDOW_SERVICE);
		this.mRotation = window.getDefaultDisplay().getRotation();

		return rootView;
	}

	/*
	 * @Override public boolean onTouch(View view, MotionEvent event) { switch
	 * (view.getId()) { case R.id.accelBtn: int action = event.getAction(); if
	 * (action == MotionEvent.ACTION_DOWN) { if (this.enabled == false) { switch
	 * (this.direction) { case 0: if (this.forward) { this.onCommand('r'); }
	 * else { this.onCommand('l'); } break; case 1: if (this.forward) {
	 * this.onCommand('f'); } else { this.onCommand('b'); } break; case 2: if
	 * (this.forward) { this.onCommand('F'); } else { this.onCommand('R'); }
	 * break; } } this.enabled = true;
	 * 
	 * } else if ((action == MotionEvent.ACTION_UP)) { this.enabled = false;
	 * this.onCommand('s'); this.onCommand('S'); } break; } return true; }
	 */

	@Override
	public void onResume() {
		mgr.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}

	@Override
	public void onPause() {
		mgr.unregisterListener(this, gyroscope);
		super.onPause();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore
	}

	public void onSensorChanged(SensorEvent event) {
		/*
		 * float[] data = Arrays.copyOf(event.values, event.values.length);
		 * Arrays.sort(data);
		 * 
		 * if ((int)event.values[0] == (int)event.values[1] &&
		 * (int)event.values[1] == (int)event.values[2]) {
		 * 
		 * } if (event.values[0] > event.values[1]) { if (event.values[0] >
		 * event.values[2]) {
		 * 
		 * } } else {
		 * 
		 * } String msg = String.format(
		 * "X: %8.4f\nY: %8.4f\nZ: %8.4f\nRotation: %d", event.values[0],
		 * event.values[1], event.values[2], mRotation); text.setText(msg);
		 * text.invalidate();
		 */

		/*
		 * event.values[2] -= 9.2; for (int i = 0; i < event.values.length; i++)
		 * { if (Math.abs(event.values[this.direction]) <
		 * Math.abs(event.values[i])) { this.direction = i; } } if
		 * (event.values[this.direction] > 0) { this.forward = true; } else {
		 * this.forward = false; }
		 */
		if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			Log.e("x", "" + event.values[0]);
			Log.e("y", "" + event.values[1]);
			Log.e("z", "" + event.values[2]);

//			ImageView img = (ImageView) this.rootView
//					.findViewById(R.id.gyro_arrow);

			if (Math.abs(event.values[0]) > 1) {
				if (event.values[0] > 0) {
					Log.e("x", "positive");
					// this.onCommand('l');
//					img.setBackground(getResources().getDrawable(
//							R.drawable.gyro_arrow_right));
				} else {
					Log.e("x", "negative");
//					img.setBackground(getResources().getDrawable(
//							R.drawable.gyro_arrow_left));
					// this.onCommand('r');
				}
			} else if (Math.abs(event.values[1]) > 1) {
				if (event.values[1] > 0) {
					Log.e("y", "positive");
					// img.setBackground(getResources().getDrawable(R.drawable.gyro_arrow_up));
					// this.onCommand('b');
				} else {
					Log.e("y", "negative");
					// img.setBackground(getResources().getDrawable(R.drawable.gyro_arrow_down));
					// this.onCommand('f');
				}
			}
			/*
			 * else if (Math.abs(event.values[2]) > 1) { if (event.values[2] >
			 * 0) { Log.e("gz", "positive"); this.onCommand('F'); } else {
			 * Log.e("gz", "negative"); this.onCommand('R'); } }
			 */
			else {
				this.onCommand('s');
				this.onCommand('S');
				// img.setImageDrawable(null);
			}
		}

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

		if (call != 's' && call != 'S') {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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