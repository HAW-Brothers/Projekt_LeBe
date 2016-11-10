package lebe.lebeprototyp02.gui.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private Fragment f = null;

    @Override
    public Fragment getItem(int position) { // Returns Fragment based on position
        switch (position) {
            case 0:
                f = new BlankFragment();
                break;
            case 1:
                f = new BlankFragment();
                break;
            case 2:
                f = new UserViewFragment();
                break;
        }
        return f;
    }

    @Override
    public int getCount() { // Return the number of pages
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) { // Set the tab text
        if (position == 0) {
            return "Home";
        }
        if (position == 1) {
            return "Store";
        }
        if (position == 2) {
            return "Settings";
        }
        return getPageTitle(position);
    }
}