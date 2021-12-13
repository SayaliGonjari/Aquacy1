package com.project.aquacy.CommonFile;

public class WebUrlClass {

    public static final String APP_URL_PM = "http://vritti1.simplifypractice.com";
    public static final String PREFERENCE_DATA_CALCULATION = "prefrencedataused";// Appication data Usage calculation
    public static final String PREFERENCE_CONSUMEDDATA_KEY = "consumeddata";

    public static String APP_CURRENT_VERSION = "", APP_NEW_VERSION = "";


    public static final String USERINFO = "UserInfo"; // usermaster preference
    public static final String USERINFO_YESTERDAY_DATE = "UserInfo_yesdate";
    public static final String USERINFO_OFF_DAY = "UserInfo_offday";
    public static final String USERINFO_SHORTCUTADGER_COUNT = "UserInfo_ShortcutBadger";
    public static final String USERINFO_TIMESHEET_ISTIMESlOT = "IsTimeslotBooked";
    public static final String USERINFO_USER_TYPE = "usertype";
    public static final String USERINFO_ISCOLLECTION_APPLICABLE = "IsSalesModule";


    public static final String MyPREFERENCES = "LoggingPrefs";// login preference
    public static final String MyPREFERENCES_URL_KEY = "Url";
    public static final String MyPREFERENCES_EnvMasterID_KEY = "EnvMasterID";
    public static final String MyPREFERENCES_PlantID_KEY = "Plantidkey";
    public static final String MyPREFERENCES_PlantName_KEY = "Plantnamekey";
    public static final String MyPREFERENCES_LOGIN_KEY = "login";
    public static final String MyPREFERENCES_PSW_KEY = "psw";
    public static final String MyPREFERENCES_MOBILE_KEY = "Mobile";
    public static final String MyPREFERENCES_SETTING_KEY = "setting";
    public static final String MyPREFERENCES_SETTING_POSITION_KEY = "settingKRY";
    public static final String MyPREFERENCES_USERMASTER_ID_KEY = "UserMasterId";
    public static final String MyPREFERENCES_USERNAME_KEY = "UserName";
    public static final String MyPREFERENCES_Designation_KEY = "Designation";
    public static final String MyPREFERENCES_Material_KEY = "Designation";


    public static final String MyPREFERENCES_IS_CHAT_APPLICABLE_KEY = "chatapplica";
    public static final String MyPREFERENCES_IS_GPS_LOCATION_KEY = "gpsloc";
    public static final String MyPREFERENCES_FIREBASE_TOKEN_KEY = "firbasetoken";
    public static final String MyPREFERENCES_IS_CRMUSER_KEY = "crmuser";
    public static final String INTENT_LOGIN_SCREEN_BACKFLAG = "intent_flag";
    public static final String VALUE_LOGIN_SCREEN_BACKFLAG = "1";



    public static final String api_checkEnv = "/api/LoginAPI/CheckAppEnvmaster";
    public static final String api_getPlants = "/api/LoginAPI/GetPlants";
    public static final String api_getEnv = "/api/LoginAPI/GetEnvis";
    public static final String api_GetIsValidUser = "/api/LoginAPI/GetIsValidUser";

    public static final String api_GetSessions = "/api/LoginAPI/GetSessions";
    public static final String setError = "error";

    public static final String IMAGE_DIRECTORY_EKATM = "EkatmDCIM";
    public static final String IMAGE_DIRECTORY_PM = "PMDCIM";

    public static final String AppNameFCM_EKATM = "Aquacy";

    public static final String FCMurl = "/api/PushNotificationAPI/PostDeviceMaster";
    public static final String api_getIfCRMUser = "/api/MyClaimAPI/getIfCRMUser";

    public static final String api_GetUserMasterIdAndroid = "/api/TimesheetAPI/GetUserMasterIdForAndroid";
    public static final String api_GetOTPServer = "/api/GenerateOTPAPI/GetGenerateOTP";
    public static final String api_getEnvPM = "/api/LoginAPI/GetChatApplicable";

    public static final String GET_DATABASE_NAME_KEY = "DatabaseName";
    public static final String GET_COMPANY_URL_KEY = "CompanyURL";
    public static final String GET_EnvMasterID_KEY = "EnvId";
    public static final String GET_PlantID_KEY = "PlantID";
    public static final String GET_PlantName_KEY = "PlantName";
    public static final String GET_LOGIN_KEY = "UserLogInId";
    public static final String GET_PSW_KEY = "Password";


    public static  final String api_PostEntity = "/api/AC_EntityMasterAPI/PostEntity";
    public static  final String api_GetData = "/api/AC_EntityMasterAPI/GetEntity";
    public static  final String GETPassword = "/ForgotPass/GETPassword";
    public static  final String CheckWhatsAppNo = "/api/AC_EntityMasterAPI/CheckWhatsAppNo";
   // public static  final String api_GetData = "/api/AC_EntityMasterAPI/GetEntity?id=07b0be30-27e0-49cc-a3ea-2687c8797040";
    public static final String Errormsg = "error";

}
