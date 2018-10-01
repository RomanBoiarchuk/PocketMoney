package com.Dialogs.OperationDetails;

import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;
import com.company.*;

import javax.swing.*;
import java.awt.*;

import static com.company.OperationsPanel.IToolbar;

public abstract class OperationDetails extends JDialog {
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
        JPanel tempPanel;
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
    }
}
