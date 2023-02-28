package com.example.datavaulty;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DialpadContactListAdapter extends RecyclerView.Adapter<DialpadContactListAdapter.MyViewHolder>  {
    Context mContext;
    ArrayList<ContactModel> contactModels;
    String name, FirstLetter = "a";
    boolean SameHeading = true;
    private String title = "";

    public DialpadContactListAdapter(Context context, ArrayList<ContactModel> Models) {
        this.mContext = context;
        this.contactModels = Models;
    }

//    @Override
//    public int getCount() {
//        return contactModels.size();
//    }

//    @Override
//    public Object getItem(int position) {
//        return contactModels.get(position);
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialpad_contact_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        name = contactModels.get(position).getName();

        if (!TextUtils.equals("" + contactModels.get(position).getName().charAt(0), title)) {
            title = "" + contactModels.get(position).getName().charAt(0);
            holder.txt_title_name.setVisibility(View.VISIBLE);
            holder.txt_title_name.setText("" + contactModels.get(position).getName().charAt(0));
        }

        holder.Name.setText(name);
        holder.number.setText(contactModels.get(position).getPhoneNumber());
        if (contactModels.get(position).getPicture() != null)
            holder.Icon.setImageBitmap(contactModels.get(position).getPicture());
        holder.ll_main_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel: " + contactModels.get(position).getPhoneNumber()));
//                mContext.startActivity(callIntent);

                try {
                    Intent callIntent = new Intent(mContext, contacts_detail.class);
                    callIntent.putExtra("name", contactModels.get(position).getName());
                    callIntent.putExtra("number", contactModels.get(position).getPhoneNumber());
                    callIntent.putExtra("email", contactModels.get(position).getContectEmail());
                    mContext.startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<ContactModel> filterdNames) {
        this.contactModels = filterdNames;
        notifyDataSetChanged();
    }


    private boolean checkFirstLetter(int Position, String Letter) {
        if (TextUtils.equals("" + getFirstLetter(Position), Letter))
            return true;
        else {
            FirstLetter = "" + contactModels.get(Position).getName().charAt(0);
            return false;
        }
    }

    private String getFirstLetter(int Position) {
        return "" + contactModels.get(Position).getName().charAt(0);
    }


    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= contactModels.size())
            return null;

        String name = contactModels.get(pos).getName();
        if (name == null || name.length() < 1)
            return null;

        return contactModels.get(pos).getName().substring(0, 1);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView txt_title_name;
        private final TextView number;
        private final LinearLayout ll_main_contact;
        ImageView Icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.txt_con_name);
            txt_title_name = (TextView) itemView.findViewById(R.id.txt_title_name);
            number = (TextView) itemView.findViewById(R.id.txt_con_number);
            Icon = (ImageView) itemView.findViewById(R.id.icon);
            ll_main_contact = (LinearLayout) itemView.findViewById(R.id.ll_main_contact);
        }
    }


}
