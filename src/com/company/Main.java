package com.company;
import com.PocketMoney.*;
import javafx.print.Collation;

import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
//        User user=new User("Roman");
//        user.addIncome("Scholarship","Card",1300);
//        List<User> users=new ArrayList<>();
//        users.add(user);
//        writeUsersInFile(users);
        List<User> users=readUsersFromFile();
        User user;
        byte action;
        int index;
        String source,account,accountIn,accountOut,goal,comment,name;
        double sum;
        boolean exists;
        while (true){
            System.out.println("1-view users; 2-create new user; 3-delete user; 0-exit");
            action=readByte("action- ");
            switch (action){
                case 1:
                    while (true) {
                        System.out.println("User's index-open user menu; 0-back to the main menu");
                        for (int i=0;i<users.size();i++){
                            System.out.println(+i+1+") "+users.get(i).getName());
                        }
                        action=readByte("action- ");
                        if (action == 0) break;
                        else if (action <= users.size()) {
                            user = users.get(action - 1);
                            while (true){
                                System.out.print("1- operations; 2- accounts status; 3- balance; 4- incomes; " );
                                System.out.print("5- outgoings; 6- transfers; 7- change name; 8-create new operation; ");
                                System.out.println("9-add category; 0-back to users list");
                                action=readByte("action- ");
                                if (action==0) break;
                                switch (action) {
                                    case 1:
                                        user.printOperations();
                                        break;
                                    case 2:
                                        while (true){
                                            System.out.println("1-add account; 2-delete account; 0-back to users list");
                                            user.printAccounts();
                                            action=readByte("action- ");
                                            if (action==0) break;
                                            switch (action){
                                                case 1:
                                                    account=readLine("account name- ");
                                                    if (user.accountExists(account)){
                                                        System.out.println("Account already exists! ");
                                                    }
                                                    else
                                                    {
                                                        user.addAccount(account);
                                                    }
                                                    break;
                                                case 2:
                                                    account=readLine("account name- ");
                                                    if (user.accountExists(account)){
                                                        user.deleteAccount(account);
                                                        writeUsersInFile(users);
                                                    }
                                                    else
                                                    {
                                                        System.out.println("Account doesn't exist!");
                                                    }
                                                    break;
                                                default:
                                                    System.out.println("Incorrect action! ");
                                                    break;
                                            }
                                        }
                                        break;
                                    case 3:
                                        System.out.println("balance- "+user.getBalance()+ "UAH");
                                        break;
                                    case 4:
                                        while (true) {
                                            System.out.println("index-change operation; 0-back to user's menu");
                                            user.printIncomes();
                                            if (user.getIncomesSize() == 0) break;
                                            index = readInt("action- ");
                                            if (index == 0) break;
                                            else {
                                                if (index <= user.getIncomesSize()) {
                                                    System.out.println("1-delete; 2-edit; 0-back");
                                                    action = readByte("action- ");
                                                    if (action == 0) break;
                                                    switch (action) {
                                                        case 1:
                                                            user.deleteIncome(index - 1);
                                                            writeUsersInFile(users);
                                                            System.out.println("Operation has been deleted");
                                                            break;
                                                        case 2:
                                                            while (true) {
                                                                user.printIncome(index - 1);
                                                                System.out.println("Edit: 1- source; 2- account; 3-sum; 4-comment; 0-back");
                                                                action = readByte("action- ");
                                                                if (action == 0) break;
                                                                switch (action) {
                                                                    case 1:
                                                                        source = readLine("new source- ");
                                                                        user.setIncomeSource(index - 1, source);
                                                                        writeUsersInFile(users);
                                                                        break;
                                                                    case 2:
                                                                        account = readLine("new account- ");
                                                                        if (user.accountExists(account)) {
                                                                            user.setIncomeAccount(index - 1, account);
                                                                            writeUsersInFile(users);
                                                                        } else
                                                                            System.out.println("Error! Account doesn't exist!");
                                                                        break;
                                                                    case 3:
                                                                        sum = readDouble("new sum- ");
                                                                        user.setIncomeSum(index - 1, sum);
                                                                        writeUsersInFile(users);
                                                                        break;
                                                                    case 4:
                                                                        comment = readLine("new comment- ");
                                                                        if (comment.equals("")) {
                                                                            user.setIncomeComment(index - 1, null);
                                                                            writeUsersInFile(users);
                                                                        } else {
                                                                            user.setIncomeComment(index - 1, comment);
                                                                            writeUsersInFile(users);
                                                                        }
                                                                        break;
                                                                    default:
                                                                        System.out.println("Incorrect action! ");
                                                                        break;
                                                                }
                                                            }
                                                            break;
                                                        default:
                                                            System.out.println("Incorrect action!");
                                                            break;
                                                    }
                                                } else {
                                                    System.out.println("Incorrect action! ");
                                                }
                                            }
                                        }
                                        break;
                                    case 5:
                                        System.out.println("index-change operation; 0-back to user's menu");
                                        user.printOutgoings();
                                        if (user.getOutgoingsSize()==0) break;
                                        index=readInt("action- ");
                                        if (index==0) break;
                                        else{
                                            if (index<=user.getOutgoingsSize()){
                                                System.out.println("1-delete; 2-edit; 0-back");
                                                action=readByte("action- ");
                                                if (action==0) break;
                                                switch (action){
                                                    case 1:
                                                        user.deleteOutgoing(index-1);
                                                        writeUsersInFile(users);
                                                        System.out.println("Operation has been deleted");
                                                        break;
                                                    case 2:
                                                        while (true) {
                                                            user.printOutgoing(index - 1);
                                                            System.out.println("Edit: 1- account; 2- goal; 3-sum; 4-comment; 0-back");
                                                            action = readByte("action- ");
                                                            if (action == 0) break;
                                                            switch (action) {
                                                                case 2:
                                                                    goal = readLine("new goal- ");
                                                                    user.setOutgoingGoal(index - 1, goal);
                                                                    writeUsersInFile(users);
                                                                    break;
                                                                case 1:
                                                                    account = readLine("new account- ");
                                                                    if (user.accountExists(account)) {
                                                                        user.setOutgoingAccount(index - 1, account);
                                                                        writeUsersInFile(users);
                                                                    } else
                                                                        System.out.println("Error! Account doesn't exist!");
                                                                    break;
                                                                case 3:
                                                                    sum = readDouble("new sum- ");
                                                                    user.setOutgoingSum(index - 1, sum);
                                                                    writeUsersInFile(users);
                                                                    break;
                                                                case 4:
                                                                    comment = readLine("new comment- ");
                                                                    if (comment.equals("")) {
                                                                        user.setOutgoingComment(index - 1, null);
                                                                        writeUsersInFile(users);
                                                                    } else {
                                                                        user.setOutgoingComment(index - 1, comment);
                                                                        writeUsersInFile(users);
                                                                    }
                                                                    break;
                                                                default:
                                                                    System.out.println("Incorrect action! ");
                                                                    break;
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        System.out.println("Incorrect action!");
                                                        break;
                                                }
                                            }
                                            else{
                                                System.out.println("Incorrect action! ");
                                            }
                                        }
                                        break;
                                    case 6:
                                        System.out.println("index-change operation; 0-back to user's menu");
                                        user.printTransfers();
                                        if (user.getTransfersSize()==0) break;
                                        index=readInt("action- ");
                                        if (index==0) break;
                                        else{
                                            if (index<=user.getTransfersSize()){
                                                System.out.println("1-delete; 2-edit; 0-back");
                                                action=readByte("action- ");
                                                if (action==0) break;
                                                switch (action){
                                                    case 1:
                                                        user.deleteTransfer(index-1);
                                                        writeUsersInFile(users);
                                                        System.out.println("Operation has been deleted");
                                                        break;
                                                    case 2:
                                                        while (true) {
                                                            user.printTransfer(index - 1);
                                                            System.out.println("Edit: 1- income account; 2- outgoing account; 3-sum; 4-comment; 0-back");
                                                            action = readByte("action- ");
                                                            if (action == 0) break;
                                                            switch (action) {
                                                                case 1:
                                                                    accountIn = readLine("income account- ");
                                                                    if (user.accountExists(accountIn)) {
                                                                        user.setTransferIn(index - 1, accountIn);
                                                                        writeUsersInFile(users);
                                                                    } else
                                                                        System.out.println("Error! Account doesn't exist!");
                                                                    break;
                                                                case 2:
                                                                    accountOut = readLine("outgoing account- ");
                                                                    if (user.accountExists(accountOut)) {
                                                                        user.setTransferOut(index - 1, accountOut);
                                                                        writeUsersInFile(users);
                                                                    } else
                                                                        System.out.println("Error! Account doesn't exist!");
                                                                    break;
                                                                case 3:
                                                                    sum = readDouble("new sum- ");
                                                                    user.setTransferSum(index - 1, sum);
                                                                    writeUsersInFile(users);
                                                                    break;
                                                                case 4:
                                                                    comment = readLine("new comment- ");
                                                                    if (comment.equals("")) {
                                                                        user.setTransferComment(index - 1, null);
                                                                        writeUsersInFile(users);
                                                                    } else {
                                                                        user.setTransferComment(index - 1, comment);
                                                                        writeUsersInFile(users);
                                                                    }
                                                                    break;
                                                                default:
                                                                    System.out.println("Incorrect action! ");
                                                                    break;
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        System.out.println("Incorrect action!");
                                                        break;
                                                }
                                            }
                                            else{
                                                System.out.println("Incorrect action! ");
                                            }
                                        }
                                        break;
                                    case 7:
                                        name=readLine("New name- ");
                                        exists=false;
                                        for (User _user:users) {
                                            if (_user.getName().equals(name)) {
                                                exists = true;
                                                break;
                                            }
                                        }
                                        if (exists){
                                            System.out.println("User with same name already exists!");
                                        }
                                        else
                                        {
                                            user.setName(name);
                                            writeUsersInFile(users);
                                        }
                                         break;
                                    case 8:
                                        System.out.println("1- income; 2-outgoing; 3-transfer; 0-back to user's menu;");
                                        action=readByte("action- ");
                                            switch (action){
                                                case 1:
                                                    source=readLine("source- ");
                                                    while (true){
                                                        account=readLine("account- ");
                                                        if (user.accountExists(account)) break;
                                                        else {
                                                            System.out.println("Account doesn't exist. Try another one! ");
                                                        }
                                                    }
                                                    sum=readDouble("sum- ");
                                                    System.out.println("Do you want to add comment? ");
                                                    System.out.println("1-yes; 2-no; 0-cancel");
                                                    action=readByte("action- ");
                                                    switch (action){
                                                        case 1:
                                                            comment=readLine("comment- ");
                                                            user.addIncome(source,account,sum,comment);
                                                            writeUsersInFile(users);
                                                            System.out.println("Income has been successfully added!");
                                                            break;
                                                        case 2:
                                                            user.addIncome(source,account,sum);
                                                            writeUsersInFile(users);
                                                            System.out.println("Income has been successfully added!");
                                                            break;
                                                        case 0:
                                                            System.out.println("Income hasn't been added!");
                                                            break;
                                                        default:
                                                            System.out.println("Wrong number! Income hasn't been added! ");
                                                            break;
                                                    }
                                                    break;
                                                case 2:
                                                    while (true){
                                                        account=readLine("account- ");
                                                        if (user.accountExists(account)) break;
                                                        else {
                                                            System.out.println("Account doesn't exist. Try another one! ");
                                                        }
                                                    }
                                                    goal=readLine("goal- ");
                                                    sum=readDouble("sum- ");
                                                    System.out.println("Do you want to add comment? ");
                                                    System.out.println("1-yes; 2-no; 0-cancel");
                                                    action=readByte("action- ");
                                                    switch (action){
                                                        case 1:
                                                            comment=readLine("comment- ");
                                                            if (user.categoryExists(goal)) {
                                                                user.addOutgoing(sum, goal, account, comment);
                                                                writeUsersInFile(users);
                                                                System.out.println("Outgoing has been successfully added!");
                                                            }
                                                            else{
                                                                System.out.println("Wrong category! Outgoing hasn't been added! ");
                                                            }
                                                            break;
                                                        case 2:
                                                            if (user.categoryExists(goal)) {
                                                                user.addOutgoing(sum, goal, account);
                                                                writeUsersInFile(users);
                                                                System.out.println("Outgoing has been successfully added!");
                                                            }
                                                            else{
                                                                System.out.println("Wrong category! Outgoing hasn't been added! ");
                                                            }
                                                            break;
                                                        case 0:
                                                            System.out.println("Outgoing hasn't been added!");
                                                            break;
                                                        default:
                                                            System.out.println("Wrong number! Outgoing hasn't been added! ");
                                                            break;
                                                    }
                                                    break;
                                                case 3:
                                                    while (true){
                                                        accountOut=readLine("outgoing account- ");
                                                        if (user.accountExists(accountOut)) break;
                                                        else {
                                                            System.out.println("Account doesn't exist. Try another one! ");
                                                        }
                                                    }
                                                    while (true){
                                                        accountIn=readLine("income account- ");
                                                        if (user.accountExists(accountIn)) break;
                                                        else {
                                                            System.out.println("Account doesn't exist. Try another one! ");
                                                        }
                                                    }
                                                    sum=readDouble("sum- ");
                                                    System.out.println("Do you want to add comment? ");
                                                    System.out.println("1-yes; 2-no; 0-cancel");
                                                    action=readByte("action- ");
                                                    switch (action){
                                                        case 1:
                                                            comment=readLine("comment- ");
                                                            user.transfer(accountOut,accountIn,sum,comment);
                                                            writeUsersInFile(users);
                                                            System.out.println("Money have been successfully transferred!");
                                                            break;
                                                        case 2:
                                                            user.transfer(accountOut,accountIn,sum);
                                                            writeUsersInFile(users);
                                                            System.out.println("Money have been successfully transferred!");
                                                            break;
                                                        case 0:
                                                            System.out.println("Money haven't been transferred!");
                                                            break;
                                                        default:
                                                            System.out.println("Wrong number! Money haven't been transferred!");
                                                            break;
                                                    }
                                                    break;
                                                case 0:
                                                    break;
                                                default:
                                                    System.out.println("Incorrect action!");
                                                    break;
                                            }
                                        break;
                                    case 9:
                                        goal=readLine("Category- ");
                                        if (!user.categoryExists(goal)){
                                            user.addCategory(goal);
                                            writeUsersInFile(users);
                                        }
                                        else
                                        {
                                            System.out.println("Category already exists!");
                                        }
                                        break;
                                    default:
                                        System.out.println("Incorrect action!");
                                        break;
                                }
                            }
                        }
                        else {
                            System.out.println("Incorrect action!");
                            break;
                        }
                    }
                    break;
                case 2:
                    name=readLine("User's name- ");
                    exists=false;
                    for (User _user:users) {
                        if (_user.getName().equals(name)) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists){
                        System.out.println("User with same name already exists!");
                    }
                    else
                    {
                        users.add(new User(name));
                        writeUsersInFile(users);
                        System.out.println("User has been successfully added!");
                    }
                    break;
                case 3:
                        System.out.println("Which user do you want to delete? (0-cancel)");
                        for (int i = 0; i < users.size(); i++) {
                            System.out.println(+i + 1 + ") " + users.get(i).getName());
                        }
                        action=readByte("user- ");
                        if (action==0) break;
                        else
                        {
                            if (action<=users.size())
                            {
                                users.remove(action-1);
                                writeUsersInFile(users);
                                System.out.println("User has been deleted!");
                                break;
                            }
                            else
                            {
                                System.out.println("Incorrect action! User hasn't been deleted!");
                                break;
                            }
                        }
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect action!");
                    break;
            }
        }
    }






    public static void writeUsersInFile(List<User> users) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("users");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(users);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

        public static List<User> readUsersFromFile () throws Exception {
            List<User> _users=null;
                FileInputStream fileInputStream = new FileInputStream("users");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                _users = (List<User>) objectInputStream.readObject();
                objectInputStream.close();
                return _users;
        }

        public static byte readByte(String message){
            Scanner scanner;
            byte value;
            while (true) {
                try {
                    System.out.print(message);
                    scanner = new Scanner(System.in);
                    value = scanner.nextByte();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println(e);
                }
            }
            return value;
        }

    public static String readLine(String message){
        Scanner scanner;
        String line;
        while (true) {
            try {
                System.out.print(message);
                scanner = new Scanner(System.in);
                line = scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println(e);
            }
        }
        return line;
    }

    public static double readDouble(String message){
        Scanner scanner;
        double value;
        while (true) {
            try {
                System.out.print(message);
                scanner = new Scanner(System.in);
                value = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println(e);
            }
        }
        return value;
    }

    public static int readInt(String message){
        Scanner scanner;
        int value;
        while (true) {
            try {
                System.out.print(message);
                scanner = new Scanner(System.in);
                value = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println(e);
            }
        }
        return value;
    }
    public static void pushForward(List<User> users,User user){
        List<User> newList=new ArrayList<>(users);
        users.clear();
        users.add(user);
        users.addAll(newList);
    }
}
