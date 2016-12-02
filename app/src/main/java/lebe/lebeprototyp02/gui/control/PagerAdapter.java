package lebe.lebeprototyp02.gui.control;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import lebe.lebeprototyp02.gui.fragments.HomeFragment;
import lebe.lebeprototyp02.gui.fragments.MarketFragment;
import lebe.lebeprototyp02.gui.fragments.StoreFragment;
import lebe.lebeprototyp02.gui.fragments.UserViewFragment;

/**
 * Created by Graumann on 08.11.2016.
 * <br><br>
 * Der PageAdapter hat die Aufgabe die Funktionalität des PageSliders zu bestimmen.<br>
 * Hier ist es möglich die Navigationsleiste um Elemente zu erweitern oder sie zu entfernen.<br>
 * Des Weiteren lässt sich hier zuordnen welcher Navigationsreiter welches Fragment öffnet.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private Fragment f = null;

    /**
     * Konstruiert einen PagerAdapter
     * @param fm FragmentManager der Applikation
     */
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Identifiziert ein Fragment auf basis seiner Position in der Navigationsleiste und gibt es zurück.
     * @param position Position in der Navigationsleiste
     * @return Gibt ein Fragment auf basis seiner Position in der Navigationsleiste zurück
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
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
                break;
        }
        return f;
    }

    /**
     * Gibt die Anzahl an möglichen verschiedenen Fragments zurück.
     * @return Anzahl an möglichen verschiedenen Fragments
     */
    @Override
    public int getCount() { // Return the number of pages
        return 3;
    }

    /**
     * Bestimmt den String, welcher in der Navigationleiste für die einzelnen Fragments angezeigt wird.
     * @param position Position des Fragments
     * @return Name det Navigationsposition
    */
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

    /**
     * Identifiziert das aktive Fragment-Object des PageViewer und gibt dieses Zurück.
     * @param container ViewPager - Container für die Fragments
     * @param fm FragmentManager der Applikation
     * @param position Aktuell gewählte Position des PageAdapters
     * @return Aktives Fragment
     */
    public Fragment getActiveFragment(ViewPager container, FragmentManager fm, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  fm.findFragmentByTag(name);
    }

    /**
     * Erstellt den Tag(Namen) eines Fragments damit er mit findFragmentByTag() gefunden werden kann.
     * @param viewId ViewPager container ID
     * @param index Postion im PageAdapter
     * @return Fragmenttag
     */
    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}