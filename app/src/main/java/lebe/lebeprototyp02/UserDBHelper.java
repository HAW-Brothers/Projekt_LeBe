package lebe.lebeprototyp02;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by Höling on 23.10.2016.
 * Klasse die beim Interagieren mit der Userdatenbank hilft.
 */

public class UserDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Lebe";
    static final String TABLE_NAME = "Lebe";
    static final int DATABASE_VERSION = 1;

    Context mContext;
    SQLiteDatabase dataBase;


    UserDBHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        mContext=context;
        //dataBase=openOrCreateDatabase("LeBe", MODE_PRIVATE, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dataBase=db;
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate VARCHAR, Regdate VARCHAR, AnzeigeName VARCHAR, Email VARCHAR);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //---------------------------------------------eigene Methoden----------------------------------

    /**
     * Diese Methode liefert die Logindaten (Email und Passwort) in einem Array zurück.
     * String[0] ist die Email
     * String[1] ist das Passwort
     */
    public String[] getLogin(){

        String[] temp=new String[2];
        Cursor resultSet = dataBase.rawQuery("Select Email, Password FROM UserProfile",null);


        if(resultSet.moveToFirst()){
            temp[0]=resultSet.getString(0);
            temp[1]=resultSet.getString(1);

        }else{
            //hier im internet überprüfen ob es den User gibt
        }


        return temp;
    }

}
