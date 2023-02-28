package com.example.datavaulty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class FavoriteContactListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ContactModel> contactModels;

    public FavoriteContactListAdapter(Context context, ArrayList<ContactModel> Models) {
        this.mContext = context;
        this.contactModels = Models;

    }

    @Override
    public int getCount() {
        return contactModels.size();
    }

    @Override
    public Object getItem(int position) {
        return contactModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.favorit_contact_item, parent, false);
        TextView Name = (TextView) rowView.findViewById(R.id.txt_con_name);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
        TextView Number = (TextView) rowView.findViewById(R.id.txt_con_number);
        Name.setText(contactModels.get(position).getName());
//        Number.setText(contactModels.get(position).getPhoneNumber());
        Number.setText("mobile");

        if (contactModels.get(position).getPicture() != null)
            icon.setImageBitmap(contactModels.get(position).getPicture());
//        Glide.with(mContext)
//                .asBitmap()
//                .placeholder(R.drawable.ic_launcher)
//                .load(contactModels.get(position).getPicture())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(icon);
        return rowView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
