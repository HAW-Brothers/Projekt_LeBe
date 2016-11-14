package lebe.lebeprototyp02.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowViev=inflater.inflate(R.layout.market_single_item,parent,false);


        final ImageView logo = (ImageView) rowViev.findViewById(R.id.market_logo);
        TextView name = (TextView) rowViev.findViewById(R.id.market_Name);
        TextView beschreibung = (TextView) rowViev.findViewById(R.id.market_beschreibungTextView);


        //MarketItem Objekt erzeugen
        MarketItem item = itemListe.get(position);

        name.setText(item.getName());

        final String imgPfad = item.getImgpath();

        new Thread() {

            public void run() {
                URL url=null;
                final ImageView logo2 = logo;
                Bitmap image=null;
                try {
                   url = new URL(imgPfad);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    image = BitmapFactory.decodeStream(url.openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logo2.setImageBitmap(image);

            }
        }.start();

        beschreibung.setText(item.getDiscription());

        //dem jeweiligen element (name, beschreibung, img..) hinzufügen



        return rowViev;
    }
}