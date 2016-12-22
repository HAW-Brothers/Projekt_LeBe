package lebe.lebeprototyp02.register;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import lebe.lebeprototyp02.RegActivity;

/**
 * Created by Höling on 21.12.2016.
 */

public class EmailAnleger extends AsyncTask<String, Void, String> {

    private Context mContext;

    @Override
    protected String doInBackground(String... params) {

        String email = params[0];

        String passwd = params[1];
        String usrname = params[2];
        String anzeigeName =params[3];
        String geburtstag =params[4];


        HttpURLConnection c = null;
        try {
            URL u = new URL("http://lebe-app.hol.es/dbabfrage/emailanlegen.php?email="+email+"&passwd="+passwd+"&usrname="+usrname+"&anzName="+anzeigeName+"&bdate="+geburtstag);
            System.out.println("--------------------------------------URL:----------"+u.toString());
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
                    System.out.println(sb);
                    //sb.toString ist zwar schlechter stil, lässt sich aber nicht verhindern denn der string beinhaltet irgendwie noch leerzeichen
                    if(sb.toString().startsWith("OK")){

                        System.out.println("email wurde vom server erstellt");
                        return "OK";

                    }else{
                        return "NOK";
                    }

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
