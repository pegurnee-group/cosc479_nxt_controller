package com.example.swipe4seasons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	final int SEASON_NUM = 4;
	
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
        case 0:
            // Spring fragment activity
            return new Tab1_SpringFragment();
        case 1:
            // Summer fragment activity
            return new Tab2_SummerFragment();
        case 2:
            // Fall fragment activity
            return new Tab3_FallFragment();
        case 3:
            // Spring fragment activity
            return new Tab4_WinterFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return SEASON_NUM;
	}
}
