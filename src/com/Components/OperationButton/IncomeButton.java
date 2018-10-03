package com.Components.OperationButton;

import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.company.AccountsPanel;
import com.company.OperationsPanel;
import com.company.Swing;

public class IncomeButton extends OperationButton {
    @Override
    void deleteOperation(Operation operation) {
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
    public IncomeButton(String text, Income income) {
        super(text, income);
    }
}
