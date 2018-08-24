package com.PocketMoney;

import java.io.Serializable;
import java.util.Date;

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
         void print(){
        System.out.print(date+": from "+account+"; goal:  "+goal+": "+sum+" UAH; ");
        if (comment!=null){
            System.out.print("comment: "+comment);
        }
             System.out.println();
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
