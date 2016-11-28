package lebe.lebeprototyp02.market;

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

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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


                    downloadAndInstall.setContext(getContext());
                    downloadAndInstall.execute("http://wfarm2.dataknet.com/static/resources/icons/set52/ba7285f0.png", "test.png");
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



}
