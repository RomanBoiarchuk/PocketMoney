package com.Dialogs.OperationDetails;

import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;
import com.company.*;

import javax.swing.*;
import java.awt.*;

import static com.company.OperationsPanel.IToolbar;

public class OperationDetails extends JDialog {
    JButton editSumButton;
    public OperationDetails(Operation operation){
        super(Swing.frame,"Operation",true);
        // decorating dialog window
        setLayout(new GridBagLayout());
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension dimension=toolkit.getScreenSize();
        int height=450;
        int width=500;
        setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
        boolean unknownComponent=false; // to check if any operation's field is unknown
        JPanel tempPanel;
        if (operation.getClass().equals(Income.class)){

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
            editAccountButton.addActionListener(e -> {
                JDialog dialog=new JDialog(Swing.frame,"Outgoing",true){
                    {
                        Toolkit toolkit13 =Toolkit.getDefaultToolkit();
                        Dimension dimension13 = toolkit13.getScreenSize();
                        int height13 =200;
                        int width13 =300;
                        setBounds((dimension13.width- width13)/2,(dimension13.height- height13)/2, width13, height13);
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
                        cancelButton.addActionListener(e13 -> dispose());
                        JButton confirmButton=new JButton("Confirm");
                        confirmButton.addActionListener(e->{
                            if (!accountBox.getSelectedItem().equals(((Outgoing) operation).getAccount())) {
                                String oldAccount=((Outgoing) operation).getAccount();
                                Swing.user.setOutgoingAccount(OperationsPanel.OToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)), (String)accountBox.getSelectedItem());
                                String line = operation.getDate() + "\n" + "from \"" + ((Outgoing) operation).getAccount() + "\" to \"" + ((Outgoing) operation).getGoal() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                accountLabel.setText(((Outgoing) operation).getAccount());
                                AccountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                AccountsPanel.AButtons.get(((Outgoing) operation).getAccount()).setText(((Outgoing) operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Outgoing) operation).getAccount()))+" UAH");
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
            JLabel categoryLabel=new JLabel(((Outgoing)operation).getGoal());
            tempPanel=new JPanel();
            tempPanel.setLayout(new BorderLayout());
            tempPanel.add(new JLabel("Category: "),BorderLayout.WEST);
            tempPanel.add(categoryLabel,BorderLayout.EAST);
            add(tempPanel,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(30,0,0,0),0,0));
            JButton editCategoryButton=new JButton("edit");

            editCategoryButton.addActionListener(e -> {
                JDialog dialog=new JDialog(Swing.frame,"Outgoing",true){
                    {
                        // decorating editCategory window
                        Toolkit toolkit14 =Toolkit.getDefaultToolkit();
                        Dimension dimension14 = toolkit14.getScreenSize();
                        int height14 =200;
                        int width14 =300;
                        setBounds((dimension14.width- width14)/2,(dimension14.height- height14)/2, width14, height14);
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
                        cancelButton.addActionListener(e14 -> dispose());
                        JButton confirmButton=new JButton("Confirm");
                        confirmButton.addActionListener(e->{
                            if (!categoriesBox.getSelectedItem().equals(((Outgoing) operation).getGoal())) {
                                String oldCategory=((Outgoing) operation).getGoal();
                                String newCategory=(String)categoriesBox.getSelectedItem();
                                Swing.user.setOutgoingGoal(OperationsPanel.OToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)), (String)categoriesBox.getSelectedItem());
                                String line = operation.getDate() + "\n" + "from \"" + ((Outgoing) operation).getAccount() + "\" to \"" + ((Outgoing) operation).getGoal() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                categoryLabel.setText(newCategory);
                                CategoriesPanel.categoriesMap.replace(oldCategory,CategoriesPanel.categoriesMap.get(oldCategory)-operation.getSum());
                                CategoriesPanel.categoriesMap.replace(newCategory,CategoriesPanel.categoriesMap.get(newCategory)+operation.getSum());
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
            editAccountOutButton.addActionListener(e -> {
                JDialog dialog=new JDialog(Swing.frame,"Transfer",true){
                    {
                        Toolkit toolkit15 =Toolkit.getDefaultToolkit();
                        Dimension dimension15 = toolkit15.getScreenSize();
                        int height15 =200;
                        int width15 =300;
                        setBounds((dimension15.width- width15)/2,(dimension15.height- height15)/2, width15, height15);
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
                        cancelButton.addActionListener(e15 -> dispose());
                        JButton confirmButton=new JButton("Confirm");
                        confirmButton.addActionListener(e->{
                            if (!accountBox.getSelectedItem().equals(((Transfer) operation).getAccountOut())) {
                                String oldAccount=((Transfer) operation).getAccountOut();
                                String newAccount=(String)accountBox.getSelectedItem();
                                Swing.user.setTransferOut(OperationsPanel.TToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)), newAccount);
                                String line = operation.getDate() + "\n" + "from \"" + ((Transfer) operation).getAccountOut() + "\" to \"" + ((Transfer) operation).getAccountIn() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                accountOutLabel.setText(newAccount);
                                AccountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                AccountsPanel.AButtons.get(newAccount).setText(newAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(newAccount))+" UAH");
                                UsersChanger.writeUsersInFile(Swing.users);
                                Swing.accountsPanel.revalidate();
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
            editAccountInButton.addActionListener(e -> {
                JDialog dialog=new JDialog(Swing.frame,"Transfer",true){
                    {
                        Toolkit toolkit16 =Toolkit.getDefaultToolkit();
                        Dimension dimension16 = toolkit16.getScreenSize();
                        int height16 =200;
                        int width16 =300;
                        setBounds((dimension16.width- width16)/2,(dimension16.height- height16)/2, width16, height16);
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
                        cancelButton.addActionListener(e16 -> dispose());
                        JButton confirmButton=new JButton("Confirm");
                        confirmButton.addActionListener(e->{
                            if (!accountBox.getSelectedItem().equals(((Transfer) operation).getAccountIn())) {
                                String oldAccount=((Transfer) operation).getAccountIn();
                                String newAccount=(String)accountBox.getSelectedItem();
                                Swing.user.setTransferIn(OperationsPanel.TToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)), newAccount);
                                String line = operation.getDate() + "\n" + "from \"" + ((Transfer) operation).getAccountOut() + "\" to \"" + ((Transfer) operation).getAccountIn() + "\": " + String.format("%.2f", operation.getSum()) + " UAH";
                                OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                accountInLabel.setText(newAccount);
                                AccountsPanel.AButtons.get(oldAccount).setText(oldAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(oldAccount))+" UAH");
                                AccountsPanel.AButtons.get(newAccount).setText(newAccount+": "+String.format("%.2f", Swing.user.getAccountBalance(newAccount))+" UAH");
                                UsersChanger.writeUsersInFile(Swing.users);
                                Swing.accountsPanel.revalidate();
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
            add(editAccountInButton,new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                    new Insets(30,15,0,0),0,0));
            if (((Transfer) operation).getAccountIn().equals("Unknown") || ((Transfer) operation).getAccountOut().equals("Unknown")){
                editAccountInButton.setEnabled(false);
                editAccountOutButton.setEnabled(false);
                unknownComponent=true;
            }
        }
        // adding date panel
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

        editSumButton=new JButton("edit");
        editSumButton.addActionListener(e -> {
            JDialog dialog=new JDialog(Swing.frame,"Operation",true){
                {
                    // decorating editSum window
                    Toolkit toolkit17 =Toolkit.getDefaultToolkit();
                    Dimension dimension17 = toolkit17.getScreenSize();
                    int height17 =200;
                    int width17 =300;
                    setBounds((dimension17.width- width17)/2,(dimension17.height- height17)/2, width17, height17);
                    setLayout(new GridBagLayout());
                    JTextField sumField=new JTextField(20);
                    sumField.setAutoscrolls(false);
                    add(new JLabel("Sum"),new GridBagConstraints(0,0,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(0,0,0,0),0,0));
                    add(sumField,new GridBagConstraints(1,0,2,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(0,0,0,0),0,0));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e17 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                        boolean isError=false;
                        double newSum=0;
                        double oldSum=operation.getSum();
                        try{
                            newSum=Double.valueOf(sumField.getText().replaceAll(",","."));
                        }
                        catch (Exception ex){
                            JOptionPane.showMessageDialog(Swing.frame,"Incorrect input in sum field!","Error",JOptionPane.ERROR_MESSAGE);
                            isError=true;
                        }
                        if (!isError){
                            if (operation.getClass().equals(Income.class)){
                                Swing.user.setIncomeSum(IToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)),newSum);
                                AccountsPanel.AButtons.get(((Income)operation).getAccount()).setText(((Income)operation).getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Income)operation).getAccount()))+" UAH");
                                AccountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                                sumLabel.setText(String.format("%.2f",operation.getSum())+" UAH");
                                String line=operation.getDate()+"\n"+"from \""+((Income)operation).getSource()+"\" to \""+((Income)operation).getAccount()+"\": "+String.format("%.2f",newSum)+" UAH";
                                OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                UsersChanger.writeUsersInFile(Swing.users);
                                Swing.accountsPanel.revalidate();
                                Swing.operationsPanel.revalidate();
                                dispose();
                            }
                            else
                            {
                                if (operation.getClass().equals(Outgoing.class)){
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
                                    dispose();
                                }
                                else
                                {
                                    Swing.user.setTransferSum(OperationsPanel.TToolbar.getComponentIndex(OperationsPanel.OperationsButtons.get(operation)),newSum);
                                    AccountsPanel.AButtons.get(((Transfer)operation).getAccountIn()).setText(((Transfer)operation).getAccountIn()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountIn()))+" UAH");
                                    AccountsPanel.AButtons.get(((Transfer)operation).getAccountOut()).setText(((Transfer)operation).getAccountOut()+": "+String.format("%.2f", Swing.user.getAccountBalance(((Transfer)operation).getAccountOut()))+" UAH");
                                    sumLabel.setText(String.format("%.2f",operation.getSum())+" UAH");
                                    String line=operation.getDate()+"\n"+"from \""+((Transfer)operation).getAccountOut()+"\" to \""+((Transfer)operation).getAccountIn()+"\": "+String.format("%.2f",newSum)+" UAH";
                                    OperationsPanel.OperationsButtons.get(operation).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                    UsersChanger.writeUsersInFile(Swing.users);
                                    Swing.accountsPanel.revalidate();
                                    Swing.operationsPanel.revalidate();
                                    dispose();
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
        });
        add(editSumButton,new GridBagConstraints(2,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,15,0,0),0,0));
        // editing commentArea
        JButton addCommentButton=new JButton("Add comment");
        JTextArea commentArea=new JTextArea(3,5);
        commentArea.setText(operation.getComment());
        commentArea.setLineWrap(true);
        commentArea.setEditable(false);
        JScrollPane commentPane=new JScrollPane(commentArea);
        JButton editCommentButton=new JButton("edit");
        JButton deleteCommentButton=new JButton("delete");
        JDialog mainDialog=this;
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
        addCommentButton.addActionListener(e -> {
            JDialog dialog=new JDialog(Swing.frame,"Operation",true){
                {
                    // decorating addComment window
                    Toolkit toolkit18 = Toolkit.getDefaultToolkit();
                    Dimension dimension18 = toolkit18.getScreenSize();
                    int height18 = 200;
                    int width18 = 300;
                    setBounds((dimension18.width - width18) / 2, (dimension18.height - height18) / 2, width18, height18);
                    setLayout(new GridBagLayout());
                    JTextArea newCommentArea=new JTextArea(3,5);
                    newCommentArea.setLineWrap(true);
                    JScrollPane newCommentPane=new JScrollPane(newCommentArea);
                    add(newCommentPane,new GridBagConstraints(0,0,2,2,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(0,0,0,0),0,30));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e18 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e19 -> {
                        if (!newCommentArea.getText().trim().equals("")){
                            operation.setComment(newCommentArea.getText());
                            mainDialog.add(commentPane, new GridBagConstraints(0, 4, 2, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                    new Insets(30, 0, 0, 0), 0, 30));
                            commentArea.setText(newCommentArea.getText());
                            mainDialog.add(editCommentButton,new GridBagConstraints(2,4,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(30,15,0,0),0,0));
                            mainDialog.add(deleteCommentButton,new GridBagConstraints(2,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                    new Insets(0,15,0,0),0,0));
                            mainDialog.remove(addCommentButton);
                            mainDialog.revalidate();
                            UsersChanger.writeUsersInFile(Swing.users);
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
        editCommentButton.addActionListener(e -> {
            JDialog dialog=new JDialog(Swing.frame,"Operation",true){
                {
                    // decorating editComment window
                    Toolkit toolkit19 = Toolkit.getDefaultToolkit();
                    Dimension dimension19 = toolkit19.getScreenSize();
                    int height19 = 200;
                    int width19 = 300;
                    setBounds((dimension19.width - width19) / 2, (dimension19.height - height19) / 2, width19, height19);
                    setLayout(new GridBagLayout());
                    JTextArea newCommentArea=new JTextArea(3,5);
                    newCommentArea.setText(operation.getComment());
                    newCommentArea.setLineWrap(true);
                    JScrollPane newCommentPane=new JScrollPane(newCommentArea);
                    add(newCommentPane,new GridBagConstraints(0,0,2,2,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                            new Insets(0,0,0,0),0,30));
                    JButton cancelButton=new JButton("Cancel");
                    cancelButton.addActionListener(e110 -> dispose());
                    JButton confirmButton=new JButton("Confirm");
                    confirmButton.addActionListener(e->{
                        if (!newCommentArea.getText().equals(operation.getComment())){
                            if (!newCommentArea.getText().trim().equals("")){
                                operation.setComment(newCommentArea.getText());
                                commentArea.setText(newCommentArea.getText());
                                UsersChanger.writeUsersInFile(Swing.users);
                            }
                            else
                            {
                                operation.setComment(null);
                                mainDialog.remove(commentPane);
                                mainDialog.remove(editCommentButton);
                                mainDialog.remove(deleteCommentButton);
                                mainDialog.remove(commentPane);
                                mainDialog.add(addCommentButton,new GridBagConstraints(0,6,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                                        new Insets(30,0,0,30),0,0));
                                mainDialog.revalidate();
                                UsersChanger.writeUsersInFile(Swing.users);
                            }
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
        deleteCommentButton.addActionListener(e -> {
            int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this comment?",
                    "Delete comment",JOptionPane.YES_NO_OPTION);
            if (option==0){
                operation.setComment(null);
                mainDialog.remove(commentPane);
                mainDialog.remove(editCommentButton);
                mainDialog.remove(deleteCommentButton);
                mainDialog.remove(commentPane);
                mainDialog.add(addCommentButton,new GridBagConstraints(0,6,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                        new Insets(30,0,0,30),0,0));
                mainDialog.revalidate();
                UsersChanger.writeUsersInFile(Swing.users);
            }
        });
        JButton okButton=new JButton("OK");
        okButton.addActionListener(e -> dispose());
        add(okButton,new GridBagConstraints(1,6,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),30,0));
        if (unknownComponent)
            editSumButton.setEnabled(false);
    }
}
