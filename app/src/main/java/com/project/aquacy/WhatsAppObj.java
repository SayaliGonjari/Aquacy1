package com.project.aquacy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WhatsAppObj implements Serializable {
    @SerializedName("mob")
    public String mobileNo;

    public WhatsAppObj(String s) {
        this.mobileNo = s;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
