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
	
	

	private View rootView;
	
	String[] goForward = {"Stuff"};
	String[] goLeft = {"Stuff"};

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
	
	@Override
	public void onClick(View v) {
		if (v.getId() == this.mbtSpeak.getId()) {
			this.speak(v);
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
						
//						for(String s : goForward) {
//							if (textMatchList.get(0).toLowerCase().contains(s)) {
//								onCommand('f');
//								return;
//							}
//						}
//						
//						
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
}
