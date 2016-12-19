package lebe.lebeprototyp02.Internetz;

import android.os.AsyncTask;

/**
 * Created by Höling on 19.12.2016.
 */

public class ZeiterfassungSync extends AsyncTask <String, Void, Void>{
    @Override
    protected Void doInBackground(String... params) {

        String email = params[0];
        String start = params[1];
        String ende = params[2];
        String dauer = params[3];




        return null;
    }
}
