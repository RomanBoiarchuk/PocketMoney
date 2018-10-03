package com.Dialogs.AddOperation;

import com.Components.OperationButton.IncomeButton;
import com.PocketMoney.Income;
import com.company.UsersChanger;
import com.company.Swing;
import com.company.AccountsPanel;
import com.company.OperationsPanel;

import javax.swing.*;
import java.awt.*;

public class AddIncome extends AddOperation {
    public AddIncome(String account){
        super("Income");
        add(new JLabel("Source "),new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));
        JTextField sourceField=new JTextField(20);
        sourceField.setAutoscrolls(false);
        add(sourceField,new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(0,20,0,0),0,0));
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
                AccountsPanel.AButtons.get(account).setText(account+": "+String.format("%.2f", Swing.user.getAccountBalance(account))+" UAH");
                AccountsPanel.balanceLabel.setText(String.format("%.2f", Swing.user.getBalance()));
                Income income=Swing.user.getIncome(Swing.user.getIncomesSize()-1);
                String line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+account+"\": "+String.format("%.2f",sum)+" UAH";
                JButton operationButton=new IncomeButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>", income);
                OperationsPanel.IButtons.put(OperationsPanel.IToolbar.getComponentCount(),operationButton);
                OperationsPanel.IToolbar.add(operationButton);
                JOptionPane.showMessageDialog(Swing.frame,"Income has benn successfully added!");
                UsersChanger.writeUsersInFile(Swing.users);
                dispose();
            }
        });
    }
}