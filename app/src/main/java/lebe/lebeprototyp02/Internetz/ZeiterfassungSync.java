package lebe.lebeprototyp02.Internetz;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Höling on 19.12.2016.
 */

public class ZeiterfassungSync extends AsyncTask <String, Void, Void>{

    private Context mContext;

    @Override
    protected Void doInBackground(String... params) {

        String email = params[0];
        String start = params[1];
        String ende = params[2];
        String dauer = params[3];

        long startZeit= Long.parseLong(start);


        System.out.println("--------------------------------------parameter fuer serversync: "+email+" start: "+start+" ende: "+ende+" dauer: "+dauer);


        return null;
    }

    public void setmContext(Context cont){
        mContext=cont;
    }
}
