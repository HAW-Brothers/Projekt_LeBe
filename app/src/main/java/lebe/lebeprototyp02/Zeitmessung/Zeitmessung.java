package lebe.lebeprototyp02.Zeitmessung;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import lebe.lebeprototyp02.Internetz.ZeiterfassungSync;

/**
 * Created by Höling on 19.12.2016.
 */

public class Zeitmessung {

    private long startzeit=0;
    private long endzeit=0;
    private long dauer=0;
    private String email;
    private Context mContext;

    public Zeitmessung(String emailAdresse, Context context){

        email=emailAdresse;
        mContext=context;

    }

    public void startZeit(){
        //alle datensätze aus der Datenbank an server übertragen


        SQLiteDatabase db = mContext.openOrCreateDatabase("LeBe", Context.MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS Zeitmessung(Email VARCHAR, Start INTEGER, Ende INTEGER, Dauer INTEGER);");


        Cursor resultSet = db.rawQuery("Select Email, Start, Ende, Dauer FROM Zeitmessung",null);



        String start = "";
        String ende = "";
        String dauer = "";

        if(resultSet.moveToFirst()){

            start=resultSet.getString(1);
            ende=resultSet.getString(2);
            dauer=resultSet.getString(3);

        }

        ZeiterfassungSync neueZeit = new ZeiterfassungSync();
        neueZeit.setmContext(mContext);
        neueZeit.execute(email,start,ende,dauer);


        startzeit=System.currentTimeMillis();


    }
    public void endZeit(){

        endzeit=System.currentTimeMillis();
        dauer = endzeit-startzeit;

        speichereInDatenbank();


    }

    private void speichereInDatenbank() {

        SQLiteDatabase db = mContext.openOrCreateDatabase("LeBe", Context.MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS Zeitmessung(Email VARCHAR, Start INTEGER, Ende INTEGER, Dauer INTEGER);");


        db.execSQL("INSERT INTO Zeitmessung (Email, Start, Ende, Dauer) VALUES ( '"+email+"' , '"+startzeit+"', '"+endzeit+"', '"+dauer+"' )");


    }

}
