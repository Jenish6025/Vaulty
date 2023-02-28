package com.example.datavaulty;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;

public class gallery_img extends AppCompatActivity {

    RecyclerView fromemobile;
    private static final int REQUEST_PERMISSIONS = 100;
    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    Adapter_PhotosFolder obj_adapter;
    ImageView img_back;
    private ProgressDialog progressDialog;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_img);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fromemobile=findViewById(R.id.fromemobile);
        img_back=findViewById(R.id.img_back);
        avi=findViewById(R.id.avi);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        //progressDialog.show();
        avi.show();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        opem_Gallery();

        fromemobile.addOnItemTouchListener(
                new RecyclerItemClickListener(gallery_img.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            Intent intent=new Intent(gallery_img.this,photos_act.class);
                            intent.putExtra("value", position);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }
    private void opem_Gallery() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(gallery_img.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                        if ((ActivityCompat.shouldShowRequestPermissionRationale(gallery_img.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(gallery_img.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE))) {
                        } else {
                            ActivityCompat.requestPermissions(gallery_img.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_PERMISSIONS);
                        }
                    } else {
                        Log.e("Else", "Else");
                        getPicturePaths();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);


    }

    private ArrayList<Model_images> getPicturePaths() {
        al_images.clear();
        try {
            int int_position = 0;
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            String absolutePathOfImage = null;
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Log.e("uri------",""+uri);
            String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
            Log.e("hyyy------",""+projection);
            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            cursor = getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);
                Log.e("Column", absolutePathOfImage);
                Log.e("Folder", cursor.getString(column_index_folder_name));
                for (int i = 0; i < al_images.size(); i++) {
                    if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                        boolean_folder = true;
                        int_position = i;
                        break;
                    } else {
                        boolean_folder = false;
                    }
                }
                if (boolean_folder) {
                    try {
                        ArrayList<String> al_path = new ArrayList<>();
                        al_path.addAll(al_images.get(int_position).getAl_imagepath());
                        al_path.add(absolutePathOfImage);
                        al_images.get(int_position).setAl_imagepath(al_path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ArrayList<String> al_path = new ArrayList<>();
                    al_path.add(absolutePathOfImage);
                    Model_images obj_model = new Model_images();
                    obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                    obj_model.setAl_imagepath(al_path);
                    al_images.add(obj_model);
                }
            }
            for (int i = 0; i < al_images.size(); i++) {
                Log.e("FOLDER", al_images.get(i).getStr_folder());
                for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                    Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
                }
            }
            if (al_images.isEmpty()) {
            } else {

                obj_adapter = new Adapter_PhotosFolder(gallery_img.this, al_images);
                GridLayoutManager mLayoutManager = new GridLayoutManager(gallery_img.this, 2);
                fromemobile.setLayoutManager(mLayoutManager);
                fromemobile.setItemAnimator(new DefaultItemAnimator());
                fromemobile.setAdapter(obj_adapter);

                //progressDialog.hide();
                avi.hide();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return al_images;
    }
    public static void NotifySDCard(Context context, String FileName) {
        try {
            Log.w("msg", "Notify SDCard File: " + FileName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                final Uri contentUri = Uri.fromFile(new File(FileName));
                scanIntent.setData(contentUri);
                context.sendBroadcast(scanIntent);
            } else {
                final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + FileName));
                context.sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
