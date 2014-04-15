package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

	private class NXTSensor {
		private String title;
		private int icon;
		private boolean polling;

		public NXTSensor() {
			this("no title", -1);
		}

		public NXTSensor(String title, int icon) {
			super();
			this.title = title;
			this.icon = icon;
			this.polling = false;
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
		 */

		return rootView;
	}
}
