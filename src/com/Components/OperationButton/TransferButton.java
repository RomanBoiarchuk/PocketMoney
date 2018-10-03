package com.Components.OperationButton;

import com.PocketMoney.Operation;
import com.PocketMoney.Transfer;
import com.company.AccountsPanel;
import com.company.OperationsPanel;
import com.company.Swing;

public class TransferButton extends OperationButton {
    @Override
    void deleteOperation(Operation operation) {
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

    public TransferButton(String text, Transfer transfer) {
        super(text, transfer);
    }
}
