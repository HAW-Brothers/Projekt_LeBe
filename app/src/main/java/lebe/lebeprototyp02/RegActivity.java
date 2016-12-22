package lebe.lebeprototyp02;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import lebe.lebeprototyp02.dbhelper.UserDB;
import lebe.lebeprototyp02.register.EmailAnleger;

public class RegActivity extends AppCompatActivity {
    Button createAccount;
    EditText emailadresse;
    EditText passwd;
    EditText username;
    EditText anzName;
    EditText bdate;
    CheckBox geschlecht;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        createAccount = (Button) findViewById(R.id.regSave);
        emailadresse = (EditText) findViewById(R.id.regEmail);
        passwd = (EditText) findViewById(R.id.regPasswortEdit);
        username = (EditText) findViewById(R.id.regUserNameEdit);
        anzName = (EditText) findViewById(R.id.regAnzeigeNameEdit);
        bdate = (EditText) findViewById(R.id.regBirthdateEdit);
        geschlecht = (CheckBox) findViewById(R.id.regGeschlecht);




        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validateEingaben()) {

                    String email = emailadresse.getText().toString();
                    String passwort = passwd.getText().toString();
                    String usrname = username.getText().toString();
                    String anzeigeName = anzName.getText().toString();
                    String geburtstag = bdate.getText().toString();

                    long datumMilli = 0;

                    try {
                        Date datum = new SimpleDateFormat("yyyy-MM-dd").parse(geburtstag);



                        datumMilli = datum.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    EmailAnleger ec = new EmailAnleger();
                    ec.setmContext(getApplicationContext());
                    ec.execute(email, passwort, usrname, anzeigeName, ""+datumMilli);

                    String ergebnis = "ERR";

                    try {
                        ergebnis = (String) ec.get(2, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                        //wenn timeout, dann gibt es keine verbindung mit dem server
                    }
                    if (ergebnis.equals("OK")) {

                        //den USer in die Lokale datenbank legen




                        boolean geschlechtBool = geschlecht.isChecked();
                        UserDB.getInstance().addUser(email, passwort, anzeigeName, usrname, "" + datumMilli, geschlechtBool);


                        finish();


                    } else if (ergebnis.equals("ERR")) {
                        //irgendein fehler trat auf
                        Toast.makeText(getApplicationContext(),"Emailadresse ist bereits vorhanden oder keine verbindung zum Server",Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

    }

    /**
     * hier wird gecheckt ob die eingaben korrekt sind
     * @return
     */
    public boolean validateEingaben(){

        String email = emailadresse.getText().toString();
        String passwort = passwd.getText().toString();
        String usrname = username.getText().toString();
        String anzeigeName = anzName.getText().toString();
        String geburtstag = bdate.getText().toString();

        boolean erfolgreich=true;

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            erfolgreich=false;
            emailadresse.setBackgroundColor(Color.RED);
        }else{
            emailadresse.setBackgroundColor(Color.WHITE);
        }

        //-----------------------------------------------------------------------------
        if(passwort.equals("")){
            erfolgreich=false;
            passwd.setBackgroundColor(Color.RED);
        }
        else{
            passwd.setBackgroundColor(Color.WHITE);
        }

        //----------------------------------------------------------------------------
        if(usrname.equals("")){
            erfolgreich=false;
            username.setBackgroundColor(Color.RED);
        }else{
            username.setBackgroundColor(Color.WHITE);
        }
        //---------------------------------------------------------------------------
        if(anzeigeName.equals("")){
            erfolgreich=false;
            anzName.setBackgroundColor(Color.RED);
        }else{
            anzName.setBackgroundColor(Color.WHITE);
        }

        if(!Pattern.matches("(\\d{4}-\\d{2}-[0-2][0-9]|[30]|[31])",geburtstag)){

            erfolgreich=false;
            bdate.setBackgroundColor(Color.RED);
        }else{
            bdate.setBackgroundColor(Color.WHITE);
        }



        return erfolgreich;
    }

}
