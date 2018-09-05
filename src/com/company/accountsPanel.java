package com.company;

import com.PocketMoney.Income;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JButton tempButton;
        JPanel tempPanel;
        JPopupMenu popupMenu;
        JMenuItem addIncomeItem;
        JMenuItem transferItem;
        JMenuItem deleteItem;
        for (String account:accounts){
            tempButton=new JButton(account+": "+String.format("%.2f", Swing.user.getAccountBalance(account))+" UAH");
            tempButton.setEnabled(false);
            tempPanel=new JPanel();
            tempPanel.add(tempButton);
            tempPanel.setBackground(Color.ORANGE);
            tempButton.setPreferredSize(dimension);
            popupMenu=new JPopupMenu();
            addIncomeItem=new JMenuItem("Income");
            transferItem=new JMenuItem("Transfer");
            deleteItem=new JMenuItem("Delete");
            popupMenu.add(addIncomeItem);
            popupMenu.add(transferItem);
            popupMenu.add(deleteItem);
            tempButton.setComponentPopupMenu(popupMenu);
            addIncomeItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddIncome addIncome=new AddIncome(ItemsAccounts.get(e.getSource()));
                    addIncome.setVisible(true);
                }
            });
            transferItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddTransfer addTransfer=new AddTransfer(ItemsAccounts.get(e.getSource()));
                    addTransfer.setVisible(true);
                }
            });
            deleteItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this account?",
                            "Delete account",JOptionPane.YES_NO_OPTION);
                    if (option==0){
                        Income income;
                        String line;
                        for (int i=0;i<Swing.user.getIncomesSize();i++){
                            income=Swing.user.getIncome(i);
                            if (income.getAccount().equals(ItemsAccounts.get(e.getSource()))) {
                                income.setUnknownAccount();
                                line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+income.getAccount()+"\": "+String.valueOf(income.getSum())+" UAH";
                                operationsPanel.IButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            }
                        }
                        Outgoing outgoing;
                        for (int i=0;i<Swing.user.getOutgoingsSize();i++){
                            outgoing=Swing.user.getOutgoing(i);
                            if (outgoing.getAccount().equals(ItemsAccounts.get(e.getSource()))){
                                outgoing.setUnknownAccount();
                                line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.valueOf(outgoing.getSum())+" UAH";
                                operationsPanel.OButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            }
                        }
                        Transfer transfer;
                        for (int i=0;i<Swing.user.getTransfersSize();i++){
                            transfer=Swing.user.getTransfer(i);
                            if (transfer.getAccountIn().equals(ItemsAccounts.get(e.getSource())) || transfer.getAccountOut().equals(ItemsAccounts.get(e.getSource()))){
                                if (transfer.getAccountIn().equals(ItemsAccounts.get(e.getSource())))
                                    transfer.setUnknownAccountIn();
                                else
                                    transfer.setUnknownAccountOut();
                                line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.valueOf(transfer.getSum())+" UAH";
                                operationsPanel.TButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            }
                        }
                        AToolbar.remove(AButtons.get(ItemsAccounts.get(e.getSource())).getParent());
                        AToolbar.revalidate();
                        JButton button=AButtons.get(ItemsAccounts.get(e.getSource()));
                        AButtons.remove(ItemsAccounts.get(e.getSource()));
                        Swing.user.deleteAccount(ItemsAccounts.get(e.getSource()));
                        for (int i=0;i<3;i++)
                        ItemsAccounts.remove(button.getComponentPopupMenu().getComponent(0));
                        balanceLabel.setText(String.valueOf(Swing.user.getBalance()));
                        panel.revalidate();
                        Functions.writeUsersInFile(Swing.users);
                        JOptionPane.showMessageDialog(Swing.frame,"Account has been successfully deleted!");
                    }
                }
            });
            ItemsAccounts.put(addIncomeItem,account);
            ItemsAccounts.put(transferItem,account);
            ItemsAccounts.put(deleteItem,account);

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
                        JButton tempButton = new JButton(name + ": 0.00 UAH");
                        tempButton.setEnabled(false);
                        JPanel tempPanel = new JPanel();
                        tempPanel.add(tempButton);
                        tempPanel.setBackground(Color.ORANGE);
                        tempButton.setPreferredSize(dimension);
                        JPopupMenu popupMenu=new JPopupMenu();
                        JMenuItem addIncomeItem=new JMenuItem("Income");
                        JMenuItem transferItem=new JMenuItem("Transfer");
                        JMenuItem deleteItem=new JMenuItem("Delete");
                        popupMenu.add(addIncomeItem);
                        popupMenu.add(transferItem);
                        popupMenu.add(deleteItem);
                        tempButton.setComponentPopupMenu(popupMenu);
                        addIncomeItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                AddIncome addIncome=new AddIncome(ItemsAccounts.get(e.getSource()));
                                addIncome.setVisible(true);
                            }
                        });
                        transferItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                AddTransfer addTransfer=new AddTransfer(ItemsAccounts.get(e.getSource()));
                                addTransfer.setVisible(true);
                            }
                        });
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this account?",
                                        "Delete account",JOptionPane.YES_NO_OPTION);
                                if (option==0){
                                    Income income;
                                    String line;
                                    for (int i=0;i<Swing.user.getIncomesSize();i++){
                                        income=Swing.user.getIncome(i);
                                        if (income.getAccount().equals(ItemsAccounts.get(e.getSource()))) {
                                            income.setUnknownAccount();
                                            line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+income.getAccount()+"\": "+String.format("%.2f",income.getSum())+" UAH";
                                            operationsPanel.IButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                        }
                                    }
                                    Outgoing outgoing;
                                    for (int i=0;i<Swing.user.getOutgoingsSize();i++){
                                        outgoing=Swing.user.getOutgoing(i);
                                        if (outgoing.getAccount().equals(ItemsAccounts.get(e.getSource()))){
                                            outgoing.setUnknownAccount();
                                            line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.valueOf(outgoing.getSum())+" UAH";
                                            operationsPanel.OButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                        }
                                    }
                                    Transfer transfer;
                                    for (int i=0;i<Swing.user.getTransfersSize();i++){
                                        transfer=Swing.user.getTransfer(i);
                                        if (transfer.getAccountIn().equals(ItemsAccounts.get(e.getSource())) || transfer.getAccountOut().equals(ItemsAccounts.get(e.getSource()))){
                                            if (transfer.getAccountIn().equals(ItemsAccounts.get(e.getSource())))
                                                transfer.setUnknownAccountIn();
                                            else
                                                transfer.setUnknownAccountOut();
                                            line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.valueOf(transfer.getSum())+" UAH";
                                            operationsPanel.TButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                        }
                                    }
                                    AToolbar.remove(AButtons.get(ItemsAccounts.get(e.getSource())).getParent());
                                    AToolbar.revalidate();
                                    JButton button=AButtons.get(ItemsAccounts.get(e.getSource()));
                                    AButtons.remove(ItemsAccounts.get(e.getSource()));
                                    Swing.user.deleteAccount(ItemsAccounts.get(e.getSource()));
                                    for (int i=0;i<3;i++)
                                        ItemsAccounts.remove(button.getComponentPopupMenu().getComponent(0));
                                    balanceLabel.setText(String.valueOf(Swing.user.getBalance()));
                                    panel.revalidate();
                                    Functions.writeUsersInFile(Swing.users);
                                    JOptionPane.showMessageDialog(Swing.frame,"Account has been successfully deleted!");
                                }
                            }
                        });
                        ItemsAccounts.put(addIncomeItem,name);
                        ItemsAccounts.put(transferItem,name);
                        ItemsAccounts.put(deleteItem,name);
                        AButtons.put(name, tempButton);
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
        tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(accountsLabel,BorderLayout.WEST);
        tempPanel.add(balanceLabel,BorderLayout.EAST);
        tempPanel.setPreferredSize(new Dimension(250,50));
        tempPanel.setBackground(Color.ORANGE);
        panel.add(tempPanel);
        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setViewportView(AToolbar);
        scrollPane.setMinimumSize(new Dimension(300,350));
        //scrollPane.setPreferredSize(scrollPane.getMinimumSize());
        panel.add(scrollPane,new GridBagConstraints(0,1,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
                new Insets(30,0,0,0),0,0));
        AToolbar.setBackground(Color.ORANGE);
        return panel;
    }
    public static class AddIncome extends JDialog{
        public AddIncome(String account){
            super(Swing.frame,"Income",true);
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
            JTextArea comment=new JTextArea("Comment",3,5);
            comment.setLineWrap(true);
            JScrollPane commentPane=new JScrollPane(comment);
            add(commentPane,new GridBagConstraints(0,2,6,3,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,0,0,0),100,80));
            JButton confirm=new JButton("Confirm");
            JButton cancel=new JButton("Cancel");
            add(confirm,new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,0,0,0),0,0));
            add(cancel,new GridBagConstraints(1,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,20,0,0),0,0));
            setBackground(Color.orange);
            Toolkit toolkit=Toolkit.getDefaultToolkit();
            Dimension dimension=toolkit.getScreenSize();
            int height=300;
            int width=500;
            setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                        accountsPanel.AButtons.get(account).setText(account+": "+String.format("%.2f", Swing.user.getAccountBalance(account))+" UAH");
                        accountsPanel.balanceLabel.setText(String.format("%.2f", Swing.user.getBalance()));
                        Income income=Swing.user.getIncome(Swing.user.getIncomesSize()-1);
                        String line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+account+"\": "+String.format("%.2f",sum)+" UAH";
                        JButton operationButton=new JButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                        JPopupMenu popupMenu=new JPopupMenu();
                        JMenuItem deleteItem=new JMenuItem("delete");
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                                        "Delete operation",JOptionPane.YES_NO_OPTION);
                                if (option==0){
                                    Income income=(Income)operationsPanel.ItemsOperations.get(e.getSource());
                                    Swing.user.deleteIncome(operationsPanel.IToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(income)));
                                    operationsPanel.IToolbar.remove(operationsPanel.OperationsButtons.get(income));
                                    operationsPanel.IButtons.remove(income);
                                    accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                                    accountsPanel.AButtons.get(income.getAccount()).setText(income.getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(income.getAccount()))+" UAH");
                                    operationsPanel.IToolbar.revalidate();
                                    Functions.writeUsersInFile(Swing.users);
                                }
                            }
                        });
                        popupMenu.add(deleteItem);
                        operationButton.setComponentPopupMenu(popupMenu);
                        operationButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                operationsPanel.OperationsDetails operationsDetails=new operationsPanel.OperationsDetails(Swing.user.getIncome(operationsPanel.IToolbar.getComponentIndex((JButton)e.getSource())));
                                operationsDetails.setVisible(true);
                            }
                        });
                        operationsPanel.IButtons.put(operationsPanel.IToolbar.getComponentCount(),operationButton);
                        operationsPanel.IToolbar.add(operationButton);
                        operationsPanel.ItemsOperations.put(deleteItem,income);
                        operationsPanel.OperationsButtons.put(income,operationButton);
                        JOptionPane.showMessageDialog(Swing.frame,"Income has benn successfully added!");
                        Functions.writeUsersInFile(Swing.users);
                        dispose();
                    }
                }
            });
        }
    }
    public static class AddTransfer extends JDialog{
        public AddTransfer(String accountOut){
            super(Swing.frame,"AddTransfer",true);
            setLayout(new GridBagLayout());
            add(new JLabel("Income Account "),new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(0,0,0,0),0,0));
            JComboBox<String> accountInBox=new JComboBox<>();
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
            JTextArea comment=new JTextArea("Comment",3,5);
            comment.setLineWrap(true);
            JScrollPane commentPane=new JScrollPane(comment);
            add(commentPane,new GridBagConstraints(0,2,6,3,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(20,0,0,0),100,80));
            JButton confirm=new JButton("Confirm");
            JButton cancel=new JButton("Cancel");
            add(confirm,new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,0,0,0),0,0));
            add(cancel,new GridBagConstraints(1,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(40,20,0,0),0,0));
            setBackground(Color.orange);
            Toolkit toolkit=Toolkit.getDefaultToolkit();
            Dimension dimension=toolkit.getScreenSize();
            int height=300;
            int width=500;
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                        accountsPanel.AButtons.get(accountIn).setText(accountIn+": "+String.format("%.2f",Swing.user.getAccountBalance(accountIn))+" UAH");
                        accountsPanel.AButtons.get(accountOut).setText(accountOut+": "+String.format("%.2f",Swing.user.getAccountBalance(accountOut))+" UAH");
                        Transfer transfer =Swing.user.getTransfer(Swing.user.getTransfersSize()-1);
                        String line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.format("%.2f",transfer.getSum())+" UAH";
                        JButton operationButton=new JButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                        JPopupMenu popupMenu=new JPopupMenu();
                        JMenuItem deleteItem=new JMenuItem("delete");
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                                        "Delete operation",JOptionPane.YES_NO_OPTION);
                                if (option==0){
                                    Transfer transfer=(Transfer) operationsPanel.ItemsOperations.get(e.getSource());
                                    Swing.user.deleteTransfer(operationsPanel.TToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(transfer)));
                                    operationsPanel.TToolbar.remove(operationsPanel.OperationsButtons.get(transfer));
                                    operationsPanel.TButtons.remove(transfer);
                                    accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                                    accountsPanel.AButtons.get(transfer.getAccountIn()).setText(transfer.getAccountIn()+": "+String.format("%.2f", Swing.user.getAccountBalance(transfer.getAccountIn()))+" UAH");
                                    accountsPanel.AButtons.get(transfer.getAccountOut()).setText(transfer.getAccountOut()+": "+String.format("%.2f", Swing.user.getAccountBalance(transfer.getAccountOut()))+" UAH");
                                    operationsPanel.TToolbar.revalidate();
                                    Functions.writeUsersInFile(Swing.users);
                                }
                            }
                        });
                        popupMenu.add(deleteItem);
                        operationButton.setComponentPopupMenu(popupMenu);
                        operationButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                operationsPanel.OperationsDetails operationsDetails=new operationsPanel.OperationsDetails(Swing.user.getTransfer(operationsPanel.TToolbar.getComponentIndex((JButton)e.getSource())));
                                operationsDetails.setVisible(true);
                            }
                        });
                        operationsPanel.TButtons.put(operationsPanel.TToolbar.getComponentCount(),operationButton);
                        operationsPanel.TToolbar.add(operationButton);
                        operationsPanel.ItemsOperations.put(deleteItem,transfer);
                        operationsPanel.OperationsButtons.put(transfer,operationButton);
                        JOptionPane.showMessageDialog(Swing.frame,"Transfer has benn completed!");
                        Functions.writeUsersInFile(Swing.users);
                        dispose();
                    }
                }
            });
            setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
        }
    }
}






