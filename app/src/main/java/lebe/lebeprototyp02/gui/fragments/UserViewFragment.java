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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import lebe.lebeprototyp02.MainActivity;
import lebe.lebeprototyp02.R;
import lebe.lebeprototyp02.dbhelper.UserDB;
import lebe.lebeprototyp02.gui.control.GUIController;
import lebe.lebeprototyp02.gui.control.GUIStyles;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserViewFragment extends Fragment {

//    private SQLiteDatabase db;
//    public static String emailAddresse;


    private UserDB dbHelper;
    private GUIController guiController;


    public UserViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_user_view, container, false);
        Button button;
        button = (Button) view.findViewById(R.id.button2);
//        System.out.println("-----------------------------------------" + button);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//               updateTodatabase(view);
//            }
//        });


        dbHelper=UserDB.getInstance();
        this.guiController = GUIController.getInstance();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Intent intent = getIntent();

//        db = getActivity().openOrCreateDatabase("LeBe", MODE_PRIVATE, null);
//
//        //db.execSQL("DROP TABLE IF EXISTS UserProfile");
//
//
////        db.execSQL("CREATE TABLE IF NOT EXISTS UserProfile(Username VARCHAR, Password VARCHAR, Birthdate VARCHAR, Regdate VARCHAR, AnzeigeName VARCHAR, Email VARCHAR);");
//        //db.execSQL("INSERT INTO UserProfile VALUES ('TestUser','Test123', '01.01.1900', '01.06.2016','blahUsername');");
//
//        //db.execSQL("INSERT INTO UserProfile VALUES ('Schorzz','Test123', NOW(), NOW());");
//
//        Cursor resultSet = db.rawQuery("Select * FROM UserProfile WHERE Email ='"+emailAddresse+"'",null);
//
//
//        if(resultSet.moveToFirst()){
//
//
//
//
//            String username = resultSet.getString(0);
//            String password = resultSet.getString(1);
//
//            String birthdate = resultSet.getString(2);
//            String regdate = resultSet.getString(3);
//            String anzeigeName = resultSet.getString(4);
//            String email = resultSet.getString(5);
//
//            String rememberMe = resultSet.getString(7);
//            String geschlecht = resultSet.getString(9);
//
//            EditText usernameEdit = (EditText) getView().findViewById(R.id.userNameEdit);
//            usernameEdit.setText(username);
//            usernameEdit.setEnabled(false);
//            EditText passwort = (EditText) getView().findViewById(R.id.passwortEdit);
//            passwort.setText(password);
//            EditText birthdateEdit = (EditText) getView().findViewById(R.id.birthdateEdit);
//            birthdateEdit.setText(birthdate);
//            //birthdateEdit.setEnabled(false);
//            EditText regdateEdit = (EditText) getView().findViewById(R.id.regDateEdit);
//            regdateEdit.setText(regdate);
//            regdateEdit.setEnabled(false);
//            EditText anzeigename = (EditText)getView().findViewById(R.id.anzeigeNameEdit);
//            anzeigename.setText(anzeigeName);
//            EditText emailEdit = (EditText)getView().findViewById(R.id.editEmail);
//            emailEdit.setText(email);
//            TextView geschlechtFeld = (TextView)getView().findViewById(R.id.tv_uf_geschlecht);
//
//
//            if(geschlecht.equals("true")){
//                geschlechtFeld.setText("Geschlecht: maennlich");
//            }else{
//                geschlechtFeld.setText("Geschlecht: weiblich");
//            }
//
//
//
//            CheckBox remember = (CheckBox)getView().findViewById(R.id.checkBoxRemember);
//            if (rememberMe.equals("true")){
//                remember.setChecked(true);
//            }else{
//                remember.setChecked(false);
//            }
//
//            remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(buttonView.isChecked()){
//                        //hier in der Datenbank ändern
//
//                        System.out.println("merken");
//
//                    }else{
//                        //ist nicht gecheckt
//                        System.out.println("nicht mehr merken");
//
//                    }
//                }
//            });
//
//
//        }else{
////            db.execSQL("INSERT INTO UserProfile VALUES ('TestUser','Test123', '28.02.1991', '18.07.2016','blahUsername','test@haw-hamburg.de');");
//        }
//
//        //db.execSQL("DROP TABLE IF EXISTS LeBe.UserProfile");


                    EditText usernameEdit = (EditText) getView().findViewById(R.id.userNameEdit);
            usernameEdit.setText(dbHelper.getUsername());
            usernameEdit.setEnabled(false);

            EditText passwort = (EditText) getView().findViewById(R.id.passwortEdit);
            passwort.setText(dbHelper.getPasswort());

            EditText birthdateEdit = (EditText) getView().findViewById(R.id.birthdateEdit);
            birthdateEdit.setText(dbHelper.getGeburtsdatum());
            //birthdateEdit.setEnabled(false);

            EditText regdateEdit = (EditText) getView().findViewById(R.id.regDateEdit);
            regdateEdit.setText(dbHelper.getRegDatum());
            regdateEdit.setEnabled(false);


            EditText anzeigename = (EditText)getView().findViewById(R.id.anzeigeNameEdit);
            anzeigename.setText(dbHelper.getAnzeigeName());

            EditText emailEdit = (EditText)getView().findViewById(R.id.editEmail);
            emailEdit.setText(dbHelper.getEmail());

            TextView geschlechtFeld = (TextView)getView().findViewById(R.id.tv_uf_geschlecht);


            if(dbHelper.getGeschlecht().equals("true")){
                geschlechtFeld.setText("Geschlecht: maennlich");
            }else{
                geschlechtFeld.setText("Geschlecht: weiblich");
            }


            /*
            CheckBox remember = (CheckBox)getView().findViewById(R.id.checkBoxRemember);
            if (dbHelper.getRememberMe()){
                remember.setChecked(true);
            }else{
                remember.setChecked(false);
            }
            */

        this.initializeSpinner();

        Button knopp = (Button)getView().findViewById(R.id.button2);

        knopp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dbHelper.updateToDB();
            }
        });


    }


    /**
     * Initialisiert den Dropdownfeld indem man den Style auswählen kann
     */
    public void initializeSpinner(){
        String[] arraySpinner;

        arraySpinner = new String[] {
                "Male", "Female", "Default"
        };
        Spinner s = (Spinner) getView().findViewById(R.id.Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);

        String acStyle = dbHelper.getInstance().getStyle();
        if( acStyle.equals("MALE")){
            s.setSelection(0);
        } else if ( acStyle.equals("FEMALE")){
            s.setSelection(1);
        } else if ( acStyle.equals("DEFAULT")){
            s.setSelection(2);
        }


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) {
                if(position == 0){
                    guiController.setStyle(GUIStyles.MALE);
                    dbHelper.setStyle("MALE");
                } else if(position == 1){
                    guiController.setStyle(GUIStyles.FEMALE);
                    dbHelper.setStyle("FEMALE");
                } else if(position == 2){
                    guiController.setStyle(GUIStyles.DEFAULT);
                    dbHelper.setStyle("DEFAULT");

                }
                guiController.changeStyle(getActivity().findViewById(R.id.main_activity), getView());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {



            }

        });

    }

}
