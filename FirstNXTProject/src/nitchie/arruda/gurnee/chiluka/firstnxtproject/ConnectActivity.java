package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ConnectActivity extends  Activity implements  OnClickListener{
	
	private final String TAG = "NXT Project 1";
	private final String ROBOTNAME = "herb-E";

	// UI Components
	Button connectButton;
	Button disconnectButton;
	ImageView btImage;
	TextView statusLabel;


	// Bluetooth Variables
	private BluetoothAdapter btInterface;
	private Set<BluetoothDevice> pairedDevices;
	private BluetoothDevice bd;
	private BluetoothSocket socket;
	private InputStream is;
	private OutputStream os;

	// flag representing BT connection status
	private boolean btConnected;
	
	boolean flag = false;
	int mpower1 = 20;
	int mpower2 = 30;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.id.connect_view_layout);
			
		btConnected = false;

		connectButton = (Button) this.findViewById(R.id.connectButton);
		connectButton.setOnClickListener(this);

		disconnectButton = (Button) this.findViewById(R.id.disconnectButton);
		disconnectButton.setOnClickListener(this);
		disconnectButton.setVisibility(View.GONE);
		btImage = (ImageView) findViewById(R.id.imageView1);
		btImage.setImageAlpha(50);
		statusLabel = (TextView) findViewById(R.id.statusLabel);


	}
	
	private String getBatteryLevel() {
		byte[] response = new byte[7];
		try {
			
			byte[] buffer = new byte[4];
			
			buffer[0] = 2; // length lsb
			buffer[1] = 0; // length msb
			buffer[2] = 0x00;			// actual
			buffer[3] = 0x0B;			// message			

			os.write(buffer);
			os.flush();
			
			response[0] = (byte) is.read(); // length lsb
			response[1] = (byte) is.read(); // length msb
			response[2] = (byte) is.read(); // will be 2
			response[3] = (byte) is.read(); // will be 11 -> 0x0B
			response[4] = (byte) is.read(); // Status byte. 0 = successful.
			response[5] = (byte) is.read(); // battery level msb
			response[6] = (byte) is.read(); // bettery level lsb
			
		}
		catch (Exception e) {
			Log.e(TAG,"Error in MoveForward(" + e.getMessage() + ")");
			return null;
		}
		// unsigned!!!
		int responseVoltage = (response[5] * 256) + response[6];
		
		return "" + responseVoltage;
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.connectButton):
			connectToDevice();
			break;
		case (R.id.disconnectButton):
			disconnectNXT(v);
			break;
			
		}
	}

	public void connectToDevice() {
		btInterface = BluetoothAdapter.getDefaultAdapter();
		pairedDevices = btInterface.getBondedDevices();
		Iterator<BluetoothDevice> it = pairedDevices.iterator();
		while (it.hasNext()) {
			bd = it.next();

			if (bd.getName().equalsIgnoreCase(ROBOTNAME)) {
				try {
					socket = bd
							.createRfcommSocketToServiceRecord(UUID
									.fromString("00001101-0000-1000-8000-00805F9B34FB"));
					socket.connect();
				} catch (IOException e) {
					Log.e(TAG,
							"Error interacting with remote device -> "
									+ e.getMessage());
					return;
				}

				try {
					is = socket.getInputStream();
					os = socket.getOutputStream();
					
					DeviceData myObject = (DeviceData) DeviceData.getInstance();
					myObject.setIs(is);
					myObject.setOs(os);
					
				} catch (IOException e) {
					is = null;
					os = null;
					disconnectNXT(null);
					return;
				}
				
 	 	    	btConnected = true;
 	 	    	connectButton.setVisibility(View.GONE);
 	 	    	disconnectButton.setVisibility(View.VISIBLE);
 	 			TextView textView = (TextView) findViewById(R.id.textView1);
 	 			textView.setText(getBatteryLevel());
 	 	    	btImage.setImageAlpha(255);
 	 	    	statusLabel.setText(R.string.nxtConnected);

				Log.i(TAG, "Connected with " + bd.getName());
				return;
			}
		}
	}

	public void disconnectNXT(View v) {
		try {
			Log.i(TAG, "Attempting to break BT connection of " + bd.getName());
			socket.close();
			is.close();
			os.close();
			Log.i(TAG, "BT connection of " + bd.getName() + " is disconnected");
		} catch (Exception e) {
			Log.e(TAG, "Error in disconnect -> " + e.getMessage());
		}

		btConnected = false;
		connectButton.setVisibility(View.VISIBLE);
		disconnectButton.setVisibility(View.GONE);
		btImage.setImageAlpha(100);
		statusLabel.setText(R.string.nxtDisconnected);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
}