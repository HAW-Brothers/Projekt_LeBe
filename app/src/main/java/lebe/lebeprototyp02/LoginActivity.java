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

import lebe.lebeprototyp02.dbhelper.UserDB;
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



    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }



    private void login(String email, String passwd){

        dbHelper.login(email, passwd);
        finish();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }


}

