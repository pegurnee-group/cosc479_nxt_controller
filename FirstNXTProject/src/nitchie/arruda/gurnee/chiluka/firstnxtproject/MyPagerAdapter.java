package nitchie.arruda.gurnee.chiluka.firstnxtproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	final int TAB_NUM = 3;
	
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
        case 0:
        	return new ConnectActivity();
        case 1:
        	return new DriveActivity();
        case 2:
        	return new SensorActivity();
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TAB_NUM;
	}
}
