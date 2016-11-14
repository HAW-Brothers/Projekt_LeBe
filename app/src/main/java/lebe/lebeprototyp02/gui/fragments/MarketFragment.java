package lebe.lebeprototyp02.gui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import lebe.lebeprototyp02.R;
import lebe.lebeprototyp02.market.CVAMarket;
import lebe.lebeprototyp02.market.MarketItem;

/**
 * Created by Höling on 13.11.2016.
 */

public class MarketFragment extends Fragment {

    private ListView marketview;
    private ArrayList<MarketItem> datensatz;

    public MarketFragment() {
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


        marketview = (ListView) getView().findViewById(R.id.listview_market);

        CVAMarket adapter = new CVAMarket(this.getContext(),datensatz);

        marketview.setAdapter(adapter);

    }



}
