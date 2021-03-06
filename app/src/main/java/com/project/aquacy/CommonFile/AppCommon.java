package com.project.aquacy.CommonFile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.util.ArrayList;

public class AppCommon {
    private static final String My_Preferences = "myPreferences";
    private static final String LastLocation_Value = "lastLocationValue";
    private static final String Job_Dispacher_start = "Job_Dispacher_start";
    private static final String Location_Object = "Location_Object";
    private static final String DateCount = "datecount";
    private static final String apiCount = "apiCount";
    private static final String Net_Connection = "internet connection";
    private static final String chatJson = "Chat_Jason";
    private static final String UnReadCount = "UnreadCount";
    private static final String SampleFinalList = "SampleFinalList";
    private static final String DeliveryBoy = "DeliveryBoy";
    private static final String IsStartLocation = "IsLocationStart";
    private static final String BioTime = "BIOTime";
    private static final String LogoutTime = "LogoutTime";
    public static AppCommon mInstance = null;
    static Context mContext;
    private String notificationCpunt = "Notification_count";
    private String enSampleLis = "ENo_Sample_List";
    private static final String TextSize = "TEXT_SIZE";
    private static final String ThemeImg = "ThemeImg";
    private String userImageUri = "UserImageUri";
    private String ActivityId = "ACTIVITY_ID";
    private String notificationFlag = "NotificationFlag";
    private String currentDate = "CurrentDate";
    private String workingTime = "WorkingHour";
    private String isSMS = "IS_SMS";
    private String VendorContact = "VendorContact";
    private String VandorLat = "VandorLat";
    private String VandorLong = "VandorLong";
    private String VandorName = "VandorName";
    private String BillNo_print = "BillNO";
    private String RowCnt = "ROWS";
    private static final String BillingObject = "BillingObject";
    private String UserId = "UserId";
    private String IsGpsEnable = "IsGpsEnable";
    private String StrUrl = "CompanyURL";

    public static AppCommon getInstance( Context context){
        if (mInstance ==null){
            mInstance = new AppCommon();
        }
        mContext = context;
        return mInstance;
    }
    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public void setLastLocationTime(long mSec){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putLong(LastLocation_Value , mSec);
        editor.apply();
    }

    public long getLastLocationValue(){
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getLong(LastLocation_Value , -1L);
    }

    public void setServiceStarted(boolean value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putBoolean(Job_Dispacher_start , value);
        editor.apply();
    }

    public boolean isServiceIsStart(){
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Job_Dispacher_start , false);
    }

    public void setLastLocationObject(String locationStaring) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(Location_Object , locationStaring);
        editor.apply();
    }

    public String getLastLocationObject() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(Location_Object  , null);
    }

    public void setClaimDateList(String toJson) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(DateCount , toJson);
        editor.apply();
    }

    public String getClaimDateList() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(DateCount  , null);
    }

    public void onHideKeyBoard(Activity activity){
        InputMethodManager imn = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null){
            view = new View(activity);
        }
        imn.hideSoftInputFromInputMethod(view.getWindowToken() ,0);
    }

    public void showHideKeyBoard(Activity activity){
        InputMethodManager imn = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null){
            view = new View(activity);
        }
        imn.toggleSoftInputFromWindow(view.getWindowToken() ,InputMethodManager.SHOW_FORCED, 0);


        /*InputMethodManager inputMethodManager =
    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
inputMethodManager.toggleSoftInputFromWindow(
    linearLayout.getApplicationWindowToken(),
    InputMethodManager.SHOW_FORCED, 0);*/
    }

    public void IsConnected(boolean connection) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putBoolean(Net_Connection , connection);
        editor.apply();
    }

    public boolean getConnectionStatus() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(Net_Connection  , false);
    }

    public void setChatJson(String finalJson) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(chatJson , finalJson);
        editor.apply();
    }

    public String getChatJson() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(chatJson  , null);
    }

    public int getNotificationCount() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(notificationCpunt  , 0);
    }

    public void setNotificationCount(int i) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putInt(notificationCpunt , i);
        editor.apply();
    }

    public void setUnReadCount(String count) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(UnReadCount , count);
        editor.apply();
    }

    public String getUnReadCount() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(UnReadCount  , "0");
    }

    public void setEnoSampelList(String samplePojoClassArrayList) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(enSampleLis , samplePojoClassArrayList);
        editor.apply();
    }

    public String getEnoSampelList() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        //return mSharedPreferences.getString(enSampleLis  , new Gson().toJson(new ListObjectForEno()));
        return mSharedPreferences.getString(enSampleLis  ,null);
    }

    public void setTextSize(int textSize) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putInt(TextSize , textSize);
        editor.apply();
    }

    public int getTextSize() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(TextSize  , 1);
    }

    public void setThemeURI(String uri) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(ThemeImg , uri);
        editor.apply();
    }
    public String getThemeURI() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(ThemeImg  , null);
    }

    public void setFilnalListSampl(String toJson) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(SampleFinalList , toJson);
        editor.apply();
    }
    public String getFilnalListSampl() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(SampleFinalList  , null);
    }

    public void setImageUrl(String imagePath1) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(userImageUri , imagePath1);
        editor.apply();
    }

    public String  getImageUrl() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(userImageUri  , null);
    }



    public void setChatPostion(int i) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putInt(ActivityId , i);
        editor.apply();
    }
    public int  getChatPostion() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(ActivityId  , 0);
    }

    public void setNotificationFlag(boolean flag) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putBoolean(notificationFlag , flag);
        editor.apply();
    }
    public boolean  getNotificationFlag() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(notificationFlag  , false);
    }

    public String getCurrentDate() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(currentDate  , "");
    }
    public void setCurrentDate(String formattedDate) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(currentDate , formattedDate);
        editor.apply();
    }
    public String getWorkingHour() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(workingTime  , "8.01");
    }
    public void setWorkingHour(String formattedDate) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(workingTime , formattedDate);
        editor.apply();
    }
    public String isDeliveryBoy() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(DeliveryBoy  , "false");
    }
    public void setDeliveryBoy(String formattedDate) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(DeliveryBoy , formattedDate);
        editor.apply();
    }

    public boolean isStartLocation() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(IsStartLocation  , false);
    }

    public void setStartLocation(boolean isStartLocation) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putBoolean(IsStartLocation , isStartLocation);
        editor.apply();
    }

    public void setStartBioTime(String abc) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(BioTime, abc);
        editor.apply();
    }
    public String getBioLocation() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(BioTime  , "");
    }

    public void setLogOutTime(String logoutTime) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(LogoutTime, logoutTime);
        editor.apply();
    }
    public String getLogOutTime() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(LogoutTime  , null);
    }

    public void setSMS_Servive(boolean b, String contactNo, String latitude, String longitude , String vendorName) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putBoolean(isSMS, b);
        editor.putString(VendorContact, contactNo);
        editor.putString(VandorLat, latitude);
        editor.putString(VandorLong, longitude);
        editor.putString(VandorName, vendorName);
        editor.apply();
    }
    public String getvendorContactNumber() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(VendorContact  , null);
    }
    public String getVendorLatitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(VandorLat  , null);
    } public String getVendorLongitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(VandorLong  , null);
    } public String getvendorName() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(VandorName  , null);
    }
    public Boolean IsSMS() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(isSMS  , false);
    }

    public void setBillingObject(String billingObj) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(BillingObject , billingObj);
        editor.apply();
    }

    public String getBillingObject() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(BillingObject  , "");
    }

    public void setBillNo_print(String billNo_print) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(BillNo_print , billNo_print);
        editor.apply();
    }

    public String getBillNo_print() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(BillNo_print  , "");
    }

    public void setRowcnt(String billNo_print) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(RowCnt , billNo_print);
        editor.apply();
    }

    public String getRowCnt() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(RowCnt  , "");
    }

    public String getUserMasterId() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(UserId  , "");
    }

    public void setCompanyUrl(String sharedPreference_url) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(StrUrl , sharedPreference_url);
        editor.apply();
    }
    public String getCompanyUrl() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getString(StrUrl  , "");
    }
    public void setUserMasterId(String sharedPreference_userMasterID) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putString(UserId , sharedPreference_userMasterID);
        editor.apply();
    }

    public void setIsGpsLocationEnable(boolean b) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(My_Preferences ,Context.MODE_PRIVATE ).edit();
        editor.putBoolean(IsGpsEnable , b);
        editor.apply();
    }
    public boolean IsGpsLocationEnable() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(My_Preferences , Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(IsGpsEnable  , false);
    }
}
