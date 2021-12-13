package com.project.aquacy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddClientMaster extends AppCompatActivity {
    String PlantMasterId = "", LoginId = "", Password = "", EnvMasterId = "",
            UserMasterId = "", UserName = "", MobileNo = "", clientname = "", clientid = "", contactno = "", contactid = "", id1 = "",
            CompanyURL = "";

    DatabaseHandlers db;
    SQLiteDatabase sql;
    CommonFunction cf;
    Context context;
    SharedPreferences userpreferences;

    TextInputEditText edt_fname, edt_lname, edt_addr, edt_mobileNo, emailID, edt_connectionNo, edt_meterNo;
    Toolbar toolbar;
    String whatsappNo = "";
    Button btn_AMR, btn_noAMR, btn_resident, btn_commercial;
    int clickedConnType = -1;
    ImageView img_save, img_delete;
    int clickedMType = -1;
    Utility ut;
    String custVendorMasterId = "", mode = "";
    String connType = "";
    String mType = "";
    int pos;
    ClientListAdapter clientListAdapter;
    ArrayList<ClientDetailsBean> clientDetailsBeanArrayList;
    ProgressBar progress;
    CheckBox checkIsWhatsapp;
    String entityName = "", addr = "", mob = "", email = "", connNo = "", meterNo = "", fName = "", lName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client_master);

        Window window = AddClientMaster.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(AddClientMaster.this, R.color.colorPrimaryDark));
        }
        InitView();
        SetListner();

    }

    private void InitView() {
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        // toolbar.setLogo(R.mipmap.ic_toolbar_logo_vwb);
//        toolbar.setTitle("Client Master");

        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_addr = findViewById(R.id.edt_addr);
        edt_mobileNo = findViewById(R.id.edt_mobileNo);
        emailID = findViewById(R.id.emailID);
        edt_meterNo = findViewById(R.id.edt_meterNo);
        btn_noAMR = findViewById(R.id.btn_noAMR);
        btn_AMR = findViewById(R.id.btn_AMR);
        btn_resident = findViewById(R.id.btn_resident);
        btn_commercial = findViewById(R.id.btn_commercial);
        edt_connectionNo = findViewById(R.id.edt_connectionNo);
        img_save = findViewById(R.id.img_save);
        img_delete = findViewById(R.id.img_delete);
        progress = findViewById(R.id.progress);
        checkIsWhatsapp = findViewById(R.id.checkIsWhatsapp);

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
        sql = db.getWritableDatabase();
        clientDetailsBeanArrayList = new ArrayList<>();
        clientListAdapter = new ClientListAdapter();
        // ScratchWorkspaceName = userpreferences.getString("ScratchWorkspaceName", null);
        //  ScratchWorkspaceId = userpreferences.getString("ScratchWorkspaceId", null);

        if (getIntent() != null) {
            mode = getIntent().getStringExtra("Mode");
            clientDetailsBeanArrayList = new ArrayList<>();
            if (!mode.equals("A")) {
                custVendorMasterId = getIntent().getStringExtra("CustvendorMasterId");

                pos = getIntent().getIntExtra("Position", -1);
                clientDetailsBeanArrayList = new Gson().fromJson(getIntent().getStringExtra("ClientList"), ClientDetailsBean.class)
                        .getClientDetailsBeanArrayList();

                if (mode.equals("D")) {
                    img_save.setImageDrawable(getResources().getDrawable(R.drawable.close));
                } else {
                    img_save.setImageDrawable(getResources().getDrawable(R.drawable.basic_tick));
                }

                // .putExtra("Mode","E").putExtra("ClientList",list).putExtra("CustvendorMasterId",custVendorMasterId));
                if (clientDetailsBeanArrayList.get(pos).getCustVendorName().contains(" ")) {
                    String[] split = clientDetailsBeanArrayList.get(pos).getCustVendorName().split(" ");
                    edt_fname.setText(split[0]);
                    if (split.length == 2) {
                        edt_lname.setText(split[1]);
                    }


                } else {
                    edt_fname.setText(clientDetailsBeanArrayList.get(pos).getCustVendorName());
                }
                edt_addr.setText(clientDetailsBeanArrayList.get(pos).getAddress());
                edt_mobileNo.setText(clientDetailsBeanArrayList.get(pos).getMobile());
                edt_connectionNo.setText(clientDetailsBeanArrayList.get(pos).getConnNo());
                edt_meterNo.setText(clientDetailsBeanArrayList.get(pos).getMeterNo());
                emailID.setText(clientDetailsBeanArrayList.get(pos).getEmail());
                //if 1 then AMR and 2 Non AMR
                if (clientDetailsBeanArrayList.get(pos).getMeterType().equalsIgnoreCase("AMR")) {
                    clickedMType = 1;
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                } else if (clientDetailsBeanArrayList.get(pos).getMeterType().equalsIgnoreCase("NonAMR")) {
                    clickedMType = 2;
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                } else {
                    clickedMType = -1;
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                }
                //if 1 then residential and 2 commercial
                if (clientDetailsBeanArrayList.get(pos).getConnectionType().equalsIgnoreCase("Residential")) {
                    clickedConnType = 1;
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                } else if (clientDetailsBeanArrayList.get(pos).getConnectionType().equalsIgnoreCase("Commercial")) {
                    clickedConnType = 2;
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                } else {
                    clickedConnType = -1;
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                }


                if (clientDetailsBeanArrayList.get(pos).getIsWhatsapp() != null) {
                    if (clientDetailsBeanArrayList.get(pos).getIsWhatsapp().equals("Y")) {
                        checkIsWhatsapp.setChecked(true);
                    } else {
                        checkIsWhatsapp.setChecked(false);
                    }
                }else{
                    checkIsWhatsapp.setChecked(false);
                }
            }
        }

    }

    private void SetListner() {
        btn_commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedConnType = 2;
                //if 1 then residential and 2 commercial
                if (clickedConnType == -1 || clickedConnType == 2) {
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedConnType = 2;
                } else if (clickedConnType == 1) {
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    clickedConnType = 1;
                }

            }
        });
        btn_resident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedConnType = 1;
                //if 1 then residential and 2 commercial
                if (clickedConnType == -1 || clickedConnType == 2) {
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedConnType = 2;
                } else if (clickedConnType == 1) {
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    clickedConnType = 1;
                }

            }
        });

        btn_AMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedMType = 1;
                //if 1 then AMR and 2 Non AMR
                if (clickedMType == -1 || clickedMType == 1) {
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedMType = 1;
                } else if (clickedMType == 2) {
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    clickedMType = 2;
                }
            }
        });

        btn_noAMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedMType = 2;
                //if 1 then AMR and 2 Non AMR
                if (clickedMType == -1 || clickedMType == 1) {
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedMType = 1;
                } else if (clickedMType == 2) {
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    clickedMType = 2;
                }
            }
        });

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickedConnType == 1) {
                    connType = "Residential";
                } else if (clickedConnType == 2) {
                    connType = "commercial";
                } else {
                    connType = "";
                }


                if (clickedMType == 1) {
                    mType = "AMR";
                } else if (clickedMType == 2) {
                    mType = "NonAMR";
                } else {
                    mType = "";
                }


                entityName = (edt_fname.getText().toString().trim() + " " + edt_lname.getText().toString().trim()).trim();
                fName = edt_fname.getText().toString().trim();
                lName = edt_lname.getText().toString().trim();
                addr = edt_addr.getText().toString().trim();
                mob = edt_mobileNo.getText().toString();
                email = emailID.getText().toString().trim();
                meterNo = edt_meterNo.getText().toString().trim();
                connNo = edt_connectionNo.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String emailPattern1 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+[a-z]";

                if (fName == null || fName.isEmpty()) {
                    Toast.makeText(AddClientMaster.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                } else if (lName == null || lName.isEmpty()) {
                    Toast.makeText(AddClientMaster.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                } else if (addr == null || addr.isEmpty()) {
                    Toast.makeText(AddClientMaster.this, "Please enter address", Toast.LENGTH_SHORT).show();
                } else if (mob.length() != 10 || mob.isEmpty()) {
                    Toast.makeText(AddClientMaster.this, "Enter valid mobile no", Toast.LENGTH_SHORT).show();
                } else if (meterNo == null || meterNo.isEmpty()) {
                    Toast.makeText(AddClientMaster.this, "Enter valid meter no", Toast.LENGTH_SHORT).show();
                } else {
                    if (!(email == null || email.equals(""))) {
                        if (!(email.matches(emailPattern) || email.matches(emailPattern1))) {
                            Toast.makeText(AddClientMaster.this, "Enter valid email id", Toast.LENGTH_SHORT).show();
                        } else {
                            PostSaveJsonObject();
                        }
                    } else {
                        PostSaveJsonObject();
                    }


                }


              /*  {
                    "CustId": "",
                        "entityname": "Sayali ",
                        "entityadd": "Pune",
                        "mob": "4655698653",
                        "email": "sayali@gmail.com",
                        "metertype": "AMR",
                        "ConnectionType": "Residential",
                        "connectionno": "123",
                        "meterno": "31",
                        "Mode": "A"
                } */


            }
        });


        edt_mobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 10) {
                    whatsappNo = s.toString();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("mob", whatsappNo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String finalJsonObj = jsonObject.toString();
                    new CheckWhatsappValid().execute(finalJsonObj);
                }

            }
        });


    }

    public class CheckWhatsappValid extends AsyncTask<String, Void, String> {
        String response = "";
        Object res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = CompanyURL + WebUrlClass.CheckWhatsAppNo;
            String obj = params[0];

            res = ut.OpenPostConnection(url, obj, AddClientMaster.this);
            try {
                if (res != null) {
                    response = res.toString();
                } else {
                    response = "error";

                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (response.contains("Success")) {
                checkIsWhatsapp.setChecked(true);
            } else {
                checkIsWhatsapp.setChecked(false);
            }
        }
    }

    public void PostSaveJsonObject() {

        JSONObject jsonObject = new JSONObject();
        try {
            if (mode != null || mode.equals("E") || mode.equals("D")) {
                jsonObject.put("CustId", custVendorMasterId);
            } else {
                jsonObject.put("CustId", "");
            }
            jsonObject.put("entityname", entityName);
            jsonObject.put("entityadd", addr);
            jsonObject.put("mob", mob);
            jsonObject.put("email", email);
            jsonObject.put("meterno", meterNo);
            if (mode != null && mode.equals("E")) {
                jsonObject.put("Mode", "E");
            }else if (mode != null && mode.equals("D")) {
                jsonObject.put("Mode", "D");
            } else {
                jsonObject.put("Mode", "A");
            }
            jsonObject.put("metertype", mType);
            jsonObject.put("ConnectionType", connType);
            jsonObject.put("connectionno", connNo);
            if (checkIsWhatsapp.isChecked()) {
                jsonObject.put("IsWhatsAppNo", "Y");
            } else {
                jsonObject.put("IsWhatsAppNo", "N");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalJosnObj = jsonObject.toString().replace("\\\\", "");
        if (ut.isNet(AddClientMaster.this)) {
            progress.setVisibility(View.VISIBLE);
            new StartSession(AddClientMaster.this, new CallbackInterface() {
                @Override
                public void callMethod() {
                    new PostSaveData().execute(finalJosnObj);
                }

                @Override
                public void callfailMethod(String msg) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(AddClientMaster.this, msg, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            progress.setVisibility(View.GONE);
            Toast.makeText(AddClientMaster.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }





 /*   private void CheckWhatsappValid(String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CompanyURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        JSONObject postParam = null;
        try {
            postParam = new JSONObject();
            postParam.put("mob", s);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<String> call = apiInterface.ApiName(postParam);
        Log.d("API",call.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Response", response.body());
                if (response != null) {
                    if (response.body().contains("Not a whatsapp number")) {
                        checkIsWhatsapp.setChecked(false);
                    } else if (response.body().contains("Success")) {
                        checkIsWhatsapp.setChecked(true);
                    } else {
                        checkIsWhatsapp.setChecked(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddClientMaster.this, "please contact admin ", Toast.LENGTH_SHORT).show();
            }
        });*/


    private class PostSaveData extends AsyncTask<String, Void, String> {

        Object res;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = CompanyURL + WebUrlClass.api_PostEntity;

            try {
                res = ut.OpenPostConnection(url, params[0], getApplicationContext());
                if (res != null) {
                    response = res.toString();
                    response = res.toString().replaceAll("\\\\", "");
                    response = response.replaceAll("\\\\\\\\/", "");
                    response = response.substring(1, response.length() - 1);

                } else {
                    response = "error";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (response.equals("success")) {

                ClientDetailsBean clientDetailsBean = new ClientDetailsBean();
                clientDetailsBean.setCustVendorName(edt_fname.getText().toString().trim() + " " + edt_lname.getText().toString().trim());
                clientDetailsBean.setCustVendorMasterId(custVendorMasterId);
                clientDetailsBean.setAddress(edt_addr.getText().toString());
                clientDetailsBean.setMobile(edt_mobileNo.getText().toString());
                clientDetailsBean.setEmail(emailID.getText().toString());
                clientDetailsBean.setMeterType(mType);
                clientDetailsBean.setConnectionType(connType);
                clientDetailsBean.setMeterNo(edt_meterNo.getText().toString());
                clientDetailsBean.setConnNo(edt_connectionNo.getText().toString());
                if (checkIsWhatsapp.isChecked()) {
                    clientDetailsBean.setIsWhatsapp("Y");
                } else {
                    clientDetailsBean.setIsWhatsapp("N");
                }

                if (mode != null && mode.equals("E")) {
                    cf.UpdateExpenseDetails(clientDetailsBean, custVendorMasterId);
                    clientDetailsBeanArrayList.remove(pos);
                    clientDetailsBeanArrayList.add(pos, clientDetailsBean);
                    //clientListAdapter.update(clientDetailsBeanArrayList);
                    Toast.makeText(AddClientMaster.this, "Client master updated successfully !!!", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                    onBackPressed();
                } else if (mode != null && mode.equals("D")) {
                    cf.DeleteExpenseDetails(custVendorMasterId);
                    clientDetailsBeanArrayList.remove(pos);
                    //clientDetailsBeanArrayList.add(pos, clientDetailsBean);
                    Toast.makeText(AddClientMaster.this, "Client master deleted successfully !!!", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                    onBackPressed();
                } else {

                    clientDetailsBeanArrayList.add(clientDetailsBean);
                    cf.AddClientDetails(clientDetailsBean);
                    // clientListAdapter.update(clientDetailsBeanArrayList);
                    Toast.makeText(AddClientMaster.this, "Client master Save successfully !!!", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                    onBackPressed();
                }


            } else {
                progress.setVisibility(View.GONE);
                Toast.makeText(AddClientMaster.this, "Client master not Save successfully", Toast.LENGTH_SHORT).show();
            }
        }
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
        protected String doInBackground(String... params) {
            String url = CompanyURL + WebUrlClass.api_GetData + "?Id=" + params[0];
            try {

                res = ut.OpenConnection(url, AddClientMaster.this);
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

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ClientDetailsBean clientDetailsBean = new ClientDetailsBean();

                        clientDetailsBeanArrayList.add(clientDetailsBean);
                        edt_addr.setText(jsonObject.getString("Address"));
                        edt_mobileNo.setText(jsonObject.getString("Mobile"));
                        emailID.setText(jsonObject.getString("Email"));
                        edt_connectionNo.setText(jsonObject.getString("ConnNo"));
                        edt_meterNo.setText(jsonObject.getString("MeterNo"));

                        clientDetailsBean.setAddress(jsonObject.getString("Address"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent intent = new Intent(AddClientMaster.this, ClientListActivity.class);

        String datasheetList = new Gson().toJson(new ClientDetailsBean(clientDetailsBeanArrayList));

        intent.putExtra("ClientList", datasheetList);
        setResult(10, intent);
        finish();
    }
}