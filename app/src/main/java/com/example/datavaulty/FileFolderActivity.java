package com.example.datavaulty;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class FileFolderActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    List<Model_Files_1> files = new ArrayList<>();
    ArrayList<String> TotalFolder = new ArrayList<>();
    FileFolderListAdapter fileFolderListAdapter;
    ImageView img_back;
    private ProgressDialog progressDialog;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_folder);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        avi=findViewById(R.id.avi);
        img_back=(ImageView)findViewById(R.id.img_back);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        //progressDialog.show();
        avi.show();
        getAllFiles();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getAllFiles() {
        files.clear();
        @SuppressLint("StaticFieldLeak") DocumentScanTask task = new DocumentScanTask() {
            @Override
            protected void onPostExecute(Result result) {
                super.onPostExecute(result);
                if (result.success) {
                    for (int i = 0; i < result.files.size(); i++) {
                        files.add(new Model_Files_1(result.files.get(i), false));
                    }
                    TotalFolder = getTotalFolder(files);
                    populateRecyclerView();
                } else {
                    String error = result.message;
                    Log.w("msg", "Error ;" + error);
                    recycler_view.setAdapter(null);
//                    hud.dismiss();
                }

            }
        };
        task.execute("pdf", "doc", "docx", "html", "htm", "odt", "xls", "xlsx", "ods", "ppt", "pptx", "txt");
    }

    private void populateRecyclerView() {
        try {
            if (files.size() != 0) {

                fileFolderListAdapter = new FileFolderListAdapter(FileFolderActivity.this, files, TotalFolder);
                GridLayoutManager mLayoutManager = new GridLayoutManager(FileFolderActivity.this, 2);
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(fileFolderListAdapter);
                //progressDialog.hide();
                avi.hide();
                fileFolderListAdapter.notifyDataSetChanged();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getTotalFolder(List<Model_Files_1> VideoLists) {
        ArrayList<String> Folders = new ArrayList<>();
        for (int i = 0; i < VideoLists.size(); i++) {
            String FolderName = VideoLists.get(i).getFile().getParentFile().getName();
            if (!Folders.contains(FolderName))
                Folders.add(FolderName);
        }
        return Folders;
    }
}
