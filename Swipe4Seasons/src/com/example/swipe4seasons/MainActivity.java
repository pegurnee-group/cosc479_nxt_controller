package com.example.swipe4seasons;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager myViewPager;
	private MyPagerAdapter myPagerAdapter;
	private ActionBar myActionBar;
	// Tab titles
	private String[] tabs = { "Spring", "Summber", "Fall", "Winter" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Initilization
        myViewPager = (ViewPager) findViewById(R.id.pager);
        myActionBar = getActionBar();
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
 
        myViewPager.setAdapter(myPagerAdapter);
        myActionBar.setHomeButtonEnabled(false);
        myActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
 
        // Adding Tabs
        for (String tab_name : tabs) {
            myActionBar.addTab(myActionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
