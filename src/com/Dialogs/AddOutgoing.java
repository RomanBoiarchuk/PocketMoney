package com.Dialogs;

import com.Components.OperationButton;
import com.PocketMoney.Outgoing;
import com.company.*;

import javax.swing.*;
import java.awt.*;

public class AddOutgoing extends AddOperation {
    public AddOutgoing(String category) {
        super("Outgoing");
        add(new JLabel("Account "), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        JComboBox<String> accountBox = new JComboBox<>();
        for (String key : Swing.user.getAccountsKeys()) {
            accountBox.addItem(key);
        }
        add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 0, 0), 0, 0));
        JButton confirm = new JButton("Confirm");
        JButton cancel = new JButton("Cancel");
        add(confirm, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(40, 0, 0, 0), 0, 0));
        add(cancel, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(40, 20, 0, 0), 0, 0));
        cancel.addActionListener(e -> dispose());
        confirm.addActionListener(e -> {
            String account = accountBox.getItemAt(accountBox.getSelectedIndex());
            double sum = 0;
            boolean isError = false;
            try {
                sum = Double.valueOf(sumField.getText().replaceAll(",", "."));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Swing.frame, "Incorrect input in sum field!", "Error", JOptionPane.ERROR_MESSAGE);
                isError = true;
            }
            if (!isError) {
                if (comment.getText().trim().equals("") || comment.getText().equals("Comment"))
                    Swing.user.addOutgoing(sum,category, account);
                else
                    Swing.user.addOutgoing(sum,category, account, comment.getText());
                Outgoing outgoing = Swing.user.getOutgoing(Swing.user.getOutgoingsSize() - 1);
                // editing data in panels and adding operation button
                AccountsPanel.AButtons.get(account).setText(account + ": " + String.format("%.2f",Swing.user.getAccountBalance(account)) + " UAH");
                CategoriesPanel.categoriesMap.replace(outgoing.getGoal(),CategoriesPanel.categoriesMap.get(outgoing.getGoal())+outgoing.getSum());
                CategoriesPanel.CButtons.get(category).setText(outgoing.getGoal() + ": " + String.format("%.2f",CategoriesPanel.categoriesMap.get(outgoing.getGoal())) + " UAH");
                AccountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                String line = outgoing.getDate() + "\n" + "from \"" + outgoing.getAccount() + "\" to \"" + outgoing.getGoal() + "\": " + String.format("%.2f",outgoing.getSum()) + " UAH";
                JButton operationButton = new OperationButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>",outgoing);
                OperationsPanel.OButtons.put(OperationsPanel.OToolbar.getComponentCount(), operationButton);
                OperationsPanel.OToolbar.add(operationButton);
                JOptionPane.showMessageDialog(Swing.frame, "Outgoing has benn successfully added!");
                UsersChanger.writeUsersInFile(Swing.users);
                dispose();
            }
        });
    }
}
