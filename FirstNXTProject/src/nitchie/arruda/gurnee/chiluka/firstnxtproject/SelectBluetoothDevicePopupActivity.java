package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import java.util.Set;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectBluetoothDevicePopupActivity extends ListActivity {
	private BluetoothDevice[] devices;
	private DeviceData myObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.select_bluetooth_layout);

		Set<BluetoothDevice> bluetoothDevicesSet = BluetoothAdapter
				.getDefaultAdapter().getBondedDevices();

		if (null != bluetoothDevicesSet) {
			this.devices = (BluetoothDevice[]) bluetoothDevicesSet
					.toArray(new BluetoothDevice[0]);
		} else {
			this.finish();
			return;
		}

		int limit = this.devices.length;
		String[] names = new String[limit];

		for (int i = 0; i < limit; i++) {
			names[i] = this.devices[i].getName() + "\n" + this.devices[i].getAddress();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.select_bluetooth_row_layout, R.id.row_lbl, names);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		this.myObject = (DeviceData) DeviceData.getInstance();
		this.myObject.setBt(this.devices[position]);

		this.setResult(RESULT_OK);
		this.finish();
	}
}