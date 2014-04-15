package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * PEEPS! Important! Anytime you would use <code>findViewById()</code> use
 * <code>rootView.findViewById()</code> instead
 * 
 * Anytime you would use <code>this</code> use <code>getActivity()</code>
 * instead
 */
public class SensorActivity extends Fragment {

	private View rootView;

	private DeviceData myObject;
	private NXTExtension[] extensions;

	private final int N_EXTENSIONS = 7;
	private final String[] EXTENSION_NAMES = { "1", "2", "3", "4", "A", "B",
			"C" };
	private final int[] EXTENSION_ICONS = { R.drawable.sensor_distance,
			R.drawable.sensor_light, R.drawable.sensor_sound,
			R.drawable.sensor_touch, R.drawable.sensor_distance,
			R.drawable.sensor_distance, R.drawable.sensor_distance };

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

		this.rootView = inflater
				.inflate(R.layout.sensor_view, container, false);

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

		NXTExtensionAdapter theAdapter = new NXTExtensionAdapter(
				this.getActivity(), R.layout.sensor_row_view, this.extensions);

		
		ListView theList = (ListView) this.rootView
				.findViewById(R.id.sensor_listView);
		theList.setAdapter(theAdapter);

		return rootView;
	}
}
