package com.Dialogs.OperationDetails;

import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.Frame.AccountsPanel;
import com.Frame.OperationsPanel;
import com.Frame.Swing;
import com.Frame.UsersChanger;

import javax.swing.*;
import java.awt.*;

import static com.Frame.OperationsPanel.IToolbar;

public class IncomeDetails extends OperationDetails {
    @Override
    void changeSum(Operation operation,double newSum) {
        Swing.user.setIncomeSum(IToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)),newSum);
        AccountsPanel.AButtons.get(((Income)operation).getAccount()).setText(((Income)operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Income)operation).getAccount()))+" UAH");
        AccountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
        sumLabel.setText(String.format("%.2f",operation.getSum())+" UAH");
        String line=operation.getDate()+"\n"+"from \""+((Income)operation).getSource()+"\" to \""+((Income)operation).getAccount()+"\": "+String.format("%.2f",newSum)+" UAH";
        OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
        UsersChanger.writeUsersInFile(Swing.users);
        Swing.accountsPanel.revalidate();
        Swing.operationsPanel.revalidate();
    }
    public IncomeDetails(Income income){
        super(income);
        JLabel sourceLabel=new JLabel(income.getSource());
        JPanel tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(new JLabel("Source: "),BorderLayout.WEST);
        tempPanel.add(sourceLabel,BorderLayout.EAST);
        add(tempPanel,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JButton editSourceButton=new JButton("edit");
        editSourceButton.addActionListener(e -> {
            JDialog dialog=new FieldChangerWindow("Income"){
                {
                    JTextField sourceField=new JTextField(20);
                    sourceField.setAutoscrolls(false);
                    add(new JLabel("Source"),new GridBagConstraints(0,0,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(0,0,0,0),0,0));
                    add(sourceField,new GridBagConstraints(0,1,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,0,0,0),0,0));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e1 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                        if (sourceField.getText().trim().equals("")){
                            JOptionPane.showMessageDialog(Swing.frame,"Incorrect input!","Error",JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            Swing.user.setIncomeSource(OperationsPanel.IToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(income)), sourceField.getText());
                            String line=income.getDate()+"\n"+"from \""+(income.getSource()+"\" to \""+(income.getAccount()+"\": "+String.format("%.2f",income.getSum())+" UAH"));
                            OperationsPanel.OperationsButtons.get(income).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            sourceLabel.setText(income.getSource());
                            UsersChanger.writeUsersInFile(Swing.users);
                            Swing.operationsPanel.revalidate();
                            dispose();
                        }
                    });
                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,0,0,0),0,0));
                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,20,0,0),0,0));
                }
            };
            dialog.setVisible(true);
        });
        add(editSourceButton,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,15,0,0),0,0));
        JLabel accountLabel=new JLabel(income.getAccount());
        tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(new JLabel("Account: "),BorderLayout.WEST);
        tempPanel.add(accountLabel,BorderLayout.EAST);
        add(tempPanel,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JButton editAccountButton=new JButton("edit");
        editAccountButton.addActionListener(e -> {
            JDialog dialog=new FieldChangerWindow("Income"){
                {
                    JComboBox<String> accountBox=new JComboBox<>();
                    for (String key : Swing.user.getAccountsKeys()) {
                        accountBox.addItem(key);
                    }
                    accountBox.setSelectedItem(income.getAccount());
                    add(new JLabel("Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e12 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                                if (!accountBox.getSelectedItem().equals(income.getAccount())) {
                                    String oldAccount=(income.getAccount());
                                    Swing.user.setIncomeAccount(IToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(income)), (String)accountBox.getSelectedItem());
                                    String line = income.getDate() + "\n" + "from \"" + (income).getSource() + "\" to \"" + (income).getAccount() + "\": " + String.format("%.2f", income) + " UAH";
                                    OperationsPanel.OperationsButtons.get(income).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                    accountLabel.setText((income).getAccount());
                                    AccountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                    AccountsPanel.AButtons.get((income).getAccount()).setText((income).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance((income).getAccount()))+" UAH");
                                    Swing.accountsPanel.revalidate();
                                    Swing.operationsPanel.revalidate();
                                    UsersChanger.writeUsersInFile(Swing.users);
                                }
                                dispose();
                            }
                    );
                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,0,0,0),0,0));
                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,20,0,0),0,0));
                }
            };
            dialog.setVisible(true);
        });
        add(editAccountButton,new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,15,0,0),0,0));
        editSumButton.addActionListener(e->{

        });
        if (income.getAccount().equals("Unknown")){
            editSourceButton.setEnabled(false);
            editAccountButton.setEnabled(false);
            editSumButton.setEnabled(false);
        }
    }
}
