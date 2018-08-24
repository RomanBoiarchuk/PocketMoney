package com.PocketMoney;


import java.io.Serializable;
import java.util.Date;

public class Transfer extends Operation{
    private static final long serialVersionUID = -5436907653167685370L;
    String accountOut;
    String accountIn;

    public Transfer(String accountOut, String accountIn, double sum, String comment) {
        super(sum,comment);
        this.accountOut = accountOut;
        this.accountIn = accountIn;
    }

    public Transfer(String accountOut, String accountIn, double sum) {
        super(sum);
        this.accountOut = accountOut;
        this.accountIn = accountIn;
    }
    void print(){
        System.out.print(date+": from "+accountOut+" to "+accountIn+": "+sum+" UAH; ");
        if (comment!=null){
            System.out.print("comment: "+comment);
        }
        System.out.println();
    }
    public void setUnknownAccountIn(){
        accountIn="Unknown";
    }
    public void setUnknownAccountOut(){
        accountOut="Unknown";
    }

    public String getAccountOut() {
        return accountOut;
    }

    public String getAccountIn() {
        return accountIn;
    }
}

