package lebe.lebeprototyp02;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import lebe.lebeprototyp02.gui.control.GUIController;
import lebe.lebeprototyp02.gui.control.PagerAdapter;


public class MainActivity extends AppCompatActivity {

     /**
     * GUI - Container für die Fragments
     */
    private ViewPager viewPager;
    /**
     * GUI - Entscheidet, welche Navigatonspunkte vorhanden sind und welches Fragment dazu geladen wird
     */
    private PagerAdapter pagerAdapter;
    /**
     * GUI - Navigationsleiste
     */
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    /**
     * GUI - Setzt das gewähte Design um
     */
    private GUIController guiController;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        startService(new Intent(this, MessageBroker.class));

        /**
         * GUI - Initialisierung - Startet hier
         */
        this.initializeGUI();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * Initialisiert das Design und die Design-Elemente für die MainActivity.<br>
     * Diese Methode nimmt den GUIController von der LoginActivity entgegen und setzt das Design für die MainActivity.<br>
     * Außerdem wird die Navigationsleiste der MainActivity erstellt.
     */
    private void initializeGUI(){
        /**
         * Nimmt das GUIController Object von der LoginActivity entgegen
         */
        guiController = (GUIController) getIntent().getSerializableExtra("gui");

        /**
         * Setzt das Design für die Activity
         */
        if (guiController != null) {
            guiController.updateGUI(getWindow().getDecorView().getRootView(), this);
        }

        /**
         * Erstellt und Initiliasiert den ViewPager und setzt einen ChangeListener auf ihn, so dass bei jedem neugeladenen
         * Fragment die GUI angepasst wird
         */
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        final android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        this.viewPager.addOnLayoutChangeListener((new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                if (guiController != null) {
                    guiController.updateGUI(getWindow().getDecorView().getRootView(), pagerAdapter.getActiveFragment(viewPager, fm, viewPager.getCurrentItem()));
                }
            }
        }));

        this.pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.PageSliderTabs);

        this.pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(pagerAdapter);
        this.pagerSlidingTabStrip.setViewPager(viewPager);

    }

}
