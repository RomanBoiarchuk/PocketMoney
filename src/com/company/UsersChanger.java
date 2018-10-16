package com.company;
import com.PocketMoney.*;
import javafx.print.Collation;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class UsersChanger {

    public static void writeUsersInFile(List<User> users) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream("users");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(users);
            objectOutputStream.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(Swing.frame,"Error! Can't create log file in this folder","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
        public static List<User> readUsersFromFile () throws Exception {
            List<User> _users;
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
    public static boolean addUser() {
        // returns whether user was added
        String name = JOptionPane.showInputDialog(Swing.frame,"User name");
        if (name != null) {
            boolean isError = false; // to check whether name is valid
            if (name.trim().equals("")) {
                JOptionPane.showMessageDialog(Swing.frame, "Incorrect input!", "Error", JOptionPane.ERROR_MESSAGE);
                isError = true;
            } else {
                for (User user : Swing.users) {
                    if (user.getName().trim().equals(name.trim())) {
                        JOptionPane.showMessageDialog(Swing.frame, "User with such name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        isError = true;
                        break;
                    }
                }
            }
            if (isError) {
                return addUser();
            }
            else {
                UsersChanger.pushForward(Swing.users, new User(name));
                Swing.user = Swing.users.get(0);
                // delete links to previous panels if they are not null
                try{
                    Swing.mainPanel.remove(Swing.accountsPanel);
                    Swing.mainPanel.remove(Swing.categoriesPanel);
                    Swing.mainPanel.remove(Swing.operationsPanel);
                } catch (Exception e){}

                // prepare frame for new user
                Swing.accountsPanel = AccountsPanel.getPanel();
                Swing.categoriesPanel = CategoriesPanel.getPanel();
                Swing.operationsPanel = OperationsPanel.getPanel();
                // add panels to card layout if it's first user
                try{
                    Swing.mainPanel.add(Swing.categoriesPanel,"categories");
                    Swing.mainPanel.add(Swing.accountsPanel,"accounts");
                    Swing.mainPanel.add(Swing.operationsPanel,"operations");
                } catch (Exception e){}
                Swing.nameLabel.setText(Swing.user.getName());
                if (Swing.unableButton!=null) {
                    Swing.unableButton.setEnabled(true);
                    Swing.unableButton = Swing.categoriesButton;
                    Swing.unableButton.setEnabled(false);
                }
                if (Swing.frame != null)
                    Swing.frame.revalidate();
                UsersChanger.writeUsersInFile(Swing.users);
                JOptionPane.showMessageDialog(Swing.frame, "User has been successfully added!");
                return true;
            }
        }
        else
            return false;
    }
}
