package com.example.android.shoppingbasket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.support.v4.content.ContextCompat.startActivity;
import static android.support.v7.widget.AppCompatDrawableManager.get;
import static com.example.android.shoppingbasket.R.id.info;

/**
 * Created by Renato on 2017. 10. 08..
 */

public class AdapterItem extends ArrayAdapter<Item> implements View.OnClickListener{
    private Activity activity;
    private ArrayList<Item> items;
    private static LayoutInflater inflater = null;
    Context mContext;

    public static class ViewHolder {
        TextView txtName;
        TextView txtCat;
        TextView txtCost;
        TextView txtShop;
        Button settings;
        CheckBox info;
    }
    public AdapterItem (ArrayList<Item> _items,Context context) {
        super(context, R.layout.row_item, _items);
        try {
            this.mContext = context;
            this.items = _items;
            inflater = LayoutInflater.from( context );

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return items.size();
    }

    public ArrayList<Item> getList() {
        return new ArrayList<Item>(items);
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Item _item) { items.set(position, _item); }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Item dataModel = (Item) object;

        switch (v.getId()) {
            case R.id.item_check:
//                CheckBox info=(CheckBox).findViewById(R.id.item_check);
//                info.setChecked(true);
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {
        // Get the data item for this position
        final Item dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtCat = (TextView) convertView.findViewById(R.id.cat);
            viewHolder.txtCost = (TextView) convertView.findViewById(R.id.cost);
            viewHolder.txtShop = (TextView) convertView.findViewById(R.id.shop);
            viewHolder.settings = (Button) convertView.findViewById(R.id.settings);
            viewHolder.settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ((ListView) parent).performItemClick(view, position, 0);
                    Intent toSettings = new Intent(parent.getContext(), settings.class);
                        toSettings.putExtra("sampleObject",items);
                        toSettings.putExtra("position",position);
                        parent.getContext().startActivity(toSettings);

                }
            });
            viewHolder.info = (CheckBox) convertView.findViewById(R.id.item_check);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtCat.setText(dataModel.getCategory());
        viewHolder.txtCost.setText(String.valueOf(dataModel.getPrice()));
        viewHolder.txtShop.setText(dataModel.getShop());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }




}
