package lebe.lebeprototyp02.gui.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lebe.lebeprototyp02.ApplicationDetail;
import lebe.lebeprototyp02.MessageBroker;
import lebe.lebeprototyp02.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<ApplicationDetail> applications;
    private TableLayout tl;;
    private List<TableRow> tableRows;

    public HomeFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);



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

        tl = (TableLayout) view.findViewById(R.id.table);
        tl.setVisibility(View.VISIBLE);
        loadApplication();
        tableRows = new ArrayList<TableRow>();

        for(int i = 0; i< applications.size() ; i++){
            final TableRow toAdd = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.tablerow, null);
            //TableRow toAdd = new TableRow(getActivity());

            ImageView pluginIcon = new ImageView(getActivity());
            pluginIcon.setImageDrawable(applications.get(i).getIcon());
            toAdd.addView(pluginIcon);

            final String name = applications.get(i).getLabel().toString();
            TextView pluginName = new TextView(getActivity());
            pluginName.setText(name);


            toAdd.addView(pluginName);
            tl.addView(toAdd, i);


            toAdd.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {

                    int rowPostion = tl.indexOfChild(toAdd);
                    openPlugin(getActivity().getApplicationContext(), applications.get(rowPostion).getName().toString());

                }
            } );

            toAdd.setLongClickable(true);

            toAdd.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    System.out.println("6666++59+59+8*+/*-/*-*-*-*-*-**--: " + "LOOOOONG CLICK");

                    showPopup(v, name);

                    return false;
                }
            } );

            System.out.println("6666++59+59+8*+/*-/*-*-*-*-*-**--: " + toAdd.isLongClickable());





            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams();

            tableRowParams.setMargins(0, 0, 0, 25);

            toAdd.setLayoutParams(tableRowParams);

            tableRows.add(toAdd);
        }


        tl.setClickable(true);



        return view;
    }


    public void showPopup(View anchorView, String pluginName) {

        /**
         * Abspeicherung der Favoriten
         */
        /*
        // Teil 1
        SharedPreferences prefs=this.getActivity().getSharedPreferences("yourPrefsKey",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=prefs.edit();

        Set<String> set = new HashSet<String>();

        set.add("Test");
        edit.putStringSet("yourKey", set);
        edit.commit();

        // Teil 2
        Set<String> set = prefs.getStringSet("yourKey", null);
        List<String> sample=new ArrayList<String>(set);

        System.out.println("56665565615156>>>>>>>>>>>>>>>>>>: " + sample.get(0));
        */



        View popupView = getActivity().getLayoutInflater().inflate(R.layout.home_bibliothek_popup, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView pluginNameTextView = (TextView) popupView.findViewById(R.id.popup_bib_textView);

        pluginNameTextView.setText(pluginName);


        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView

        popupWindow.showAtLocation(anchorView, Gravity.CENTER,
                0, 0);
    }


    public List<TableRow> getTablesRows(){
        return this.tableRows;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public static void openPlugin(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                throw new PackageManager.NameNotFoundException();
            }
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {}
    }




    // sucht nach angegeben CATEGORY tag: "ltd.netarchitectures.PLUGIN" und sammelt alle

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
