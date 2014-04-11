package nitchie.arruda.gurnee.chiluka.firstnxtproject;

/**
 * Taken from:
http://www.javacodegeeks.com/2012/08/android-voice-recognition-tutorial.html
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * PEEPS! Important!
 * Anytime you would use
 * <code>findViewById()</code>
 * use
 * <code>rootView.findViewById()</code>
 * instead
 * 
 * Anytime you would use
 * <code>this</code>
 * use
 * <code>getActivity()</code>
 * instead
 */
public class VoiceRecognitionActivity extends Fragment implements OnClickListener {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

	private EditText metTextHint;
	private ListView mlvTextMatches;
	private Spinner msTextMatches;
	private Button mbtSpeak;
	private boolean flag = false;

	private View rootView;
	private DeviceData myObject;
	
	private final int MOTOR_A = 0;
	private final int MOTOR_B = 1;
	private final int MOTOR_C = 2;
	
	private final int ON_MOTOR = 0x20;
	private final int OFF_MOTOR = 0x00;
	
	private int drivePower = 40;
	private int thirdPower = 40;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.voice_control_view, container,
				false);
		metTextHint = (EditText) rootView.findViewById(R.id.etTextHint);
		mlvTextMatches = (ListView) rootView.findViewById(R.id.lvTextMatches);
		msTextMatches = (Spinner) rootView.findViewById(R.id.sNoOfMatches);
		mbtSpeak = (Button) rootView.findViewById(R.id.btSpeak);
		mbtSpeak.setOnClickListener(this);
		checkVoiceRecognition();

		return rootView;
	}
	
	

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			mbtSpeak.setEnabled(false);
			mbtSpeak.setText("Voice recognizer not present");
			Toast.makeText(getActivity(), "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void speak(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		// Specify the calling package to identify your application
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());

		// Display an hint to the user about what he should say.
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText()
				.toString());

		// Given an hint to the recognizer about what the user is going to say
		// There are two form of language model available
		// 1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
		// 2.LANGUAGE_MODEL_FREE_FORM : If not sure about the words or phrases
		// and its domain.
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

		// If number of Matches is not selected then return show toast message
		if (msTextMatches.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
			Toast.makeText(getActivity(),
					"Please select No. of Matches from spinner",
					Toast.LENGTH_SHORT).show();
			return;
		}

		int noOfMatches = Integer.parseInt(msTextMatches.getSelectedItem()
				.toString());
		// Specify how many results you want to receive. The results will be
		// sorted where the first result is the one with higher confidence.
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		// Start the Voice recognizer activity for the result.
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

			// If Voice recognition is successful then it returns RESULT_OK
			if (resultCode == Activity.RESULT_OK) {

				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if (!textMatchList.isEmpty()) {
					// If first Match contains the 'search' word
					// Then start web search.
					if (textMatchList.get(0).contains("search")) {

						String searchQuery = textMatchList.get(0);
						searchQuery = searchQuery.replace("search", "");
						Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
						search.putExtra(SearchManager.QUERY, searchQuery);
						startActivity(search);
					} else {
						// populate the Matches
						mlvTextMatches.setAdapter(new ArrayAdapter<String>(
								getActivity(), android.R.layout.simple_list_item_1,
								textMatchList));
					}

				}
				// Result code for various error.
			} else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
				showToastMessage("Audio Error");
			} else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
				showToastMessage("Client Error");
			} else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
				showToastMessage("Network Error");
			} else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
				showToastMessage("No Match");
			} else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
				showToastMessage("Server Error");
			}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Helper method to show the toast message
	 **/
	void showToastMessage(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == this.mbtSpeak.getId()) {
			this.speak(v);
		}
		
	}
	
	
	public boolean onCommand(char call) {

		int action;
		Button button;

		switch (call) {
		// Go Fwd
		case 'F':
				
					MoveMotor(this.MOTOR_A, -this.drivePower, this.ON_MOTOR);
					MoveMotor(this.MOTOR_B, -this.drivePower, this.ON_MOTOR);
			
			break;
			
			/*
		// Go Rev
		case R.id.fwd_btn:

			action = event.getAction();
			button = (Button) rootView.findViewById(R.id.fwd_btn);
			
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
			button = (Button) rootView.findViewById(R.id.rgt_btn);

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
			button = (Button) rootView.findViewById(R.id.lft_btn);

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
			button = (Button) rootView.findViewById(R.id.third_lft_btn);

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
			button = (Button) rootView.findViewById(R.id.third_rgt_btn);

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
*/
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
