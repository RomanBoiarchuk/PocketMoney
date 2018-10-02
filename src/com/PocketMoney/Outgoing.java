package com.PocketMoney;


    public class Outgoing extends Operation {
        private static final long serialVersionUID = 5610883753266549574L;
    String goal;
    String account;
    Outgoing(double sum, String goal, String account){
        super(sum);
        this.goal=goal;
        this.account=account;
    }
    Outgoing(double sum, String goal, String account, String comment){
        super(sum,comment);
        this.goal=goal;
        this.account=account;
    }
        public String getGoal() {
            return goal;
        }

        public String getAccount() {
            return account;
        }
       public void setUnknownCategory(){
            goal="Unknown";
        }
        public void setUnknownAccount(){
            account="Unknown";
        }
    }
