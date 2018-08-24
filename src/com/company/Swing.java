package com.company;

import com.PocketMoney.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Swing {
    public static List<User> users;
    public static JButton unableButton;
    public static User user;
    public static JFrame frame;
    public static void main(String[] args) {
        users=Main.readUsersFromFile();
        user=users.get(0);
        frame = Frame.getFrame();
        JPanel mainPanel=new JPanel();
        JPanel accountsPanel= com.company.accountsPanel.getPanel();
        JPanel categoriesPanel= com.company.categoriesPanel.getPanel();
        JPanel operationsPanel= com.company.operationsPanel.getPanel();
        CardLayout card=new CardLayout();
        mainPanel.setLayout(card);
        mainPanel.add(accountsPanel,"accounts");
        mainPanel.add(categoriesPanel,"categories");
        mainPanel.add(operationsPanel,"operations");
        JToolBar toolBar=new JToolBar();
        JButton accountsButton=new JButton("Accounts");
        JButton categoriesButton=new JButton("Categories");
        JButton operationsButton=new JButton("Operations");
        toolBar.add(accountsButton);
        toolBar.add(categoriesButton);
        toolBar.add(operationsButton);
        toolBar.setFloatable(false);
        card.show(mainPanel,"categories");
        categoriesButton.setEnabled(false);
        unableButton=categoriesButton;
        JPanel panel=new JPanel();
        panel.add(toolBar);
        frame.add(panel,BorderLayout.SOUTH);
        panel.setBackground(Color.CYAN);
        frame.add(mainPanel,BorderLayout.CENTER);
        categoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unableButton.setEnabled(true);
                card.show(mainPanel,"categories");
                unableButton=categoriesButton;
                categoriesButton.setEnabled(false);
            }
        });
        accountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unableButton.setEnabled(true);
                card.show(mainPanel,"accounts");
                unableButton=accountsButton;
                accountsButton.setEnabled(false);
            }
        });
        operationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unableButton.setEnabled(true);
                card.show(mainPanel,"operations");
                unableButton=operationsButton;
                operationsButton.setEnabled(false);
            }
        });
        frame.setVisible(true);
    }

}
