package com.company;

import com.Dialogs.ChangeUserDialog;
import com.PocketMoney.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
            if (!UsersChanger.addUser()){
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
        newUserButton.addActionListener(e -> UsersChanger.addUser());
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
}
