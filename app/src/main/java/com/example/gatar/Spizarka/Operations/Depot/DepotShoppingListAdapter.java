package com.example.gatar.Spizarka.Operations.Depot;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gatar.Spizarka.Database.Item;
import com.example.gatar.Spizarka.R;

import java.util.ArrayList;

/**
 * Creating adaptor for ListView in {@link com.example.gatar.Spizarka.Fragments.Depot.DepotOverviewFragment}.
 * Based on ArrayList<Item> received in constructor. Show quantity is calculated as subtraction between minimum quantity and on stock quantity.
 *
 */
public class DepotShoppingListAdapter extends ArrayAdapter<Item> {
    private int layoutResourceId;
    private Context context;

    public DepotShoppingListAdapter(Context context, int layoutResourceId, ArrayList<Item> data){
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
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

        holder.imgIcon.setImageResource(p.getCategory().getIconId());
        holder.txtQuantity.setText(String.format("%d",p.getMinimumQuantity()-p.getQuantity()));
        holder.txtTitle.setText(p.getTitle());
        return convertView;
    }

    public static class ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtQuantity;
    }
}
