package com.example.datavaulty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class permission extends AppCompatActivity {

    Button btnOk;
    CheckBox checkBox1,checkBox2,checkBox3;
    private static final int STORAGE_PERMISSION_CODE=7;
    private PrefManager prefManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean data=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



        btnOk=findViewById(R.id.btnOk);
        checkBox1=findViewById(R.id.checkbox1);
        checkBox2=findViewById(R.id.checkbox2);
        checkBox3=findViewById(R.id.checkbox3);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked()==true && checkBox2.isChecked()==true && checkBox3.isChecked() == true) {
                    if (!requestStoragePermission()) {
                        RequestMultiplePermission();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Select All Permission",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private boolean requestStoragePermission()
    {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ForthPermissionResult= ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_CONTACTS);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED ;

    }

    private void RequestMultiplePermission() {
        ActivityCompat.requestPermissions(permission.this, new String[]
                {
                        WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE,
                        CALL_PHONE,
                        WRITE_CONTACTS


                }, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if(requestCode==STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {
                data=true;

                startActivity(new Intent(permission.this,instruction.class));
                finish();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Permission Not Granted",Toast.LENGTH_LONG).show();
            }
        }
    }
}
