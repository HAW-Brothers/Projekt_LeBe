package lebe.lebeprototyp02;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import lebe.lebeprototyp02.dbhelper.UserDB;
import lebe.lebeprototyp02.gui.control.GUIController;
import lebe.lebeprototyp02.gui.control.GUIStyles;
import lebe.lebeprototyp02.gui.fragments.UserViewFragment;

/**
 * Created by Höling on 23.10.2016.
 */

public class LoginActivity extends AppCompatActivity{

    EditText emailFeld;
    EditText passwordFeld;
    Button loginButton;
    SQLiteDatabase dataBase;
    UserDB dbHelper;
    String lastStyle;

    /**
     * GUI - Setzt das gewähte Design um
     */
    private GUIController guiController;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        dbHelper = UserDB.getInstance(this.getApplicationContext());



        emailFeld = (EditText)findViewById(R.id.login_email);
        emailFeld.setText(dbHelper.getLastEmail());
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
                if(dbHelper.validate(emailFeld.getText().toString(), passwordFeld.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"erfolgreich angemeldet!",Toast.LENGTH_LONG);
                    toast.show();
                    login(emailFeld.getText().toString(), passwordFeld.getText().toString());
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Nicht erfolgreich!",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


        if(dbHelper.autoLogin()){
            String email = dbHelper.getEmail();
            String passwd = dbHelper.getPasswort();
            login(email,passwd);
        }

        /**
         * GUI - Initialisierung - Startet hier
         */


    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    /**
     * Meldet den Benutzer mit der Email an und startet die eigentliche app.
     * @param email
     * @param passwd
     */
    private void login(String email, String passwd){

        dbHelper.login(email, passwd);
        this.initializeGUI();
        finish();
        startActivity(new Intent(this, MainActivity.class));


    }

    /**
     * GUI - GUIContoller - Initialisierung.<br>
     * Erstellt den GUIController, setzt das Design für die LoginActivity und verpackt denn
     * GUIController in den Intent für die MainActivity.
     */
    private void initializeGUI(){
        int age = this.identifyAge(dbHelper.getInstance().getGeburtsdatum());

        /**
         * Von der Datenbank wird das Geschlecht des User ausgelesen. Das Geschlecht
         * entscheidet darüber welches Design der GUIController läd
         */

        System.out.println("++++++++++++++++++>: " + dbHelper.getInstance().getStyle());

        GUIStyles acceptedStyle;
        if(dbHelper.getInstance().getStyle().equals("MALE")){
            acceptedStyle = GUIStyles.MALE;
        } else if (dbHelper.getInstance().getStyle().equals("FEMALE")) {
            acceptedStyle = GUIStyles.FEMALE;
        } else if(dbHelper.getInstance().getStyle().equals("DEFAULT")){
            acceptedStyle = GUIStyles.DEFAULT;
        } else {

            if(age < 2000){
                acceptedStyle = GUIStyles.DEFAULT;
            } else if (dbHelper.getInstance().getGeschlecht().equals("true")){
                acceptedStyle = GUIStyles.MALE;
            } else if((dbHelper.getInstance().getGeschlecht().equals("false"))){
                acceptedStyle = GUIStyles.FEMALE;
            } else {
                acceptedStyle = GUIStyles.DEFAULT;
            }

        }

        this.guiController = GUIController.getInstance();
        guiController.setStyle(acceptedStyle);

        //this.guiController = null;

        /**
         * Setzt das Design für die Activity
         */
        if(guiController != null){
            guiController.updateGUI(getWindow().getDecorView().getRootView(), this);
        }
    }


    /**
     * Ein helferlein welches das momentane alter des users bestimmt
     * @param dateString
     * @return
     */
    private int identifyAge(String dateString){

        String zu = dbHelper.getInstance().getGeburtsdatum();
        int index = zu.length();
        String date = zu.substring(0,4);

        return Integer.valueOf(date);
    }



}

