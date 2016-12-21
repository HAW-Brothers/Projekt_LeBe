package lebe.lebeprototyp02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lebe.lebeprototyp02.dbhelper.UserDB;
import lebe.lebeprototyp02.register.EmailAnleger;

public class RegActivity extends AppCompatActivity {
    Button createAccount;
    EditText emailadresse;
    EditText passwd;
    EditText username;
    EditText anzName;
    EditText bdate;

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


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailadresse.getText().toString();
                String passwort = passwd.getText().toString();
                String usrname = username.getText().toString();
                String anzeigeName = anzName.getText().toString();
                String geburtstag = bdate.getText().toString();

                EmailAnleger ec = new EmailAnleger();
                ec.setmContext(getApplicationContext());
                ec.execute(email, passwort,usrname,anzeigeName,geburtstag);

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
                if(ergebnis.equals("OK")){

                    //den USer in die Lokale datenbank legen

                    UserDB.getInstance().addUser(email,passwort,anzeigeName,usrname,geburtstag);

                    finish();


                }else if(ergebnis.equals("ERR")){
                    //irgendein fehler trat auf
                }

            }
        });

    }

    /**
     * hier wird gecheckt ob die eingaben korrekt sind
     * @return
     */
    public boolean validateEingaben(){

        return false;
    }

}
