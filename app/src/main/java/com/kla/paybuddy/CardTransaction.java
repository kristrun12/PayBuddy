package com.kla.paybuddy;

import android.support.annotation.XmlRes;

import java.util.Date;

/**
 * Created by kla on 20.2.2015.
 */
public class CardTransaction
{
    private Date date = new Date();
    private String accountNumber;
    private Number amount;

    public CardTransaction(String accountNumber, Number amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }



}
