package lebe.lebeprototyp02;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.Serializable;

import lebe.lebeprototyp02.gui.control.GUIController;

/**
 * Created by Höling on 23.10.2016.
 */

public class LoginActivity extends AppCompatActivity {

    EditText emailFeld;
    EditText passwordFeld;
    Button loginButton;
    SQLiteDatabase dataBase;
    ScrollView sc;
    GUIController guiController;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Von der Datenbank wird das Geschlecht des User ausgelesen. Das Geschlecht
        // entscheidet darüber welches Design der GUIController läd
        this.guiController = new GUIController("female");

        /*
        GUIController guiController
        Setzt das Design für die Activity -CG
         */
        if(guiController != null){
            guiController.handleLoginInterface(getWindow().getDecorView().getRootView());
        }

        // Übergibt das GUIController Object an das Intent. Dieses Intent wird beim start der
        // MainActivity übergeben
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("gui", guiController);



        dataBase=openOrCreateDatabase("LeBe", MODE_PRIVATE, null);

        dataBase.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate VARCHAR, Regdate VARCHAR, AnzeigeName VARCHAR, Email VARCHAR);");


        /*
        Je nachdem welcher User sich hier einloggt, soll das jewielige Design geladen werden
         */
           /*
        Cursor resultSet = dataBase.rawQuery("Select * FROM UserProfile",null);
        // Was ist, wenn die DB leer ist?
        resultSet.moveToFirst();
        String username = resultSet.getString(0);
        System.out.println(username + "**************************************");
        if (username.equals("TestUser1")){
            System.out.println(username + "----------************+++++++++++++//////++++++++++");
            setContentView(R.layout.activity_login_female);
        } else {
            System.out.println("************************************1: " + username);

        }
        */




        emailFeld = (EditText)findViewById(R.id.login_email);
        emailFeld.setText(getEmail());
        passwordFeld = (EditText)findViewById(R.id.login_password);
        loginButton = (Button)findViewById(R.id.login_button);



        emailFeld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailFeld.getText().toString()=="hello@world.com"){
                    emailFeld.setText("");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Toast toast = Toast.makeText(getApplicationContext(),"erfolgreich angemeldet!",Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Nicht erfolgreich!",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
    public boolean validate(){

        boolean temp = false;

        if(Patterns.EMAIL_ADDRESS.matcher(emailFeld.getText().toString()).matches()){
            temp=false;


            Cursor resultSet = dataBase.rawQuery("Select Email, Password FROM UserProfile",null);



            if(resultSet.moveToFirst()){


                if(!resultSet.getString(0).equals(emailFeld.getText().toString())){
                    System.out.println("Email: "+resultSet.getString(0));
                    return false;

                }
                if(!resultSet.getString(1).equals(passwordFeld.getText().toString())){
                    System.out.println("passwd: "+resultSet.getString(1));
                    return false;
                }
                return true;


            }else{
                //hier mit dem internet synchronisierenund feststellen ob es den user online gibt.

                dataBase.execSQL("INSERT INTO UserProfile VALUES ('TestUser','Test123', '28.02.1991', '18.07.2016','blahUsername','test@haw-hamburg.de');");
            }

        }


        return temp;
    }
    public String getEmail(){


        String temp="";
        Cursor resultSet = dataBase.rawQuery("Select Email, Password FROM UserProfile",null);



        if(resultSet.moveToFirst()){
            temp=resultSet.getString(0);

        }else{
            temp="Email";
        }

     return temp;
    }
}
