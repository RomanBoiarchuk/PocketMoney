package com.company;

import com.Dialogs.ChangeUserDialog;
import com.PocketMoney.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Swing {
    public static List<User> users;
    public static JButton unableButton; // to switch from one panel to another and set current panel button unable
    public static User user; //current user
    public static JFrame frame;
    // panels, frame
    public static JPanel accountsPanel;
    public static JPanel categoriesPanel;
    public static JPanel operationsPanel;
    public static JLabel nameLabel=new JLabel(); // current user name
    public static JPanel mainPanel;
    public static JButton categoriesButton; // default start panel button

    public static void main(String[] args) {
        // looking for file with users
        try {
            users = UsersChanger.readUsersFromFile();
        }
        catch (Exception e){
            users=new ArrayList<>();
        }
        // check whether there are any users
        if (users.isEmpty()){
            JOptionPane.showMessageDialog(frame,"You don't have any user. Add one!");
            // add new user if there are no users yet
            if (!addUser()){
                System.exit(0);
            }

        }
        user=users.get(0);
        frame = Frame.getFrame();
        mainPanel=new JPanel();
        accountsPanel= AccountsPanel.getPanel();
        categoriesPanel= CategoriesPanel.getPanel();
        operationsPanel= OperationsPanel.getPanel();

        // to switch between few panels at one position
        CardLayout card=new CardLayout();
        mainPanel.setLayout(card);
        mainPanel.add(accountsPanel,"accounts");
        mainPanel.add(categoriesPanel,"categories");
        mainPanel.add(operationsPanel,"operations");

        // panel with buttons of switching panels
        JPanel southButtonsPanel=new JPanel();
        southButtonsPanel.setBackground(Color.CYAN);
        southButtonsPanel.setLayout(new GridLayout());
        JButton accountsButton=new JButton("Accounts");
        accountsButton.setBackground(Color.CYAN);
        categoriesButton=new JButton("Categories");
        categoriesButton.setBackground(Color.CYAN);
        JButton operationsButton=new JButton("Operations");
        operationsButton.setBackground(Color.CYAN);
        southButtonsPanel.add(accountsButton);
        southButtonsPanel.add(categoriesButton);
        southButtonsPanel.add(operationsButton);
        card.show(mainPanel,"categories");
        categoriesButton.setEnabled(false);
        unableButton=categoriesButton;

        // background panel
        JPanel panel=new JPanel();
        panel.add(southButtonsPanel);
        frame.add(panel,BorderLayout.SOUTH);
        panel.setBackground(Color.CYAN);
        frame.add(mainPanel,BorderLayout.CENTER);

        JPanel northPanel=new JPanel();
        northPanel.setBackground(Color.CYAN);

        nameLabel.setText(user.getName());
        JButton newUserButton=new JButton("New user");
        newUserButton.addActionListener(e -> addUser());
        JButton changeUserButton=new JButton("Users");
        changeUserButton.addActionListener(e -> {
            ChangeUserDialog changeUserDialog=new ChangeUserDialog();
            changeUserDialog.setVisible(true);
        });
        newUserButton.setBackground(Color.CYAN);
        changeUserButton.setBackground(Color.CYAN);

        JPanel northButtonsPanel=new JPanel();
        northButtonsPanel.setLayout(new GridLayout());
        northButtonsPanel.add(nameLabel);
        northButtonsPanel.add(newUserButton);
        northButtonsPanel.add(changeUserButton);
        northButtonsPanel.setBackground(Color.CYAN);

        northPanel.add(northButtonsPanel);
        frame.add(northPanel,BorderLayout.NORTH);

        // logic of switch buttons
        categoriesButton.addActionListener(e -> {
            unableButton.setEnabled(true);
            card.show(mainPanel,"categories");
            unableButton=categoriesButton;
            categoriesButton.setEnabled(false);
        });
        accountsButton.addActionListener(e -> {
            unableButton.setEnabled(true);
            card.show(mainPanel,"accounts");
            unableButton=accountsButton;
            accountsButton.setEnabled(false);
        });
        operationsButton.addActionListener(e -> {
            unableButton.setEnabled(true);
            card.show(mainPanel,"operations");
            unableButton=operationsButton;
            operationsButton.setEnabled(false);
        });
        frame.setVisible(true);
    }
    static boolean addUser() {
        // returns whether user was added
        String name = JOptionPane.showInputDialog(frame,"User name");
        if (name != null) {
            boolean isError = false; // to check whether name is valid
            if (name.trim().equals("")) {
                JOptionPane.showMessageDialog(frame, "Incorrect input!", "Error", JOptionPane.ERROR_MESSAGE);
                isError = true;
            } else {
                for (User user : users) {
                    if (user.getName().trim().equals(name.trim())) {
                        JOptionPane.showMessageDialog(frame, "User with such name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        isError = true;
                        break;
                    }
                }
            }
            if (isError) {
                return addUser();
            }
            else {
                UsersChanger.pushForward(users, new User(name));
                user = users.get(0);
                // delete links to previous panels if they are not null
                try{
                    mainPanel.remove(accountsPanel);
                    mainPanel.remove(categoriesPanel);
                    mainPanel.remove(operationsPanel);
                } catch (Exception e){}

                // prepare frame for new user
                accountsPanel = AccountsPanel.getPanel();
                categoriesPanel = CategoriesPanel.getPanel();
                operationsPanel = OperationsPanel.getPanel();
                // add panels to card layout if it's first user
                try{
                    mainPanel.add(categoriesPanel,"categories");
                    mainPanel.add(accountsPanel,"accounts");
                    mainPanel.add(operationsPanel,"operations");
                } catch (Exception e){}
                nameLabel.setText(user.getName());
                if (unableButton!=null) {
                    unableButton.setEnabled(true);
                    unableButton = categoriesButton;
                    unableButton.setEnabled(false);
                }
                if (frame != null)
                    frame.revalidate();
                UsersChanger.writeUsersInFile(users);
                JOptionPane.showMessageDialog(frame, "User has been successfully added!");
                return true;
            }
        }
        else
            return false;
    }
}
