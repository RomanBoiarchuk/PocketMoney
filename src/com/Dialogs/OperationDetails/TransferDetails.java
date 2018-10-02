package com.Dialogs.OperationDetails;

import com.PocketMoney.Transfer;
import com.company.AccountsPanel;
import com.company.OperationsPanel;
import com.company.Swing;
import com.company.UsersChanger;

import javax.swing.*;
import java.awt.*;

public class TransferDetails extends OperationDetails {
    public TransferDetails(Transfer transfer){
        super(transfer);
        JLabel accountOutLabel=new JLabel(transfer.getAccountOut());
        JPanel tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(new JLabel("Outgoing account: "),BorderLayout.WEST);
        tempPanel.add(accountOutLabel,BorderLayout.EAST);
        add(tempPanel,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JButton editAccountOutButton=new JButton("edit");
        editAccountOutButton.addActionListener(e -> {
            JDialog dialog=new FieldChangerWindow("Transfer"){
                {
                    JComboBox<String> accountBox=new JComboBox<>();
                    for (String key : Swing.user.getAccountsKeys()) {
                        accountBox.addItem(key);
                    }
                    accountBox.setSelectedItem(transfer.getAccountOut());
                    accountBox.removeItem(transfer.getAccountIn());
                    add(new JLabel("Outgoing Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e15 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                        if (!accountBox.getSelectedItem().equals(transfer.getAccountOut())) {
                            String oldAccount=transfer.getAccountOut();
                            String newAccount=(String)accountBox.getSelectedItem();
                            Swing.user.setTransferOut(OperationsPanel.TToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(transfer)), newAccount);
                            String line = transfer.getDate() + "\n" + "from \"" + transfer.getAccountOut() + "\" to \"" + transfer.getAccountIn() + "\": " + String.format("%.2f", transfer.getSum()) + " UAH";
                            OperationsPanel.OperationsButtons.get(transfer).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            accountOutLabel.setText(newAccount);
                            AccountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                            AccountsPanel.AButtons.get(newAccount).setText(newAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(newAccount))+" UAH");
                            UsersChanger.writeUsersInFile(Swing.users);
                            Swing.accountsPanel.revalidate();
                            Swing.operationsPanel.revalidate();
                        }
                        dispose();
                    });
                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,0,0,0),0,0));
                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,20,0,0),0,0));
                }
            };
            dialog.setVisible(true);
        });
        add(editAccountOutButton,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,15,0,0),0,0));
        JLabel accountInLabel=new JLabel(transfer.getAccountIn());
        tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(new JLabel("Income account: "),BorderLayout.WEST);
        tempPanel.add(accountInLabel,BorderLayout.EAST);
        add(tempPanel,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JButton editAccountInButton=new JButton("edit");
        editAccountInButton.addActionListener(e -> {
            JDialog dialog=new FieldChangerWindow("Transfer"){
                {
                    JComboBox<String> accountBox=new JComboBox<>();
                    for (String key : Swing.user.getAccountsKeys()) {
                        accountBox.addItem(key);
                    }
                    accountBox.setSelectedItem(transfer.getAccountIn());
                    accountBox.removeItem(transfer.getAccountOut());
                    add(new JLabel("Income Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e16 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                        if (!accountBox.getSelectedItem().equals(transfer.getAccountIn())) {
                            String oldAccount=transfer.getAccountIn();
                            String newAccount=(String)accountBox.getSelectedItem();
                            Swing.user.setTransferIn(OperationsPanel.TToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(transfer)), newAccount);
                            String line = transfer.getDate() + "\n" + "from \"" + transfer.getAccountOut() + "\" to \"" + transfer.getAccountIn() + "\": " + String.format("%.2f", transfer.getSum()) + " UAH";
                            OperationsPanel.OperationsButtons.get(transfer).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            accountInLabel.setText(newAccount);
                            AccountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                            AccountsPanel.AButtons.get(newAccount).setText(newAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(newAccount))+" UAH");
                            UsersChanger.writeUsersInFile(Swing.users);
                            Swing.accountsPanel.revalidate();
                            Swing.operationsPanel.revalidate();
                        }
                        dispose();
                    });
                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,0,0,0),0,0));
                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(20,20,0,0),0,0));
                }
            };
            dialog.setVisible(true);
        });
        add(editAccountInButton,new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,15,0,0),0,0));
        if (transfer.getAccountIn().equals("Unknown") || transfer.getAccountOut().equals("Unknown")){
            editAccountInButton.setEnabled(false);
            editAccountOutButton.setEnabled(false);
            editSumButton.setEnabled(false);
        }
    }
}
