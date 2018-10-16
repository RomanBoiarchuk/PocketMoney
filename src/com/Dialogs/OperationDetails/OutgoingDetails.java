package com.Dialogs.OperationDetails;

import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.Frame.*;

import javax.swing.*;
import java.awt.*;

public class OutgoingDetails extends OperationDetails {
    @Override
    void changeSum(Operation operation,double newSum) {
        double oldSum=operation.getSum();
        Swing.user.setOutgoingSum(OperationsPanel.OToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)),newSum);
        AccountsPanel.AButtons.get(((Outgoing)operation).getAccount()).setText(((Outgoing)operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Outgoing)operation).getAccount()))+" UAH");
        AccountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
        sumLabel.setText(String.format("%.2f",operation.getSum())+" UAH");
        String line=operation.getDate()+"\n"+"from \""+((Outgoing)operation).getAccount()+"\" to \""+((Outgoing)operation).getGoal()+"\": "+String.format("%.2f",newSum)+" UAH";
        OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
        CategoriesPanel.categoriesMap.replace(((Outgoing)operation).getGoal(),CategoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())-oldSum+newSum);
        CategoriesPanel.CButtons.get(((Outgoing)operation).getGoal()).setText(((Outgoing)operation).getGoal() + ": " + String.format("%.2f",CategoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())) + " UAH");
        UsersChanger.writeUsersInFile(Swing.users);
        Swing.accountsPanel.revalidate();
        Swing.categoriesPanel.revalidate();
        Swing.operationsPanel.revalidate();
    }

    public OutgoingDetails(Outgoing outgoing){
        super(outgoing);
        JLabel accountLabel=new JLabel(outgoing.getAccount());
        JPanel tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(new JLabel("Account: "),BorderLayout.WEST);
        tempPanel.add(accountLabel,BorderLayout.EAST);
        add(tempPanel,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JButton editAccountButton=new JButton("edit");
        editAccountButton.addActionListener(e -> {
            JDialog dialog=new FieldChangerWindow("OutgoingButton"){
                {
                    JComboBox<String> accountBox=new JComboBox<>();
                    for (String key : Swing.user.getAccountsKeys()) {
                        accountBox.addItem(key);
                    }
                    accountBox.setSelectedItem(outgoing.getAccount());
                    add(new JLabel("Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e13 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                        if (!accountBox.getSelectedItem().equals(outgoing.getAccount())) {
                            String oldAccount=outgoing.getAccount();
                            Swing.user.setOutgoingAccount(OperationsPanel.OToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(outgoing)), (String)accountBox.getSelectedItem());
                            String line = outgoing.getDate() + "\n" + "from \"" + outgoing.getAccount() + "\" to \"" + outgoing.getGoal() + "\": " + String.format("%.2f", outgoing.getSum()) + " UAH";
                            OperationsPanel.OperationsButtons.get(outgoing).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            accountLabel.setText(outgoing.getAccount());
                            AccountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                            AccountsPanel.AButtons.get(outgoing.getAccount()).setText(outgoing.getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(outgoing.getAccount()))+" UAH");
                            UsersChanger.writeUsersInFile(Swing.users);
                            Swing.accountsPanel.revalidate();
                            Swing.categoriesPanel.revalidate();
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
        add(editAccountButton,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,15,0,0),0,0));
        JLabel categoryLabel=new JLabel(outgoing.getGoal());
        tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(new JLabel("Category: "),BorderLayout.WEST);
        tempPanel.add(categoryLabel,BorderLayout.EAST);
        add(tempPanel,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JButton editCategoryButton=new JButton("edit");

        editCategoryButton.addActionListener(e -> {
            JDialog dialog=new FieldChangerWindow("OutgoingButton"){
                {
                    JComboBox<String> categoriesBox=new JComboBox<>();
                    for (String key : Swing.user.getOutgoingsCategories().keySet()) {
                        categoriesBox.addItem(key);
                    }
                    categoriesBox.setSelectedItem(outgoing.getGoal());
                    add(new JLabel("Category"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    add(categoriesBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 20, 0, 0), 0, 0));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e14 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                        if (!categoriesBox.getSelectedItem().equals(outgoing.getGoal())) {
                            String oldCategory=outgoing.getGoal();
                            String newCategory=(String)categoriesBox.getSelectedItem();
                            Swing.user.setOutgoingGoal(OperationsPanel.OToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(outgoing)), (String)categoriesBox.getSelectedItem());
                            String line = outgoing.getDate() + "\n" + "from \"" + outgoing.getAccount() + "\" to \"" + outgoing.getGoal() + "\": " + String.format("%.2f", outgoing.getSum()) + " UAH";
                            OperationsPanel.OperationsButtons.get(outgoing).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                            categoryLabel.setText(newCategory);
                            CategoriesPanel.categoriesMap.replace(oldCategory,CategoriesPanel.categoriesMap.get(oldCategory)-outgoing.getSum());
                            CategoriesPanel.categoriesMap.replace(newCategory,CategoriesPanel.categoriesMap.get(newCategory)+outgoing.getSum());
                            CategoriesPanel.CButtons.get(oldCategory).setText(oldCategory + ": " + String.format("%.2f",CategoriesPanel.categoriesMap.get(oldCategory)) + " UAH");
                            CategoriesPanel.CButtons.get(newCategory).setText(newCategory + ": " + String.format("%.2f",CategoriesPanel.categoriesMap.get(newCategory)) + " UAH");
                            UsersChanger.writeUsersInFile(Swing.users);
                            Swing.categoriesPanel.revalidate();
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

        add(editCategoryButton,new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,15,0,0),0,0));
        if (outgoing.getAccount().equals("Unknown") || outgoing.getGoal().equals("Unknown")){
            editAccountButton.setEnabled(false);
            editCategoryButton.setEnabled(false);
            editSumButton.setEnabled(false);
        }
    }
}
