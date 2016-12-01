package lebe.lebeprototyp02.gui.control;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
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

import lebe.lebeprototyp02.LoginActivity;
import lebe.lebeprototyp02.MainActivity;
import lebe.lebeprototyp02.R;
import lebe.lebeprototyp02.gui.fragments.HomeFragment;
import lebe.lebeprototyp02.gui.fragments.StoreFragment;
import lebe.lebeprototyp02.gui.fragments.UserViewFragment;

/**
 * Created by Graumann on 28.11.2016.
 * <br><br>
 * Die Klasse GUIController ist dafür verantwortlich, dass das Design der Applikation angepasst wird. <br>
 * Dafür benötigt der GUIController das jeweils aktive View von der jeweiligen aktiven Activity oder Fragment.<br>
 * Bei Änderung der View muss updateGUI(View view) ausgeführt werden.
 *
 */

public class GUIController implements Serializable{

    /*
    Hier wird das Design für male und female gesetzt.
    Color werden in der res/values/color.xml gesucht
    Drawables werden in res/drawable gesucht
     */
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

    private static final long serialVersionUID = 42L;
    private final GUIStyles gender;
    private final GUIStyles MALE = GUIStyles.MALE;
    private final GUIStyles FEMALE = GUIStyles.FEMALE;

    /**
     * Erstellt ein GUIController der auf ein Style fixiert wird.
     * @param gender Gibt an, welchen Style der GUIController umsetzen soll
     */
    public GUIController(GUIStyles gender){
        this.gender = gender;
    }

    /**
     * Bei Ausführung wird das Style des GUIController auf die übergebende View und Object angewandt.
     * @param view Aktuelle View der Applikation
     * @param object Object, welches updateGUI() ausführt
     */
    public void updateGUI(View view, Object object){
        this.verifyArguments(view, object);

        if(object instanceof Activity){
            this.updateGUIActivity(view, (Activity) object);
        } else if (object instanceof Fragment){
            this.updateGUIFragment(view, (Fragment) object);
        } else {
            throw new IllegalArgumentException("only works on activitys or fragments");
        }

    }

    /**
     * Identifiziert welche Activity angepasst werden muss.
     * @param view Aktuelle View der Applikation
     * @param activity Zu überprüfende Activity
     */
    private void updateGUIActivity(View view, Activity activity){
        this.verifyArguments(view, activity);

        if( activity instanceof LoginActivity){
            this.handleLoginInterface(view);
        } else if(activity instanceof MainActivity){
            this.handleMainInterface(view);
        } else {
            throw new IllegalArgumentException("design for activity not declarated");
        }
    }

    /**
     * Identifiziert welches Fragment angepasst werden muss.
     * @param view Aktuelle View der Applikation
     * @param fragment Zu überprüfendes Fragment
     */
    private void updateGUIFragment(View view, Fragment fragment){
        this.verifyArguments(view, fragment);
        Class value = fragment.getClass();

        if(value.equals(HomeFragment.class)){
            this.handelFragmentHome(view, (HomeFragment) fragment);

        } else if(value.equals(StoreFragment.class)){
            this.handleFragmentStore(view);

        } else if(value.equals(UserViewFragment.class)){
            this.handleFragmentSettings(view);
        }  else {
            throw new IllegalArgumentException("design for fragment not declarated");
        }
    }

    /**
     * Passt das Design der LoginActivity an.
     * @param view Aktuelle View der Applikation
     */
    private void handleLoginInterface(View view){
        this.verifyArguments(view);

        if(this.gender.equals(MALE)) {
            this.handleLoginInterfaceHelper(view, LOGIN_COLOR_BACKGROUNG_MALE,
                    LOGIN_DRAWABLE_LOGO_MALE, LOGIN_DRAWABLE_BUTTON_MALE);

        } else if (this.gender.equals(FEMALE)) {
            this.handleLoginInterfaceHelper(view, LOGIN_COLOR_BACKGROUNG_FEMALE,
                    LOGIN_DRAWABLE_LOGO_FEMALE, LOGIN_DRAWABLE_BUTTON_FEMALE);

        } else {
            this.missingSytle();
        }

    }

    /**
     * Helfer für handleLoginInterface.<br>
     * Setzt die Design-Elemente für die LoginActivity
     * @param view Aktuelle View der Applikation
     * @param scrollViewColorBackground Hintergrundfarbe des ScrollViews
     * @param lebeLogoDrawable Drawablename für das Logo
     * @param buttonDrawable Drawablename für den Login-Button
     */
    private void handleLoginInterfaceHelper(View view, String scrollViewColorBackground, String lebeLogoDrawable, String buttonDrawable){
        this.verifyArguments(view, scrollViewColorBackground, lebeLogoDrawable, buttonDrawable);

        Context context = view.getContext();

        ScrollView scrollView1 = (ScrollView) view.findViewById(R.id.scrollView1);
        ImageView lebeLogo = (ImageView) view.findViewById(R.id.imageView2);
        Button button_login = (Button) view.findViewById(R.id.login_button);

        scrollView1.setBackgroundColor(this.getColor(view, scrollViewColorBackground));
        lebeLogo.setImageDrawable(this.getDrawable(view, lebeLogoDrawable));
        button_login.setBackground(this.getDrawable(view, buttonDrawable));

    }

    /**
     * Passt das Design der MainActivity an.
     * @param view Aktuelle View der Applikation
     */
    private void handleMainInterface(View view){
        this.verifyArguments(view);

        Context context = view.getContext();

        if(this.gender.equals(MALE)) {
            this.handleMainInterfaceHelper(view, MAIN_COLOR_NAVIGATION_MALE,
                    MAIN_COLOR_SLIDER_INDICATOR_MALE, MAIN_COLOR_SLIDER_BACKGROUND_MALE,
                    MAIN_DRAWABLE_HEADER_MALE);

        } else if (this.gender.equals(FEMALE)) {
            this.handleMainInterfaceHelper(view, MAIN_COLOR_NAVIGATION_FEMALE,
                    MAIN_COLOR_SLIDER_INDICATOR_FEMALE, MAIN_COLOR_SLIDER_BACKGROUND_FEMALE,
                    MAIN_DRAWABLE_HEADER_FEMALE);

        } else {
            this.missingSytle();
        }
    }

    /**
     * Helfer für handleMainInterfaceHelper.<br>
     * Setzt die Design-Elemente für die MainActivity
     * @param view Aktuelle View der Applikation
     * @param navigationColor Hintergrundfarbe der Navigation
     * @param sliderIndicatorColor Farbe für den Navgations-Slider-Indikator
     * @param sliderBackgroundColor Hintergundfarbe für den Navgations-Slider
     * @param headerDrawable Drawablename für den Header
     */
    private void handleMainInterfaceHelper(View view, String navigationColor, String sliderIndicatorColor, String sliderBackgroundColor, String headerDrawable){
        this.verifyArguments(view, navigationColor, sliderIndicatorColor, sliderBackgroundColor, headerDrawable);

        Context context = view.getContext();

        LinearLayout navigation = (LinearLayout) view.findViewById(R.id.navigation);
        PagerSlidingTabStrip slider = (PagerSlidingTabStrip) view.findViewById(R.id.PageSliderTabs);
        ImageView header_view = (ImageView) view.findViewById(R.id.header);

        navigation.setBackgroundColor(this.getColor(view, navigationColor));
        slider.setIndicatorColor(this.getColor(view, sliderIndicatorColor));
        slider.setBackgroundColor(this.getColor(view, sliderBackgroundColor));
        header_view.setImageDrawable(this.getDrawable(view, headerDrawable));

    }


    /**
     * Passt das Design des Settings-Fragments an.
     * @param view Aktuelle View der Applikation
     */
    private void handleFragmentSettings(View view){
        this.verifyArguments(view);

        Context context = view.getContext();
        ArrayList<String> editTextLabels = new ArrayList(Arrays.asList(
                new String[]{"userNameEdit", "anzeigeNameEdit", "passwortEdit", "birthdateEdit",
                        "regDateEdit", "editEmail"}));

        ArrayList<EditText> editTexts = this.handleFragmentSettingsEditTextIdentifier(view, editTextLabels);

        if(this.gender.equals(MALE)) {
            handleFragmentSettingsHelper(view, editTexts, SETTINGS_DRAWABLE_EDITTEXT_MALE,
                    SETTINGS_DRAWABLE_BUTTON_MALE);


        } else if (this.gender.equals(FEMALE)) {
            handleFragmentSettingsHelper(view, editTexts, SETTINGS_DRAWABLE_EDITTEXT_FEMALE,
                    SETTINGS_DRAWABLE_BUTTON_FEMALE);

        } else {
            this.missingSytle();
        }
    }

    /**
     * Helfer für handleFragmentSettings.<br>
     * Identifiziert die EditText aus der SettingsView von dem Settings-Fragment und speichert sie in eine ArrayList
     * @param view Aktuelle View der Applikation
     * @param ids IDs der EditText, welche in der fragment_home.xml gesetzt wurden
     * @return ArrayList<EditText> welche verknüpft mit den EditText der fragment_home.xml
     */
    private ArrayList<EditText> handleFragmentSettingsEditTextIdentifier(View view, List<String> ids){
        this.verifyArguments(view, ids);

        Context context = view.getContext();
        ArrayList<EditText> list = new ArrayList<EditText>();

        for(int i = 0; i < ids.size(); i++){
            list.add((EditText) view.findViewById(this.getIdentifier(view, ids.get(i))));
        }

        return list;
    }

    /**
     * Helfer für handleFragmentSettings.<br>
     * Setzt auf die EditTexts und den Button das richtige Drawable als Background
     * @param view Aktuelle View der Applikation
     * @param editTexts ArrayList<EditText> welche verknüpft mit den EditText der fragment_home.xml
     * @param draw - Drawablename welches auf allen EditText`s angewandt wird
     * @param button - Drawablename welches auf den Save-Button angewandt wird
     */
    private void handleFragmentSettingsHelper(View view, ArrayList<EditText> editTexts, String draw, String button){
        this.verifyArguments(view, editTexts, draw, button);

        Context context = view.getContext();
        Drawable toDraw = this.getDrawable(view, draw);
        for(int i=0; i < editTexts.size(); i++){
            editTexts.get(i).setBackground(toDraw);

        }
        Button button_login = (Button) view.findViewById(R.id.button2);
        Drawable buttonDraw = this.getDrawable(view, button);
        button_login.setBackground(buttonDraw);
    }


    /**
     * Passt das Design des Home-Fragments an.
     * @param view Aktuelle View der Applikation
     * @param fragment Home-Fragment, welches angepasst werden soll und aus dem die TableRows gelesen werden
     */
    private void handelFragmentHome(View view, HomeFragment fragment){
        this.verifyArguments(view, fragment);

        List<TableRow> list = fragment.getTablesRows();
        Context context = view.getContext();

        this.handleTableRow(list, view);
    }


    /**
     * Helfer für handelFragmentHome.<br>
     * Identifiziert den gewählten Style und Setzt die Design-Elemente für das Home-Fragment-TableRows des TableLayouts
     * @param list Liste mit den zu bearbeitenden TableRows
     * @param view Aktuelle View der Applikation
     */
    private void handleTableRow(List<TableRow> list, View view){
        this.verifyArguments(view, list);

        Context context = view.getContext();

        if(gender.equals(MALE)) {
            this.handleTableRowHelper(view, list, HOME_DRAWABLE_TABLEROW_MALE);

        } else if (gender.equals(FEMALE)) {
            this.handleTableRowHelper(view, list, HOME_DRAWABLE_TABLEROW_FEMALE);

        } else {
            this.missingSytle();
        }

    }

    /**
     * Helfer für handleTableRow.<br>
     * Setzt den Hintergrung die TableRows
     * @param view Aktuelle View der Applikation
     * @param list Liste mit den zu bearbeitenden TableRows
     * @param tableRowDrawable - Drawablename welches als Hintergrund für die TableRows gesetzt wird.
     */
    private void handleTableRowHelper(View view, List<TableRow> list, String tableRowDrawable){
        this.verifyArguments(view, list, tableRowDrawable);
        Context context = view.getContext();

        for(int i = 0; i < list.size() ; i++){
            list.get(i).setBackground(this.getDrawable(view, tableRowDrawable));
        }
    }

    /**
     * Pass das Design des Store-Fragments an.
     * @param view Aktuelle View der Applikation
     */
    private void handleFragmentStore(View view){
        this.verifyArguments(view);

        Context context = view.getContext();
    }


    /**
     * Sucht im Ordner drawable das zu suchende Drawable und gibt es zurück.
     * @param view Aktuelle View der Applikation
     * @param string Name des zu suchende Drawable im Ordner drawable Bsp.: (drawable/logo.png -> "logo")
     * @return Gibt das Drawable-Objekt, welches zum Drawablenamen gehört zurück
     */
    private Drawable getDrawable(View view, String string){
        this.verifyArguments(view, string);

        Context context = view.getContext();
        return context.getResources().getDrawable(context.getResources().getIdentifier(string, "drawable", context.getPackageName()));
    }

    /**
     * Identifiziert die Elemente des .xml source anhand der dort vergebenen id.
     * @param view Aktuelle View der Applikation
     * @param string ID-Name aus dem .xml source Bsp.: (android:id="@+id/login_email" -> "login_email")
     * @return Android-Identifier Wert
     */
    private int getIdentifier(View view, String string){
        this.verifyArguments(view, string);

        Context context = view.getContext();
        return context.getResources().getIdentifier(string, "id", context.getPackageName());
    }


    /**
     * Sucht in values/Color.xml die zu suchende Farbe und identifiziert sie.
     * @param view Aktuelle View der Applikation
     * @param string Farbenname Bsp.: (<color name="colorLebePrimaryBackground_Male"> -> "colorLebePrimaryBackground_Male")
     * @return Farbe als int Wert
     */
    private int getColor(View view, String string){
        this.verifyArguments(view, string);

        Context context = view.getContext();
        return context.getResources().getColor(context.getResources().getIdentifier(string, "color", context.getPackageName()));
    }

    /**
     * Wift eine IllegalArgumentException.
     */
    private void missingSytle(){
        throw new IllegalArgumentException("no style for: " + this.gender);
    }

    /**
     * Prüft Argumente auf nicht null.<br>
     * Wenn ein Argument null ist, wird eine IllegalArgumentException geworfen
     * @param arguments
     */
    public void verifyArguments(Object ...arguments){
        for(int i = 0 ; i< arguments.length; i++){
            if(arguments[i] == null){
                throw new IllegalArgumentException("arguments incorrect");
            }
        }
    }




}
