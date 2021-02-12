package com.example.ues_finalversion.CreateNewOrder;

import android.widget.RadioButton;


public class Order {
    private String name;
    private String transport;
    private String spinnerOnFoot;
    private String spinnerOnFAuto;
    private String adressNum1;
    private String time1;
    private String Date1;
    private String phone1;
    private String comment1;
    private String price;
    private String payment;

    private String adressNum2;
    private String time2;
    private String Date2;
    private String phone2;
    private String comment2;
    private String numberIN;
    private String spinner;
    private String spinner2;


    private String whatTaking;


    public Order() {
    }

    public Order(String name, String transport, String spinnerOnFoot, String spinnerOnFAuto,
                 String adressNum1, String time1, String date1, String phone1, String comment1,
                 String price, String payment, String adressNum2,
                 String time2, String date2, String phone2, String comment2,
                 String numberIN, String spinner, String spinner2, String whatTaking) {
        this.name = name;
        this.transport = transport;
        this.spinnerOnFoot = spinnerOnFoot;
        this.spinnerOnFAuto = spinnerOnFAuto;
        this.adressNum1 = adressNum1;
        this.time1 = time1;
        Date1 = date1;
        this.phone1 = phone1;
        this.comment1 = comment1;
        this.price = price;
        this.payment = payment;
        this.adressNum2 = adressNum2;
        this.time2 = time2;
        Date2 = date2;
        this.phone2 = phone2;
        this.comment2 = comment2;
        this.numberIN = numberIN;
        this.spinner = spinner;
        this.spinner2 = spinner2;
        this.whatTaking = whatTaking;
    }

    public String getSpinnerOnFoot() {
        return spinnerOnFoot;
    }

    public void setSpinnerOnFoot(String spinnerOnFoot) {
        this.spinnerOnFoot = spinnerOnFoot;
    }

    public String getSpinnerOnFAuto() {
        return spinnerOnFAuto;
    }

    public void setSpinnerOnFAuto(String spinnerOnFAuto) {
        this.spinnerOnFAuto = spinnerOnFAuto;
    }

    public String getAdressNum1() {
        return adressNum1;
    }

    public void setAdressNum1(String adressNum1) {
        this.adressNum1 = adressNum1;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getDate1() {
        return Date1;
    }

    public void setDate1(String date1) {
        Date1 = date1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getAdressNum2() {
        return adressNum2;
    }

    public void setAdressNum2(String adressNum2) {
        this.adressNum2 = adressNum2;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getDate2() {
        return Date2;
    }

    public void setDate2(String date2) {
        Date2 = date2;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getNumberIN() {
        return numberIN;
    }

    public void setNumberIN(String numberIN) {
        this.numberIN = numberIN;
    }

    public String getWhatTaking() {
        return whatTaking;
    }

    public void setWhatTaking(String whatTaking) {
        this.whatTaking = whatTaking;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getSpinner2() {
        return spinner2;
    }

    public void setSpinner2(String spinner2) {
        this.spinner2 = spinner2;
    }

    public String getName() {
        return name;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}






