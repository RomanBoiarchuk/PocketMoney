package com.Components.OperationButton;

import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.company.AccountsPanel;
import com.company.CategoriesPanel;
import com.company.OperationsPanel;
import com.company.Swing;

public class OutgoingButton extends OperationButton{
    @Override
    void deleteOperation(Operation operation) {
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

    public OutgoingButton(String text, Outgoing outgoing) {
        super(text, outgoing);
    }
}
