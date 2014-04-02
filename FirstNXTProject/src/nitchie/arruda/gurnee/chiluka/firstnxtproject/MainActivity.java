package nitchie.arruda.gurnee.chiluka.firstnxtproject;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity  {

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
        spec2.setIndicator("Drive");
        tabHost.addTab(spec2);

	}
}
