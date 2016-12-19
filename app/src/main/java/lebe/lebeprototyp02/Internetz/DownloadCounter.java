package lebe.lebeprototyp02.Internetz;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Höling on 19.12.2016.
 */

public class DownloadCounter extends AsyncTask <String, Void, Void>{

    private Context mContext;

    @Override
    protected Void doInBackground(String... params) {
        String appname = params[0];
        appname=appname.replaceAll(" ","%20");



        HttpURLConnection c = null;
        try {
            URL u = new URL("http://lebe-app.hol.es/dbabfrage/inkrement.php?name="+appname);
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
                        System.out.println("UPDATE DOWNLOAD!!! "+sb);
                    }
                    br.close();

            }

        } catch (Exception ex) {
            ex.toString();
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
    public void setmContext(Context cont){
        mContext=cont;
    }


}
