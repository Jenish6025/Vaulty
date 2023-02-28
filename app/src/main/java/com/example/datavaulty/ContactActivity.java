package com.example.datavaulty;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private LongOperation runningTask;
    ArrayList<String> Tempname = new ArrayList<>();
    boolean allSelect = false;
    RelativeLayout btn_contact_details_call;
    ImageView img_back;
    CheckBox chk_select_all;
    AVLoadingIndicatorView avi;
    ArrayList<ContactModel> SelectedContact = new ArrayList<>();
    ArrayList<String> selectedContact=new ArrayList<>();
    ArrayList<cust_help> ar;

    private ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView=findViewById(R.id.list);
        btn_contact_details_call=(RelativeLayout)findViewById(R.id.btn_contact_details_call);
        img_back=(ImageView)findViewById(R.id.img_back);
        chk_select_all=(CheckBox)findViewById(R.id.chk_select_all);
        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        ar=new ArrayList<>();
        avi.show();

        runningTask = new LongOperation();
        runningTask.execute();

        chk_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_select_all.isChecked()) {
                    allSelect = true;
                    runningTask = new LongOperation();
                    runningTask.execute();
                } else {
                    allSelect = false;
                    runningTask = new LongOperation();
                    runningTask.execute();
                }
            }
        });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public class LongOperation extends AsyncTask<Void, Void, String> {
        Bitmap Picture = null;
        ArrayList<ContactModel> arrContacts = new ArrayList<ContactModel>();
        ContactModel phoneContactInfo = null;
        private Cursor cur;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneContactInfo = new ContactModel("" + phoneContactID, contactName, contactNumber, "", null, null, null, null, allSelect);
                if (phoneContactInfo != null) {
                    if (!Tempname.contains(contactName))
                        arrContacts.add(phoneContactInfo);
                    Tempname.add(contactName);
                }
                phoneContactInfo = null;
                cursor.moveToNext();
            }
            cursor.close();
            cursor = null;
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Tempname.clear();
            if (arrContacts.size() != 0) {
                contactListAdapter = new ContactListAdapter(ContactActivity.this, arrContacts);
                GridLayoutManager mLayoutManager = new GridLayoutManager(ContactActivity.this, 1);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(contactListAdapter);
                avi.hide();
                if (cur != null) {
                    cur.close();
                }
            }
        }
    }
    public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context mContext;
        ArrayList<Object> contactModels = new ArrayList<>();
        String title = "";
        ArrayList<ContactModel> SelectedContact = new ArrayList<>();

        private static final int RECIPE = 0;

        public ContactListAdapter(Context context, ArrayList<ContactModel> Models) {
            this.mContext = context;
            this.contactModels.addAll(Models);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (viewType == RECIPE) {
                View contactView = inflater.inflate(R.layout.contact_item, parent, false);
                ViewHolder viewHolder = new ViewHolder(contactView);
                return viewHolder;
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            int itemType = getItemViewType(position);
            if (itemType == RECIPE) {
                ViewHolder viewHolder = (ViewHolder) holder;
                final ContactModel PrivateContactModel = (ContactModel) contactModels.get(position);

                try {
                    if (PrivateContactModel.isSelected()) {
                        viewHolder.chk_select_contact.setChecked(PrivateContactModel.isSelected());
                        SelectedContact.add(new ContactModel(PrivateContactModel.getContactID(),
                                PrivateContactModel.getName()
                                , PrivateContactModel.getPhoneNumber()
                                , ""
                                , null
                                , ""
                                , ""
                                , null
                                , false
                        ));
                    } else {
                        viewHolder.chk_select_contact.setChecked(PrivateContactModel.isSelected());
                        for (int i = 0; i < SelectedContact.size(); i++) {
                            String UserName = SelectedContact.get(i).getName();
                            if (TextUtils.equals(UserName, PrivateContactModel.getName())) {
                                SelectedContact.remove(i);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ((ViewHolder)holder).chk_select_contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(((ViewHolder) holder).chk_select_contact.isChecked())
                        {
                            //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
                            selectedContact.add(PrivateContactModel.PhoneNumber);
                            selectedContact.add(PrivateContactModel.Name);

                            ar.add(new cust_help(PrivateContactModel.PhoneNumber,PrivateContactModel.Name));

                            Log.e("--------------->add",""+PrivateContactModel.PhoneNumber);
                            Log.e("--------------->add",""+PrivateContactModel.Name);

                            Log.e("--------------->ar add",""+ar.get(0).Phone_no);
                            Log.e("--------------->ar add",""+ar.get(0).Name);


                        }
                        else {
                            //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();

                            selectedContact.remove(PrivateContactModel.PhoneNumber);
                            selectedContact.remove(PrivateContactModel.Name);
                            ar.remove(new cust_help(PrivateContactModel.PhoneNumber,PrivateContactModel.Name));
                            Log.e("--------------->remove",""+PrivateContactModel.PhoneNumber);
                            Log.e("--------------->remove",""+PrivateContactModel.Name);
                            Log.e("--------------->ar rem",""+ar.get(0).Phone_no);
                            Log.e("--------------->ar rem",""+ar.get(0).Name);
                        }


                    }
                });


                if (!TextUtils.equals("" + PrivateContactModel.getName().charAt(0), title)) {
                    title = "" + PrivateContactModel.getName().charAt(0);
                    viewHolder.txt_title_text.setVisibility(View.VISIBLE);
                    viewHolder.txt_title_text.setText("" + PrivateContactModel.getName().charAt(0));
                }
                viewHolder.Name.setText(PrivateContactModel.getName());
                viewHolder.Number.setText(PrivateContactModel.getPhoneNumber());
                if (PrivateContactModel.getPicture() != null)
                    viewHolder.Icon.setImageBitmap(PrivateContactModel.getPicture());

                viewHolder.btn_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (PrivateContactModel.getPhoneNumber().length() != 0) {
                                //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", PrivateContactModel.getPhoneNumber(), null)));

                                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+PrivateContactModel.getPhoneNumber()));
                                mContext.startActivity(intent);
                            } else
                                Toast.makeText(mContext, "No Number Found!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                viewHolder.btn_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (PrivateContactModel.getPhoneNumber().length() != 0) {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", PrivateContactModel.getPhoneNumber(), null)));
                            } else
                                Toast.makeText(mContext, "No Number Found!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return contactModels.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            Object item = contactModels.get(position);
            if (item instanceof ContactModel) {
                return RECIPE;
            } else {
                return -1;
            }
        }


        public ArrayList<ContactModel> getSelectedContact() {
            return SelectedContact;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView Name, txt_title_text;
            TextView Number;
            ImageView Icon;
            CheckBox chk_select_contact;
            RelativeLayout btn_call,btn_message;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                txt_title_text = (TextView) itemView.findViewById(R.id.txt_title_text);
                Name = (TextView) itemView.findViewById(R.id.txt_con_name);
                Number = (TextView) itemView.findViewById(R.id.txt_con_number);
                Icon = (ImageView) itemView.findViewById(R.id.icon);
                chk_select_contact = (CheckBox) itemView.findViewById(R.id.chk_select_contact);
                btn_call=(RelativeLayout)itemView.findViewById(R.id.btn_call);
                btn_message=(RelativeLayout)itemView.findViewById(R.id.btn_message);

            }
        }
    }

}
