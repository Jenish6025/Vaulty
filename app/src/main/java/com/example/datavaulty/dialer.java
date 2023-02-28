package com.example.datavaulty;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class dialer extends AppCompatActivity {

    private DialpadContactListAdapter contactListAdapter;
    TextView txt_add_favorit,txt_edit_favorit,startext,recenttext,contactstext,keypadtext,Voicemailtext,txt_contact_add;
    ImageView voicemailimg,keypadimg,contactimg,recentimg,starimg;
    TextView txt_no;
    TextView one,two,three,four,five,six,seven,eight,nine,zero,star,hash,addno;
    ImageView call,star1,recent,contact,dialpad,backspace,voicemail;
    String btn_one,btn_two,btn_three,btn_four,btn_five,btn_six,btn_seven,btn_eight,btn_nine,btn_zero,btn_star,btn_hash;
    LinearLayout lone,ltwo,lthree,lfour,lfive,lsix,lseven,leight,lnine,lzero,lstar,lhash,textview,spotlight,ll_favorit,ll_main_favorit,main_dialer;
    LinearLayout ll_recent,ll_contact,li_keypad,li_voice;
    int cnt=0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    KProgressHUD hud;
    int PICK_CONTACT_REQUEST = 101;
    ArrayList<ContactModel> Contects = new ArrayList<>();
    ArrayList<ContactModel> FavoritecontactModels = new ArrayList<>();
    private Cursor cursorfavorit;
    String contactID;
    String Name = "";
    private Cursor favoritphones;
    private Cursor favoritemails;
    private Cursor cursorcontacts;
    String phoneNo = "",newPin;
    Bitmap Picture = null;
    GridView list_favorite;
    RecyclerView list_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);





        sharedPreferences=getSharedPreferences("Pin",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        sharedPreferences=getSharedPreferences("First",MODE_PRIVATE);
        String data=sharedPreferences.getString("First","no");




        Intent intent=getIntent();
        String changepin=intent.getStringExtra("changepin");
        Log.e("---->changepins",""+changepin);


        newPin=intent.getStringExtra("newPin");


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ShowSportLight1("cnt+1");
            }
        }, 2400);


        spotlight=findViewById(R.id.spot);
        one=(TextView) findViewById(R.id.one);
        two=(TextView)findViewById(R.id.two);
        three=(TextView)findViewById(R.id.three);
        four=(TextView)findViewById(R.id.four);
        five=(TextView)findViewById(R.id.five);
        six=(TextView)findViewById(R.id.six);
        seven=(TextView)findViewById(R.id.seven);
        eight=(TextView)findViewById(R.id.eight);
        nine=(TextView)findViewById(R.id.nine);
        zero=(TextView)findViewById(R.id.zero);
        star=(TextView)findViewById(R.id.star);
        hash=(TextView)findViewById(R.id.hash);
        addno=(TextView)findViewById(R.id.addno);
        txt_no=(TextView) findViewById(R.id.txt_no);
        call=(ImageView) findViewById(R.id.call);
        star1=(ImageView)findViewById(R.id.star1);
        recent=(ImageView)findViewById(R.id.recent);
        dialpad=(ImageView)findViewById(R.id.dialpad);
        contact=(ImageView)findViewById(R.id.contact);
        backspace=(ImageView) findViewById(R.id.backspace);
        voicemail=(ImageView)findViewById(R.id.voicemail);
        lone=(LinearLayout)findViewById(R.id.lone);
        ltwo=(LinearLayout)findViewById(R.id.ltwo);
        lthree=(LinearLayout)findViewById(R.id.lthree);
        lfour=(LinearLayout)findViewById(R.id.lfour);
        lfive=(LinearLayout)findViewById(R.id.lfive);
        lsix=(LinearLayout)findViewById(R.id.lsix);
        lseven=(LinearLayout)findViewById(R.id.lseven);
        leight=(LinearLayout)findViewById(R.id.leight);
        lnine=(LinearLayout)findViewById(R.id.lnine);
        lzero=(LinearLayout)findViewById(R.id.lzero);
        lhash=(LinearLayout)findViewById(R.id.lhash);
        lstar=(LinearLayout)findViewById(R.id.lstar);
        textview=(LinearLayout)findViewById(R.id.textview);
        spotlight=(LinearLayout)findViewById(R.id.spot);
        ll_recent=(LinearLayout)findViewById(R.id.ll_recent);
        ll_contact=(LinearLayout)findViewById(R.id.ll_contact);
        li_keypad=(LinearLayout)findViewById(R.id.li_keypad);
        li_voice=(LinearLayout)findViewById(R.id.li_voice);
        startext=(TextView)findViewById(R.id.startext);
        recenttext=(TextView)findViewById(R.id.recenttext);
        contactstext=(TextView)findViewById(R.id.contacttext);
        keypadtext=(TextView)findViewById(R.id.keypadtext);
        Voicemailtext=(TextView)findViewById(R.id.Voicemailtext);
        starimg=(ImageView)findViewById(R.id.starimg);
        recentimg=(ImageView)findViewById(R.id.recentimg);
        contactimg=(ImageView)findViewById(R.id.contactimg);
        keypadimg=(ImageView)findViewById(R.id.keypadimg);
        voicemailimg=(ImageView)findViewById(R.id.voicemailimg);

        btn_one=one.getText().toString();
        btn_two=two.getText().toString();
        btn_three=three.getText().toString();
        btn_four=four.getText().toString();
        btn_five=five.getText().toString();
        btn_six=six.getText().toString();
        btn_seven=seven.getText().toString();
        btn_eight=eight.getText().toString();
        btn_nine=nine.getText().toString();
        btn_zero=zero.getText().toString();
        btn_star=star.getText().toString();
        btn_hash=hash.getText().toString();

        //favourate
        txt_add_favorit=(TextView)findViewById(R.id.txt_add_favorit);
        txt_edit_favorit=(TextView)findViewById(R.id.txt_edit_favorit);
        list_favorite=(GridView)findViewById(R.id.list_favorite);
        ll_favorit=(LinearLayout)findViewById(R.id.ll_favorit);
        ll_main_favorit=(LinearLayout)findViewById(R.id.ll_main_favorit);
        main_dialer=(LinearLayout)findViewById(R.id.main_dialer);
        txt_contact_add=(TextView)findViewById(R.id.txt_contact_add);
        list_contact=(RecyclerView)findViewById(R.id.list_contact);

        if (changepin.equals("yes"))
        {
            spotlight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data=txt_no.getText().toString();
                    Log.e("Dialer", "onClick: " + data );

//                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

                    if (data.length()==4)
                    {
                        Intent intent=new Intent(getApplicationContext(),check_dialer.class);
                        intent.putExtra("pin",data);
                        startActivity(intent);
                        //startActivity(new Intent(dialer.this,check_dialer.class));
                        finish();
                    }
                    else
                    {
                        final Dialog dialog=new Dialog(dialer.this);
                        dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_create);
                        dialog.setCancelable(false);
                        Button btn1=dialog.findViewById(R.id.btn_create);
                        dialog.show();

                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getApplicationContext(),"Move on another Screen",Toast.LENGTH_LONG).show();
                                txt_no.setText("");
                                addno.setVisibility(View.INVISIBLE);
                                dialog.cancel();
                            }
                        });

                    }
                }
            });
        }


        spotlight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                editor.putString("PinLock",txt_no.getText().toString());
//                editor.commit();

//                String data=sharedPreferences.getString("PinLock",null);
                String data=txt_no.getText().toString();
                Log.e("Dialer", "onClick: " + data );

//                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

                if (data.length()==4)
                {
                    Intent intent=new Intent(getApplicationContext(),check_dialer.class);
                    intent.putExtra("pin",data);
                    startActivity(intent);
                    //startActivity(new Intent(dialer.this,check_dialer.class));
                    finish();

                }
                else {
//                    new FancyGifDialog.Builder(dialer.this)
//                            .setTitle("Set pin")
//                            .setMessage("Enter only 4 digit pin.")
//                            .setPositiveBtnBackground("#E02B57")
//                            .setPositiveBtnText("Got it!")
//                            .setNegativeBtnBackground("#E02B57")
//                            .setGifResource(R.drawable.gif1)   //Pass your Gif here
//                            .isCancellable(true)
//                            .OnPositiveClicked(new FancyGifDialogListener() {
//                                @Override
//                                public void OnClick() {
//
//                                }
//                            })
//                            .build();
                    final Dialog dialog=new Dialog(dialer.this);
                    dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_create);
                    dialog.setCancelable(false);
                    Button btn1=dialog.findViewById(R.id.btn_create);
                    dialog.show();

                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getApplicationContext(),"Move on another Screen",Toast.LENGTH_LONG).show();
                            txt_no.setText("");
                            addno.setVisibility(View.INVISIBLE);
                            dialog.cancel();
                        }
                    });

                }
            }
        });

        ll_favorit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                main_dialer.setVisibility(View.INVISIBLE);
                ll_main_favorit.setVisibility(View.VISIBLE);

                startext.setTextColor(R.color.plainblack);
                recenttext.setTextColor(R.color.blue);
                contactstext.setTextColor(R.color.blue);
                keypadtext.setTextColor(R.color.blue);
                Voicemailtext.setTextColor(R.color.blue);

                starimg.setImageResource(R.drawable.ic_star_blue_24dp);
                recentimg.setImageResource(R.drawable.ic_recents_black_24dp);
                contactimg.setImageResource(R.drawable.ic_contacts_black_24dp);
                keypadimg.setImageResource(R.drawable.ic_dialpad_black_24dp);
                voicemailimg.setImageResource(R.drawable.ic_voicemail_black_24dp);



                LoadFavoriteData();
            }
        });

        ll_recent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                main_dialer.setVisibility(View.INVISIBLE);


                startext.setTextColor(R.color.blue);
                recenttext.setTextColor(R.color.plainblack);
                contactstext.setTextColor(R.color.blue);
                keypadtext.setTextColor(R.color.blue);
                Voicemailtext.setTextColor(R.color.blue);

                starimg.setImageResource(R.drawable.ic_star_black_24dp);
                recentimg.setImageResource(R.drawable.ic_recents_blue_24dp);
                contactimg.setImageResource(R.drawable.ic_contacts_black_24dp);
                keypadimg.setImageResource(R.drawable.ic_dialpad_black_24dp);
                voicemailimg.setImageResource(R.drawable.ic_voicemail_black_24dp);


            }
        });



        ll_contact.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                main_dialer.setVisibility(View.INVISIBLE);


                startext.setTextColor(R.color.blue);
                recenttext.setTextColor(R.color.blue);
                contactstext.setTextColor(R.color.plainblack);
                keypadtext.setTextColor(R.color.blue);
                Voicemailtext.setTextColor(R.color.blue);

                starimg.setImageResource(R.drawable.ic_star_black_24dp);
                recentimg.setImageResource(R.drawable.ic_recents_black_24dp);
                contactimg.setImageResource(R.drawable.ic_contacts_blue_24dp);
                keypadimg.setImageResource(R.drawable.ic_dialpad_black_24dp);
                voicemailimg.setImageResource(R.drawable.ic_voicemail_black_24dp);

                LoadContactData();

            }
        });

        li_keypad.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                main_dialer.setVisibility(View.VISIBLE);


                startext.setTextColor(R.color.blue);
                recenttext.setTextColor(R.color.blue);
                contactstext.setTextColor(R.color.blue);
                keypadtext.setTextColor(R.color.plainblack);
                Voicemailtext.setTextColor(R.color.blue);

                starimg.setImageResource(R.drawable.ic_star_black_24dp);
                recentimg.setImageResource(R.drawable.ic_recents_black_24dp);
                contactimg.setImageResource(R.drawable.ic_contacts_black_24dp);
                keypadimg.setImageResource(R.drawable.ic_dialpad_blue_24dp);
                voicemailimg.setImageResource(R.drawable.ic_voicemail_black_24dp);


            }
        });

        li_voice.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                main_dialer.setVisibility(View.INVISIBLE);


                startext.setTextColor(R.color.blue);
                recenttext.setTextColor(R.color.blue);
                contactstext.setTextColor(R.color.blue);
                keypadtext.setTextColor(R.color.blue);
                Voicemailtext.setTextColor(R.color.plainblack);

                starimg.setImageResource(R.drawable.ic_star_black_24dp);
                recentimg.setImageResource(R.drawable.ic_recents_black_24dp);
                contactimg.setImageResource(R.drawable.ic_contacts_black_24dp);
                keypadimg.setImageResource(R.drawable.ic_dialpad_black_24dp);
                voicemailimg.setImageResource(R.drawable.ic_voicemail_blue_24dp);


            }
        });

        addno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dialer.this,contacts_detail.class));
            }
        });
        lone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_one);
                cnt=cnt+1;
                if(cnt==0)
                {
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                    addno.setVisibility(View.INVISIBLE);
                }
                else
                {
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                    addno.setVisibility(View.VISIBLE);
                }
            }
        });
        ltwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_two);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                    addno.setVisibility(View.VISIBLE);
                }
            }
        });
        lthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_three);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_four);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_five);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_six);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_seven);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }           }
        });
        leight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_eight);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lnine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_nine);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lzero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_zero);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_star);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        lhash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.append(btn_hash);
                cnt=cnt+1;
                if(cnt==0)
                {
                    addno.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    backspace.setVisibility(View.INVISIBLE);
                }
                else
                {
                    addno.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                    backspace.setVisibility(View.VISIBLE);
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txt_no.getText().toString()));
                        startActivity(intent);
            }
        });
        backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                txt_no.setText("");
                backspace.setVisibility(View.INVISIBLE);
                textview.setVisibility(View.INVISIBLE);
                addno.setVisibility(View.INVISIBLE);
                return false;

            }
        });
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_no.clearFocus();
                String no=txt_no.getText().toString();
                txt_no.setText(""+method(no));
                cnt=cnt-1;
                if (cnt==0)
                {
                    backspace.setVisibility(View.INVISIBLE);
                    textview.setVisibility(View.INVISIBLE);
                    addno.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public  String method(String str)
    {
        if(str!=null && str.length()>0)
        {
            str=str.substring(0,str.length()-1);

        }
        return  str;
    }
    private void ShowSportLight1(String Tag) {
        new SpotlightView.Builder(dialer.this)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#E02B57"))//red
                .headingTvSize(28)
                .headingTvText("Enter Pin")
                .subHeadingTvColor(Color.parseColor("#ffffff"))//white
                .subHeadingTvSize(16)
                .subHeadingTvText("Set your 4 digit pin!")
                .maskColor(Color.parseColor("#000000"))//black
                .target(spotlight)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#E02B57"))//red
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .setListener(new SpotlightListener() {
                    @Override
                    public void onUserClicked(String s) {

                    }
                })
                .usageId(Tag + "_one") //UNIQUE ID
                .show();
    }
    private void LoadFavoriteData() {
        try {
            String contactEmail = "";
            hud = KProgressHUD.create(dialer.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Loading Contacts...")
                    .setDimAmount(0.5f)
                    .setSize(180, 150)
                    .setCancellable(false);
            hud.show();

            txt_edit_favorit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            txt_add_favorit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(Intent.ACTION_INSERT);
                        i.setType(ContactsContract.Contacts.CONTENT_TYPE);
                        if (Integer.valueOf(Build.VERSION.SDK) > 14)
                            i.putExtra("finishActivityOnSaveCompleted", true); // Fix for 4.0.3 +
                        startActivityForResult(i, PICK_CONTACT_REQUEST);
                    } catch (NumberFormatException e) {
                    }
                }
            });
            FavoritecontactModels.clear();
            Uri queryUri = ContactsContract.Contacts.CONTENT_URI;
            String[] projection = new String[]{
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.STARRED};
            String selection = ContactsContract.Contacts.STARRED + "=" + "1";
            ContentResolver cr = getContentResolver();
            cursorfavorit = cr.query(queryUri, projection, selection, null, null);
            if (cursorfavorit != null)
                while (cursorfavorit.moveToNext()) {
                    contactID = cursorfavorit.getString(cursorfavorit.getColumnIndex(ContactsContract.Contacts._ID));
                    Name = (cursorfavorit.getString(cursorfavorit.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    try {
                        favoritphones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);
                        while (favoritphones.moveToNext()) {
                            phoneNo = favoritphones.getString(favoritphones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        favoritphones.close();
                        favoritphones = null;

                        favoritemails = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactID,
                                null,
                                null);
                        while (favoritemails.moveToNext()) {
                            contactEmail = favoritemails.getString(favoritemails.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                        }
                        favoritemails.close();
                        favoritemails = null;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));
                        if (inputStream != null) {
                            Picture = BitmapFactory.decodeStream(inputStream);
                        }
                        assert inputStream != null;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    FavoritecontactModels.add(new ContactModel(contactID, Name, phoneNo, contactEmail, Picture, null, null, null, false));
                    Picture = null;
                }

            cursorfavorit.close();
            cursorfavorit = null;

            if (FavoritecontactModels.size() != 0) {
                FavoriteContactListAdapter favoriteContactListAdapter = new FavoriteContactListAdapter(this, FavoritecontactModels);
                list_favorite.setAdapter(favoriteContactListAdapter);
                list_favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            Intent callIntent = new Intent(dialer.this, contacts_detail.class);
                            callIntent.putExtra("name", FavoritecontactModels.get(position).getName());
                            callIntent.putExtra("number", FavoritecontactModels.get(position).getPhoneNumber());
                            callIntent.putExtra("email", FavoritecontactModels.get(position).getContectEmail());
                            startActivity(callIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        try {
//                            Intent callIntent = new Intent(Intent.ACTION_CALL);
//                            callIntent.setData(Uri.parse("tel: " + FavoritecontactModels.get(position).getPhoneNumber()));
//                            startActivity(callIntent);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                });
            } else {
            }
            hud.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String capitalize(String str) {
        if (str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void LoadContactData() {
        ArrayList<String> Tempname = new ArrayList<>();
        String contactEmail = "";
        try {
            Contects.clear();
            hud = KProgressHUD.create(dialer.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Loading Contacts...")
                    .setDimAmount(0.5f)
                    .setSize(180, 150)
                    .setCancellable(true);
            hud.show();
            txt_contact_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(Intent.ACTION_INSERT);
                        i.setType(ContactsContract.Contacts.CONTENT_TYPE);
                        if (Integer.valueOf(Build.VERSION.SDK) > 14)
                            i.putExtra("finishActivityOnSaveCompleted", true); // Fix for 4.0.3 +
                        startActivityForResult(i, PICK_CONTACT_REQUEST);
                    } catch (NumberFormatException e) {
                    }
                }
            });
            ArrayList<ContactModel> arrContacts = new ArrayList<ContactModel>();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            cursorcontacts = getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            cursorcontacts.moveToFirst();
            while (cursorcontacts.isAfterLast() == false) {
                String contactNumber = cursorcontacts.getString(cursorcontacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String contactName = cursorcontacts.getString(cursorcontacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                int phoneContactID = cursorcontacts.getInt(cursorcontacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                ContactModel phoneContactInfo = new ContactModel("" + phoneContactID, capitalize(contactName), contactNumber, contactEmail, null, null, null, null, false);
                if (phoneContactInfo != null) {
                    if (!Tempname.contains(contactName))
                        arrContacts.add(phoneContactInfo);
                    Tempname.add(contactName);
                }
                phoneContactInfo = null;
                cursorcontacts.moveToNext();
            }
            cursorcontacts.close();
            cursorcontacts = null;
            if (arrContacts.size() != 0) {
                Tempname.clear();
                Collections.sort(arrContacts, new Comparator<ContactModel>() {
                    @Override
                    public int compare(ContactModel lhs, ContactModel rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                contactListAdapter = new DialpadContactListAdapter(this, arrContacts);
                GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
                list_contact.setLayoutManager(mLayoutManager);
                list_contact.setItemAnimator(new DefaultItemAnimator());
                list_contact.setAdapter(contactListAdapter);
                ArrayList<AlphabetItem> mAlphabetItems = new ArrayList<>();
                List<String> strAlphabets = new ArrayList<>();
                for (int i = 0; i < arrContacts.size(); i++) {
                    String name = arrContacts.get(i).getName();
                    if (name == null || name.trim().isEmpty())
                        continue;
                    String word = name.substring(0, 1);
                    if (!strAlphabets.contains(word)) {
                        strAlphabets.add(word);
                        mAlphabetItems.add(new AlphabetItem(i, word, false));
                    }
                }

                Contects = arrContacts;
            } else {

            }
            hud.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
