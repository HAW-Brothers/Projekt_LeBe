package lebe.lebeprototyp02.gui.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.util.concurrent.ExecutionException;

import lebe.lebeprototyp02.MainActivity;
import lebe.lebeprototyp02.R;
import lebe.lebeprototyp02.dbhelper.UserDB;
import lebe.lebeprototyp02.market.CVAMarket;
import lebe.lebeprototyp02.market.MarketItem;


/**
 * Diese Klasse stellt den Store dar
 */
public class StoreFragment extends Fragment {

    private int counter = 0;
    private ListView marketview;
    private ArrayList<MarketItem> datensatz = new ArrayList<>();
    private final String urlString = "http://lebe-app.hol.es/dbabfrage/jsontestv2.php";

    private ListView marketviewTop;
    private ArrayList<MarketItem> datensatzTop = new ArrayList<>();
    private final String urlStringTop = "http://lebe-app.hol.es/dbabfrage/jsontopddl.php";


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

      /*  MarketItem item = new MarketItem();
        item.setName("hallo");
        item.setDiscription("beschreibung");
        item.setImgpath("https://de.wikipedia.org/wiki/Federal_Bureau_of_Investigation#/media/File:Federal_Bureau_of_Investigation.svg");
        datensatz.add(item);*/

        String geburtsdatum = UserDB.getInstance().getGeburtsdatum();

        TestAsyncTask testTask = new TestAsyncTask(getContext(), urlString+"?alter="+geburtsdatum,datensatz);

        /*
          Es mmuss auf den Task gewartet werden, damit dieser die ListViews befüllen kann.
         */
        try {
            testTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        marketview = (ListView) getView().findViewById(R.id.lv_download);

        CVAMarket adapter = new CVAMarket(this.getContext(),datensatz);

        marketview.setAdapter(adapter);


        //--------------------ab hier die 2te liste
        marketviewTop = (ListView) getView().findViewById(R.id.lv_top_download);
        TestAsyncTask testTaskTop = new TestAsyncTask(getContext(), urlStringTop+"?alter="+geburtsdatum,datensatzTop);


        /*
          Es mmuss auf den Task gewartet werden, damit dieser die ListViews befüllen kann.
         */
        try {
            testTaskTop.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        CVAMarket adapterTop = new CVAMarket(this.getContext(),datensatzTop);

        marketviewTop.setAdapter(adapterTop);


    }


    //-------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Diese Interne Klasse startet einen Asynchronen Task im hintergrund um die Daten des markets (welche apps gibt es, Name, Downloadlink..)
     * im Hintergrund herunter zu laden
     */
    public class TestAsyncTask extends AsyncTask<Void, Void, String> {
        private Context mContext;
        private String mUrl;
        private TextView dynamictext;
        private ProgressDialog progressDialog = new ProgressDialog(getContext());


        private final String urlString = "http://lebe-app.hol.es/dbabfrage/jsontestv2.php?alter="+UserDB.getInstance().getGeburtsdatum();
        private JSONArray marketAsJSONArray;
        private ArrayList<MarketItem> datensaetze;


        /**
         * Der Konstruktor für einen neuen Asynchronen Task im Hintergrund
         * @param context
         * @param url
         * @param daten
         */
        public TestAsyncTask(Context context, String url,ArrayList<MarketItem> daten) {
            mContext = context;
            mUrl = url;
            datensaetze=daten;
        }


        /**
         * Diese Methode wird ausgeführt bevor die eigentliche Arbeit getan wird
         */
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

        /**
         * Hier verrichtet der Thread seine Arbeit. Hier wird ein JSON Objekt vom Webserver geholt und es wird geparst
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Void... params) {
            //hier wird die komplette seite als String geholt
            String resultString = null;
            resultString = getJSON(mUrl);

            return resultString;
        }

        /**
         * Diese Methode wird automatisch nach {@see doInBackground()} ausgeführt. Es wird ein MarketItem-Objekt mit den Daten
         * aus dem JSON Objekt.
         * @param strings
         */
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
                    datensaetze.add(item);


                }

                //JSONObject temp = marketAsJSONArray.getJSONObject(0);



            } catch (JSONException e) {
                e.printStackTrace();
            }


            //dynamictext.setText(strings);
        }

        /**
         * Diese Methode holt sich die Webseite als String und parst daraus ein JSON Objekt.
         * @param url Die URL die das JSON als HTML generiert
         * @return Das JSON aus der Webseite geparst
         */
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
