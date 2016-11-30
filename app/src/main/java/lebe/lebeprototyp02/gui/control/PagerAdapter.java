package lebe.lebeprototyp02.gui.control;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import lebe.lebeprototyp02.gui.fragments.HomeFragment;
import lebe.lebeprototyp02.gui.fragments.StoreFragment;
import lebe.lebeprototyp02.gui.fragments.UserViewFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    /*
    Der PageAdapter hat die Aufgabe die Funktionalität des PageSliders zu bestimmen
    -> Hier ist es möglich die Navigationsleiste anzupassen
     */
    GUIController guiController;

    public PagerAdapter(FragmentManager fm, GUIController guiController) {
        super(fm);
        this.guiController = guiController;
    }

    private Fragment f = null;

    @Override
    public Fragment getItem(int position) { // Returns Fragment based on position
        switch (position) {
            case 0:
                // Jedem Fragment muss der GUIController mit übergeben werden, damit das Design angepasst werden kann
                HomeFragment f1 = new HomeFragment();
                f = f1;
                break;
            case 1:
                StoreFragment f2 = new StoreFragment();
                f = f2;
                break;
            case 2:
                UserViewFragment f3 = new UserViewFragment();
                f = f3;
               // guiController.handleFragmentSettings("male");
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

    public Fragment getActiveFragment(ViewPager container, FragmentManager fm, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  fm.findFragmentByTag(name);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}