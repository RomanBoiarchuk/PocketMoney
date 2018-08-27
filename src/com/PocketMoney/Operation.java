package com.PocketMoney;

import java.io.Serializable;
import java.util.Date;

abstract public class Operation implements Serializable {

    private static final long serialVersionUID = -2477310783152704532L;
    double sum;
    String comment;
    Date date;

    public Operation(double sum) {
        this.sum = sum;
        comment=null;
        date=new Date();
    }

    public Operation(double sum, String comment) {
        this.sum = sum;
        this.comment = comment;
        date=new Date();
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getSum() {
        return sum;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    abstract void print();
    }


