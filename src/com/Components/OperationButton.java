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
            operationsPanel.OperationsDetails operationsDetails=new operationsPanel.OperationsDetails(operation);
            operationsDetails.setVisible(true);
        });
        JPopupMenu popupMenu=new JPopupMenu();
        JMenuItem deleteItem=new JMenuItem("delete");
        deleteItem.addActionListener(e -> {
            int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                    "Delete operation",JOptionPane.YES_NO_OPTION);
            if (option==0){
                if (operation.getClass().equals(Income.class)) {
                    Swing.user.deleteIncome(operationsPanel.IToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(operation)));
                    operationsPanel.IToolbar.remove(operationsPanel.OperationsButtons.get(operation));
                    operationsPanel.IButtons.remove(operationsPanel.IToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(operation)));
                    accountsPanel.AButtons.get(((Income)operation).getAccount()).setText(((Income)operation).getAccount()+": "+
                            String.format("%.2f", Swing.user.getAccountBalance(((Income)operation).getAccount()))+" UAH");
                    operationsPanel.IToolbar.revalidate();
                    if (((Income)operation).getAccount().equals("Unknown")){
                        deleteItem.setEnabled(false);
                    }
                }
                if (operation.getClass().equals(Outgoing.class)) {
                    Swing.user.deleteOutgoing(operationsPanel.IToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(operation)));
                    operationsPanel.OToolbar.remove(operationsPanel.OperationsButtons.get(operation));
                    operationsPanel.OButtons.remove(operationsPanel.OToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(operation)));
                    accountsPanel.AButtons.get(((Outgoing)operation).getAccount()).setText(((Outgoing)operation).getAccount()+": "
                            +String.format("%.2f", Swing.user.getAccountBalance(((Outgoing)operation).getAccount()))+" UAH");
                    categoriesPanel.categoriesMap.replace(((Outgoing)operation).getGoal(),categoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())- ((Outgoing)operation).getSum());
                    categoriesPanel.CButtons.get(((Outgoing)operation).getGoal()).setText(((Outgoing)operation).getGoal() + ": "
                            + String.format("%.2f",categoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())) + " UAH");
                    operationsPanel.OToolbar.revalidate();
                    if (((Outgoing)operation).getAccount().equals("Unknown") || ((Outgoing)operation).getGoal().equals("Unknown")){
                        deleteItem.setEnabled(false);
                    }
                }
                if (operation.getClass().equals(Transfer.class)) {
                    Swing.user.deleteTransfer(operationsPanel.IToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(operation)));
                    operationsPanel.TToolbar.remove(operationsPanel.OperationsButtons.get(operation));
                    operationsPanel.TButtons.remove(operationsPanel.TToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(operation)));
                    accountsPanel.AButtons.get(((Transfer)operation).getAccountIn()).setText(((Transfer)operation).getAccountIn()+": "
                            +String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountIn()))+" UAH");
                    accountsPanel.AButtons.get(((Transfer)operation).getAccountOut()).setText(((Transfer)operation).getAccountOut()
                            +": "+String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountOut()))+" UAH");
                    operationsPanel.TToolbar.revalidate();
                    if (((Transfer)operation).getAccountIn().equals("Unknown") || ((Transfer)operation).getAccountOut().equals("Unknown")){
                        deleteItem.setEnabled(false);
                    }
                }
                accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                Functions.writeUsersInFile(Swing.users);
            }
        });
        popupMenu.add(deleteItem);
        setComponentPopupMenu(popupMenu);
        operationsPanel.ItemsOperations.put(deleteItem,operation);
        operationsPanel.OperationsButtons.put(operation,this);
    }

}
