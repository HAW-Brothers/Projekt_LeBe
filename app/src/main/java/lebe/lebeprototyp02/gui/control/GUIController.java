package lebe.lebeprototyp02.gui.control;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;

import com.astuetz.PagerSlidingTabStrip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lebe.lebeprototyp02.R;

/**
 * Created by Chris on 28.11.2016.
 */

public class GUIController implements Serializable{

    //Color werden in der res/values/color.xml gesucht
    //Drawables werden in res/drawable gesucht
    private static String LOGIN_COLOR_BACKGROUNG_MALE   = "colorLebePrimaryBackground_Male";
    private static String LOGIN_COLOR_BACKGROUNG_FEMALE = "colorLebePrimaryBackground_Female";
    private static String LOGIN_DRAWABLE_LOGO_MALE      = "logo_male";
    private static String LOGIN_DRAWABLE_LOGO_FEMALE    = "logo_female";
    private static String LOGIN_DRAWABLE_BUTTON_MALE    = "button_male";
    private static String LOGIN_DRAWABLE_BUTTON_FEMALE  = "button_female";

    private static String MAIN_COLOR_NAVIGATION_MALE          = "colorLebePrimaryBackground_Male";
    private static String MAIN_COLOR_NAVIGATION_FEMALE        = "colorLebePrimaryBackground_Female";
    private static String MAIN_COLOR_SLIDER_INDICATOR_MALE    = "color_pageslider_indicator_male";
    private static String MAIN_COLOR_SLIDER_INDICATOR_FEMALE  = "color_pageslider_indicator_female";
    private static String MAIN_COLOR_SLIDER_BACKGROUND_MALE   =  "colorLebePrimaryBackground_Male";
    private static String MAIN_COLOR_SLIDER_BACKGROUND_FEMALE = "colorLebePrimaryBackground_Female";
    private static String MAIN_DRAWABLE_HEADER_MALE           = "header_male";
    private static String MAIN_DRAWABLE_HEADER_FEMALE         = "header_female";

    private static String HOME_DRAWABLE_TABLEROW_MALE   = "table_row_bib_male";
    private static String HOME_DRAWABLE_TABLEROW_FEMALE = "table_row_bib_female";

    private static String SETTINGS_DRAWABLE_EDITTEXT_MALE   = "edittext_user_view_male";
    private static String SETTINGS_DRAWABLE_EDITTEXT_FEMALE = "edittext_user_view_female";
    private static String SETTINGS_DRAWABLE_BUTTON_MALE     = "button_male";
    private static String SETTINGS_DRAWABLE_BUTTON_FEMALE   = "button_female";

    static final long serialVersionUID = 42L;
    private String gender;

    // Konstruktor für Fragments
    public GUIController(String gender){
        this.gender = gender;
    }


    public void handleLoginInterface(View view){
        Context context = view.getContext();

        if(this.gender.equals("male")) {
            this.handleLoginInterfaceHelper(view, LOGIN_COLOR_BACKGROUNG_MALE,
                    LOGIN_DRAWABLE_LOGO_MALE, LOGIN_DRAWABLE_BUTTON_MALE);

        } else if (this.gender.equals("female")) {
            this.handleLoginInterfaceHelper(view, LOGIN_COLOR_BACKGROUNG_FEMALE,
                    LOGIN_DRAWABLE_LOGO_FEMALE, LOGIN_DRAWABLE_BUTTON_FEMALE);

        } else {

        }
    }

    // Helper für handleLoginInterface
    public void handleLoginInterfaceHelper(View view, String scrollViewColor, String lebeLogoDrawable, String buttonDrawable){
        Context context = view.getContext();

        ScrollView scrollView1 = (ScrollView) view.findViewById(R.id.scrollView1);
        ImageView lebeLogo = (ImageView) view.findViewById(R.id.imageView2);
        Button button_login = (Button) view.findViewById(R.id.login_button);

        scrollView1.setBackgroundColor(this.getColor(view, scrollViewColor));
        lebeLogo.setImageDrawable(this.getDrawable(view, lebeLogoDrawable));
        button_login.setBackground(this.getDrawable(view, buttonDrawable));

    }


    public void handleMainInterface(View view){
        Context context = view.getContext();

        if(this.gender.equals("male")) {
            this.handleMainInterfaceHelper(view, MAIN_COLOR_NAVIGATION_MALE,
                    MAIN_COLOR_SLIDER_INDICATOR_MALE, MAIN_COLOR_SLIDER_BACKGROUND_MALE,
                    MAIN_DRAWABLE_HEADER_MALE);

        } else if (this.gender.equals("female")) {
            this.handleMainInterfaceHelper(view, MAIN_COLOR_NAVIGATION_FEMALE,
                    MAIN_COLOR_SLIDER_INDICATOR_FEMALE, MAIN_COLOR_SLIDER_BACKGROUND_FEMALE,
                    MAIN_DRAWABLE_HEADER_FEMALE);

        } else {

        }
    }

    // Helper für handleMainInterface
    public void handleMainInterfaceHelper(View view, String navigationColor, String sliderIndicatorColor, String sliderBackgroundColor, String headerDrawable){
        Context context = view.getContext();

        LinearLayout navigation = (LinearLayout) view.findViewById(R.id.navigation);
        PagerSlidingTabStrip slider = (PagerSlidingTabStrip) view.findViewById(R.id.PageSliderTabs);
        ImageView header_view = (ImageView) view.findViewById(R.id.header);

        navigation.setBackgroundColor(this.getColor(view, navigationColor));
        slider.setIndicatorColor(this.getColor(view, sliderIndicatorColor));
        slider.setBackgroundColor(this.getColor(view, sliderBackgroundColor));
        header_view.setImageDrawable(this.getDrawable(view, headerDrawable));

    }



    public void handleFragmentSettings(View view){

        Context context = view.getContext();
        ArrayList<String> editTextLabels = new ArrayList(Arrays.asList(
                new String[]{"userNameEdit", "anzeigeNameEdit", "passwortEdit", "birthdateEdit",
                        "regDateEdit", "editEmail"}));

        ArrayList<EditText> editTexts = this.handleFragmentSettingsEditTextIdentifier(view, editTextLabels);

        if(this.gender.equals("male")) {
            handleFragmentSettingsHelper(view, editTexts, SETTINGS_DRAWABLE_EDITTEXT_MALE,
                    SETTINGS_DRAWABLE_BUTTON_MALE);


        } else if (this.gender.equals("female")) {
            handleFragmentSettingsHelper(view, editTexts, SETTINGS_DRAWABLE_EDITTEXT_FEMALE,
                    SETTINGS_DRAWABLE_BUTTON_FEMALE);

        } else {

        }

    }

    // Hilfsfunktion: Identifiziert die EditText aus der SettingsView und speichert sie in eine ArrayList
    public ArrayList<EditText> handleFragmentSettingsEditTextIdentifier(View view, List<String> ids){
        Context context = view.getContext();
        ArrayList<EditText> list = new ArrayList<EditText>();

        for(int i = 0; i < ids.size(); i++){
            list.add((EditText) view.findViewById(this.getIdentifier(view, ids.get(i))));
        }

        return list;
    }

    // Hilfsfunktion: Setzt auf die EditTexts und den Button das richtige Drawable als Background
    public void handleFragmentSettingsHelper(View view, ArrayList<EditText> editTexts, String draw, String button){
        Context context = view.getContext();
        Drawable toDraw = this.getDrawable(view, draw);
        for(int i=0; i < editTexts.size(); i++){
            editTexts.get(i).setBackground(toDraw);

        }
        Button button_login = (Button) view.findViewById(R.id.button2);
        Drawable buttonDraw = this.getDrawable(view, button);
        button_login.setBackground(buttonDraw);
    }




    public void handleTableRow(List<TableRow> list, View view){
        Context context = view.getContext();

        if(gender.equals("male")) {
            this.handleTableRowHelper(view, list, HOME_DRAWABLE_TABLEROW_MALE);

        } else if (gender.equals("female")) {
            this.handleTableRowHelper(view, list, HOME_DRAWABLE_TABLEROW_FEMALE);

        } else {

        }

    }

    // Helper für handleTableRow
    public void handleTableRowHelper(View view, List<TableRow> list, String tableRowDrawable){
        Context context = view.getContext();

        for(int i = 0; i < list.size() ; i++){
            list.get(i).setBackground(this.getDrawable(view, tableRowDrawable));
        }


    }


    public void handelFragmentHome(View view, List<TableRow> list){
        Context context = view.getContext();
        this.handleTableRow(list, view);

    }

    public void handleFragmentStore(View view){
        Context context = view.getContext();


    }



    public Drawable getDrawable(View view, String string){
        Context context = view.getContext();
        return context.getResources().getDrawable(context.getResources().getIdentifier(string, "drawable", context.getPackageName()));
    }

    public int getIdentifier(View view, String string){
        Context context = view.getContext();
        return context.getResources().getIdentifier(string, "id", context.getPackageName());
    }



    public int getColor(View view, String string){
        Context context = view.getContext();
        return context.getResources().getColor(context.getResources().getIdentifier(string, "color", context.getPackageName()));
    }




}
