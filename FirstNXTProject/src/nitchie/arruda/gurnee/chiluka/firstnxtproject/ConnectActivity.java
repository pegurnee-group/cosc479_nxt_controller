package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * PEEPS! Important! Anytime you would use <code>findViewById()</code> use
 * <code>rootView.findViewById()</code> instead
 * 
 * Anytime you would use <code>this</code> use <code>getActivity()</code>
 * instead
 */
public class ConnectActivity extends Fragment implements OnClickListener {

	private final String TAG = "NXT Project 1";
	private final double MAX_MILLI_VOLTS = 9000.0;
	private final int IMAGE_TRANSPARENT = 100;
	private final int IMAGE_OPAQUE = 255;
	private final int BATTERY_MAX = 100;
	private final int BATTERY_MIN = 0;

	// UI Components
	private Button connectButton;
	private Button disconnectButton;
	private ImageView btImage;
	private TextView statusLabel;
	private ProgressBar batteryStatus;
	private Button singButton;

	// Bluetooth Variables
	private BluetoothDevice bd;
	private BluetoothSocket socket;
	private InputStream is;
	private OutputStream os;
	private final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

	private final int PICK_BLUETOOTH_ID = 1;

	private DeviceData myObject;

	private View rootView;

	// default poll?
	private int batteryPollInterval = 60000;
	private Timer mBatteryTimer;

	private boolean nxtConnected = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.connect_view, container, false);

		this.myObject = (DeviceData) DeviceData.getInstance();

		this.connectButton = (Button) rootView.findViewById(R.id.connectButton);
		this.connectButton.setOnClickListener(this);

		singButton = (Button) rootView.findViewById(R.id.singButton);
		singButton.setOnClickListener(this);
		singButton.setVisibility(View.INVISIBLE);

		this.disconnectButton = (Button) rootView
				.findViewById(R.id.disconnectButton);
		this.disconnectButton.setOnClickListener(this);
		this.disconnectButton.setVisibility(View.GONE);

		this.btImage = (ImageView) rootView.findViewById(R.id.imageView1);
		this.btImage.setImageAlpha(this.IMAGE_TRANSPARENT);

		this.statusLabel = (TextView) rootView.findViewById(R.id.statusLabel);
		// this.statusLabel.setTextColor(color.primary_text_light);

		this.batteryStatus = (ProgressBar) rootView
				.findViewById(R.id.batteryStatusBar);
		this.batteryStatus.setIndeterminate(false);
		this.batteryStatus.setMax(this.BATTERY_MAX);
		this.batteryStatus.setProgress(this.BATTERY_MIN);

		this.batteryStatus.setOnClickListener(this); // for battery level reset

		return rootView;
	}

	private void mpollBattery(int interval) {
		final ConnectActivity current = this;
		mBatteryTimer = new Timer();
		mBatteryTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (nxtConnected) {
					current.setBatteryMeter(current.getBatteryLevel());
				}
			}
		}, 0, interval);
	}

	public void connectToDevice() {
		try {
			this.bd = this.myObject.getBt();

			this.socket = this.bd.createRfcommSocketToServiceRecord(UUID
					.fromString(this.SPP_UUID));
			this.socket.connect();
		} catch (IOException e) {
			Log.e(TAG,
					"Error interacting with remote device -> " + e.getMessage());
			Toast.makeText(getActivity(), "Yikes! That didn't work.",
					Toast.LENGTH_LONG).show();
			return;
		}

		try {
			this.is = this.socket.getInputStream();
			this.os = this.socket.getOutputStream();

			this.myObject.setIs(this.is);
			this.myObject.setOs(this.os);

		} catch (IOException e) {
			this.is = null;
			this.os = null;
			this.disconnectNXT(null);
			Toast.makeText(getActivity(), "You crossed the streams!",
					Toast.LENGTH_LONG).show();
			return;
		}

		this.connectButton.setVisibility(View.GONE);
		this.disconnectButton.setVisibility(View.VISIBLE);

		// this.setBatteryMeter(this.getBatteryLevel());
		this.btImage.setImageAlpha(this.IMAGE_OPAQUE);
		this.statusLabel.setText(this.getResources().getString(
				R.string.nxtConnected)
				+ bd.getName());
		this.statusLabel.setTextColor(this.getResources().getColor(
				color.holo_orange_light));
		this.nxtConnected = true;

		// Check for preference for drive power
		SharedPreferences preferences = this.getActivity()
				.getSharedPreferences("GetPrefs", 0);
		String getBattFlag = (preferences.getString("bt", "false"));
		if (getBattFlag.equals("true"))
			batteryPollInterval = 60000;
		else
			batteryPollInterval = 60000; //Default battery level..?
		this.mpollBattery(this.batteryPollInterval);

		Log.i(TAG, "Connected with " + this.bd.getName());
	}

	public void disconnectNXT(View v) {
		try {
			Log.i(TAG,
					"Attempting to break BT connection of " + this.bd.getName());
			this.socket.close();
			this.is.close();
			this.os.close();
			Log.i(TAG, "BT connection of " + this.bd.getName()
					+ " is disconnected");
		} catch (Exception e) {
			Log.e(TAG, "Error in disconnect -> " + e.getMessage());
		}

		this.connectButton.setVisibility(View.VISIBLE);
		this.disconnectButton.setVisibility(View.GONE);
		this.btImage.setImageAlpha(this.IMAGE_TRANSPARENT);
		this.statusLabel.setText(R.string.nxtDisconnected);
		this.batteryStatus.setProgress(this.BATTERY_MIN);
		this.statusLabel
				.setTextColor(this.getResources().getColor(color.white));
		this.singButton.setVisibility(View.INVISIBLE);
		this.nxtConnected = false;
	}

	private int getBatteryLevel() {
		byte[] response = new byte[7];
		try {

			byte[] buffer = new byte[4];

			// request battery level
			buffer[0] = 2; // length lsb
			buffer[1] = 0; // length msb
			buffer[2] = 0x00; // actual
			buffer[3] = 0x0B; // message

			this.os.write(buffer);
			this.os.flush();

			// receive battery level
			response[0] = (byte) is.read(); // length lsb
			response[1] = (byte) is.read(); // length msb
			response[2] = (byte) is.read(); // will be 2
			response[3] = (byte) is.read(); // will be 11 -> 0x0B
			response[4] = (byte) is.read(); // Status byte. 0 = successful.
			response[5] = (byte) is.read(); // battery level lsb
			response[6] = (byte) is.read(); // bettery level msb

		} catch (Exception e) {
			Log.e(TAG, "Error getting battery level(" + e.getMessage() + ")");
			return -1;
		}

		// converting unsigned word to an int
		int responseVoltage = (0xFF & response[5])
				| ((0xFF & response[6]) << 8);

		return responseVoltage;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_BLUETOOTH_ID) {
			if (resultCode == Activity.RESULT_OK) {
				this.connectToDevice();
			}
		}
	}

	private class DialogClickListener implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				BluetoothAdapter.getDefaultAdapter().enable();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				break;
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.connectButton):
			if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage(
						"Your Bluetooth is not enabled, would you like to enable it now?")
						.setPositiveButton("Yes", new DialogClickListener())
						.setNegativeButton("No", new DialogClickListener())
						.show();
			} else {
				Intent i = new Intent(getActivity(),
						SelectBluetoothDeviceActivity.class);
				this.startActivityForResult(i, PICK_BLUETOOTH_ID);
			}
			break;
		case (R.id.disconnectButton):
			this.disconnectNXT(v);
			break;
		case (R.id.singButton):
			singASong();
			break;
		case (R.id.batteryStatusBar):
			this.setBatteryMeter(this.getBatteryLevel());
			break;
		}

	}

	private void singASong() {
		try {

			byte[] buffer = new byte[8];

			buffer[0] = 6; // length lsb
			buffer[1] = 0; // length msb
			buffer[2] = 0x00; // actual
			buffer[3] = 0x03; // message
			buffer[4] = -72; // freq lsb
			buffer[5] = 1; // freq msb
			buffer[6] = -24; // duration in ms lsb
			buffer[7] = 3; // duration in ms msb
			os.write(buffer);
			os.flush();
		} catch (Exception e) {
			Log.e(TAG, "Error singing :-( (" + e.getMessage() + ")");
		}
	}

	private void setBatteryMeter(int voltage) {
		double batteryLevel = voltage / this.MAX_MILLI_VOLTS;
		int batteryProgress = (int) (batteryLevel * 100);
		this.batteryStatus.setProgress(batteryProgress);
	}
}
