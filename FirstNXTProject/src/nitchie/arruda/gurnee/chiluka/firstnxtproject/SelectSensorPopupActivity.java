package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SelectSensorPopupActivity extends ListActivity {
	private final int N_SENSORS = 4;
	private final int[] SENSOR_ICONS = { R.drawable.sensor_distance,
			R.drawable.sensor_light, R.drawable.sensor_sound,
			R.drawable.sensor_touch };
	private final String[] SENSOR_NAMES = { "Distance Sensor", "Light Sensor",
			"Sound Sensor", "Touch Sensor" };
	private NXTSensor[] sensors = new NXTSensor[this.N_SENSORS];

	public class NXTSensor {
		private String title;
		private int icon;

		public NXTSensor() {
			this("no title", -1);
		}

		public NXTSensor(String title, int icon) {
			super();
			this.title = title;
			this.icon = icon;
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
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.select_sensor_view_layout);

		for (int i = 0; i < this.N_SENSORS; i++) {
			this.sensors[i] = new NXTSensor(this.SENSOR_NAMES[i],
					this.SENSOR_ICONS[i]);
		}
		NXTSensorArrayAdapter adapter = new NXTSensorArrayAdapter(this,
				R.layout.select_sensor_row_layout, this.sensors);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent returnIntent = new Intent();
		returnIntent.putExtra("sensor", position);

		this.setResult(RESULT_OK, returnIntent);
		this.finish();
	}
}
