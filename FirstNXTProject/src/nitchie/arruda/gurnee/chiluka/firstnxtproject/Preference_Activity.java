package nitchie.arruda.gurnee.chiluka.firstnxtproject;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;


public class Preference_Activity extends PreferenceActivity implements
		OnPreferenceChangeListener {

	CheckBoxPreference cb_batt;
	CheckBoxPreference cb_defSpeed;
	ListPreference time;
	Boolean btlevel;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.preferences_layout);

		PreferenceManager.setDefaultValues(Preference_Activity.this, R.layout.preferences_layout,
				false);
		cb_batt = (CheckBoxPreference) findPreference("cb1");
		cb_defSpeed = (CheckBoxPreference) findPreference("cb2");
		time = (ListPreference) findPreference("updates_interval");
		cb_batt.setOnPreferenceChangeListener(this);
		cb_defSpeed.setOnPreferenceChangeListener(this);
		
		time.setOnPreferenceChangeListener(this);

	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub

		if (preference.getKey().equalsIgnoreCase("cb1")) {
			//if (!((Boolean) newValue).booleanValue()) {
				
				// cb_sync.setChecked(false);
			//}
			String battValue = newValue.toString();
			SharedPreferences shared = getSharedPreferences("GetPrefs",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = shared.edit();
			editor.putString("bt", battValue);
			editor.commit();
		} else if (preference.getKey().equalsIgnoreCase("cb2")) {
			//if (!((Boolean) newValue).booleanValue()) {
				
				// cb_sync.setChecked(false);
			//}
			// boolean checked = Boolean.valueOf(newValue.toString());
			String speedValue = newValue.toString();
			SharedPreferences shared = getSharedPreferences("GetPrefs",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = shared.edit();
			editor.putString("sp", speedValue);
			editor.commit();

		}
		else if(preference.getKey().equalsIgnoreCase("updates_interval")){
			SharedPreferences shared = getSharedPreferences("GetPrefs",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = shared.edit();
			editor.putString("time_interval", newValue.toString());
			editor.commit();
			System.out.printf("Time selected is:",shared.getString("updates_interval", newValue.toString()));
				 
		}

		return true;
	}


}
