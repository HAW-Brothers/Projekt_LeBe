package lebe.lebeprototyp02.gui.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    private final String urlString = "http://lebe-app.hol.es/dbabfrage/jsontestv2.php";


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


      /*  MarketItem item = new MarketItem();
        item.setName("hallo");
        item.setDiscription("beschreibung");
        item.setImgpath("https://de.wikipedia.org/wiki/Federal_Bureau_of_Investigation#/media/File:Federal_Bureau_of_Investigation.svg");
        datensatz.add(item);*/


        TestAsyncTask testTask = new TestAsyncTask(getContext(), urlString);
        testTask.execute();



        marketview = (ListView) getView().findViewById(R.id.listview_market);

        CVAMarket adapter = new CVAMarket(this.getContext(),datensatz);

        marketview.setAdapter(adapter);

    }


    //-------------------------------------------------------------------------------------------------------------------------------------------

    public class TestAsyncTask extends AsyncTask<Void, Void, String> {
        private Context mContext;
        private String mUrl;
        private TextView dynamictext;
        private ProgressDialog progressDialog = new ProgressDialog(getContext());


        private final String urlString = "http://lebe-app.hol.es/dbabfrage/jsontestv2.php";
        private JSONArray marketAsJSONArray;


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
                MarketItem item = new MarketItem();

                for (int i=0;i<marketAsJSONArray.length();i++){
                    json = marketAsJSONArray.getJSONObject(i);


                    item = new MarketItem();
                    item.setName(json.getString("name"));
                    item.setDiscription(json.getString("beschreibung"));
                    item.setImgpath(json.getString("pfadBild"));
                    item.setDdlpath(json.getString("pfadApp"));
                    datensatz.add(item);


                }

                //JSONObject temp = marketAsJSONArray.getJSONObject(0);



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
                            sb.append(line+"\n");
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
