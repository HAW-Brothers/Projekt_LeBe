package lebe.lebeprototyp02;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackBox on 10.11.2016.
 */

public class CustomListAdapter extends ArrayAdapter<MarketApp>{

    ArrayList<MarketApp> market_apps;
    Context context;
    int resource;

    public CustomListAdapter(Context context, int resource, List<MarketApp> objects) {
        super(context, resource, objects);
        this.market_apps = market_apps;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.custom_list_layout, null, true);
        }

        MarketApp marketApp = getItem(position);


        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewApp);
        Picasso.with(context).load(marketApp.getImage()).into(imageView);

        TextView txtName = (TextView) convertView.findViewById(R.id.tvAppName);
        txtName.setText(marketApp.getAppName());

        TextView txtInfo = (TextView) convertView.findViewById(R.id.tvAppInfo);
        txtInfo.setText(marketApp.getAppInfo());


        return convertView;

    }
}
