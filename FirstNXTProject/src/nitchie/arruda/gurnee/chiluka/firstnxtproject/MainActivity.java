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
import android.content.BroadcastReceiver;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

public class MainActivity extends Activity implements OnClickListener {
	
	private final String TAG = "NXT Project 1";
	private final String ROBOTNAME = "herb-E";
	
	//Bluetooth Variables
	private BluetoothAdapter btInterface;
	private Set<BluetoothDevice> pairedDevices;
	private BluetoothSocket socket;
	private BluetoothDevice bd;
	private InputStream is = null;
	private OutputStream os = null;
	private BroadcastReceiver btMonitor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
		setupTabs();
		
		Button connectButton = (Button) this.findViewById(R.id.connectButton);
		connectButton.setOnClickListener(this);
		
		Button disconnectButton = (Button) this.findViewById(R.id.disconnectButton);
		disconnectButton.setOnClickListener(this);

	}
	
	public void setupTabs() {
		// Set up tabbars
		Resources res = getResources();
		final TabHost tabHost = (TabHost) findViewById(R.id.ui_1_TabHost);
		
	    // need setup since uses @+id/ui_1_TabHost instead of android:id/tabhost
 	    tabHost.setup();
 	    
 	    // Set up drive view tab
 	    TabHost.TabSpec spec = tabHost.newTabSpec("tag1");
 	    getLayoutInflater().inflate(R.layout.drive_view, tabHost.getTabContentView(), true);
 	    spec.setContent(R.id.drive_view_layout);
 	    spec.setIndicator("View");
 	    tabHost.addTab(spec);
 	    
 	    // Set up connect view tab
 	    spec = tabHost.newTabSpec("tag2");
 	    getLayoutInflater().inflate(R.layout.connect_view, tabHost.getTabContentView(), true);
 	    spec.setContent(R.id.connect_view_layout);
 	    spec.setIndicator("Connect");
 	    tabHost.addTab(spec);
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case(R.id.connectButton):
			connectToDevice();
			break;
		case(R.id.disconnectButton):
			
			break;
		}
	}
	
	public void connectToDevice() {
		btInterface = BluetoothAdapter.getDefaultAdapter();
 	    pairedDevices = btInterface.getBondedDevices();
 	    Iterator<BluetoothDevice> it = pairedDevices.iterator();
 	    while(it.hasNext()) {
 	    	bd = it.next();
 	    	
 	    	if (bd.getName().equalsIgnoreCase(ROBOTNAME)) {
 	 	    	try {
 	 	    		socket = bd.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
 	 	    		socket.connect();
 	 	    	} catch (IOException e) {
 	 	    		Log.e(TAG, "Error interacting with remote device -> " + e.getMessage());
 	 	    	}
 	 	    	
 	 	    	try {
 	 	    		is = socket.getInputStream();
 	 	    		os = socket.getOutputStream();
 	 	    	} catch (IOException e) {
 	 	    		is = null;
 	 	    		os = null;
 	 	    		disconnectNXT(null);
 	 	    	}
 	 	    	
 	 	    	Log.i(TAG, "Connected with " + bd.getName());
 	 	    	return;
 	    	}
 	    }
	}
	
	public void disconnectNXT(View v) {
		try {
			Log.i(TAG,"Attempting to break BT connection of " + bd.getName());
			socket.close();
			is.close();
			os.close();
			Log.i(TAG, "BT connection of " + bd.getName() + " is disconnected");
		}
		catch (Exception e)	{
			Log.e(TAG,"Error in disconnect -> " + e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
