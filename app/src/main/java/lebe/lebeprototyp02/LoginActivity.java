package lebe.lebeprototyp02;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        emailFeld = (EditText)findViewById(R.id.login_email);
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
            temp=true;
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
            }

        }


        return temp;
    }
}
