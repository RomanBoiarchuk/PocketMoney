package com.Dialogs.AddOperation;

import com.Components.OperationButton;
import com.PocketMoney.Transfer;
import com.company.UsersChanger;
import com.company.Swing;
import com.company.AccountsPanel;
import com.company.OperationsPanel;

import javax.swing.*;
import java.awt.*;

public class AddTransfer extends AddOperation {
    public AddTransfer(String accountOut){
        super("Transfer");
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
                AccountsPanel.AButtons.get(accountIn).setText(accountIn+": "+String.format("%.2f",Swing.user.getAccountBalance(accountIn))+" UAH");
                AccountsPanel.AButtons.get(accountOut).setText(accountOut+": "+String.format("%.2f",Swing.user.getAccountBalance(accountOut))+" UAH");
                Transfer transfer =Swing.user.getTransfer(Swing.user.getTransfersSize()-1);
                String line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.format("%.2f",transfer.getSum())+" UAH";
                JButton operationButton=new OperationButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>", transfer);
                OperationsPanel.TButtons.put(OperationsPanel.TToolbar.getComponentCount(),operationButton);
                OperationsPanel.TToolbar.add(operationButton);
                JOptionPane.showMessageDialog(Swing.frame,"Transfer has benn completed!");
                UsersChanger.writeUsersInFile(Swing.users);
                dispose();
            }
        });
    }
}
