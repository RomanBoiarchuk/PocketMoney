package com.Dialogs.OperationDetails;
import com.PocketMoney.Operation;
import com.company.*;
import javax.swing.*;
import java.awt.*;

public abstract class OperationDetails extends JDialog {
    JButton editSumButton;
    JLabel sumLabel;
    abstract void changeSum(Operation operation,double newSum);
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
        sumLabel=new JLabel(String.format("%.2f",operation.getSum())+" UAH");
        tempPanel=new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(new JLabel("Sum: "),BorderLayout.WEST);
        tempPanel.add(sumLabel,BorderLayout.EAST);
        add(tempPanel,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));

        editSumButton=new JButton("edit");
        editSumButton.addActionListener(e -> {
            JDialog dialog=new FieldChangerWindow("Operation"){
                {
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
                        try{
                            newSum=Double.valueOf(sumField.getText().replaceAll(",","."));
                        }
                        catch (Exception ex){
                            JOptionPane.showMessageDialog(Swing.frame,"Incorrect input in sum field!","Error",JOptionPane.ERROR_MESSAGE);
                            isError=true;
                        }
                        if (!isError){
                            changeSum(operation,newSum);
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
            JDialog dialog=new FieldChangerWindow("Operation"){
                {
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
            JDialog dialog=new FieldChangerWindow("Operation"){
                {
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
