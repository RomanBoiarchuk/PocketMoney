package com.company;

import com.Components.AccountButton;
import com.Components.CommentArea;
import com.Components.OperationButton;
import com.PocketMoney.Income;
import com.PocketMoney.Transfer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class accountsPanel {
    public static JLabel balanceLabel;
    public static Map<String,JButton> AButtons; // User's account: button
    public static Map<JMenuItem,String> ItemsAccounts;
    public static JToolBar AToolbar; // toolbar with account's buttons
    public static JPanel panel=new JPanel();
    static JButton addAccountButton;
    static JPanel getPanel(){
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
                        Functions.writeUsersInFile(Swing.users);
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
    public static class AddIncome extends JDialog{
        public AddIncome(String account){
            super(Swing.frame,"Income",true);
            // decorating dialog window
            setBackground(Color.orange);
            Toolkit toolkit=Toolkit.getDefaultToolkit();
            Dimension dimension=toolkit.getScreenSize();
            int height=300;
            int width=500;
            setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
            setLayout(new GridBagLayout());
            add(new JLabel("Source "),new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(0,0,0,0),0,0));
            JTextField sourceField=new JTextField(20);
            sourceField.setAutoscrolls(false);
            add(sourceField,new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(0,20,0,0),0,0));
            add(new JLabel("Sum "),new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,0,0,0),0,0));
            JTextField sumField=new JTextField(20);
            add(sumField,new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,20,0,0),0,0));
            JTextArea comment=new CommentArea("Comment",3,5);
            JScrollPane commentPane=new JScrollPane(comment);
            add(commentPane,new GridBagConstraints(0,2,6,3,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,0,0,0),100,80));
            JButton confirm=new JButton("Confirm");
            JButton cancel=new JButton("Cancel");
            add(confirm,new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,0,0,0),0,0));
            add(cancel,new GridBagConstraints(1,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,20,0,0),0,0));
            cancel.addActionListener(e -> dispose());
            confirm.addActionListener(e -> {
                String source=sourceField.getText();
                double sum=0;
                boolean isError=false;
                try{
                    sum=Double.valueOf(sumField.getText().replaceAll(",","."));
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(Swing.frame,"Incorrect input in sum field!","Error",JOptionPane.ERROR_MESSAGE);
                    isError=true;
                }
                if (!isError) {
                    if (source.trim().equals("")){
                        source="Unknown";
                    }
                    if (comment.getText() == null || comment.getText().equals("Comment"))
                        Swing.user.addIncome(source,account,sum);
                    else
                        Swing.user.addIncome(source,account, sum,comment.getText());
                    // editing data in panels and adding operation button
                    accountsPanel.AButtons.get(account).setText(account+": "+String.format("%.2f", Swing.user.getAccountBalance(account))+" UAH");
                    accountsPanel.balanceLabel.setText(String.format("%.2f", Swing.user.getBalance()));
                    Income income=Swing.user.getIncome(Swing.user.getIncomesSize()-1);
                    String line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+account+"\": "+String.format("%.2f",sum)+" UAH";
                    JButton operationButton=new OperationButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>", income);
                    operationsPanel.IButtons.put(operationsPanel.IToolbar.getComponentCount(),operationButton);
                    operationsPanel.IToolbar.add(operationButton);
                    JOptionPane.showMessageDialog(Swing.frame,"Income has benn successfully added!");
                    Functions.writeUsersInFile(Swing.users);
                    dispose();
                }
            });
        }
    }
    public static class AddTransfer extends JDialog{
        public AddTransfer(String accountOut){
            super(Swing.frame,"AddTransfer",true);
            // decorating dialog window
            setBackground(Color.orange);
            Toolkit toolkit=Toolkit.getDefaultToolkit();
            Dimension dimension=toolkit.getScreenSize();
            int height=300;
            int width=500;
            setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
            setLayout(new GridBagLayout());
            add(new JLabel("Income Account "),new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(0,0,0,0),0,0));
            JComboBox<String> accountInBox=new JComboBox<>();
            // adding all possible choices to checkbox
            for (String key:Swing.user.getAccountsKeys()){
                if (!key.equals(accountOut))
                accountInBox.addItem(key);
            }
            add(accountInBox,new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(0,20,0,0),0,0));
            add(new JLabel("Sum "),new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,0,0,0),0,0));
            JTextField sumField=new JTextField(20);
            add(sumField,new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,20,0,0),0,0));
            JTextArea comment=new CommentArea("Comment",3,5);
            JScrollPane commentPane=new JScrollPane(comment);
            add(commentPane,new GridBagConstraints(0,2,6,3,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,0,0,0),100,80));
            JButton confirm=new JButton("Confirm");
            JButton cancel=new JButton("Cancel");
            add(confirm,new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,0,0,0),0,0));
            add(cancel,new GridBagConstraints(1,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,20,0,0),0,0));

            cancel.addActionListener(e -> dispose());
            confirm.addActionListener(e -> {
                String accountIn=accountInBox.getItemAt(accountInBox.getSelectedIndex());
                double sum=0;
                boolean isError=false;
                try{
                    sum=Double.valueOf(sumField.getText().replaceAll(",","."));
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(Swing.frame,"Incorrect input in sum field!","Error",JOptionPane.ERROR_MESSAGE);
                    isError=true;
                }
                if (!isError) {
                    if (comment.getText() == null || comment.getText().equals("Comment"))
                        Swing.user.transfer(accountOut,accountIn,sum);
                    else
                        Swing.user.transfer(accountOut,accountIn,sum,comment.getText());
                    // editing data in the panels and adding operation button
                    accountsPanel.AButtons.get(accountIn).setText(accountIn+": "+String.format("%.2f",Swing.user.getAccountBalance(accountIn))+" UAH");
                    accountsPanel.AButtons.get(accountOut).setText(accountOut+": "+String.format("%.2f",Swing.user.getAccountBalance(accountOut))+" UAH");
                    Transfer transfer =Swing.user.getTransfer(Swing.user.getTransfersSize()-1);
                    String line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.format("%.2f",transfer.getSum())+" UAH";
                    JButton operationButton=new OperationButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>", transfer);
                    operationsPanel.TButtons.put(operationsPanel.TToolbar.getComponentCount(),operationButton);
                    operationsPanel.TToolbar.add(operationButton);
                    JOptionPane.showMessageDialog(Swing.frame,"Transfer has benn completed!");
                    Functions.writeUsersInFile(Swing.users);
                    dispose();
                }
            });
        }
    }
}






