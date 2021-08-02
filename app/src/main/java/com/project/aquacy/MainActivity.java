package com.project.aquacy;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.aquacy.CommonFile.CallbackInterface;
import com.project.aquacy.CommonFile.CommonFunction;
import com.project.aquacy.CommonFile.StartSession;
import com.project.aquacy.CommonFile.Utility;
import com.project.aquacy.CommonFile.WebUrlClass;
import com.project.aquacy.Database.DatabaseHandlers;

import org.json.JSONException;
import org.json.JSONObject;

import static com.project.aquacy.ActivityLogIn.context;

public class MainActivity extends AppCompatActivity {

    String PlantMasterId = "", LoginId = "", Password = "", EnvMasterId = "",
            UserMasterId = "", UserName = "", MobileNo = "", clientname = "", clientid = "", contactno = "", contactid = "", id1 = "",
            CompanyURL = "";

    DatabaseHandlers db;
    SQLiteDatabase sdb;
    CommonFunction cf;
    Context context;
    SharedPreferences userpreferences;

    TextInputEditText edt_fname,edt_lname,edt_addr,edt_mobileNo,emailID,edt_connectionNo,edt_meterNo;
    Toolbar toolbar;
    Button btn_AMR,btn_noAMR,btn_resident,btn_commercial;
    int clickedConnType = -1;
    ImageView img_save;
    int clickedMType = -1;
    Utility ut;


    //if 1 then residential or commercial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        InitView();
        SetListner();

        /*{
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
}*/
    }

    private void InitView() {
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
       // ScratchWorkspaceName = userpreferences.getString("ScratchWorkspaceName", null);
      //  ScratchWorkspaceId = userpreferences.getString("ScratchWorkspaceId", null);
    }

    private void SetListner() {
        btn_commercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedConnType = 2;
                //if 1 then residential and 2 commercial
                if(clickedConnType == -1 || clickedConnType == 2){
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedConnType = 2;
                }else if(clickedConnType == 1){
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
                if(clickedConnType == -1 || clickedConnType == 2){
                    btn_commercial.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_resident.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedConnType = 2;
                }else if(clickedConnType == 1){
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
                if(clickedMType == -1 || clickedMType == 1){
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedMType = 1;
                }else if(clickedMType == 2){
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
                if(clickedMType == -1 || clickedMType == 1){
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    clickedMType = 1;
                }else if(clickedMType == 2){
                    btn_AMR.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    btn_noAMR.setBackground(ContextCompat.getDrawable(context, R.drawable.backshape_grey_selected));
                    clickedMType = 2;
                }
            }
        });

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String connType="";
                if(clickedConnType == 1){
                    connType = "Residential";
                }else if(clickedConnType == 2){
                    connType = "commercial";
                }else{
                    connType="";
                }

                String mType="";
                if(clickedMType == 1){
                    mType = "AMR";
                }else if(clickedMType == 2){
                    mType = "Non-AMR";
                }else{
                    mType="";
                }


                String entityName ="",addr="",mob = "",email="",connNo="",meterNo="",mode;
                        entityName = edt_fname.getText().toString().trim() +" "+ edt_lname.getText().toString().trim();
                        addr= edt_addr.getText().toString();
                        mob = edt_mobileNo.getText().toString();
                        email = emailID.getText().toString().trim();
                        meterNo = edt_meterNo.getText().toString();
                        connNo = edt_connectionNo.getText().toString();

                        if(entityName == null || entityName.equals("")){
                            Toast.makeText(MainActivity.this, "Please enter client name", Toast.LENGTH_SHORT).show();
                        }else if(addr == null || addr.equals("")){
                            Toast.makeText(MainActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
                        }else {

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("CustId", "");

                                jsonObject.put("entityname", entityName);
                                jsonObject.put("entityadd", addr);
                                jsonObject.put("mob", mob);
                                jsonObject.put("email", email);
                                jsonObject.put("meterno", meterNo);
                                jsonObject.put("Mode", "A");
                                jsonObject.put("metertype", mType);
                                jsonObject.put("ConnectionType", connType);
                                jsonObject.put("connectionno", connNo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            final String finalJosnObj = jsonObject.toString();

                            if (ut.isNet(MainActivity.this)) {

                                new StartSession(MainActivity.this, new CallbackInterface() {
                                    @Override
                                    public void callMethod() {
                                        new PostSaveData().execute(finalJosnObj);
                                    }

                                    @Override
                                    public void callfailMethod(String msg) {

                                    }
                                });

                            }else{
                                Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
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


    }

    private class PostSaveData extends AsyncTask<String,Void,String> {

        Object res;
        String response="";
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return  response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(response.equals("success")){
                Toast.makeText(MainActivity.this, "Client master Save successfully !!!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Client master not Save successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}