package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class DriveActivity extends Activity implements OnTouchListener {

	private int drivePower = 40;
	private int thirdPower = 40;
	private boolean flag = false;
	
	private final int MOTOR_A = 0;
	private final int MOTOR_B = 1;
	private final int MOTOR_C = 2;
	
	private final int ON_MOTOR = 0x20;
	private final int OFF_MOTOR = 0x00;

	private DeviceData myObject;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.drive_view);

		this.myObject = (DeviceData) DeviceData.getInstance();
		this.driveDirections();
	}

	// Set up Drive View Controls
	public void driveDirections() {
		Button goBwd = (Button) findViewById(R.id.bwd_btn);
		goBwd.setOnTouchListener(this);

		Button goFwd = (Button) findViewById(R.id.fwd_btn);
		goFwd.setOnTouchListener(this);

		Button goRgt = (Button) findViewById(R.id.rgt_btn);
		goRgt.setOnTouchListener(this);

		Button goLft = (Button) findViewById(R.id.lft_btn);
		goLft.setOnTouchListener(this);

		Button goThirdLft = (Button) findViewById(R.id.third_lft_btn);
		goThirdLft.setOnTouchListener(this);

		Button goThirdRgt = (Button) findViewById(R.id.third_rgt_btn);
		goThirdRgt.setOnTouchListener(this);

		SeekBar powerSeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		powerSeekBar1.setProgress(this.drivePower);

		TextView powerLabel1 = (TextView) findViewById(R.id.textViewDrive);
		powerLabel1.setText("" + this.drivePower);

		powerSeekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				DriveActivity.this.drivePower = progress;

				TextView powerLabel1 = (TextView) findViewById(R.id.textViewDrive);
				powerLabel1.setText("" + DriveActivity.this.drivePower);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		SeekBar powerSeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		powerSeekBar2.setProgress(this.thirdPower);

		TextView powerLabel2 = (TextView) findViewById(R.id.textViewAux);
		powerLabel2.setText("" + this.thirdPower);

		powerSeekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				DriveActivity.this.thirdPower = progress;

				TextView powerLabel2 = (TextView) findViewById(R.id.textViewAux);
				powerLabel2.setText("" + DriveActivity.this.thirdPower);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
	}
<<<<<<< HEAD
	
	@SuppressWarnings("deprecation")
	public boolean onTouch(View view, MotionEvent event )
	{
=======

	public boolean onTouch(View view, MotionEvent event) {
>>>>>>> ec65fb93efc9d85484a196751eb082ed6899c25d
		int action;
		Button button;

		switch (view.getId()) {
		// Go Fwd
		case R.id.bwd_btn:
			action = event.getAction();
			button = (Button) findViewById(R.id.bwd_btn);

			button.setBackground(getResources().getDrawable(
					R.drawable.arrow_down_pressed));
			
			if (action == MotionEvent.ACTION_DOWN) {
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_down_pressed));

				if (this.flag == false) {
					MoveMotor(this.MOTOR_A, -this.drivePower, this.ON_MOTOR);
					MoveMotor(this.MOTOR_B, -this.drivePower, this.ON_MOTOR);
				}
				this.flag = true;

			} else if ((action == MotionEvent.ACTION_UP)) {
				this.flag = false;
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_down));
				MoveMotor(this.MOTOR_A, -this.drivePower, this.OFF_MOTOR);
				MoveMotor(this.MOTOR_B, -this.drivePower, this.OFF_MOTOR);
			}
			break;
		// Go Rev
		case R.id.fwd_btn:

			action = event.getAction();
			button = (Button) findViewById(R.id.fwd_btn);
			
			if (action == MotionEvent.ACTION_DOWN) {
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_up_pressed));

				if (this.flag == false) {
					MoveMotor(this.MOTOR_A, this.drivePower, this.ON_MOTOR);
					MoveMotor(this.MOTOR_B, this.drivePower, this.ON_MOTOR);
				}
				this.flag = true;

			} else if ((action == MotionEvent.ACTION_UP)) {
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_up));
				this.flag = false;
				MoveMotor(this.MOTOR_A, this.drivePower, this.OFF_MOTOR);
				MoveMotor(this.MOTOR_B, this.drivePower, this.OFF_MOTOR);
			}
			break;

		// Go Right
		case R.id.rgt_btn:

			action = event.getAction();
			button = (Button) findViewById(R.id.rgt_btn);

			if (action == MotionEvent.ACTION_DOWN) {
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_right_pressed));
				if (this.flag == false) {
					MoveMotor(this.MOTOR_A, -this.drivePower, this.ON_MOTOR);
					MoveMotor(this.MOTOR_B, this.drivePower, this.ON_MOTOR);
				}
				this.flag = true;

			} else if ((action == MotionEvent.ACTION_UP)) {
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_right));
				this.flag = false;
				MoveMotor(this.MOTOR_A, -this.drivePower, this.OFF_MOTOR);
				MoveMotor(this.MOTOR_B, this.drivePower, this.OFF_MOTOR);
			}
			break;

		// Go Left
		case R.id.lft_btn:

			action = event.getAction();
			button = (Button) findViewById(R.id.lft_btn);

			if (action == MotionEvent.ACTION_DOWN) {
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_left_pressed));
				if (this.flag == false) {
					MoveMotor(this.MOTOR_A, this.drivePower, this.ON_MOTOR);
					MoveMotor(this.MOTOR_B, -this.drivePower, this.ON_MOTOR);
				}
				this.flag = true;

			} else if ((action == MotionEvent.ACTION_UP)) {
				button.setBackground(getResources().getDrawable(
						R.drawable.arrow_left));
				this.flag = false;
				MoveMotor(this.MOTOR_A, this.drivePower, this.OFF_MOTOR);
				MoveMotor(this.MOTOR_B, -this.drivePower, this.OFF_MOTOR);
			}
			break;

		case R.id.third_lft_btn:

			action = event.getAction();
			button = (Button) findViewById(R.id.third_lft_btn);

			if (action == MotionEvent.ACTION_DOWN) {
				button.setBackground(getResources().getDrawable(
						R.drawable.backward_pressed));
				if (this.flag == false) {
					MoveMotor(this.MOTOR_C, -this.thirdPower, this.ON_MOTOR);

				}
				this.flag = true;

			} else if ((action == MotionEvent.ACTION_UP)) {
				button.setBackground(getResources().getDrawable(
						R.drawable.backward));
				this.flag = false;
				MoveMotor(this.MOTOR_C, -this.thirdPower, this.OFF_MOTOR);

			}
			break;

		case R.id.third_rgt_btn:

			action = event.getAction();
			button = (Button) findViewById(R.id.third_rgt_btn);

			if (action == MotionEvent.ACTION_DOWN) {
				button.setBackground(getResources().getDrawable(
						R.drawable.forward_pressed));
				if (this.flag == false) {
					MoveMotor(this.MOTOR_C, this.thirdPower, this.ON_MOTOR);
				}
				this.flag = true;

			} else if ((action == MotionEvent.ACTION_UP)) {
				button.setBackground(getResources().getDrawable(
						R.drawable.forward));
				this.flag = false;
				MoveMotor(this.MOTOR_C, this.thirdPower, this.OFF_MOTOR);
			}
			break;

		}
		return true;

	}

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
