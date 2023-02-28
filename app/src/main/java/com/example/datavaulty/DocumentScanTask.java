package com.example.datavaulty;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocumentScanTask extends AsyncTask<String, Void, DocumentScanTask.Result> {

    @Override
    protected Result doInBackground(String... extensions) {

        if (extensions == null) {
            extensions = new String[0];
        }

        List<File> files = new ArrayList<File>();
        boolean success = false;
        String status = "";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                files.addAll(FileUtils.listFiles(Environment.getExternalStorageDirectory(), extensions, true));
                success = true;
                status = "ok";
            } catch (Exception e) {
                Log.e("MyApp", "File error:", e);
                success = false;
                status = "Scan error";
            }
        } else {
            success = false;
            status = "External storage not available";
        }


        return new Result(success, status, files);
    }

    public static class Result {
        public final boolean success;
        public final String message;
        public final List<File> files;

        public Result(boolean success, String message, List<File> files) {
            this.success = success;
            this.message = message;
            this.files = files;
        }
    }
}