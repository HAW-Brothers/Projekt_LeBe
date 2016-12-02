package lebe.lebeprototyp02.gui.fragments;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import lebe.lebeprototyp02.MessageBroker;
import lebe.lebeprototyp02.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserViewFragment extends Fragment {

    private SQLiteDatabase db;

    public UserViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user_view, container, false);
        Button button;
        button = (Button) view.findViewById(R.id.button2);
        System.out.println("-----------------------------------------" + button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               updateTodatabase(view);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Intent intent = getIntent();

        db = getActivity().openOrCreateDatabase("LeBe", MODE_PRIVATE, null);

        //db.execSQL("DROP TABLE IF EXISTS UserProfile");


        db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate VARCHAR, Regdate VARCHAR, AnzeigeName VARCHAR, Email VARCHAR, Points VARCHAR);");
        //db.execSQL("INSERT INTO UserProfile VALUES ('TestUser','Test123', '01.01.1900', '01.06.2016','blahUsername','test@haw-hamburg.de', 0);");

        //db.execSQL("INSERT INTO UserProfile VALUES ('Schorzz','Test123', NOW(), NOW());");

        if (MessageBroker.getFromMessageMap("points") != null) {
            Bundle pointsBundle = MessageBroker.getFromMessageMap("points");
            String pointsFromMB = new Integer(pointsBundle.getInt("points")).toString();

            Cursor usernameSet = db.rawQuery("Select Username FROM UserProfile", null);

            String tempUser = "";

            if (usernameSet.moveToFirst()) {
                tempUser = usernameSet.getString(0);
            }

            db.execSQL("UPDATE UserProfile SET Points = '" + pointsFromMB + "' WHERE Username='" + tempUser + "'");

        }
        Cursor resultSet = db.rawQuery("Select * FROM UserProfile",null);


        if(resultSet.moveToFirst()){


            String username = resultSet.getString(0);
            String password = resultSet.getString(1);

            String birthdate = resultSet.getString(2);
            String regdate = resultSet.getString(3);
            String anzeigeName = resultSet.getString(4);
            String email = resultSet.getString(5);
            String points = resultSet.getString(6);

            EditText usernameEdit = (EditText) getView().findViewById(R.id.userNameEdit);
            usernameEdit.setText(username);
            usernameEdit.setEnabled(false);
            EditText passwort = (EditText) getView().findViewById(R.id.passwortEdit);
            passwort.setText(password);
            EditText birthdateEdit = (EditText) getView().findViewById(R.id.birthdateEdit);
            birthdateEdit.setText(birthdate);
            //birthdateEdit.setEnabled(false);
            EditText regdateEdit = (EditText) getView().findViewById(R.id.regDateEdit);
            regdateEdit.setText(regdate);
            regdateEdit.setEnabled(false);
            EditText anzeigename = (EditText)getView().findViewById(R.id.anzeigeNameEdit);
            anzeigename.setText(anzeigeName);
            EditText emailEdit = (EditText)getView().findViewById(R.id.editEmail);
            emailEdit.setText(email);
            TextView pointsTextView = (TextView) getView().findViewById(R.id.pointsTextView);
            pointsTextView.setText(points);

        }else{
            db.execSQL("INSERT INTO UserProfile VALUES ('TestUser','Test123', '28.02.1991', '18.07.2016','blahUsername','test@haw-hamburg.de', 0);");
        }

        //db.execSQL("DROP TABLE IF EXISTS LeBe.UserProfile");



    }


    public void updateTodatabase(View view){


        EditText anzeigename = (EditText) getView().findViewById(R.id.anzeigeNameEdit);
        EditText emailAdresse = (EditText)getView().findViewById(R.id.editEmail);
        String query = "UPDATE UserProfile SET AnzeigeName='"+anzeigename.getText().toString()+"', Email='"+emailAdresse.getText().toString()+"';";
        db.execSQL(query);
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Daten an datenbank übergeben",Toast.LENGTH_LONG);
        toast.show();

        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme);
        builder.setTitle("AppCompatDialog");
        builder.setMessage("Lorem ipsum dolor...");
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();*/



    }

}
