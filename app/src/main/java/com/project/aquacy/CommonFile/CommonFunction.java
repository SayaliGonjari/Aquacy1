package com.project.aquacy.CommonFile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.aquacy.Adapter.ClientListAdapter;
import com.project.aquacy.Database.DatabaseHandlers;
import com.project.aquacy.Model.ClientDetailsBean;

public class CommonFunction {
    String CompanyURL, EnvMasterId = "", LoginId = "", Password = "", PlantMasterId = "";
    Utility ut;
    static DatabaseHandlers db;
    Context context;

    public CommonFunction(Context context) {

        ut = new Utility();
        this.context = context;
        String settingKey = ut.getSharedPreference_SettingKey(context);
        String dabasename = ut.getValue(context, WebUrlClass.GET_DATABASE_NAME_KEY, settingKey);
        db = new DatabaseHandlers(context, dabasename);
        CompanyURL = ut.getValue(context, WebUrlClass.GET_COMPANY_URL_KEY, settingKey);
        EnvMasterId = ut.getValue(context, WebUrlClass.GET_EnvMasterID_KEY, settingKey);
        LoginId = ut.getValue(context, WebUrlClass.GET_LOGIN_KEY, settingKey);
        Password = ut.getValue(context, WebUrlClass.GET_PSW_KEY, settingKey);
        PlantMasterId = ut.getValue(context, WebUrlClass.GET_PlantID_KEY, settingKey);
    }

    public int getGetClientItemcount() {
        String countQuery = "SELECT  * FROM "
                + db.TABLE_CLIENT_DATALIST;
        int count = 0;
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery(countQuery, null);
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void AddClientDetails(ClientDetailsBean clientDetailsBean) {
        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CustVendorMasterId",clientDetailsBean.getCustVendorMasterId());
        contentValues.put("CustVendorName",clientDetailsBean.getCustVendorName());
        contentValues.put("Address",clientDetailsBean.getAddress());
        contentValues.put("Mobile",clientDetailsBean.getMobile());
        contentValues.put("Email",clientDetailsBean.getEmail());
        contentValues.put("MeterType",clientDetailsBean.getMeterType());
        contentValues.put("ConnNo",clientDetailsBean.getConnNo());
        contentValues.put("MeterNo ",clientDetailsBean.getMeterNo());
        contentValues.put("ConnectionType",clientDetailsBean.getConnectionType());
        long a = sql.insert(db.TABLE_CLIENT_DATALIST, null, contentValues);
        Log.e("data", String.valueOf(a));


    }
    public void UpdateExpenseDetails(ClientDetailsBean clientDetailsBean, String id) {
        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CustVendorMasterId",clientDetailsBean.getCustVendorMasterId());
        contentValues.put("CustVendorName",clientDetailsBean.getCustVendorName());
        contentValues.put("Address",clientDetailsBean.getAddress());
        contentValues.put("Mobile",clientDetailsBean.getMobile());
        contentValues.put("Email",clientDetailsBean.getEmail());
        contentValues.put("MeterType",clientDetailsBean.getMeterType());
        contentValues.put("ConnNo",clientDetailsBean.getConnNo());
        contentValues.put("MeterNo ",clientDetailsBean.getMeterNo());
        contentValues.put("ConnectionType",clientDetailsBean.getConnectionType());
        long a = sql.update(db.TABLE_CLIENT_DATALIST,contentValues, "CustVendorMasterId=?", new String[]{id});
        Log.e("data", String.valueOf(a));
    }

    public void DeleteExpenseDetails(String id) {
        SQLiteDatabase sql = db.getWritableDatabase();
        long a = sql.delete(db.TABLE_CLIENT_DATALIST, "CustVendorMasterId=?", new String[]{id});
        Log.e("data", String.valueOf(a));
       // sql.delete(db.TABLE_ACTIVITYMASTER, "ActivityId=?", new String[]{ActivityId});
    }


}
