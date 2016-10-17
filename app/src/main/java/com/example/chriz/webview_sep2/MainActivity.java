package com.example.chriz.webview_sep2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button b1;
    Button b2;
    private WebView wv1;
    private ListView lv;
    Boolean openWeb = false;
    Boolean openList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        lv=(ListView)findViewById(R.id.listView);
        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openWeb==false){
                    String url = "https://www.google.de/?gws_rd=ssl";
                    openWeb = true;
                    wv1.setVisibility(View.VISIBLE);
                    openList=false;
                    lv.setVisibility(View.INVISIBLE);
                    wv1.getSettings().setLoadsImagesAutomatically(true);
                    wv1.getSettings().setJavaScriptEnabled(true);
                    wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    wv1.loadUrl(url);
                }
                else{
                    openWeb=false;
                    wv1.setVisibility(View.INVISIBLE);
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openList==false){
                    openList = true;
                    lv.setVisibility(View.VISIBLE);
                    openWeb=false;
                    wv1.setVisibility(View.INVISIBLE);
                }
                else{
                    openList=false;
                    lv.setVisibility(View.INVISIBLE);
                }

            }
        });


        // statische array deklaration zum testen
        List<String> packageArray = new ArrayList<String>();
        packageArray.add("com.whatsapp");
        packageArray.add("com.legendarya.helloandroid");
        packageArray.add("com.discord");
        packageArray.add("de.akquinet.campusapp.haw_hamburg");
        packageArray.add("com.twitter.android");
        packageArray.add("com.andrewshu.android.reddit");
        packageArray.add("yumekan.android.devcalc");
        packageArray.add("test");
        packageArray.add("zum");
        packageArray.add("auffuellen");
        packageArray.add("der");
        packageArray.add("liste");
        packageArray.add("wegen");
        packageArray.add("scrollen");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                packageArray );

        lv.setAdapter(arrayAdapter);
        lv.setClickable(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adView, View view, int position, long id){
                openApp(getApplicationContext(),lv.getItemAtPosition(position).toString());
            }
        });
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
            //throw new PackageManager.NameNotFoundException();
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }
}
