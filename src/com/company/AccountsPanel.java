package com.company;

import com.Components.AccountButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class AccountsPanel {
    public static JLabel balanceLabel;
    public static Map<String,JButton> AButtons; // User's account: button
    public static Map<JMenuItem,String> ItemsAccounts;
    public static JToolBar AToolbar; // toolbar with account's buttons
    public static JPanel panel=new JPanel();
    static JButton addAccountButton;
    static JPanel getPanel(){
        JPanel panel=new JPanel();
        // decorating panel
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new GridBagLayout());
        JLabel accountsLabel=new JLabel("Accounts");
        balanceLabel=new JLabel(String.format("%.2f",Swing.user.getBalance()));
        Set<String> accounts=Swing.user.getAccountsKeys();
        AButtons=new HashMap<>();
        ItemsAccounts=new HashMap<>();
        AToolbar=new JToolBar(null,JToolBar.VERTICAL);
        AToolbar.setFloatable(false);
        Dimension dimension=new Dimension(250,50);

        // creating toolbar with buttons that contain information about each account
        JButton tempButton;
        JPanel tempPanel;
        for (String account:accounts){
            tempButton=new AccountButton(account+": "+String.format("%.2f", Swing.user.getAccountBalance(account))+" UAH",account);
            tempPanel=new JPanel();
            tempPanel.add(tempButton);
            tempPanel.setBackground(Color.ORANGE);
            AButtons.put(account,tempButton);
            AToolbar.add(tempPanel);
        }

        addAccountButton=new JButton("Add new account");
        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=JOptionPane.showInputDialog("Account name");
                if (name!=null) {
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(Swing.frame, "The field is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        actionPerformed(e);
                    }
                    else
                    if (Swing.user.getAccountsKeys().contains(name))
                    {
                        JOptionPane.showMessageDialog(Swing.frame, "Account with such name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        actionPerformed(e);
                    }
                    else {
                        Swing.user.addAccount(name);
                        UsersChanger.writeUsersInFile(Swing.users);
                        JButton tempButton = new AccountButton(name + ": 0.00 UAH", name);
                        JPanel tempPanel = new JPanel();
                        tempPanel.add(tempButton);
                        tempPanel.setBackground(Color.ORANGE);
                        AButtons.put(name, tempButton);
                        // remove addAccount button from toolbar and return it back after adding new account's button
                        JPanel addAccountPanel = (JPanel) AToolbar.getComponentAtIndex(AToolbar.getComponentCount() - 1);
                        AToolbar.remove(addAccountPanel);
                        AToolbar.add(tempPanel);
                        AToolbar.add(addAccountPanel);
                        panel.revalidate();
                    }
            }
        }});
        addAccountButton.setPreferredSize(dimension);
        tempPanel=new JPanel();
        tempPanel.add(addAccountButton);
        tempPanel.setBackground(Color.ORANGE);
        AToolbar.add(tempPanel);

        // upper part of panel
        tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(accountsLabel,BorderLayout.WEST);
        tempPanel.add(balanceLabel,BorderLayout.EAST);
        tempPanel.setPreferredSize(new Dimension(250,50));
        tempPanel.setBackground(Color.ORANGE);

        // toolbar config
        panel.add(tempPanel);
        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setViewportView(AToolbar);
        scrollPane.setMinimumSize(new Dimension(300,350));
        panel.add(scrollPane,new GridBagConstraints(0,1,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
                new Insets(30,0,0,0),0,0));
        AToolbar.setBackground(Color.ORANGE);
        return panel;
    }
}






