package com.PocketMoney;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable{

    private static final long serialVersionUID = -8081303584624561024L;
    String name;
     double balance=0d;
     Map<String,Double> accounts=new HashMap();
     List<Operation> operations=new LinkedList();
     List<Outgoing> outgoings =new LinkedList();
     List<Transfer> transfers=new LinkedList();
     List<Income> incomes=new LinkedList();
     Set<String> categories=new HashSet<>();

    public boolean accountExists(String account){
        Set<String> keys=accounts.keySet();
        for (String acc:keys){
            if (acc.equals(account)){
                return true;
            }
        }
        return false;
    }
    public boolean categoryExists(String category){
        return categories.contains(category);
    }
    public Income getIncome(int index){
        return incomes.get(index);
    }
    public Outgoing getOutgoing(int index){
        return outgoings.get(index);
    }
    public Transfer getTransfer(int index){
        return transfers.get(index);
    }
    public Set<String> getAccountsKeys(){
        return accounts.keySet();
    }
    public double getAccountBalance(String account) throws IllegalArgumentException{
        if (!accounts.containsKey(account)) throw new IllegalArgumentException();
        return accounts.get(account);
    }
    public Map<String,Double> getOutgoingsCategories(){

        Map<String,Double> categories=new HashMap<>();
        for (String goal:this.categories){
            categories.put(goal,0d);
        }
        for (Outgoing outgoing:outgoings){
            if (categories.containsKey(outgoing.goal))
            categories.replace(outgoing.goal,outgoing.sum+categories.get(outgoing.goal));
        }
        return categories;
    }

    public void addCategory(String category) throws IllegalArgumentException{
        if (categories.contains(category)) throw new IllegalArgumentException();
        categories.add(category);
    }

    public void addOutgoing(double price, String goal, String account) throws IllegalArgumentException{
        if (!categories.contains(goal)) throw new IllegalArgumentException();
         Outgoing temp=new Outgoing(price,goal,account);
         outgoings.add(temp);
         operations.add(temp);
         balance-=price;
         accounts.replace(account,accounts.get(account)-price);
     }
     public void addOutgoing(double price,String goal,String account,String comment) throws IllegalArgumentException{
         if (!categories.contains(goal)) throw new IllegalArgumentException();
         Outgoing temp=new Outgoing(price,goal,account,comment);
         outgoings.add(temp);
         operations.add(temp);
         balance-=price;
         accounts.replace(account,accounts.get(account)-price);
     }
    public void transfer(String accountOut,String accountIn,double sum){
         Transfer temp=new Transfer(accountOut,accountIn,sum);
        transfers.add(temp);
        operations.add(temp);
        accounts.replace(accountOut,accounts.get(accountOut)-sum);
        accounts.replace(accountIn, accounts.get(accountIn)+sum);
    }
    public void transfer(String accountOut,String accountIn,double sum, String comment){
         Transfer temp=new Transfer(accountOut,accountIn,sum,comment);
        transfers.add(temp);
        operations.add(temp);
        accounts.replace(accountOut,accounts.get(accountOut)-sum);
        accounts.replace(accountIn,accounts.get(accountIn)+sum);
    }
    public void addIncome(String source, String account, double sum){
         Income temp=new Income(source,account,sum);
         incomes.add(temp);
         operations.add(temp);
         balance+=sum;
         accounts.replace(account,accounts.get(account)+sum);
    }
    public void addIncome(String source, String account, double sum, String comment){
         Income temp=new Income(source,account,sum,comment);
        incomes.add(temp);
        operations.add(temp);
        balance+=sum;
        accounts.replace(account,accounts.get(account)+sum);
    }
    public void addAccount(String name){
         accounts.put(name,0d);
    }

    public void deleteIncome(int index){
        if (index<0 || index>=incomes.size() || incomes.size()==0) throw new IllegalArgumentException();
        else
        {
            double sum=incomes.get(index).sum;
            String account=incomes.get(index).account;
            if (accountExists(account)) {
                accounts.replace(account, accounts.get(account) - sum);
                balance -= sum;
            }
            operations.remove(incomes.get(index));
            incomes.remove(index);
        }
    }

    public void deleteOutgoing(int index){
        if (index<0 || index>=outgoings.size()) throw new IllegalArgumentException();
        else
        {
            double sum=outgoings.get(index).sum;
            String account=outgoings.get(index).account;
            if (accountExists(account)) {
                accounts.replace(account, accounts.get(account) + sum);
                balance += sum;
            }
            operations.remove(outgoings.get(index));
            outgoings.remove(index);
        }
    }

    public void deleteTransfer(int index) throws IllegalArgumentException{
        if (index<0 || index>=transfers.size()) throw new IllegalArgumentException();
        else
        {
            double sum=transfers.get(index).sum;
            String accountIn=transfers.get(index).accountIn;
            String accountOut=transfers.get(index).accountOut;
            if (accountExists(accountIn) && accountExists(accountOut)) {
                accounts.replace(accountIn, accounts.get(accountIn) - sum);
                accounts.replace(accountOut, accounts.get(accountOut) + sum);
            }
            operations.remove(transfers.get(index));
            transfers.remove(index);
        }
    }

    public void deleteAccount(String account) throws IllegalArgumentException{
        if (!accountExists(account)) throw new IllegalArgumentException();
        else{
            balance-=accounts.get(account);
            accounts.remove(account);
        }
    }

    public void deleteCategory(String category) throws IllegalArgumentException{
        if (!categoryExists(category)) throw new IllegalArgumentException();
        else{
            categories.remove(category);
        }
    }

    public void setIncomeSource(int index, String source){
        incomes.get(index).source=source;
    }

    public void setIncomeAccount(int index, String account) throws IllegalArgumentException{
        Double sum=((Income)incomes.get(index)).sum;
        String oldAccount=((Income)incomes.get(index)).account;
        accounts.replace(oldAccount,(double)accounts.get(oldAccount)-sum);
        accounts.replace(account,(double)accounts.get(account)+sum);
        incomes.get(index).account=account;
    }
    public void setIncomeSum(int index,double sum){
        String account=incomes.get(index).account;
        Double oldSum=incomes.get(index).sum;
        balance=balance-oldSum+sum;
        accounts.replace(account,(double)accounts.get(account)-oldSum+sum);
        incomes.get(index).sum=sum;
    }


    public void setOutgoingAccount(int index, String account) throws IllegalArgumentException{
        Double sum=outgoings.get(index).sum;
        String oldAccount=outgoings.get(index).account;
        accounts.replace(oldAccount,(double)accounts.get(oldAccount)+sum);
        accounts.replace(account,(double)accounts.get(account)-sum);
        outgoings.get(index).account=account;
    }

    public void setOutgoingGoal(int index, String goal){
        outgoings.get(index).goal=goal;
    }

    public void setOutgoingSum(int index, double sum){
        String account=outgoings.get(index).account;
        Double oldSum=outgoings.get(index).sum;
        balance=balance+oldSum-sum;
        accounts.replace(account,(double)accounts.get(account)+oldSum-sum);
        outgoings.get(index).sum=sum;
    }

    public void setTransferIn(int index, String accountIn) throws IllegalArgumentException {
        Double sum=transfers.get(index).sum;
        String oldAccountIn=transfers.get(index).accountIn;
        accounts.replace(oldAccountIn,(double)accounts.get(oldAccountIn)-sum);
        accounts.replace(accountIn,(double)accounts.get(accountIn)+sum);
        transfers.get(index).accountIn=accountIn;
    }

    public void setTransferOut(int index, String accountOut) throws IllegalArgumentException{
        Double sum=transfers.get(index).sum;
        String oldAccountOut=transfers.get(index).accountOut;
        accounts.replace(oldAccountOut,(double)accounts.get(oldAccountOut)+sum);
        accounts.replace(accountOut,(double)accounts.get(accountOut)-sum);
        transfers.get(index).accountOut=accountOut;
    }

    public void setTransferSum(int index, double sum){
        String accountIn=transfers.get(index).accountIn;
        String accountOut=transfers.get(index).accountOut;
        Double oldSum=transfers.get(index).sum;
        accounts.replace(accountIn,(double)accounts.get(accountIn)-oldSum+sum);
        accounts.replace(accountOut,(double)accounts.get(accountOut)+oldSum-sum);
        transfers.get(index).sum=sum;
    }

    public int getIncomesSize() {
        return incomes.size();
    }

    public int getOutgoingsSize(){
        return outgoings.size();
    }

    public int getTransfersSize(){
        return transfers.size();
    }


    public User(String name) {
        this.name = name;
        accounts.put("Card",0d);
        accounts.put("Cash",0d);
        categories.add("Family");
        categories.add("Entertainment");
        categories.add("Health");
        categories.add("Food");
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
