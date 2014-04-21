package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

/**
 * PEEPS! Important! Anytime you would use <code>findViewById()</code> use
 * <code>rootView.findViewById()</code> instead
 * 
 * Anytime you would use <code>this</code> use <code>getActivity()</code>
 * instead
 */
public class SensorListFragment extends Fragment {

	private View rootView;

	private DeviceData myObject;
	private NXTExtension[] extensions;

	private final int N_EXTENSIONS = 7;
	private final String[] EXTENSION_NAMES = { "1", "2", "3", "4", "A", "B",
			"C" };
	private final int[] EXTENSION_ICONS = { R.drawable.sensor_distance,
			R.drawable.sensor_light, R.drawable.sensor_sound,
			R.drawable.sensor_touch, R.drawable.servo,
			R.drawable.sensor_distance, R.drawable.sensor_distance };

	private final int PICK_A_SENSOR = 1;
	private int clicked;

	/**
	 * this class is used in the NXTExtensionAdapter to create a fancy list view
	 * 
	 * @author eddie
	 * 
	 */
	public class NXTExtension {
		private String title;
		private int icon;
		private boolean polling;

		public NXTExtension() {
			this("no title", -1);
		}

		public NXTExtension(String title, int icon) {
			super();
			this.title = title;
			this.icon = icon;
			this.polling = false;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getIcon() {
			return icon;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}

		public boolean isPolling() {
			return polling;
		}

		public void setPolling(boolean polling) {
			this.polling = polling;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.rootView = inflater.inflate(R.layout.sensor_list_view_layout,
				container, false);

		/*
		 * Not sure if this is how to go about this...? No setLevel() for
		 * ImageView?
		 */

		// ImageView sensorImg = (ImageView) rootView
		// .findViewById(R.id.sensor_image);
		// sensorImg.setLevel(0);

		/*
		 * trying arrayAdapter
		 * http://www.ezzylearning.com/tutorial.aspx?tid=1763429
		 */
		this.extensions = new NXTExtension[this.N_EXTENSIONS];
		for (int i = 0; i < this.N_EXTENSIONS; i++) {
			this.extensions[i] = new NXTExtension(this.EXTENSION_NAMES[i],
					i < 4 ? EXTENSION_ICONS[i] : EXTENSION_ICONS[4]);
		}

		NXTExtensionArrayAdapter theAdapter = new NXTExtensionArrayAdapter(
				this.getActivity(), R.layout.sensor_list_row_layout,
				this.extensions);

		ListView theList = (ListView) this.rootView
				.findViewById(R.id.sensor_listView);

		theList.setAdapter(theAdapter);
		
		theList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.e("here", "here");
				SensorListFragment.this.clicked = position;
				Intent i = new Intent(getActivity(),
						SelectSensorPopupActivity.class);
				startActivityForResult(i, PICK_A_SENSOR);
			}

		});
		

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_A_SENSOR) {
			if (resultCode == Activity.RESULT_OK) {
				NXTExtension temp = this.extensions[this.clicked];
				this.extensions[this.clicked] = this.extensions[data
						.getIntExtra("sensor", 0)];
				this.extensions[data.getIntExtra("sensor", 0)] = temp;

				/*
				 * ListView theList = (ListView) this.rootView
				 * .findViewById(R.id.sensor_listView);
				 */
			}
		}
	}
}
