package lebe.lebeprototyp02.market;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import lebe.lebeprototyp02.R;

/**
 * Created by Höling on 14.11.2016.
 */

public class CVAMarket extends ArrayAdapter<MarketItem> {

    private Context mContext;
    private ArrayList<MarketItem> itemListe;



    public CVAMarket(Context context, ArrayList<MarketItem> liste) {
        super(context, R.layout.market_single_item, liste);
        mContext = context;
        itemListe= liste;



    }
    public View getView(int position, View convertview, ViewGroup parent){


        final int poosition=position;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowViev=inflater.inflate(R.layout.market_single_item,parent,false);


        ImageView logo = (ImageView) rowViev.findViewById(R.id.market_logo);
        TextView name = (TextView) rowViev.findViewById(R.id.market_Name);
        TextView beschreibung = (TextView) rowViev.findViewById(R.id.market_beschreibung);
        Button download = (Button) rowViev.findViewById(R.id.market_download);




        //MarketItem Objekt erzeugen
        MarketItem item = itemListe.get(position);

        final String tempDateiname = item.getName();
        final String tempdownload = item.getDdlpath();

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                PackageManager manager = getContext().getPackageManager();
                try {
                    i = manager.getLaunchIntentForPackage("lebe.lebeprototyp02");
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    getContext().startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    InstallAPK downloadAndInstall = new InstallAPK();


//                    Toast.makeText(getContext(),"download?!",Toast.LENGTH_LONG).show();

//                    DownloadManager mgr =(DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                    Uri uri;
//                    uri =Uri.parse("http://wfarm2.dataknet.com/static/resources/icons/set52/ba7285f0.png");
//                    DownloadManager.Request request = new DownloadManager.Request(uri);
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//                    Long reference = mgr.enqueue(request);
                    downloadAndInstall.setContext(getContext());
//                    downloadAndInstall.execute("http://www.mediafire.com/file/4yoc0a6reoj5x52/com.bizoteam.mobilebox_1.0.3-103.apk", "test.apk");

//                    System.out.println(tempdownload);
//                    tempdownload.replaceAll("\\","");
                    System.out.println(tempdownload);
                    count(tempDateiname);
                    downloadAndInstall.execute(tempdownload, tempDateiname+".apk");
                }
            }


        });



        name.setText(item.getName());

        Picasso.with(getContext()).load(item.getImgpath()).resize(256,256).centerCrop().into(logo);

        //logo.setImageResource(R.mipmap.ic_launcher);


        beschreibung.setText(item.getDiscription());

        //dem jeweiligen element (name, beschreibung, img..) hinzufügen



        return rowViev;
    }

    /**
     * Diese methode sollte eigentlich den downloadzähler auf dem server Inkrementiren indem eine phpseite ausgeführt wird
     * @param name der app die inkrementiert werden soll.
     */
    private void count(String name){

        HttpURLConnection c = null;
        try {
            URL u = new URL("http://lebe-app.hol.es/dbabfrage/inkrement.php?name="+name);
            c = (HttpURLConnection) u.openConnection();
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                        System.out.println("UPDATE DOWNLOAD!!! "+sb);
                    }
                    br.close();

            }

        } catch (Exception ex) {
            ex.toString();
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    //disconnect error
                }
            }
        }
    }



}
