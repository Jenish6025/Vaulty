package com.example.datavaulty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class show_history_adp extends RecyclerView.Adapter<show_history_adp.Myview> {

    Context context;
    List<show_history_pojo_class.HidehistoryDatum> hidehistoryData;

    public show_history_adp(Context context, List<show_history_pojo_class.HidehistoryDatum> hidehistoryData) {
        this.context = context;
        this.hidehistoryData = hidehistoryData;
    }

    @NonNull
    @Override
    public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.history_data_row,null);
        return new Myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myview holder, int position) {

        //holder.deviceid.setText(hidehistoryData.get(position).deviceid);
        //holder.historyid.setText(hidehistoryData.get(position).hidehistoryid);
        holder.totalitem.setText(hidehistoryData.get(position).totalitem);
        holder.type.setText(hidehistoryData.get(position).type);
        holder.hidedate.setText(hidehistoryData.get(position).hidedate);
    }

    @Override
    public int getItemCount() {
        return hidehistoryData.size();
    }

    public class Myview extends RecyclerView.ViewHolder {

        TextView deviceid,historyid,totalitem,type,hidedate;

        public Myview(@NonNull View itemView) {
            super(itemView);

            //deviceid=(TextView)itemView.findViewById(R.id.deviceid);
            //historyid=(TextView)itemView.findViewById(R.id.historyid);
            totalitem=(TextView)itemView.findViewById(R.id.totalitem);
            type=(TextView)itemView.findViewById(R.id.type);
            hidedate=(TextView)itemView.findViewById(R.id.hidedate);

        }
    }
}
