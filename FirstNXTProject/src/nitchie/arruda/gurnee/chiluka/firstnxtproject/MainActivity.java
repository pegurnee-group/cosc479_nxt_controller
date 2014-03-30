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
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,OnTouchListener {

	private final String TAG = "NXT Project 1";
	private final String ROBOTNAME = "herb-E";

	// UI Components
	Button connectButton;
	Button disconnectButton;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
		
		btConnected = false;

		setupTabs();
		driveDirections();
		connectButton = (Button) this.findViewById(R.id.connectButton);
		connectButton.setOnClickListener(this);

		disconnectButton = (Button) this.findViewById(R.id.disconnectButton);
		disconnectButton.setOnClickListener(this);
		disconnectButton.setVisibility(View.GONE);

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

	public void setupTabs() {
		// Set up tabbars
		Resources res = getResources();
		final TabHost tabHost = (TabHost) findViewById(R.id.ui_1_TabHost);

		// need setup since uses @+id/ui_1_TabHost instead of android:id/tabhost
		tabHost.setup();

		// Set up connect view tab
		TabHost.TabSpec spec = tabHost.newTabSpec("tag1");
		getLayoutInflater().inflate(R.layout.connect_view,
				tabHost.getTabContentView(), true);
		spec.setContent(R.id.connect_view_layout);
		spec.setIndicator("Connect");
		tabHost.addTab(spec);

		// Set up drive view tab
		spec = tabHost.newTabSpec("tag2");
		getLayoutInflater().inflate(R.layout.drive_view,
				tabHost.getTabContentView(), true);
		spec.setContent(R.id.drive_view_layout);
		spec.setIndicator("Drive");
		tabHost.addTab(spec);
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void driveDirections()
	{
		Button goFwd = (Button) findViewById(R.id.button1);
		goFwd.setOnTouchListener(this);
	}
	
	public boolean onTouch(View view,MotionEvent event)
	{
		int action;

		
		Log.i("NXT", "onTouch event: " + Integer.toString(event.getAction()));
		 action = event.getAction();
        //if ((action == MotionEvent.ACTION_DOWN) || (action == MotionEvent.ACTION_MOVE)) {
        if (action == MotionEvent.ACTION_DOWN) {
       	 Log.i("NXT", "Action1 started " );
       	 if(flag==false)
       	 {
       		 MoveMotor(1, 75, 0x20);
       		 MoveMotor(2, 75, 0x20);
       	 }
       	 
       	 flag = true;
       	 
           
        } else if ((action == MotionEvent.ACTION_UP) ) {
       	 Log.i("NXT", "Action1 Stopped " ); 
       	 flag = false;
       	 MoveMotor(1, 75, 0x00);
       	 MoveMotor(2, 75, 0x00);
        }
        return true;
	}
	
	
	private void MoveMotor(int motor,int speed, int state) {
		try {
			//Log.i(tag,"Attempting to move [" + motor + " @ " + speed + "]");
			
			byte[] buffer = new byte[15];
			
			buffer[0] = (byte) (15-2);			// length lsb
			buffer[1] = 0;						// length msb
			buffer[2] =  0;						// direct command (with response)
			buffer[3] = 0x04;					// set output state
			buffer[4] = (byte) motor;			// output 1 (motor B)
			buffer[5] = (byte) speed;			// power
			buffer[6] = 1 + 2;					// motor on + brake between PWM
			buffer[7] = 0;						// regulation
			buffer[8] = 0;						// turn ration??
			buffer[9] = (byte) state; //0x20;	// run state
			buffer[10] = 0;
			buffer[11] = 0;
			buffer[12] = 0;
			buffer[13] = 0;
			buffer[14] = 0;

			//os.write(buffer);
			//os.flush();
			
		}
		catch (Exception e) {
			//Log.e(tag,"Error in MoveForward(" + e.getMessage() + ")");
		}		
	}
	

}
