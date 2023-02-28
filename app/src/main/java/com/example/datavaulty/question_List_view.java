package com.example.datavaulty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class question_List_view  extends ArrayAdapter<question_api_response_pojo.SequrityquestionDatum> {
    Context context;
    int resource;
    List<question_api_response_pojo.SequrityquestionDatum> objects;
    LayoutInflater layoutInflater;

    public question_List_view(@NonNull Context context, int resource, @NonNull List<question_api_response_pojo.SequrityquestionDatum> objects) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.objects=objects;
        layoutInflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=layoutInflater.inflate(resource,null);

        TextView question;
        question=(TextView)view.findViewById(R.id.question);

        question.setText(objects.get(position).question);
        Log.e("QuitionId",""+objects.get(position).questionid);

        return view;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=layoutInflater.inflate(resource,null);

        TextView question;
        question=(TextView)view.findViewById(R.id.question);

        question.setText(objects.get(position).question);

        return view;
    }
}






















