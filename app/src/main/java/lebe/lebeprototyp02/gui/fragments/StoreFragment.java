package lebe.lebeprototyp02.gui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import lebe.lebeprototyp02.MainActivity;
import lebe.lebeprototyp02.R;
import lebe.lebeprototyp02.gui.control.GUIController;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

    private WebView wv1;
    private GUIController guiController;


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        guiController.updateView(view);
        guiController.handelFragmentHome();


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        wv1 = (WebView) getView().findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());

        String url = "http://lebe-app.hol.es/apps/ ";
        wv1.setVisibility(View.VISIBLE);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);




    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    public void setGUIController(GUIController controller){
        this.guiController = controller;
    }


}
