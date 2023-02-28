package com.example.datavaulty;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class photos_act extends AppCompatActivity {

    RecyclerView selectimage;
    int int_position;
    ArrayList<String> FilesList;
    GridViewAdapter gridViewAdapter;
    List<File> Selectedfiles;
    List<PrivateImageModel> files = new ArrayList<>();
    TextView header;
    int idlaviya, subidlaviya;
    String catname;
    Bundle extras;
    ImageView img_back, img_lock_photo;
    TextView txt_main_remove_all_effect;
    AsyncHttpClient asyncHttpClient;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_act);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreferences=getSharedPreferences("Deviceid",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        selectimage = findViewById(R.id.selectimage);
        header = findViewById(R.id.header);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_lock_photo = (ImageView) findViewById(R.id.img_lock_photo);


        int_position = getIntent().getIntExtra("value", 0);
        Log.e("flbgfgd", " " + int_position);

        Selectedfiles = new ArrayList<>();
        Intent i = getIntent();
        idlaviya = i.getIntExtra("idlaine", 0);
        subidlaviya = i.getIntExtra("subidlaine", 0);

        extras = getIntent().getExtras();
        if (extras != null) {
            catname = extras.getString("catnamelaine");
        }

        Log.e("hfglkhdfgd", "kdflg " + catname);
        Log.e("hfglkh", "kdflg " + subidlaviya);

        try {
            header.setText(gallery_img.al_images.get(int_position).getStr_folder());
        } catch (Exception e) {
            e.printStackTrace();
        }

        img_lock_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Dialog dialog=new Dialog(photos_act.this);
                        dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.lock_dialog);
                        dialog.setCancelable(false);

                        Button btn_finish=(Button)dialog.findViewById(R.id.btn_finish);

                        txt_main_remove_all_effect=dialog.findViewById(R.id.dialog_count);
                        int cnt=0;
                        for (File temp:Selectedfiles)
                        {
                            cnt++;
                        }

                        txt_main_remove_all_effect.setText(cnt+" Images Added to Private");

                        dialog.show();
                        int count=0;

                        Log.e("----select",""+Selectedfiles);
                for (File temp:Selectedfiles)
               {
                   Log.e("Path--",temp.toString());

                   String sdCard = Environment.getExternalStorageDirectory().toString();
                   // the file to be moved or copied
                   File sourceLocation = new File (""+temp);
                   // make sure your target location folder exists!
                   File vault = new File (sdCard + "/Android/data/Vaulty");
                       File file = new File (sdCard + "/Android/data/Vaulty/files");
                   File image = new File (sdCard + "/Android/data/Vaulty/files/image");

                   if (!vault.exists())
                   {
                       vault.mkdir();
                   }
                   if(!file.exists())
                   {
                       file.mkdir();
                   }
                   if(!image.exists())
                   {
                       image.mkdir();
                   }


                   //sourcelocation
                   String sub2=sourceLocation.toString();

                   String s1=sub2.substring(1,sub2.length());
                   File sourcelocation1=new File(s1);
                Log.e("----s1",""+sourcelocation1);


                   //targetlocation
                   String sub1=null;
                   sub1=temp.toString();

                   String s=sub1.substring(1,sub1.length());

                   Log.e("s--",""+s);

                   String[] substr1=s.split("/");

                   Log.e("substr----",""+substr1);

                   int len=substr1.length-1;

                   String name=substr1[len];

                   Log.e("split",""+name);

                   File targetLocation=new File(image+"/"+name);

                   Log.e("video locations",""+sourcelocation1);
                   Log.e("video locationt",""+targetLocation);
                   // moving the file to another directory
                   sourcelocation1.renameTo(targetLocation);
                   count++;
               }

                        String deviceid=sharedPreferences.getString("Deviceid",null);

                        Log.e("---->count",""+count);
                        Log.e("---->device",""+deviceid);


                        if (asyncHttpClient!=null)
                        {
                            asyncHttpClient.cancelRequests(photos_act.this,true);
                        }

                        asyncHttpClient=new AsyncHttpClient();
                        RequestParams requestParams=new RequestParams();
                        requestParams.put("deviceid",deviceid);
                        requestParams.put("totalitem",count);
                        requestParams.put("type","Photo Hide");

                        asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_addhistory.php",requestParams,new Photohandler());


                        btn_finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                startActivity(new Intent(photos_act.this,homescreen.class));
                                finish();
                            }
                        });
                    }
                },3);

            }
        });



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        files.clear();
        selectimage.setAdapter(null);
        //FilesList = getIntent().getStringArrayListExtra("array");
        try {
                for (int i = 0; i < gallery_img.al_images.get(int_position).getAl_imagepath().size(); i++) {
                    files.add(new PrivateImageModel(gallery_img.al_images.get(int_position).getAl_imagepath().get(i),false));
                }
            gridViewAdapter = new GridViewAdapter(this, files);
            GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            selectimage.setLayoutManager(mLayoutManager);
            selectimage.setItemAnimator(new DefaultItemAnimator());
            selectimage.setAdapter(gridViewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }

//        selectimage.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        try {
//                            files.get(position).setSelected(!files.get(position).isSelected());
//                            if (files.get(position).isSelected()){
//                                Intent ii=new Intent(this,Display_Template_activity.class);
//                                ii.putExtra("imagepath",files.get(position).getImagePath());
//                                ii.putExtra("idnext",idlaviya);
//                                ii.putExtra("subidnext",subidlaviya);
//                                ii.putExtra("namenext",catname);
//                                ii.putExtra("chack",0);
//                                startActivity(ii);
//                                Log.e("flbggdf", " " + catname);
//                                Log.e("flbg", " " + idlaviya);
//                                Log.e("flbg", " " + subidlaviya);
//
//                            }
//
//                            else{
//                                view.findViewById(R.id.iv_image_select).setBackgroundResource(0);
//                            }
//                            if (files.get(position).isSelected()) {
//                                Selectedfiles.add(files.get(position).getImagePath());
//                            } else
//                                Selectedfiles.remove(files.get(position).getImagePath());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                })
//        );

    }
    public class Photohandler extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);

            Log.e("---->",data);

            history_pojo_class history=new Gson().fromJson(data,history_pojo_class.class);



        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }

    class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder>  {

        Context context;
        List<PrivateImageModel> files = new ArrayList<>();
        int positionn;


        public GridViewAdapter(Context context, List<PrivateImageModel> files) {
            this.files = files;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photosfolder, parent, false);
            return new ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.tv_foldersize.setText(files.get(position).getImagePath().substring(files.get(position).getImagePath().lastIndexOf("/")+1));
            holder.tv_foldern.setVisibility(View.GONE);

            final PrivateImageModel privateImageModel=(PrivateImageModel) files.get(position);

            Glide.with(context).load(files.get(position).getImagePath())
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
                    }).into(holder.iv_image);

            Utils utils=new Utils();
            utils.NotifySDCard(photos_act.this, privateImageModel.getImagePath());


            positionn=position;

            if(files.get(position).isSelected())
            {
                ((ViewHolder) holder).uppere_img.setVisibility(View.VISIBLE);
            }
            else
            {
                ((ViewHolder) holder).uppere_img.setVisibility(View.GONE);
            }

            ((ViewHolder) holder).iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        if (((PrivateImageModel) files.get(position)).isSelected()) {
                            ((PrivateImageModel) files.get(position)).setSelected(false);
                            Selectedfiles.remove(privateImageModel.getImagePath());

                            Log.e("---------->remove", "" + privateImageModel.getImagePath());
                        } else {
                            ((PrivateImageModel) files.get(position)).setSelected(true);
            //                List<String> Selectedfiles = new ArrayList<>();
                            Selectedfiles.add(new File(privateImageModel.getImagePath()));

                            Log.e("---------->add", "" + privateImageModel.getImagePath());
                        }

                        notifyDataSetChanged();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return files.size();
        }

        public void Clear() {
            Selectedfiles.clear();
        }

//        public List<String> getSelectedFile() {
//            return Selectedfiles;
//        }


        public  class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_foldern, tv_foldersize;
            ImageView iv_image;
            RelativeLayout uppere_img,click;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_foldern = (TextView) itemView.findViewById(R.id.tv_folder);
                tv_foldersize = (TextView) itemView.findViewById(R.id.tv_folder2);
                iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
                click=itemView.findViewById(R.id.click);
                uppere_img=(RelativeLayout)itemView.findViewById(R.id.uppere_img);

            }
        }

    }
}



