package com.example.datavaulty;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class homescreen extends AppCompatActivity {

    TextView txt_image_count,txt_video_count,txt_sound_count,txt_file_count;
    int img_count=0,video_count=0,sound_count=0,file_count=0;
    ImageView drawer,img_calculator,img_pattern,img_pin;
    LinearLayout img_main_unlock,video_main_unlock,sound_main_unlock,file_main_unlock,main_contact;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    TextView privatepolicy,history,setting,rate,premium1,term,help,changepin,changequestion,Fingerprint;
    RelativeLayout main_image,rl_main_video,rl_main_sound,rl_main_contact,rl_main_file,rl_main_app,img_pin_select,img_cal_select,img_patt_select,rl_premium;
    RelativeLayout rl_pin_lock,rl_calculator_lock,rl_pattern_lock;
    //    LottieAnimationView lotty;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen2);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //drawable
        privatepolicy=findViewById(R.id.privatepolicy);
        drawer = findViewById(R.id.drawer);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        main_image=(RelativeLayout)findViewById(R.id.main_image);
        rl_main_video=(RelativeLayout)findViewById(R.id.rl_main_video);
        rl_main_sound=(RelativeLayout)findViewById(R.id.rl_main_sound);
        img_calculator=(ImageView)findViewById(R.id.img_calculator);
        rl_main_contact=(RelativeLayout)findViewById(R.id.rl_main_contact);
        rl_main_file=(RelativeLayout)findViewById(R.id.rl_main_file);
        rl_main_app=(RelativeLayout)findViewById(R.id.rl_main_app);
        img_pin_select=(RelativeLayout)findViewById(R.id.img_pin_select);
        img_cal_select=(RelativeLayout)findViewById(R.id.img_cal_select);
        img_patt_select=(RelativeLayout)findViewById(R.id.img_patt_select);
        img_pattern=(ImageView)findViewById(R.id.img_pattern);
        img_pin=(ImageView)findViewById(R.id.img_pin);
        img_main_unlock=(LinearLayout)findViewById(R.id.img_main_unlock);
        video_main_unlock=(LinearLayout)findViewById(R.id.video_main_unlock);
        sound_main_unlock=(LinearLayout)findViewById(R.id.sound_main_unlock);
        file_main_unlock=(LinearLayout)findViewById(R.id.file_main_unlock);
        privatepolicy=(TextView)findViewById(R.id.privatepolicy);
        history=(TextView)findViewById(R.id.history);
        changepin=(TextView)findViewById(R.id.changepin);
        setting=(TextView)findViewById(R.id.setting);
        rate=(TextView)findViewById(R.id.rate);
        premium1=(TextView)findViewById(R.id.premmium);
        term=(TextView)findViewById(R.id.term);
        help=(TextView)findViewById(R.id.help);
        rl_premium=(RelativeLayout)findViewById(R.id.rl_premium);
        rl_pin_lock=(RelativeLayout)findViewById(R.id.rl_pin_lock);
        rl_calculator_lock=(RelativeLayout)findViewById(R.id.rl_calculator_lock);
        rl_pattern_lock=(RelativeLayout)findViewById(R.id.rl_pattern_lock);
        main_contact=(LinearLayout)findViewById(R.id.main_contact);
        changequestion=(TextView)findViewById(R.id.changequestion);
        Fingerprint=(TextView)findViewById(R.id.Fingerprint);

        //count Images
        txt_image_count=(TextView)findViewById(R.id.txt_image_count);
        txt_video_count=(TextView)findViewById(R.id.txt_video_count);
        txt_sound_count=(TextView)findViewById(R.id.txt_sound_count);
        txt_file_count=(TextView)findViewById(R.id.txt_file_count);

//        lotty=(LottieAnimationView)findViewById(R.id.lotty);
//        lotty.setAnimationFromJson(readJSONFromAsset(this, "ic_paymentfail"), "ic_paymentfail");
//        lotty.playAnimation();


        sharedPreferences=getSharedPreferences("check_dialer",MODE_PRIVATE);
        edit=sharedPreferences.edit();

//
//        //img
//        String img = Environment.getExternalStorageDirectory().toString()+"/Android/data/Vaulty/files/image";
//        File[] file_img;
//        File directory = new File(img);
//        file_img = directory.listFiles();
//
//        img_count=file_img.length;
//        String s=String.valueOf(img_count);
//        txt_image_count.setText(s);
//
//        //video
//        String video = Environment.getExternalStorageDirectory().toString()+"/Android/data/Vaulty/files/video";
//        File[] file_video;
//        File directory_video = new File(video);
//        file_video = directory_video.listFiles();
//
//        video_count=file_video.length;
//        String s1=String.valueOf(video_count);
//        txt_video_count.setText(s1);
//
//        //audio
//        String audio = Environment.getExternalStorageDirectory().toString()+"/Android/data/Vaulty/files/audio";
//        File[] file_audio;
//        File directory_audio = new File(audio);
//        file_audio = directory_audio.listFiles();
//
//        sound_count=file_audio.length;
//        String s2=String.valueOf(sound_count);
//        txt_sound_count.setText(s2);
//
//        //file
//        String files = Environment.getExternalStorageDirectory().toString()+"/Android/data/Vaulty/files/audio";
//        File[] file_files;
//        File directory_file = new File(files);
//        file_files = directory_file.listFiles();
//
//        file_count=file_files.length;
//        String s3=String.valueOf(file_count);
//        txt_file_count.setText(s3);

        rl_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,primium.class));
            }
        });


        String check=sharedPreferences.getString("check_dialer","no");
        Log.e("---->",""+check);


        if (check.equals("dialer"))
        {
            img_pin_select.setVisibility(View.VISIBLE);
            img_cal_select.setVisibility(View.GONE);
            img_patt_select.setVisibility(View.GONE);
        }
        else if (check.equals("calcy"))
        {
            img_pin_select.setVisibility(View.GONE);
            img_cal_select.setVisibility(View.VISIBLE);
            img_patt_select.setVisibility(View.GONE);
        }

        rl_pin_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_pin_select.setVisibility(View.VISIBLE);
                img_cal_select.setVisibility(View.GONE);
                img_patt_select.setVisibility(View.GONE);


//                final Dialog dialog1=new Dialog(homescreen.this);
//                dialog1.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
//                dialog1.setContentView(R.layout.security_dialog);
//                dialog1.setCancelable(false);
//
//                TextView no=(TextView)dialog1.findViewById(R.id.no);
//                TextView yes=(TextView)dialog1.findViewById(R.id.yes);
//
//                dialog1.show();
//
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        edit.clear();
//                        edit.commit();
//                        Log.e("---->",""+edit);
//                        edit.putString("check_dialer","dialer");
//                        edit.commit();
//                        Log.e("---->",""+edit);
//
//                        startActivity(new Intent(homescreen.this,dialer_new.class));
//                        finish();
//                    }
//                });
//
//                no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog1.cancel();
//                    }
//                });


                final AlertDialog.Builder builder=new AlertDialog.Builder(homescreen.this);
                builder.setTitle("Security Alert");
                builder.setIcon(R.drawable.ic_dialog_alert);
                builder.setMessage("Are you sure to change security type?");
                builder.setCancelable(false);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit.clear();
                        edit.commit();
                        Log.e("---->",""+edit);
                        edit.putString("check_dialer","dialer");
                        edit.commit();
                        Log.e("---->",""+edit);

                        startActivity(new Intent(homescreen.this,dialer_new.class));
                        finish();
                    }
                });

                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });

        rl_calculator_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_pin_select.setVisibility(View.GONE);
                img_cal_select.setVisibility(View.VISIBLE);
                img_patt_select.setVisibility(View.GONE);

//                final Dialog dialog=new Dialog(homescreen.this);
//                dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.security_dialog);
//                dialog.setCancelable(false);
//
//                TextView no=(TextView)dialog.findViewById(R.id.no);
//                TextView yes=(TextView)dialog.findViewById(R.id.yes);
//
//                dialog.show();
//
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        edit.clear();
//                        edit.commit();
//                        Log.e("---->",""+edit);
//                        edit.putString("check_dialer","calcy");
//                        edit.commit();
//                        Log.e("---->",""+edit);
//
//                        startActivity(new Intent(homescreen.this,calculator.class));
//                        finish();
//                    }
//                });
//
//                no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.cancel();
//                    }
//                });

                final AlertDialog.Builder builder=new AlertDialog.Builder(homescreen.this);
                builder.setTitle("Security Alert");
                builder.setIcon(R.drawable.ic_dialog_alert);
                builder.setMessage("Are you sure to change security type?");
                builder.setCancelable(false);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit.clear();
                        edit.commit();
                        Log.e("---->",""+edit);
                        edit.putString("check_dialer","calcy");
                        edit.commit();
                        Log.e("---->",""+edit);

                        startActivity(new Intent(homescreen.this,calculator.class));
                      finish();
                    }
                });

                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        main_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,Contact_hide_Activity.class));
                finish();
            }
        });

        rl_pattern_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               img_pin_select.setVisibility(View.GONE);
                img_cal_select.setVisibility(View.GONE);
                img_patt_select.setVisibility(View.VISIBLE);
            }
        });


        main_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,gallery_img.class));

            }
        });
        rl_main_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,VideoFolderAct.class));

            }
        });
        rl_main_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,SoundFolderAct.class));

            }
        });
        changequestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,changequestion.class));

            }
        });
        rl_main_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,ContactActivity.class));
            }
        });
        rl_main_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,FileFolderActivity.class));
            }
        });
        rl_main_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,ApplicationListAct.class));
            }
        });

        img_main_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,photo_unhide_act.class));

            }
        });

        video_main_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,video_unhide_act.class));

            }
        });

        sound_main_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,sound_unhide_act.class));
            }
        });

        file_main_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,file_unhide_act.class));

            }
        });
        privatepolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,private_policy.class));
            }
        });

        changepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,changepin.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,History.class));
            }
        });

        Fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(homescreen.this,Fingerprint.class));

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Setting",Toast.LENGTH_LONG).show();

            }
        });



        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Dialog dialog1=new Dialog(homescreen.this);
                dialog1.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.activity_rate);
                dialog1.setCancelable(false);

                Button btn4=dialog1.findViewById(R.id.rating);     //findviewbyid with dialog btn and textview

                dialog1.show();

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG).show();
                        dialog1.cancel();
                    }
                });

            }
        });

        premium1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,primium.class));
            }
        });

        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,termcondition.class));
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homescreen.this,help.class));
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };




        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }

    public static String readJSONFromAsset(Activity activity, String name) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("lottie_file/" + name + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}