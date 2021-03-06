package com.project.aquacy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.project.aquacy.CommonFile.AppCommon;
import com.project.aquacy.CommonFile.CallBack;
import com.project.aquacy.CommonFile.CommonFunction;
import com.project.aquacy.CommonFile.SetAppName;
import com.project.aquacy.CommonFile.Utility;
import com.project.aquacy.CommonFile.ValidateUser;
import com.project.aquacy.CommonFile.WebUrlClass;
import com.project.aquacy.Database.DatabaseHandlers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.leolin.shortcutbadger.ShortcutBadger;


public class ActivityLogIn extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // DownloadAuthenticate();

    public static final String APP_URL_SAHARA = "http://e207.ekatm.com";
    // public static final String APP_URL_SAHARA = "http://education.talukahaveli.in";
    public static final String APP_URL_HAJMOLA = "http://hajmola.ekatm.com";

    String CompanyURL = "";

    // public static final String APP_URL_SAHARA = "http://education.talukahaveli.in";

    String PlantMasterId = "", LoginId = "", Password = "", EnvMasterId = "", UserName = "", MobileNo = "";
    Utility ut;
    DatabaseHandlers db;
    public static Context context;
    Button btnLogin;
    TextView txt_frgPwd;
    Boolean IsSessionActivate, IsValidUser;
    Spinner edEnv, edPlant;
    String IsCrmUser;
    private ProgressDialog progressDialog;
    EditText edLoginId, edPassword, edmob;
    public static EditText textotp;
    public static Intent igpsalarm;
    String App_version;
    private Button BtnOk;
    Spinner spinner_env,spinner_Plant;
    private LinearLayout lin_compcode, lin_login;
    private CheckBox mCbShowPwd;
    CheckBox mSecure;
    EditText mEturl;
    private static int REQUEST_CODE = 12;
    private static final int PERMISSION_REQUEST_CODE = 1;
    int PERMISSION_ALL = 1;

    SharedPreferences sharedpreferences;
    public static SharedPreferences userpreferences;

    private RelativeLayout relProgress;
    CommonFunction cf;
    private boolean isFirstImage = true;
    private volatile static int numThread = 1;
    private volatile static int threadAllowedToRun = 1;
    private int myThreadID;
    private volatile static Object myLock = new Object();
    private volatile static boolean hasNotSlept = true;
    public volatile static boolean fixForMatch = false;
    GoogleApiClient googleApiClient = null;

    private String APP_URL_Alfa = "http://alfatest.ekatm.co.in";


    // private String APP_URL_Alfa = "http://10.128.72.105";

    private FirebaseAuth mAuth;
    private String mVerificationId;

    ImageView img_back;
    TextView txt_title;
    String[] PERMISSIONS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
      //  setToolbar();
        InitView();
        setListner();
        // App_version=intent.getStringExtra("version");
        context = getApplicationContext();
        ut = new Utility();
        cf = new CommonFunction(context);
        db = new DatabaseHandlers(context);
        sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
        userpreferences = getSharedPreferences(WebUrlClass.USERINFO,
                Context.MODE_PRIVATE);
        ShortcutBadger.with(getApplicationContext()).remove();
        SharedPreferences.Editor editor = userpreferences.edit();
        editor.remove(WebUrlClass.USERINFO_SHORTCUTADGER_COUNT);
        editor.commit();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (ut.isNet(getApplicationContext())) {
          //  versionCheck();
        }

        if (googleApiClient == null) {


            if (ut.isNet(getApplicationContext())) {
                //EnableGPSAutoMatically();
            } else {
                ut.displayToast(getApplicationContext(), "No Internet Connection");
            }


            Intent i = getIntent();
            if (!(i.hasExtra(WebUrlClass.INTENT_LOGIN_SCREEN_BACKFLAG))) {
               /* if (getLogINCount() && !ut.IsChangePassword(context)) {
                    Intent intent = new Intent(ActivityLogIn.this, ActivityModuleSelection.class);
                    startActivity(intent);
                    finish();
                }*/ if (getLogINCount() ) {

                        Intent intent = new Intent(ActivityLogIn.this, ClientListActivity.class);
                        startActivity(intent);
                        finish();
                }

            }


        } else {
            Intent i = getIntent();
            if (!(i.hasExtra(WebUrlClass.INTENT_LOGIN_SCREEN_BACKFLAG))) {
                if (getLogINCount()) {
                    Intent intent = new Intent(ActivityLogIn.this, ClientListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }





        //    sendVerificationCode("+918600669097");
    }

    private void setListner() {
        txt_frgPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogIn.this,ForgetPasswordActivity.class));
            }
        });


        AppCommon.getInstance(ActivityLogIn.this).onHideKeyBoard(ActivityLogIn.this);


        BtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCommon.getInstance(ActivityLogIn.this).onHideKeyBoard(ActivityLogIn.this);

                /*if(Constants.type == Constants.Type.Vwb) {

                    lin_login.setVisibility(View.VISIBLE);
                    lin_compcode.setVisibility(View.GONE);
                    methodEnvirnment();

                }else {
*/
                if (ut.isNet(getApplicationContext())) {
                    if (isGooglePlayServicesAvailable()) {

                            if (checkEditTextUrl()) {
/*
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                    String[] PERMISSIONS = {
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.INTERNET,
                                           // Manifest.permission.READ_CONTACTS,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                    };
                                    if (!hasPermissions(ActivityLogIn.this, PERMISSIONS)) {
                                        ActivityCompat.requestPermissions(ActivityLogIn.this, PERMISSIONS, PERMISSION_ALL);
                                    } else {
                                        BtnOk.setEnabled(false);
                                        methodEnvirnment();
                                    }
                                }
*/

                                    String[] PERMISSIONS =
                                            {
                                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.INTERNET,
                                                   // Manifest.permission.READ_CONTACTS,
                                            };

                                    if (!hasPermissions(ActivityLogIn.this, PERMISSIONS)) {
                                        ActivityCompat.requestPermissions(ActivityLogIn.this, PERMISSIONS, PERMISSION_ALL);
                                    } else {
                                        BtnOk.setEnabled(false);
                                        methodEnvirnment();
                                    }

                            }

                    }


                } else {
                    ut.displayToast(getApplicationContext(), "No Internet Connection");
                }
            }
        });


        spinner_env.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EnvMasterId = spinner_env.getSelectedItem().toString();
                if (!(EnvMasterId.equalsIgnoreCase(getResources().getString(R.string.instruction_Spinner_Change_Company)))) {
                    SharedPreferences sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(WebUrlClass.MyPREFERENCES_EnvMasterID_KEY, EnvMasterId);
                    editor.commit();
                    DownloadPlantsJSON plants = new DownloadPlantsJSON();
                    plants.execute(EnvMasterId);
                } else {
                    mEturl.setText("");
                    ut.hideProgress(relProgress);
                    lin_login.setVisibility(View.GONE);
                    lin_compcode.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_Plant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String PlantName = spinner_Plant.getSelectedItem().toString();
                PlantMasterId = getPlantID(PlantName);
                SharedPreferences sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(WebUrlClass.MyPREFERENCES_PlantID_KEY, PlantMasterId);
                editor.putString(WebUrlClass.MyPREFERENCES_PlantName_KEY, PlantName);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditText()) {
                    LoginId = edLoginId.getText().toString();
                    Password = edPassword.getText().toString();
                    MobileNo = edmob.getText().toString();

                    final String strUserID = LoginId;
                    final String strpsw = Password;
                    if (ut.isNet(getApplicationContext())) {
                        btnLogin.setEnabled(false);
                        ut.showProgress(relProgress);
                        new ValidateUser(EnvMasterId, LoginId, strpsw, PlantMasterId, getApplicationContext(), new CallBack() {
                            @Override
                            public void onCall() {
                                Register register = new Register();
                                register.execute("", EnvMasterId, strUserID, strpsw, MobileNo, PlantMasterId);

                            }

                            @Override
                            public void failCall(String msg) {
                                MySnackbar(msg);
                                btnLogin.setEnabled(true);

                                ut.hideProgress(relProgress);
                            }
                        });
                    } else {
                        MySnackbar("No Internet Connection");
                    }
                }
            }
        });


    }

    private void methodEnvirnment() {

        String extn;

            Boolean flaBoolean = mSecure.isChecked();

            if (mSecure.isChecked()) {
                extn = "https://";
            } else {
                if (mEturl.getText().toString().toLowerCase().trim().contains("vritti") || mEturl.getText().toString().toLowerCase().trim().contains("mmpl")
                        || mEturl.getText().toString().toLowerCase().trim().contains("vst"))
                    extn = "https://";
                else
                    extn = "http://";
            }

            CompanyURL = extn + mEturl.getText().toString().toLowerCase().trim();
            GetEnv getEnv = new GetEnv();
            getEnv.execute(CompanyURL);




    }

    private void InitView() {
        mSecure = (CheckBox) findViewById(R.id.checkBoxsecure);
        mSecure.setVisibility(View.VISIBLE);
        BtnOk = findViewById(R.id.BtnOK);
        spinner_env = findViewById(R.id.spinner_env);
        spinner_Plant = findViewById(R.id.spinner_Plant);
        lin_login = findViewById(R.id.lin_login);
        lin_compcode = findViewById(R.id.lin_compcode);
        mCbShowPwd = findViewById(R.id.checkBox);
        edPassword = findViewById(R.id.edPassword);
        edLoginId = findViewById(R.id.edLoginId);
        edmob = findViewById(R.id.edmob);
        btnLogin = findViewById(R.id.btnLogin);
        txt_frgPwd = findViewById(R.id.txt_frgPwd);

        mEturl = (EditText) findViewById(R.id.input_Url);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEturl, InputMethodManager.SHOW_IMPLICIT);
        mEturl.requestFocus();
        relProgress = (RelativeLayout) findViewById(R.id.rel_progress);

    }

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");


      //  img_back.setImageDrawable(getResources().getDrawable(R.drawable.app_logo_1));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
     //   Mint.initAndStartSession(this.getApplication(), "4cf63235");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private boolean getLogINCount() {
        SQLiteDatabase sql = db.getWritableDatabase();
        Cursor c = sql.rawQuery("Select * from " + db.TABLE_LOGIN_SETTING, null);
        int cnt = c.getCount();
        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("NewApi")


    private Boolean checkEditText() {
        if (EnvMasterId.equalsIgnoreCase("Environment ID")) {
            ut.displayToast(getApplicationContext(), "Please Select Environment ID");
            return false;
        } else if (PlantMasterId.equalsIgnoreCase("Plant ID")) {
            ut.displayToast(getApplicationContext(), "Please Select Plant ID");
            return false;
        } else if (!(edLoginId.getText().toString().length() > 0)) {
            ut.displayToast(getApplicationContext(), "Please Enter User ID");
            return false;
        } else if (!(edPassword.getText().toString().length() > 0)) {
            ut.displayToast(getApplicationContext(), "Please Enter Password");
            return false;
        } else if (!(edmob.getText().toString().length() > 0)) {
            ut.displayToast(getApplicationContext(), "Please Enter Register Mobile No.");
            return false;
        } else if (!(edmob.getText().toString().length() > 9)) {
            ut.displayToast(getApplicationContext(), "Please Enter valid Mobile No.");
            return false;
        }

        return true;
    }

    private void UpdateSpinner(String companyName) {
        List<String> list = new ArrayList<String>();
        list.add(companyName);
        list.add(getResources().getString(R.string.instruction_Spinner_Change_Company));
        // list = GetEnvData();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ActivityLogIn.this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        edEnv.setAdapter(dataAdapter);
    }

    public class Register extends AsyncTask<String, String, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            Object res;
            String response;
            String a = ut.getSharedPreference_URL(context);
            String url = ut.getSharedPreference_URL(context) + WebUrlClass.api_GetSessions + "?AppEnvMasterId="
                    + params[1] + "&UserLoginId=" + params[2] +
                    "&UserPwd=" + params[3] + "&PlantId=" + params[5] + "";
            try {
                url = url.replaceAll(" ", "%20");
                res = Utility.OpenConnection(url, getApplicationContext());
                response = res.toString().replaceAll("\\\\", "");

            } catch (Exception e) {
                e.printStackTrace();
                response = WebUrlClass.setError;
            }
            List<String> result = new ArrayList<String>();
            result.add(response);
            result.add(params[1]);
            result.add(params[2]);
            result.add(params[3]);
            result.add(params[4]);
            result.add(params[5]);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);
            String s2 = s.get(0);
            String envId = s.get(1);
            String logInId = s.get(2);
            String userPsw = s.get(3);
            String mobile = s.get(4);
            String PlantId = s.get(5);

            if (s2.equalsIgnoreCase(WebUrlClass.setError)) {
                btnLogin.setEnabled(true);
                MySnackbar("Server not responding...Please try after Sometime...");
                ut.hideProgress(relProgress);
            } else if (s2.contains("true")) {
                SharedPreferences sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(WebUrlClass.MyPREFERENCES_LOGIN_KEY, logInId);
                editor.putString(WebUrlClass.MyPREFERENCES_PSW_KEY, userPsw);
                editor.putString(WebUrlClass.MyPREFERENCES_MOBILE_KEY, mobile);
                editor.commit();
                new DownloadAuthenticate().execute();


            } else {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                MySnackbar("Failed to Start Session");
            }
        }
    }

    private JSONObject getJobject() {
        JSONObject data = null;
        try {
         //   FirebaseInstanceId.getInstance().getToken();
            data = new JSONObject();
            //String Token = sharedpreferences.getString(WebUrlClass.MyPREFERENCES_FIREBASE_TOKEN_KEY, null);
            data.put("App_Name", SetAppName.AppNameFCM);
            data.put("UserMasterId", ut.getSharedPreference_UserMasterID(getApplicationContext()));
            data.put("DeviceId", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    class UploadFCMDetail extends AsyncTask<String, Void, String> {
        Object res;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = null;
            url = ut.getSharedPreference_URL(context) + WebUrlClass.FCMurl;
            try {
                res = ut.OpenPostConnection(url, params[0], getApplicationContext());
                response = res.toString().replaceAll("\\\\", "");
                response = response.substring(1, response.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
                response = "Error";
            }
            return response;
        }

        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);
            new DownloadIfCRMUserJson().execute();

            if (integer.equalsIgnoreCase("Fail")) {
                Log.d("FCM registration Status", "Fail");
            } else if (integer.equalsIgnoreCase("Error")) {
                ut.displayToast(getApplicationContext(), "FCM Registration Failed");
                Log.d("FCM registration Status", "Error");

            } else if (integer.equalsIgnoreCase("Success")) {

                Log.d("FCM registration Status", "Success");
                // ut.displayToast(getApplicationContext(), "FCM Registration Failed");
            }
        }
    }

    class DownloadIfCRMUserJson extends AsyncTask<Integer, Void, Integer> {
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            String url = null;
            try {
                url = ut.getSharedPreference_URL(context) + WebUrlClass.api_getIfCRMUser + "?UserMstrId=" + URLEncoder.encode
                        (ut.getSharedPreference_UserMasterID(getApplicationContext()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                ut.displayToast(getApplicationContext(), "Unsupported Encoding Exception occurred");
            }

            try {
                res = ut.OpenConnection(url, getApplicationContext());
                res = res.replaceAll("\\\\\"", "");
                res = res.replaceAll("\"", "");
                res = res.replaceAll(" ", "");
                IsCrmUser = res;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            ut.hideProgress(relProgress);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(WebUrlClass.MyPREFERENCES_IS_CRMUSER_KEY, IsCrmUser);
            editor.commit();

            int cnt = insertLoginData();
            editor.putInt(WebUrlClass.MyPREFERENCES_SETTING_POSITION_KEY, cnt - 1);
            editor.commit();
            btnLogin.setEnabled(true);
            MySnackbar("Login Successful...");


                    Intent intent = new Intent(ActivityLogIn.this, ClientListActivity.class);
                    intent.putExtra("cnt", cnt);
                    startActivity(intent);
                    finish();
            }




        }


    class DownloadUserMasterIdFromServer extends AsyncTask<String, Void, String> {
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String url = ut.getSharedPreference_URL(context) + WebUrlClass.api_GetUserMasterIdAndroid;
            try {
                res = ut.OpenConnection(url, getApplicationContext());
                res = res.replaceAll("\\\\", "");
                // res = res.replaceAll("\"", "");
                res = res.substring(1, res.length() - 1);
                // UserMasterId = res;

            } catch (Exception e) {
                res = WebUrlClass.setError;
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);
            String s = integer;
            if (s.contains("UserMasterI")) {//UserMasterId
                SharedPreferences sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
                String UsermasterID = "", UserName = "", LogINID = "",Designation="";
                if (s.contains("UserMasterId##")) {
                    String data[] = res.split("##");
                    String us1 = data[0];
                    UsermasterID = data[1];
                } else {
                    try {
                        JSONArray jResults = new JSONArray(s);
                        for (int index = 0; index < jResults.length(); index++) {
                            JSONObject jorder = jResults.getJSONObject(index);
                            UsermasterID = jorder.getString("UserMasterID");
                            UserName = jorder.getString("UserName");
                            LogINID = jorder.getString("LoginID");
                            Designation = jorder.getString("Designation");


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(WebUrlClass.MyPREFERENCES_USERMASTER_ID_KEY, UsermasterID);
                editor.putString(WebUrlClass.MyPREFERENCES_USERNAME_KEY, UserName);
                editor.putString(WebUrlClass.MyPREFERENCES_Designation_KEY, Designation);
                editor.commit();

                if (!getLogINCount()) {
                    JSONObject Jobj = getJobject();
                    String jobject = Jobj.toString().replaceAll("\\\\", "");
                    new UploadFCMDetail().execute(jobject);

                    //  regservicenonGPS(getApplicationContext());

                } else {
                    new DownloadIfCRMUserJson().execute();
                }

            } else {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                Toast.makeText(getApplicationContext(), "Can not find out user", Toast.LENGTH_LONG).show();
            }


        }
    }

    class DownloadAuthenticate extends AsyncTask<String, Void, String> {
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String AppName = "";
            AppName = SetAppName.AppNameFCM;

           /* String url = ut.getSharedPreference_URL(context) +
                    WebUrlClass.api_GetOTPServer + "?MobNo=" + MobileNo + "&UserLoginId=" + LoginId + "&AppName=" + AppName;*/

             String url = "http://clientsms.vritti.co.in/VrittiQM.asmx/WatsAppLess?m="+MobileNo+"&u=ae1001&p=vritti123";
          //  http://clientsms.vritti.co.in/VrittiQM.asmx/WatsAppLess?m=7020256278&u=ae1001&p=vritti123

            try {
                res = ut.httpGet(url);
                res = res.toString();
                res = res.toString().replaceAll("\\\\", "");


               // response = ut.OpenConnection(url, MilkRunLocationListActivity.this);
               /* res = ut.OpenConnection(url, ActivityLogIn.this);
                res = res.replaceAll("\\\\", "");
                res = res.substring(1, res.length() - 1);*/


            } catch (Exception e) {
                e.printStackTrace();
                res = "Error";
            }
            return res;
        }


        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);

            if(res.contains("Not a whatsapp number")){
                Toast.makeText(ActivityLogIn.this, "Not a whatsapp number", Toast.LENGTH_SHORT).show();
            }else if(res.contains("Success")){
                Toast.makeText(ActivityLogIn.this, "Valid whatsapp number", Toast.LENGTH_SHORT).show();
            }

            new DownloadAuthenticate1().execute();



            /*if (res.contains("#Success")) {
                String data[] = res.split("#");
                final String OPT = data[0];

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivityLogIn.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.vwb_otp_lay, null);
                dialogBuilder.setView(dialogView);

                // set the custom dialog components - text, image and button
                textotp = (EditText) dialogView.findViewById(R.id.edt_otp);
                Button button = (Button) dialogView.findViewById(R.id.txt_submit);
                Button txt_resend_otp = (Button) dialogView.findViewById(R.id.txt_resend_otp);
                // TextView txt_resend_otp=dialogView.findViewById(R.id.txt_resend_otp);
                dialogBuilder.setCancelable(false);
                final AlertDialog b = dialogBuilder.create();
                b.show();
                // if button is clicked, close the custom dialog
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String entrotp = textotp.getText().toString().trim();
                        if (!(entrotp.equals(""))) {
                            if (entrotp.equalsIgnoreCase(OPT)) {
                                b.dismiss();
                                //Toast.makeText(getApplicationContext(), "OTP s", Toast.LENGTH_LONG).show();
                                new DownloadUserMasterIdFromServer().execute();

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid OTP!!! try again", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter OTP", Toast.LENGTH_LONG).show();
                        }
                    }
                });


                */
            /*
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b.dismiss();
                    }
                });
*//*

                txt_resend_otp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MobileNo = edmob.getText().toString();
                        LoginId = edLoginId.getText().toString();

                        new DownloadAuthenticate().execute();

                    }
                });

            }
            else if (res.contains("User Not Found")) {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                Toast.makeText(getApplicationContext(), "Please Enter Register Mobile Number", Toast.LENGTH_LONG).show();
            }
            else if (res.contains("UserId and Password not found in ERPModuleSetUp")) {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                Toast.makeText(getApplicationContext(), "OTP service is not registered ", Toast.LENGTH_LONG).show();
            }
            else {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                Toast.makeText(getApplicationContext(), "temporarily unavailable service!!! Please try after some time..", Toast.LENGTH_LONG).show();
            }*/

        }
    }

    class DownloadAuthenticate1 extends AsyncTask<String, Void, String> {
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String AppName = "";
            AppName = SetAppName.AppNameFCM;
            String url = ut.getSharedPreference_URL(context) + WebUrlClass.api_GetOTPServer + "?MobNo=" + MobileNo + "&UserLoginId=" + LoginId + "&AppName=" + AppName;
            //UserLoginId=300207&AppName
            try {
                res = ut.OpenConnection(url, getApplicationContext());
                res = res.replaceAll("\\\\", "");
                res = res.substring(1, res.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
                res = "Error";
            }
            return res;
        }


        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);
            if (res.contains("#Success")) {
                String data[] = res.split("#");
                final String OPT = data[0];

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivityLogIn.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.vwb_otp_lay, null);
                dialogBuilder.setView(dialogView);

                // set the custom dialog components - text, image and button
                textotp = (EditText) dialogView.findViewById(R.id.edt_otp);
                Button button = (Button) dialogView.findViewById(R.id.txt_submit);
                Button txt_resend_otp = (Button) dialogView.findViewById(R.id.txt_resend_otp);
                // TextView txt_resend_otp=dialogView.findViewById(R.id.txt_resend_otp);
                dialogBuilder.setCancelable(false);
                final AlertDialog b = dialogBuilder.create();
                b.show();
                // if button is clicked, close the custom dialog
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String entrotp = textotp.getText().toString().trim();
                        if (!(entrotp.equals(""))) {
                            if (entrotp.equalsIgnoreCase(OPT)) {
                                b.dismiss();
                                //Toast.makeText(getApplicationContext(), "OTP s", Toast.LENGTH_LONG).show();
                                new DownloadUserMasterIdFromServer().execute();
                               // new DownloadUserMasterIdFromServer().execute();

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid OTP!!! try again", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter OTP", Toast.LENGTH_LONG).show();
                        }
                    }
                });
/*
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b.dismiss();
                    }
                });
*/

                txt_resend_otp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MobileNo = edmob.getText().toString();
                        LoginId = edLoginId.getText().toString();

                        new DownloadAuthenticate().execute();

                    }
                });

            } else if (res.contains("User Not Found")) {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                Toast.makeText(getApplicationContext(), "Please Enter Register Mobile Number", Toast.LENGTH_LONG).show();
            } else if (res.contains("UserId and Password not found in ERPModuleSetUp")) {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                Toast.makeText(getApplicationContext(), "OTP service is not registered ", Toast.LENGTH_LONG).show();
            } else {
                btnLogin.setEnabled(true);
                ut.hideProgress(relProgress);
                Toast.makeText(getApplicationContext(), "temporarily unavailable service!!! Please try after some time..", Toast.LENGTH_LONG).show();
            }

        }
    }

    class DownloadPlantsJSON extends AsyncTask<String, String, String> {
        String res;
        List plantsName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String url = ut.getSharedPreference_URL(context) + WebUrlClass.api_getPlants + "?AppEnvMasterId=" +
                        URLEncoder.encode(EnvMasterId, "UTF-8") + "&PlantId=";
                res = ut.OpenConnection(url, getApplicationContext());
                res = res.replaceAll("\\\\", "");
                //  res = res.replaceAll("\\\"", "");
                //  res = res.replaceAll("", "");
                res = res.substring(1, res.length() - 1);
                JSONArray jResults = new JSONArray(res);
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(db.TABLE_PLANTMASTER, null,
                        null);
                Cursor c = sql.rawQuery("SELECT * FROM " + db.TABLE_PLANTMASTER, null);
                int count = c.getCount();
                String columnName, columnValue;
                ContentValues values = new ContentValues();
                plantsName = new ArrayList();
                for (int i = 0; i < jResults.length(); i++) {
                    JSONObject jorder = jResults.getJSONObject(i);
                    for (int j = 0; j < c.getColumnCount(); j++) {
                        columnName = c.getColumnName(j);
                        columnValue = jorder.getString(columnName);
                        values.put(columnName, columnValue);
                        if (columnName.equalsIgnoreCase("PlantMasterId")) {

                        } else {
                            plantsName.add(jorder.getString("PlantName"));
                        }
                    }
                    long a = sql.insert(db.TABLE_PLANTMASTER, null, values);
                }

            } catch (Exception e) {
                e.printStackTrace();
                res = "Error";
            }
            return res;
        }

        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);
            ut.hideProgress(relProgress);
            if (integer.contains("PlantMasterId")) {
                Collections.sort(plantsName);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ActivityLogIn.this, android.R.layout.simple_spinner_item, plantsName);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Plant.setAdapter(dataAdapter);
            } else {

                ut.displayToast(getApplicationContext(), "Couldn't Connect to the Server");
            }
        }

    }

    class GetEnv extends AsyncTask<String, Void, List<String>> {
        String res;
        List<String> EnvName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ut.showProgress(relProgress);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            String url = params[0] + WebUrlClass.api_getEnv;
            try {
                res = ut.OpenConnection(url, getApplicationContext());
                res = res.replaceAll("\\\\", "");
                res = res.substring(1, res.length() - 1);


            } catch (Exception e) {
                e.printStackTrace();

                res = e.getMessage();
            }

            List<String> result = new ArrayList<>();
            result.add(res);
            result.add(params[0]);
            return result;

        }

        @Override
        protected void onPostExecute(List<String> str) {
            super.onPostExecute(str);

            BtnOk.setEnabled(true);
            String s1 = str.get(1);
            String s2 = str.get(0);
            ut.hideProgress(relProgress);

            //  Toast.makeText(ActivityLogIn.this,res +"--"+CompanyURL,Toast.LENGTH_SHORT).show();

            if (res.contains("AppEnvMasterId")) {

                /// URL Sharedpreference
                lin_compcode.setVisibility(View.GONE);
                lin_login.setVisibility(View.VISIBLE);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(WebUrlClass.MyPREFERENCES_URL_KEY, s1);

                try {
                    JSONArray jResults = new JSONArray(res);

                    EnvName = new ArrayList<String>();
                    for (int index = 0; index < jResults.length(); index++) {

                        JSONObject jorder = jResults.getJSONObject(index);
                        EnvName.add(jorder.getString("AppEnvMasterId"));
                        String isChatApplicable = jorder.getString("IsChatApplicable");
                        String isGPSLocation = jorder.getString("IsGPSLocation");
                        editor.putString(WebUrlClass.MyPREFERENCES_IS_CHAT_APPLICABLE_KEY, isChatApplicable);
                        editor.putString(WebUrlClass.MyPREFERENCES_IS_GPS_LOCATION_KEY, isGPSLocation);
                    }
                    EnvName.add(getResources().getString(R.string.instruction_Spinner_Change_Company));
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(ActivityLogIn.this,android.R.layout.simple_spinner_dropdown_item,EnvName);
                    stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_env.setAdapter(stringArrayAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                editor.commit();

            } else {
                mEturl.setText("");
                lin_compcode.setVisibility(View.VISIBLE);
                lin_login.setVisibility(View.GONE);
                ut.displayToast(getApplicationContext(), "Enter valid Url");
                MySnackbar("Enter valid URL");
            }
        }
    }

    class GetEnvpm extends AsyncTask<String, Void, List<String>> {
        String res;
        List<String> EnvName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ut.showProgress(relProgress);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            String url = ut.getSharedPreference_URL(getApplicationContext()) + WebUrlClass.api_getEnvPM + "?AppEnvMasterId=" + params[0];
            url = url.replaceAll(" ", "%20");
            try {
                res = ut.OpenConnection(url, getApplicationContext());
                res = res.replaceAll("\\\\", "");
                res = res.substring(1, res.length() - 1);


            } catch (Exception e) {
                e.printStackTrace();
                res = WebUrlClass.setError;
            }
            List<String> result = new ArrayList<>();
            result.add(res);
            result.add(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<String> str) {
            super.onPostExecute(str);


            String s1 = str.get(1);
            String s2 = str.get(0);

            if (res.contains("AppEnvMasterId")) {
                lin_compcode.setVisibility(View.GONE);
                lin_login.setVisibility(View.VISIBLE);
                /// URL Sharedpreference


                try {
                    JSONArray jResults = new JSONArray(res);
                    EnvName = new ArrayList<String>();
                    for (int index = 0; index < jResults.length(); index++) {
                        JSONObject jorder = jResults.getJSONObject(index);
                        EnvName.add(jorder.getString("AppEnvMasterId"));
                        String isChatApplicable = jorder.getString("IsChatApplicable");
                        String isGPSLocation = jorder.getString("IsGPSLocation");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(WebUrlClass.MyPREFERENCES_IS_CHAT_APPLICABLE_KEY, isChatApplicable);
                        editor.putString(WebUrlClass.MyPREFERENCES_IS_GPS_LOCATION_KEY, isGPSLocation);
                        editor.commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public class CheckEnv extends AsyncTask<String, String, List<String>> {
        Object res;
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ut.showProgress(relProgress);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            String url = WebUrlClass.APP_URL_PM + WebUrlClass.api_checkEnv + "?AppEnvMasterId=" + params[0];
            try {
                res = Utility.OpenConnection(url, getApplicationContext());
                response = res.toString().replaceAll("\\\\", "");
                response = response.substring(1, response.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
            }
            List<String> result = new ArrayList<>();
            result.add(response);
            result.add(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);
            ut.hideProgress(relProgress);
            BtnOk.setEnabled(true);
            String s1 = s.get(1);
            String s2 = s.get(0);
            if (s2.contains("True")) {

                lin_compcode.setVisibility(View.GONE);
                lin_login.setVisibility(View.VISIBLE);
                /// URL Sharedpreference
                SharedPreferences sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(WebUrlClass.MyPREFERENCES_URL_KEY, WebUrlClass.APP_URL_PM);
                editor.commit();
                UpdateSpinner(s1);
            } else {
                mEturl.setText("");
                lin_compcode.setVisibility(View.VISIBLE);
                lin_login.setVisibility(View.GONE);
                MySnackbar(getResources().getString(R.string.error_code_company_valid));
            }
        }
    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, ActivityLogIn.this, 0).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private Boolean checkEditTextUrl() {
        if (!(mEturl.getText().toString().length() > 0)) {
            MySnackbar(getResources().getString(R.string.error_code_company));
            return false;
        }
        return true;
    }

    private void MySnackbar(String display) {
        Snackbar.make(findViewById(R.id.asd), display, Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText( CompanyURLActivity.this, "Snackbar Action", Toast.LENGTH_LONG).show();
            }
        }).show();
    }

    private boolean getLogINCountgps() {
        SQLiteDatabase sql = db.getWritableDatabase();
        Cursor c = sql.rawQuery("Select * from " + db.TABLE_LOGIN_SETTING, null);
        int cnt = c.getCount();
        if (cnt == 1) {
            return true;
        } else {
            return false;
        }
    }

    private int insertLoginData() {
        String imei_number = "";
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            imei_number = telephonyManager.getDeviceId();
            imei_number = telephonyManager.getDeviceId();

        } catch (SecurityException e) {
            imei_number = "Security Exception";
        }
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ContentValues contentValues = new ContentValues();
        String uniqueID = UUID.randomUUID().toString();
        contentValues.put("LogInKey", uniqueID);
        contentValues.put("CompanyURL", ut.getSharedPreference_URL(getApplicationContext()));
        contentValues.put("EnvId", ut.getSharedPreference_EnvMasterID(getApplicationContext()));
        contentValues.put("PlantID", ut.getSharedPreference_PlantID(getApplicationContext()));
        contentValues.put("PlantName", ut.getSharedPreference_plantName(getApplicationContext()));
        contentValues.put("UserLogInId", ut.getSharedPreference_UserloginID(getApplicationContext()));
        contentValues.put("UserMasterId", ut.getSharedPreference_UserMasterID(getApplicationContext()));
        contentValues.put("Password", ut.getSharedPreference_Psw(getApplicationContext()));
        contentValues.put("Mobile", ut.getSharedPreference_Mobile(getApplicationContext()));
        contentValues.put("UserName", ut.getSharedPreference_getUsername(getApplicationContext()));
        contentValues.put("IsCRMuser", ut.getSharedPreference_isCRMUser(getApplicationContext()));
        contentValues.put("IsChatApplicable", ut.getSharedPreference_isChatApplicable(getApplicationContext()));
        contentValues.put("IsGpsLocation", ut.getSharedPreference_isGPSLocation(getApplicationContext()));
        contentValues.put("BackDateTimesheet", "");
        contentValues.put("AndroidId", android_id);
        contentValues.put("IMEINumber", imei_number);
        contentValues.put("FCMToken", ut.getSharedPreference_getFCMToken(getApplicationContext()));
        contentValues.put("Designation", ut.getSharedPreference_Designation(getApplicationContext()));

        final int min = 1000;
        final int max = 9999;
        final int random = new Random().nextInt((max - min) + 1) + min;
        contentValues.put("DatabaseName", random + ".dbo");
        SQLiteDatabase sql = db.getWritableDatabase();
        long e = sql.insert(db.TABLE_LOGIN_SETTING, null, contentValues);
        Log.e("", "Setting table value : " + e);
        int i = (int) e;
        SharedPreferences sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(WebUrlClass.MyPREFERENCES_SETTING_KEY, uniqueID);
        editor.commit();
        return i;

    }

    private String getPlantID(String plantName) {
        String data = "";
        SQLiteDatabase Sql = db.getWritableDatabase();
        Cursor c = Sql.rawQuery("Select * from " + db.TABLE_PLANTMASTER + " where PlantName='" + plantName + "'", null);
        int Count = c.getCount();
        if (Count > 0) {
            c.moveToFirst();
            do {
                data = c.getString(c.getColumnIndex("PlantMasterId"));
            } while (c.moveToNext());
        }
        c.close();
        // Sql.close();

        return data;
    }

    public void regservicenonGPS(Context mcontext) {

       /* Intent serviceIntent = new Intent(mcontext, PaidLocationFusedLocationTracker1.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            ContextCompat.startForegroundService(this, serviceIntent);
        } else {
            mcontext.startService(serviceIntent);
        }
*/
         /* int itime;
         itime = 15;
         long aTime = 1000 * 60 * itime;
         Intent igpsalarm = new Intent(mcontext, FusedLocationTracker.class);
         PendingIntent piHeartBeatService = PendingIntent.getService(
                 mcontext, 0, igpsalarm, PendingIntent.FLAG_UPDATE_CURRENT);
         AlarmManager alarmManager = (AlarmManager) mcontext
                 .getSystemService(Context.ALARM_SERVICE);
         alarmManager.cancel(piHeartBeatService);
         alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                 System.currentTimeMillis(), aTime, piHeartBeatService);*/


    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

/*
    private void EnableGPSAutoMatically() {
        // GoogleApiClient googleApiClient = null;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) ActivityLogIn.this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) ActivityLogIn.this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            */
/*Intent i = getIntent();
                            if (!(i.hasExtra(WebUrlClass.INTENT_LOGIN_SCREEN_BACKFLAG))) {
                                if (getLogINCount()) {
                                    Intent intent = new Intent(ActivityLogIn.this, ActivityModuleSelection.class);

                                    startActivity(intent);
                                    finish();
                                }
                            }*//*

                            //toast("Success");
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            //  toast("GPS is not on");
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.

                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(ActivityLogIn.this, REQUEST_CODE);

                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //  toast("Setting change not allowed");
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }
*/


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == PERMISSION_ALL) {
          /*  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED *//*&&
                        grantResults[5] == PackageManager.PERMISSION_GRANTED*//*) {
                    BtnOk.setEnabled(false);
                    methodEnvirnment();
                }
            }else {*/
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[3] == PackageManager.PERMISSION_GRANTED /*&&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED*/){
                    BtnOk.setEnabled(false);
                    methodEnvirnment();
                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                System.out.println("Resultdata" + result);
                // splash();

                Intent i = getIntent();
                if (!(i.hasExtra(WebUrlClass.INTENT_LOGIN_SCREEN_BACKFLAG))) {
                    if (getLogINCount()) {
                        Intent intent = new Intent(ActivityLogIn.this, ClientListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        toast("Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void toast(String message) {
        try {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            //  splash();
        } catch (Exception ex) {
        }

    }


    private long value(String string) {
        string = string.trim();
        if (string.contains(".")) {
            final int index = string.lastIndexOf(".");
            return value(string.substring(0, index)) * 100 + value(string.substring(index + 1));
        } else {
            return Long.valueOf(string);
        }
    }

  /*  private void versionCheck() {
        Document doc = null;
        try {
            WebUrlClass.APP_CURRENT_VERSION = (getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            if (Constants.type == Constants.Type.Vwb) {
                doc = Jsoup.connect("https://play.google.com/store/apps/details?id="//com.stavigilmonitoring
                        + "vworkbench7.vritti.com.vworkbench7").get();

            } else if (Constants.type == Constants.Type.PM) {
                doc = Jsoup.connect("https://play.google.com/store/apps/details?id="//com.stavigilmonitoring
                        + "practice.vritti.com").get();
            }
            String AllStr = doc.text();
            String parts[] = AllStr.split("Current Version");
            String newparts[] = parts[1].split("Requires Android");
            //String divleftpart[] = ne
            WebUrlClass.APP_NEW_VERSION = newparts[0].trim();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (WebUrlClass.APP_NEW_VERSION != "" && !WebUrlClass.APP_NEW_VERSION.isEmpty()) {
            if (value(WebUrlClass.APP_CURRENT_VERSION) < value(WebUrlClass.APP_NEW_VERSION) || value(WebUrlClass.APP_CURRENT_VERSION) > value(WebUrlClass.APP_NEW_VERSION)) {
                final String Update_Message = "A new version " + WebUrlClass.APP_NEW_VERSION + " of application is now available on playstore";
                new Thread() {
                    public void run() {
                        ActivityLogIn.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast toast = Toast.makeText(ActivityLogIn.this, Update_Message, Toast.LENGTH_LONG);
                                TextView v = (TextView) toast.getView().findViewById(R.id.message);
                                if (v != null) v.setGravity(Gravity.CENTER);
                                toast.show();
                            }
                        });
                    }
                }.start();
                // Toast.makeText(SpalshActivity.this,",Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();


    }

    /*private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                //verifying the code
                //verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ActivityLogIn.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };*/


}

