package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
