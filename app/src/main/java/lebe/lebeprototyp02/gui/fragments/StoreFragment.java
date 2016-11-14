package lebe.lebeprototyp02.gui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import java.util.ArrayList;

import lebe.lebeprototyp02.MainActivity;
import lebe.lebeprototyp02.R;
import lebe.lebeprototyp02.market.CVAMarket;
import lebe.lebeprototyp02.market.MarketItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

    private ListView marketview;
    private ArrayList<MarketItem> datensatz = new ArrayList<>();


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        MarketItem item = new MarketItem();
        item.setName("hallo");
        item.setDiscription("beschreibung");
        item.setImgpath("https://de.wikipedia.org/wiki/Federal_Bureau_of_Investigation#/media/File:Federal_Bureau_of_Investigation.svg");
        datensatz.add(item);

        marketview = (ListView) getView().findViewById(R.id.listview_market);

        CVAMarket adapter = new CVAMarket(this.getContext(),datensatz);

        marketview.setAdapter(adapter);

    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
