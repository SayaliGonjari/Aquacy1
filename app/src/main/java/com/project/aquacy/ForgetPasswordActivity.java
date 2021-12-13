package com.project.aquacy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.aquacy.CommonFile.CallbackInterface;
import com.project.aquacy.CommonFile.CommonFunction;
import com.project.aquacy.CommonFile.StartSession;
import com.project.aquacy.CommonFile.Utility;
import com.project.aquacy.CommonFile.WebUrlClass;
import com.project.aquacy.Database.DatabaseHandlers;

import me.leolin.shortcutbadger.ShortcutBadger;

public class ForgetPasswordActivity extends AppCompatActivity {
    String PlantMasterId = "", LoginId = "", Password = "", EnvMasterId = "",
            UserMasterId = "", UserName = "", MobileNo = "", clientname = "", clientid = "", contactno = "", contactid = "", id1 = "",
            CompanyURL = "";

    DatabaseHandlers db;
    SQLiteDatabase sql;
    CommonFunction cf;
    Context context;
    SharedPreferences userpreferences;
    Utility ut;
    EditText edLoginId;
    EditText edEmail;
    Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        InitView();

        SetListner();


    }

    private void SetListner() {
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String loginId = edLoginId.getText().toString();
                final String emailId = edEmail.getText().toString();

                if (ut.isNet(ForgetPasswordActivity.this)) {
                    new ForgotPasswordRequest().execute(loginId, emailId);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        edLoginId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              //  edLoginId.setVisibility(View.GONE);
                if(!(s.toString().equals(""))) {
                    edEmail.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    edEmail.setEnabled(false);
                }else{
                    edEmail.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text));
                    edEmail.setEnabled(true);
                }
            }
        });

        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))) {
                    // edEmail.setVisibility(View.GONE);
                    edLoginId.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
                    edLoginId.setEnabled(false);
                }else{
                    edLoginId.setBackground(ContextCompat.getDrawable(context, R.drawable.edit_text));
                    edLoginId.setEnabled(true);
                }
            }
        });

    }

    private void InitView() {

        context = getApplicationContext();
        ut = new Utility();
        cf = new CommonFunction(context);
        db = new DatabaseHandlers(context);
        // sharedpreferences = getSharedPreferences(WebUrlClass.MyPREFERENCES, MODE_PRIVATE);
        userpreferences = getSharedPreferences(WebUrlClass.USERINFO,
                Context.MODE_PRIVATE);
        ShortcutBadger.with(getApplicationContext()).remove();
        SharedPreferences.Editor editor = userpreferences.edit();
        editor.remove(WebUrlClass.USERINFO_SHORTCUTADGER_COUNT);
        editor.commit();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        edLoginId = findViewById(R.id.edLoginId);
        edEmail = findViewById(R.id.edEmail);
        btnRequest = findViewById(R.id.btnRequest);

    }

    private class ForgotPasswordRequest extends AsyncTask<String, Void, String> {

        String response = "";
        Object res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String logi = params[0];
            String email = params[1];
            String url = ut.getSharedPreference_URL(context) + WebUrlClass.GETPassword + "?txtEmail=" + email + "&userId=" + logi;

            res = ut.OpenConnection(url, ForgetPasswordActivity.this);
            try {
                if (res != null) {
                    response = res.toString().replaceAll("\\\\", "");

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

            if (response.equals("OK")) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                finish();
            } else if (response.equalsIgnoreCase("Entered UserId does not exist")) {
                Toast.makeText(ForgetPasswordActivity.this, "Entered UserId does not exist", Toast.LENGTH_SHORT).show();
            }else if(response.equalsIgnoreCase("Email address is not registered")){
                Toast.makeText(ForgetPasswordActivity.this, "Email address is not registered", Toast.LENGTH_SHORT).show();
            }
        }
    }
}