package com.example.datavaulty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Contact_hide_Activity extends AppCompatActivity {

    ArrayList<cust_help> ar;
    RecyclerView c_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_hide_);

        ar=new ArrayList<>();
        c_list=findViewById(R.id.c_list);

    }

    public class contact_hide_adp extends RecyclerView.Adapter<contact_hide_adp.Myview> {

        Context context;
        ArrayList<cust_help> data;

        public contact_hide_adp(Context context, ArrayList<cust_help> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(context).inflate(R.layout.contact_hide_item,null);
            return new Myview(view);

        }

        @Override
        public void onBindViewHolder(@NonNull Myview holder, int position) {
            holder.txt_con_name.setText(data.get(position).getName());
            holder.txt_con_number.setText(data.get(position).getPhone_no());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class Myview extends RecyclerView.ViewHolder {

            TextView txt_con_name,txt_con_number;

            public Myview(@NonNull View itemView) {
                super(itemView);

                txt_con_name=(TextView)itemView.findViewById(R.id.txt_con_name);
                txt_con_number=(TextView)itemView.findViewById(R.id.txt_con_number);

            }
        }
    }

}
