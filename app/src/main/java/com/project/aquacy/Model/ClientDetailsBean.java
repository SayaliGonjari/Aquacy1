package com.project.aquacy.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientDetailsBean implements Serializable {
    String custVendorMasterId, custVendorName, address, mobile, email, meterType, meterNo, connNo, connectionType;
    ArrayList<ClientDetailsBean> clientDetailsBeanArrayList;
    int pos;

    public ClientDetailsBean(ArrayList<ClientDetailsBean> clientDetailsBeanArrayList) {
        this.clientDetailsBeanArrayList = clientDetailsBeanArrayList;
    }

    public ClientDetailsBean() {

    }

    public ClientDetailsBean(String custVendorMasterId, String custVendorName, String address,
                             String mobile, String email, String meterType, String connNo, String meterNo, String connectionType) {

        this.custVendorMasterId = custVendorMasterId;
        this.custVendorName = custVendorName;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.meterType = meterType;
        this.connNo = connNo;
        this.meterNo = meterNo;
        this.connectionType = connectionType;

    }

    public String getCustVendorMasterId() {
        return custVendorMasterId;
    }

    public void setCustVendorMasterId(String custVendorMasterId) {
        this.custVendorMasterId = custVendorMasterId;
    }

    public String getCustVendorName() {
        return custVendorName;
    }

    public void setCustVendorName(String custVendorName) {
        this.custVendorName = custVendorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getConnNo() {
        return connNo;
    }

    public void setConnNo(String connNo) {
        this.connNo = connNo;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public ArrayList<ClientDetailsBean> getClientDetailsBeanArrayList() {
        return clientDetailsBeanArrayList;
    }

    public void setClientDetailsBeanArrayList(ArrayList<ClientDetailsBean> clientDetailsBeanArrayList) {
        this.clientDetailsBeanArrayList = clientDetailsBeanArrayList;
    }
}
