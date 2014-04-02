package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PopupActivity extends ListActivity {
	BluetoothDevice[] devices;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.popup_view);

		// String[] names = {"eddie", "was", "here", "don'tchu", "worry",
		// "child" };

		devices = (BluetoothDevice[]) BluetoothAdapter.getDefaultAdapter()
				.getBondedDevices().toArray();

		int limit = devices.length;
		String[] names = new String[limit];

		for (int i = 0; i < limit; i++) {
			names[i] = devices[i].getName() + "\n" + devices[i].getAddress();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.row_view, R.id.row_lbl, names);
		this.setListAdapter(adapter);

		((ListView) this.findViewById(R.id.popup_list))
				.setOnItemClickListener(new ListItemListener());

	}

	private class ListItemListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			DeviceData myObject = (DeviceData) DeviceData.getInstance();
			myObject.setTheUUID(devices[position].getUuids()[0].getUuid());
			PopupActivity.this.finish();
		}

	}
}