package com.company;

import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class operationsPanel {
    static Map<Integer,JButton> OButtons;
    static JToolBar OToolbar;
    static JToolBar IToolbar;
    static JToolBar TToolbar;
    static Map<Integer,JButton> IButtons;
    static Map<Integer,JButton> TButtons;
    static Map<JMenuItem,Operation> ItemsOperations;
    static Map<Operation,JButton> OperationsButtons;
    static JPanel getPanel(){
        JPanel panel=new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new GridBagLayout());
        IButtons=new HashMap<>();
        OButtons=new HashMap<>();
        TButtons=new HashMap<>();
        TToolbar=new JToolBar("Transfers");
        IToolbar=new JToolBar("Incomes");
        OToolbar=new JToolBar("Outgoings");
        TToolbar.setFloatable(false);
        IToolbar.setFloatable(false);
        OToolbar.setFloatable(false);
        OperationsButtons=new HashMap<>();
        ItemsOperations=new HashMap<>();
        JButton tempButton;
        Income income;
        Outgoing outgoing;
        Transfer transfer;
        String line;
        JPopupMenu popupMenu;
        JMenuItem editItem;
        JMenuItem deleteItem;
        for (int i=0;i<Swing.user.getIncomesSize();i++) {
            income=Swing.user.getIncome(i);
            line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+income.getAccount()+"\": "+String.format("%.2f",income.getSum())+" UAH";
            tempButton=new JButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
            tempButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    OperationsDetails operationsDetails=new OperationsDetails(Swing.user.getIncome(IToolbar.getComponentIndex((JButton)e.getSource())));
                    operationsDetails.setVisible(true);
                }
            });
            popupMenu=new JPopupMenu();
            deleteItem=new JMenuItem("delete");
            deleteItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                            "Delete operation",JOptionPane.YES_NO_OPTION);
                    if (option==0){
                        Income income=(Income)ItemsOperations.get(e.getSource());
                        Swing.user.deleteIncome(IToolbar.getComponentIndex(OperationsButtons.get(income)));
                        IToolbar.remove(OperationsButtons.get(income));
                        IButtons.remove(income);
                        accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                        accountsPanel.AButtons.get(income.getAccount()).setText(income.getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(income.getAccount()))+" UAH");
                        IToolbar.revalidate();
                        Main.writeUsersInFile(Swing.users);
                    }
                }
            });
            popupMenu.add(deleteItem);
            tempButton.setComponentPopupMenu(popupMenu);
            if (income.getAccount().equals("Unknown")){
                deleteItem.setEnabled(false);
            }
            ItemsOperations.put(deleteItem,income);
            OperationsButtons.put(income,tempButton);
            IToolbar.add(tempButton);
            IButtons.put(i,tempButton);
        }
        IToolbar.setBackground(Color.ORANGE);
        panel.add(new JLabel("Incomes"),new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JScrollPane IncomePane=new JScrollPane();
        IncomePane.setViewportView(IToolbar);
        IncomePane.setPreferredSize(new Dimension(100,100));
        IToolbar.setLayout(new GridLayout());
        panel.add(IncomePane,new GridBagConstraints(0,3,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(10,0,0,0),0,0));
        for (int i=0;i<Swing.user.getTransfersSize();i++) {
            transfer=Swing.user.getTransfer(i);
                line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.format("%.2f",transfer.getSum())+" UAH";
            tempButton=new JButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
            tempButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    OperationsDetails operationsDetails=new OperationsDetails(Swing.user.getTransfer(TToolbar.getComponentIndex((JButton)e.getSource())));
                    operationsDetails.setVisible(true);
                }
            });
            popupMenu=new JPopupMenu();
            deleteItem=new JMenuItem("delete");
            deleteItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                            "Delete operation",JOptionPane.YES_NO_OPTION);
                    if (option==0){
                        Transfer transfer=(Transfer) ItemsOperations.get(e.getSource());
                        Swing.user.deleteTransfer(TToolbar.getComponentIndex(OperationsButtons.get(transfer)));
                        TToolbar.remove(OperationsButtons.get(transfer));
                        TButtons.remove(transfer);
                        accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                        accountsPanel.AButtons.get(transfer.getAccountIn()).setText(transfer.getAccountIn()+": "+String.format("%.2f", Swing.user.getAccountBalance(transfer.getAccountIn()))+" UAH");
                        accountsPanel.AButtons.get(transfer.getAccountOut()).setText(transfer.getAccountOut()+": "+String.format("%.2f", Swing.user.getAccountBalance(transfer.getAccountOut()))+" UAH");
                        TToolbar.revalidate();
                        Main.writeUsersInFile(Swing.users);
                    }
                }
            });
            popupMenu.add(deleteItem);
            tempButton.setComponentPopupMenu(popupMenu);
            if (transfer.getAccountIn().equals("Unknown") || transfer.getAccountOut().equals("Unknown")){
                deleteItem.setEnabled(false);
            }
            ItemsOperations.put(deleteItem,transfer);
            OperationsButtons.put(transfer,tempButton);
            TToolbar.add(tempButton);
            TButtons.put(i,tempButton);
        }
        TToolbar.setBackground(Color.ORANGE);
        panel.add(new JLabel("Transfers"),new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JScrollPane TransferPane=new JScrollPane();
        TransferPane.setViewportView(TToolbar);
        TransferPane.setPreferredSize(new Dimension(100,100));
        TToolbar.setLayout(new GridLayout());
        panel.add(TransferPane,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(10,0,0,0),0,0));;
        for (int i=0;i<Swing.user.getOutgoingsSize();i++) {
            outgoing=Swing.user.getOutgoing(i);
                line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.format("%.2f",outgoing.getSum())+" UAH";
            tempButton=new JButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
            tempButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    OperationsDetails operationsDetails=new OperationsDetails(Swing.user.getOutgoing(OToolbar.getComponentIndex((JButton)e.getSource())));
                    operationsDetails.setVisible(true);
                }
            });
            popupMenu=new JPopupMenu();
            deleteItem=new JMenuItem("delete");
            deleteItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                            "Delete operation",JOptionPane.YES_NO_OPTION);
                    if (option==0){
                        Outgoing outgoing=(Outgoing) ItemsOperations.get(e.getSource());
                        Swing.user.deleteOutgoing(OToolbar.getComponentIndex(OperationsButtons.get(outgoing)));
                        OToolbar.remove(OperationsButtons.get(outgoing));
                        OButtons.remove(outgoing);
                        accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                        accountsPanel.AButtons.get(outgoing.getAccount()).setText(outgoing.getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(outgoing.getAccount()))+" UAH");
                        categoriesPanel.categoriesMap.replace(outgoing.getGoal(),categoriesPanel.categoriesMap.get(outgoing.getGoal())-outgoing.getSum());
                        categoriesPanel.CButtons.get(outgoing.getGoal()).setText(outgoing.getGoal() + ": " + String.format("%.2f",categoriesPanel.categoriesMap.get(outgoing.getGoal())) + " UAH");
                        OToolbar.revalidate();
                        Swing.frame.revalidate();
                        Main.writeUsersInFile(Swing.users);
                    }
                }
            });
            popupMenu.add(deleteItem);
            tempButton.setComponentPopupMenu(popupMenu);
            if (outgoing.getAccount().equals("Unknown") || outgoing.getGoal().equals("Unknown")){
                deleteItem.setEnabled(false);
            }
            ItemsOperations.put(deleteItem,outgoing);
            OperationsButtons.put(outgoing,tempButton);
            OToolbar.add(tempButton);
            OButtons.put(i,tempButton);
        }
        OToolbar.setBackground(Color.ORANGE);
        panel.add(new JLabel("Outgoings"),new GridBagConstraints(0,4,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JScrollPane OutgoingPane= new JScrollPane();
        OutgoingPane.setViewportView(OToolbar);
        OutgoingPane.setPreferredSize(new Dimension(250,100));
        OToolbar.setLayout(new GridLayout());
        panel.add(OutgoingPane,new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(10,0,0,0),0,0));
        return panel;
    }

    static class OperationsDetails extends JDialog{
        public OperationsDetails(Operation operation){
            super(Swing.frame,"Operation",true);
            setLayout(new GridBagLayout());
            Toolkit toolkit=Toolkit.getDefaultToolkit();
            Dimension dimension=toolkit.getScreenSize();
            int height=450;
            int width=500;
            setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
            boolean unknownComponent=false; // to check if there is unknown field within operation's fields
            JPanel tempPanel;
            if (operation.getClass().equals(Income.class)){
                JLabel sourceLabel=new JLabel(((Income)operation).getSource());
                tempPanel=new JPanel();
                tempPanel.setLayout(new BorderLayout());
                tempPanel.add(new JLabel("Source: "),BorderLayout.WEST);
                tempPanel.add(sourceLabel,BorderLayout.EAST);
                add(tempPanel,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(30,0,0,0),0,0));
                JButton editSourceButton=new JButton("edit");
                editSourceButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialog dialog=new JDialog(Swing.frame,"Income",true){
                            {
                                Toolkit toolkit=Toolkit.getDefaultToolkit();
                                Dimension dimension=toolkit.getScreenSize();
                                int height=200;
                                int width=300;
                                setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
                                setLayout(new GridBagLayout());
                                JTextField sourceField=new JTextField(20);
                                sourceField.setAutoscrolls(false);
                                add(new JLabel("Source"),new GridBagConstraints(0,0,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(0,0,0,0),0,0));
                                add(sourceField,new GridBagConstraints(0,1,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(20,0,0,0),0,0));
                                JButton cancelButton=new JButton("Cancel");
                                cancelButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dispose();
                                    }
                                });
                                JButton confirmButton=new JButton("Confirm");
                                confirmButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (sourceField.getText().trim().equals("")){
                                            JOptionPane.showMessageDialog(Swing.frame,"Incorrect input!","Error",JOptionPane.ERROR_MESSAGE);
                                        }
                                        else {
                                            Swing.user.setIncomeSource(IToolbar.getComponentIndex(OperationsButtons.get(operation)), sourceField.getText());
                                            String line=operation.getDate()+"\n"+"from \""+((Income)operation).getSource()+"\" to \""+((Income)operation).getAccount()+"\": "+String.format("%.2f",operation.getSum())+" UAH";
                                            OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                            sourceLabel.setText(((Income) operation).getSource());
                                            Main.writeUsersInFile(Swing.users);
                                            Swing.operationsPanel.revalidate();
                                            dispose();
                                        }
                                    }
                                });
                                add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(20,0,0,0),0,0));
                                add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(20,20,0,0),0,0));
                            }
                        };
                        dialog.setVisible(true);
                    }
                });
                add(editSourceButton,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(30,15,0,0),0,0));
                JLabel accountLabel=new JLabel(((Income)operation).getAccount());
                tempPanel=new JPanel();
                tempPanel.setLayout(new BorderLayout());
                tempPanel.add(new JLabel("Account: "),BorderLayout.WEST);
                tempPanel.add(accountLabel,BorderLayout.EAST);
                add(tempPanel,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(30,0,0,0),0,0));
                JButton editAccountButton=new JButton("edit");
                editAccountButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialog dialog=new JDialog(Swing.frame,"Income",true){
                            {
                                Toolkit toolkit=Toolkit.getDefaultToolkit();
                                Dimension dimension=toolkit.getScreenSize();
                                int height=200;
                                int width=300;
                                setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
                                setLayout(new GridBagLayout());
                                JComboBox<String> accountBox=new JComboBox<>();
                                for (String key : Swing.user.getAccountsKeys()) {
                                    accountBox.addItem(key);
                                }
                                accountBox.setSelectedItem(((Income) operation).getAccount());
                                add(new JLabel("Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 20, 0, 0), 0, 0));
                                add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 20, 0, 0), 0, 0));
                                JButton cancelButton=new JButton("Cancel");
                                cancelButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dispose();
                                    }
                                });
                                JButton confirmButton=new JButton("Confirm");
                                confirmButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (!accountBox.getSelectedItem().equals(((Income) operation).getAccount())) {
                                            String oldAccount=((Income) operation).getAccount();
                                            Swing.user.setIncomeAccount(IToolbar.getComponentIndex(OperationsButtons.get(operation)), (String)accountBox.getSelectedItem());
                                            String line = operation.getDate() + "\n" + "from \"" + ((Income) operation).getSource() + "\" to \"" + ((Income) operation).getAccount() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                            OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                            accountLabel.setText(((Income) operation).getAccount());
                                            accountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                            accountsPanel.AButtons.get(((Income) operation).getAccount()).setText(((Income) operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Income) operation).getAccount()))+" UAH");
                                            Swing.accountsPanel.revalidate();
                                            Swing.operationsPanel.revalidate();
                                            Main.writeUsersInFile(Swing.users);
                                        }
                                            dispose();
                                    }
                                });
                                add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(20,0,0,0),0,0));
                                add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(20,20,0,0),0,0));
                            }
                        };
                        dialog.setVisible(true);
                    }
                });
                add(editAccountButton,new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(30,15,0,0),0,0));
                if (((Income) operation).getAccount().equals("Unknown")){
                    editSourceButton.setEnabled(false);
                    editAccountButton.setEnabled(false);
                    unknownComponent=true;
                }
            }
            else
                if (operation.getClass().equals(Outgoing.class)){
                    JLabel accountLabel=new JLabel(((Outgoing)operation).getAccount());
                    tempPanel=new JPanel();
                    tempPanel.setLayout(new BorderLayout());
                    tempPanel.add(new JLabel("Account: "),BorderLayout.WEST);
                    tempPanel.add(accountLabel,BorderLayout.EAST);
                    add(tempPanel,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,0,0,0),0,0));
                    JButton editAccountButton=new JButton("edit");
                    editAccountButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JDialog dialog=new JDialog(Swing.frame,"Outgoing",true){
                                {
                                    Toolkit toolkit=Toolkit.getDefaultToolkit();
                                    Dimension dimension=toolkit.getScreenSize();
                                    int height=200;
                                    int width=300;
                                    setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
                                    setLayout(new GridBagLayout());
                                    JComboBox<String> accountBox=new JComboBox<>();
                                    for (String key : Swing.user.getAccountsKeys()) {
                                        accountBox.addItem(key);
                                    }
                                    accountBox.setSelectedItem(((Outgoing) operation).getAccount());
                                    add(new JLabel("Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    JButton cancelButton=new JButton("Cancel");
                                    cancelButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            dispose();
                                        }
                                    });
                                    JButton confirmButton=new JButton("Confirm");
                                    confirmButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (!accountBox.getSelectedItem().equals(((Outgoing) operation).getAccount())) {
                                                String oldAccount=((Outgoing) operation).getAccount();
                                                Swing.user.setOutgoingAccount(OToolbar.getComponentIndex(OperationsButtons.get(operation)), (String)accountBox.getSelectedItem());
                                                String line = operation.getDate() + "\n" + "from \"" + ((Outgoing) operation).getAccount() + "\" to \"" + ((Outgoing) operation).getGoal() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                                OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                                accountLabel.setText(((Outgoing) operation).getAccount());
                                                accountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                                accountsPanel.AButtons.get(((Outgoing) operation).getAccount()).setText(((Outgoing) operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Outgoing) operation).getAccount()))+" UAH");
                                                Main.writeUsersInFile(Swing.users);
                                                Swing.accountsPanel.revalidate();
                                                Swing.categoriesPanel.revalidate();
                                                Swing.operationsPanel.revalidate();
                                            }
                                            dispose();
                                        }
                                    });
                                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,0,0,0),0,0));
                                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,20,0,0),0,0));
                                }
                            };
                            dialog.setVisible(true);
                        }
                    });
                    add(editAccountButton,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,15,0,0),0,0));
                    JLabel categoryLabel=new JLabel(((Outgoing)operation).getGoal());
                    tempPanel=new JPanel();
                    tempPanel.setLayout(new BorderLayout());
                    tempPanel.add(new JLabel("Category: "),BorderLayout.WEST);
                    tempPanel.add(categoryLabel,BorderLayout.EAST);
                    add(tempPanel,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,0,0,0),0,0));
                    JButton editCategoryButton=new JButton("edit");

                    editCategoryButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JDialog dialog=new JDialog(Swing.frame,"Outgoing",true){
                                {
                                    Toolkit toolkit=Toolkit.getDefaultToolkit();
                                    Dimension dimension=toolkit.getScreenSize();
                                    int height=200;
                                    int width=300;
                                    setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
                                    setLayout(new GridBagLayout());
                                    JComboBox<String> categoriesBox=new JComboBox<>();
                                    for (String key : Swing.user.getOutgoingsCategories().keySet()) {
                                        categoriesBox.addItem(key);
                                    }
                                    categoriesBox.setSelectedItem(((Outgoing) operation).getGoal());
                                    add(new JLabel("Category"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    add(categoriesBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    JButton cancelButton=new JButton("Cancel");
                                    cancelButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            dispose();
                                        }
                                    });
                                    JButton confirmButton=new JButton("Confirm");
                                    confirmButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (!categoriesBox.getSelectedItem().equals(((Outgoing) operation).getGoal())) {
                                                String oldCategory=((Outgoing) operation).getGoal();
                                                String newCategory=(String)categoriesBox.getSelectedItem();
                                                Swing.user.setOutgoingGoal(OToolbar.getComponentIndex(OperationsButtons.get(operation)), (String)categoriesBox.getSelectedItem());
                                                String line = operation.getDate() + "\n" + "from \"" + ((Outgoing) operation).getAccount() + "\" to \"" + ((Outgoing) operation).getGoal() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                                OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                                categoryLabel.setText(newCategory);
                                                categoriesPanel.categoriesMap.replace(oldCategory,categoriesPanel.categoriesMap.get(oldCategory)-operation.getSum());
                                                categoriesPanel.categoriesMap.replace(newCategory,categoriesPanel.categoriesMap.get(newCategory)+operation.getSum());
                                                categoriesPanel.CButtons.get(oldCategory).setText(oldCategory + ": " + String.format("%.2f",categoriesPanel.categoriesMap.get(oldCategory)) + " UAH");
                                                categoriesPanel.CButtons.get(newCategory).setText(newCategory + ": " + String.format("%.2f",categoriesPanel.categoriesMap.get(newCategory)) + " UAH");
                                                Main.writeUsersInFile(Swing.users);
                                                Swing.categoriesPanel.revalidate();
                                                Swing.operationsPanel.revalidate();
                                            }
                                            dispose();
                                        }
                                    });
                                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,0,0,0),0,0));
                                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,20,0,0),0,0));
                                }
                            };
                            dialog.setVisible(true);
                        }
                    });

                    add(editCategoryButton,new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,15,0,0),0,0));
                    if (((Outgoing) operation).getAccount().equals("Unknown") || ((Outgoing) operation).getGoal().equals("Unknown")){
                        editAccountButton.setEnabled(false);
                        editCategoryButton.setEnabled(false);
                        unknownComponent=true;
                    }
                }
                else
                {
                    JLabel accountOutLabel=new JLabel(((Transfer)operation).getAccountOut());
                    tempPanel=new JPanel();
                    tempPanel.setLayout(new BorderLayout());
                    tempPanel.add(new JLabel("Outgoing account: "),BorderLayout.WEST);
                    tempPanel.add(accountOutLabel,BorderLayout.EAST);
                    add(tempPanel,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,0,0,0),0,0));
                    JButton editAccountOutButton=new JButton("edit");
                    editAccountOutButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JDialog dialog=new JDialog(Swing.frame,"Transfer",true){
                                {
                                    Toolkit toolkit=Toolkit.getDefaultToolkit();
                                    Dimension dimension=toolkit.getScreenSize();
                                    int height=200;
                                    int width=300;
                                    setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
                                    setLayout(new GridBagLayout());
                                    JComboBox<String> accountBox=new JComboBox<>();
                                    for (String key : Swing.user.getAccountsKeys()) {
                                        accountBox.addItem(key);
                                    }
                                    accountBox.setSelectedItem(((Transfer) operation).getAccountOut());
                                    accountBox.removeItem(((Transfer) operation).getAccountIn());
                                    add(new JLabel("Outgoing Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    JButton cancelButton=new JButton("Cancel");
                                    cancelButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            dispose();
                                        }
                                    });
                                    JButton confirmButton=new JButton("Confirm");
                                    confirmButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (!accountBox.getSelectedItem().equals(((Transfer) operation).getAccountOut())) {
                                                String oldAccount=((Transfer) operation).getAccountOut();
                                                String newAccount=(String)accountBox.getSelectedItem();
                                                Swing.user.setTransferOut(TToolbar.getComponentIndex(OperationsButtons.get(operation)), newAccount);
                                                String line = operation.getDate() + "\n" + "from \"" + ((Transfer) operation).getAccountOut() + "\" to \"" + ((Transfer) operation).getAccountIn() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                                OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                                accountOutLabel.setText(newAccount);
                                                accountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                                accountsPanel.AButtons.get(newAccount).setText(newAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(newAccount))+" UAH");
                                                Main.writeUsersInFile(Swing.users);
                                                Swing.accountsPanel.revalidate();
                                                Swing.operationsPanel.revalidate();
                                            }
                                            dispose();
                                        }
                                    });
                                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,0,0,0),0,0));
                                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,20,0,0),0,0));
                                }
                            };
                            dialog.setVisible(true);
                        }
                    });
                    add(editAccountOutButton,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,15,0,0),0,0));
                    JLabel accountInLabel=new JLabel(((Transfer)operation).getAccountIn());
                    tempPanel=new JPanel();
                    tempPanel.setLayout(new BorderLayout());
                    tempPanel.add(new JLabel("Income account: "),BorderLayout.WEST);
                    tempPanel.add(accountInLabel,BorderLayout.EAST);
                    add(tempPanel,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,0,0,0),0,0));
                    JButton editAccountInButton=new JButton("edit");
                    editAccountInButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JDialog dialog=new JDialog(Swing.frame,"Transfer",true){
                                {
                                    Toolkit toolkit=Toolkit.getDefaultToolkit();
                                    Dimension dimension=toolkit.getScreenSize();
                                    int height=200;
                                    int width=300;
                                    setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
                                    setLayout(new GridBagLayout());
                                    JComboBox<String> accountBox=new JComboBox<>();
                                    for (String key : Swing.user.getAccountsKeys()) {
                                        accountBox.addItem(key);
                                    }
                                    accountBox.setSelectedItem(((Transfer) operation).getAccountIn());
                                    accountBox.removeItem(((Transfer) operation).getAccountOut());
                                    add(new JLabel("Income Account"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 20, 0, 0), 0, 0));
                                    JButton cancelButton=new JButton("Cancel");
                                    cancelButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            dispose();
                                        }
                                    });
                                    JButton confirmButton=new JButton("Confirm");
                                    confirmButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (!accountBox.getSelectedItem().equals(((Transfer) operation).getAccountIn())) {
                                                String oldAccount=((Transfer) operation).getAccountIn();
                                                String newAccount=(String)accountBox.getSelectedItem();
                                                Swing.user.setTransferIn(TToolbar.getComponentIndex(OperationsButtons.get(operation)), newAccount);
                                                String line = operation.getDate() + "\n" + "from \"" + ((Transfer) operation).getAccountOut() + "\" to \"" + ((Transfer) operation).getAccountIn() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                                OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                                accountInLabel.setText(newAccount);
                                                accountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                                accountsPanel.AButtons.get(newAccount).setText(newAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(newAccount))+" UAH");
                                                Main.writeUsersInFile(Swing.users);
                                                Swing.accountsPanel.revalidate();
                                                Swing.operationsPanel.revalidate();
                                            }
                                            dispose();
                                        }
                                    });
                                    add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,0,0,0),0,0));
                                    add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                            new Insets(20,20,0,0),0,0));
                                }
                            };
                            dialog.setVisible(true);
                        }
                    });
                    add(editAccountInButton,new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(30,15,0,0),0,0));
                    if (((Transfer) operation).getAccountIn().equals("Unknown") || ((Transfer) operation).getAccountOut().equals("Unknown")){
                        editAccountInButton.setEnabled(false);
                        editAccountOutButton.setEnabled(false);
                        unknownComponent=true;
                    }
                }
            tempPanel=new JPanel();
            tempPanel.setLayout(new BorderLayout());
            tempPanel.add(new JLabel("Date: "),BorderLayout.WEST);
            tempPanel.add(new JLabel(String.valueOf(operation.getDate())),BorderLayout.EAST);
            add(tempPanel,new GridBagConstraints(0,3,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(30,0,0,0),50,0));
            JLabel sumLabel=new JLabel(String.format("%.2f",operation.getSum())+" UAH");
            tempPanel=new JPanel();
            tempPanel.setLayout(new BorderLayout());
            tempPanel.add(new JLabel("Sum: "),BorderLayout.WEST);
            tempPanel.add(sumLabel,BorderLayout.EAST);
            add(tempPanel,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(30,0,0,0),0,0));
            JButton editSumButton=new JButton("edit");
            editSumButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog=new JDialog(Swing.frame,"Operation",true){
                        {
                            Toolkit toolkit=Toolkit.getDefaultToolkit();
                            Dimension dimension=toolkit.getScreenSize();
                            int height=200;
                            int width=300;
                            setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
                            setLayout(new GridBagLayout());
                            JTextField sumField=new JTextField(20);
                            sumField.setAutoscrolls(false);
                            add(new JLabel("Sum"),new GridBagConstraints(0,0,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(0,0,0,0),0,0));
                            add(sumField,new GridBagConstraints(1,0,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(0,0,0,0),0,0));
                            JButton cancelButton=new JButton("Cancel");
                            cancelButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dispose();
                                }
                            });
                            JButton confirmButton=new JButton("Confirm");
                            confirmButton.addActionListener(new ActionListener() {
                                boolean isError=false;
                                double newSum=0;
                                double oldSum=operation.getSum();
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try{
                                        newSum=Double.valueOf(sumField.getText().replaceAll(",","."));
                                    }
                                    catch (Exception ex){
                                        JOptionPane.showMessageDialog(Swing.frame,"Incorrect input in sum field!","Error",JOptionPane.ERROR_MESSAGE);
                                        isError=true;
                                    }
                                    if (!isError){
                                        if (operation.getClass().equals(Income.class)){
                                            Swing.user.setIncomeSum(IToolbar.getComponentIndex(OperationsButtons.get(operation)),newSum);
                                            accountsPanel.AButtons.get(((Income)operation).getAccount()).setText(((Income)operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Income)operation).getAccount()))+" UAH");
                                            accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                                            sumLabel.setText(String.format("%.2f",operation.getSum())+" UAH");
                                            String line=operation.getDate()+"\n"+"from \""+((Income)operation).getSource()+"\" to \""+((Income)operation).getAccount()+"\": "+String.format("%.2f",newSum)+" UAH";
                                            OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                            Main.writeUsersInFile(Swing.users);
                                            Swing.accountsPanel.revalidate();
                                            Swing.operationsPanel.revalidate();
                                            dispose();
                                        }
                                        else
                                        {
                                            if (operation.getClass().equals(Outgoing.class)){
                                                Swing.user.setOutgoingSum(OToolbar.getComponentIndex(OperationsButtons.get(operation)),newSum);
                                                accountsPanel.AButtons.get(((Outgoing)operation).getAccount()).setText(((Outgoing)operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Outgoing)operation).getAccount()))+" UAH");
                                                accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                                                sumLabel.setText(String.format("%.2f",operation.getSum())+" UAH");
                                                String line=operation.getDate()+"\n"+"from \""+((Outgoing)operation).getAccount()+"\" to \""+((Outgoing)operation).getGoal()+"\": "+String.format("%.2f",newSum)+" UAH";
                                                OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                                categoriesPanel.categoriesMap.replace(((Outgoing)operation).getGoal(),categoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())-oldSum+newSum);
                                                categoriesPanel.CButtons.get(((Outgoing)operation).getGoal()).setText(((Outgoing)operation).getGoal() + ": " + String.format("%.2f",categoriesPanel.categoriesMap.get(((Outgoing)operation).getGoal())) + " UAH");
                                                Main.writeUsersInFile(Swing.users);
                                                Swing.accountsPanel.revalidate();
                                                Swing.categoriesPanel.revalidate();
                                                Swing.operationsPanel.revalidate();
                                                dispose();
                                            }
                                            else
                                            {
                                                Swing.user.setOutgoingSum(TToolbar.getComponentIndex(OperationsButtons.get(operation)),newSum);
                                                accountsPanel.AButtons.get(((Transfer)operation).getAccountIn()).setText(((Transfer)operation).getAccountIn()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountIn()))+" UAH");
                                                accountsPanel.AButtons.get(((Transfer)operation).getAccountOut()).setText(((Transfer)operation).getAccountOut()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountOut()))+" UAH");
                                                sumLabel.setText(String.format("%.2f",operation.getSum())+" UAH");
                                                String line=operation.getDate()+"\n"+"from \""+((Transfer)operation).getAccountOut()+"\" to \""+((Transfer)operation).getAccountIn()+"\": "+String.format("%.2f",newSum)+" UAH";
                                                OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                                Main.writeUsersInFile(Swing.users);
                                                Swing.accountsPanel.revalidate();
                                                Swing.operationsPanel.revalidate();
                                                dispose();
                                            }
                                        }
                                    }
                                }
                            });
                            add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(20,0,0,0),0,0));
                            add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(20,20,0,0),0,0));
                        }
                    };
                    dialog.setVisible(true);
                }
            });
            add(editSumButton,new GridBagConstraints(2,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(30,15,0,0),0,0));
            JButton addCommentButton=new JButton("Add comment");
            JTextArea commentArea=new JTextArea(3,5);
            commentArea.setText(operation.getComment());
            commentArea.setLineWrap(true);
            commentArea.setEditable(false);
            JScrollPane commentPane=new JScrollPane(commentArea);
            JButton editCommentButton=new JButton("edit");
            JButton deleteCommentButton=new JButton("delete");
            JDialog thisDialog=this;
            if (operation.getComment()!=null) {
                add(commentPane, new GridBagConstraints(0, 4, 2, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(30, 0, 0, 0), 0, 30));
                add(editCommentButton,new GridBagConstraints(2,4,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(30,15,0,0),0,0));
                add(deleteCommentButton,new GridBagConstraints(2,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(0,15,0,0),0,0));
            }
            else
                add(addCommentButton,new GridBagConstraints(0,6,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(30,0,0,30),0,0));
            addCommentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog=new JDialog(Swing.frame,"Operation",true){
                        {
                            Toolkit toolkit = Toolkit.getDefaultToolkit();
                            Dimension dimension = toolkit.getScreenSize();
                            int height = 200;
                            int width = 300;
                            setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height);
                            setLayout(new GridBagLayout());
                            JTextArea newCommentArea=new JTextArea(3,5);
                            newCommentArea.setLineWrap(true);
                            JScrollPane newCommentPane=new JScrollPane(newCommentArea);
                            add(newCommentPane,new GridBagConstraints(0,0,2,2,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(0,0,0,0),0,30));
                            JButton cancelButton=new JButton("Cancel");
                            cancelButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dispose();
                                }
                            });
                            JButton confirmButton=new JButton("Confirm");
                            confirmButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!newCommentArea.getText().trim().equals("")){
                                        operation.setComment(newCommentArea.getText());
                                        thisDialog.add(commentPane, new GridBagConstraints(0, 4, 2, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                                new Insets(30, 0, 0, 0), 0, 30));
                                        commentArea.setText(newCommentArea.getText());
                                        thisDialog.add(editCommentButton,new GridBagConstraints(2,4,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                                new Insets(30,15,0,0),0,0));
                                        thisDialog.add(deleteCommentButton,new GridBagConstraints(2,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                                new Insets(0,15,0,0),0,0));
                                        thisDialog.remove(addCommentButton);
                                        thisDialog.revalidate();
                                        Main.writeUsersInFile(Swing.users);
                                    }
                                    dispose();
                                }
                            });
                            add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(20,0,0,0),0,0));
                            add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(20,20,0,0),0,0));
                        }
                    };
                    dialog.setVisible(true);
                }
            });
                editCommentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialog dialog=new JDialog(Swing.frame,"Operation",true){
                            {
                                Toolkit toolkit = Toolkit.getDefaultToolkit();
                                Dimension dimension = toolkit.getScreenSize();
                                int height = 200;
                                int width = 300;
                                setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height);
                                setLayout(new GridBagLayout());
                                JTextArea newCommentArea=new JTextArea(3,5);
                                newCommentArea.setText(operation.getComment());
                                newCommentArea.setLineWrap(true);
                                JScrollPane newCommentPane=new JScrollPane(newCommentArea);
                                add(newCommentPane,new GridBagConstraints(0,0,2,2,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(0,0,0,0),0,30));
                                JButton cancelButton=new JButton("Cancel");
                                cancelButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dispose();
                                    }
                                });
                                JButton confirmButton=new JButton("Confirm");
                                confirmButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (!newCommentArea.getText().equals(operation.getComment())){
                                            if (!newCommentArea.getText().trim().equals("")){
                                                operation.setComment(newCommentArea.getText());
                                                commentArea.setText(newCommentArea.getText());
                                                Main.writeUsersInFile(Swing.users);
                                            }
                                            else
                                            {
                                                operation.setComment(null);
                                                thisDialog.remove(commentPane);
                                                thisDialog.remove(editCommentButton);
                                                thisDialog.remove(deleteCommentButton);
                                                thisDialog.remove(commentPane);
                                                thisDialog.add(addCommentButton,new GridBagConstraints(0,6,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                                        new Insets(30,0,0,30),0,0));
                                                thisDialog.revalidate();
                                                Main.writeUsersInFile(Swing.users);
                                            }
                                        }
                                        dispose();
                                    }
                                });
                                add(cancelButton,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(20,0,0,0),0,0));
                                add(confirmButton,new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(20,20,0,0),0,0));
                            }
                        };
                        dialog.setVisible(true);
                    }
                });
                deleteCommentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this comment?",
                                "Delete comment",JOptionPane.YES_NO_OPTION);
                        if (option==0){
                            operation.setComment(null);
                            thisDialog.remove(commentPane);
                            thisDialog.remove(editCommentButton);
                            thisDialog.remove(deleteCommentButton);
                            thisDialog.remove(commentPane);
                            thisDialog.add(addCommentButton,new GridBagConstraints(0,6,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(30,0,0,30),0,0));
                            thisDialog.revalidate();
                            Main.writeUsersInFile(Swing.users);
                        }
                    }
                });
            JButton okButton=new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            add(okButton,new GridBagConstraints(1,6,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(30,0,0,0),30,0));
            if (unknownComponent)
                editSumButton.setEnabled(false);
        }
    }
}
