package com.gatar.Spizarka.Depot.Operations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatar.Spizarka.Database.Item;
import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Depot.DepotOptions;
import com.gatar.Spizarka.Depot.View.DepotOverviewFragment;

import java.util.ArrayList;

/**
 * Creating adaptor for ListView in {@link DepotOverviewFragment}.
 * Based on ArrayList<Item> received in constructor.
 */
public class DepotOverviewAdapter extends ArrayAdapter<Item>{
    private int layoutResourceId;
    private Context context;
    private DepotOptions depotOptions;

    public DepotOverviewAdapter(Context context, int layoutResourceId, ArrayList<Item> data, DepotOptions depotOptions){
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.depotOptions = depotOptions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Item p = getItem(position);
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layoutResourceId, null);
            holder = new ViewHolder();
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtQuantity = (TextView) convertView.findViewById(R.id.txtQuantity);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //TODO Make view factory for different view types
        Integer quantityValue;
        switch(depotOptions){
            case ShoppingListView:
                quantityValue = p.getMinimumQuantity() - p.getQuantity();
                break;
            default:
                quantityValue = p.getQuantity();
                break;
        }
        holder.imgIcon.setImageResource(p.getCategory().getIconId());
        holder.txtQuantity.setText(String.format("%d",quantityValue));
        holder.txtTitle.setText(p.getTitle());
        return convertView;
    }

    public static class ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtQuantity;
    }

}


