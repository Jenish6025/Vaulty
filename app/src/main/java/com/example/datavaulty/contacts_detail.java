package com.example.datavaulty;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class contacts_detail extends AppCompatActivity {


    TextView txt_contact_details_contact_name, txt_contact_details_contact_numbers, txt_option_add_favourites;
    RelativeLayout btn_contact_details_call, btn_contact_details_message, btn_contact_details_whatsapp, btn_contact_details_email;
    RelativeLayout btn_option_send_message, btn_option_share_contact, btn_option_add_favourites;
    ImageView ImageView;
    boolean isContactFavorit = false;
    ImageView img_back;
    private String name = "";
    private String number = "";
    private String email = "";
    private String TAG = "msg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contacts_detail);

        name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");

        ImageView = (ImageView) findViewById(R.id.img_contact_details_contact);
        btn_contact_details_call = (RelativeLayout) findViewById(R.id.btn_contact_details_call);
        btn_contact_details_message = (RelativeLayout) findViewById(R.id.btn_contact_details_message);
        btn_contact_details_whatsapp = (RelativeLayout) findViewById(R.id.btn_contact_details_whatsapp);
        btn_contact_details_email = (RelativeLayout) findViewById(R.id.btn_contact_details_email);
        btn_option_send_message = (RelativeLayout) findViewById(R.id.btn_option_send_message);
        btn_option_share_contact = (RelativeLayout) findViewById(R.id.btn_option_share_contact);
        btn_option_add_favourites = (RelativeLayout) findViewById(R.id.btn_option_add_favourites);

        img_back = (ImageView) findViewById(R.id.img_back);

        txt_option_add_favourites = (TextView) findViewById(R.id.txt_option_add_favourites);
        txt_contact_details_contact_name = (TextView) findViewById(R.id.txt_contact_details_contact_name);
        txt_contact_details_contact_numbers = (TextView) findViewById(R.id.txt_contact_details_contact_numbers);


        if (name.length() != 0)
            txt_contact_details_contact_name.setText(name);
        if (number.length() != 0)
            txt_contact_details_contact_numbers.setText(number);
        ArrayList<String> FavoritList = getFavoritContact();
        if (FavoritList.size() != 0) {
            if (FavoritList.contains(name)) {
                txt_option_add_favourites.setText(getResources().getString(R.string.removetofav));
                isContactFavorit = true;
            }
        }
        btn_contact_details_call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    if (number.length() != 0) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel: " + number));
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);
                    } else
                        Toast.makeText(contacts_detail.this, "No Number Found!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_contact_details_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (number.length() != 0) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
                    } else
                        Toast.makeText(contacts_detail.this, "No Number Found!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_contact_details_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (number.length() != 0) {
                        if (!number.contains("+91")) {
                            number = "+91" + number;
                        }
                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + number + "?body=" + ""));
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(sendIntent);
                    } else
                        Toast.makeText(contacts_detail.this, "No Number Found!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(contacts_detail.this, "it may be you don't have whats app!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_contact_details_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (email.length() != 0) {
                        Intent intentemail = new Intent(Intent.ACTION_SEND);
                        intentemail.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                        intentemail.putExtra(Intent.EXTRA_SUBJECT, "");
                        intentemail.putExtra(Intent.EXTRA_TEXT, "");
                        intentemail.setType("message/rfc822");
                        startActivity(Intent.createChooser(intentemail, "Choose an Email: "));
                    } else
                        Toast.makeText(contacts_detail.this, "No Email Found!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(contacts_detail.this, "it may be you don't have Email app!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_option_add_favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isContactFavorit) {
                    try {
                        ContentValues values = new ContentValues();
                        String[] fv = new String[]{name};
                        values.put(ContactsContract.Contacts.STARRED, 0);
                        getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, values,
                                ContactsContract.Contacts.DISPLAY_NAME + "= ?", fv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    txt_option_add_favourites.setText(getResources().getString(R.string.addtofav));

                    isContactFavorit = false;
                } else {
                    try {
                        ContentValues values = new ContentValues();
                        String[] fv = new String[]{name};
                        values.put(ContactsContract.Contacts.STARRED, 1);
                        getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, values,
                                ContactsContract.Contacts.DISPLAY_NAME + "= ?", fv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    txt_option_add_favourites.setText(getResources().getString(R.string.removetofav));
                    isContactFavorit = true;
                }
            }
        });
        btn_option_share_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "Name: " + name + " Number: " + number + " Email: " + email;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Contact Via"));
            }
        });
        btn_option_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (number.length() != 0) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
                    } else
                        Toast.makeText(contacts_detail.this, "No Number Found!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<String> getFavoritContact() {
        ArrayList<String> favoritContact = new ArrayList<>();
        Uri queryUri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.STARRED};
        String selection = ContactsContract.Contacts.STARRED + "=" + "1";
        Cursor cursor = managedQuery(queryUri, projection, selection, null, null);
        while (cursor.moveToNext()) {
            String Name = (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            favoritContact.add(Name);
        }
        cursor.close();
        return favoritContact;
    }
    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity();
    }
}
