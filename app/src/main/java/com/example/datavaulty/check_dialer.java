package com.example.datavaulty;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;

public class check_dialer extends AppCompatActivity {

    TextView one,two,three,four,five,six,seven,eight,nine,zero,star,hash,addno;
    TextView txt_no;
    ImageView call,star1,recent,contact,dialpad,backspace,voicemail;
    String btn_one,btn_two,btn_three,btn_four,btn_five,btn_six,btn_seven,btn_eight,btn_nine,btn_zero,btn_star,btn_hash;
    LinearLayout lone,ltwo,lthree,lfour,lfive,lsix,lseven,leight,lnine,lzero,lstar,lhash,textview,spotlight;
    int cnt=0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String data,confirm_txt,newdialerpin;
    int count=0;
    LottieAnimationView lottieAnimationView;
    Dialog dialog1,dialog2,dialog3,dialog4,dialog5;
    TextView dialog_one,dialog_two,dialog_three,dialog_four;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor1;


    AsyncHttpClient asyncHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_dialer);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


         sharedPreferences2= getSharedPreferences("password", Context.MODE_PRIVATE);
         final SharedPreferences.Editor editor1 = sharedPreferences2.edit();

        sharedPreferences1 = getSharedPreferences("Deviceid", MODE_PRIVATE);
        final String  devid = sharedPreferences1.getString("Deviceid", null);


        sharedPreferences=getSharedPreferences("Pin",MODE_PRIVATE);
//        data=sharedPreferences.getString("Pin",null);



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
//        l=ottieAnimationView=(LottieAnimationView)findViewById(R.id.animation1);

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

        Intent intent=getIntent();
        final String pin=intent.getStringExtra("pin");

        spotlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm_txt=txt_no.getText().toString();

                  //String data=sharedPreferences.getString("PinLock",null);


                Log.e("check", "onClick: " + confirm_txt + data );
                if (confirm_txt.equals(pin))
                {
                    dialog1=new Dialog(check_dialer.this);
                    dialog1.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.dialog_success);
                    dialog1.setCancelable(false);

                    Button btn4=dialog1.findViewById(R.id.btn4);     //findviewbyid with dialog btn and textview

                    dialog_one=dialog1.findViewById(R.id.dialog_one);
                    dialog_two=dialog1.findViewById(R.id.dialog_two);
                    dialog_three=dialog1.findViewById(R.id.dialog_three);
                    dialog_four=dialog1.findViewById(R.id.dialog_four);


                    dialog_one.setText(pin.substring(0,1));
                    dialog_two.setText(pin.substring(1,2));
                    dialog_three.setText(pin.substring(2,3));
                    dialog_four.setText(pin.substring(3,4));
                    if (asyncHttpClient!=null)
                    {
                        asyncHttpClient.cancelRequests(check_dialer.this,true);
                    }
                    asyncHttpClient=new AsyncHttpClient();
                    RequestParams requestParams=new RequestParams();
                    requestParams.put("fourdigitpin",pin);
                    requestParams.put("deviceid",devid);
                    asyncHttpClient.post("http://skysparrow.in/project_api/vault/api_updatepin.php",requestParams,new changepinhandler());



                    dialog1.show();

                    sharedPreferences3=getSharedPreferences("Deviceid",MODE_PRIVATE);
                    editor3=sharedPreferences.edit();
                    final String deviceid=sharedPreferences3.getString("Deviceid",null);

                    count=0;
                    btn4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            editor1.putString("password", confirm_txt);
                            editor1.commit();


                            txt_no.setText("");
                            addno.setVisibility(View.INVISIBLE);
                            dialog1.cancel();
                            startActivity(new Intent(check_dialer.this,homescreen.class));
                            finish();

                        }
                    });
                }
                else {

                    count++;

                    if (count==1) {
//                        new FancyGifDialog.Builder(check_dialer.this)
//                                .setTitle(count + "/3")
//                                .setMessage("Wrong Pin..!")
//                                .setPositiveBtnBackground("#E02B57")
//                                .setPositiveBtnText("Try Again!")
//                                .setNegativeBtnBackground("#E02B57")
//                                .setGifResource(R.drawable.gif1)   //Pass your Gif here
//                                .isCancellable(false)
//                                .OnPositiveClicked(new FancyGifDialogListener() {
//                                    @Override
//                                    public void OnClick() {
//                                       txt_no.setText("");
//                                       addno.setVisibility(View.INVISIBLE);
//                                    }
//                                })
//                                .build();
                        dialog2=new Dialog(check_dialer.this);
                        dialog2.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.dialog_wrongpin);
                        dialog2.setCancelable(false);
                        Button btn1=dialog2.findViewById(R.id.btn1);
                        dialog2.show();

                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getApplicationContext(),"Move on another Screen",Toast.LENGTH_LONG).show();
                                txt_no.setText("");
                                addno.setVisibility(View.INVISIBLE);
                                dialog2.cancel();
                            }
                        });

                    }else if (count==2)
                    {
//                        new FancyGifDialog.Builder(check_dialer.this)
//                                .setTitle(count + "/3")
//                                .setMessage("We guess that you forgot your 4 digit pin.\n")
//                                .setPositiveBtnBackground("#E02B57")
//                                .setPositiveBtnText("Re-Start!")
//                                .setNegativeBtnBackground("#E02B57")
//                                .setGifResource(R.drawable.gif1)   //Pass your Gif here
//                                .isCancellable(false)
//                                .OnPositiveClicked(new FancyGifDialogListener() {
//                                    @Override
//                                    public void OnClick() {
//
//                                        startActivity(new Intent(check_dialer.this,dialer.class));
//                                        finish();
//                                    }
//                                })
//                                .build();
                        dialog3=new Dialog(check_dialer.this);
                        dialog3.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                        dialog3.setContentView(R.layout.dialog_wrongpin2);
                        dialog3.setCancelable(false);
                        Button btn2=dialog3.findViewById(R.id.btn2);
                        dialog3.show();

                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getApplicationContext(),"Move on another Screen",Toast.LENGTH_LONG).show();
                                txt_no.setText("");
                                addno.setVisibility(View.INVISIBLE);
                                dialog3.cancel();
                            }
                        });

                    }
                   else if(count==3) {
                        dialog4=new Dialog(check_dialer.this);
                        dialog4.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
                        dialog4.setContentView(R.layout.dialog_wrongpin3);
                        dialog4.setCancelable(false);
                        Button btn3=dialog4.findViewById(R.id.btn3);
                        dialog4.show();

                        btn3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getApplicationContext(),"Move on another Screen",Toast.LENGTH_LONG).show();
                                dialog4.cancel();
                                startActivity(new Intent(check_dialer.this,dialer.class));
                                finish();
                            }
                        });

                    }
                    else
                    {
                        count=0;
                    }
                }
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
        new SpotlightView.Builder(check_dialer.this)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#E02B57"))//red
                .headingTvSize(28)
                .headingTvText("Enter Pin")
                .subHeadingTvColor(Color.parseColor("#ffffff"))//white
                .subHeadingTvSize(16)
                .subHeadingTvText("Confirm your 4 digit pin!")
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

    public  class Pinhandler extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);

            Log.e("----->data",""+data);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }

    public  class changepinhandler extends AsyncHttpResponseHandler
    {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String data=new String(responseBody);

            Log.e("----->new pin",""+data);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    }


}
