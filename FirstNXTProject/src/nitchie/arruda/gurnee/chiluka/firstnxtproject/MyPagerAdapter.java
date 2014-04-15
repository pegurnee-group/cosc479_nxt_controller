package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	final int TAB_NUM = 6;
	
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
        case 0:
        	return new ConnectFragment();
        case 1:        	
        	return new ManualDriveFragment();
        case 2:
        	return new SensorListFragment();
        case 3:
        	return new VoiceDriveFragment();
        case 4:
        	return new GyroscopeDriveFragment();
        case 5:
        	return new AccelerometerDriveFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TAB_NUM;
	}
}
