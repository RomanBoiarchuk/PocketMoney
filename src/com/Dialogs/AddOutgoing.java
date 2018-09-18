package com.Dialogs;

import com.Components.CommentArea;
import com.Components.OperationButton;
import com.PocketMoney.Outgoing;
import com.company.*;

import javax.swing.*;
import java.awt.*;

public class AddOutgoing extends JDialog {
    public AddOutgoing(JButton button) {
        super(Swing.frame, "Outgoing", true);
        // decorating dialog window
        setBackground(Color.orange);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int height = 300;
        int width = 500;
        setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height);
        setLayout(new GridBagLayout());
        add(new JLabel("Account "), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        JComboBox<String> accountBox = new JComboBox<>();
        for (String key : Swing.user.getAccountsKeys()) {
            accountBox.addItem(key);
        }
        add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 0, 0), 0, 0));
        add(new JLabel("Sum "), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 0, 0), 0, 0));
        JTextField sumField = new JTextField(20);
        add(sumField, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 20, 0, 0), 0, 0));
        JTextArea comment = new CommentArea("Comment", 3, 5);
        JScrollPane commentPane = new JScrollPane(comment);
        add(commentPane, new GridBagConstraints(0, 2, 6, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 0, 0), 100, 80));
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
                    Swing.user.addOutgoing(sum, categoriesPanel.BCategories.get(button), account);
                else
                    Swing.user.addOutgoing(sum, categoriesPanel.BCategories.get(button), account, comment.getText());
                Outgoing outgoing = Swing.user.getOutgoing(Swing.user.getOutgoingsSize() - 1);
                // editing data in panels and adding operation button
                accountsPanel.AButtons.get(account).setText(account + ": " + String.format("%.2f",Swing.user.getAccountBalance(account)) + " UAH");
                categoriesPanel.categoriesMap.replace(outgoing.getGoal(),categoriesPanel.categoriesMap.get(outgoing.getGoal())+outgoing.getSum());
                button.setText(outgoing.getGoal() + ": " + String.format("%.2f",categoriesPanel.categoriesMap.get(outgoing.getGoal())) + " UAH");
                accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                String line = outgoing.getDate() + "\n" + "from \"" + outgoing.getAccount() + "\" to \"" + outgoing.getGoal() + "\": " + String.format("%.2f",outgoing.getSum()) + " UAH";
                JButton operationButton = new OperationButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>",outgoing);
                operationsPanel.OButtons.put(operationsPanel.OToolbar.getComponentCount(), operationButton);
                operationsPanel.OToolbar.add(operationButton);
                JOptionPane.showMessageDialog(Swing.frame, "Outgoing has benn successfully added!");
                Functions.writeUsersInFile(Swing.users);
                dispose();
            }
        });
    }
}
