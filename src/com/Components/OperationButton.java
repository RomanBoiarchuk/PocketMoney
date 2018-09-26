package com.Components;

import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;
import com.company.*;
import javax.swing.*;

public class OperationButton extends JButton {
    public OperationButton(String text, Operation operation){
        super(text);
        addActionListener(e -> {
            OperationsPanel.OperationsDetails operationsDetails=new OperationsPanel.OperationsDetails(operation);
            operationsDetails.setVisible(true);
        });
        JPopupMenu popupMenu=new JPopupMenu();
        JMenuItem deleteItem=new JMenuItem("delete");
        deleteItem.addActionListener(e -> {
            int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                    "Delete operation",JOptionPane.YES_NO_OPTION);
            if (option==0){
                if (operation.getClass().equals(Income.class)) {
                    Swing.user.deleteIncome(OperationsPanel.IToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)));
                    OperationsPanel.IToolbar.remove(OperationsPanel.OperationsButtons.get(operation));
                    OperationsPanel.IButtons.remove(OperationsPanel.IToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)));
                    AccountsPanel.AButtons.get(((Income)operation).getAccount()).setText(((Income)operation).getAccount()+": "+
                            String.format("%.2f", Swing.user.getAccountBalance(((Income)operation).getAccount()))+" UAH");
                    OperationsPanel.IToolbar.revalidate();
                    if (((Income)operation).getAccount().equals("Unknown")){
                        deleteItem.setEnabled(false);
                    }
                }
                if (operation.getClass().equals(Outgoing.class)) {
                    Swing.user.deleteOutgoing(OperationsPanel.OToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)));
                    OperationsPanel.OToolbar.remove(OperationsPanel.OperationsButtons.get(operation));
                    OperationsPanel.OButtons.remove(OperationsPanel.OToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)));
                    AccountsPanel.AButtons.get(((Outgoing)operation).getAccount()).setText(((Outgoing)operation).getAccount()+": "
                            +String.format("%.2f", Swing.user.getAccountBalance(((Outgoing)operation).getAccount()))+" UAH");
                    CategoriesPanel.categoriesMap.replace(((Outgoing)operation).getGoal(),CategoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())- ((Outgoing)operation).getSum());
                    CategoriesPanel.CButtons.get(((Outgoing)operation).getGoal()).setText(((Outgoing)operation).getGoal() + ": "
                            + String.format("%.2f",CategoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())) + " UAH");
                    OperationsPanel.OToolbar.revalidate();
                    if (((Outgoing)operation).getAccount().equals("Unknown") || ((Outgoing)operation).getGoal().equals("Unknown")){
                        deleteItem.setEnabled(false);
                    }
                }
                if (operation.getClass().equals(Transfer.class)) {
                    Swing.user.deleteTransfer(OperationsPanel.TToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)));
                    OperationsPanel.TToolbar.remove(OperationsPanel.OperationsButtons.get(operation));
                    OperationsPanel.TButtons.remove(OperationsPanel.TToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)));
                    AccountsPanel.AButtons.get(((Transfer)operation).getAccountIn()).setText(((Transfer)operation).getAccountIn()+": "
                            +String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountIn()))+" UAH");
                    AccountsPanel.AButtons.get(((Transfer)operation).getAccountOut()).setText(((Transfer)operation).getAccountOut()
                            +": "+String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountOut()))+" UAH");
                    OperationsPanel.TToolbar.revalidate();
                    if (((Transfer)operation).getAccountIn().equals("Unknown") || ((Transfer)operation).getAccountOut().equals("Unknown")){
                        deleteItem.setEnabled(false);
                    }
                }
                AccountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                UsersChanger.writeUsersInFile(Swing.users);
            }
        });
        popupMenu.add(deleteItem);
        setComponentPopupMenu(popupMenu);
        OperationsPanel.ItemsOperations.put(deleteItem,operation);
        OperationsPanel.OperationsButtons.put(operation,this);
    }

}
