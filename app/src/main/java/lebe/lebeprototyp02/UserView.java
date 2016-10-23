package lebe.lebeprototyp02;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Höling on 23.10.2016.
 */

public class UserView extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        //Intent intent = getIntent();

        db = openOrCreateDatabase("LeBe", MODE_PRIVATE, null);


        db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate VARCHAR, Regdate VARCHAR, AnzeigeName VARCHAR);");
        //db.execSQL("INSERT INTO UserProfile VALUES ('Schorzz','Test123', NOW(), NOW());");

        Cursor resultSet = db.rawQuery("Select * FROM UserProfile",null);


        if(resultSet.moveToFirst()){


            String username = resultSet.getString(0);
            String password = resultSet.getString(1);

            String birthdate = resultSet.getString(2);
            String regdate = resultSet.getString(3);

            EditText usernameEdit = (EditText) findViewById(R.id.userNameEdit);
            usernameEdit.setText(username);
            EditText passwort = (EditText) findViewById(R.id.passwortEdit);
            passwort.setText(password);
            EditText birthdateEdit = (EditText) findViewById(R.id.birthdateEdit);
            birthdateEdit.setText(birthdate);
            birthdateEdit.setEnabled(false);
            EditText regdateEdit = (EditText) findViewById(R.id.regDateEdit);
            regdateEdit.setText(regdate);
            regdateEdit.setEnabled(false);
        }else{
            db.execSQL("INSERT INTO UserProfile VALUES ('Schorzz','Test123', '10.10.2016', '01.01.1000');");
        }
        //db.execSQL("DROP TABLE IF EXISTS LeBe.UserProfile");

    }
    public void addToDatabase(SQLiteDatabase db){

        db.execSQL("INSERT INTO UserProfile VALUES ('Schorzz','Test123', NOW(), NOW());");
    }
    public void updateTodatabase(View view){


        EditText usernameEdit = (EditText) findViewById(R.id.userNameEdit);
        String query = "UPDATE UserProfile SET Username='"+usernameEdit.getText().toString()+"';";
        db.execSQL(query);
        Toast toast = Toast.makeText(getApplicationContext(),"erfolgreich",Toast.LENGTH_LONG);
        toast.show();

    }

}
