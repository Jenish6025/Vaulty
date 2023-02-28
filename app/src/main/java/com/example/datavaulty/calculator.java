package com.example.datavaulty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

import java.util.ArrayList;

public class calculator extends AppCompatActivity {

    TextView cone,ctwo,cthree,cfour,cfive,csix,cseven,ceight,cnine,czero,cdot,cresult,clear,tadd,tsub,tdiv,tmul,equal,percentage,minusValue;
    Double var1;
    Double var2;
    CharSequence var3;
    boolean add,sub,mul,div;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences10;
    SharedPreferences.Editor  edit;

    LinearLayout spotlight;
    String FirstPass = "";
    private String ans = "";
    ArrayList<Integer> a = new ArrayList<Integer>();
    String PackageName = "";
    private Boolean exit = false;
    RelativeLayout view_hidden_click;
    private int attempt = 0;

    private String packageName;
    private Vibrator mVibrator;

    private CancellationSignal mCancellationSignal;
    private FingerprintManagerCompat mFingerprintManager = FingerprintManagerCompat.from(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cone=(TextView) findViewById(R.id.cone);
        ctwo=(TextView)findViewById(R.id.ctwo);
        cthree=(TextView)findViewById(R.id.cthree);
        cfour=(TextView)findViewById(R.id.cfour);
        cfive=(TextView)findViewById(R.id.cfive);
        csix=(TextView)findViewById(R.id.csix);
        cseven=(TextView)findViewById(R.id.cseven);
        ceight=(TextView)findViewById(R.id.ceight);
        cnine=(TextView)findViewById(R.id.cnine);
        czero=(TextView)findViewById(R.id.czero);
        cdot=(TextView)findViewById(R.id.cdot);
        cresult=(TextView)findViewById(R.id.cresult);
        clear=(TextView)findViewById(R.id.cac);
        tadd=(TextView)findViewById(R.id.add);
        tsub=(TextView)findViewById(R.id.sub);
        tmul=(TextView)findViewById(R.id.mul);
        tdiv=(TextView)findViewById(R.id.div);
        percentage=(TextView)findViewById(R.id.percentage);
        equal=(TextView)findViewById(R.id.equal);
        minusValue=(TextView)findViewById(R.id.minusValue);
        cresult=(TextView)findViewById(R.id.cresult);
        spotlight=findViewById(R.id.spot);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ShowSportLight1("cnt+1");
            }
        }, 2400);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans = "";
                cresult.setText("0");
                clear.setText("AC");
                a.clear();
            }
        });

        cone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("1");            }
        });
        ctwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("2");            }
        });
        cthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("3");            }
        });
        cfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("4");            }
        });
        cfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("5");            }
        });
        csix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("6");            }
        });
        cseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("7");            }
        });
        ceight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("8");            }
        });
        cnine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("9");            }
        });

        czero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumber("0");            }
        });
        cdot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!cresult.getText().toString().isEmpty()) {
                    String s = cresult.getText().toString();
                    char ch = s.charAt(s.length() - 1);
                    if (ch != '+' && ch != '-' && ch != '%' && ch != '*' && ch != '/' && ch != '.') {
                        ans += ".";
                        cresult.setText(cresult.getText() + ".");
                    }
                }
            }
        });


        sharedPreferences10=getSharedPreferences("check_dialer",MODE_PRIVATE);
        edit=sharedPreferences10.edit();

        sharedPreferences=getSharedPreferences("password",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        final String data=sharedPreferences.getString("password","no");


        spotlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=cresult.getText().toString();

                if (data.equals(result))
                {
                    edit.putString("check_dialer","calcy");
                    edit.commit();
                    startActivity(new Intent(calculator.this,homescreen.class));
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not Match",Toast.LENGTH_LONG).show();
                }
            }
        });



        minusValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    try {
                        String s = cresult.getText().toString();
                        cresult.setText("");
                        double res = Double.parseDouble(s + "");
                        res *= -1;
                        ans = String.valueOf(res);
                        cresult.setText(/*res + */"");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (!cresult.getText().toString().isEmpty()) {
                        String s = cresult.getText().toString();
                        cresult.setText("");
                        char ch = s.charAt(s.length() - 1);
                        if (ch != '+' && ch != '-' && ch != '%' && ch != '*' && ch != '/' && ch != '.') {
                            a.add(s.length() - 1);
                            ans += "+";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        tsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!cresult.getText().toString().isEmpty()) {
                        String s = cresult.getText().toString();
                        cresult.setText("");
                        char ch = s.charAt(s.length() - 1);
                        if (ch != '+' && ch != '-' && ch != '%' && ch != '*' && ch != '/' && ch != '.') {
                            a.add(s.length() - 1);
                            ans += "-";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        tmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!cresult.getText().toString().isEmpty()) {
                        String s = cresult.getText().toString();
                        cresult.setText("");
                        char ch = s.charAt(s.length() - 1);
                        if (ch != '+' && ch != '-' && ch != '%' && ch != '*' && ch != '/' && ch != '.') {
                            a.add(s.length() - 1);
                            ans += "*";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tdiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!cresult.getText().toString().isEmpty()) {
                        String s = cresult.getText().toString();
                        cresult.setText("");
                        char ch = s.charAt(s.length() - 1);
                        if (ch != '+' && ch != '-' && ch != '%' && ch != '*' && ch != '/' && ch != '.') {
                            a.add(s.length() - 1);
                            ans += "/";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!cresult.getText().toString().isEmpty()) {
                        String s = cresult.getText().toString();
                        cresult.setText("");
                        char ch = s.charAt(s.length() - 1);
                        if (ch != '+' && ch != '-' && ch != '%' && ch != '*' && ch != '/' && ch != '.') {
                            a.add(s.length() - 1);
                            ans += "%";
                            cresult.setText(/*textView.getText() +*/ "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cresult.getText().toString().isEmpty()) {
                    try {
                        String s = ans;
                        char ch = s.charAt(s.length() - 1);
                        if (ch == '+' || ch == '-' || ch == '%' || ch == '*' || ch == '/' || ch == '.') {
                        } else {
                            try {
                                a.add(s.length() - 1);
                                s += "=";
                                double res = Double.parseDouble(s.substring(0, a.get(0) + 1));
                                for (int i = 0; i < a.size() - 1; i++) {
                                    try {
                                        double answ = Double.parseDouble(s.substring(a.get(i) + 2, a.get(i + 1) + 1));
                                        if (s.charAt(a.get(i) + 1) == '+') {
                                            res += answ;
                                        } else if (s.charAt(a.get(i) + 1) == '-') {
                                            res -= answ;
                                        } else if (s.charAt(a.get(i) + 1) == '/') {
                                            res /= answ;
                                        } else if (s.charAt(a.get(i) + 1) == '*') {
                                            res *= answ;
                                        } else if (s.charAt(a.get(i) + 1) == '%') {
                                            res = (res) * (answ) / 10000;
                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    } catch (Exception e1) {
                                        FirstPass = "";
                                        Reset();
                                    }
                                }
                                String answer = String.valueOf(res);
                                a.clear();
                                cresult.setText(answer);
                                ans = answer;
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void ShowSportLight1(String Tag) {
        new SpotlightView.Builder(calculator.this)
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


    private void EnterNumber(String Number) {
        if (cresult.getText().toString().equals("0"))
            cresult.setText("");
        clear.setText("C");
        ans += Number;
        String s = cresult.getText() + Number;
        s = s.replace("+", "");
        s = s.replace("-", "");
        s = s.replace("*", "");
        s = s.replace("/", "");
        cresult.setText(s);
    }
    private void Reset() {
        cresult.setText("");
//        FirstPass = "";
        ans = "";
    }

}
