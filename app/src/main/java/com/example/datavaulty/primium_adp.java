package com.example.datavaulty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class primium_adp extends RecyclerView.Adapter<primium_adp.Myview> {

    Context context;
    List<primium_pojo_response.PackageDatum> packageData;

    public primium_adp(Context context, List<primium_pojo_response.PackageDatum> packageData) {
        this.context = context;
        this.packageData = packageData;
    }

    @NonNull
    @Override
    public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.primium_data_row,null);
        return new primium_adp.Myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myview holder, int position) {

        String iconpath="http://skysparrow.in/"+packageData.get(position).icon.toString().replace(" ","%20");
        Log.e("hfgh"," "+iconpath);
        String amount=packageData.get(position).duration;
        String amount1=amount+" Month";
        String price=packageData.get(position).price;
        String price1=price+"/-";


        holder.package_name.setText(packageData.get(position)._package);
        holder.package_duration.setText(amount1);
        holder.package_amount.setText(price1);
        Glide.with(context).load(iconpath).into(holder.packicon);
    }

    @Override
    public int getItemCount() {
        return packageData.size();
    }

    public class Myview extends RecyclerView.ViewHolder {

        TextView package_name,package_duration,package_amount;
        ImageView packicon;

        public Myview(@NonNull View itemView) {
            super(itemView);

            package_name=(TextView)itemView.findViewById(R.id.package_name);
            package_duration=(TextView)itemView.findViewById(R.id.package_duration);
            package_amount=(TextView)itemView.findViewById(R.id.package_amount);
            packicon=(ImageView)itemView.findViewById(R.id.packicon);
        }
    }
}
