package com.Dialogs;

import com.Components.CommentArea;
import com.Components.OperationButton;
import com.PocketMoney.Income;
import com.company.Functions;
import com.company.Swing;
import com.company.accountsPanel;
import com.company.operationsPanel;

import javax.swing.*;
import java.awt.*;

public class AddIncome extends JDialog {
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