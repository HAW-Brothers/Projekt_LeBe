package lebe.lebeprototyp02;

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

/**
 * Created by Höling on 23.10.2016.
 */

public class LoginActivity extends AppCompatActivity{

    EditText emailFeld;
    EditText passwordFeld;
    Button loginButton;
    SQLiteDatabase dataBase;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataBase=openOrCreateDatabase("LeBe", MODE_PRIVATE, null);

        //wird zum resetten benutzt
      if(false){
          dataBase.execSQL("DROP TABLE UserProfile;");
          dataBase.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate INTEGER, Regdate INTEGER, AnzeigeName VARCHAR, Email VARCHAR, LastLogin INTEGER, Remember BOOLEAN);");
          dataBase.execSQL("INSERT INTO UserProfile VALUES ('TestUser2','Test', date('now','-3 month'), date('now'),'blahUsername','test2@haw-hamburg.de', date('now','-1 month'),'true');");
          dataBase.execSQL("INSERT INTO UserProfile VALUES ('TestUser','Test', date('now','-3 month'), date('now'),'blahUsername','test@haw-hamburg.de', date('now'),'true');");
      }


        autoLogin();

        dataBase.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate INTEGER, Regdate INTEGER, AnzeigeName VARCHAR, Email VARCHAR, LastLogin INTEGER);");

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
                    login();
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


            Cursor resultSet = dataBase.rawQuery("Select Email, Password FROM UserProfile WHERE Email ='"+emailFeld.getText()+"';",null);



            if(resultSet.moveToFirst()){


                if(!resultSet.getString(0).equals(emailFeld.getText().toString())){
                    System.out.println("Email: "+resultSet.getString(0));
                    return false;

                }
                if(!resultSet.getString(1).equals(passwordFeld.getText().toString())){
                    System.out.println("passwd: "+resultSet.getString(1));
                    return false;
                }

                //last login aktualisieren anhand der email
                aktualisiereLastLogin(resultSet.getString(0));

                return true;


            }else{
                //hier mit dem internet synchronisierenund feststellen ob es den user online gibt.

                dataBase.execSQL("INSERT INTO UserProfile VALUES ('TestUser2','Test123', date('now','-3 month'), date('now'),'blahUsername','test2@haw-hamburg.de', date('now','-1 month'));");
                dataBase.execSQL("INSERT INTO UserProfile VALUES ('TestUser','Test123', date('now','-3 month'), date('now'),'blahUsername','test@haw-hamburg.de', date('now'));");

            }

        }


        return temp;
    }
    public String getEmail(){


        String temp="";
        Cursor resultSet = dataBase.rawQuery("Select Email, Password, LastLogin FROM UserProfile ORDER BY LastLogin DESC",null);



        if(resultSet.moveToFirst()){
            temp=resultSet.getString(0);

        }else{
            temp="Email";
        }

     return temp;
    }
    private void aktualisiereLastLogin(String email){

        dataBase.execSQL("UPDATE UserProfile SET LastLogin = date('now') WHERE Email ='"+email+"';");


    }
    private void login(){
        finish();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }
    private void autoLogin(){

        String tempMail="";
        String tempRemeber="";
        Cursor resultSet = dataBase.rawQuery("Select Email, Password, Remember,LastLogin FROM UserProfile ORDER BY LastLogin DESC",null);



        if(resultSet.moveToFirst()){
            tempMail=resultSet.getString(0);
            tempRemeber=resultSet.getString(2);

        }

        if(!(tempMail.equals("")) && tempRemeber.equals("true")){

            aktualisiereLastLogin(tempMail);
            login();
        }



    }

}

