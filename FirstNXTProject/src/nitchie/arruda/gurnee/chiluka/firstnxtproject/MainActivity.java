package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager myViewPager;
	private MyPagerAdapter myPagerAdapter;
	private ActionBar myActionBar;
	private final int PREF_ID = 2;

	private String[] tabs = { "Connect", "Drive", "Sensors", "Voice", "Gyro",
			"Accel" };
	private int[] icons = { R.drawable.icon_connect, R.drawable.icon_drive,
			R.drawable.icon_sensors, R.drawable.icon_voice,
			R.drawable.icon_gyro, R.drawable.icon_accel };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.main_view);

		myViewPager = (ViewPager) findViewById(R.id.pager);

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		myViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

		for (int i = 0; i < tabs.length; i++) {
			myActionBar.addTab(myActionBar.newTab().setText(tabs[i])
					.setIcon(icons[i]).setTabListener(this));
		}

		/*
		for (String s : tabs) {
			myActionBar.addTab(myActionBar.newTab().setText(s)
					.setTabListener(this));
		}
		*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.aboutapp:
			Intent intent1 = new Intent(this, AppDetails_Activity.class);
			startActivity(intent1);
			break;
		case R.id.preference:
			Intent intent2 = new Intent(this, Preference_Activity.class);
			// this.startActivity(intent);
			startActivityForResult(intent2, PREF_ID);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PREF_ID) {
			SharedPreferences myPref = getSharedPreferences("GetPrefs",
					MODE_PRIVATE);
			// String batteryFlag = (myPref.getString("bt", "false"));
			// String defSpeedFlag = (myPref.getString("sp", "false"));
			// String timeIntravel = (myPref.getString("updates_interval",
			// "0"));
			// System.out.println("batteryFlag status::"+batteryFlag);
			// System.out.println("defSpeedFlag status::"+defSpeedFlag);
			// System.out.println("timeIntravel----"+timeIntravel);

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		myViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}
