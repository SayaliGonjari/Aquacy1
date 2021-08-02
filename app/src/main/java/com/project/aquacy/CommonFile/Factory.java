package com.project.aquacy.CommonFile;

import com.project.aquacy.Database.DatabaseHandlers;

public class Factory {

    public static final String CREATE_TABLE_LOGIN_SETTING = "CREATE TABLE " + DatabaseHandlers.TABLE_LOGIN_SETTING +
            "(LogInKey TEXT,CompanyURL TEXT,EnvId TEXT,PlantID TEXT," +
            "PlantName TEXT,UserLogInId TEXT,UserMasterId TEXT,UserName TEXT," +
            "Password TEXT,Mobile TEXT,DatabaseName TEXT," +
            "IsCRMuser TEXT,IsChatApplicable TEXT,IsGpsLocation TEXT," +
            "BackDateTimesheet TEXT,AndroidId TEXT,IMEINumber TEXT,FCMToken TEXT,Designation Text,Material TEXT)";


    public static final String CREATE_TABLE_PLANTMASTER = "CREATE TABLE " + DatabaseHandlers.TABLE_PLANTMASTER
            + "(PlantMasterId TEXT,PlantName TEXT)";


    public static final String CREATE_CLIENT_DATALIST = "CREATE TABLE " + DatabaseHandlers.TABLE_CLIENT_DATALIST
            + "(CustVendorMasterId TEXT," +
             "CustVendorName TEXT," +
            "Address TEXT," +
            "Mobile TEXT," +
            "Email TEXT," +
            "MeterType TEXT," +
            "ConnNo TEXT," +
            "MeterNo TEXT," +
            "ConnectionType TEXT" +
            ")";




}
