package com.example.datavaulty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import java.util.ArrayList;

public class Adapter_PhotosFolder extends RecyclerView.Adapter<Adapter_PhotosFolder.ViewHolder> {

    Context context;
    ArrayList<Model_images> al_menu = new ArrayList<>();
    ArrayList<Model_images> selectedList = new ArrayList<>();
    int positionn;

    public Adapter_PhotosFolder(Context context, ArrayList<Model_images> al_menu) {
        this.al_menu = al_menu;
        this.context = context;
        this.al_menu.addAll(al_menu);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photosfolder, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_foldern.setText(al_menu.get(position).getStr_folder());
        holder.tv_foldersize.setText(al_menu.get(position).getAl_imagepath().size() + "");

        Log.e("----->","file://" + al_menu.get(position).getAl_imagepath().get(0));
        Glide.with(context).load("file://" + al_menu.get(position).getAl_imagepath().get(0))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.iv_image);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        Log.e("count"," "+al_menu.size());
        return al_menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
        RelativeLayout uppere_img,click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foldern = (TextView) itemView.findViewById(R.id.tv_folder);
            tv_foldersize = (TextView) itemView.findViewById(R.id.tv_folder2);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            uppere_img=(RelativeLayout)itemView.findViewById(R.id.uppere_img);
            click=(RelativeLayout)itemView.findViewById(R.id.click);
        }
    }
}
