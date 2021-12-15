package com.project.aquacy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.aquacy.Adapter.ClientListAdapter;
import com.project.aquacy.CommonFile.CallbackInterface;
import com.project.aquacy.CommonFile.CommonFunction;
import com.project.aquacy.CommonFile.StartSession;
import com.project.aquacy.CommonFile.Utility;
import com.project.aquacy.CommonFile.WebUrlClass;
import com.project.aquacy.Database.DatabaseHandlers;
import com.project.aquacy.Model.ClientDetailsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Locale;

public class ClientListActivity extends AppCompatActivity {

    String PlantMasterId = "", LoginId = "", Password = "", EnvMasterId = "",
            UserMasterId = "", UserName = "", MobileNo = "", clientname = "", clientid = "", contactno = "", contactid = "", id1 = "",
            CompanyURL = "";

    DatabaseHandlers db;
    SQLiteDatabase sql;
    CommonFunction cf;
    Context context;
    SharedPreferences userpreferences;
    Utility ut;
    //SQLiteDatabase sql;

    RecyclerView list_clientDetails;
    ImageView img_refresh,img_add;
    ClientListAdapter clientListAdapter;
    ArrayList<ClientDetailsBean> clientDetailsBeanArrayList;
    ArrayList<ClientDetailsBean> filterClientArrayList;
    boolean isEdit = false;
    String custVendorMasterId = "";
    ProgressBar progress;
    EditText edt_search;
    ImageView img_search;
    boolean searchVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);


        Window window = ClientListActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ClientListActivity.this, R.color.colorPrimaryDark));
        }

        InitView();
        if (cf.getGetClientItemcount() > 0) {
            updateListClientData();
        } else {

            if (ut.isNet(ClientListActivity.this)) {
                new StartSession(ClientListActivity.this, new CallbackInterface() {
                    @Override
                    public void callMethod() {
                        new GetClientDataList().execute();
                    }

                    @Override
                    public void callfailMethod(String msg) {

                    }
                });
            }else{
                Toast.makeText(ClientListActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }

        setListner();


    }

    private void setListner() {
        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ut.isNet(ClientListActivity.this)) {
                    progress.setVisibility(View.VISIBLE);
                    new StartSession(ClientListActivity.this, new CallbackInterface() {
                        @Override
                        public void callMethod() {
                            new GetClientDataList().execute();
                        }

                        @Override
                        public void callfailMethod(String msg) {
                            Toast.makeText(ClientListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    });
                }else{
                    Toast.makeText(ClientListActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClientListActivity.this, AddClientMaster.class);
                i.putExtra("Mode", "A");
                startActivityForResult(i, 110);
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable text) {

                String text1 = edt_search.getText().toString().toLowerCase(Locale.getDefault());

                filter(text1);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {


            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchVisible) {
                    edt_search.setVisibility(View.VISIBLE);
                    searchVisible = false;
                }
                else {
                    edt_search.setVisibility(View.GONE);
                    searchVisible = true;
                }
            }
        });
    }

    private void filter(String text1) {
        filterClientArrayList.clear();
        text1 = text1.toLowerCase(Locale.getDefault());
        if(text1.length() == 0){
           filterClientArrayList.addAll(clientDetailsBeanArrayList);
        }else{
            for (ClientDetailsBean clientDetailsBean : clientDetailsBeanArrayList) {
                if (clientDetailsBean.getCustVendorName().toLowerCase(Locale.getDefault()).contains(text1)) {
                    filterClientArrayList.add(clientDetailsBean);
                }
            }
        }

        clientListAdapter = new ClientListAdapter(ClientListActivity.this, filterClientArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list_clientDetails.setLayoutManager(mLayoutManager);
        list_clientDetails.setItemAnimator(new DefaultItemAnimator());
        list_clientDetails.setAdapter(clientListAdapter);


    }

    public void InitView() {
        ut = new Utility();
        userpreferences = getSharedPreferences(WebUrlClass.USERINFO,
                Context.MODE_PRIVATE);
        context = getApplicationContext();
        ut = new Utility();
        cf = new CommonFunction(context);
        String settingKey = ut.getSharedPreference_SettingKey(context);
        String dabasename = ut.getValue(context, WebUrlClass.GET_DATABASE_NAME_KEY, settingKey);
        db = new DatabaseHandlers(context, dabasename);
        CompanyURL = ut.getValue(context, WebUrlClass.GET_COMPANY_URL_KEY, settingKey);
        EnvMasterId = ut.getValue(context, WebUrlClass.GET_EnvMasterID_KEY, settingKey);
        PlantMasterId = ut.getValue(context, WebUrlClass.GET_PlantID_KEY, settingKey);
        LoginId = ut.getValue(context, WebUrlClass.GET_LOGIN_KEY, settingKey);
        Password = ut.getValue(context, WebUrlClass.GET_PSW_KEY, settingKey);
        UserMasterId = ut.getValue(context, WebUrlClass.MyPREFERENCES_USERMASTER_ID_KEY, settingKey);
        UserName = ut.getValue(context, WebUrlClass.MyPREFERENCES_USERNAME_KEY, settingKey);
        MobileNo = ut.getValue(context, WebUrlClass.MyPREFERENCES_MOBILE_KEY, settingKey);
        sql = db.getWritableDatabase();
        list_clientDetails = findViewById(R.id.list_clientDetails);
        img_refresh = findViewById(R.id.img_refresh);
        img_add = findViewById(R.id.img_add);
        progress = findViewById(R.id.progress);
        edt_search = findViewById(R.id.edt_search);
        img_search = findViewById(R.id.img_search);
        clientDetailsBeanArrayList = new ArrayList<>();
        filterClientArrayList = new ArrayList<>();
    }


    private class GetClientDataList extends AsyncTask<String, Void, String> {

        Object res;
        String response;
        JSONArray jResults;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = CompanyURL + WebUrlClass.api_GetData + "?Id=";
            try {

                res = ut.OpenConnection(url, ClientListActivity.this);
                if (res != null) {
                    response = res.toString();
                    response = res.toString().replaceAll("\\\\\\\\\\\"", "");
                    response = response.replaceAll("\\\\", "");
                    response = response.replaceAll("u0026", "&");
                    response = response.replaceAll("%", "per.");
                } else {
                    response = "error";
                }
                // response = response.substring(1, response.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (!response.equals("error")) {

                sql.delete(db.TABLE_CLIENT_DATALIST, null,
                        null);
                ContentValues values = new ContentValues();
                try {
                    jResults = new JSONArray(response);

                    String msg = "";

                    Cursor c = sql.rawQuery("SELECT * FROM " + db.TABLE_CLIENT_DATALIST, null);
                    int count = c.getCount();
                    String columnName, columnValue;
                    for (int i = 0; i < jResults.length(); i++) {
                        JSONObject jorder = jResults.getJSONObject(i);
                        for (int j = 0; j < c.getColumnCount(); j++) {
                            columnName = c.getColumnName(j);
                            columnValue = jorder.getString(columnName);
                            values.put(columnName, columnValue);
                        }

                        long a = sql.insert(db.TABLE_CLIENT_DATALIST, null, values);
                        Log.i("Client List", "" + a);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (cf.getGetClientItemcount() > 0) {
                    updateListClientData();
                }


            }
        }
    }

    private void updateListClientData() {
        progress.setVisibility(View.VISIBLE);
        clientDetailsBeanArrayList.clear();
        Cursor cur = sql.rawQuery("SELECT * FROM " + db.TABLE_CLIENT_DATALIST, null);
        Cursor cur1 = sql.rawQuery("SELECT * FROM " + db.TABLE_CLIENT_DATALIST, null);
        int count = cur.getCount();
        Log.i("cnt:", String.valueOf(cur.getCount()));

        int cnt = cur.getCount();

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                ClientDetailsBean clientDetailsBean = new ClientDetailsBean();
                String s = cur.getString(cur.getColumnIndex("CustVendorMasterId"));
                clientDetailsBean.setCustVendorMasterId(s);
                clientDetailsBean.setCustVendorName(cur.getString(cur.getColumnIndex("CustVendorName")));
                clientDetailsBean.setAddress(cur.getString(cur.getColumnIndex("Address")));
                clientDetailsBean.setMobile(cur.getString(cur.getColumnIndex("Mobile")));
                clientDetailsBean.setEmail(cur.getString(cur.getColumnIndex("Email")));
                clientDetailsBean.setMeterType(cur.getString(cur.getColumnIndex("MeterType")));
                clientDetailsBean.setConnNo(cur.getString(cur.getColumnIndex("ConnNo")));
                clientDetailsBean.setMeterNo(cur.getString(cur.getColumnIndex("MeterNo")));
                clientDetailsBean.setConnectionType(cur.getString(cur.getColumnIndex("ConnectionType")));
                clientDetailsBean.setIsWhatsapp(cur.getString(cur.getColumnIndex("IsWhatsAppNo")));

                clientDetailsBeanArrayList.add(clientDetailsBean);


            } while (cur.moveToNext());

            progress.setVisibility(View.GONE);
            clientListAdapter = new ClientListAdapter(ClientListActivity.this, clientDetailsBeanArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            list_clientDetails.setLayoutManager(mLayoutManager);
            list_clientDetails.setItemAnimator(new DefaultItemAnimator());
            list_clientDetails.setAdapter(clientListAdapter);

            /////    activityListadapterNew.notifyDataSetChanged();


        }

    }

    public void EditRow(int adapterPosition, boolean b) {
        isEdit = b;
        custVendorMasterId = clientDetailsBeanArrayList.get(adapterPosition).getCustVendorMasterId();
        progress.setVisibility(View.VISIBLE);
        if(isEdit) {
            String list = new Gson().toJson(clientDetailsBeanArrayList.get(adapterPosition));
            String datasheetList = new Gson().toJson(new ClientDetailsBean(clientDetailsBeanArrayList));

            Intent i = new Intent(ClientListActivity.this, AddClientMaster.class);
            i.putExtra("Mode", "E");
            i.putExtra("ClientList", datasheetList);
            i.putExtra("CustvendorMasterId", custVendorMasterId);
            i.putExtra("Position", adapterPosition);
            startActivityForResult(i, 110);

        }else{

            String list = new Gson().toJson(clientDetailsBeanArrayList.get(adapterPosition));
            String datasheetList = new Gson().toJson(new ClientDetailsBean(clientDetailsBeanArrayList));

            Intent i = new Intent(ClientListActivity.this, AddClientMaster.class);
            i.putExtra("Mode", "D");
            i.putExtra("ClientList", datasheetList);
            i.putExtra("CustvendorMasterId", custVendorMasterId);
            i.putExtra("Position", adapterPosition);
            startActivityForResult(i, 110);

        }
        progress.setVisibility(View.GONE);


    }

    @Override
    protected void onResume() {
        super.onResume();


        updateListClientData();


    }
}