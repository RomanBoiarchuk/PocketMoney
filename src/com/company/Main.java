package com.company;
import com.PocketMoney.*;
import javafx.print.Collation;

import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Main {

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
    public static void pushForward(List<User> users,User user){
        List<User> newList=new ArrayList<>(users);
        users.clear();
        users.add(user);
        users.addAll(newList);
    }
}
