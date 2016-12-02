package lebe.lebeprototyp02.gui.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


    private final String urlString = "http://lebe-app.hol.es/dbabfrage/jsontestv2.php";
    private JSONArray marketAsJSONArray;


    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        TestAsyncTask testTask = new TestAsyncTask(this.getContext(), urlString);
        testTask.execute();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        marketview = (ListView) getView().findViewById(R.id.listview_market);

        TestAsyncTask testTask = new TestAsyncTask(this.getContext(), urlString);
        testTask.execute();

        CVAMarket adapter = new CVAMarket(this.getContext(), datensatz);

        marketview.setAdapter(adapter);

    }

    //----------------------------------------------------------------------------------------------------
    public class TestAsyncTask extends AsyncTask<Void, Void, String> {
        private Context mContext;
        private String mUrl;

        private ProgressDialog progressDialog = new ProgressDialog(getContext());

        public TestAsyncTask(Context context, String url) {
            mContext = context;
            mUrl = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog.setMessage("Downloade Daten...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    TestAsyncTask.this.cancel(true);
                }
            });

        }

        @Override
        protected String doInBackground(Void... params) {
            //hier wird die komplette seite als String geholt
            String resultString = null;
            resultString = getJSON(mUrl);

            return resultString;
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);

            progressDialog.dismiss();

            //hier dann die objekte mit jsondaten befüllen


            try {
                JSONObject json = new JSONObject(strings);
                marketAsJSONArray = json.getJSONArray("market");


                JSONObject temp = marketAsJSONArray.getJSONObject(0);

                TextView name = (TextView) getView().findViewById(R.id.market_Name);

                //marketAsJSON.get
                // name.setText(marketAsJSONArray.get(0).);

                TextView link = (TextView) getView().findViewById(R.id.market_beschreibung);
                // link.setText(json.get("pfad").toString());

                for (int i = 0; i < marketAsJSONArray.length(); i++) {
                    JSONObject c = marketAsJSONArray.getJSONObject(i);
                    MarketItem tempItem = new MarketItem();

                    tempItem.setName(c.getString("name"));
                    tempItem.setDiscription(c.getString("pfadApp"));
                    tempItem.setDdlpath(c.getString("pfadApp"));
                    datensatz.add(tempItem);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //dynamictext.setText(strings);
        }

        public String getJSON(String url) {
            HttpURLConnection c = null;
            try {
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.connect();
                int status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (Exception ex) {
                return ex.toString();
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        //disconnect error
                    }
                }
            }
            return null;
        }

    }

}