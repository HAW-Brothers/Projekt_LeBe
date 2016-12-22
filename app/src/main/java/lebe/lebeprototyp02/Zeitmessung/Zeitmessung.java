package lebe.lebeprototyp02.Zeitmessung;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import lebe.lebeprototyp02.Internetz.ZeiterfassungSync;

/**
 * Misst die Zeit der Aktiven Nutzung und synchronisiert das mit dem DB-Server
 * Created by Höling on 19.12.2016.
 */

public class Zeitmessung {

    private long startzeit=0;
    private long endzeit=0;
    private long dauer=0;
    private String email;
    private Context mContext;
    public final int MODE_ACTIVE = 1;
    public final int MODE_ONLINE = 2;
    private int modus=0;

    /**
     * misst die zeit in der die app offen war
     * @param emailAdresse
     * @param context
     */
    public Zeitmessung(String emailAdresse, Context context){

        email=emailAdresse;
        mContext=context;


    }

    /**
     * Startet die Zeitmessung durch setzten einen neuen Zeitstempels als beginn. Zudem wird im Hintergrund mit dem LeBe Server synchronisiert
     */
    public void startZeit(){
        //alle datensätze aus der Datenbank an server übertragen


        SQLiteDatabase db = mContext.openOrCreateDatabase("LeBe", Context.MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS Zeitmessung(Email VARCHAR, Start INTEGER, Ende INTEGER, Dauer INTEGER);");


        Cursor resultSet = db.rawQuery("Select Email, Start, Ende, Dauer FROM Zeitmessung",null);



        String emailAddr="";
        String start = "";
        String ende = "";
        String dauer = "";

        if(resultSet.moveToFirst()){

            emailAddr=resultSet.getString(0);
            start=resultSet.getString(1);
            ende=resultSet.getString(2);
            dauer=resultSet.getString(3);


            ZeiterfassungSync neueZeit = new ZeiterfassungSync();
            neueZeit.setmContext(mContext);
            neueZeit.execute(emailAddr,start,ende,dauer);

            //hier die datenbank wieder leeren
            db.delete("Zeitmessung",null,null);

        }else{
            System.out.println("------------------------------------------keine vorherigen daten vorhanden");
        }




        startzeit=System.currentTimeMillis();


    }

    /**
     * schliesst das messen der Zeit ab und speichert die ergebnisse in der Datenbank ab
     */
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
