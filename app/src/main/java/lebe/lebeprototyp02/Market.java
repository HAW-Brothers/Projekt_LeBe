package lebe.lebeprototyp02;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Market extends AppCompatActivity {

    ArrayList<MarketApp> arrayList;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        arrayList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lvMarket);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //JSON String!!!
                new ReadJSON().execute("http://lebe-app.hol.es/dbabfrage/jsontestv2.php");
            }
        });
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);

                // "products bezieht sich auf den JSON Array namen
                JSONArray jsonArray = jsonObject.getJSONArray("market");

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject appObject = jsonArray.getJSONObject(i);
                    arrayList.add(new MarketApp(

                            //an das JSON anpassen!!!
                            appObject.getString("pfadBild"),
                            appObject.getString("name"),
                            appObject.getString("beschreibung")

                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapter adapter = new CustomListAdapter(
                    getApplicationContext(), R.layout.custom_list_layout, arrayList
            );
            lv.setAdapter(adapter);
        }
    }



    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
