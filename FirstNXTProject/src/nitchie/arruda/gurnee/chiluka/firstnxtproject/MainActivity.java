package nitchie.arruda.gurnee.chiluka.firstnxtproject;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity  {


/*

	private final String TAG = "NXT Project 1";
	private final String ROBOTNAME = "Columbus";

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

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);


		
		btConnected = false;

		setupTabs();
		
		btImage = (ImageView) findViewById(R.id.imageView1);
		btImage.setImageAlpha(50);
		
		driveDirections();
		//driveDirections();

		
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
	public void setupTabs() {
		// Set up tabbars

			TabHost tabHost = getTabHost();
	 	    
	        // Tab for Connect
	        TabSpec spec = tabHost.newTabSpec("Connect");
	       
	        spec.setIndicator("Connect", getResources().getDrawable(R.drawable.blue_button));
	        Intent i = new Intent(this, Connect.class);
	        spec.setContent(i);
	        tabHost.addTab(spec);
		
	        // Tab for Drive
	        TabSpec drivespec = tabHost.newTabSpec("Drive");
	        drivespec.setIndicator("Drive", getResources().getDrawable(R.drawable.blue_button));
	        Intent driveIntent = new Intent(this, driveModule.class);
	        drivespec.setContent(driveIntent);
	        tabHost.addTab(drivespec);
	        
		Resources res = getResources();
		final TabHost tabHost = (TabHost) findViewById(R.id.ui_1_TabHost);

		// need setup since uses @+id/ui_1_TabHost instead of android:id/tabhost
		tabHost.setup();

		// Set up connect view tab
		TabSpec spec1 = tabHost.newTabSpec("tag1");
		getLayoutInflater().inflate(R.layout.connect_view,
				tabHost.getTabContentView(), true);
		spec1.setContent(R.id.connect_view_layout);
		spec1.setIndicator("Connect");
		tabHost.addTab(spec1);
		
		Log.i("fine!","");

		// Set up drive view tab
		TabSpec spec2 = tabHost.newTabSpec("tag2");
		//getLayoutInflater().inflate(R.layout.drive_view,
			//	tabHost.getTabContentView(), true);
		Intent  driveLayout = new Intent(this, DriveActivity.class);
		spec2.setContent(driveLayout);
		//spec.setContent(R.id.drive_view_layout);
		spec2.setIndicator("Drive");
		tabHost.addTab(spec2);
	}
/*
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
 	 	    	btImage.setImageAlpha(255);
 	 	    	statusLabel.setText(R.string.nxtConnected);
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
		btImage.setImageAlpha(100);
		statusLabel.setText(R.string.nxtDisconnected);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	//Set up Drive View Controls
	public void driveDirections()
	{
		Button goFwd = (Button) findViewById(R.id.button1);
		goFwd.setOnTouchListener(this);

		Button goBwd = (Button) findViewById(R.id.button2);
		goBwd.setOnTouchListener(this);
>>>>>>> 33fe26cc527a2e516ec187013cdf179284ae665d
		
		TabHost tabHost;
		
		 tabHost = getTabHost();
		
		// Set up connect view tab
		TabSpec spec1 = tabHost.newTabSpec("tag1");
		Intent connectIntent = new Intent(this,ConnectActivity.class);
		spec1.setIndicator("Connect");
		spec1.setContent(connectIntent);
		tabHost.addTab(spec1);
	

		// Set up drive view tab
		TabSpec spec2 = tabHost.newTabSpec("tag2");
	
		Intent  driveIntent = new Intent(this, DriveActivity.class);
		spec2.setIndicator("Drive");
		spec2.setContent(driveIntent);
		tabHost.addTab(spec2);


	}
	
*/
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
       
        Resources res = getResources();
        //final TabHost tabHost = (TabHost) findViewById(R.id.ui_1_TabHost);

        // need setup since uses @+id/ui_1_TabHost instead of android:id/tabhost
        //tabHost.setup();   
        TabHost tabHost = getTabHost();
       

        // Set up connect view tab
        TabSpec spec1 = tabHost.newTabSpec("tag1");
        Intent connectIntent = new Intent(this,ConnectActivity.class);
        spec1.setContent(connectIntent);
        spec1.setIndicator("Connect");
        tabHost.addTab(spec1);
  
        // Set up drive view tab
        TabSpec spec2 = tabHost.newTabSpec("tag2");
   
        Intent  driveIntent = new Intent(this, DriveActivity.class);
        spec2.setContent(driveIntent);
        //spec2.setContent(R.id.drive_view_layout);
        spec2.setIndicator("Drive", res.getDrawable(R.drawable.steering_wheel));
        tabHost.addTab(spec2);

	}
}
