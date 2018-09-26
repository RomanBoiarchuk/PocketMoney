package com.Components;

import com.PocketMoney.Income;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;
import com.company.UsersChanger;
import com.company.Swing;
import com.company.AccountsPanel;
import com.company.OperationsPanel;
import com.Dialogs.*;

import javax.swing.*;
import java.awt.*;


public class AccountButton extends JButton {
    public AccountButton(String text, String account){
        super(text);
        setEnabled(false);
        Dimension dimension=new Dimension(250,50);
        setPreferredSize(dimension);
        JPopupMenu popupMenu=new JPopupMenu();
        JMenuItem addIncomeItem=new JMenuItem("Income");
        JMenuItem transferItem=new JMenuItem("Transfer");
        JMenuItem deleteItem=new JMenuItem("Delete");
        popupMenu.add(addIncomeItem);
        popupMenu.add(transferItem);
        popupMenu.add(deleteItem);
        setComponentPopupMenu(popupMenu);
        addIncomeItem.addActionListener(e -> {
            AddIncome addIncome=new AddIncome(AccountsPanel.ItemsAccounts.get(e.getSource()));
            addIncome.setVisible(true);
        });
        transferItem.addActionListener(e -> {
            AddTransfer addTransfer=new AddTransfer(AccountsPanel.ItemsAccounts.get(e.getSource()));
            addTransfer.setVisible(true);
        });
        deleteItem.addActionListener(e -> {
            int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this account?",
                    "Delete account",JOptionPane.YES_NO_OPTION);
            if (option==0){
                Income income;
                String line;
                for (int i=0;i<Swing.user.getIncomesSize();i++){
                    income=Swing.user.getIncome(i);
                    if (income.getAccount().equals(AccountsPanel.ItemsAccounts.get(e.getSource()))) {
                        income.setUnknownAccount();
                        line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+income.getAccount()+"\": "+String.valueOf(income.getSum())+" UAH";
                        OperationsPanel.IButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                    }
                }
                Outgoing outgoing;
                for (int i=0;i<Swing.user.getOutgoingsSize();i++){
                    outgoing=Swing.user.getOutgoing(i);
                    if (outgoing.getAccount().equals(AccountsPanel.ItemsAccounts.get(e.getSource()))){
                        outgoing.setUnknownAccount();
                        line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.valueOf(outgoing.getSum())+" UAH";
                        OperationsPanel.OButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                    }
                }
                Transfer transfer;
                for (int i=0;i<Swing.user.getTransfersSize();i++){
                    transfer=Swing.user.getTransfer(i);
                    if (transfer.getAccountIn().equals(AccountsPanel.ItemsAccounts.get(e.getSource())) || transfer.getAccountOut().equals(AccountsPanel.ItemsAccounts.get(e.getSource()))){
                        if (transfer.getAccountIn().equals(AccountsPanel.ItemsAccounts.get(e.getSource())))
                            transfer.setUnknownAccountIn();
                        else
                            transfer.setUnknownAccountOut();
                        line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.valueOf(transfer.getSum())+" UAH";
                        OperationsPanel.TButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                    }
                }
                AccountsPanel.AToolbar.remove(AccountsPanel.AButtons.get(AccountsPanel.ItemsAccounts.get(e.getSource())).getParent());
                AccountsPanel.AToolbar.revalidate();
                JButton button=AccountsPanel.AButtons.get(AccountsPanel.ItemsAccounts.get(e.getSource()));
                AccountsPanel.AButtons.remove(AccountsPanel.ItemsAccounts.get(e.getSource()));
                Swing.user.deleteAccount(AccountsPanel.ItemsAccounts.get(e.getSource()));
                for (int i=0;i<3;i++)
                    AccountsPanel.ItemsAccounts.remove(button.getComponentPopupMenu().getComponent(0));
                AccountsPanel.balanceLabel.setText(String.valueOf(Swing.user.getBalance()));
                AccountsPanel.panel.revalidate();
                UsersChanger.writeUsersInFile(Swing.users);
                JOptionPane.showMessageDialog(Swing.frame,"Account has been successfully deleted!");
            }
        });
        AccountsPanel.ItemsAccounts.put(addIncomeItem,account);
        AccountsPanel.ItemsAccounts.put(transferItem,account);
        AccountsPanel.ItemsAccounts.put(deleteItem,account);
    }
}
