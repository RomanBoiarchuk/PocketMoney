package com.PocketMoney;

public class Income extends Operation {
    private static final long serialVersionUID = -4279840767029269881L;
    String source;
    String account;

    public Income(String source, String account, double sum) {
        super(sum);
        this.source = source;
        this.account = account;
    }

    public Income(String source, String account, double sum, String comment) {
        super(sum,comment);
        this.source = source;
        this.account = account;
    }
    public String getSource() {
        return source;
    }

    public void setUnknownAccount(){
        account="Unknown";
    }

    public String getAccount() {
        return account;
    }
}
