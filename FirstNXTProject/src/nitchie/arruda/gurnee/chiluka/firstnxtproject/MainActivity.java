package nitchie.arruda.gurnee.chiluka.firstnxtproject;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;




import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity  {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
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
	

}
