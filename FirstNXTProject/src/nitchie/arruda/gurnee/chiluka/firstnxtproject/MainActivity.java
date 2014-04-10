package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener  {
	
	private ViewPager myViewPager;
	private MyPagerAdapter myPagerAdapter;
	private ActionBar myActionBar;
	
//	private String[] tabs = {"Connect", "Drive", "Sensors"};
	
	private Map<String, Integer> tabs;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.main_view);
        
        tabs = new HashMap<String, Integer>();
        tabs.put("Connect", R.drawable.connect_icon);
        tabs.put("Drive", R.drawable.drive_icon);
        tabs.put("Sensors", R.drawable.connect_icon);
       
        myViewPager = (ViewPager) findViewById(R.id.pager);

        /**
         * on swiping the viewpager make respective tab selected
         * */
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                myActionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        
        myActionBar = getActionBar();
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
 
        myViewPager.setAdapter(myPagerAdapter);
        myActionBar.setHomeButtonEnabled(false);
        myActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
        
        // Hide the title bar
        myActionBar.setDisplayShowTitleEnabled(false); 
        myActionBar.setDisplayShowHomeEnabled(false);
        
 
        // Adding Tabs
        
        for(Map.Entry<String, Integer> entry : tabs.entrySet()) {
        	myActionBar.addTab(myActionBar.newTab().setText(entry.getKey()).setIcon(entry.getValue())
                    .setTabListener(this));
        }

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		myViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}
}
