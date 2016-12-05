package lebe.lebeprototyp02.gui.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lebe.lebeprototyp02.ApplicationDetail;
import lebe.lebeprototyp02.R;


/**
 * Das HomeFragment ist das erste Fragment, dass die Applikation anzeigt.<br>
 *     Es beinhaltet die Bibliothek, welche alle verfügbaren LeBe-Plug-Ins auflistet.
 *     Außerdem beinhaltet es eine Favoritenleiste.
 */
public class HomeFragment extends Fragment {

    private List<ApplicationDetail> applications;
    private TableLayout tableLayout;
    ;
    private List<TableRow> tableRows;

    // Jetzt
    private Map<String, ApplicationDetail> map;
    private final int MAX_FAV = 4;
    private int favs = 0;

    List<String> favorites;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        favorites = new ArrayList<String>();


        /*
        Das ListView ist rausgewandert, weil es keine Plugin Icons + Plugin Text anzeigen kann.
        Dafür haben wir ein TableLayout erstellt, welche mit TableRows bevölkert wird.
        Jede Row wird einzelnd erstellt und erhält einen ClickListener, der dafür sorgt, dass das
        zugehörige Plugin aufgerufen wird.

        Plug-In werden der Bibliothek hinzugefügt, indem ein neues Projekt erstellt wird. In diesem
        muss im manifest bei <intent-filter> ein <category android:name="lebe.PLUGIN" /> hinzugefügt
        werden. Danach muss dieses Plugin auf dem Zielsystem ausgeführt werden. Anschließen ist es
        unter Bibliothek zufinden.
        -CG, -P, -E -10.11.2016
        */
        this.initilizeBibliothek(view);

        /**
         * GUI - Läd die gespeicherten Favorites und fügt sie der Favoritenliste hinzu.
         */

        favorites = new ArrayList<String>();
        this.loadFavorites();
        this.updateFavoritestrip(view);

        return view;
    }

    /**
     * Initialisiert die Bibliothek der Applikation.<br>
     *     Es werden alle vorhandenen Lebe-Plug-Ins gesucht und einem TableLayout hinzugefügt
     * @param view
     */
    private void initilizeBibliothek(final View view) {

        tableLayout = (TableLayout) view.findViewById(R.id.table);

        loadApplication();
        tableRows = new ArrayList<TableRow>();

        map = new HashMap<String, ApplicationDetail>();

        for (int i = 0; i < applications.size(); i++) {
            final TableRow toAdd = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.tablerow, null);

            ImageView pluginIcon = (ImageView) getActivity().getLayoutInflater().inflate(R.layout.image_view, null);
            //ImageView pluginIcon = new ImageView(getActivity());

            pluginIcon.setImageDrawable(applications.get(i).getIcon());


            /*
            pluginIcon.setScaleType(ImageView.ScaleType.FIT_XY);

            pluginIcon.setPadding(0,0,25,0);

            pluginIcon.setImageDrawable(applications.get(i).getIcon());

            pluginIcon.setMaxHeight(200);
            pluginIcon.setMinimumHeight(200);
            pluginIcon.setMaxWidth(225);
            pluginIcon.setMinimumWidth(225);
            */


            toAdd.addView(pluginIcon);

            final String name = applications.get(i).getLabel().toString();
            TextView pluginName = new TextView(getActivity());
            pluginName.setText(name);

            toAdd.addView(pluginName);
            tableLayout.addView(toAdd, i);

            map.put(applications.get(i).getLabel().toString(), applications.get(i));

            toAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rowPostion = tableLayout.indexOfChild(toAdd);
                    openPlugin(getActivity().getApplicationContext(), applications.get(rowPostion).getName().toString());

                }
            });

            toAdd.setLongClickable(true);

            toAdd.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    showPopup(v, name, view, "zu Favoriten hinzufügen?", "Ok", "Cancel", "add");
                    return false;
                }
            });


            TableLayout.LayoutParams tableRowParams =
                    new TableLayout.LayoutParams();
            tableRowParams.setMargins(0, 0, 0, 25);
            toAdd.setLayoutParams(tableRowParams);
            tableRows.add(toAdd);
        }
        tableLayout.setClickable(true);


    }

    /**
     * Speicher die Benutzerfavoriten lokal ab.
     *
     * @param toSave - Set gefüllt mit den Benutzerfavoriten
     */
    private void saveFavorites(Set<String> toSave) {
        SharedPreferences.Editor edit = this.getActivity().getSharedPreferences("bib_favorites", Context.MODE_PRIVATE).edit();
        edit.putStringSet("bib_favorites", toSave).commit();
    }

    /**
     * Läd die Benutzerfavoriten aus dem lokalen Speicher.
     */
    private void loadFavorites() {
        Set<String> set = this.getActivity().getSharedPreferences("bib_favorites", Context.MODE_PRIVATE).getStringSet("bib_favorites", null);

        if (set != null) {
            this.favorites = new ArrayList<String>(set);
        }
    }


    /**
     * Öffnet ein Popup welches Plug-Ins von den Favoriten hinzufügt oder entfernt
     * @param anchorView View in dem das PopUp erscheinen soll
     * @param pluginName Dieses Plugin soll geöffnet werden
     * @param view Aktuelle View der Applikation
     * @param text Popup-Text
     * @param buttonName1 Erste Buttonbeschriftung
     * @param buttonName2 Zweite Buttonbeschriftung
     * @param type Typ des Popup, entweder add oder remove
     */
    private void showPopup(View anchorView, final String pluginName, final View view, String text, String buttonName1, String buttonName2, String type) {
        //Pop nicht öffnen wenn typ add und bereits in der list
        //Pop nicht öffnet wenn Liste voll
        if (this.favorites.contains(pluginName)) {
            type = "remove";
            text = "von Favoriten entfernen";
        } else if (favorites.size() == MAX_FAV && type.equals("add")) {
            text = "Favortienliste voll";
        }


        View popupView = getActivity().getLayoutInflater().inflate(R.layout.home_bibliothek_popup, null);


        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ((TextView) popupView.findViewById(R.id.popup_bib_textView)).setText(pluginName);
        ((TextView) popupView.findViewById(R.id.popup_bib_textView2)).setText(text);


        // Buttons
        Button ok = (Button) popupView.findViewById(R.id.popup_bib_ok);
        ok.setText(buttonName1);
        Button cancle = (Button) popupView.findViewById(R.id.popup_bib_cancel);
        cancle.setText(buttonName2);

        final String finalType = type;
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalType.equals("add")) {
                    addFavorite(view, pluginName);
                } else if (finalType.equals("remove")) {
                    removeFavorite(view, pluginName);
                }
                popupWindow.dismiss();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        anchorView.getLocationOnScreen(new int[2]);
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

    }


    /**
     * Fügt der Favoritenliste ein Favorit hinzu.<br>
     * Im Anschluss wird die Favoritenleiste aktualisiert
     *
     * @param view Aktuelle View der Applikation
     * @param name Name des Favoriten
     */
    private void addFavorite(View view, String name) {
        if (favorites.size() < MAX_FAV && (!favorites.contains(name))) {
            favorites.add(name);
        }
        this.updateFavoritestrip(view);
    }

    /**
     * Entfernt ein Favorit von der Favoritenliste.<br>
     * Im Anschluss wird die Favoritenleiste aktualisiert
     *
     * @param view Aktuelle View der Applikation
     * @param name Name des Favoriten
     */
    private void removeFavorite(View view, String name) {
        if (favorites.size() > 0) {
            favorites.remove(name);
        }
        this.updateFavoritestrip(view);
    }

    /**
     * Updated die Favorit der Favoritenliste bei einer Änderung durch add oder remove
     * @param view Aktuelle View der Applikation
     */
    private void updateFavoritestrip(final View view) {
        int nullCounter = 0;
        // Alle zurücksetzten
        for (int i = 0; i < MAX_FAV; i++) {
            updateFavoritestripHelper(view, "fav" + i, "fav" + i + "_text", null, null, false, i);
        }
        // Alle setzten

        for (int i = 0; i < favorites.size(); i++) {

            if (!(map.get(favorites.get(i))==null)){
            updateFavoritestripHelper(view, "fav" + (i - nullCounter), "fav" + (i - nullCounter) + "_text",
                    map.get(favorites.get(i)).getIcon(), map.get(favorites.get(i)).getLabel().toString(), true, i);
            } else {
                nullCounter++;
            }

        }
        saveFavorites(new HashSet<String>(this.favorites));

    }

    /**
     * Helfer für updateFavoritestrip
     * @param view Aktuelle View der Applikation
     * @param imageView ImageView ID Name
     * @param textView Plug-In Name
     * @param toDraw Plug-In Icon Drawable
     * @param text Plug-In Label
     * @param clicklistiner - ClickListiner auf dem ImageView. Wenn man auf das Icon drückt, soll das Plug-In geöffnet werden
     * @param i Index des Plug-In in der Favortien Map(Dort sind alle vorhandenen Plug-Ins hinterlegt)
     */
    private void updateFavoritestripHelper(final View view, String imageView, String textView, Drawable toDraw, String text, boolean clicklistiner, int i) {

        ImageView fav = (ImageView) view.findViewById(view.getContext().getResources()
                .getIdentifier(imageView, "id", view.getContext().getPackageName()));

        fav.setImageDrawable(toDraw);


        TextView fav_text = (TextView) view.findViewById(view.getContext()
                .getResources().getIdentifier(textView, "id", view.getContext()
                        .getPackageName()));

        fav_text.setText(text);

        if (clicklistiner == true) {
            final String name = favorites.get(i);
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openPlugin(getActivity().getApplicationContext(), map.get(name).getName().toString());

                }
            });

            fav.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    showPopup(v, name, view, "von Favoriten entfernen", "Ok", "Cancle", "remove");

                    return false;
                }
            });
        }
    }


    /**
     * Gibt die TableRow-Liste der Bibliothek zurück.<br>
     * @return Liste mit Bibliothek Tabellen
     */
    public List<TableRow> getTablesRows() {
        return this.tableRows;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    /**
     * Öffnet ein LeBe-Plug-In und legt es über die LeBe-Applikation.<br>
     * @param context Applikations-Context
     * @param packageName PackageName der Applikation
     */
    private static void openPlugin(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                throw new PackageManager.NameNotFoundException();
            }
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
        }
    }


    /**
     * Sucht nach angegeben CATEGORY tag: "lebe.PLUGIN" und sammelt alle.<br>
     *     Diese Funktion ermittelt alle verfügbaren LeBe-Plug-In`s auf dem Zielsystem
     */
    private void loadApplication() {
        // package manager is used to retrieve the system's packages
        PackageManager packageManager = getActivity().getPackageManager();
        applications = new ArrayList<>();
        // we need an intent that will be used to load the packages
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        // in this case we want to load all packages wtih PLUGIN category
        intent.addCategory("lebe.PLUGIN");
        List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(intent, 0);
        // for each one we create a custom list view item
        for (ResolveInfo resolveInfo : availableActivities) {
            ApplicationDetail applicationDetail = new ApplicationDetail(
                    resolveInfo.loadLabel(packageManager),
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.loadIcon(packageManager));
            applications.add(applicationDetail);
        }
    }

}
