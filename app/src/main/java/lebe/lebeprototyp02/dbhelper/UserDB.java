package lebe.lebeprototyp02.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Patterns;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Höling on 02.12.2016.
 */

public class UserDB {

    private static UserDB dbhelper;// = new UserDB();

    public static boolean reset = false;

    private String style;
    private String email;
    private String geburtsdatum;
    private String regDatum;
    private String anzeigeName;
    private String geschlecht;
    private String passwort;
    private String username;
    private String rememberMe;

    private SQLiteDatabase db;
    Context mContext;

    private UserDB(Context context){
        //hier connecten und alle infos aus der db holen

        mContext=context.getApplicationContext();



        db=mContext.openOrCreateDatabase("LeBe", MODE_PRIVATE, null);

//        DataBaseH
//
//        SQLiteOpenHelper oh = new SQLiteOpenHelper(mContext, "LeBe", null, 1) {
//            @Override
//            public void onCreate(SQLiteDatabase db) {
//
//
//                db.execSQL("sqlite3 LeBe.db");
//                db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate INTEGER, Regdate INTEGER, AnzeigeName VARCHAR, Email VARCHAR, LastLogin INTEGER, Remember BOOLEAN, Style VARCHAR, Geschlecht BOOLEAN, Points INTEGER);");
//                db.execSQL("INSERT INTO UserProfile VALUES ('Jung','Test', date('now','-6 years'), date('now'),'kind','jung@haw-hamburg.de', date('now','-1 month'),'false', 'test', 'true',0);");
//                db.execSQL("INSERT INTO UserProfile VALUES ('Teen','Test', date('now','-15 years'), date('now'),'teenager','teen@haw-hamburg.de', date('now'),'false', 'test', 'false',1000);");
//                db.execSQL("INSERT INTO UserProfile VALUES ('Alt','Test', date('now','-30 years'), date('now'),'hentai jiji','alt@haw-hamburg.de', date('now','-1 year'),'false', 'test', 'true',13);");
//
//            }
//
//            @Override
//            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//                db.execSQL("DROP TABLE IF EXIST Lebe.UserProfile;");
//                db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate INTEGER, Regdate INTEGER, AnzeigeName VARCHAR, Email VARCHAR, LastLogin INTEGER, Remember BOOLEAN, Style VARCHAR, Geschlecht BOOLEAN, Points INTEGER);");
//            }
//        };
//
//        oh.getWritableDatabase().execSQL("DROP TABLE UserProfile");

//        if(reset){
////            String[] temp= {};
////            db.delete("UserProfile","",temp);
////            SQLiteDatabase.deleteDatabase(new File(db.getPath()));
//            db.execSQL("DROP TABLE IF EXISTS UserProfile;");
//            db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate INTEGER, Regdate INTEGER, AnzeigeName VARCHAR, Email VARCHAR, LastLogin INTEGER, Remember BOOLEAN, Style VARCHAR, Geschlecht BOOLEAN, Points INTEGER);");
//            db.execSQL("INSERT INTO UserProfile VALUES ('Jung','Test', date('now','-6 years'), date('now'),'kind','jung@haw-hamburg.de', datetime('now','-1 month'),'false', 'test', 'true',0);");
//            db.execSQL("INSERT INTO UserProfile VALUES ('Teen','Test', date('now','-15 years'), date('now'),'teenager','teen@haw-hamburg.de', datetime('now','-1 seconds'),'false', 'test', 'false',1000);");
//            db.execSQL("INSERT INTO UserProfile VALUES ('Alt','Test', date('now','-30 years'), date('now'),'hentai jiji','alt@haw-hamburg.de', datetime('now','-1 year'),'false', 'test', 'true',13);");
//
////            db = context.openOrCreateDatabase("LeBe", MODE_PRIVATE, null);
//        }


        db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate INTEGER, Regdate INTEGER, AnzeigeName VARCHAR, Email VARCHAR, LastLogin INTEGER, Remember BOOLEAN, Style VARCHAR, Geschlecht BOOLEAN, Points INTEGER);");






    }


    public static UserDB getInstance(Context context){

        dbhelper = new UserDB(context);


        return dbhelper;
    }
    public static UserDB getInstance(){
        return dbhelper;
    }

//    public String eMail(){
//        return email;
//    }

    /**
     * Checkt ob das Passwort zu der Emailadresse passt
     * @param email
     * @param passwort
     * @return
     *          {@code true} wenn Email & Passwort zusammenpassen
     *          {@code false} wenn Email nicht dem Muster einer Emailadresse entspricht
     */
    public boolean validate(String email, String passwort){
        boolean temp = false;

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            temp=false;


            Cursor resultSet = db.rawQuery("Select Email, Password FROM UserProfile WHERE Email ='"+email+"';",null);



            if(resultSet.moveToFirst()){


                if(!resultSet.getString(0).equals(email)){

                    return false;

                }
                if(!resultSet.getString(1).equals(passwort)){

                    return false;
                }

                this.email=resultSet.getString(0);
                this.passwort=resultSet.getString(1);

                return true;


            }else{
                //hier mit dem internet synchronisierenund feststellen ob es den user online gibt.

                Emailchecker ec = new Emailchecker();
                ec.setmContext(mContext);
                ec.execute(email,passwort);


                //hier weitermachen
                try {
                   String ergebnis = ec.get(2, TimeUnit.SECONDS);

                    String[] ergebnisArray = ergebnis.split("<>");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

            }

        }


        return temp;
    }

    /**
     * Speichert alle Informationen über den User in dieser Klasse. Implizites aufrufen von {@code validate(String mail, String passwd)} {@see validate()}
     * mit dem sich angemeldet werden soll
     */
    public void login(String emailAdresse, String passwort){

        if(validate(emailAdresse,passwort)){
            //ab hier alle infos holen und speichern

            Cursor resultSet = db.rawQuery("Select * FROM UserProfile WHERE Email ='"+email+"'",null);


            if(resultSet.moveToFirst()){
                username = resultSet.getString(0);
                this.passwort = resultSet.getString(1);

                geburtsdatum = resultSet.getString(2);
                regDatum = resultSet.getString(3);
                anzeigeName = resultSet.getString(4);
//                email = resultSet.getString(5);
//                resultSet.getString(5) wäre lastLogin

                rememberMe = resultSet.getString(7);
                geschlecht = resultSet.getString(9);
                style = resultSet.getString(8);
            }

            updateLastLogin();
        }


    }

    /**
     * aktualisiert den Zeitpunkt des letzten logins
     */
    private void updateLastLogin(){
        db.execSQL("UPDATE UserProfile SET LastLogin = datetime('now') WHERE Email = '"+email+"';");
    }
    private boolean rememberMe(String email){
        boolean temp=false;

        Cursor resultSet = db.rawQuery("Select Remember FROM UserProfile WHERE Email = '"+email+"';",null);

        if(resultSet.moveToFirst()) {

            if(resultSet.getString(0).equals("true")){
                temp=true;
            }

        }
        return temp;
    }

    /**
     * Liefert die Emailadresse des zuletzt eingeloggten Users zurück
     * @return
     *          Emailadresse von zuletzt eingeloggten User. {@code null} wenn noch niemand eingelogt war.
     */
    public String getLastEmail(){
        String temp=null;
        Cursor resultSet = db.rawQuery("Select Email, Password, LastLogin FROM UserProfile ORDER BY LastLogin DESC",null);



        if(resultSet.moveToFirst()){
            temp=resultSet.getString(0);
            email=temp;
            passwort=resultSet.getString(1);

        }
        return temp;
    }

    /**
     * Wenn im Profil 'rememberMe' aktiviert wurde, wird der zuletzt eingeloggte User direkt angemeldet (lastLogin wird aktualisiert)
     * ACHTUNG!!! USER MUSS NICHT ERNEUT ANGEMELDET WERDEN!
     * @return
     *          {@code true} wenn 'rememberMe' aktiviert ist
     *          {@code false} wenn 'rememberMe' nicht aktiviert ist
     */
    public boolean autoLogin(){

        String email=getLastEmail();
        boolean temp = rememberMe(email);

        if (temp) {

            login(email,passwort);

        }


       return temp;
    }

    /**
     * Speichert das Objekt im momentanen zustand in die Datenbank
     */
    public void updateToDB(){


        db.execSQL("UPDATE UserProfile SET AnzeigeName = '"+anzeigeName+"', Remember = '"+rememberMe+"', Style = '"+style+"' WHERE Email = '"+email+"';");




    }

    public String getStyle() {
        return style;
    }

    /**
     * Setzt den Style
     * @param style
     *              Darf nicht {@code null} oder "" sein
     * @throws
     *      IllegalArgumentException
     */
    public void setStyle(String style){
        if(style==null || style.equals("")){
            throw new IllegalArgumentException("Parameter darf nicht null oder leer sein");
        }
        this.style=style;



    }

    public String getEmail() {
        return email;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    /**
     * Setzt das GeburtsDatum als String
     * @param data
     *              Format d.m.Y oder Y-m-d
     * @throws
     *      IllegalArgumentException wenn data {@code null} oder "" ist
     */
    public void setGeburtsdatum(String data){
        if(data==null || data.equals("")){
            throw new IllegalArgumentException("Parameter darf nicht null oder leer sein");
        }
        geburtsdatum=data;
    }

    public String getRegDatum() {
        return regDatum;
    }

    public String getAnzeigeName() {
        return anzeigeName;
    }

    /**
     * Setzt den AnzeigeNamen des Benutzers
     * @param data
     * @throws
     *      IllegalArgumentException wenn data {@code null} oder "" ist
     */
    public void setAnzeigeName(String data){
        if(data==null || data.equals("")){
            throw new IllegalArgumentException("Parameter darf nicht null oder leer sein");
        }
        anzeigeName=data;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswort() {
        return passwort;
    }

    /**
     *Setz ein neues Passwort für die momentane Emailadresse
     * @param data
     * @throws
     *      IllegalArgumentException wenn data {@code null} oder "" ist
     */
    public void setPasswort(String data){
        if(data==null || data.equals("")){
            throw new IllegalArgumentException("Parameter darf nicht null oder leer sein");
        }
        passwort=data;
    }

    public boolean getRememberMe() {
        if(rememberMe!=null) {
            if (rememberMe.equals("true")){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @param data Darf nur "true" oder "false" sein.
     * @throws
     *      IllegalArgumentException wenn data {@code null} oder "" ist
     */
    public void setRememberMe(String data){
        if(data==null || data.equals("")){
            throw new IllegalArgumentException("Parameter darf nicht null oder leer sein");
        }

        if(data.equals("true")){
            rememberMe="true";
        }else if(data.equals("false")){
            rememberMe="false";
        }
    }

    /**
     * Ein neuer User wird lokal angelegt
     */
    public void addUser(String email, String password, String anzeigename, String benutzername, String bdate){



        db.execSQL("INSERT INTO UserProfile VALUES ('"+benutzername+"','"+password+"', date('now','-20 years'), date('now'),'"+anzeigename+"','"+email+"', datetime('now'),'false', 'DEFAULT', 'true',0);");
    }

}
