package com.example.swipe4seasons;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
        
        myActionBar.setDisplayShowTitleEnabled(false); 
        myActionBar.setDisplayShowHomeEnabled(false);
        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            myActionBar.addTab(myActionBar.newTab().setText(tab_name)
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
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		myViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
	
//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//          super.onWindowFocusChanged(hasFocus);
//      if (hasFocus) {
//    	  getWindow().getDecorView().setSystemUiVisibility(
//                  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                  | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                  | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                  | View.SYSTEM_UI_FLAG_FULLSCREEN
//                  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//           );
//      }
//	}

}
