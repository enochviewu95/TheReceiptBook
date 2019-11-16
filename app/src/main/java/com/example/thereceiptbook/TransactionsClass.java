package com.example.thereceiptbook;

public class TransactionsClass {
    private int[] id;
    private String[] transactions;
    private String fullName;
    private String[] date;
    private String phoneNumber;

    public TransactionsClass(int[] id,String fullName,String phoneNumber,String[] transactions,String[] date){
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.transactions = transactions;
        this.date = date;
    }

    public int[] getId() {
        return id;
    }

    public String[] getTransactions() {
        return transactions;
    }

    public String[] getDate() {
        return date;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
