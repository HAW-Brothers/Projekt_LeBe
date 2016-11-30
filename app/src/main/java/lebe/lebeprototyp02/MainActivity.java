package lebe.lebeprototyp02;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
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
    /*
    Button b1;
    Button b2;
    Button bUserview;
    private WebView wv1;
    private ListView lv;
    Boolean openWeb = false;
    Boolean openList = false;
    private List<ApplicationDetail> applications;
    */


    /*
    GUI
     */
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    GUIController guiController;



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Nimmt das GUIController Object von der LoginActivity entgegen
         */
        guiController = (GUIController) getIntent().getSerializableExtra("gui");
        /*
        GUIController guiController
        Setzt das Design für die Activity -CG
         */
        if(guiController != null){
            guiController.handleMainInterface(getWindow().getDecorView().getRootView());
        }





        /*
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        bUserview = (Button) findViewById(R.id.bUser);
        bUserview.setText(R.string.button_mainActivity_userview);
        lv = (ListView) findViewById(R.id.listView);
        wv1 = (WebView) findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openWeb) {

                    //ipv6 kann nicht aufgelöst werden
                    String url = "http://lebe-app.hol.es/apps/ ";
                    openWeb = true;
                    wv1.setVisibility(View.VISIBLE);
                    openList = false;
                    lv.setVisibility(View.INVISIBLE);
                    wv1.getSettings().setLoadsImagesAutomatically(true);
                    wv1.getSettings().setJavaScriptEnabled(true);
                    wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    wv1.loadUrl(url);
                } else {
                    openWeb = false;
                    wv1.setVisibility(View.INVISIBLE);
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openList) {
                    openList = true;
                    lv.setVisibility(View.VISIBLE);
                    openWeb = false;
                    wv1.setVisibility(View.INVISIBLE);
                } else {
                    openList = false;
                    lv.setVisibility(View.INVISIBLE);
                }

            }
        });


        bUserview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserView.class);
                startActivity(intent);
            }
        });


        loadApplication();

        ArrayAdapter<ApplicationDetail> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                applications);

        lv.setAdapter(arrayAdapter);
        lv.setClickable(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adView, View view, int position, long id) {
                openPlugin(getApplicationContext(),
                        ((ApplicationDetail) lv.getItemAtPosition(position))
                                .getName().toString());
            }
        });

         */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();




        /*
        GUI
         */
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this.guiController);
        final android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        viewPager.addOnLayoutChangeListener((new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                if(guiController != null){
                    guiController.updateGUIFragment(viewPager.getRootView().findViewById(R.id.pager),
                            pagerAdapter.getActiveFragment(viewPager,fm , viewPager.getCurrentItem()));
                }
            }
        }));


        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.PageSliderTabs);
        viewPager.setAdapter(pagerAdapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
        startService(new Intent(this, MessageBroker.class));

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

    /*
    public static void openPlugin(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                throw new PackageManager.NameNotFoundException();
            }
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {}
    }

    // sucht nach angegeben CATEGORY tag: <category android:name="lebe.PLUGIN" /> (in Manifest) und sammelt alle

    private void loadApplication() {
        // package manager is used to retrieve the system's packages
        PackageManager packageManager = getPackageManager();
        applications = new ArrayList<>();
        // we need an intent that will be used to load the packages
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        // in this case we want to load all packages wtih PLUGIN category
        intent.addCategory("lebe.PLUGIN");
        List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(intent, 0);
        // for each one we create a custom list view item
        for (ResolveInfo resolveInfo : availableActivities) {
            ApplicationDetail applicationDetail = new ApplicationDetail(
                    resolveInfo.loadLabel(packageManager),
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.loadIcon(packageManager));
            applications.add(applicationDetail);
        }
    }
    */
}
