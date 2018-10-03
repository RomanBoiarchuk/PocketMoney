package com.Components.OperationButton;

import com.Dialogs.OperationDetails.*;
import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;
import com.company.*;
import javax.swing.*;

public abstract class OperationButton extends JButton {
    abstract void deleteOperation(Operation operation);
    JMenuItem deleteItem;
    public OperationButton(String text, Operation operation){
        super(text);
        addActionListener(e -> {
            OperationDetails operationDetails =(operation.getClass().equals(Income.class))?new IncomeDetails((Income)operation):
                    (operation.getClass().equals(Outgoing.class))?new OutgoingDetails((Outgoing)operation):new TransferDetails((Transfer)operation);
            operationDetails.setVisible(true);
        });
        JPopupMenu popupMenu=new JPopupMenu();
        deleteItem=new JMenuItem("delete");
        deleteItem.addActionListener(e -> {
            int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                    "Delete operation",JOptionPane.YES_NO_OPTION);
            if (option==0){
                deleteOperation(operation);
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
